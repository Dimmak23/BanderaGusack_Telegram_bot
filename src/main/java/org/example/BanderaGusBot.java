package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class BanderaGusBot extends TelegramLongPollingBot {

	public BanderaGusBot() {

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
		Long chatID = Utils.getChatId(update);

		if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
			SendMessage message = Utils.messageSystem("Привіт!");
			Utils.attachButtons(message, Map.of(
					"Слава Україні!", "glory_for_ukraine")
			);
			message.setChatId(chatID);
			sendApiMethodAsync(message);
		} else if (update.hasCallbackQuery()) {
			switch (update.getCallbackQuery().getData()) {
				case "glory_for_ukraine": {
					SendMessage message = Utils.messageSystem("Героям слава!");
					Utils.attachButtons(message, Map.of(
							"Слава Нації!", "glory_for_nation")
					);
					message.setChatId(chatID);
					sendApiMethodAsync(message);
					break;
				}
				case "glory_for_nation": {
					SendMessage message = Utils.messageSystem("Смерть ворогам!");
					Utils.attachButtons(message, Map.of(
							"Україна!", "ukraine")
					);
					message.setChatId(chatID);
					sendApiMethodAsync(message);
					break;
				}
				case "ukraine": {
					SendMessage message = Utils.messageSystem("Понад усе!");
					message.setChatId(chatID);
					sendApiMethodAsync(message);
					break;
				}
			}
		}
	}
}
