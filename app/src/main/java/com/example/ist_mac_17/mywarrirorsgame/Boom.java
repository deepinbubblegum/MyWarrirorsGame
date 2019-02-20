package com.example.ist_mac_17.mywarrirorsgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.List;

public class Boom {

//    private float x;
//    private float y;
//    private Bitmap bmp;
    private int life = 10;
    private List<Boom> temps;

    static final int BMP_ROWS = 1;
    static final int BMP_COLUMNS = 8;
    int x = 0;
    int y = 0;
    GameView gameView;
    Bitmap bmp;
    int currentFrame = 0;
    int width;
    int height;
    int xSpeed = 0;
    int ySpeed = 0;

    public Boom(List<Boom> temps, GameView gameView, int
            x,int y, Bitmap bmp) {

        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameView = gameView;
        this.bmp = bmp;
        this.temps = temps;
        this.x = x;
        this.y = y;
    }

    public void onDraw(Canvas canvas) {
        //update();
        //canvas.drawBitmap(bmp, x, y, null);
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width,
                srcY + height);
        Rect dst = new Rect(x, y, x + 500, y + 500);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    private void update() {
        if (--life < 1) {
            temps.remove(this);
        }

        if (x >= gameView.getWidth() - width - xSpeed
                || x + xSpeed <= 0) {
            xSpeed = 0;
        }

        x = x + xSpeed;
        if (y >= gameView.getHeight() - height - ySpeed
                || y + ySpeed <= 0) {
            ySpeed = 0;
        }
        y = y + ySpeed;

        //if(xSpeed+ySpeed!=0)
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    private int getAnimationRow() {
            return 0;
    }



}
