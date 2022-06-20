package almoder.space.fitnesstrainer;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import almoder.space.fitnesstrainer.MainActivity.Logic;
import static almoder.space.fitnesstrainer.MainActivity.Expressions.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityLogicAndroidTests {

    private final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private final Logic logic = new Logic(context);

    //  getTitleResById
    @Test
    public void getTitleResByIdTest() {
        int[] args = new int[] {
                R.id.nav_exercise, R.id.nav_workouts, R.id.nav_articles,
                R.id.nav_settings, R.id.nav_aboutapp, -1
        };
        int[] expecteds = new int[] {
                R.string.m1, R.string.m2, R.string.m3,
                R.string.m4, R.string.m6, R.string.undefined
        };
        int[] actuals = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            actuals[i] = logic.getTitleResById(args[i]);
        }
        Assert.assertArrayEquals(expecteds, actuals);
    }

    //  getItemIdByTitleRes
    @Test
    public void getItemIdByTitleResTest() {
        int[] args = new int[] {
                R.string.m1, R.string.m2,
                R.string.m3, R.string.m4,
                R.string.m5, R.string.m6,
                R.string.app_name, -1
        };
        int[] expecteds = new int[] {
                R.id.nav_exercise, R.id.nav_workouts,
                R.id.nav_articles, R.id.nav_settings,
                R.id.nav_share, R.id.nav_aboutapp,
                R.id.nav_aboutapp, R.id.nav_workouts
        };
        int[] actuals = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            actuals[i] = logic.getItemIdByTitleRes(args[i]);
        }
        Assert.assertArrayEquals(expecteds, actuals);
    }

    //  getDialogById
    @Test(expected = IllegalArgumentException.class)
    public void getDialogByIdExceptionTest1() {
        logic.getDialogById(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDialogByIdExceptionTest2() {
        new Logic(null).getDialogById(0);
    }

    //  getArticleIdByType
    @Test
    public void getArticleIdByType() {
        String[] args = context.getResources()
                .getStringArray(R.array.exc_types);
        int[] expecteds = { 0, 1, 2 };
        int[] actuals = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            actuals[i] = logic.getArticleIdByType(args[i]);
        }
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getArticleIdByTypeExceptionTest1() {
        logic.getArticleIdByType("test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getArticleIdByTypeExceptionTest2() {
        new Logic(null).getArticleIdByType("test");
    }
}