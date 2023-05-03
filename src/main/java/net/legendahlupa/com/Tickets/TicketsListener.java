package net.legendahlupa.com.Tickets;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.channel.ChannelManager;

import java.io.*;
import java.util.*;

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
        if (event.getModalId().equals("ingamesubjectmodal")){
            String fileName = "TicketNumber.txt";
            try {
                File file = new File(fileName);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                int number = Integer.parseInt(line);
                String subject = event.getValue("ingamesubject").getAsString();
                String description = event.getValue("ingamedescription").getAsString();
                number++;
                reader.close();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(String.valueOf(number));
                writer.close();
                event.getGuild().getCategoryById(event.getGuild().getCategoriesByName("Открытые жалобы", true).get(0).getId()).createTextChannel("Жалоба номер " + number).queue(textChannel -> {
                    event.reply("Ваша жалоба успешно создана -->" + textChannel.getJumpUrl()).setEphemeral(true).queue();
                    textChannel.getManager().putMemberPermissionOverride(event.getMember().getIdLong(), Collections.singleton(VIEW_CHANNEL), Collections.singleton(ADMINISTRATOR));
                });
            } catch (IOException e) {
                System.out.println("Произошла ошибка при чтении в файла: " + e.getMessage());
            }
        }
        if (event.getModalId().equals("discordsubjectmodal")){
            String fileName = "TicketNumber.txt";
            try {
                File file = new File(fileName);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                int number = Integer.parseInt(line);
                String subject = event.getValue("ingamesubject").getAsString();
                String description = event.getValue("ingamedescription").getAsString();
                number++;
                reader.close();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(String.valueOf(number));
                writer.close();
                event.getGuild().getCategoryById(event.getGuild().getCategoriesByName("Открытые жалобы", true).get(0).getId()).createTextChannel("Жалоба номер " + number).queue(textChannel -> {
                    event.deferReply().setEphemeral(true);
                });
            } catch (IOException e) {
                System.out.println("Произошла ошибка при чтении в файла: " + e.getMessage());
            }
        }
        if (event.getModalId().equals("questionmodal")){
            String fileName = "TicketNumber.txt";
            try {
                File file = new File(fileName);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                int number = Integer.parseInt(line);
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
    }
}
