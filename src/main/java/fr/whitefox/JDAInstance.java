package fr.whitefox;

import fr.whitefox.commands.BoostCommand;
import fr.whitefox.commands.ConfigCommand;
import fr.whitefox.commands.RankCommand;
import fr.whitefox.events.Messages;
import fr.whitefox.events.Ready;
import fr.whitefox.events.VoiceLeave;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import static net.dv8tion.jda.api.requests.GatewayIntent.GUILD_VOICE_STATES;

public class JDAInstance {
    public static JDA createJDAInstance(String token) {
        return JDABuilder.createDefault(token)
                .setActivity(Activity.playing("Rank Bot !"))
                .enableIntents(GUILD_VOICE_STATES)
                .addEventListeners(new Ready(), new VoiceLeave(), new RankCommand(), new Messages(), new BoostCommand(), new ConfigCommand())
                .build();
    }
}
