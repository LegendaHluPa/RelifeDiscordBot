package net.legendahlupa.com.Tickets;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class TicketModal {

    public void sendInGameModal(ButtonInteractionEvent event) {
        TextInput IngameSubject = TextInput.create("ingamesubject", "Ник нарушитель", TextInputStyle.SHORT)
                .setPlaceholder("Пример: LegendaHluPa")
                .build();

        TextInput IngameSubjectDescription = TextInput.create("ingamedescription", "Описание нарушения", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Пример: Гриферство")
                .build();

        Modal modal = Modal.create("ingamesubjectmodal", "Жалоба на игрока сервера")
                .addComponents(ActionRow.of(IngameSubject), ActionRow.of(IngameSubjectDescription))
                .build();
        event.replyModal(modal).queue();

    }

    public void sendDiscordModal(ButtonInteractionEvent event) {
        TextInput DiscordSubject = TextInput.create("discordsubject", "Ник нарушитель", TextInputStyle.SHORT)
                .setPlaceholder("Пример: Хлюпа#9766")
                .build();

        TextInput DiscordSubjectDescription = TextInput.create("discorddescription", "Описание нарушения", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Пример: Оскобительный ник")
                .build();

        Modal modal = Modal.create("discordsubjectmodal", "Жалоба на пользователя дискорд")
                .addComponents(ActionRow.of(DiscordSubject), ActionRow.of(DiscordSubjectDescription))
                .build();
        event.replyModal(modal).queue();
    }

    public void sendquestionModal(ButtonInteractionEvent event) {
        TextInput IngameSubjectDescription = TextInput.create("questiondescription", "Опишите ваш вопрос", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Пример: Как купить здание")
                .build();

        Modal modal = Modal.create("questionmodal", "Вопрос о сервере")
                .addComponents(ActionRow.of(IngameSubjectDescription))
                .build();
        event.replyModal(modal).queue();
    }

}

