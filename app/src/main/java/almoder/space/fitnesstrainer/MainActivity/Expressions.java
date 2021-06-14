package almoder.space.fitnesstrainer.MainActivity;

import android.view.View;
import android.widget.EditText;
import almoder.space.fitnesstrainer.R;

public class Expressions {

    private static final String toolbarTitleIsNull = "toolBarTitle is null!",
                                titleIsNull = "title is null!",
                                editTextIsNull = "editText is null!";

    public static boolean isFragmentOpened(CharSequence toolbarTitle, String title) {
        if (toolbarTitle == null) throw new NullPointerException(toolbarTitleIsNull);
        if (title == null) throw new NullPointerException(titleIsNull);
        return toolbarTitle.equals(title);
    }

    public static boolean isArticlesOpened(int title) { return title == R.string.m3; }

    public static boolean isWorkoutsEmptyOnBackPress(int title, int count) {
        return title == 0 && count == 0;
    }

    public static boolean isBackStackHasEntries(int count) { return count > 1; }

    public static boolean editTextIsVisible(EditText editText) {
        if (editText == null) throw new NullPointerException(editTextIsNull);
        return editText.getVisibility() == View.VISIBLE;
    }
}
