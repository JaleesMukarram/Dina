package com.JaleesMukarramGame.FightingWithCorona.Models;

import android.graphics.Bitmap;

public class Gun {

    public static final int UP_DOWN_MARGIN = 200;
    public static final int FIXED_X = 10;
    public static final int INITIAL_Y = 400;

    private Bitmap mGun;

    private int height, width;
    private int currentY, maxY, minY;
    private int canvasWidth, canvasHeight;
    private int counterForUp;

    private boolean touched;

    public Gun(Bitmap mGun) {
        this.mGun = mGun;
    }

    public void fillAllCoordinates(int canvasWidth, int canvasHeight) {

        this.height = mGun.getHeight();
        this.width = mGun.getWidth();
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        this.currentY = INITIAL_Y;

        this.maxY = canvasHeight - height;
        this.minY = UP_DOWN_MARGIN;

    }


    public Bitmap getmGun() {
        return mGun;
    }


    public void setmGun(Bitmap mGun) {
        this.mGun = mGun;
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

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public void setCanvasWidth(int canvasWidth) {
        this.canvasWidth = canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public void setCanvasHeight(int canvasHeight) {
        this.canvasHeight = canvasHeight;
    }

    public int getCounterForUp() {
        return counterForUp;
    }

    public void setCounterForUp(int counterForUp) {
        this.counterForUp = counterForUp;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public boolean isTouched() {

        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
        counterForUp = 20;
    }

    public boolean shouldMoveUp() {

        this.counterForUp--;
        return this.counterForUp > 0;

    }

    public void moveUp() {

        if (currentY < minY) {

        } else {
            currentY -= 10;

        }

        this.touched = false;

    }

    public void moveDown() {

        if (currentY > maxY) {

        } else {

            currentY += 10;
        }
    }
}
