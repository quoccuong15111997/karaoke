package com.nqc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nqc.model.Sing;
import com.nqc.tracuukaraoke.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SingAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Sing> singList;

    public SingAdapter(Context context, int layout, List<Sing> singList) {
        this.context = context;
        this.layout = layout;
        this.singList = singList;
    }

    @Override
    public int getCount() {
        return singList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        ImageView imgThumbnail;
        TextView txtTitle;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            holder.imgThumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Sing sing = singList.get(i);

        holder.txtTitle.setText(sing.getTitle());

        Picasso.with(context).load(sing.getThumbnail()).into(holder.imgThumbnail);


        return view;
    }
}
