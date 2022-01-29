package org.klishin.telegram.informatics.project.telegram.operations;

import org.klishin.telegram.informatics.project.telegram.Bot;
import org.klishin.telegram.informatics.project.telegram.enums.OperationEnum;
import org.klishin.telegram.informatics.project.telegram.nonCommand.model.Dictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

abstract class OperationCommand extends BotCommand {
    private Logger logger = LoggerFactory.getLogger(OperationCommand.class);

    public OperationCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    /**
     * Отправка ответа пользователю
     */
    void sendAnswer(AbsSender absSender, Long chatId, OperationEnum operations, String description,
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

    private String createAnswer(OperationEnum operations) {
        String msg="";

        switch (operations) {
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
            case TEST:
                break;
        }

        return msg;
    }

    /**
     * Отправка пользователю сообщения об ошибке
     */
    private void sendError(AbsSender absSender, Long chatId, String commandName, String userName) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), "Похоже, я сломался. Попробуйте позже"));
        } catch (TelegramApiException e) {
            logger.error(String.format("Ошибка %s. Команда %s. Пользователь: %s", e.getMessage(), commandName, userName));
            e.printStackTrace();
        }
    }
}
