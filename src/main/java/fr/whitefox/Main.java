package fr.whitefox;

import net.dv8tion.jda.api.JDA;

public class Main {

    private static JDA jda;
    private static long uptime;

    public static JDA getJDAInstance() {
        return jda;
    }

    public static long getUptime() {
        return uptime;
    }

    public static void main(String[] args) {
        try {
            jda = JDAInstance.createJDAInstance(args[0]);
            uptime = System.currentTimeMillis();
            CommandsManager.deploySlashCommands();
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("[ERROR] Vous devez mettre le token de votre bot Discord en argument !");
            System.out.println("Exemple : java -jar DiscordRankBot.jar VOTRE_TOKEN");
        }
    }
}