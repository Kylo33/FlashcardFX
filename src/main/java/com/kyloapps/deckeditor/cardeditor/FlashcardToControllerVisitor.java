package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.deckeditor.cardeditor.forms.CardController;
import com.kyloapps.deckeditor.cardeditor.forms.classic.ClassicMvciController;
import com.kyloapps.deckeditor.cardeditor.forms.multiplechoice.MultipleChoiceMvciController;
import com.kyloapps.deckeditor.cardeditor.forms.table.TableMvciController;
import com.kyloapps.domain.ClassicFlashcard;
import com.kyloapps.domain.FlashcardVisitor;
import com.kyloapps.domain.MultipleChoiceFlashcard;
import com.kyloapps.domain.TableFlashcard;

public class FlashcardToControllerVisitor implements FlashcardVisitor<CardController<?>> {
    @Override
    public CardController<?> visit(ClassicFlashcard flashcard) {
        ClassicMvciController result = new ClassicMvciController();
        result.loadCard(flashcard);
        return result;
    }

    @Override
    public CardController<?> visit(MultipleChoiceFlashcard flashcard) {
        MultipleChoiceMvciController result = new MultipleChoiceMvciController();
        result.loadCard(flashcard);
        return result;
    }

    @Override
    public CardController<?> visit(TableFlashcard flashcard) {
        TableMvciController result = new TableMvciController();
        result.loadCard(flashcard);
        return result;
    }
}
