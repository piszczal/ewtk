package com.example.szymon.ewtk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.szymon.ewtk.Model.Answer;
import com.example.szymon.ewtk.R;

import java.util.List;

/**
 * Created by Szymon on 27.11.2016.
 */

public class AnswerAdapter extends ArrayAdapter<Answer> {

    private Context activity;
    private List<Answer> answerList;

    public AnswerAdapter(Context context, int resource, List<Answer> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.answerList = objects;
    }

    @Override
    public int getCount() {
        return answerList.size();
    }

    @Override
    public Answer getItem(int position) {
        return answerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.execution_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(getItem(position).getTitle());
        holder.description.setText(getItem(position).getValue());
        String firstLetter = String.valueOf(position + 1);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(getItem(position));
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        holder.imageView.setImageDrawable(drawable);
        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView description;

        public ViewHolder(View v) {
            imageView = (ImageView) v.findViewById(R.id.item_execution);
            title = (TextView) v.findViewById(R.id.title);
            description = (TextView) v.findViewById(R.id.description);
        }
    }
}
