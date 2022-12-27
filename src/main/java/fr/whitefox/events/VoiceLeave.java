package fr.whitefox.events;

import fr.whitefox.db.MongoDB;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static fr.whitefox.tasks.Rank.voiceXp;

public class VoiceLeave extends ListenerAdapter {

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        if (event.getChannelJoined() == null) {
            long userId = event.getMember().getIdLong();
            if (voiceXp.containsKey(userId)) {
                int xp = voiceXp.get(userId);

                if (MongoDB.isRegister(userId)) {
                    MongoDB.incrementXp(userId, xp);
                } else {
                    MongoDB.register(userId, xp, 0);
                }

                event.getMember().getUser().openPrivateChannel()
                        .flatMap(channel -> channel.sendMessage(":rocket: » Félicitations **" + event.getMember().getUser().getName() + "** ! Vous avez gagné un total de **" + xp + "xp** pour cette session de vocal."))
                        .queue();

                voiceXp.remove(userId);
            }
        }
    }
}
