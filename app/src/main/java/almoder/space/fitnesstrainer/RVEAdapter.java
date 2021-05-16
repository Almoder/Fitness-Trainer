package almoder.space.fitnesstrainer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class RVEAdapter extends RecyclerView.Adapter<RVEAdapter.ContentViewHolder> {

    private final OnItemClickListener itemClickListener;

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

    LinkedList<Exercise> content;

    public RVEAdapter(LinkedList<Exercise> content, OnItemClickListener itemClickListener) {
        this.content = content;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
        return new ContentViewHolder(v, itemClickListener);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder cvh, int i) {
        cvh.num = content.get(i).num();
        cvh.cvTitle.setText(content.get(i).title());
        cvh.cvType.setText(content.get(i).type());
        cvh.cvImage.setImageResource(content.get(i).imgRes1());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView rv) {
        super.onAttachedToRecyclerView(rv);
    }
}
