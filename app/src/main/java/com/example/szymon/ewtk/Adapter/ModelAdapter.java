package com.example.szymon.ewtk.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.szymon.ewtk.Model.Model;
import com.example.szymon.ewtk.R;

/**
 * Created by Szymon on 04.10.2016.
 */

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {
    @SuppressWarnings("unused")
    private static final String TAG = ModelAdapter.class.getSimpleName();
    private ViewHolder.ClickListener clickListener;
    private final static int FADE_DURATION = 500;

    private List<Model> models;

    public ModelAdapter(ViewHolder.ClickListener clickListener) {
        super();
        this.clickListener = clickListener;
    }

    public void setModelList(List<Model> modelList) {
        this.models = modelList;
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, parent, false);
        return new ViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Model model = models.get(position);

        holder.current.setText(String.valueOf(position+1+"/"+getItemCount()));
        holder.name.setText(model.getName());

        String firstLetter = String.valueOf(model.getName().charAt(0));

        if(model.getModelExecution().size()>0){
            holder.fireImage.setVisibility(View.VISIBLE);
            holder.fireImage.setImageResource(R.drawable.ic_fire);
            holder.executionCount.setVisibility(View.VISIBLE);
            holder.executionCount.setText(String.valueOf(model.getModelExecution().size()));
        }

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(model.getID());
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        holder.modelImage.setImageDrawable(drawable);

        setScaleAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @SuppressWarnings("unused")
        private static final String TAG = ViewHolder.class.getSimpleName();
        private ClickListener clickListener;

        TextView current;
        TextView name;
        TextView executionCount;
        ImageView fireImage;
        ImageView modelImage;
        CardView itemCard;

        public ViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);

            modelImage = (ImageView) itemView.findViewById(R.id.modelImage);
            fireImage = (ImageView) itemView.findViewById(R.id.fireImage);
            current = (TextView) itemView.findViewById(R.id.model_count);
            name = (TextView) itemView.findViewById(R.id.modelName);
            executionCount = (TextView) itemView.findViewById(R.id.execCount);
            itemCard = (CardView) itemView.findViewById(R.id.model_card);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClicked(getPosition());
        }

        public interface ClickListener {
            public void onItemClicked(int position);
        }

    }
}
