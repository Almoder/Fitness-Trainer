package almoder.space.fitnesstrainer.RVEAdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import almoder.space.fitnesstrainer.R;

public class ExerciseViewHolder extends RecyclerView.ViewHolder
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
