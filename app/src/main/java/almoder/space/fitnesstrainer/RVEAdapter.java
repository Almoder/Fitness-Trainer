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

public class RVEAdapter extends RecyclerView.Adapter<RVEAdapter.ContentViewHolder> {

    private final OnItemClickListener mItemClickListener;

    public interface OnItemClickListener{
        void onItemClicked(int position);
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        int num;
        TextView cvTitle, cvType;
        ImageView cvImage;
        OnItemClickListener cvClickListener;

        public ContentViewHolder(View itemView, OnItemClickListener clickListener) {
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

    List<RowData> mCardContents;

    public RVEAdapter(List<RowData> mCardContents, OnItemClickListener itemClickListener) {
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
    public void onBindViewHolder(ContentViewHolder cvh, int i) {
        cvh.num = mCardContents.get(i).num();
        cvh.cvTitle.setText(mCardContents.get(i).title());
        cvh.cvType.setText(mCardContents.get(i).type());
        cvh.cvImage.setImageResource(mCardContents.get(i).imgRes());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView rv) {
        super.onAttachedToRecyclerView(rv);
    }
}
