package fr.whitefox.tasks;

import fr.whitefox.Configuration;
import fr.whitefox.Main;
import fr.whitefox.db.MongoDB;
import fr.whitefox.events.Messages;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Rank {

    private static final JDA jda = Main.getJDAInstance();
    private static final Configuration config = Configuration.getInstance();
    public static HashMap<Long, Integer> voiceXp = new HashMap<>();
    public static HashMap<Long, Integer> textualXp = Messages.xp;

    public static void addXpVoice() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            if (config.getProperty("server_id") == null) return;
            Guild guild = jda.getGuildById(config.getProperty("server_id"));
            Configuration config = Configuration.getInstance();

            HashMap<Long, Integer> updates = new HashMap<>();
            for (VoiceChannel voiceChannel : guild.getVoiceChannelCache()) {
                if (voiceChannel.getMembers().size() < 2) return;
                for (int i = 0; i < voiceChannel.getMembers().size(); i++) {
                    if (voiceChannel.getMembers().get(i).getVoiceState().isSelfMuted() || voiceChannel.getMembers().get(i).getVoiceState().isSelfDeafened()) return;
                    int randomNum = ThreadLocalRandom.current().nextInt(5, 12 + 1);
                    long userID = voiceChannel.getMembers().get(i).getIdLong();

                    if (voiceXp.containsKey(userID)) {
                        updates.put(userID, voiceXp.get(userID) + randomNum * (!config.exist("boost") ? 1 : Integer.parseInt(config.getProperty("boost"))));
                    } else {
                        updates.put(userID, randomNum);
                    }

                    voiceXp.putAll(updates);
                }
            }
        };

        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.MINUTES);
    }

    public static void addXpText() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            for (Map.Entry<Long, Integer> entry : textualXp.entrySet()) {
                long userId = entry.getKey();
                int xp = entry.getValue();

                if (MongoDB.isRegister(userId)) {
                    MongoDB.incrementXp(userId, xp);
                } else {
                    MongoDB.register(userId, xp, 0);
                }
            }
        };

        executor.scheduleWithFixedDelay(task, 0, 20, TimeUnit.SECONDS);
    }
}
