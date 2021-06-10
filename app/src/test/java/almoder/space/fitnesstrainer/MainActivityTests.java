package almoder.space.fitnesstrainer;

import org.junit.Assert;
import org.junit.Test;

import almoder.space.fitnesstrainer.MainActivity.Expressions;
import almoder.space.fitnesstrainer.MainActivity.Logic;
import almoder.space.fitnesstrainer.fragments.AboutFragment;
import almoder.space.fitnesstrainer.fragments.ArticlesFragment;
import almoder.space.fitnesstrainer.fragments.ExercisesFragment;
import almoder.space.fitnesstrainer.fragments.SettingsFragment;
import almoder.space.fitnesstrainer.fragments.WorkoutsFragment;

public class MainActivityTests {

    private final Logic logic = new Logic();
    private final Expressions exp = new Expressions();

    @Test
    public void getTitleByIdTest() {
        int[] args = {
                R.id.nav_exercise, R.id.nav_workouts, R.id.nav_articles,
                R.id.nav_settings, R.id.nav_aboutapp, -1
        };
        int[] expects = {
                R.string.m1, R.string.m2, R.string.m3,
                R.string.m4, R.string.m6, R.string.undefined
        };
        int[] actuals = new int[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getTitleById(args[i]);
        Assert.assertArrayEquals(expects, actuals);
    }

    @Test
    public void getFragmentByIdTest() {
        int[] args = {
                R.id.nav_exercise, R.id.nav_workouts, R.id.nav_articles,
                R.id.nav_settings, R.id.nav_aboutapp, R.id.nav_share
        };
        Object[] expects = {
                ExercisesFragment.class, WorkoutsFragment.class, ArticlesFragment.class,
                SettingsFragment.class, AboutFragment.class, AboutFragment.class
        };
        Object[] actuals = new Object[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getFragmentById(args[i]).getClass();
        Assert.assertArrayEquals(expects, actuals);
    }

    @Test
    public void getItemByTitleResTest() {
        int[] args = {
                R.string.m1, R.string.m2, R.string.m3, R.string.m4,
                R.string.m5, R.string.m6, R.string.app_name, R.string.undefined
        };
        int[] expects = {
                R.id.nav_exercise, R.id.nav_workouts, R.id.nav_articles, R.id.nav_settings,
                R.id.nav_share, R.id.nav_aboutapp, R.id.nav_aboutapp, R.id.nav_workouts
        };
        int[] actuals = new int[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getItemIdByTitleRes(args[i]);
        Assert.assertArrayEquals(expects, actuals);
    }

    @Test
    public void getOnCreateItemIdTest() {
        boolean[][] args = {
                { false, true }, { true, false }, { true, true }
        };
        int[] expects = {
                R.id.nav_aboutapp, R.id.nav_settings, R.id.nav_settings
        };
        int[] actuals = new int[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getOnCreateItemId(args[i][0], args[i][1]);
        Assert.assertArrayEquals(expects, actuals);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOnCreateItemIdExceptionTest() {
        logic.getOnCreateItemId(false, false);
    }

    @Test
    public void getOnCreateTitleTest() {
        boolean[][] args = {
                { false, false }, { false, true }, { true, false }, { true, true }
        };
        int[] expects = {
                R.string.undefined, R.string.app_name, R.string.m4, R.string.m4
        };
        int[] actuals = new int[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getOnCreateTitle(args[i][0], args[i][1]);
        Assert.assertArrayEquals(expects, actuals);
    }

    @Test
    public void getOnCreateFragmentTest() {
        boolean[][] args = {
                { false, true }, { true, false }, { true, true }
        };
        Object[] expects = {
                AboutFragment.class, SettingsFragment.class, SettingsFragment.class
        };
        Object[] actuals = new Object[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getOnCreateFragment(args[i][0], args[i][1]).getClass();
        Assert.assertArrayEquals(expects, actuals);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOnCreateFragmentExceptionTest() {
        logic.getOnCreateFragment(false, false);
    }

    @Test
    public void isArticleOpenedTest() {
        int[] args = {
                R.string.m1, R.string.m2, R.string.m3, Integer.MIN_VALUE, Integer.MAX_VALUE
        };
        boolean[] expects = { false, false, true, false, false };
        boolean[] actuals = new boolean[args.length];
        for (int i = 0; i < args.length; i++) actuals[i] = exp.isArticlesOpened(args[i]);
        Assert.assertArrayEquals(expects, actuals);
    }

    @Test
    public void isWorkoutsEmptyOnBackPressTest() {
        int[][] args = {
                { 0, 0 }, { 0, Integer.MIN_VALUE }, { Integer.MAX_VALUE, 0 },
                { Integer.MIN_VALUE, Integer.MIN_VALUE }, { Integer.MAX_VALUE, Integer.MAX_VALUE }
        };
        boolean[] expects = { true, false, false, false, false };
        boolean[] actuals = new boolean[args.length];
        for (int i = 0; i < args.length; i++)
            actuals[i] = exp.isWorkoutsEmptyOnBackPress(args[i][0], args[i][1]);
        Assert.assertArrayEquals(expects, actuals);
    }

    @Test
    public void isBackStackHasEntriesTest() {
        int[] args = { 0, 1, 100, -1, -100, Integer.MIN_VALUE, Integer.MAX_VALUE };
        boolean[] expects = { false, false, true, false, false, false, true };
        boolean[] actuals = new boolean[args.length];
        for (int i = 0; i < args.length; i++) actuals[i] = exp.isBackStackHasEntries(args[i]);
        Assert.assertArrayEquals(expects, actuals);
    }
}