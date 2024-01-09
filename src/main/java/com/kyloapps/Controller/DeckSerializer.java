package com.kyloapps.Controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.kyloapps.DisplayableFlashcard;
import com.kyloapps.Model.AnswerChoice;
import com.kyloapps.Model.Deck;
import com.kyloapps.View.TableFlashcardView;

import java.io.IOException;
import java.util.stream.IntStream;

public class DeckSerializer extends StdSerializer<Deck> {

    public DeckSerializer() {
        this(null);
    }

    public DeckSerializer(Class<Deck> t) {
        super(t);
    }

    @Override
    public void serialize(Deck deck, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("title", deck.getTitle());
        jsonGenerator.writeStringField("description", deck.getDescription());
        jsonGenerator.writeArrayFieldStart("cards");
        for (DisplayableFlashcard flashcard: deck.getFlashcards()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("question", flashcard.getQuestion());
            jsonGenerator.writeStringField("flashcardType", flashcard.getTypeString());


            addAnswers(jsonGenerator, flashcard);
            addOptions(jsonGenerator, flashcard);

            if (flashcard instanceof TableFlashcardView) {
                addHeaders(jsonGenerator, flashcard);
            }

            if (flashcard.getImageURL() != null) {
                jsonGenerator.writeStringField("image", flashcard.getImageURL());
            }
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }

    private static void addHeaders(JsonGenerator jsonGenerator, DisplayableFlashcard flashcard) throws IOException {
        jsonGenerator.writeArrayFieldStart("headers");
        for (String header : ((TableFlashcardView) flashcard).getHeaders()) {
            jsonGenerator.writeString(header);
        }
        jsonGenerator.writeEndArray();
    }

    private static void addOptions(JsonGenerator jsonGenerator, DisplayableFlashcard flashcard) throws IOException {
        jsonGenerator.writeArrayFieldStart("options");
        flashcard.getOptions().stream().map((answerChoice) ->
                ((AnswerChoice) answerChoice).getContent()
        ).forEach((answerChoiceContent) -> {
            if (flashcard instanceof TableFlashcardView) {
                try {
                    jsonGenerator.writeStartArray();
                    for (String cell : ((String[]) answerChoiceContent)) {
                        jsonGenerator.writeString(cell);
                    }
                    jsonGenerator.writeEndArray();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    jsonGenerator.writeString((String) answerChoiceContent);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        jsonGenerator.writeEndArray();
    }

    private static void addAnswers(JsonGenerator jsonGenerator, DisplayableFlashcard flashcard) throws IOException {
        jsonGenerator.writeArrayFieldStart("answers");
        IntStream.range(0, flashcard.getOptions().size())
                .filter((index) -> ((AnswerChoice) flashcard.getOptions().get(index)).isCorrect())
                .forEach((number) -> {
                    try {
                        jsonGenerator.writeNumber(number);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        jsonGenerator.writeEndArray();
    }
}
