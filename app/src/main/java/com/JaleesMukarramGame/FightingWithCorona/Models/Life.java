package com.JaleesMukarramGame.FightingWithCorona.Models;

import android.graphics.Bitmap;

public class Life {

    private Bitmap mLifeAvailable, mLifeGone;
    private int height, width;
    private boolean available;


    public Life(Bitmap mLifeAvailable, Bitmap mLifeGone) {
        this.mLifeAvailable = mLifeAvailable;
        this.mLifeGone = mLifeGone;
        this.width = mLifeAvailable.getWidth();
        this.height = mLifeAvailable.getHeight();
        this.available = true;
    }

    public Bitmap getmLifeAvailable() {
        return mLifeAvailable;
    }

    public void setmLifeAvailable(Bitmap mLifeAvailable) {
        this.mLifeAvailable = mLifeAvailable;
    }

    public Bitmap getmLifeGone() {
        return mLifeGone;
    }

    public void setmLifeGone(Bitmap mLifeGone) {
        this.mLifeGone = mLifeGone;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
