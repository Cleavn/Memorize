package cleavn.memorize.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import cleavn.memorize.R;

public class ColorPicker extends Dialog implements View.OnClickListener {

    private View color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12, color13, color14, color15;
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
        color2.setOnClickListener(this);
        color3 = (View) findViewById(R.id.color3);
        color3.setOnClickListener(this);
        color4 = (View) findViewById(R.id.color4);
        color4.setOnClickListener(this);
        color5 = (View) findViewById(R.id.color5);
        color5.setOnClickListener(this);

        color6 = (View) findViewById(R.id.color6);
        color6.setOnClickListener(this);
        color7 = (View) findViewById(R.id.color7);
        color7.setOnClickListener(this);
        color8 = (View) findViewById(R.id.color8);
        color8.setOnClickListener(this);
        color9 = (View) findViewById(R.id.color9);
        color9.setOnClickListener(this);
        color10 = (View) findViewById(R.id.color10);
        color10.setOnClickListener(this);

        color11 = (View) findViewById(R.id.color11);
        color11.setOnClickListener(this);
        color12 = (View) findViewById(R.id.color12);
        color12.setOnClickListener(this);
        color13 = (View) findViewById(R.id.color13);
        color13.setOnClickListener(this);
        color14 = (View) findViewById(R.id.color14);
        color14.setOnClickListener(this);
        color15 = (View) findViewById(R.id.color15);
        color15.setOnClickListener(this);
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
        }
        dismiss();
    }

    public int getColor(){
        return color;
    }
}
