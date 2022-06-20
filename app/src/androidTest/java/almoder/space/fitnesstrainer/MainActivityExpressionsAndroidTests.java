package almoder.space.fitnesstrainer;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

import static almoder.space.fitnesstrainer.MainActivity.Expressions.editTextIsVisible;

public class MainActivityExpressionsAndroidTests {

    private final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    //  editTextIsVisible
    @Test
    public void editTextIsVisibleTest() {
        EditText[] args = new EditText[3];
        args[0] = new EditText(context);
        args[0].setVisibility(View.VISIBLE);
        args[1] = new EditText(context);
        args[1].setVisibility(View.INVISIBLE);
        args[2] = new EditText(context);
        args[2].setVisibility(View.GONE);
        boolean[] expecteds = { true, false, false },
                  actuals = new boolean[args.length];
        for (int i = 0; i < args.length; i++) {
            actuals[i] = editTextIsVisible(args[i]);
        }
        Assert.assertArrayEquals(expecteds, actuals);
    }
}
