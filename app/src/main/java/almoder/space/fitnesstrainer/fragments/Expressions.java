package almoder.space.fitnesstrainer.fragments;

import android.text.Editable;

public class Expressions {

    private static final String textIsNull = "text is null!";

    public static boolean isRepsEditTextChanged(Editable text) {
        if (text == null) {
            throw new NullPointerException(textIsNull);
        }
        return !text.toString().equals("0");
    }

    public static boolean isWeightTextChanged(Editable text) {
        if (text == null) {
            throw new NullPointerException(textIsNull);
        }
        return !text.toString().equals("0");
    }

    public static boolean thereIsNoWorkouts(int count) {
        return count == 0;
    }
}
