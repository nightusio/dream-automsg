package cc.dreamcode.plugin.service;

import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.plugin.AutoMessagePlugin;
import cc.dreamcode.plugin.event.AutoMsgStartEvent;
import cc.dreamcode.plugin.event.EventHandler;
import cc.dreamcode.plugin.runnable.AutoMsgRunnable;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Scheduler(interval = 20, delay = 20)
public class AutoMsgService {

    private @Inject AutoMessagePlugin autoMessagePlugin;
    private @Inject AutoMsgRunnable autoMsgRunnable;
    ;
    public void run() {
        if (EventHandler.handle(new AutoMsgStartEvent())) {
            return;
        }

        this.autoMessagePlugin.getServer().getScheduler().runTask(this.autoMessagePlugin, () ->
                this.autoMsgRunnable.run());
    }
}
