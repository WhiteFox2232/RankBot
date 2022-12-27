package fr.whitefox.commands;

import fr.whitefox.db.MongoDB;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class RankCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("rank")) {
            if (event.getSubcommandName().equals("user")) {
                long user = event.getOption("user").getAsLong();

                if (!(MongoDB.isRegister(user))) {
                    String msg;
                    if (user == event.getUser().getIdLong()) {
                        msg = "<:Deny:951975823324364830> Vous n'êtes pas encore **classé**.";
                    } else if (event.getOption("user").getAsUser().isBot()) {
                        msg = "<:Deny:951975823324364830> Le bot `" + event.getOption("user").getAsUser().getAsTag() + "` ne peut pas être **classé**. ";
                    } else {
                        msg = "<:Deny:951975823324364830> Le membre `" + event.getOption("user").getAsUser().getAsTag() + "` n'est pas encore **classé**. ";
                    }
                    EmbedBuilder noRankEmbed = new EmbedBuilder();
                    noRankEmbed.setDescription(msg);
                    noRankEmbed.setColor(new Color(0x303136));
                    event.replyEmbeds(noRankEmbed.build()).queue();
                    return;
                }

                calculateLevel(MongoDB.getXp(user), MongoDB.getLevel(user), user);

                int level = MongoDB.getLevel(user);
                int xp = MongoDB.getXp(user);
                int rank = MongoDB.getRank(user);
                String avancement = getAdvancement(xp, xpNeeded(level));

                EmbedBuilder rankEmbed = new EmbedBuilder();
                rankEmbed.setAuthor(event.getOption("user").getAsUser().getAsTag(), null, event.getOption("user").getAsUser().getAvatarUrl());
                rankEmbed.addField(":chart_with_upwards_trend: Niveau `" + level + "`", "** **", true);
                rankEmbed.addField(":hash: Position `#" + rank + "`", "** **", true);
                rankEmbed.addField("** **", avancement, false);
                rankEmbed.setColor(new Color(0x303136));
                event.replyEmbeds(rankEmbed.build()).queue();
            }
        }

        if (event.getSubcommandName().equals("top")) {
            EmbedBuilder listEmbed = new EmbedBuilder();
            listEmbed.setAuthor(event.getGuild().getName(), null, event.getGuild().getIconUrl());
            listEmbed.setDescription(MongoDB.getRankList());
            listEmbed.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
            listEmbed.setColor(new Color(0x303136));
            event.replyEmbeds(listEmbed.build()).queue();
        }
    }

    public void calculateLevel(int xp, int actualLevel, long userId) {

        int RequiredXp = (actualLevel ^ 2) + (50 * actualLevel) + 100;
        int newLevel = actualLevel;

        while (RequiredXp < xp) {
            xp -= RequiredXp;
            newLevel++;
            RequiredXp = (newLevel ^ 2) + (50 * newLevel) + 100;
        }

        MongoDB.replaceXp(userId, xp);
        MongoDB.replaceLevel(userId, newLevel);
    }

    public int xpNeeded(int actualLevel) {
        return (actualLevel ^ 2) + (50 * actualLevel) + 100;
    }

    public String getAdvancement(float xp, float xpNeeded) {
        String emoteYes = "<:green:1056351366714499094>";
        String emoteNo = "<:white:1056363496612307004>";
        float xpPercentage = (xp / xpNeeded) * 100;
        StringBuilder advancement = new StringBuilder("**" + Math.toIntExact((long) xp) + "** ");

        int progress = (int) (xpPercentage / 10);

        for (int i = 0; i < progress; i++) {
            if (i == 0) {
                advancement.append("<:green_left:1056353912090787860>");
            } else if (i == 9) {
                advancement.append("<:green_right:1056357356595847179>");
            } else {
                advancement.append(emoteYes);
            }
        }
        for (int i = progress; i < 10; i++) {
            if (progress == 0 && i == progress) {
                advancement.append("<:white_left:1056363493328179291>");
            } else if (i == 9) {
                advancement.append("<:white_right:1056363495031046164>");
            } else {
                advancement.append(emoteNo);
            }
        }

        advancement.append(" **").append(Math.toIntExact((long) xpNeeded)).append("**");

        return advancement.toString();
    }
}
