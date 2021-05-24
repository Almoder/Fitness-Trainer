package almoder.space.fitnesstrainer;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.paging.PositionalDataSource;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public class RVEAdapter extends PagedListAdapter<Exercise, RVEAdapter.ExerciseViewHolder> {

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
            holder.num = getItem(position).num();
            holder.cvTitle.setText(getItem(position).title());
            holder.cvType.setText(getItem(position).type());
            holder.cvImage.setImageResource(getItem(position).imgRes1());
        }
    }

    public interface OnItemClickListener{ void onItemClicked(int position); }

    public static class MainThreadExecutor implements Executor {
        private final Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) { mHandler.post(command); }
    }

    public static class ExerciseDataSource extends PositionalDataSource<Exercise> {

        private final Exercise storage;

        public ExerciseDataSource(Exercise storage) {
            this.storage = storage;
        }

        @Override
        public void loadInitial(@NonNull LoadInitialParams params,
                                @NonNull LoadInitialCallback<Exercise> callback) {
            Log.d("TAG", "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                    ", requestedLoadSize = " + params.requestedLoadSize);
            List<Exercise> result = storage.getData(
                    params.requestedStartPosition, params.requestedLoadSize);
            callback.onResult(result, params.requestedStartPosition);
        }

        @Override
        public void loadRange(@NonNull LoadRangeParams params,
                              @NonNull LoadRangeCallback<Exercise> callback) {
            Log.d("TAG", "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
            List<Exercise> result = storage.getData(params.startPosition, params.loadSize);
            callback.onResult(result);
        }
    }

    public static class ExerciseDiffUtilCallback extends DiffUtil.ItemCallback<Exercise> {

        @Override
        public boolean areItemsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem.num() == newItem.num();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem.title().equals(newItem.title()) && oldItem.type().equals(newItem.type());
        }
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        int num;
        TextView cvTitle, cvType;
        ImageView cvImage;
        RVEAdapter.OnItemClickListener cvClickListener;

        public ExerciseViewHolder(View itemView, RVEAdapter.OnItemClickListener clickListener) {
            super(itemView);
            cvTitle = itemView.findViewById(R.id.row_title);
            cvType = itemView.findViewById(R.id.row_type);
            cvImage = itemView.findViewById(R.id.row_image);
            cvClickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cvClickListener.onItemClicked(getLayoutPosition());
        }
    }
}
