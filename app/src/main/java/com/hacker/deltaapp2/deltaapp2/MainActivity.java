package com.hacker.deltaapp2.deltaapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    final static int confirm = 97;
    private  final int UP = Menu.FIRST;
    private  final int DOWN = Menu.FIRST + 1;
    private  final int LEFT = Menu.FIRST + 2;
    private  final int RIGHT = Menu.FIRST + 3;
    private  final int SPEAK = Menu.FIRST + 4;

    private  int selectShape = DrawableViewComponent.CIRCLE;
    SpeechRecognizer speechRecognizer;
    Context mContext;
    DrawableViewComponent viewComponent;

    int pX = 0;
    int pY = 0;
    int h = 100;
    int w = 100;


    public void OnScreenTouched(View v){
        Toast.makeText(mContext, "Please press any of the buttons", Toast.LENGTH_SHORT).show();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int groupID = 1;
        menu.add(groupID,UP,UP,"UP").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(groupID,DOWN,DOWN,"DOWN").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(groupID,LEFT,LEFT,"LEFT").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(groupID,RIGHT,RIGHT,"RIGHT").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(groupID,SPEAK,SPEAK,"SPEAK").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(groupID,6,6,"Instructions").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case UP:
                pY -= 10;
                break;
            case DOWN:
                pY += 10;
                break;
            case LEFT:
                pX -= 10;
                break;
            case RIGHT:
                pX += 10;
                break;
            case SPEAK: {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak UP");
                startActivityForResult(i, confirm);

            }
                break;
            case 6: {
                Intent i = new Intent(mContext,InstructionsActivity.class);
                startActivity(i);
            } break;
            default:
                return super.onOptionsItemSelected(item);

        }

        checkBoundary();
        viewComponent.DrawShape(mContext,selectShape,pX,pY,h,w,true);
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) {
            speechRecognizer.cancel();
            speechRecognizer.destroy();

        }

    }
    void checkBoundary(){
        if(pY+h/2>=viewComponent.getHeight()){
            pY = viewComponent.getHeight()-h/2;
        }
        if(pY-h/2<=0){
            pY = h/2;
        }

        if(pX+w/2>=viewComponent.getWidth()){
            pX = viewComponent.getWidth()-w/2;
        }
        if(pX-w/2<=0){
            pX = w/2;
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        viewComponent = (DrawableViewComponent) findViewById(R.id.canvasArea);
        viewComponent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pX = viewComponent.getWidth()/2;
                pY = viewComponent.getHeight()/2;
                viewComponent.DrawShape(mContext,selectShape,pX,pY,h,w,true);
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String str;
        if(requestCode==confirm&&resultCode==RESULT_OK)
        {

            ArrayList<String> textList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            str = textList.get(0);

           if((Objects.equals("up", str))||(Objects.equals("Up", str)))
            {
                 pY -= 10;
                viewComponent.DrawShape(mContext,selectShape,pX,pY,h,w,true);
                checkBoundary();

            }
            if((Objects.equals("down", str))||(Objects.equals("Down", str)))
            {
                pY += 10;
                viewComponent.DrawShape(mContext,selectShape,pX,pY,h,w,true);
                checkBoundary();
            }
            if((Objects.equals("left", str))||(Objects.equals("Left", str)))
            {
                pX -= 10;
                viewComponent.DrawShape(mContext,selectShape,pX,pY,h,w,true);
                checkBoundary();
            }
            if((Objects.equals("right", str))||(Objects.equals("Right", str)))
            {
                pX += 10;
                viewComponent.DrawShape(mContext,selectShape,pX,pY,h,w,true);
                checkBoundary();
            }
            if((Objects.equals("grow", str))||(Objects.equals("Grow", str))) {
                h += 10;
                w += 10;
                if (h == viewComponent.getHeight() || w == viewComponent.getWidth()) {
                    h = 100;
                    w = 100;
                }
            }

            if((Objects.equals("shrink", str))||(Objects.equals("Shrink", str))) {
                h -= 10;
                w -= 10;
                if (h == 0 || w == 0) {
                    h = 100;
                    w = 100;
                }
            }

            if((Objects.equals("circle", str))||(Objects.equals("Circle", str)))
            {
                selectShape = DrawableViewComponent.CIRCLE;
            }



            if((Objects.equals("square", str))||(Objects.equals("Square", str)))
            {
                selectShape = DrawableViewComponent.SQUARE;
            }

            Toast.makeText(mContext, "Thanks da", Toast.LENGTH_SHORT).show();

        }

    }


}
