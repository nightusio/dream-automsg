package cc.dreamcode.plugin;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.serdes.bukkit.okaeri.MenuBuilderSerdes;
import cc.dreamcode.notice.bukkit.BukkitNoticeProvider;
import cc.dreamcode.notice.bukkit.okaeri_serdes.BukkitNoticeSerdes;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.CommandComponentResolver;
import cc.dreamcode.platform.bukkit.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.bukkit.component.DocumentPersistenceComponentResolver;
import cc.dreamcode.platform.bukkit.component.DocumentRepositoryComponentResolver;
import cc.dreamcode.platform.bukkit.component.ListenerComponentResolver;
import cc.dreamcode.platform.bukkit.component.RunnableComponentResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.plugin.command.AutoMsgCommand;
import cc.dreamcode.plugin.config.MessageConfig;
import cc.dreamcode.plugin.config.PluginConfig;
import cc.dreamcode.plugin.mcversion.VersionProvider;
import cc.dreamcode.plugin.runnable.AutoMsgStartRunnable;
import cc.dreamcode.plugin.service.AutoMsgService;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;

public final class AutoMessagePlugin extends DreamBukkitPlatform {

    @Getter private static AutoMessagePlugin autoMessagePlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        autoMessagePlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        this.registerInjectable(VersionProvider.getVersionAccessor());
        this.registerInjectable(BukkitTasker.newPool(this));
        this.registerInjectable(BukkitMenuProvider.create(this));
        this.registerInjectable(BukkitNoticeProvider.create(this));
        this.registerInjectable(BukkitCommandProvider.create(this, this.getInjector()));

        componentManager.registerResolver(CommandComponentResolver.class);
        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(RunnableComponentResolver.class);

        componentManager.registerResolver(ConfigurationComponentResolver.class);
        componentManager.registerComponent(MessageConfig.class, messageConfig -> this.getInject(BukkitCommandProvider.class).ifPresent(bukkitCommandProvider -> {
            bukkitCommandProvider.setNoPermissionMessage(messageConfig.noPermission);
            bukkitCommandProvider.setNoPlayerMessage(messageConfig.noPlayer);
        }));
        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {

            componentManager.registerResolver(DocumentPersistenceComponentResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentResolver.class);

        });
        componentManager.registerComponent(AutoMsgService.class);
        componentManager.registerComponent(AutoMsgStartRunnable.class);
        componentManager.registerComponent(AutoMsgCommand.class);
    }

    @Override
    public void disable() {
        // features need to be call when server is stopping
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-AutoMessage", "1.0", "Nightusio");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigurationSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getPersistenceSerdesPack() {
        return registry -> {

        };
    }

}
