package org.example;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Utils {

	private static Map<Long, Integer> levels = new HashMap<>();
	private static Map<Long, Integer> bonuses = new HashMap<>();

	public static int getLevel(Long chatId) {
		return levels.getOrDefault(chatId, 1);
	}

	public static void setLevel(Long chatId, int level) {
		levels.put(chatId, level);
	}

	public static int getBonus(Long chatId) {
		return bonuses.getOrDefault(chatId, 5);
	}

	public static void addBonus(Long chatId, int adder) {
		bonuses.put(chatId, getBonus(chatId) + adder);
	}

	public static Long getChatId(Update update) {
		if (update.hasMessage()) {
			return update.getMessage().getFrom().getId();
		} else if (update.hasCallbackQuery()) {
			return update.getCallbackQuery().getFrom().getId();
		} else return null;
	}

	public static SendMessage messageSystem(String text) {
		SendMessage message = new SendMessage();

		/*
		The new String(byte[]) constructor without specifying an explicit character
		set will use the default platform encoding,
		which can cause issues with portability between platforms that use different default encodings.
		To fix this bug, you should specify the character set explicitly when converting between bytes and strings.

		We're explicitly specifying the character set to use when converting the byte array to a string,
		and also when creating the new string.
		By using StandardCharsets.UTF_8 instead of the default platform encoding,
		we ensure that the behavior of the application is consistent across different platforms.
		 */
		message.setText(new String(text.getBytes(StandardCharsets.UTF_8),
				StandardCharsets.UTF_8));
		message.setParseMode("markdown");
		return message;
	}

	public static void attachButtons(SendMessage message, Map<String, String> buttons) {
		InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

		List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

		/*
		We iterate over the entrySet() of the buttons map,
		which returns a set of Map.Entry objects that contain both the key
		and the value for each entry in the map.
		This allows us to directly access the value of each entry
		without having to do a Map.get(key) lookup for each key.
		 */
		for (Map.Entry<String, String> entry : buttons.entrySet()) {
			String buttonName = entry.getKey();
			String buttonValue = entry.getValue();

			InlineKeyboardButton button = new InlineKeyboardButton();
			button.setText(new String(buttonName.getBytes(StandardCharsets.UTF_8),
					StandardCharsets.UTF_8));
			button.setCallbackData(buttonValue);
			//Returns an immutable list containing only the specified object. The returned list is serializable.
			keyboard.add(Collections.singletonList(button));
		}

		markup.setKeyboard(keyboard);
		message.setReplyMarkup(markup);
	}

	public static List<String> getRandomVariants(List<String> variants) {
		ArrayList<String> copy = new ArrayList<>(variants);
		Collections.shuffle(copy);
		return copy.subList(0, 3);
	}

	/*
	public static void checkInput(String input) {
		if (input.matches("(add|minus)_\\d{2}")) {
			String[] parts = input.split("_");
			String operator = parts[0];
			int number = Integer.parseInt(parts[1]);
			if (number >= 1 && number <= 99) {
				System.out.println("Input is valid: " + operator + " " + number);
			} else {
				System.out.println("Invalid input: number must be between 01 and 99");
			}
		} else {
			System.out.println("Invalid input: input must start with 'add' or 'minus' and end with a two-digit number");
		}
	}
	 */
	public static void updateBonus(Long chatId, String CallBack) {
		if (CallBack.matches("(add|minus)_\\d{2}")) {
			String[] parts = CallBack.split("_");
			String operator = parts[0]; // get operator
			int number = Integer.parseInt(parts[1]); // get value
			if (number >= 1 && number <= 99) {
				if (operator.equals("minus")) number *= -1;
				addBonus(chatId, number);
			} else {
				System.out.println("Invalid input: number must be between 01 and 99");
			}
		} else {
			System.out.println("Invalid input: input must start with 'add' or 'minus' and end with a two-digit number");
		}
	}
}
