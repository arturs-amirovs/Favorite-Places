package com.example.android.favoriteplaces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.android.favoriteplaces.Categories.Category;

import java.util.List;

import static android.view.View.GONE;

public class MyListAdapter extends BaseAdapter implements ListAdapter {

    Context listContext;
    private static LayoutInflater layoutInflater;
    private List<Category> names;

    public MyListAdapter(Context listContext, List<Category> names){
        this.names = names;
        this.listContext = listContext;
        layoutInflater = (LayoutInflater) listContext.getSystemService(listContext.LAYOUT_INFLATER_SERVICE);
    }

    public void addCat(Category c){
        names.add(c);
        notifyDataSetChanged();
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
        private ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.list_item, null, true);
            holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.textViewRV);
            holder.imageView = (ImageView) view.findViewById(R.id.categoryIMG);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.textView.setText(names.get(position).get_categoryname());
        if(names.get(position).getImg() != 0) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageResource(names.get(position).getImg());
        } else {
            holder.imageView.setVisibility(GONE);
        }

        return view;
    }
}
