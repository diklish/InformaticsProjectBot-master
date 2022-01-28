package org.klishin.telegram.informatics.project.telegram.nonCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Обработка сообщения, не являющегося командой (т.е. обычного текста не начинающегося с "/")
 */
public class NonCommand {
    private Logger logger = LoggerFactory.getLogger(NonCommand.class);

    public String nonCommandExecute(Long chatId, String userName, String text) {
        logger.debug(String.format("Пользователь %s. Начата обработка сообщения \"%s\", не являющегося командой",
                userName, text));
        String answer;
        try {
            logger.debug(String.format("Пользователь %s. Пробуем обработать ответ из сообщения \"%s\"",
                    userName, text));

            answer = String.format("Ответ принят");

        } catch (IllegalArgumentException e) {
            logger.debug(String.format("Пользователь %s. Не удалось обработать ответ из сообщения \"%s\". " +
                    "%s", userName, text, e.getMessage()));
            answer = e.getMessage() +
                    "\n\n❗ Не удалось обработать ответ из сообщения";
        } catch (Exception e) {
            logger.debug(String.format("Пользователь %s. Не удалось обработать ответ из сообщения \"%s\". " +
                    "%s. %s", userName, text, e.getClass().getSimpleName(), e.getMessage()));
            answer = "Простите, я не понимаю Вас. Похоже, что Вы ввели сообщение, не соответствующее формату\n\n" +
                    "Возможно, Вам поможет /help";
        }

        logger.debug(String.format("Пользователь %s. Завершена обработка сообщения \"%s\", не являющегося командой",
                userName, text));
        return answer;
    }
}
