package fr.whitefox.commands;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import fr.whitefox.Configuration;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ConfigCommand extends ListenerAdapter {

    public static boolean tryMongoConnexion(String url) {
        try {
            MongoClient mongo = new MongoClient(new MongoClientURI(url));
            mongo.close();
            return true;
        } catch (ExceptionInInitializerError | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("config")) {
            Configuration config = Configuration.getInstance();
            if (event.getSubcommandName().equals("bdd")) {
                String bdd = event.getOption("lien").getAsString();

                if (!tryMongoConnexion(bdd)) {
                    event.reply("<:Deny:951975823324364830> La connexion à la bdd est impossible !").queue();
                    return;
                }
                config.setProperty("bdd", bdd);
                event.reply("<:Allow:951975822745563197> La base de donnée a bien été **mise à jour**.").queue();
            }

            if (event.getSubcommandName().equals("server")) {
                String server = event.getOption("id").getAsString();
                try {
                    Guild guild = event.getJDA().getGuildById(server);
                    if(guild == null) {
                        event.reply("<:Deny:951975823324364830> Le bot n'est pas sur le serveur !").queue();
                        return;
                    }
                    config.setProperty("server_id", server);
                    event.reply("<:Allow:951975822745563197> Le serveur à écouter a bien **mis à jour**.").queue();
                } catch(NumberFormatException e) {
                    event.reply("<:Deny:951975823324364830> Le serveur n'existe pas !").queue();
                }
            }
        }
    }
}