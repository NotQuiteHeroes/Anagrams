package com.eckstein.paige.anagrams;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Scores extends Activity {

    int highScore;
    String allScores;
    ArrayList<String> splitScores;

    TextView highScoreField, allScoresField;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        highScoreField = findViewById(R.id.highScoreField);
        allScoresField = findViewById(R.id.allScoresField);

        loadInfo();
        setFields();
    }

    protected void loadInfo()
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        highScore = prefs.getInt("HighScore", 0);
        allScores = prefs.getString("AllScores", "");
        splitScores();

    }

    protected void splitScores()
    {
        splitScores = new ArrayList<>(Arrays.asList(allScores.split(",")));
    }

    protected void setFields()
    {
        highScoreField.setText(highScore);

        for(int i = 0; i < splitScores.size()/3; i+=3)
        {
            allScoresField.append(splitScores.get(i) + "\t" + splitScores.get(i+1) + "\t" + splitScores.get(i+2));
        }
    }
}
