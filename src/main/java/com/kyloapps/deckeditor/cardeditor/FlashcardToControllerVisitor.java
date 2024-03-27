package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.deckeditor.cardeditor.forms.CardController;
import com.kyloapps.deckeditor.cardeditor.forms.multiplechoice.MultipleChoiceMvciController;
import com.kyloapps.domain.ClassicFlashcard;
import com.kyloapps.domain.FlashcardVisitor;
import com.kyloapps.domain.MultipleChoiceFlashcard;
import com.kyloapps.domain.TableFlashcard;

public class FlashcardToControllerVisitor implements FlashcardVisitor<CardController<?>> {
    @Override
    public CardController<?> visit(ClassicFlashcard flashcard) {
        return null;
    }

    @Override
    public CardController<?> visit(MultipleChoiceFlashcard flashcard) {
        MultipleChoiceMvciController result = new MultipleChoiceMvciController();
        result.loadCard(flashcard);
        return result;
    }

    @Override
    public CardController<?> visit(TableFlashcard flashcard) {
        return null;
    }
}
