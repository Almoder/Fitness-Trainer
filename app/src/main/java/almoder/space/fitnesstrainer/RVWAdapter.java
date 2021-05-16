package almoder.space.fitnesstrainer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

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

    private LinkedList<WktData> content;

    public void addItem(WktData item) {
        content.add(item);
        notifyDataSetChanged();
    }

    public RVWAdapter(OnItemClickListener itemClickListener) {
        content = new LinkedList<>();
        this.mItemClickListener = itemClickListener;
    }

    public RVWAdapter(OnItemClickListener itemClickListener, LinkedList<WktData> content) {
        this(itemClickListener);
        this.content = content;
    }

    @Override
    public int getItemCount() {
        return content == null ? 0 : content.size();
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wkt, viewGroup, false);
        return new ContentViewHolder(v, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder contentViewHolder, int i) {
        contentViewHolder.wktTitle.setText(content.get(i).title());
        contentViewHolder.wktCount.append(" " + content.get(i).count());
        contentViewHolder.wktImage.setImageResource(R.drawable.ic_workouts);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView rv) {
        super.onAttachedToRecyclerView(rv);
    }
}
