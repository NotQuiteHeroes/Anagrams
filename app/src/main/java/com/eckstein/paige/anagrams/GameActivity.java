package com.eckstein.paige.anagrams;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    //GamePlay object for setting up board, choosing random tiles, getting letter from tile,
    //and validating user input words
    private GamePlay play;
    //holds individual letter button's id as Integer and its corresponding letter as String
    private HashMap<Integer, String> tiles;
    //text view to display current word user is creating
    private TextView currentWord;
    //holds list of individual letter button's ids as Integer
    //to be used to keep user from re-using a button
    private ArrayList<Integer> selectedLetters;
    //timer
    private TextView timer;
    //minutes and seconds
    int minutes = 0, seconds = 0;
    //handler for timer
    Handler timerHandler;
    //for individual tiles
    ImageButton button;
    //for button sounds
    MediaPlayer success;
    MediaPlayer fail;
    MediaPlayer song;
    int volume = 100;

    //button ids for game board
    private static final int[] button_ids =
            {
                    R.id.c0r0, R.id.c0r1, R.id.c0r2, R.id.c0r3, R.id.c0r4,
                    R.id.c1r0, R.id.c1r1, R.id.c1r2, R.id.c1r3, R.id.c1r4,
                    R.id.c2r0, R.id.c2r1, R.id.c2r2, R.id.c2r3, R.id.c2r4,
                    R.id.c3r0, R.id.c3r1, R.id.c3r2, R.id.c3r3, R.id.c3r4,
                    R.id.c4r0, R.id.c4r1, R.id.c4r2, R.id.c4r3, R.id.c4r4

            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //initialization
        play = new GamePlay(this);
        selectedLetters = new ArrayList<>();
        currentWord = findViewById(R.id.playField);
        tiles = new HashMap<>();
        timer = findViewById(R.id.timer);
        timerHandler = new Handler();
        success = MediaPlayer.create(this, R.raw.success);
        fail = MediaPlayer.create(this, R.raw.failure);
        song = MediaPlayer.create(this, R.raw.music);


        FloatingActionButton mute = findViewById(R.id.muteButton);
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(volume != 0) {
                    song.setVolume(0, 0);
                    volume = 0;
                }
                else
                {
                    song.setVolume(1, 1);
                    volume = 100;
                }
            }
        });

        //initialize timer
        timer.setText(this.getString(R.string.time, 3, 0));

        //start timer
        startTimer();
        song.start();

        //set up buttons on board
        for(int id : button_ids)
        {
            button = findViewById(id);                      //connect button
            button.setOnClickListener(this);                //attach onClickListener
            int randomTile = play.getRandomTile();          //get a random letter tile image
            button.setImageResource(randomTile);            //set the image to the button
            tiles.put(id, play.getTileLetter(randomTile));  //add button's id and letter to tiles
        }

        //button to clear current word user is creating
        Button clear = findViewById(R.id.clearButton);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentWord.setText("");                    //clear textview of word
                for(int id: selectedLetters)                //for each button currently selected
                {
                    button = findViewById(id);              //change it's image to unpressed
                    button.setImageResource(play.getTileDrawable(tiles.get(id)));
                }
                selectedLetters.clear();                    //clear list of currently selected
            }
        });

        //validate word
        Button submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current word user created
                String word = currentWord.getText().toString();
                //validate it
                boolean isWord = checkWord(word);

                //valid word
                if(isWord) {
                    //spin animation
                    success.start();
                    successAnimation();
                    for (int id : selectedLetters) {
                        button = findViewById(id);
                        int randomTile = play.getRandomTile();
                        button.setImageResource(randomTile);
                        tiles.put(id, play.getTileLetter(randomTile));
                    }
                    //clear selected letters and currentWord
                    selectedLetters.clear();
                    currentWord.setText("");
                }
                //not valid word
                else
                {
                    fail.start();
                    failAnimation();
                    currentWord.setText("");                    //clear textview of word
                    for(int id: selectedLetters)                //for each button currently selected
                    {
                        button = findViewById(id);              //change it's image to unpressed
                        button.setImageResource(play.getTileDrawable(tiles.get(id)));
                    }
                    selectedLetters.clear();                    //clear list of currently selected
                }
            }
        });
    }

    /**
     * Counter for game play.
     * Currently counts down from 3 minutes (180000 millis)
     * with a one second countDownInterval
     */
    public void startTimer()
    {
        new CountDownTimer(180000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                seconds = (int) (millisUntilFinished/1000) % 60;
                minutes = (int) ((millisUntilFinished/(1000*60)) % 60);
                updateTimer();
            }

            //when timer is up go to end game activity
            public void onFinish()
            {
                song.stop();
                Intent intent = new Intent(GameActivity.this, GameOver.class);
                intent.putExtra("wordScores", play.getWordScores());
                startActivity(intent);
                finish();
                System.out.println("FINISHED TIMER");
            }
        }.start();
    }

    /**
     * update timer displayed to the user.
     * updates each second.
     * format:
     *      .02d:.02d
     *      00:00
     */
    public void updateTimer()
    {
        //if less than ten seconds, change text to red
        if(minutes == 0 && seconds <= 10)
        {
            timer.setTextColor(Color.RED);
        }
        timer.setText(this.getString(R.string.time, minutes, seconds));
    }

    /**
     * Checks if user submitted string is a valid English word
     * @param word user submitted string to check validity of
     * @return true if valid word, false otherwise
     */
    public boolean checkWord(String word)
    {
        //word must be more than two letters
        if(word.length() > 2) {
            //if valid word
            if (play.validWord(word)) {
                //determine its score
                int score = play.scoreWord(word);
                //add word and its score to list of all found words by user
                play.addWord(word, score);
                //temporary --- for testing purposes
                System.out.println("SCORE! " + word + "\t" + score);
                return true;
                //if not valid word
            } else {
                System.out.println("ERROR! " + word + " is not a valid word.");
                return false;
            }
            //not valid word if less than 2 letters in length
        } else {
            System.out.println("ERROR! " + word + " is not a valid word.");
            return false;
        }
    }

    /**
     * onClick event for all tiles in game board
     * animates on button click
     * if the button hasn't been pressed before:
     *      adds the letter from the button pressed to the currentWord textview display
     *      and adds button to selectedLetters list
     * if the button has been pressed before:
     *      remove it from current word text view
     *      remove it from selected letters
     *      change tile to unpressed image
     * @param v View of the object - which button was clicked
     */
    public void onClick(View v)
    {
        int id = v.getId();
        button = findViewById(id);

        //each tile can only be used once
        //if button wasn't already pressed...
        if(!selectedLetters.contains(id))
        {
            //add selected letter to current word text view
            currentWord.append((tiles.get(id).toUpperCase()));
            //add button's id to selected list
            selectedLetters.add(id);
            //set button image to pressed version
            button.setImageResource(play.getPressedTile(play.getTileDrawable(tiles.get(id))));
        }
        //if button was already pressed
        else
        {
            //get current word in text view
            String current = currentWord.getText().toString();
            //get letter of already pressed button to remove
            String toRemove = tiles.get(id).toUpperCase();
            //find last occurrence of letter to remove
            int lastOccurrence = current.lastIndexOf(toRemove);
            //remove letter and concat rest of remaining letters
            String newWord = (current.substring(0, lastOccurrence))+(current.substring(lastOccurrence+1));
            //remove the already pressed button from selected letters
            selectedLetters.remove(Integer.valueOf(id));
            //update current word text field
            currentWord.setText(newWord);
            //change button image to unpressed
            button.setImageResource(play.getTileDrawable(tiles.get(id)));
        }
    }

    /**
     * If valid word, spin all letters upon submission
     */
    public void successAnimation()
    {
        Animation ranim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        for(int id: selectedLetters)                //for each button currently selected
        {
            button = findViewById(id);              //change it's image to unpressed
            button.startAnimation(ranim);
        }
    }

    /**
     * If not valid word, shake all letters upon submission
     */
    public void failAnimation()
    {
        Animation ranim = AnimationUtils.loadAnimation(this, R.anim.shake);
        for(int id: selectedLetters)                //for each button currently selected
        {
            button = findViewById(id);              //change it's image to unpressed
            button.startAnimation(ranim);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(song.isPlaying())
            song.pause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if(song.isPlaying())
            song.stop();
    }

    @Override
    public void onResume()
    {
        super.onResume();
            song.start();

    }
}
