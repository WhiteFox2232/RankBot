package fr.whitefox.commands;

import fr.whitefox.Configuration;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BoostCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("boost")) {
            Configuration config = Configuration.getInstance();
            if (event.getSubcommandName().equals("get")) {
                String boost = config.getProperty("boost");
                if (boost.equals("1")) boost = "défaut";
                event.reply("Le boost du serveur est actuellement défini sur : `" + (!config.exist("boost") ? "défaut" : boost) + "`.").queue();
            }

            if (event.getSubcommandName().equals("set")) {
                String boost = event.getOption("multiplicateur").getAsString();
                config.setProperty("boost", boost);
                if (boost.equals("1")) boost = "défaut";
                event.reply("<:Allow:951975822745563197> Le boost d'xp du serveur a bien été défini sur `" + boost + "`.").queue();
            }
        }
    }
}