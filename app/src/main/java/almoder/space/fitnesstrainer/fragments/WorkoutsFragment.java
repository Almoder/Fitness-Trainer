package almoder.space.fitnesstrainer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.RVWAdapter;
import almoder.space.fitnesstrainer.WktData;

public class WorkoutsFragment extends Fragment implements RVWAdapter.OnItemClickListener {

    public interface WorkoutsFragmentListener {
        void wkItemClicked(int id);
    }

    private WorkoutsFragment.WorkoutsFragmentListener listener;
    private final LinkedList<WktData> list = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        if (list.isEmpty()) {
            list.add(new WktData("Test workout1"));
            list.add(new WktData("Test workout2"));
            list.add(new WktData("Test workout3"));
            list.add(new WktData("Test workout4"));
            list.add(new WktData("Test workout5"));
            list.add(new WktData("Test workout6"));
            list.add(new WktData("Test workout7"));
            list.add(new WktData("Test workout8"));
            list.add(new WktData("Test workout9"));
            list.add(new WktData("Test workout10"));
        }
        recyclerView.setAdapter(new RVWAdapter(list, this));
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (WorkoutsFragment.WorkoutsFragmentListener)context;
    }

    @Override
    public void onItemClicked(int position) {
        listener.wkItemClicked(position);
    }
}
