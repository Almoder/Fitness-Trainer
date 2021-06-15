package almoder.space.fitnesstrainer.RVEAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import almoder.space.fitnesstrainer.Exercise;

public class ExerciseDiffUtilCallback extends DiffUtil.ItemCallback<Exercise> {

    @Override
    public boolean areItemsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
        return oldItem.num() == newItem.num();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
        return oldItem.title().equals(newItem.title()) && oldItem.type().equals(newItem.type());
    }
}