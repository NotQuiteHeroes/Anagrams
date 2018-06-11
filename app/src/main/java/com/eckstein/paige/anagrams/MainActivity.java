package com.eckstein.paige.anagrams;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer intro;
    int volume = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button play = findViewById(R.id.playButton);
        Button userInfo = findViewById(R.id.userButton);
        Button scores = findViewById(R.id.scoresButton);
        FloatingActionButton mute = findViewById(R.id.muteButton);

        final MediaPlayer pop = MediaPlayer.create(this, R.raw.success);
        intro = MediaPlayer.create(this, R.raw.intro);
        intro.setLooping(true);
        intro.start();

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
                    volume = 100;
                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.start();
                intro.stop();
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.start();
                intro.stop();
                Intent intent = new Intent(MainActivity.this, userInfo.class);
                startActivity(intent);
                finish();
            }
        });

        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.start();
                intro.stop();
                Intent intent = new Intent(MainActivity.this, Scores.class);
                startActivity(intent);
                finish();
            }
        });
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
