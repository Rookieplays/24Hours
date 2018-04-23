package ie.ul.o.daysaver;

import android.graphics.Color;

/**
 * Created by Ali on 18/04/2018.
 */

public class TextEditorProperty {
    public static final int RED= Color.RED;
    public static final int BLACK=Color.BLACK;
    public static final int GREEN= Color.GREEN;
    public static final int BLUE=Color.BLUE;
    public static final int GRAY= Color.GRAY;

    public static final int XSS=8;
    public static final int XS=10;
    public static final int S=12;
    public static final int M=14;
    public static final int L=16;
    public static final int XL=18;
    public static final int XXL=24;
    public static final int XXXL=32;

    public static int CHOSSENCOLOR(String color)
    {
        switch (color)
        {
            case "RED":return RED;
            case "BLACK":return BLACK;
            case "GREEN":return GREEN;
            case "BLUE":return BLUE;
            case "GRAY":return GRAY;
            default:return BLACK;
        }
    }


}
