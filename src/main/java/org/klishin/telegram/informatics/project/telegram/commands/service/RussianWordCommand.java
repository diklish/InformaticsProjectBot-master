package org.klishin.telegram.informatics.project.telegram.commands.service;

import org.klishin.telegram.informatics.project.Utils;
import org.klishin.telegram.informatics.project.telegram.enums.OperationEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class RussianWordCommand extends ServiceCommand {
    private Logger logger = LoggerFactory.getLogger(EnglishWordCommand.class);

    public RussianWordCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        logger.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));
        sendAnswerTest(absSender, chat.getId(), OperationEnum.RUSSIAN_WORD, this.getDescription(),
                this.getCommandIdentifier(), userName);
        logger.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}
