package almoder.space.fitnesstrainer.MainActivity;

import almoder.space.fitnesstrainer.R;

public class Expressions {

    public static boolean isFragmentOpened(CharSequence toolbarTitle, String title) {
        return toolbarTitle.equals(title);
    }

    public static boolean isArticlesOpened(int title) { return title == R.string.m3; }

    public static boolean isWorkoutsEmptyOnBackPress(int title, int count) {
        return title == 0 && count == 0;
    }

    public static boolean isBackStackHasEntries(int count) { return count > 1; }
}
