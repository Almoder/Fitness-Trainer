package almoder.space.fitnesstrainer;

import org.junit.Assert;
import org.junit.Test;
import almoder.space.fitnesstrainer.MainActivity.Logic;
import almoder.space.fitnesstrainer.fragments.*;
import static almoder.space.fitnesstrainer.CustomAsserting.*;

public class MainActivityLogicTests {

    private final Logic logic = new Logic(null);

    @Test
    public void getTitleByIdTest() {
        int[] args = {
                R.id.nav_exercise, R.id.nav_workouts, R.id.nav_articles,
                R.id.nav_settings, R.id.nav_aboutapp, -1
        };
        int[] expecteds = {
                R.string.m1, R.string.m2, R.string.m3,
                R.string.m4, R.string.m6, R.string.undefined
        };
        int[] actuals = new int[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getTitleById(args[i]);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void getFragmentByIdTest() {
        int[] args = {
                R.id.nav_exercise, R.id.nav_workouts, R.id.nav_articles,
                R.id.nav_settings, R.id.nav_aboutapp, R.id.nav_share
        };
        Object[] expecteds = {
                ExercisesFragment.class, WorkoutsFragment.class, ArticlesFragment.class,
                SettingsFragment.class, AboutFragment.class, AboutFragment.class
        };
        Object[] actuals = new Object[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getFragmentById(args[i]).getClass();
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void getItemByTitleResTest() {
        int[] args = {
                R.string.m1, R.string.m2, R.string.m3, R.string.m4,
                R.string.m5, R.string.m6, R.string.app_name, R.string.undefined
        };
        int[] expecteds = {
                R.id.nav_exercise, R.id.nav_workouts, R.id.nav_articles, R.id.nav_settings,
                R.id.nav_share, R.id.nav_aboutapp, R.id.nav_aboutapp, R.id.nav_workouts
        };
        int[] actuals = new int[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getItemIdByTitleRes(args[i]);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void getOnCreateItemIdTest() {
        boolean[][] args = {{ false, true }, { true, false }, { true, true }};
        int[] expecteds = { R.id.nav_aboutapp, R.id.nav_settings, R.id.nav_settings };
        int[] actuals = new int[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getOnCreateItemId(args[i][0], args[i][1]);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void getOnCreateTitleTest() {
        boolean[][] args = {{ true, true }, { false, true }, { true, false }};
        int[] expecteds = { R.string.m4, R.string.app_name, R.string.m4 };
        int[] actuals = new int[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getOnCreateTitle(args[i][0], args[i][1]);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void getOnCreateFragmentTest() {
        boolean[][] args = {{ false, true }, { true, false }, { true, true }};
        Object[] expecteds = {
                AboutFragment.class, SettingsFragment.class, SettingsFragment.class
        };
        Object[] actuals = new Object[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getOnCreateFragment(args[i][0], args[i][1]).getClass();
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void getOnDescAddClickToastTest1() {
        String[] args = { "", "0", "1" };
        int[] expecteds = { R.string.reps_not_empty, R.string.reps_not_zero, 0 },
                actuals = new int[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getOnDescAddClickToast(args[i]);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void getOnDescAddClickToastTest2() {
        String[][] args = {{ "", "" }, { "0", "0" }, {"", "1"},
                {"0", "1"}, { "1", ""}, { "1", "0" }, { "1", "1" }};
        int[] expecteds = { R.string.reps_weight_not_empty, R.string.reps_weight_not_zero,
                R.string.reps_not_empty, R.string.reps_not_zero,
                R.string.weight_not_empty, R.string.weight_not_zero, 0 },
        actuals = new int[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getOnDescAddClickToast(args[i][0], args[i][1]);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void mainActivityLogicExceptionsTest() {
        Function[] functions1 = {
                () -> logic.getOnCreateItemId(false, false),
                () -> logic.getOnCreateTitle(false, false),
                () -> logic.getOnCreateFragment(false, false)
        }, functions2 = {
                () -> logic.getOnDescAddClickToast(null),
                () -> logic.getOnDescAddClickToast("1", null),
                () -> logic.getOnDescAddClickToast(null, "1"),
                () -> logic.getOnDescAddClickToast(null, null)
        };
        assertArrayOfExceptions(functions1, IllegalArgumentException.class);
        assertArrayOfExceptions(functions2, NullPointerException.class);
    }
}