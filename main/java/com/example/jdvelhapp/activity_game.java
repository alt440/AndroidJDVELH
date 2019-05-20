package com.example.jdvelhapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_game extends AppCompatActivity {

    public static IndexInArrayFollower index = new IndexInArrayFollower();
    //for updating the int values
    private Handler updateIndex = new Handler();
    private Handler updateLife = new Handler();
    private Button rightButton;
    private Button bottomButton;
    private Button leftButton;
    private Button topButton;
    private Button attack;
    private static int length = 4;

    //initial enemyHP
    private final int initialEnemyHP = 10;

    //user has 20HP. Enemies have 10HP.
    private int userHP = 20;
    private int enemyHP = initialEnemyHP;

    //this is the text description of what is happening
    private TextView pageDescription;

    public static int getLength(){
        return length;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //create a grid
        int[][] gridOfPossibilities = new int[length][length];
        //generate a random destination for the user to win
        int userWins = (int)(Math.random()*(length*length));
        //must not be at initial position!
        while(userWins==0){
            userWins = (int)(Math.random()*(length*length));
        }

        //mark that location in grid
        gridOfPossibilities[(int)(userWins/length)][userWins%length]=2;
        //generate enemies at 33% of locations
        int nbEnemies = (int)((length*length)*0.33);

        //assigning enemies at locations
        for(int i=0;i<nbEnemies;i++){
            int enemyLoc = (int)(Math.random()*(length*length));
            if(gridOfPossibilities[(int)(enemyLoc/length)][enemyLoc%length] == 0 && enemyLoc!=0){
                gridOfPossibilities[(int)(enemyLoc/length)][enemyLoc%length] = 1;
            } else{
                while(gridOfPossibilities[(int)(enemyLoc/length)][enemyLoc%length] !=0){
                    enemyLoc = (int)(Math.random()*(length*length));
                }
                gridOfPossibilities[(int)(enemyLoc/length)][enemyLoc%length] = 1;
            }
        }

        //print grid
        for (int i=0;i<gridOfPossibilities.length;i++){
            for (int j=0;j<gridOfPossibilities[0].length;j++){
                System.out.print(gridOfPossibilities[i][j]+" ");
            }
            System.out.println();
        }


        //user hits randomly from 1-5. Enemy does same.
        //if user dies, directed to a losing page.
        //if user reaches the winning point, he wins

        //final grid layout
        final int[][] gridFinalLayout = gridOfPossibilities;

        //reset values of column/row in case it gets screwed up
        index.setCurrentRowIndex(0);
        index.setCurrentColumnIndex(0);

        //initialize the movement buttons
        rightButton = (Button)findViewById(R.id.rightButton);
        bottomButton = (Button)findViewById(R.id.downButton);
        leftButton = (Button)findViewById(R.id.leftButton);
        topButton = (Button)findViewById(R.id.upButton);
        //initialize the attack button
        attack = (Button)findViewById(R.id.attackButton);

        pageDescription = (TextView)findViewById(R.id.descriptionPage);
        pageDescription.setText("You are now in the labyrinth.\nChoose where to go next.");

        //disable left and up button because cannot go left nor up
        leftButton.setEnabled(false);
        topButton.setEnabled(false);
        attack.setVisibility(View.INVISIBLE);

        //the +1/-1 added inside of the onClick events for looking at the grid are being put as
        //the expected behavior of the button. Thus, the currentRow/currentColumn have not yet been
        //updated, but I expect them to have changed by +1/-1. This is because of threads.
        leftButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //reset attack button to invisible
                attack.setVisibility(View.INVISIBLE);

                index.setDecrementColumn();
                updateIndex.post(new indexUpdater());

                //why is it 1 instead of 0? idk. maybe because of delay of executing thread
                if(index.getCurrentColumnIndex() <= 1){
                    leftButton.setEnabled(false);
                    rightButton.setEnabled(true);
                } else{
                    rightButton.setEnabled(true);
                }

                if(gridFinalLayout[index.getCurrentRowIndex()][index.getCurrentColumnIndex()-1] == 2){
                    startActivity(new Intent(activity_game.this, winningScreen.class));
                    finish();
                } else if(gridFinalLayout[index.getCurrentRowIndex()][index.getCurrentColumnIndex()-1] == 1){
                    //enemy encountered. Fight
                    attack.setVisibility(View.VISIBLE);
                    pageDescription.setText("There is an enemy.\nBeat it until it is dead!");
                    System.out.println("There is an enemy");
                    //set buttons to not enabled because must fight the enemy.
                    leftButton.setEnabled(false);
                    topButton.setEnabled(false);
                    rightButton.setEnabled(false);
                    bottomButton.setEnabled(false);
                } else{
                    pageDescription.setText("Nothing interesting here.");
                }
            }
        });

        topButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //reset attack button to invisible
                attack.setVisibility(View.INVISIBLE);

                index.setDecrementRow();
                updateIndex.post(new indexUpdater());

                if(index.getCurrentRowIndex() <= 1){
                    topButton.setEnabled(false);
                    bottomButton.setEnabled(true);
                } else{
                    bottomButton.setEnabled(true);
                }

                if(gridFinalLayout[index.getCurrentRowIndex()-1][index.getCurrentColumnIndex()] == 2){
                    startActivity(new Intent(activity_game.this, winningScreen.class));
                    finish();
                } else if(gridFinalLayout[index.getCurrentRowIndex()-1][index.getCurrentColumnIndex()] == 1){
                    //enemy encountered. Fight
                    attack.setVisibility(View.VISIBLE);
                    pageDescription.setText("There is an enemy.\nBeat it until it is dead!");
                    System.out.println("There is an enemy");
                    //set buttons to not enabled because must fight the enemy.
                    leftButton.setEnabled(false);
                    topButton.setEnabled(false);
                    rightButton.setEnabled(false);
                    bottomButton.setEnabled(false);
                } else{
                    pageDescription.setText("Nothing interesting here.");
                }
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //reset attack button to invisible
                attack.setVisibility(View.INVISIBLE);

                index.setIncrementColumn();
                updateIndex.post(new indexUpdater());

                if(index.getCurrentColumnIndex() >= length-2) {
                    rightButton.setEnabled(false);
                    leftButton.setEnabled(true);
                } else{
                    leftButton.setEnabled(true);
                }

                if(gridFinalLayout[index.getCurrentRowIndex()][index.getCurrentColumnIndex()+1] == 2){
                    startActivity(new Intent(activity_game.this, winningScreen.class));
                    finish();
                } else if(gridFinalLayout[index.getCurrentRowIndex()][index.getCurrentColumnIndex()+1] == 1){
                    //enemy encountered. Fight
                    attack.setVisibility(View.VISIBLE);
                    pageDescription.setText("There is an enemy.\nBeat it until it is dead!");
                    System.out.println("There is an enemy");
                    //set buttons to not enabled because must fight the enemy.
                    leftButton.setEnabled(false);
                    topButton.setEnabled(false);
                    rightButton.setEnabled(false);
                    bottomButton.setEnabled(false);
                } else{
                    pageDescription.setText("Nothing interesting here.");
                }
            }
        });

        bottomButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //reset attack button to invisible
                attack.setVisibility(View.INVISIBLE);

                index.setIncrementRow();
                updateIndex.post(new indexUpdater());

                if(index.getCurrentRowIndex() >= length-2){
                    bottomButton.setEnabled(false);
                    topButton.setEnabled(true);
                } else{
                    topButton.setEnabled(true);
                }

                if(gridFinalLayout[index.getCurrentRowIndex()+1][index.getCurrentColumnIndex()] == 2){
                    startActivity(new Intent(activity_game.this, winningScreen.class));
                    finish();
                } else if(gridFinalLayout[index.getCurrentRowIndex()+1][index.getCurrentColumnIndex()] == 1){
                    //enemy encountered. Fight
                    attack.setVisibility(View.VISIBLE);
                    pageDescription.setText("There is an enemy.\nBeat it until it is dead!");
                    System.out.println("There is an enemy");
                    //set buttons to not enabled because must fight the enemy.
                    leftButton.setEnabled(false);
                    topButton.setEnabled(false);
                    rightButton.setEnabled(false);
                    bottomButton.setEnabled(false);
                } else{
                    pageDescription.setText("Nothing interesting here.");
                }
            }
        });

        attack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                updateLife.post(new lifeUpdater());
            }
        });

    }

    //creating an inner class is the right way to easily have access to all your variables set
    //within the class. Creating a class outside of the activity_game class makes some variables
    //inaccessible.
    private class lifeUpdater implements Runnable{
        public void run(){
            //this will get the random value and update the life of the user
            //first enemy attacks
            int damageEnemy = (int)(Math.random()*5)+1;
            int damageUser = (int)(Math.random()*5)+1;

            userHP-=damageEnemy;
            enemyHP-=damageUser;

            pageDescription.setText("The enemy lost "+damageUser+"HP.\n You lost "+damageEnemy
                    +"HP.\n You have "+userHP+"HP left.");

            if(enemyHP<=0){
                pageDescription.setText("The enemy died, and you\nare left with "+userHP+"HP.");
                //set buttons to enabled because enemy died.
                leftButton.setEnabled(true);
                topButton.setEnabled(true);
                rightButton.setEnabled(true);
                bottomButton.setEnabled(true);
                //reset attack button to invisible
                attack.setVisibility(View.INVISIBLE);
                enemyHP=initialEnemyHP;
            } else if(userHP<=0){
                System.out.println("You lost!");
                startActivity(new Intent(activity_game.this, losingScreen.class));
                finish();
            }
        }
    }
}

class indexUpdater implements Runnable{
    //https://stackoverflow.com/questions/7938516/continuously-increase-integer-value-as-the-button-is-pressed
    public void run(){
        int currentRow = activity_game.index.getCurrentRowIndex();
        int currentColumn = activity_game.index.getCurrentColumnIndex();
        if(activity_game.index.getDecrementColumn()){
            System.out.println(activity_game.index.getCurrentColumnIndex());
            activity_game.index.setCurrentColumnIndex(currentColumn-1);
            activity_game.index.setDecrementColumn();
        } else if(activity_game.index.getDecrementRow()){
            System.out.println(activity_game.index.getCurrentRowIndex());
            activity_game.index.setCurrentRowIndex(currentRow-1);
            activity_game.index.setDecrementRow();
        } else if(activity_game.index.getIncrementColumn()){
            System.out.println(activity_game.index.getCurrentColumnIndex());
            activity_game.index.setCurrentColumnIndex(currentColumn+1);
            activity_game.index.setIncrementColumn();
        } else if(activity_game.index.getIncrementRow()){
            System.out.println(activity_game.index.getCurrentRowIndex());
            activity_game.index.setCurrentRowIndex(currentRow+1);
            activity_game.index.setIncrementRow();
        }

        System.out.println(activity_game.index.getCurrentRowIndex()+" "
                +activity_game.index.getCurrentColumnIndex());
    }
}