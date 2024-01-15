package com.kyloapps.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "classic", value = ClassicFlashcard.class),
    @JsonSubTypes.Type(name = "multipleChoice", value = MultipleChoiceFlashcard.class),
    @JsonSubTypes.Type(name = "table", value = TableFlashcard.class),
})
public interface Flashcard {
    void accept(Visitor visitor);
}
