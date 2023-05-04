package net.legendahlupa.com.Tickets;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class SetupTicket {

    public void setup(SlashCommandInteractionEvent event) {
        event.reply("Успешно").setEphemeral(true).queue();

        Objects.requireNonNull(event.getGuild()).createTextChannel("Вопросы и Жалобы").queue(textChannel -> {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(new Color(255, 212, 60));
            embedBuilder.setDescription("Это страница обратной связи ReLifeMC. \n" +
                    "\n" +
                    "Тут вы можете пожаловаться на неподобающее поведение игроков и получить помощь в решении проблемы, возникшей во время игры на сервере. \n" +
                    "\n" +
                    "Просто выберите категорию вашего обращения ниже и опишите вашу проблему в новосозданном канале-тикете");

            Button subjectGamePlayer = Button.primary("subjectGamePlayer", "Жалоба на игрока сервера");
            Button subjectDiscordPlayer = Button.primary("subjectDiscordMember", "Жалоба на пользователя дискорд");
            Button question = Button.primary("question", "Вопрос");
            textChannel.sendMessageEmbeds(embedBuilder.build()).setActionRow(subjectGamePlayer, subjectDiscordPlayer, question).queue();
            event.getGuild().createCategory("Открытые жалобы").queue();



        });
    }


}
