package cleavn.memorize.Objects;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import cleavn.memorize.R;

public class ColorPicker extends Dialog implements View.OnClickListener {

    private View color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12, color13, color14, color15, color16, color17, color18, color19, color20, color21, color22, color23, color24, color25;
    private int color;

    public ColorPicker(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_colorpicker);

        color1 = (View) findViewById(R.id.color1);
        color1.setOnClickListener(this);
        color2 = (View) findViewById(R.id.color2);
        color1.setOnClickListener(this);
        color3 = (View) findViewById(R.id.color3);
        color1.setOnClickListener(this);
        color4 = (View) findViewById(R.id.color4);
        color1.setOnClickListener(this);
        color5 = (View) findViewById(R.id.color5);
        color1.setOnClickListener(this);

        color6 = (View) findViewById(R.id.color6);
        color1.setOnClickListener(this);
        color7 = (View) findViewById(R.id.color7);
        color1.setOnClickListener(this);
        color8 = (View) findViewById(R.id.color8);
        color1.setOnClickListener(this);
        color9 = (View) findViewById(R.id.color9);
        color1.setOnClickListener(this);
        color10 = (View) findViewById(R.id.color10);
        color1.setOnClickListener(this);

        color11 = (View) findViewById(R.id.color11);
        color1.setOnClickListener(this);
        color12 = (View) findViewById(R.id.color12);
        color1.setOnClickListener(this);
        color13 = (View) findViewById(R.id.color13);
        color1.setOnClickListener(this);
        color14 = (View) findViewById(R.id.color14);
        color1.setOnClickListener(this);
        color15 = (View) findViewById(R.id.color15);
        color1.setOnClickListener(this);

        color16 = (View) findViewById(R.id.color16);
        color1.setOnClickListener(this);
        color17 = (View) findViewById(R.id.color17);
        color1.setOnClickListener(this);
        color18 = (View) findViewById(R.id.color18);
        color1.setOnClickListener(this);
        color19 = (View) findViewById(R.id.color19);
        color1.setOnClickListener(this);
        color20 = (View) findViewById(R.id.color20);
        color1.setOnClickListener(this);

        color21 = (View) findViewById(R.id.color21);
        color1.setOnClickListener(this);
        color22 = (View) findViewById(R.id.color22);
        color1.setOnClickListener(this);
        color23 = (View) findViewById(R.id.color23);
        color1.setOnClickListener(this);
        color24 = (View) findViewById(R.id.color24);
        color1.setOnClickListener(this);
        color25 = (View) findViewById(R.id.color25);
        color1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.color1:
                color = color1.getDrawingCacheBackgroundColor();
                break;
            case R.id.color2:
                color = color2.getDrawingCacheBackgroundColor();
                break;
            case R.id.color3:
                color = color3.getDrawingCacheBackgroundColor();
                break;
            case R.id.color4:
                color = color4.getDrawingCacheBackgroundColor();
                break;
            case R.id.color5:
                color = color5.getDrawingCacheBackgroundColor();
                break;
            case R.id.color6:
                color = color6.getDrawingCacheBackgroundColor();
                break;
            case R.id.color7:
                color = color7.getDrawingCacheBackgroundColor();
                break;
            case R.id.color8:
                color = color8.getDrawingCacheBackgroundColor();
                break;
            case R.id.color9:
                color = color9.getDrawingCacheBackgroundColor();
                break;
            case R.id.color10:
                color = color10.getDrawingCacheBackgroundColor();
                break;
            case R.id.color11:
                color = color11.getDrawingCacheBackgroundColor();
                break;
            case R.id.color12:
                color = color12.getDrawingCacheBackgroundColor();
                break;
            case R.id.color13:
                color = color13.getDrawingCacheBackgroundColor();
                break;
            case R.id.color14:
                color = color14.getDrawingCacheBackgroundColor();
                break;
            case R.id.color15:
                color = color15.getDrawingCacheBackgroundColor();
                break;
            case R.id.color16:
                color = color16.getDrawingCacheBackgroundColor();
                break;
            case R.id.color17:
                color = color17.getDrawingCacheBackgroundColor();
                break;
            case R.id.color18:
                color = color18.getDrawingCacheBackgroundColor();
                break;
            case R.id.color19:
                color = color19.getDrawingCacheBackgroundColor();
                break;
            case R.id.color20:
                color = color20.getDrawingCacheBackgroundColor();
                break;
            case R.id.color21:
                color = color21.getDrawingCacheBackgroundColor();
                break;
            case R.id.color22:
                color = color22.getDrawingCacheBackgroundColor();
                break;
            case R.id.color23:
                color = color23.getDrawingCacheBackgroundColor();
                break;
            case R.id.color24:
                color = color24.getDrawingCacheBackgroundColor();
                break;
            case R.id.color25:
                color = color25.getDrawingCacheBackgroundColor();
                break;
        }
    }

    public int getColor(){
        return color;
    }
}
