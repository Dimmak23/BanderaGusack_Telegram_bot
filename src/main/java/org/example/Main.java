package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main extends TelegramLongPollingBot {

	public static void main(String[] args) throws TelegramApiException {
		TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
		api.registerBot(new Main());
	}

	@Override
	public String getBotUsername() {
		return "BanderaGus_DK_bot";
	}

	@Override
	public String getBotToken() {
		return "6026190816:AAFGLYIKXsQSKyOVNe5iZahDHrma6gJSYLQ";
	}

	@Override
	public void onUpdateReceived(Update update) {
		Long chatID = getChatId(update);

		SendMessage message = messageSystem("*Hello*, _Dmytro_!");
		message.setChatId(chatID);
		sendApiMethodAsync(message);
	}

	public Long getChatId(Update update) {
		if (update.hasMessage()) {
			return update.getMessage().getFrom().getId();
		}
		if (update.hasCallbackQuery()) {
			return update.getCallbackQuery().getFrom().getId();
		} else return null;
	}

	public SendMessage messageSystem(String text) {
		SendMessage message = new SendMessage();
		message.setText(text);
		message.setParseMode("markdown");
		return message;
	}
}
