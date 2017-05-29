package com.example.android.favoriteplaces.Places;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.android.favoriteplaces.R;

import java.util.List;

/**
 * Created by arturs.amirovs on 05/05/17.
 */

public class PlaceListAdapter extends BaseAdapter implements ListAdapter {
    Context listContext;
    private static LayoutInflater layoutInflater;
    private List<Place> names;

    public PlaceListAdapter(Context listContext, List<Place> names){
        this.names = names;
        this.listContext = listContext;
        layoutInflater = (LayoutInflater) listContext.getSystemService(listContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder{
        private TextView textView;
        private TextView textViewStatus;
        private TextView textViewRating;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.place_list_item, null, true);
            holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.textViewRVPlace);
            holder.textViewStatus = (TextView) view.findViewById(R.id.statusTextView);
            holder.textViewRating = (TextView) view.findViewById(R.id.ratingTextView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.textView.setText(names.get(position).getPlace());
        holder.textViewStatus.setText(names.get(position).getOpenNow());
        if(names.get(position).getOpenNow().equals("Open")) holder.textViewStatus.setTextColor(Color.GREEN);
        else holder.textViewStatus.setTextColor(Color.RED);

        holder.textViewRating.setText(names.get(position).getRating());

        return view;
    }
}
