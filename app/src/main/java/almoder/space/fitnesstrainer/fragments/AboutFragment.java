package almoder.space.fitnesstrainer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import almoder.space.fitnesstrainer.R;

public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        String[] stringArray = view.getResources().getStringArray(R.array.about_text);
        TextView textView = view.findViewById(R.id.about_text);
        for (String s : stringArray) {
            textView.append(s);
            textView.append("\n");
        }
        return view;
    }
}
