package com.example.szymon.ewtk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.szymon.ewtk.Model.Disease;
import com.example.szymon.ewtk.R;

import java.util.List;

/**
 * Created by Szymon on 14.11.2016.
 */

public class ListViewAdapter extends ArrayAdapter<Disease> {

    private Context activity;
    private List<Disease> friendList;

    public ListViewAdapter(Context context, int resource, List<Disease> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.friendList = objects;
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Disease getItem(int position) {
        return friendList.get(position);
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
            convertView = inflater.inflate(R.layout.diseases_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.friendName.setText(getItem(position).getName());
        String firstLetter = String.valueOf(getItem(position).getName().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(getItem(position));
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        holder.imageView.setImageDrawable(drawable);
        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView friendName;

        public ViewHolder(View v) {
            imageView = (ImageView) v.findViewById(R.id.item_list);
            friendName = (TextView) v.findViewById(R.id.text_list);
        }
    }
}
