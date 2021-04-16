package almoder.space.fitnesstrainer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ContentViewHolder> {

    private final OnItemClickListener mItemClickListener;

    public interface OnItemClickListener{
        void onItemClicked(int position);
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        TextView cvTitle, cvType;
        ImageView cvImage;
        OnItemClickListener cvClickListener;

        public ContentViewHolder(View itemView, OnItemClickListener clickListener) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
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

    List<RowData> mCardContents;

    public RVAdapter(List<RowData> mCardContents, OnItemClickListener itemClickListener) {
        this.mCardContents = mCardContents;
        this.mItemClickListener = itemClickListener;

    }

    @Override
    public int getItemCount() {
        return mCardContents.size();
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
        return new ContentViewHolder(v, mItemClickListener);
    }
    @Override
    public void onBindViewHolder(ContentViewHolder contentViewHolder, int i) {
        contentViewHolder.cvTitle.setText(mCardContents.get(i).title());
        contentViewHolder.cvType.setText(mCardContents.get(i).type());
        contentViewHolder.cvImage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        contentViewHolder.cvImage.setImageResource(mCardContents.get(i).imgRes());

    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView rv) {
        super.onAttachedToRecyclerView(rv);
    }
}
