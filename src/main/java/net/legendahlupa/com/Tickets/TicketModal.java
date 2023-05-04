package net.legendahlupa.com.Tickets;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class TicketModal {

    public void sendInGameModal(ButtonInteractionEvent event) {
        TextInput IngameSender = TextInput.create("ingamesender", "Ваш ник", TextInputStyle.SHORT)
                .setPlaceholder("Пример: Azirivka")
                .build();
        TextInput IngameSubject = TextInput.create("ingamesubject", "Ник нарушитель", TextInputStyle.SHORT)
                .setPlaceholder("Пример: LegendaHluPa")
                .build();

        TextInput IngameSubjectDescription = TextInput.create("ingamedescription", "Описание нарушения", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Пример: Гриферство")
                .build();

        TextInput IngameSubjectURL = TextInput.create("ingameurl", "Доказательство ", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Пример: https://imgur.com/ | https://www.youtube.com/")
                .build();
        Modal modal = Modal.create("ingamesubjectmodal", "Жалоба на игрока сервера")
                .addComponents(ActionRow.of(IngameSender) ,ActionRow.of(IngameSubject), ActionRow.of(IngameSubjectDescription), ActionRow.of(IngameSubjectURL))
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
        TextInput DiscordSubjectURL = TextInput.create("discordurl", "Доказательство", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Пример: https://www.youtube.com/ или ссылка на сообщение")
                .build();

        Modal modal = Modal.create("discordsubjectmodal", "Жалоба на пользователя дискорд")
                .addComponents(ActionRow.of(DiscordSubject), ActionRow.of(DiscordSubjectDescription), ActionRow.of(DiscordSubjectURL))
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

