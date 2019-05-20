package com.example.jdvelhapp;

import android.os.Handler;

public class IndexInArrayFollower {
    //to update the ints currentRowIndex and currentColumnIndex, I need to do it async, because
    //cannot directly change an int value from a click event.
    private boolean incrementRow = false;
    private boolean incrementColumn = false;
    private boolean decrementRow = false;
    private boolean decrementColumn = false;

    //current index values
    //follows the player as he plays
    private int currentRowIndex = 0;
    private int currentColumnIndex = 0;

    public IndexInArrayFollower(){
        currentColumnIndex = 0;
        currentRowIndex = 0;
    }

    public boolean getIncrementRow(){
        return incrementRow;
    }

    public void setIncrementRow(){
        incrementRow=!incrementRow;
    }

    public boolean getIncrementColumn(){
        return incrementColumn;
    }

    public void setIncrementColumn(){
        incrementColumn=!incrementColumn;
    }

    public boolean getDecrementColumn(){
        return decrementColumn;
    }

    public void setDecrementColumn(){
        decrementColumn=!decrementColumn;
    }

    public boolean getDecrementRow(){
        return decrementRow;
    }

    public void setDecrementRow(){
        decrementRow=!decrementRow;
    }

    public int getCurrentRowIndex(){
        return currentRowIndex;
    }

    public int getCurrentColumnIndex(){
        return currentColumnIndex;
    }

    public void setCurrentRowIndex(int val){
        currentRowIndex = val;
        System.out.println("Verified currentRowIndex:"+currentRowIndex);
    }

    public void setCurrentColumnIndex(int val){
        currentColumnIndex = val;
        System.out.println("Verified currentColumnIndex:"+currentColumnIndex);
    }
}
