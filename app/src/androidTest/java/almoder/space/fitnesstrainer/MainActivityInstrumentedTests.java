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
public class MainActivityInstrumentedTests {

    private final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private final Logic logic = new Logic(context);
    //private final Dialogue dialogue = new Dialogue(context);

    @Test(expected = IllegalArgumentException.class)
    public void getDialogByIdExceptionTest1() { logic.getDialogById(-1); }

    @Test(expected = IllegalArgumentException.class)
    public void getDialogByIdExceptionTest2() { new Logic().getDialogById(0); }

    @Test
    public void getArticleIdByType() {
        String[] args = context.getResources().getStringArray(R.array.exc_types);
        int[] expecteds = { 0, 1, 2 };
        int[] actuals = new int[args.length];
        for (int i = 0; i < actuals.length; i++) actuals[i] = logic.getArticleIdByType(args[i]);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getArticleIdByTypeExceptionTest1() { logic.getArticleIdByType("test"); }

    @Test(expected = IllegalArgumentException.class)
    public void getArticleIdByTypeExceptionTest2() { new Logic().getArticleIdByType("test"); }

    @Test
    public void editTextIsVisibleTest() {
        EditText[] args = new EditText[3];
        args[0] = new EditText(context);
        args[0].setVisibility(View.VISIBLE);
        args[1] = new EditText(context);
        args[1].setVisibility(View.INVISIBLE);
        args[2] = new EditText(context);
        args[2].setVisibility(View.GONE);
        boolean[] expecteds = { true, false, false }, actuals = new boolean[args.length];
        for (int i = 0; i < actuals.length; i++) actuals[i] = editTextIsVisible(args[i]);
        Assert.assertArrayEquals(expecteds, actuals);
    }
  
    /*@Test
    public void getDialogByIdTest() {
        int[] args = { 0, 1, 2 };
        Object[] expecteds = {
                dialogue.langDialog().getClass(),
                dialogue.themeDialog().getClass(),
                dialogue.textSizeDialog().getClass()
        };
        Object[] actuals = new Object[args.length];
        for (int i = 0; i < actuals.length; i++)
            actuals[i] = logic.getDialogById(args[i]).getClass();
        Assert.assertArrayEquals(expecteds, actuals);
    }*/
}