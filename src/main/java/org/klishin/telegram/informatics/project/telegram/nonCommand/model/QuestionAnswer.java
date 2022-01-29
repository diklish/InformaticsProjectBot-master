package org.klishin.telegram.informatics.project.telegram.nonCommand.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Вопрос с ответами
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswer {
    private String wold;
    private List<String> answer;
    private String answerTrue;
}

