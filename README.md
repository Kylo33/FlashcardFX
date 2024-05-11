# FlashcardFX
## By Renn Gilbert

#### Video Demo:  [Video on YouTube](https://www.youtube.com/watch?v=i39mGI96HUg)
#### Description:
A flashcard app built with JavaFX for Harvard's CS50x -- "Introduction to Computer Science".

![Demo Image](https://github.com/Kylo33/FlashcardFX/assets/56988649/fda59ea6-35f4-469c-a5b3-c8b3f7602408)


##### Features:

+ 🖼️ Built-in support for images
+ ⭐ Several types of flashcards:
  + Classic flashcards
  + Multiple choice flashcards
  + Table-based flashcards
+ ⌨️ Easily scriptable with JSON
+ 📝 Built-in deck editor
+ 🌗 Several light-mode and dark-mode themes

#### Installation:

Download the latest release (on the right). It is a 'fat jar' -- the dependencies are packaged with the program. Simply run the jar on your computer, and you are good to go!

##### Quick Start:

1. Select a location to store your decks in the **Settings** page.
2. Create a new deck on the **Deck Editor** page.
3. Head to the home page to begin practicing!!

#### JSON Reference:

```json
{
  "title" : "Deck Title",
  "description" : "Deck Description",
  "flashcards" : [ {
    "type" : "classic",
    "question" : "Classic Flashcard Question",
    "answer" : "Classic Flashcard Answer",
    "imageUrl" : "Classic Flashcard Image URL"
  }, {
    "type" : "multipleChoice",
    "question" : "Multiple Choice Question",
    "imageUrl" : "Multiple Choice Image URL",
    "options" : [ {
      "correct" : true,
      "content" : "Multiple Choice Answer 1"
    }, {
      "correct" : false,
      "content" : "Multiple Choice Answer 2"
    } ]
  }, {
    "type" : "table",
    "question" : "Table Question",
    "imageUrl" : "Table Image URL",
    "headers" : [ "Table Header Column 1", "Table Header Column 2" ],
    "options" : [ {
      "correct" : false,
      "content" : [ "Table Header Column 1 Row 1", "Table Header Column 2 Row 1" ]
    }, {
      "correct" : true,
      "content" : [ "Table Header Column 1 Row 2", "Table Header Column 2 Row 2" ]
    } ]
  } ]
}
```

#### Screenshots:

##### Deck Editor

![Deck Editor](https://github.com/Kylo33/FlashcardFX/assets/56988649/74794a19-82c4-417f-8364-488035ae1dc6)

##### Dark Theme

![Nord Dark Color Scheme](https://github.com/Kylo33/FlashcardFX/assets/56988649/c3e3068c-de33-461c-ab33-885ca0c30d62)

##### Home Page

![Home Page](https://github.com/Kylo33/FlashcardFX/assets/56988649/0a3a59ba-66f0-4fdf-a558-4af747bad9bb)

#### Building from Source:

First, clone the repository:

```
git clone https://github.com/Kylo33/FlashcardFX.git
cd FlashcardFX
```

Then, use **Maven** to build the app:

```
mvn package
```

To use the app, head to the `target` directory, and run the `jar` file (`java -jar flashcard-fx-{version}.jar` or `chmod +x flashcard-fx-{version}.jar` and open it using your file manager).

#### Reflection

By far the most difficult part about building this project was creating the **Deck Editor** page. It was incredibly difficult to manage the 'dirty' states of so many modules and components, and the library I used ([DirtyFX](https://github.com/thomasnield/DirtyFX)) is unmaintained (I found some problems myself, but the maintainer is inactive & I don't want to support the library myself, as it is written in Kotlin). So, I ended up making some helper classes of my own (`DeepDirtyList` and `DeepDirtyProperty`) which helped tremendously.

In addition, I restarted this project several times in an attempt to clean up my code. It still isn't perfect, but I am proud of the state it is in. If you look back at some of the older commits on this branch, I pretty much deleted everything after completing the project once. With my real-life time commitments, this was a difficult decision, and I really started to run out of motivation at some points. I am glad that I persevered, but I am unsure if the time I spent rebuilding the project was worth it.

This project taught me a lot about object-oriented programming and designing GUI apps that I am extremely glad to have pursued. I chose to use JavaFX after a brief introduction in the Univeristy of Helsinki's Java Programming courses, but building a multi-screen app was much more challenging. I ended up using the MVCI-architecture proposed by Dave Barrett on [PragmaticCoding](https://www.pragmaticcoding.ca/javafx/mvci/) it works really well and he gives countless examples that served as a lot of guidance throughout the entire project.

#### Contributors:

+ [Kylo33](https://github.com/Kylo33)
