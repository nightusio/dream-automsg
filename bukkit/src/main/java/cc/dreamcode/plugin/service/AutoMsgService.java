package cc.dreamcode.plugin.service;

import cc.dreamcode.menu.bukkit.base.BukkitMenuPaginated;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.plugin.AutoMessagePlugin;
import cc.dreamcode.plugin.event.AutoMsgStartEvent;
import cc.dreamcode.plugin.event.EventHandler;
import cc.dreamcode.plugin.exception.PluginRuntimeException;
import cc.dreamcode.plugin.runnable.AutoMsgRunnable;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class AutoMsgService {

    private @Inject AutoMessagePlugin autoMessagePlugin;

    private final AtomicLong runTime = new AtomicLong();
    private AutoMsgState autoMsgState = AutoMsgState.OFF;


    public void start() {
        if (this.autoMsgState.equals(AutoMsgState.OFF)) {
            throw new PluginRuntimeException("automsg is not turned OFF, automsg-task start is aborted.");
        }

        if (EventHandler.handle(new AutoMsgStartEvent())) {
            return;
        }

        this.autoMessagePlugin.createInstance(AutoMsgRunnable.class)
                .runTaskTimerAsynchronously(this.autoMessagePlugin, 20L, 20L);
    }
}
