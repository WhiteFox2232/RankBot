package fr.whitefox.commands;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import fr.whitefox.Configuration;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ConfigCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("config")) {
            if (event.getSubcommandName().equals("bdd")) {
                String bdd = event.getOption("lien").getAsString();
                Configuration config = Configuration.getInstance();

                if (!tryMongoConnexion(bdd)) {
                    event.reply("<:Deny:951975823324364830> La connexion à la bdd est impossible !").queue();
                    return;
                }
                config.setProperty("bdd", bdd);
                event.reply("<:Allow:951975822745563197> La base de donnée a bien été **mise à jour**.").queue();
            }
        }
    }

    public static boolean tryMongoConnexion(String url) {
        try {
            MongoClient mongo = new MongoClient(new MongoClientURI(url));
            mongo.close();
            return true;
        } catch (ExceptionInInitializerError | IllegalArgumentException e) {
            return false;
        }
    }
}