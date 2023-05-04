package net.legendahlupa.com.Tickets;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.channel.ChannelManager;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import static net.dv8tion.jda.api.Permission.ADMINISTRATOR;
import static net.dv8tion.jda.api.Permission.VIEW_CHANNEL;

public class TicketsListener extends ListenerAdapter {
    public void onButtonInteraction(ButtonInteractionEvent event) {
        List<MessageEmbed> embeds = event.getMessage().getEmbeds();
        if (event.getButton().getId().equals("subjectGamePlayer")) {
            if (!embeds.isEmpty()) {
                TicketModal ticket = new TicketModal();
                ticket.sendInGameModal(event);
            }
        }
        if (event.getButton().getId().equals("subjectDiscordMember")) {
            if (!embeds.isEmpty()) {
                TicketModal ticket = new TicketModal();
                ticket.sendDiscordModal(event);
            }
        }
        if (event.getButton().getId().equals("question")) {
            if (!embeds.isEmpty()) {
                TicketModal ticket = new TicketModal();
                ticket.sendquestionModal(event);
            }
        }
    }

    public void onModalInteraction(ModalInteractionEvent event) {

        List<Category> categories = Objects.requireNonNull(event.getGuild()).getCategories();
        categories.get(0).getId();
        /**
         *  Жалоба на игрока сервера
         */
        try {
            String fileName = "settings.txt";
            File file = new File(fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            int number = 0;
            while (line != null) {
                if (line.startsWith("number: ")) {
                    String numberStr = line.substring(8);
                    number = Integer.parseInt(numberStr);
                    break;
                }
                line = reader.readLine();
            }
            reader.close();
            if (event.getModalId().equals("ingamesubjectmodal")){
                try {

                    String sender = event.getValue("ingamesender").getAsString();
                    String subject = event.getValue("ingamesubject").getAsString();
                    String description = event.getValue("ingamedescription").getAsString();
                    String url = event.getValue("ingameurl").getAsString();
                    number++;
                    writeNewValue(number);
                    event.getGuild().getCategoryById(event.getGuild().getCategoriesByName("Открытые жалобы", true).get(0).getId()).createTextChannel("Жалоба номер " + number).queue(textChannel -> {
                        event.reply("Ваша жалоба успешно создана -->" + textChannel.getJumpUrl()).setEphemeral(true).queue();
                        textChannel.getManager().putMemberPermissionOverride(event.getMember().getIdLong(), Collections.singleton(VIEW_CHANNEL), Collections.singleton(null)).queue();

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setColor(new Color(255, 212, 60));
                        embedBuilder.setTitle("Отправитель: " + sender + "\n"  + "Нарушитель: " + subject);
                        embedBuilder.setDescription("Описание: " + description + "\n" + "Доказательство: " + url);

                        textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
                    });
                } catch (Exception e) {
                    System.out.println("Произошла ошибка при чтении в файла: " + e.getMessage());
                }
            }
            /**
             * Жалоба на пользователя дискорд
             */
            if (event.getModalId().equals("discordsubjectmodal")){
                try {
                    String subject = event.getValue("discordsubject").getAsString();
                    String description = event.getValue("discorddescription").getAsString();
                    String url  = event.getValue("discordurl").getAsString();
                    number++;
                    reader.close();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(String.valueOf(number));
                    writer.close();
                    event.getGuild().getCategoryById(event.getGuild().getCategoriesByName("Открытые жалобы", true).get(0).getId()).createTextChannel("Жалоба номер " + number).queue(textChannel -> {
                        event.deferReply().setEphemeral(true);
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setColor(new Color(255, 212, 60));
                        embedBuilder.setAuthor(event.getUser().getName(), null, event.getUser().getAvatarUrl());
                        embedBuilder.setTitle("Нарушитель: " + subject);
                        embedBuilder.setDescription("Описание: " + description + "\n" + "Доказательство: " + url);

                        textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
                    });
                } catch (IOException e) {
                    System.out.println("Произошла ошибка при чтении в файла: " + e.getMessage());
                }
            }
            /**
             * Вопрос о сервере или к администрации
             */
            if (event.getModalId().equals("questionmodal")){
                try {
                    String subject = event.getValue("ingamesubject").getAsString();
                    String description = event.getValue("ingamedescription").getAsString();
                    number++;
                    reader.close();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(String.valueOf(number));
                    writer.close();
                    event.getGuild().getCategoryById(event.getGuild().getCategoriesByName("Открытые жалобы", true).get(0).getId()).createTextChannel("Жалоба номер " + number).queue(textChannel -> {
                        event.reply("").setEphemeral(true).queue();
                    });
                } catch (IOException e) {
                    System.out.println("Произошла ошибка при чтении в файла: " + e.getMessage());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }



    }

    public void writeNewValue(int newnumber){
        try {
            // Открываем файл в режиме чтения и записи
            RandomAccessFile file = new RandomAccessFile("settings.txt", "rw");

            // Перемещаем указатель на начало файла
            file.seek(0);

            // Считываем строку с текущим значением number
            String line = file.readLine();
            long position = 0; // Позиция в файле, с которой начинается строка с number

            // Ищем строку, начинающуюся с "number: "
            while (line != null && !line.startsWith("number: ")) {
                position = file.getFilePointer();
                line = file.readLine();
            }


            // Если строка найдена, заменяем значение number на новое значение "50"
            if (line != null) {
                file.seek(position + 8); // Перемещаем указатель на начало числа
                file.writeBytes(String.valueOf(newnumber + "\n")); // Записываем новое значение
            }


            // Закрываем файл
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
