package com.eckstein.paige.anagrams;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Scores extends Activity {

    //holds current highest score
    int currentHighScore;
    //string with all previous scores in format:
    //      "User name, score, User name, score, etc."
    String allScores;
    //Split version of allScores string - split at comma
    ArrayList<String> splitScores;

    //ui elements
    TextView highScoreField, allScoresField;
    Button done;

    //to get previous high score and user name, as well as allScores string
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        final MediaPlayer pop = MediaPlayer.create(this, R.raw.success);

        //initialize ui elements
        highScoreField = findViewById(R.id.highScoreField);
        allScoresField = findViewById(R.id.allScoresField);
        done = findViewById(R.id.infoDoneButton);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.start();
                Intent intent = new Intent(Scores.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //load previously stored information from shared prefs
        loadInfo();
        //update text fields with updated information
        setFields();
    }

    /**
     * get previous high score and all scores string from shared preferences
     */
    protected void loadInfo()
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        currentHighScore = prefs.getInt("HighScore", 0);
        allScores = prefs.getString("AllScores", "");
        splitScores();

    }

    /**
     * Takes allScores string in format:
     *       "User name, score, User name, score, etc."
     * and split at commas to obtain arrayList in form:
     *       {Username, score, UserName, score}
     */
    protected void splitScores()
    {
        splitScores = new ArrayList<>(Arrays.asList(allScores.split(",")));
    }

    /**
     * Update highest score and all scores text fields with updated information
     */
    protected void setFields()
    {

        //if information in splitScores
        if(splitScores.size()>1) {
            //count by two because always in username-score pair
            for (int i = 0; i < splitScores.size(); i += 2) {
                //left align userName, right align score
                String scoreLine = String.format("%-10s %28s", splitScores.get(i), splitScores.get(i + 1));
                allScoresField.append(scoreLine + "\n");
                //if score is greater than current highest score, then update current highest score
                if (Integer.parseInt(splitScores.get(i + 1)) > currentHighScore) {
                    currentHighScore = Integer.parseInt(splitScores.get(i + 1));
                }
            }

            //update high score field
            highScoreField.setText(Integer.toString(currentHighScore));
        }
        //if no information in splitScores, set high score to 0 and all scores field to empty
        else
        {
            allScoresField.setText("");
            highScoreField.setText("0");
        }
    }
}
