package cc.dreamcode.plugin.runnable;

import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.plugin.AutoMessagePlugin;
import cc.dreamcode.plugin.service.AutoMsgService;
import eu.okaeri.injector.annotation.Inject;

@Scheduler(delay = 20, interval = 20)
public class AutoMsgStartRunnable implements Runnable {

    private @Inject AutoMessagePlugin autoMessagePlugin;
    private @Inject AutoMsgService autoMsgService;


    @Override
    public void run() {
        this.autoMessagePlugin.getServer().getScheduler().runTask(this.autoMessagePlugin, () ->
                this.autoMsgService.start());
    }
}