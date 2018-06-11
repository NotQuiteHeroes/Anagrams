package com.eckstein.paige.anagrams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class GameOver extends Activity {

    private SharedPreferences prefs;
    private HashMap<String, Integer> wordScores;
    private TextView scoreField, allScores;
    private int finalScore;
    private String finalScoreString;
    private String userName;
    private String allScoresString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        scoreField = findViewById(R.id.scoreField);
        scoreField.setText("");
        allScores = findViewById(R.id.allWordsField);
        allScores.setText("");

        Intent intent = getIntent();
        wordScores = (HashMap<String, Integer>) intent.getSerializableExtra("wordScores");
        calculateFinalScore();
        scoreField.setText(this.getString(R.string.finalScore, finalScore));

        for(Map.Entry<String, Integer> entry : wordScores.entrySet())
        {
            String word = entry.getKey();
            int value = entry.getValue();
            System.out.println(word+" "+value);
            allScores.append(this.getString(R.string.scores, word, value)+"\n");
        }

        loadInfo();
        generateFinalScoreString();

        Button done = findViewById(R.id.doneButton);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    protected void loadInfo()
    {
        prefs = getSharedPreferences("privatePrefs", Context.MODE_PRIVATE);
        userName = prefs.getString("UserName", "");
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        allScoresString = prefs.getString("AllScores", "");
    }

    public void calculateFinalScore()
    {
        finalScore = 0;
        for(int score :  wordScores.values())
        {
            finalScore+=score;
        }
    }

    public void generateFinalScoreString()
    {
        if(allScoresString.equals(""))
        {
            finalScoreString = userName+","+finalScore;
        }
        else {
            finalScoreString = allScoresString + "," + userName + "," + finalScore;
        }
    }
}
