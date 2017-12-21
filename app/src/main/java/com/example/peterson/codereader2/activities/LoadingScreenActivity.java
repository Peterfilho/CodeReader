package com.example.peterson.codereader2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.peterson.codereader2.R;

import static java.lang.Thread.sleep;

/**
 * Created by peterson on 21/12/17.
 */

public class LoadingScreenActivity extends AppCompatActivity {


    private TextView tv;
    private TextView tv2;
    private ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv = (TextView) findViewById(R.id.splashTextView);
        tv2 = (TextView) findViewById(R.id.splashTextView2);
        iv = (ImageView) findViewById(R.id.splashImageView);

        Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tv.startAnimation(myAnim);
        iv.startAnimation(myAnim);
        tv2.startAnimation(myAnim);

        final Intent i = new Intent(getApplicationContext(), MainActivity.class);

        Thread timer = new Thread() {
            public void run (){
                try{
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
            timer.start();
    }
}
