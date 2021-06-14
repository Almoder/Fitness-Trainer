package almoder.space.fitnesstrainer;

import org.junit.Assert;
import org.junit.Test;

import static almoder.space.fitnesstrainer.MainActivity.Expressions.editTextIsVisible;
import static almoder.space.fitnesstrainer.MainActivity.Expressions.isArticlesOpened;
import static almoder.space.fitnesstrainer.MainActivity.Expressions.isBackStackHasEntries;
import static almoder.space.fitnesstrainer.MainActivity.Expressions.isWorkoutsEmptyOnBackPress;

public class MainActivityExpressionsTests {

    @Test
    public void isArticleOpenedTest() {
        int[] args = {
                R.string.m1, R.string.m2, R.string.m3, Integer.MIN_VALUE, Integer.MAX_VALUE
        };
        boolean[] expecteds = { false, false, true, false, false };
        boolean[] actuals = new boolean[args.length];
        for (int i = 0; i < actuals.length; i++) actuals[i] = isArticlesOpened(args[i]);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void isWorkoutsEmptyOnBackPressTest() {
        int[][] args = {
                { 0, 0 }, { 0, Integer.MIN_VALUE }, { Integer.MAX_VALUE, 0 },
                { Integer.MIN_VALUE, Integer.MIN_VALUE }, { Integer.MAX_VALUE, Integer.MAX_VALUE }
        };
        boolean[] expecteds = { true, false, false, false, false };
        boolean[] actuals = new boolean[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = isWorkoutsEmptyOnBackPress(args[i][0], args[i][1]);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void isBackStackHasEntriesTest() {
        int[] args = { 0, 1, 100, -1, -100, Integer.MIN_VALUE, Integer.MAX_VALUE };
        boolean[] expecteds = { false, false, true, false, false, false, true };
        boolean[] actuals = new boolean[args.length];
        for (int i = 0; i < actuals.length; i++) actuals[i] = isBackStackHasEntries(args[i]);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test(expected = NullPointerException.class)
    public void editTextIsVisibleExpressionText() { editTextIsVisible(null); }
}