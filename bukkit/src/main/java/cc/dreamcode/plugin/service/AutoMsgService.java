package cc.dreamcode.plugin.service;

import cc.dreamcode.plugin.AutoMessagePlugin;
import cc.dreamcode.plugin.config.PluginConfig;
import cc.dreamcode.plugin.event.AutoMsgStartEvent;
import cc.dreamcode.plugin.event.EventHandler;
import cc.dreamcode.plugin.runnable.AutoMsgRunnable;
import eu.okaeri.injector.annotation.Inject;

public class AutoMsgService {

    private @Inject AutoMessagePlugin autoMessagePlugin;
    private @Inject PluginConfig pluginConfig;

    public void callScheduler() {
        if (EventHandler.handle(new AutoMsgStartEvent())) {
            return;
        }

        long runIntervalLong = this.pluginConfig.msgInterval.getSeconds() * 20;
        int runInterval = Math.round((runIntervalLong));

        this.autoMessagePlugin.getServer().getScheduler().runTaskTimer(
                this.autoMessagePlugin,
                this.autoMessagePlugin.createInstance(AutoMsgRunnable.class),
                runInterval,
                runInterval
        );
    }
}
