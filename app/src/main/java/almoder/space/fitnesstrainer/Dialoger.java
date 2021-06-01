package almoder.space.fitnesstrainer;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

public class Dialoger {

    private final Context c;
    private String locale;
    private int temp;
    private final int[] themes = { R.style.AppTheme, R.style.DarkTheme };
    private final SharedPreferencer sp;

    public Dialoger(Context c) { this.c = c; sp = new SharedPreferencer(c); }

    public AlertDialog langDialog() {
        return new AlertDialog.Builder(c).setTitle(R.string.lang)
                .setItems(R.array.lang_entries, (di, i) -> {
                    locale = c.getResources().getStringArray(R.array.lang_values)[i];
                    if (!locale.equals(sp.locale())) sp.locale(locale);
                    di.dismiss(); }).create();
    }

    public AlertDialog themeDialog() {
        return new AlertDialog.Builder(c).setTitle(R.string.theme)
                .setItems(R.array.theme_entries, (di, i) -> {
                    temp = themes[i];
                    Log.d("TAG", "temp:" + temp + ",theme:" + sp.theme());
                    if (temp != sp.theme()) sp.theme(temp);
                    di.dismiss(); }).create();
    }

}
