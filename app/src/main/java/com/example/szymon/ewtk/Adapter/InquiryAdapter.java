package com.example.szymon.ewtk.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.szymon.ewtk.Model.Inquiry;
import com.example.szymon.ewtk.R;

import org.w3c.dom.Text;

/**
 * Created by Szymon on 20.10.2016.
 */

public class InquiryAdapter extends RecyclerView.Adapter<InquiryAdapter.ViewHolder> {
    @SuppressWarnings("unused")
    private static final String TAG = ModelAdapter.class.getSimpleName();
    public static final int NAME = 0;
    public static final int DETAILS = 1;
    public static final int NEEDMENTS = 2;
    private final static int FADE_DURATION = 500;
    private int mDataSetTypes[] = {NAME, DETAILS, NEEDMENTS};

    private Inquiry inquiry_;
    private Context context_;

    public InquiryAdapter(Inquiry inquiry, Context context) {
        this.inquiry_ = inquiry;
        this.context_ = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public class NameViewHolder extends ViewHolder {
        TextView name;
        TextView isActive;
        TextView description;

        public NameViewHolder(View v) {
            super(v);
            this.name = (TextView) v.findViewById(R.id.name);
            this.isActive = (TextView) v.findViewById(R.id.isActive);
            this.description = (TextView) v.findViewById(R.id.description);
        }
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public class DetailsViewHolder extends ViewHolder {
        TextView startDate;
        TextView endDate;
        TextView singUpDate;
        TextView endSingUpDate;

        public DetailsViewHolder(View v) {
            super(v);
            this.startDate = (TextView) v.findViewById(R.id.startDate);
            this.endDate = (TextView) v.findViewById(R.id.endDate);
            this.singUpDate = (TextView) v.findViewById(R.id.singUpDate);
            this.endSingUpDate = (TextView) v.findViewById(R.id.endSingUpDate);
        }
    }

    public class NeedmentsViewHolder extends ViewHolder {
        TextView age;
        TextView weight;
        TextView width;
        TextView blood;

        public NeedmentsViewHolder(View v) {
            super(v);
            this.age = (TextView) v.findViewById(R.id.age);
            this.weight = (TextView) v.findViewById(R.id.weight);
            this.width = (TextView) v.findViewById(R.id.width);
            this.blood = (TextView) v.findViewById(R.id.blood);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        if (viewType == NAME) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.inquiry_card, viewGroup, false);
            return new NameViewHolder(v);
        } else if (viewType == DETAILS) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.inqdetails_card, viewGroup, false);
            return new DetailsViewHolder(v);
        } else {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.inqneedments_card, viewGroup, false);
            return new NeedmentsViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (viewHolder.getItemViewType() == NAME) {
            NameViewHolder holder = (NameViewHolder) viewHolder;
            holder.name.setText(inquiry_.getName());
            holder.description.setText(inquiry_.getDescription());
            if (inquiry_.getIsActive() != 0)
                holder.isActive.setText(getString(R.string.active_research));
            else
                holder.isActive.setText(getString(R.string.disable_research));
            setScaleAnimation(holder.itemView);
        } else if (viewHolder.getItemViewType() == DETAILS) {
            DetailsViewHolder holder = (DetailsViewHolder) viewHolder;
            holder.startDate.setText(getString(R.string.data_start) + ": " + replaceData(inquiry_.getStartDate()));
            holder.endDate.setText(getString(R.string.data_end) + ": " + replaceData(inquiry_.getEndDate()));
            holder.singUpDate.setText(getString(R.string.data_singUp) + ": " + replaceData(inquiry_.getStartSingUpDate()));
            holder.endSingUpDate.setText(getString(R.string.data_endSingUp) + ": " + replaceData(inquiry_.getEndSingUpDate()));
            setScaleAnimation(holder.itemView);
        } else {
            NeedmentsViewHolder holder = (NeedmentsViewHolder) viewHolder;
            holder.age.setText(getString(R.string.age) + ": " + getString(R.string.from) + " " + inquiry_.getAgeFrom() + " " + getString(R.string.to) + " " + inquiry_.getAgeTo());
            holder.width.setText(getString(R.string.growth) + ": " + getString(R.string.from) + " " + inquiry_.getWidthFrom() + " " + getString(R.string.to) + " " + inquiry_.getWidthTo());
            holder.weight.setText(getString(R.string.weight) + ": " + getString(R.string.from) + " " + inquiry_.getWeightFrom() + " " + getString(R.string.to) + " " + inquiry_.getWeightTo());
            holder.blood.setText(getString(R.string.blood) + ": " + inquiry_.getBloodType());
            setScaleAnimation(holder.itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSetTypes.length;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSetTypes[position];
    }

    private String getString(int value) {
        String text = context_.getResources().getString(value);
        return text;
    }

    private String replaceData(String data) {
        if (data != null) {
            StringBuilder newData = new StringBuilder(data);
            for (int i = 0; i < data.length(); i++) {
                if (newData.charAt(i) == 'T' || newData.charAt(i) == 'Z')
                    newData.setCharAt(i, ' ');
                else if (newData.charAt(i) == '/')
                    newData.setCharAt(i, '-');
                else if (i < 18)
                    newData.setCharAt(i, data.charAt(i));
                else if (i > 18)
                    newData.setCharAt(i, ' ');
            }
            return newData.toString();
        } else
            return data;
    }
}
