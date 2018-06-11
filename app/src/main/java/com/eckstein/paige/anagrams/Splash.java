package com.eckstein.paige.anagrams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Splash extends Activity {

    //font
    Typeface plain;
    //for drawing
    Paint paint;
    //background and logo image
    Bitmap background, logo;
    //to draw background
    Rect src, dest;
    //_active and _splashtime used to determine how long to display splashScreen
    boolean _active = true;
    int _splashTime = 4000;
    //layout and views for displaying splash screen
    DrawView drawView;
    LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize ui elements
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        drawView = new DrawView(this);
        root.addView(drawView);
        setContentView(root);

        //splash screen thread
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

    //drawing for splash screen
    public class DrawView extends View {
        public DrawView(Context context)
        {
            super(context);
            //get font
            plain = ResourcesCompat.getFont(context, R.font.hand_type_writer);
            //initialize paint and images
            paint = new Paint();
            background = BitmapFactory.decodeResource(getResources(), R.drawable.halftone_yellow_light);
            logo = BitmapFactory.decodeResource(getResources(), R.drawable.anagram_icon);
            //initialize drawing rectangle to screen dimensions
            src = new Rect(0, 0, background.getWidth(), background.getHeight());
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            //text to draw
            String text = "Anagrams";
            //location to draw text
            int x =10;
            int y = 1350;
            //screen dimensions
            //used to draw background and logo
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            //set text font, color, size and draw direction
            paint.setTypeface(plain);
            paint.setColor(getResources().getColor(R.color.darkBlue));
            paint.setLinearText(true);
            paint.setTextSize(170);
            //determine end point for drawing background
            dest = new Rect(0, 0, width, height);
            //draw background
            canvas.drawBitmap(background, src, dest, paint);
            //draw logo
            canvas.drawBitmap(logo, (width-logo.getWidth())/2, ((height-logo.getHeight())/2)-350, paint);
            //draw text
            canvas.drawText(text, x, y, paint);
        }
    }
}
