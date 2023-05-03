package net.legendahlupa.com;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        User user = event.getAuthor();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        TextChannel acceptChannel = event.getGuild().getTextChannelById("1098678046690652240");
        if (event.getMessage().getContentRaw().contains("!news")) {
            if (!event.getChannel().getId().equalsIgnoreCase("1098678139623841852")) {
                user.openPrivateChannel().queue(privateChannel -> {
                    privateChannel.sendMessage("Вам нужно написать в канал https://discord.com/channels/737364823175856158/1098678139623841852").queue();
                    event.getMessage().delete().queue();
                });
                return;
            }
            String str = event.getMessage().getContentRaw();
            Pattern pattern = Pattern.compile("\\$(.*?)\\$\\s*(.*)");
            Matcher matcher = pattern.matcher(str);


            if (matcher.find()) {
                String header = matcher.group(1);
                embedBuilder.setTitle(header);
                if (!event.getMessage().getAttachments().isEmpty()) {
                    embedBuilder.setImage(event.getMessage().getAttachments().get(0).getUrl());
                }
                embedBuilder.setAuthor(user.getName(), null, user.getAvatarUrl());
                embedBuilder.setColor(new Color(255, 212, 60));
                embedBuilder.setDescription("```" + str.replaceFirst("\\$[^$]+\\$", "").replaceAll("!news", " ").trim() + "```");
                event.getMessage().delete().queue();
                user.openPrivateChannel().queue(privateChannel -> {
                    privateChannel.sendMessage("Новость успешно отправлена на рассмотрение").queue();
                });

                Button accept = Button.success("accept", "Принять");
                Button decline = Button.danger("decline", "Отклонить");


                acceptChannel.sendMessageEmbeds(embedBuilder.build()).setActionRow(accept, decline).queue();
            } else {
                System.out.println("Заголовок не найден");
            }
        }

    }

    public void onButtonInteraction(ButtonInteractionEvent event) {
        List<MessageEmbed> embeds = event.getMessage().getEmbeds();
        if (event.getButton().getId().equalsIgnoreCase("accept")) {
            if (!embeds.isEmpty()) {
                TextChannel newsChannel = event.getGuild().getTextChannelById("1098676362727931985");
                newsChannel.sendMessageEmbeds(embeds).queue();
                event.getMessage().delete().queue();
                event.reply("Новость успешно одобрена").setEphemeral(true).queue();

            }

        }
        if (event.getButton().getId().equalsIgnoreCase("decline")) {
            event.getMessage().delete().queue();
            event.reply("Новость успешно отклонена").setEphemeral(true).queue();
            TextChannel archive = event.getGuild().getTextChannelById("1098680616968523880");
            archive.sendMessageEmbeds(embeds).queue();
        }
    }

}
