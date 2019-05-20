package com.example.jdvelhapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class winningScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning_screen);

        Button returnMainPage = (Button)findViewById(R.id.buttonReturnToMainPage);

        returnMainPage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //this changes the page to become that defined by the MainActivity activity.
                //this layout is defined as well in res/layout, just like MainActivity (for winningScreen)
                startActivity(new Intent(winningScreen.this, MainActivity.class));
                finish();
            }
        });
    }
}
