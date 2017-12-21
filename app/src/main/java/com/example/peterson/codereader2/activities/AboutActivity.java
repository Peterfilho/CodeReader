package com.example.peterson.codereader2.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.peterson.codereader2.R;

/**
 * Created by peterson on 21/12/17.
 */

public class AboutActivity extends MainActivity {

  //  public TextView textView4, textView7, textView9, textView11, textView12, textView13, textView14;
  //  public ScrollView scrollView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        returnMain();
/*
        textView4 = (TextView) findViewById(R.id.textView4);
        textView7 = (TextView) findViewById(R.id.textView7);
        textView9 = (TextView) findViewById(R.id.textView9);
        textView11 = (TextView) findViewById(R.id.textView11);
        textView12 = (TextView) findViewById(R.id.textView12);
        textView13 = (TextView) findViewById(R.id.textView13);
        textView14 = (TextView) findViewById(R.id.textView14);
        scrollView2 = (ScrollView) findViewById(R.id.scrollView2);
*/
    }
}
