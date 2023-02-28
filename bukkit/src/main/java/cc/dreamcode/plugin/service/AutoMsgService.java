package cc.dreamcode.plugin.service;

import cc.dreamcode.plugin.AutoMessagePlugin;
import cc.dreamcode.plugin.event.AutoMsgStartEvent;
import cc.dreamcode.plugin.event.EventHandler;
import cc.dreamcode.plugin.runnable.AutoMsgRunnable;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutoMsgService {

    private @Inject AutoMessagePlugin autoMessagePlugin;
    private @Inject AutoMsgRunnable autoMsgRunnable;

    public void start() {
        if (EventHandler.handle(new AutoMsgStartEvent())) {
            return;
        }

        this.autoMessagePlugin.createInstance(AutoMsgRunnable.class)
                .runTaskTimerAsynchronously(this.autoMessagePlugin, 20L, 20L);
    }
}
