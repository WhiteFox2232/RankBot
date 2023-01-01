package fr.whitefox.events;

import fr.whitefox.Configuration;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Messages extends ListenerAdapter {

    public static HashMap<Long, Long> rateLimit = new HashMap<>();
    public static HashMap<Long, Integer> xp = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromType(ChannelType.TEXT) && event.getAuthor().isBot()) return;
        long user = event.getAuthor().getIdLong();
        long messageId = event.getMessage().getTimeCreated().toEpochSecond();
        Configuration config = Configuration.getInstance();

        if (!(rateLimit.containsKey(user))) {
            int randomNum = ThreadLocalRandom.current().nextInt(10, 24 + 1);
            rateLimit.put(user, messageId);
            xp.put(user, randomNum * (!config.exist("boost") ? 1 : Integer.parseInt(config.getProperty("boost"))));
        } else {
            if ((messageId - rateLimit.get(user)) > 10) {
                int randomNum = ThreadLocalRandom.current().nextInt(10, 24 + 1);
                xp.put(user, xp.get(user) + randomNum * (!config.exist("boost") ? 1 : Integer.parseInt(config.getProperty("boost"))));
                rateLimit.replace(user, messageId);
            }
        }
    }
}