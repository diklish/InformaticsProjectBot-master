package org.klishin.telegram.informatics.project.telegram.commands.service;

import org.klishin.telegram.informatics.project.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Команда "Помощь"
 */
public class HelpCommand extends ServiceCommand {
    private Logger logger = LoggerFactory.getLogger(HelpCommand.class);

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        logger.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Я бот - проект Клишина Кирилла.\n" +
                        "Бот поможет Вам выучить несколько английских слов\n" +
                        "❗*Список команд*\n" +
                        " /allword - Вывод всех слов с переводом;\n" +
                        " /englishword - Вывод всех английских слов;\n" +
                        " /russianword - Вывод всех руских слов;\n" +
                        "/help - помощь\n\n" +
                        "\n");
        logger.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}
