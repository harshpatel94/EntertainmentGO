package com.example.hunter.finalproj;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.graphics.Typeface;
import android.media.tv.TvInputService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.facebook.FacebookSdk;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    TextView splashAppName;
    Typeface gothamrounded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_splash_screen);

            gothamrounded = Typeface.createFromAsset(getAssets(), "fonts/Gotham-Rounded/OpenType/GothamRnd-Bold.otf");
            //splashAppName = (TextView) findViewById(R.id.text_splash_appname);
            //splashAppName.setTypeface(gothamrounded);


            new Timer().schedule(new TimerTask() {
                public void run() {
                    SplashScreen.this.runOnUiThread(new Runnable() {
                        public void run() {

                            String profile = getSharedPreferences("fbPrefs", MODE_PRIVATE).getString("profile", "NA");
                            String login = getSharedPreferences("MyPrefs",MODE_PRIVATE).getString("userId",null);
                            Intent intent;
                            if (profile.equalsIgnoreCase("NA") && login == null) {
                                //not logged in
                                intent = new Intent(SplashScreen.this, LoginActivity.class);
                            } else {
                                //already logged in
                                //initialize the facebook sdk
                                intent = new Intent(SplashScreen.this, MainActivity.class);
                            }
//                            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}