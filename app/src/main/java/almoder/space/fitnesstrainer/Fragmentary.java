package almoder.space.fitnesstrainer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

public class Fragmentary {

    private final FragmentManager manager;
    private int titleResId = R.string.undefined;
    private String title = "Undefined";

    public Fragmentary(FragmentManager manager) {
        this.manager = manager;
    }

    public void replace(Fragment fragment, int title) {
        if (manager == null) {
            return;
        }
        titleResId(title);
        manager.beginTransaction().replace(R.id.container, fragment)
            .addToBackStack(String.valueOf(titleResId())).commit();
    }

    public void replace(Fragment fragment, String title) {
        if (manager == null) {
            return;
        }
        title(title);
        manager.beginTransaction().replace(R.id.container, fragment)
                .addToBackStack(title()).commit();
    }

    public boolean popBackStack() {
        if (manager == null) {
            return false;
        }
        manager.popBackStack();
        String temp = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 2).getName();
        if (temp != null && temp.toCharArray()[0] == '_') {
            title(temp.substring(1));
            return true;
        }
        else {
            titleResId(Integer.parseInt(Objects.requireNonNull(temp)));
            return false;
        }
    }

    public void title(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }

    public void titleResId(int titleResId) {
        this.titleResId = titleResId;
    }

    public int titleResId() {
        return titleResId;
    }
}
