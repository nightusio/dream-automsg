package cc.dreamcode.plugin.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.plugin.config.MessageConfig;
import cc.dreamcode.plugin.config.PluginConfig;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredPermission
public class AutoMsgCommand extends BukkitCommand {

    private @Inject
    MessageConfig messageConfig;
    private @Inject
    PluginConfig pluginConfig;

    public AutoMsgCommand() {
        super("automsg", "am");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        final long time = System.currentTimeMillis();

        if(args.length > 1) {
            sender.sendMessage("Poprawne uzycie: /automsg (reload, time) <czas>");
            return;
        }
        try {
            this.messageConfig.load();
            this.pluginConfig.load();

            this.messageConfig.reloaded.send(sender, new MapBuilder<String, Object>()
                    .put("time", TimeUtil.convertMills(System.currentTimeMillis() - time))
                    .build());
        }
        catch (NullPointerException | OkaeriException e) {
            e.printStackTrace();

            this.messageConfig.reloadError.send(sender, new MapBuilder<String, Object>()
                    .put("error", e.getMessage())
                    .build());
        }
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        return null;
    }
}