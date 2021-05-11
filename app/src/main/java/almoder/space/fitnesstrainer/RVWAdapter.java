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

public class RVWAdapter extends RecyclerView.Adapter<RVWAdapter.ContentViewHolder> {

    private final OnItemClickListener mItemClickListener;

    public interface OnItemClickListener{
        void onItemClicked(int position);
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView wktTitle, wktCount;
        ImageView wktImage;
        OnItemClickListener cvClickListener;

        public ContentViewHolder(View itemView, OnItemClickListener clickListener) {
            super(itemView);
            wktTitle = itemView.findViewById(R.id.wkt_title);
            wktCount = itemView.findViewById(R.id.wkt_count);
            wktImage = itemView.findViewById(R.id.wkt_image);
            cvClickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cvClickListener.onItemClicked(getLayoutPosition());
        }
    }

    List<WktData> mCardContents;

    public RVWAdapter(List<WktData> mCardContents, OnItemClickListener itemClickListener) {
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wkt, viewGroup, false);
        return new ContentViewHolder(v, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder contentViewHolder, int i) {
        contentViewHolder.wktTitle.setText(mCardContents.get(i).title());
        contentViewHolder.wktCount.append(" " + mCardContents.get(i).count());
        contentViewHolder.wktImage.setImageResource(R.drawable.ic_workouts);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView rv) {
        super.onAttachedToRecyclerView(rv);
    }
}
