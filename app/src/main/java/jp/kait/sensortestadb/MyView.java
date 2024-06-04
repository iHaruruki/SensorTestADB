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
        ps.setColor(Color.argb(255, 160, 160, 255));
        canvas.drawCircle(width/2 - posX, height/2 + posY, 20, ps);
    }

    public void setPos(int x, int y){
        posX = x;
        posY = y;

        int width = getWidth();
        int height = getHeight();
        int w2 = width/2;
        int h2 = height/2;

        invalidate();
    }
}
