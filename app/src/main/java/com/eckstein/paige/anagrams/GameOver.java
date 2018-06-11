package com.eckstein.paige.anagrams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class GameOver extends AppCompatActivity {

    //shared preferences to get username and save score
    private SharedPreferences prefs;
    //holds all words and their corresponding scores
    private HashMap<String, Integer> wordScores;
    //ui for high score and all scores
    private TextView scoreField, allWords, allScores;
    //holds tallied score from all words submitting during game
    private int finalScore;
    //holds player's username and final score of game
    //to be passed to shared preferences for use with Scores activity
    private String finalScoreString;
    //user's username, from shared preferences
    private String userName;
    //holds all scores from previously played games in format:
    //      "User Name, score, User Name, score, etc."
    //to be concatenated with finalScoreString before storing in shared prefs
    private String allScoresString;
    MediaPlayer intro;
    int volume = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //initialize ui and clear text views
        scoreField = findViewById(R.id.scoreField);
        scoreField.setText("");
        allWords = findViewById(R.id.allWordsField);
        allWords.setText("");
        allScores = findViewById(R.id.allScoresField);
        allScores.setText("");

        intro = MediaPlayer.create(this, R.raw.intro);
        final MediaPlayer pop = MediaPlayer.create(this, R.raw.success);

        //get wordScores hashmap from GamePlay activity
        Intent intent = getIntent();
        wordScores = (HashMap<String, Integer>) intent.getSerializableExtra("wordScores");
        //tally final score
        calculateFinalScore();
        //update final score text field
        scoreField.setText(this.getString(R.string.finalScore, finalScore));

        //step through each key and value from word scores hashmap
        //update textviews with each word and score pair
        for(Map.Entry<String, Integer> entry : wordScores.entrySet())
        {
            String word = entry.getKey();
            int value = entry.getValue();
            allWords.append(word+"\n");
            allScores.append(value+"\n");
        }

        //load previous score information from shared prefs
        loadInfo();
        //generate final score string to be appended to allScoresString
        generateFinalScoreString();

        FloatingActionButton mute = findViewById(R.id.muteButton);
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(volume != 0) {
                    intro.setVolume(0, 0);
                    volume = 0;
                }
                else
                {
                    intro.setVolume(1, 1);
                }
            }
        });

        //when done, update information in shared prefs
        Button done = findViewById(R.id.doneButton);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.start();
                prefs = PreferenceManager.getDefaultSharedPreferences(GameOver.this);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putInt("HighScore", finalScore);
                edit.putString("AllScores", finalScoreString);
                edit.apply();

                Intent intent = new Intent(GameOver.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * load previous information on scores and get player's username
     */
    protected void loadInfo()
    {
        prefs = getSharedPreferences("privatePrefs", Context.MODE_PRIVATE);
        userName = prefs.getString("UserName", "");
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        allScoresString = prefs.getString("AllScores", "");
    }

    /**
     * Tally final total score from all individual word scores
     */
    public void calculateFinalScore()
    {
        finalScore = 0;
        for(int score :  wordScores.values())
        {
            finalScore+=score;
        }
    }

    /**
     * Scores activity expects a string of the form:
     *       "User name, score, User name, score, etc."
     *  That it uses to split and display all previous game's scores
     *  This adds most recently played game's information in the correct format
     *  and appends it to any previously stored score information
     */
    public void generateFinalScoreString()
    {
        //if no previous score information
        if(allScoresString.equals(""))
        {
            finalScoreString = userName+","+finalScore;
        }
        //if previous score information
        else {
            finalScoreString = allScoresString + "," + userName + "," + finalScore;
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(intro.isPlaying())
            intro.pause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if(intro.isPlaying())
            intro.stop();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        intro.start();
    }
}
