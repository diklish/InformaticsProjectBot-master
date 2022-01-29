package org.klishin.telegram.informatics.project.telegram;

import lombok.Getter;
import org.klishin.telegram.informatics.project.Utils;
import org.klishin.telegram.informatics.project.telegram.commands.service.*;
import org.klishin.telegram.informatics.project.telegram.nonCommand.NonCommand;
import org.klishin.telegram.informatics.project.telegram.nonCommand.model.Dictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Основной класс бота
 */
public final class Bot extends TelegramLongPollingCommandBot {
    private Logger logger = LoggerFactory.getLogger(Bot.class);

    private final String BOT_NAME;
    private final String BOT_TOKEN;

    private final NonCommand nonCommand;

    /**
     * Набор слов
     */
    @Getter
    private static List<Dictionary> dictionaries;

    public Bot(String botName, String botToken) {
        super();
        logger.debug("Конструктор суперкласса отработал");
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        logger.debug("Имя и токен присвоены");

        this.nonCommand = new NonCommand();
        logger.debug("Класс обработки сообщения, не являющегося командой, создан");

        register(new StartCommand("start", "Старт"));
        logger.debug("Команда start создана");

        register(new AllWordCommand("allword", "Вывод всех слов с переводом"));
        logger.debug("Команда allword создана");

        register(new EnglishWordCommand("englishword", "Вывод всех английских слов"));
        logger.debug("Команда englishword создана");

        register(new RussianWordCommand("russianword", "Вывод всех русских"));
        logger.debug("Команда russianword создана");

        register(new TestCommand("test", "Старт теста"));
        logger.debug("Команда test создана");

        register(new HelpCommand("help", "Помощь"));
        logger.debug("Команда help создана");

        setDictionary();
        logger.debug("Словарь заполнен. Количество слов: " + dictionaries.size());

        logger.info("Бот создан!");

    }

    /**
     * Заполняем словарь
     */
    private void setDictionary() {
        dictionaries = new ArrayList<>();
        dictionaries.add(Dictionary.builder()
                .english("table")
                .russian("стол")
                .build());
        dictionaries.add(Dictionary.builder()
                .english("have")
                .russian("иметь")
                .build());
        dictionaries.add(Dictionary.builder()
                .english("word")
                .russian("слово")
                .build());
        dictionaries.add(Dictionary.builder()
                .english("other")
                .russian("другой")
                .build());
        dictionaries.add(Dictionary.builder()
                .english("do")
                .russian("делать")
                .build());
        dictionaries.add(Dictionary.builder()
                .english("small")
                .russian("маленький")
                .build());
        dictionaries.add(Dictionary.builder()
                .english("home")
                .russian("дом")
                .build());
        dictionaries.add(Dictionary.builder()
                .english("ask")
                .russian("спрашивать")
                .build());
        dictionaries.add(Dictionary.builder()
                .english("again")
                .russian("снова")
                .build());
        dictionaries.add(Dictionary.builder()
                .english("build")
                .russian("строить")
                .build());
        dictionaries.add(Dictionary.builder()
                .english("milk")
                .russian("молоко")
                .build());
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    /**
     * Ответ на запрос, не являющийся командой
     * т.е. обработка сообщения не начинающегося с '/'
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        logger.debug("Начало обработки processNonCommandUpdate");

        if (!update.hasMessage()) {
            logger.error("У Update нет тела!");
            throw new IllegalStateException("У Update нет тела!");
        }

        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = Utils.getUserName(msg);

        String answer = nonCommand.nonCommandExecute(chatId, userName, msg.getText());
        setAnswer(chatId, userName, answer);
    }

    /**
     * Отправка ответа
     *
     * @param chatId   id чата
     * @param userName имя пользователя
     * @param text     текст ответа
     */
    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            logger.error(String.format("Ошибка %s. Сообщение, не являющееся командой. Пользователь: %s", e.getMessage(),
                    userName));
            e.printStackTrace();
        }
    }

}
