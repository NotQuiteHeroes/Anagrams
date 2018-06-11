package com.eckstein.paige.anagrams;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Scores extends Activity {

    int currentHighScore;
    String allScores;
    ArrayList<String> splitScores;

    TextView highScoreField, allScoresField;
    SharedPreferences prefs;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        highScoreField = findViewById(R.id.highScoreField);
        allScoresField = findViewById(R.id.allScoresField);
        done = findViewById(R.id.infoDoneButton);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Scores.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loadInfo();
        setFields();
    }

    protected void loadInfo()
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        currentHighScore = prefs.getInt("HighScore", 0);
        allScores = prefs.getString("AllScores", "");
        splitScores();

    }

    protected void splitScores()
    {
        splitScores = new ArrayList<>(Arrays.asList(allScores.split(",")));
        if(splitScores.size() > 0) {
            for (int i = 0; i < splitScores.size(); i++) {
                System.out.println(i + " " + splitScores.get(i));
            }
        }
    }

    protected void setFields()
    {

        if(splitScores.size()>1) {
            for (int i = 0; i < splitScores.size(); i += 2) {
                String scoreLine = String.format("%-10s %28s", splitScores.get(i), splitScores.get(i + 1));
                allScoresField.append(scoreLine + "\n");
                if (Integer.parseInt(splitScores.get(i + 1)) > currentHighScore) {
                    currentHighScore = Integer.parseInt(splitScores.get(i + 1));
                }
            }

            highScoreField.setText(Integer.toString(currentHighScore));
        }
        else
        {
            allScoresField.setText("");
            highScoreField.setText("0");
        }
    }
}
