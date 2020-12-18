package com.JaleesMukarramGame.FightingWithCorona.CoronaCOVID_19;

import android.graphics.Bitmap;

public class CoronaAttackers {

    public static final int EASY_SPEED = 8;
    public static final int MODIUM_SPEED = 12;
    public static final int FAST_SPEED = 18;

    private Bitmap mCorona;

    private int width, height;
    private int currentX, currentY;
    private boolean hidden;

    private int minX;

    private boolean isAttacking;

    public CoronaAttackers(Bitmap mCorona) {

        this.mCorona = mCorona;
        this.height = mCorona.getHeight();
        this.width = mCorona.getWidth();

    }

    public Bitmap getmCorona() {
        return mCorona;
    }

    public void setmCorona(Bitmap mCorona) {
        this.mCorona = mCorona;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public boolean canMoveForward() {

        return currentX > minX;
    }

    public void moveForward(int speed) {

        currentX -= speed;

    }

    public void attack(int x, int y) {

        currentX = x;
        currentY = y;
        minX = 0 - height;
        isAttacking = true;
    }

}
