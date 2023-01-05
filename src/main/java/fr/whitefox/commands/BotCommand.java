package fr.whitefox.commands;

import fr.whitefox.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class BotCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("bot")) {
            EmbedBuilder rankEmbed = new EmbedBuilder();
            rankEmbed.setAuthor(event.getJDA().getSelfUser().getAsTag(), null, event.getJDA().getSelfUser().getAvatarUrl());
            rankEmbed.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
            rankEmbed.setDescription("RankBot est un bot privé permettant de classer et répertorier l'activité de votre serveur Discord.");
            rankEmbed.addField("<:Dev:1060334357690200248> Développeur", "• `WhiteFox#0001`", true);
            rankEmbed.addField("<:Ping:1060337112634896436> Latences", "API » `" + event.getJDA().getGatewayPing() + "ms`", true);
            rankEmbed.addField("<:Uptime:1060337056691277914> Uptime", "• " + getUptime(), true);
            rankEmbed.addField("<:Insights:1060334363033731254> Statistiques", "Serveurs » `" + event.getJDA().getGuilds().size() + "`\n" +
                    "Membres » `" + getAllMembers() + "`", true);
            rankEmbed.addField("<:Update:1060334356629037096> Versions",
                    "Java » `17`\nJDA » `5.0.0-beta.2`", true);
            rankEmbed.setColor(new Color(0x303136));
            event.replyEmbeds(rankEmbed.build()).queue();
        }
    }

    public String getUptime() {
        return "<t:" + Main.getUptime() / 1000L + ":R>";
    }

    public int getAllMembers() {
        int i = 0;
        for(Guild guilds : Main.getJDAInstance().getGuilds()) {
            i += guilds.getMemberCount();
        }
        return i;
    }
}
