package com.eckstein.paige.anagrams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;


public class GameActivity extends Activity implements View.OnClickListener {

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

        //initialize timer
        timer.setText(this.getString(R.string.time, 3, 0));

        startTimer();

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

        //this temporarily just takes the user to the end game screen
        //will eventually be used to submit button for verification process
        Button submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current word user created
                String word = currentWord.getText().toString();
                //validate it
                boolean isWord = checkWord(word);
                if(isWord) {
                    for (int id : selectedLetters) {
                        button = findViewById(id);
                        int randomTile = play.getRandomTile();
                        button.setImageResource(randomTile);
                        tiles.put(id, play.getTileLetter(randomTile));
                    }
                    selectedLetters.clear();
                    currentWord.setText("");
                }
                else
                {
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

            public void onFinish()
            {
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
     */
    public void updateTimer()
    {
        timer.setText(this.getString(R.string.time, minutes, seconds));
    }

    /**
     * Checks if user submitted string is a valid English word
     * @param word user submitted string to check validity of
     * @return true if valid word, false otherwise
     */
    public boolean checkWord(String word)
    {
        //if valid word
        if(word.length() > 2) {
            if (play.validWord(word)) {
                //determine its score
                int score = play.scoreWord(word);
                //add word and its score to list of all found words by user
                play.addWord(word, score);
                //temporary --- for testing purposes
                System.out.println("SCORE! " + word + "\t" + score);
                return true;
            } else {
                System.out.println("ERROR! " + word + " is not a valid word.");
                return false;
            }
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
            //spin animation
            Animation ranim = AnimationUtils.loadAnimation(this, R.anim.rotate);
            v.startAnimation(ranim);
            //add selected letter to current word text view
            currentWord.append((tiles.get(id).toUpperCase()));
            //add button's id to selected list
            selectedLetters.add(id);
            //set button image to pressed version
            button.setImageResource(play.getPressedTile(play.getTileDrawable(tiles.get(id))));
        }
        //if tile already used, shake
        else
        {
            Animation ranim = AnimationUtils.loadAnimation(this, R.anim.shake);
            v.startAnimation(ranim);
        }
    }
}
