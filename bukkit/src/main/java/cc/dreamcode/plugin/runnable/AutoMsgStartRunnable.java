package cc.dreamcode.plugin.runnable;

import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.plugin.AutoMessagePlugin;
import cc.dreamcode.plugin.config.PluginConfig;
import cc.dreamcode.plugin.service.AutoMsgService;
import cc.dreamcode.plugin.service.AutoMsgState;

import eu.okaeri.injector.annotation.Inject;

@Scheduler(delay = 20, interval = 20)
public class AutoMsgStartRunnable implements Runnable {

    private @Inject AutoMessagePlugin autoMessagePlugin;
    private @Inject AutoMsgService autoMsgService;
    private @Inject PluginConfig pluginConfig;


    @Override
    public void run() {
        if (!this.autoMsgService.getAutoMsgState().equals(AutoMsgState.OFF)) {
            return;
        }

        this.autoMsgService.setAutoMsgState(AutoMsgState.WAITING);

        if (this.autoMsgService.getRunTime().get() == 0L) {
            this.autoMsgService.getRunTime().set(System.currentTimeMillis());
        }

        this.autoMessagePlugin.getServer().getScheduler().runTask(this.autoMessagePlugin, () ->
                this.autoMsgService.start());
    }
}
