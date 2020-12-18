package com.JaleesMukarramGame.FightingWithCorona.Models;

import android.graphics.Bitmap;

public class Sanitizer {

    private Bitmap mSanitizer;
    private int currentX, currentY;
    private int height, width;
    private int minX;

    private boolean sanitizing;
    private boolean hidden;

    public Sanitizer(Bitmap mSanitizer) {
        this.mSanitizer = mSanitizer;
        this.width = mSanitizer.getWidth();
        this.height = mSanitizer.getHeight();
    }

    public Bitmap getmSanitizer() {
        return mSanitizer;
    }

    public void setmSanitizer(Bitmap mSanitizer) {
        this.mSanitizer = mSanitizer;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
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

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public boolean isSanitizing() {
        return sanitizing;
    }

    public void setSanitizing(boolean sanitizing) {
        this.sanitizing = sanitizing;
    }

    public boolean canMoveForward() {

        return currentX > minX;
    }

    public void moveForward() {

        currentX -= 10;

    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void sanitize(int x, int y) {

        currentX = x;
        currentY = y;
        minX = 0 - height;
        this.sanitizing = true;

    }


}
