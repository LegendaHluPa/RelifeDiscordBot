package net.legendahlupa.com.Tickets;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.EnumSet;
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
            event.getGuild().createCategory("Открытые жалобы").queue(category -> {
                try {
                    // Открываем файл в режиме чтения и записи
                    RandomAccessFile file = new RandomAccessFile("settings.txt", "rw");

                    // Перемещаем указатель на начало файла
                    file.seek(0);

                    // Считываем строку с текущим значением number
                    String line = file.readLine();
                    long position = 0; // Позиция в файле, с которой начинается строка с number

                    // Ищем строку, начинающуюся с "number: "
                    while (line != null && !line.startsWith("openid: ")) {
                        position = file.getFilePointer();
                        line = file.readLine();
                    }

                    // Если строка найдена, заменяем значение number на новое значение "50"
                    if (line != null) {
                        file.seek(position + 8); // Перемещаем указатель на начало числа
                        file.writeBytes(category.getId() + "\n"); // Записываем новое значение
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            });

            event.getGuild().createCategory("Закрытые жалобы").queue(category1 -> {
                try {
                    // Открываем файл в режиме чтения и записи
                    RandomAccessFile file = new RandomAccessFile("settings.txt", "rw");

                    // Перемещаем указатель на начало файла
                    file.seek(0);

                    // Считываем строку с текущим значением number
                    String line = file.readLine();
                    long position = 0; // Позиция в файле, с которой начинается строка с number

                    // Ищем строку, начинающуюся с "number: "
                    while (line != null && !line.startsWith("closeid: ")) {
                        position = file.getFilePointer();
                        line = file.readLine();
                    }

                    // Если строка найдена, заменяем значение number на новое значение "50"
                    if (line != null) {
                        file.seek(position + 7); // Перемещаем указатель на начало числа
                        file.writeBytes("closeid: " + category1.getId()); // Записываем новое значение
                    }

                    // Закрываем файл
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            });
            });
    }


}
