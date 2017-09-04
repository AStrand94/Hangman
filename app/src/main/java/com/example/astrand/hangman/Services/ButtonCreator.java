package com.example.astrand.hangman.Services;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapSize;
import com.example.astrand.hangman.Helper.MyBootstrapButton;

import java.util.Collection;
import java.util.HashMap;

public final class ButtonCreator {

    public static int BUTTON_GRID_SIZE = 6;

    public static HashMap<String,MyBootstrapButton> createListFromChars(Context contexts, char[] chars){
        HashMap<String,MyBootstrapButton> bootstrapButtonList = new HashMap<>();

        for (int i = 0; i < chars.length; i++){
            char c = chars[i];
            MyBootstrapButton button = new MyBootstrapButton(contexts);
            String charString = Character.toString(c);
            button.setText(charString);
            bootstrapButtonList.put(charString,button);

        }

        return bootstrapButtonList;
    }

    public static HashMap<String,MyBootstrapButton> createButtonGrid(Context context, char[] chars, GridLayout gridLayout){
        return createButtonGrid(createListFromChars(context,chars), gridLayout,chars,context);
    }

    public static HashMap<String,MyBootstrapButton> createButtonGrid(HashMap<String,MyBootstrapButton> bootstrapButtons, GridLayout gridLayout, char[] chars,Context context){

        int row, col, index = 0;
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);

        row = BUTTON_GRID_SIZE;
        col = BUTTON_GRID_SIZE;

        int screenWidth = getScreenWidth(context) / row;

        gridLayout.setColumnCount(col);
        gridLayout.setRowCount(row);

        for (int i = 0; i < col; i++){
            for (int j = 0; j < row; j++){
                if (index >= bootstrapButtons.size()) break;

                BootstrapButton button = bootstrapButtons.get(Character.toString(chars[index++]));
                button.setBootstrapBrand(DefaultBootstrapBrand.SUCCESS);

                button.setBootstrapSize(DefaultBootstrapSize.MD);
                button.setWidth(screenWidth);

                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                //param.leftMargin = 10;
                //param.topMargin = 5;
                //param.setGravity(Gravity.CENTER);not needed?
                param.columnSpec = GridLayout.spec(j);
                param.rowSpec = GridLayout.spec(i);
                button.setLayoutParams(param);
                //gridLayout.addView(button);
            }
        }
        return bootstrapButtons;
    }

    public static int getScreenWidth(Context context){
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);

        return point.x;
    }

    public static void updateButtonWidth(int layoutSize, int rowCount, Collection<MyBootstrapButton> buttons) {
        for (BootstrapButton button : buttons){
            button.setWidth(layoutSize/rowCount);
        }
    }
}
