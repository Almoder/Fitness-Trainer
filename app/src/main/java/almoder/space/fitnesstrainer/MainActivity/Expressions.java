package almoder.space.fitnesstrainer.MainActivity;

import almoder.space.fitnesstrainer.R;

public class Expressions {

    public boolean isFragmentOpened(CharSequence toolbarTitle, String title) {
        return toolbarTitle.equals(title);
    }

    public boolean isArticlesOpened(int title) { return title == R.string.m3; }

    public boolean isWorkoutsEmptyOnBackPress(int title, int count) {
        return title == 0 && count == 0;
    }

    public boolean isBackStackHasEntries(int count) { return count > 1; }
}
