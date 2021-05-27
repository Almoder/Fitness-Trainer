package almoder.space.fitnesstrainer.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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
import almoder.space.fitnesstrainer.RVEAdapter;
import almoder.space.fitnesstrainer.SharedPreferencer;
import almoder.space.fitnesstrainer.WktData;

public class WktDescFragment extends Fragment implements RVEAdapter.OnItemClickListener {

    private int id, position = 0;
    private WktData wkt = null;
    private PagedList<Exercise> pagedList;
    private ExercisesFragment.ExercisesFragmentListener listener;

    public WktDescFragment() { this.id = -1; position = 0; }

    public WktDescFragment(int id) {
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
        View view = inflater.inflate(R.layout.fragment_wkt_desc, container, false);

        if (sis != null) id = sis.getInt("id", -1);
        if (id != -1) {
            wkt = new SharedPreferencer(view.getContext()).loadWorkout(id);
            TextView textView = view.findViewById(R.id.wkt_desc_exes_count);
            textView.setText(getString(R.string.exs_count) + " " + wkt.count());
            RVEAdapter.ExerciseDataSource dataSource = new RVEAdapter.ExerciseDataSource(wkt.exercises());
            PagedList.Config config = new PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setPageSize(4)
                    .build();
            if (sis != null) position = sis.getInt("pos", 0);
            pagedList = new PagedList.Builder<>(dataSource, config)
                    .setNotifyExecutor(new RVEAdapter.MainThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .setInitialKey(position)
                    .build();
            RVEAdapter adapter = new RVEAdapter(new RVEAdapter.ExerciseDiffUtilCallback(), this);
            adapter.submitList(pagedList);
            adapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(adapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    position = ((LinearLayoutManager) Objects.requireNonNull(
                            recyclerView.getLayoutManager())).findFirstVisibleItemPosition();
                }
            });
            Log.d("TAG", "wktId: " + id);
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (ExercisesFragment.ExercisesFragmentListener)context;
    }

    @Override
    public void onPause() {
        super.onPause();
        onSaveInstanceState(new Bundle());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos", position);
        outState.putInt("id", id);
    }

    @Override
    public void onItemClicked(int position) {
        if (listener != null && pagedList.get(position) != null)
            listener.exItemClicked(position, wkt.exercises().get(position));
        else Toast.makeText(getContext(), "Pos: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.wkt_desc_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
