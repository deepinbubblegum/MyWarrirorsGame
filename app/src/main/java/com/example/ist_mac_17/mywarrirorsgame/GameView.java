package com.example.ist_mac_17.mywarrirorsgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View
{
    private Timer mUpdateTimer;
    public GameView(Context context)
    {
        super(context);
        setFocusable(true);
        mUpdateTimer = new Timer();
        mUpdateTimer.schedule(new UpdateTask(), 0, 150);
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            postInvalidate();
        }
    }

    private Hero hero;
    private Bitmap heroBmp;
    private List<Enemy> enemies = new ArrayList<Enemy>();
    private Enemy createEnemies(int resource) {
        Bitmap bmp =
                BitmapFactory.decodeResource(getResources(), resource);
        return new Enemy(this,bmp);
    }
    private Bitmap boom;
    private List<Boom> tempB = new ArrayList<Boom>();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int
            oldh)
    {

        super.onSizeChanged(w, h, oldw, oldh);
        heroBmp =
                BitmapFactory.decodeResource(getResources(),
                        R.drawable.hero);
        hero = new Hero(this,heroBmp);

        for(int i = 0 ; i< 10 ; i++)
            enemies.add(createEnemies(R.drawable.enemy));

        boom = BitmapFactory.decodeResource(getResources(),
                R.drawable.boom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap map = BitmapFactory.decodeResource(getResources(),
                R.drawable.bg);
        Rect src = new Rect(0,0,map.getWidth(),map.getHeight());
        Rect dst = new Rect(0,0,getWidth(),getHeight());
        canvas.drawBitmap(map,src,dst,null);

        Bitmap wasdbt = BitmapFactory.decodeResource(getResources(),R.drawable.wasd);
        Rect srcwasd = new Rect(0,0,wasdbt.getWidth(),wasdbt.getHeight());
        Rect dstwasd = new Rect(0,getHeight() - 300,300,getHeight());
        canvas.drawBitmap(wasdbt,srcwasd,dstwasd,null);

        Bitmap xbutton = BitmapFactory.decodeResource(getResources(),
                R.drawable.xbutton);
        Rect srcx = new Rect(0,0,xbutton.getWidth(),xbutton.getHeight());
        Rect dstx = new Rect(getWidth()-400,getHeight()-400,getWidth()-200,getHeight()-200);
        canvas.drawBitmap(xbutton,srcx,dstx,null);

        Bitmap ybutton = BitmapFactory.decodeResource(getResources(),
                R.drawable.ybutton);
        Rect srcy = new Rect(0,0,ybutton.getWidth(),ybutton.getHeight());
        Rect dsty = new Rect(getWidth()-200,getHeight()-400,getWidth(),getHeight()-200);
        canvas.drawBitmap(ybutton,srcy,dsty,null);

        super.onDraw(canvas);
        hero.onDraw(canvas);

        Paint pb = new Paint();
        pb.setColor(Color.argb(100,255,0,0));
        if (barrier)
            canvas.drawCircle(hero.x+hero.width/2,hero.y+hero.height/2,200,pb);

        for (Enemy enemy : enemies) {
             enemy.onDraw(canvas);
        }

        for (int i = enemies.size()-1; i >= 0; i--) {
            Enemy en = enemies.get(i);
            if (en.isHit(hero.x,hero.y))
            {
                enemies.remove(en);
                tempB.add(new Boom(tempB, this,
                        hero.x, hero.y, boom));
                score ++;
            }
        }

        for(int i = 0 ; i< tempB.size();i++)
            tempB.get(i).onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        p.setTextSize(100);

        canvas.drawText("SCORE : "+ score , 50,100 , p);
    }

    int score = 0;
    boolean barrier = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int tx = (int)event.getX();
        int ty = (int)event.getY();

        if (tx > getWidth() - 400 && tx < getWidth() - 200 && ty > getHeight() - 400 && tx < getHeight() - 200)
            barrier = !barrier;


//        UP
        if(tx > 100 && tx < 200 && ty > getHeight() - 300 && ty < getHeight() - 200 )
            hero.ySpeed = -30;
        if ((tx > 200 && tx < 300 ) && (ty > getHeight() - 200 && ty < getHeight() - 100))
            hero.xSpeed = 30;
//        down
        if ((tx > 100 && tx < 200 ) && (ty > getHeight() - 200 && ty < getHeight()))
            hero.ySpeed = 30;
        if ((tx > 0 && tx < 100 )&&(ty > getHeight()-200 && ty < getHeight() -100))
            hero.xSpeed = -30;
//        if(tx>hero.x)
//            hero.xSpeed = 30;
//        if(tx<hero.x)
//            hero.xSpeed = -30;
//        if(ty>hero.y)
//            hero.ySpeed = 30;
//        if(ty<hero.y)
//            hero.ySpeed = -30;
        if(event.getAction()== MotionEvent.ACTION_UP)
        {
            hero.xSpeed=0;
            hero.ySpeed=0;
        }
        return true;
    }
}
