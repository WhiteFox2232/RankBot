package fr.whitefox.events;

import fr.whitefox.tasks.Rank;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class Ready implements EventListener {
    @Override
    public void onEvent(GenericEvent event) {
        if (!(event instanceof ReadyEvent)) return;
        System.out.println("• Bot en ligne sous l'identité " + event.getJDA().getSelfUser().getAsTag() + " (" + event.getJDA().getSelfUser().getId() + ").");
        Rank.addXpVoice();
        Rank.addXpText();
    }
}
