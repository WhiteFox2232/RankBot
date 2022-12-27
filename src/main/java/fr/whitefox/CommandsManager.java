package fr.whitefox;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public class CommandsManager {

    private static final JDA jda = Main.getJDAInstance();

    public static void deploySlashCommands() {
        jda.updateCommands().addCommands(
                Commands.slash("rank", "Obtenez le rang d'un membre")
                        .addSubcommands(
                                new SubcommandData("top", "Obtenir le classement des levels"),
                                new SubcommandData("user", "Obtenir le niveau d'un utilisateur")
                                        .addOption(OptionType.USER, "user", "De quel utilisateur souhaitez-vous savoir le rank ?", true)
                        ),
                Commands.slash("boost", "Gérez le boost d'xp du serveur")
                        .addSubcommands(
                                new SubcommandData("set", "Définissez le multiplicateur d'xp du serveur")
                                        .addOption(OptionType.INTEGER, "multiplicateur", "Par combien souhaitez-vous multiplier l'xp acquis ?", true),
                                new SubcommandData("get", "Obtenez le boost actuel du serveur")
                        )
        ).queue();
    }
}
