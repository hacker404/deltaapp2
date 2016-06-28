package com.hacker.deltaapp2.deltaapp2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hacker on 27/6/16.
 */
public class DrawableViewComponent extends View {



    private ShapeDrawable shapeDrawable;

    public static final int SQUARE = 0;
    public static final int CIRCLE = 1;


    public DrawableViewComponent(Context context, AttributeSet att) {
        super(context, att);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (shapeDrawable != null) {

            shapeDrawable.draw(canvas);
        }
    }

    public void DrawShape(Context mContext,int Shape, int x, int y, int height, int width, boolean drawFromCenter){

        switch (Shape){
            case 0:
                shapeDrawable = new ShapeDrawable(new RectShape());
                shapeDrawable.getPaint().setColor(ContextCompat.getColor(mContext,R.color.blue));
                if (drawFromCenter) {
                    shapeDrawable.setBounds(x-width/2,y-height/2,x+width/2,y+height/2);
                }
                else {
                    shapeDrawable.setBounds(x,y,x+width,y+height);
                }
                break;
            case 1:
                shapeDrawable = new ShapeDrawable(new OvalShape());
                shapeDrawable.getPaint().setColor(ContextCompat.getColor(mContext,R.color.blue));
                if (drawFromCenter) {
                    shapeDrawable.setBounds(x-width/2,y-height/2,x+width/2,y+height/2);
                }
                else {
                    shapeDrawable.setBounds(x,y,x+width,y+height);
                }
                break;
            default: break;
        }
        invalidate();
    }
}