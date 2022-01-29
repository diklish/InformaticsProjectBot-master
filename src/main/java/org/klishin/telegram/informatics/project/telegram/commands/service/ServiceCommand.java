package org.klishin.telegram.informatics.project.telegram.commands.service;

import lombok.NonNull;
import org.klishin.telegram.informatics.project.telegram.Bot;
import org.klishin.telegram.informatics.project.telegram.enums.OperationEnum;
import org.klishin.telegram.informatics.project.telegram.nonCommand.model.Dictionary;
import org.klishin.telegram.informatics.project.telegram.nonCommand.model.QuestionAnswer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

abstract class ServiceCommand extends BotCommand {
    private Logger logger = LoggerFactory.getLogger(ServiceCommand.class);

    ServiceCommand(String identifier, String description) {
        super(identifier, description);
    }

    /**
     * Отправка ответа пользователю
     */
    void sendAnswerTest(AbsSender absSender, Long chatId, OperationEnum operations, String description,
                        String commandName, String userName) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText(createAnswer(operations));
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            logger.error(String.format("Ошибка %s. Команда %s. Пользователь: %s", e.getMessage(), commandName, userName));
            e.printStackTrace();
        }
    }

    /**
     * Отправка ответа пользователем
     */
    void sendAnswerTest(AbsSender absSender, Long chatId, String commandName, String userName, String  wold, List<List<InlineKeyboardButton>> rowList) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(wold);
        InlineKeyboardMarkup replyMarkup=new InlineKeyboardMarkup();
        replyMarkup.setKeyboard(rowList);
        message.setReplyMarkup(replyMarkup);
        message.enableMarkdown(true);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            logger.error(String.format("Ошибка %s. Команда %s. Пользователь: %s", e.getMessage(), commandName, userName));
            e.printStackTrace();
        }
    }

    private String createAnswer(OperationEnum operations) {
        String msg="";

        switch (operations) {
            case START:
                msg = "Давайте начнём! Если Вам нужна помощь, нажмите /help";
                break;
            case HELP:
                msg = "Я бот - проект Клишина Кирилла.\n" +
                        "Бот поможет Вам выучить несколько английских слов\n" +
                        "❗*Список команд*\n" +
                        " /allword - Вывод всех слов с переводом;\n" +
                        " /englishword - Вывод всех английских слов;\n" +
                        " /russianword - Вывод всех руских слов;\n" +
                        " /test - Старт теста;\n" +
                        "/help - помощь\n\n" +
                        "\n";
                break;
            case ALL_WORD:
                msg = Bot.getDictionaries().toString();
                break;
            case ENGLISH_WORD:
                for (Dictionary dictionary:Bot.getDictionaries()) {
                    msg+=dictionary.getEnglish()+'\n';
                }
                break;
            case RUSSIAN_WORD:
                for (Dictionary dictionary:Bot.getDictionaries()) {
                    msg+=dictionary.getRussian()+"\n";
                }
                break;
            case TEST:
                msg = "Старт теста!";
                break;
        }

        return msg;
    }
}
