package almoder.space.fitnesstrainer.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;

import almoder.space.fitnesstrainer.Exercise;
import almoder.space.fitnesstrainer.R;
import almoder.space.fitnesstrainer.RVEAdapter;
import almoder.space.fitnesstrainer.SharedPreferencer;

public class ExercisesFragment extends Fragment implements RVEAdapter.OnItemClickListener {

    public interface ExercisesFragmentListener {
        void exItemClicked(int id);
    }

    private ExercisesFragmentListener listener;
    private PagedList<Exercise> pagedList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("WrongThread")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        RVEAdapter.ExerciseDataSource dataSource = new RVEAdapter.ExerciseDataSource(new Exercise(view.getContext()));
        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(30)
                .setEnablePlaceholders(false)
                .setPrefetchDistance(30)
                .setPageSize(10)
                .build();
        pagedList = new PagedList.Builder<>(dataSource, config)
                .setNotifyExecutor(new RVEAdapter.MainThreadExecutor())
                .setFetchExecutor(Executors.newFixedThreadPool(4))
                .build();
        RVEAdapter adapter = new RVEAdapter(new RVEAdapter.ExerciseDiffUtilCallback(), this);
        adapter.submitList(pagedList);
        adapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (ExercisesFragmentListener)context;
    }

    @Override
    public void onItemClicked(int position) {
        if (listener != null && pagedList.get(position) != null) {
            listener.exItemClicked(Objects.requireNonNull(pagedList.get(position)).num());
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
                Toast.makeText(getContext(), "Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sort:
                Toast.makeText(getContext(), "Sort", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
