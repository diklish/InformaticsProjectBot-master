package org.klishin.telegram.informatics.project.telegram.commands.service;

import org.klishin.telegram.informatics.project.Utils;
import org.klishin.telegram.informatics.project.telegram.Bot;
import org.klishin.telegram.informatics.project.telegram.enums.OperationEnum;
import org.klishin.telegram.informatics.project.telegram.nonCommand.model.Dictionary;
import org.klishin.telegram.informatics.project.telegram.nonCommand.model.QuestionAnswer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestENCommand extends ServiceCommand {
    private Logger logger = LoggerFactory.getLogger(EnglishWordCommand.class);

    public TestENCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        logger.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));
        sendAnswerTest(absSender, chat.getId(), OperationEnum.TEST_EN, this.getDescription(),
                this.getCommandIdentifier(), userName);
        //Тест
        for (Dictionary dict : Bot.getDictionaries()) {
            String answer1 = randomAnswer(List.of(dict.getRussian()));
            String answer2 = randomAnswer(List.of(dict.getRussian(), answer1));
            List<String> answer = new ArrayList<>();
            answer.add(dict.getRussian());
            answer.add(answer1);
            answer.add(answer2);
            Collections.shuffle(answer);
            QuestionAnswer qa = QuestionAnswer.builder()
                    .wold(dict.getEnglish())
                    .answer(answer)
                    .answerTrue(dict.getRussian())
                    .build();
            List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
            String answerTrue = qa.getAnswerTrue();
            for (String ans : qa.getAnswer()) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(ans);
                inlineKeyboardButton.setCallbackData(answerTrue + ":::" + ans);
                List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
                keyboardButtonsRow.add(inlineKeyboardButton);
                rowList.add(keyboardButtonsRow);
            }
            sendAnswerTest(absSender, chat.getId(), this.getCommandIdentifier(), userName, qa.getWold(), rowList);
        }
        logger.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }

    private String randomAnswer(List<String> stringList) {
        Random random = new Random();
        int index = random.nextInt(Bot.getDictionaries().size());
        String answer = Bot.getDictionaries().get(index).getRussian();
        boolean retry = false;
        for (String str : stringList) {
            if (str.equals(Bot.getDictionaries().get(index).getRussian())) {
                retry = true;
            }
        }
        if (retry) answer = randomAnswer(stringList);
        return answer;
    }
}
