package jp.kait.sensortestadb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Rect;

/**
 * TODO: document your custom view class.
 */
public class MyView extends View {
    private  int  posX = 0 ,  posY = 0;
    private int[] psColor = {160, 160, 255};

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width , height;
        width = getWidth();
        height = getHeight();
        Rect r = new Rect(0,0, width, height);
        Paint p = new Paint();
        p.setColor(Color.argb(255, 0, 128, 0));
        canvas.drawRect(r, p);

        Paint ps = new Paint();
        //ps.setColor(Color.argb(255, 160, 160, 255));
        ps.setColor(Color.argb(255,psColor[0],psColor[1],psColor[2]));
        canvas.drawCircle(width/2 - posX, height/2 + posY, 20, ps);
    }

    public void setColor(int red,int green,int blue){
        psColor[0]=red;
        psColor[1]=green;
        psColor[2]=blue;
    }

    public void setPos(int x, int y){
        posX = x;
        posY = y;
        //MyViewの幅/2 + posX
        //MyViewの

        int width = getWidth();
        int height = getHeight();
        int w2 = width/2;
        int h2 = height/2;
        int Circle_Center_x = w2 + posX;
        int Circle_center_y = h2 + posY;


        //(w2 + posX - 20) >= 0;
        //(w2 + posX + 20) <= width;
        //(h2 - posY - 20) >= 0;
        //(h2 - posY + 20) <= height;
        posX=Math.max(20-w2,Math.min(posX,width - 20 - w2));
        posY=Math.max(-height + 20 + h2,Math.min(posY,h2-20));
        invalidate();
    }
}
