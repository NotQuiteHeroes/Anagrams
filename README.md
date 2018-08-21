# Anagrams
Find as many words as you can in three minutes!

## Usage

### Splash Screen
![alt text](https://github.com/NotQuiteHeroes/Resources/blob/master/ScreenShots/anagramSplashScreen.JPG "Splash Screen")

The splash screen shows for a few seconds when the user initially opens the game. It simply displays the logo. The splash screen is done by
implementing a DrawView and it's accompanying OnDraw method. The timer for the splash screen is run in a separate thread. 

### Home Screen
![alt text](https://github.com/NotQuiteHeroes/Resources/blob/master/ScreenShots/anagramMainScreen.JPG "Main Screen")

The home screen is the landing pad of the game. It provides four buttons, one to start the game, one to add personal information such as a 
username and location, one to view all past scores, and a mute button in the bottom right corner. 

### Game Play
![alt text](https://github.com/NotQuiteHeroes/Resources/blob/master/ScreenShots/anagramGamePlay.JPG "Game Play")
![alt text](https://github.com/NotQuiteHeroes/Resources/blob/master/ScreenShots/anagramGamePlay1.JPG "Selected Letters")

The game itself consists of a 5x5 board of letters. Each letter is an ImageButton that is randomly set with a letter using
a similar letter distribution to Scrabble. Each selected letter will appear darker to show it is pressed, and the letters will
appear in the textbox. Clicking a selected letter will deselect it, and remove it from the textbox. The clear button will deselect all
letters and remove them from the textbox. The submit word button will submit the current text in the textbox for verification. If a valid
word was submitted, the letters will spin then be replaced with new letters. If the word was invalid, the letters will shake and deselect
themselves. The mute button will mute the background music, but not the button presses.

Valid words are stored in a Set that is initialized upon opening the application. The valid words are read in from a text file
stored as a raw resource. Scoring for individual letters is similar to Scrabble, with colors indicating the score level. These individual
letter scores are stored in a HashMap in the form <Character, Integer>. Each valid word entered by the user is scored using these
individual letter scores and stored in a HashMap in the form <String, Integer> and is displayed at the end of the game. GamePlay functionality
is held in the GamePlay class. 

During game play, the selected letters are held in an ArrayList. The background song and button press sound effects are implemented
using a MediaPlayer. The timer is implemented using a Handler. Button shake and spin are animations defined in the anim resource. 

### End Game
![alt text](https://github.com/NotQuiteHeroes/Resources/blob/master/ScreenShots/anagramEndGame.JPG "End of Game")

Once the timer reaches 00:00 the game will automatically end and the user will be taken to the game over screen. This retrieves
all the valid words found by the user during game play and displays them, along with their score. It also displays the total score
at the top of the screen. The final score will be converted to a string in the form userName + "," + finalScore. If previous scores
exist, this string will be appended at the end with a comma. This string is to be parsed by the Scores screen on the home screen to 
display all past total scores. The final score string, along with the high score of the game, will be stored in shared preferences for 
later retrieval.

### Scores
![alt text](https://github.com/NotQuiteHeroes/Resources/blob/master/ScreenShots/anagramScores.JPG "GUI Version")

The Scores screen is accessed from the home screen. It displays the user's username and any past final scores from their game play.
It also shows the highest score the user has gotten during game play at the top of the screen. The list of scores are retrieved
from a string stored in shared preferences. This string is in the form username + "," + score. This string is then parsed
to display one on each line. 
