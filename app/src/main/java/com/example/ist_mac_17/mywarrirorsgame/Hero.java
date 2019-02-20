package com.example.ist_mac_17.mywarrirorsgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Hero {

    static final int BMP_ROWS = 4;
    static final int BMP_COLUMNS = 4;
    int x = 0;
    int y = 0;
    GameView gameView;
    Bitmap bmp;
    int currentFrame = 0;
    int width;
    int height;
    int xSpeed = 0;
    int ySpeed = 0;

    public Hero(GameView gameView, Bitmap bmp) {
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.gameView = gameView;
        this.bmp = bmp;
        x=gameView.getWidth()/2;
        y=gameView.getHeight()/2;
    }

    private void update() {
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
        //right
        if(xSpeed > 0 && ySpeed ==0)
            return 2;
        //left
        else if(xSpeed < 0 && ySpeed == 0)
            return 1;
        //up
        else if(xSpeed == 0 && ySpeed < 0)
            return 3;
        //down
        else if(xSpeed == 0 && ySpeed > 0)
            return 0;

        //right up
        if(xSpeed > 0 && ySpeed < 0)
            return 2;
        //left up
        else if(xSpeed < 0 && ySpeed < 0)
            return 1;
        //right down
        else if(xSpeed > 0 && ySpeed > 0)
            return 0;
        //left down
        else if(xSpeed < 0 && ySpeed > 0)
            return 1;

        else
            return 0;

    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width,
                srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }


}
