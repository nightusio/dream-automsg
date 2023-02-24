package cc.dreamcode.plugin.runnable;

import cc.dreamcode.plugin.AutoMessagePlugin;
import cc.dreamcode.plugin.service.AutoMsgService;
import cc.dreamcode.plugin.service.AutoMsgState;
import cc.dreamcode.plugin.stereotypes.Scheduler;
import eu.okaeri.injector.annotation.Inject;

@Scheduler(delay = 20, repeat = 20)
public class AutoMsgStartRunnable implements Runnable {

    private @Inject
    AutoMessagePlugin autoMessagePlugin;
    private @Inject
    AutoMsgService autoMsgService;


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
