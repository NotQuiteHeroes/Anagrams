package com.eckstein.paige.anagrams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;

public class Splash extends Activity {

    Typeface plain;
    Paint paint;
    Bitmap background, logo;
    Rect src, dest;
    boolean _active = true;
    int _splashTime = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));

        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime))
                    {
                        sleep(100);
                        if(_active)
                        {
                            waited += 100;
                        }
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                }
            }
        };
        splashThread.start();
    }

    public class DrawView extends View {
        public DrawView(Context context)
        {
            super(context);
            plain = ResourcesCompat.getFont(context, R.font.sketch_college);
            paint = new Paint();
            background = BitmapFactory.decodeResource(getResources(), R.drawable.halftone_yellow_light);
            logo = BitmapFactory.decodeResource(getResources(), R.drawable.anagram_icon);
            src = new Rect(0, 0, background.getWidth(), background.getHeight());
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            String text = "Anagrams";
            int x =10;
            int y = 1350;
            paint.setTypeface(plain);
            paint.setColor(getResources().getColor(R.color.pink));
            paint.setLinearText(true);
            paint.setTextSize(190);
            dest = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
            canvas.drawBitmap(background, src, dest, paint);
            canvas.drawBitmap(logo, 220, 230, paint);
            canvas.drawText(text, x, y, paint);
        }
    }
}
