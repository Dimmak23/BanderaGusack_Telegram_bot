package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.Arrays;
import java.util.List;
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

			sendGif("level-1", chatID);
			SendMessage message = Utils.messageSystem(
					"Ґа-ґа-ґа!\n" +
							"Вітаємо у боті біолабораторії «Батько наш Бандера».\n" +
							"\n" +
							"Ти отримуєш гусака №71\n" +
							"\n" +
							"Цей бот ми створили для того, щоб твій гусак прокачався з рівня звичайної свійської\n" +
							"худоби до рівня біологічної зброї, здатної нищити ворога.\n" +
							"\n" +
							"Щоб звичайний гусак перетворився на бандерогусака, тобі необхідно:\n" +
							"✔️виконувати завдання\n" +
							"✔️переходити на наступні рівні\n" +
							"✔️заробити достатню кількість монет, щоб придбати Джавеліну і зробити свєрхтра-та-та.\n" +
							"\n" +
							"*Гусак звичайний.* Стартовий рівень.\n" +
							"Бонус: 5 монет.\n" +
							"Обери завдання, щоб перейти на наступний рівень"
			);
			message.setChatId(chatID);
			List<String> buttons = Arrays.asList(
					"Сплести маскувальну сітку (+15 монет)",
					"Зібрати кошти патріотичними піснями (+15 монет)",
					"Вступити в Міністерство Мемів України (+15 монет)",
					"Запустити волонтерську акцію (+15 монет)",
					"Вступити до лав тероборони (+15 монет)"
			);
			buttons = Utils.getRandomVariants(buttons);
			Utils.attachButtons(message, Map.of(
					buttons.get(0), "level_1_task",
					buttons.get(1), "level_1_task",
					buttons.get(2), "level_1_task"
					)
			);
			sendApiMethodAsync(message);

		} else if (update.hasCallbackQuery()) {
			switch (update.getCallbackQuery().getData()) {
				case "level_1_task": {
					sendGif("level-2", chatID);
					SendMessage message = Utils.messageSystem(
							"*Вітаємо на другому рівні! Твій гусак - біогусак.*\n" +
									"Баланс: 20 монет.\n" +
									"Обери завдання, щоб перейти на наступний рівень"
					);
					List<String> buttons = Arrays.asList(
							"Зібрати комарів для нової біологічної зброї (+15 монет)",
							"Пройти курс молодого бійця (+15 монет)",
							"Задонатити на ЗСУ (+15 монет)",
							"Збити дрона банкою огірків (+15 монет)",
							"Зробити запаси коктейлів Молотова (+15 монет)"
					);
					buttons = Utils.getRandomVariants(buttons);
					Utils.attachButtons(message, Map.of(
							buttons.get(0), "level_2_task",
							buttons.get(1), "level_2_task",
							buttons.get(2), "level_2_task"
							)
					);
					message.setChatId(chatID);
					sendApiMethodAsync(message);
					break;
				}
				case "level_2_task": {
					sendGif("level-3", chatID);
					SendMessage message = Utils.messageSystem(
							"*Вітаємо на третьому рівні! Твій гусак - бандеростажер.*\n" +
									"Баланс: 35 монет.\n" +
									"Обери завдання, щоб перейти на наступний рівень"
					);
					List<String> buttons = Arrays.asList(
							"Злітати на тестовий рейд по чотирьох позиціях (+15 монет)",
							"Відвезти гуманітарку на передок (+15 монет)",
							"Знайти зрадника та здати в СБУ (+15 монет)",
							"Навести арту на орків (+15 монет)",
							"Притягнути танк трактором (+15 монет)"
					);
					buttons = Utils.getRandomVariants(buttons);
					Utils.attachButtons(message, Map.of(
							buttons.get(0), "level_3_task",
							buttons.get(1), "level_3_task",
							buttons.get(2), "level_3_task"
							)
					);
					message.setChatId(chatID);
					sendApiMethodAsync(message);
					break;
				}
				case "level_3_task": {
					sendGif("level-4", chatID);
					SendMessage message = Utils.messageSystem(
							"*Вітаємо на останньому рівні!" +
									"Твій гусак - готова біологічна зброя - бандерогусак.*\n" +
									"Баланс: 50 монет.\n" +
									"Тепер ти можеш придбати Джавелін і глушити чмонь"
					);
					Utils.attachButtons(message, Map.of(
							"Купити Джавелін (50 монет)", "level_4_task"
							)
					);
					message.setChatId(chatID);
					sendApiMethodAsync(message);
					break;
				}
				case "level_4_task": {
					sendGif("final", chatID);
					SendMessage message = Utils.messageSystem(
							"*Джавелін твій. Повний вперед!*"
					);
					message.setChatId(chatID);
					sendApiMethodAsync(message);
					break;
				}
			}
		}
	}

	// This need to be as a method of a class BanderaGusBot that extends TelegramLongPollingBot
	public void sendGif(String name, Long chatId) {
		SendAnimation animation = new SendAnimation();

		InputFile file = new InputFile();
		file.setMedia(new File("assets/gifs/" + name + ".gif")); //java.io.File

		animation.setAnimation(file);
		animation.setChatId(chatId);

		executeAsync(animation);
	}
}
