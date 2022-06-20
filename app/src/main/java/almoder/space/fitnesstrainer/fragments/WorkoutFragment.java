package almoder.space.fitnesstrainer.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;
import java.util.concurrent.Executors;

import almoder.space.fitnesstrainer.Exercise;
import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.RVEAdapter.ExerciseDataSource;
import almoder.space.fitnesstrainer.RVEAdapter.ExerciseDiffUtilCallback;
import almoder.space.fitnesstrainer.RVEAdapter.MainThreadExecutor;
import almoder.space.fitnesstrainer.RVEAdapter.RVEAdapter;
import almoder.space.fitnesstrainer.SharedPreferencer;
import almoder.space.fitnesstrainer.Workout;

public class WorkoutFragment extends Fragment implements RVEAdapter.OnItemClickListener {

    private int id, position = 0;
    private boolean edit = false;
    private Workout wkt = null;
    private ExercisesFragment.ExercisesFragmentListener listener;
    private MenuItem menuItem;
    private View view;

    public WorkoutFragment() {
        this.id = -1; position = 0;
    }

    public WorkoutFragment(int id) {
        this.id = id;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint({"SetTextI18n", "WrongThread"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle sis) {
        view = inflater.inflate(R.layout.fragment_wkt_desc, container, false);

        if (sis != null) {
            id = sis.getInt("id", -1);
        }
        if (id != -1) {
            wkt = new SharedPreferencer(view.getContext()).loadWorkout(id);
            TextView textView = view.findViewById(R.id.wkt_desc_exes_count);
            textView.setText(getString(R.string.exs_count) + " " + wkt.count());
            if (sis != null) {
                position = sis.getInt("pos", 0);
            }
            initRecyclerView();
        }
        return view;
    }

    public void initRecyclerView() {
        SharedPreferencer sp = new SharedPreferencer(view.getContext());
        ExerciseDataSource dataSource = new ExerciseDataSource(
                sp.loadWorkout(id).exercises());
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(4)
                .build();
        PagedList<Exercise> pagedList = new PagedList.Builder<>(dataSource, config)
                .setNotifyExecutor(new MainThreadExecutor())
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setInitialKey(position)
                .build();
        RVEAdapter adapter = new RVEAdapter(new ExerciseDiffUtilCallback(), this);
        adapter.submitList(pagedList);
        RecyclerView rv = view.findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                position = ((LinearLayoutManager) Objects.requireNonNull(
                        recyclerView.getLayoutManager())).findFirstVisibleItemPosition();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (ExercisesFragment.ExercisesFragmentListener)context;
    }

    @Override
    public void onResume() {
        super.onResume();
        edit = false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos", position);
        outState.putInt("id", id);
    }

    @Override
    public void onItemClicked(int position) {
        if (edit) {
            SharedPreferencer sp = new SharedPreferencer(getContext());
            sp.loadWorkouts();
            wkt.exercises().remove(position);
            sp.saveWorkout(wkt, id);
            initRecyclerView();
            String temp = getString(R.string.exs_count) + " " + wkt.count();
            TextView textView = view.findViewById(R.id.wkt_desc_exes_count);
            textView.setText(temp);
            if (wkt.count() == 0) {
                onOptionsItemSelected(menuItem);
            }
        }
        else if (listener != null && wkt.exercises().get(position) != null) {
            listener.exItemClicked(position, wkt.exercises().get(position));
        }
        else {
            Toast.makeText(getContext(), "Pos: " + position, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.wkt_desc_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        edit = !edit;
        menuItem = item;
        item.setIcon(edit ? R.drawable.ic_done : R.drawable.ic_menu_edit);
        return true;
    }
}
