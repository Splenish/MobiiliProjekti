package com.example.test;

public class TrapArrayData {

    // Store the id of the trap icon
    private int mImageDrawable;
    // Store the name of the trap?
    private String trapName;
    // Store the state of the trap
    private String trapState;

    // Constructor that is used to create an instance of the Trap object
    public TrapArrayData(int mImageDrawable, String trapName, String trapState) {
        this.mImageDrawable = mImageDrawable;
        this.trapName = trapName;
        this.trapState = trapState;
    }

    public int getmImageDrawable() {
        return mImageDrawable;
    }

    public void setmImageDrawable(int mImageDrawable) {
        this.mImageDrawable = mImageDrawable;
    }

    public String gettrapName() {
        return trapName;
    }

    public void settrapName(String trapName) {
        this.trapName = trapName;
    }

    public String gettrapState() {
        return trapState;
    }

    public void settrapState(String trapState) {
        this.trapState = trapState;
    }
}

