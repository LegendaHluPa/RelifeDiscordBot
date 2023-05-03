package net.legendahlupa.com.Messages;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SendMessage {
    public void sendGuide(SlashCommandInteractionEvent event){
        event.getUser().openPrivateChannel().queue(privateChannel -> {
            EmbedBuilder guide1 = new EmbedBuilder();
            guide1.setImage("https://cdn.discordapp.com/attachments/903749953321586699/1098704535331020810/image.png");

            privateChannel.sendMessage("Здравствуйте! Это гайд как пользоваться нашим новостным ботом. Новость создается в 3 очень простых этапа. Все они описаны ниже:\n" +
                    "1) Прописать кодовое слово !news чтобы бот мог понять что вы пишите новость.\n" +
                    "2) Написать заголовок статьи, обрамив его в знак $. Это должно выглядеть примерно так $Заголовок$.  Бот поймет что вы хотите выделить эту часть как заголовок.\n" +
                    "3) Дописать пост и прикрепить картинку(она может быть только одна).\n" +
                    "\n" +
                    "В итоге должно получиться что-то вроде этого:").addEmbeds(guide1.build()).queue();
            if (event.getGuild() != null){
                event.reply("Посмотрите личные сообщения").setEphemeral(true).queue();
            }
            EmbedBuilder guide2 = new EmbedBuilder();
            guide2.setImage("https://cdn.discordapp.com/attachments/903749953321586699/1098704945072590990/image.png");
            privateChannel.sendMessage("После последует рассмотрение вашего поста и в случае его одобрения в канале новостей бот опубликует вашу новость. Это будет выглядеть примерно так:").addEmbeds(guide2.build()).queue();
        });
    }
}
