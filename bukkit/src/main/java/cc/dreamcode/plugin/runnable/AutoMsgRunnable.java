package cc.dreamcode.plugin.runnable;

import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.plugin.AutoMessagePlugin;
import cc.dreamcode.plugin.config.PluginConfig;
import cc.dreamcode.plugin.service.AutoMsgService;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoMsgRunnable extends BukkitRunnable {

    private @Inject AutoMessagePlugin autoMessagePlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject AutoMsgService autoMsgService;
    private int currentMessageIndex = 0;
    private int runStart = 0;

    @Override
    public void run() {
        Long runIntervalLong = pluginConfig.msgInterval.getSeconds() * 20;
        int runInterval = Math.round((runIntervalLong));

        if (this.pluginConfig.messages.isEmpty() || !this.pluginConfig.shouldSendMessages) {
            return;
        }

        runStart++;
        if (runStart >= runInterval) {
            BukkitNotice message = this.pluginConfig.messages.get(currentMessageIndex);
            this.autoMessagePlugin.getServer().getOnlinePlayers()
                    .forEach(message::send);

            currentMessageIndex++;
            if (currentMessageIndex >= this.pluginConfig.messages.size()) {
                currentMessageIndex = 0;
            }

            runStart = 0;
        }
    }
}