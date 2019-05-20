package com.example.jdvelhapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finds a button in your activity_main.xml file with the ID buttonNext.
        //the R is the agglomeration of all the elements you have in your XML files
        Button buttonNext = (Button)findViewById(R.id.buttonNext);
        //final TextView description = (TextView)findViewById(R.id.description);

        //this adds an event to the button for it to listen to clicks and do something when someone
        //does click on the button
        buttonNext.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //this works! changes the text correctly
                //description.setText("Oy! You pressed on the button");

                //this changes the page to become that defined by the activity_game activity.
                //this layout is defined as well in res/layout, just like activity_main (for MainActivity)
                startActivity(new Intent(MainActivity.this, activity_game.class));
                finish();
            }
        });
    }
}
