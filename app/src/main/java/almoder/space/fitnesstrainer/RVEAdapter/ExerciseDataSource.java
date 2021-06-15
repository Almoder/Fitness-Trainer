package almoder.space.fitnesstrainer.RVEAdapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.LinkedList;
import java.util.List;

import almoder.space.fitnesstrainer.Exercise;

public class ExerciseDataSource extends PositionalDataSource<Exercise> {

    public Exercise storage;
    public List<Exercise> store = null;

    public ExerciseDataSource(Exercise storage) {
        this.storage = storage;
    }

    public ExerciseDataSource(List<Exercise> store) {
        this.store = store;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params,
                            @NonNull LoadInitialCallback<Exercise> callback) {
        Log.d("TAG", "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        if (storage != null) {
            List<Exercise> result = storage.getData(
                    params.requestedStartPosition, params.requestedLoadSize);
            callback.onResult(result, params.requestedStartPosition);
        }
        else if (store != null && store.size() != 0){
            int sp = params.requestedStartPosition, loadSize = params.requestedLoadSize;
            List<Exercise> result = new LinkedList<>();
            if (sp >= store.size()) {
                callback.onResult(result, params.requestedStartPosition);
                return;
            }
            else if (sp + loadSize >= store.size()) loadSize = store.size() - sp;
            for (int i = sp; i < loadSize; i++) result.add(store.get(i));
            callback.onResult(result, params.requestedStartPosition);
        }
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params,
                          @NonNull LoadRangeCallback<Exercise> callback) {
        Log.d("TAG", "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        if (storage != null) {
            List<Exercise> result = storage.getData(params.startPosition, params.loadSize);
            callback.onResult(result);
        }
        else if (store != null){
            int sp = params.startPosition, loadSize = params.loadSize;
            List<Exercise> result = new LinkedList<>();
            if (sp >= store.size()) {
                callback.onResult(result);
                return;
            }
            else if (sp + loadSize >= store.size()) loadSize = store.size() - sp;
            for (int i = sp; i < loadSize; i++) result.add(store.get(i));
            callback.onResult(result);
        }
    }
}
