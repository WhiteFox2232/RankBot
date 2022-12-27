package fr.whitefox;

import net.dv8tion.jda.api.JDA;

public class Main {

    private static JDA jda;

    public static JDA getJDAInstance() {
        return jda;
    }

    public static void main(String[] args) {
        try {
            jda = JDAInstance.createJDAInstance(args[0]);
            CommandsManager.deploySlashCommands();
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Vous devez mettre le token de votre bot Discord en argument !");
            System.out.println("Exemple : java -jar DiscordRankBot.jar VOTRE_TOKEN");
        }
    }
}