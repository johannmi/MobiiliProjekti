package com.example.mobiiliprojekti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/*
* Initial commit test
* TESTI TESTI
*
*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DateAndTime testiTime = new DateAndTime();
        TextView testi = findViewById(R.id.testiView);
        testi.setText(testiTime.getTime());
    }
}
