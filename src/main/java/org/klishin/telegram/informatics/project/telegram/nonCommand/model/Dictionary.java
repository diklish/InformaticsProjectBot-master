package org.klishin.telegram.informatics.project.telegram.nonCommand.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Словарь
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dictionary {

    /**
     * Написание слова на английском языке
     */
    private String english;

    /**
     * Написание слова на русском языке
     */
    private String russian;

    @Override
    public String toString() {
        return '\n'+english + ' ' + russian;
    }
}
