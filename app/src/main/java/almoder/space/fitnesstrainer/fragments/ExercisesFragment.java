package almoder.space.fitnesstrainer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.Objects;

import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.RVAdapter;
import almoder.space.fitnesstrainer.RowData;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class ExercisesFragment extends Fragment implements RVAdapter.OnItemClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ArrayList<RowData> arrayList = new ArrayList<>();
        for (int i = 1; i < 31; i++) arrayList.add(new RowData(view.getContext(), i));
        recyclerView.setAdapter(new RVAdapter(arrayList, this));
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getContext(), "Pos: " + position, Toast.LENGTH_SHORT).show();
    }
}
