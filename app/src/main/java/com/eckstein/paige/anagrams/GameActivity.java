package com.eckstein.paige.anagrams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameActivity extends Activity implements View.OnClickListener {

    GamePlay play = new GamePlay();
    HashMap<Integer, String> tiles;
    TextView currentWord;
    static final int[] button_ids =
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

        GamePlay play = new GamePlay();
        currentWord = findViewById(R.id.playField);

        tiles = new HashMap<>();
        for(int id : button_ids)
        {
            ImageButton button = findViewById(id);
            button.setOnClickListener(this);
            int randomTile = play.getRandomTile();
            button.setImageResource(randomTile);
            tiles.put(id, play.getTileLetter(randomTile));
        }

        //this temporarily just takes the user to the end game screen
        //will eventually be used to submit button for verification process
        Button submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, GameOver.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onClick(View v)
    {
        Animation ranim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        v.startAnimation(ranim);
        currentWord.append(tiles.get(v.getId()));
    }
}
