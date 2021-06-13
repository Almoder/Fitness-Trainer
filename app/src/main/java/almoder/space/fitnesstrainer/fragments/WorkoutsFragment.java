package almoder.space.fitnesstrainer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.RVWAdapter;
import almoder.space.fitnesstrainer.SharedPreferencer;

import static almoder.space.fitnesstrainer.fragments.Expressions.thereIsNoWorkouts;

public class WorkoutsFragment extends Fragment implements RVWAdapter.OnItemClickListener {

    public interface WorkoutsFragmentListener {
        void wkItemClicked(int id, String title);
    }

    private boolean edit = false;
    private RVWAdapter adapter;
    private SharedPreferencer sp;
    private WorkoutsFragment.WorkoutsFragmentListener listener;
    private MenuItem editItem = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle sis) {
        super.onCreateView(inflater, container, sis);
        View view = inflater.inflate(R.layout.fragment_workouts, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        sp = new SharedPreferencer(view.getContext());
        sp.loadWorkouts();
        adapter = new RVWAdapter(this, sp.workouts, getString(R.string.exs_count));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (WorkoutsFragment.WorkoutsFragmentListener)context;
    }

    @Override
    public void onResume() {
        super.onResume();
        edit = false;
    }

    @Override
    public void onItemClicked(int position) {
        if (edit) {
            sp.removeWorkout(position);
            adapter.notifyDataSetChanged();
            if (thereIsNoWorkouts(sp.workouts.size())) onOptionsItemSelected(editItem);
        }
        else listener.wkItemClicked(position, sp.workouts.get(position).title());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.wkt_desc_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        edit = !edit;
        editItem = item;
        item.setIcon(edit ? R.drawable.ic_done : R.drawable.ic_menu_edit);
        adapter.editMode();
        return true;
    }
}
