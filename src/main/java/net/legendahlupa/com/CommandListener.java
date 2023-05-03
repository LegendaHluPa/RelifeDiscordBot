package net.legendahlupa.com;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.legendahlupa.com.Tickets.SetupTicket;
import net.legendahlupa.com.Messages.SendMessage;

public class CommandListener extends ListenerAdapter {
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("guide")) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.sendGuide(event);
        }
        if (event.getMember().hasPermission(Permission.ADMINISTRATOR)){
            if (event.getName().equals("setupticket")) {
                SetupTicket setupTicket = new SetupTicket();
                setupTicket.setup(event);
            }
        } else {
            event.reply("У вас недостаточно прав").setEphemeral(true).queue();
        }
    }
}
