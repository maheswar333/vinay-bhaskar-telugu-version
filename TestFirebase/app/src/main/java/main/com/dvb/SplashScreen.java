package main.com.dvb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by AIA on 8/16/16.
 */
public class SplashScreen extends AppCompatActivity{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "rs2jjzShvGV47MidZZVlNjzAO";
    private static final String TWITTER_SECRET = "WUcR1u1AeLhC0KRAxf3Sc3jJoPSGAaUUTvfWPJQOgLY3t9YOCS";

    
    private static int SPLASH_TIME_OUT = 4000;
    Handler handler;
    Runnable runnable;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        handler = new Handler();


        runnable = new Runnable() {
            @Override
            public void run() {
                    Intent intent = new Intent(SplashScreen.this, Dashboard_main.class);
                    startActivity(intent);
                    finish();

            }
        };

        handler.postDelayed(runnable, SPLASH_TIME_OUT);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
