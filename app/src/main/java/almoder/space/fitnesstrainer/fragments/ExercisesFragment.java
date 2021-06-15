package almoder.space.fitnesstrainer.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;
import java.util.concurrent.Executors;

import almoder.space.fitnesstrainer.Exercise;
import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.RVEAdapter;

public class ExercisesFragment extends Fragment implements RVEAdapter.OnItemClickListener {

    public interface ExercisesFragmentListener {
        void exItemClicked(int id, Exercise exc);
    }

    private ExercisesFragmentListener listener;
    private PagedList<Exercise> pagedList;
    private int position = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongThread")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle sis) {
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        RVEAdapter.ExerciseDataSource dataSource = new RVEAdapter.ExerciseDataSource(new Exercise(view.getContext()));
        if (sis != null) position = sis.getInt("pos", 0);
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(12)
                .setPrefetchDistance(16)
                .setPageSize(4)
                .build();
        pagedList = new PagedList.Builder<>(dataSource, config)
                .setNotifyExecutor(new RVEAdapter.MainThreadExecutor())
                .setFetchExecutor(Executors.newWorkStealingPool())
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
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (ExercisesFragmentListener)context;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos", position);
    }

    @Override
    public void onItemClicked(int position) {
        if (listener != null && pagedList.get(position) != null) {
            listener.exItemClicked(Objects.requireNonNull(
                    pagedList.get(position)).num(), null);
        }
        else Toast.makeText(getContext(), "Pos: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.exersises_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(getContext(), R.string.search, Toast.LENGTH_SHORT).show();
                break;
            case R.id.sort:
                Toast.makeText(getContext(), R.string.sort_az, Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
