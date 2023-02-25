package cc.dreamcode.plugin.config;

import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-AutoMessage (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Gdzie wiadomosc ma sie wyswietlac, Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
    public NoticeType noticeType = NoticeType.CHAT;

    @Comment("Czy ma wysylac wiadomosci")
    public boolean shouldSendMessages = true;

    @Comment("Co ile ma byc wysylana wiadomosc")
    public int msgInterval = 10;

    @Comment("Lista wiadomosci ktore wysyla plugin")
    public List<String> messages = new ArrayList<>(Arrays.asList("Jest to wiadomosc testowa!", "Ta wiadomosc mozesz zmienic w config.yml!", "Kolejna testowa wiadomosci"));
}
