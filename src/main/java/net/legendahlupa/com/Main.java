package net.legendahlupa.com;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.legendahlupa.com.Tickets.TicketsListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JDA jda = JDABuilder.createLight("OTE3OTMyNTA0MDM0NDcxOTY2.GSlmSG.qctfp2PMSAKGbga-OINslFhFd_2M7XIxIChr7g")

                    .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.watching("ReLife"))
                .addEventListeners(new TicketsListener())
                .addEventListeners(new NewsListener())
                .addEventListeners(new CommandListener())
                .build();


        jda.updateCommands().addCommands(
                Commands.slash("guide", "Информация о командах бота"),
                Commands.slash("setupticket", "Создать панель тикетов")
        ).queue();

        String fileName = "settings.txt";

        File file = new File(fileName);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                FileWriter writer = new FileWriter(fileName);
                writer.write("number: 0\nopenid: null\ncloseid: null\n");
                writer.close();
                System.out.println("Значение успешно записано в файл.");
            } catch (IOException e) {
                System.out.println("Произошла ошибка при записи в файл: " + e.getMessage());
            }
        }


    }
}
