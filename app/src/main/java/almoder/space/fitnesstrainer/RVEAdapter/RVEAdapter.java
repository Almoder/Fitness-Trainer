package almoder.space.fitnesstrainer.RVEAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

import almoder.space.fitnesstrainer.Exercise;
import almoder.space.fitnesstrainer.R;

public class RVEAdapter extends PagedListAdapter<Exercise, ExerciseViewHolder> {

    private final OnItemClickListener listener;

    public RVEAdapter(DiffUtil.ItemCallback<Exercise> diffUtilCallback, OnItemClickListener listener) {
        super(diffUtilCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ExerciseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        if (getItem(position) == null) {
            holder.num = 0;
            holder.cvTitle.setText(R.string.empty);
            holder.cvType.setText(R.string.empty);
            holder.cvImage.setImageResource(R.drawable.ic_empty);
        }
        else {
            holder.num = Objects.requireNonNull(getItem(position)).num();
            holder.cvTitle.setText(Objects.requireNonNull(getItem(position)).title());
            holder.cvType.setText(Objects.requireNonNull(getItem(position)).type());
            holder.cvImage.setImageResource(Objects.requireNonNull(getItem(position)).imgRes1());
        }
    }

    public interface OnItemClickListener{ void onItemClicked(int position); }
}
