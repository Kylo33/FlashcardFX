package com.kyloapps.View;

import com.kyloapps.DisplayableFlashcard;

public class CardOptionsFactory {
    public static CardOptions build(DisplayableFlashcard flashcard) {
        CardOptions cardOptions = new CardOptions(flashcard.getTypeString());
        cardOptions.getCreator().fillFromFlashcard(flashcard);
        return cardOptions;
    }
}
