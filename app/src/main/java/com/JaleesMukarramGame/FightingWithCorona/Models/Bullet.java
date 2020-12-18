package com.JaleesMukarramGame.FightingWithCorona.Models;

import android.graphics.Bitmap;


public class Bullet {

    private Bitmap mBullet;

    private int currentX, currentY;
    private int maxX, minX;
    private int height, width;

    private boolean isShooting;

    public Bullet(Bitmap mBullet) {

        this.mBullet = mBullet;
        this.height = mBullet.getHeight();
        this.width = mBullet.getWidth();

    }

    public Bitmap getmBullet() {
        return mBullet;
    }

    public void setmBullet(Bitmap mBullet) {
        this.mBullet = mBullet;
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

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
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

    public void moveForward() {

        this.currentX += 13;

    }

    public boolean isShooting() {
        return isShooting;
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }

    public void throwBullet(int x, int y, int maxX) {

        this.currentX = x;
        this.currentY = y;
        this.maxX = maxX;
        this.isShooting = true;

    }

    public boolean canMoveForward() {

        return currentX < maxX;

    }

}
