package cc.dreamcode.plugin.runnable;

import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.plugin.AutoMessagePlugin;
import cc.dreamcode.plugin.config.PluginConfig;
import cc.dreamcode.plugin.service.AutoMsgService;
import eu.okaeri.injector.annotation.Inject;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoMsgRunnable extends BukkitRunnable {

    private @Inject
    AutoMessagePlugin autoMessagePlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject
    AutoMsgService autoMsgService;

    private int currentMessageIndex = 0;

    @Override
    public void run() {
        if (this.pluginConfig.messages.isEmpty()) {
            return;
        }

        String messageString = this.pluginConfig.messages.get(currentMessageIndex);
        BukkitNotice message = new BukkitNotice(this.pluginConfig.noticeType, messageString);
        for (Player p : Bukkit.getOnlinePlayers()) {
            message.send(p);
        }

        currentMessageIndex++;
        if (currentMessageIndex >= this.pluginConfig.messages.size()) {
            currentMessageIndex = 0;
        }
    }

}
