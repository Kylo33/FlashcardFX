package com.kyloapps.View;

import com.kyloapps.DisplayableFlashcard;
import com.kyloapps.Model.AnswerChoice;
import com.kyloapps.View.MultipleChoiceFlashcardView;
import com.kyloapps.View.SimpleFlashcardView;
import com.kyloapps.View.TableFlashcardView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FlashcardFactory {
    public static DisplayableFlashcard build(JSONObject card) {
        String type = card.getString("flashcardType");
        String question = card.getString("question");
        String imageURL = card.has("image") ? card.getString("image") : null;
        // Build the AnswerOptions
        JSONArray options = card.getJSONArray("options");
        List<Integer> correctAnswers = JSONArrayTyped(card.getJSONArray("answers"));

        List answerChoices;
        if (!type.equals(TableFlashcardView.typeString)) {
            answerChoices = IntStream.range(0, options.length())
                    .mapToObj(i -> {
                        boolean correct = correctAnswers.contains(i);
                        return new AnswerChoice<>(options.getString(i), correct);
                    }).collect(Collectors.toList());
        } else {
            answerChoices = IntStream.range(0, options.length())
                    .mapToObj(i -> {
                        JSONArray optionsJSONArray = options.getJSONArray(i);
                        boolean correct = correctAnswers.contains(i);
                        String[] strings = new String[optionsJSONArray.length()];
                        for (int j = 0, k = optionsJSONArray.length(); j < k; j++) {
                            strings[j] = optionsJSONArray.getString(j);
                        }
                        return new AnswerChoice<>(strings, correct);
                    }).collect(Collectors.toList());
        }

        // Create the flashcards
        switch (type) {
            case SimpleFlashcardView.typeString:
                return new SimpleFlashcardView(question, answerChoices, imageURL);
            case MultipleChoiceFlashcardView.typeString:
                return new MultipleChoiceFlashcardView(question, answerChoices, imageURL);
            case TableFlashcardView.typeString:
                JSONArray headersJSONArray = card.getJSONArray("headers");
                String[] headers = new String[headersJSONArray.length()];
                for (int i = 0, j = headersJSONArray.length(); i < j; i++) {
                    headers[i] = headersJSONArray.getString(i);
                }
                return new TableFlashcardView(question, answerChoices, headers, imageURL);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private static <T> List<T> JSONArrayTyped(JSONArray answers) {
        List<T> tList = new ArrayList<>();
        for (int i = 0, c = answers.length(); i < c; i++) {
            tList.add((T) answers.get(i));
        }
        return tList;
    }
}
