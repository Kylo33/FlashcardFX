# FlashcardApp

## by Kylo33 for Harvard's CS50x

Hi! This flashcard app is the project I chose to make for my final project as part of Harvard's CS50.

# Features

## Support for multiple types of flashcards:

+ Classic flashcards
+ Table-style flashcards
+ Multiple choice flashcards

## Simple support for scripting

Decks are stored as JSON files, which means they support creations via scripts. This was a key consideration in the creation of this program because writing scripts is my preferred way to create flashcards quickly! Languages such as JavaScript and Python have excellent support for interacting with JSON files, which makes the creation of flashcards much easier for those who choose to.

## In-depth deck creation menu

The deck creation menu supports a variety of options including editing all aspects of a deck's attributes:

+ Deck title
+ Deck description
+ Add/delete cards
+ Add/delete decks
+ Adding images to cards
+ Selecting multiple correct answers

## Various color themes

Using the [atlantafx](https://mkpaz.github.io/atlantafx/) project, the app supports a variety of color themes: both light and dark. All colors in the application are based on these themes, so nothing will feel out of place, no matter which theme you use.

## Performance

Performance was an important consideration for the project, especially with the file operations. By working towards reducing the load times, I learned how to manage threads effectively.

All decks are loaded without their images when the program starts. For directories with a small number of decks, they will appear instantly. For others, the application will load with a loading bar to indicate that the remaining decks are still loading.
When a deck is selected for practice, all of the images are loaded sequentially in another thread.

# Reflection

Overall, this project was very difficult for me. I spent a few months learning java from the University of Helensiki's MOOC before coming back to finish this project. I chose to use javafx because I had a basic understanding of it from that course, but the project was much more difficult than I had expected. For one, I think the scope was bigger than I realized. In addition, it quickly got disorganized. Although I spent countless hours researching the **Model View Controller** architecture for GUI apps, my project still feels messy in that regard.

I found the article [FXML isn't MVC](https://www.pragmaticcoding.ca/javafx/fxml_isnt_mvc) very useful in my learning about this model. My app still feels somewhat messy in its implementation of the Model-View-Controller architecture—such as the DisplayableFlashcard class which is not clearly in one category or another—but I feel grateful for the opportunity to get a brief introduction to the MVC architecture which I know is used in Web Development, which I intend on pursuing next.
