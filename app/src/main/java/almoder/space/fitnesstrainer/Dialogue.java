package almoder.space.fitnesstrainer;

import android.app.AlertDialog;
import android.content.Context;

public class Dialogue {

    private final Context c;
    private final SharedPreferencer sp;

    public Dialogue(Context c) { this.c = c; sp = new SharedPreferencer(c); }

    public AlertDialog langDialog() {
        return new AlertDialog.Builder(c).setTitle(R.string.lang)
                .setItems(R.array.lang_entries, (di, i) -> {
                    String locale = c.getResources().getStringArray(R.array.lang_values)[i];
                    if (!locale.equals(sp.locale())) sp.locale(locale);
                    di.dismiss(); }).create();
    }

    public AlertDialog themeDialog() {
        return new AlertDialog.Builder(c).setTitle(R.string.theme)
                .setItems(R.array.theme_entries, (di, i) -> {
                    if (i != sp.themeId()) sp.theme(i, sp.textSizeId());
                    di.dismiss(); }).create();
    }

    public AlertDialog textSizeDialog() {
        return new AlertDialog.Builder(c).setTitle(R.string.text_size)
                .setItems(R.array.text_size_entries, (di, i) -> {
                    if (i != sp.textSizeId()) sp.theme(sp.themeId(), i);
                    di.dismiss(); }).create();
    }

}
