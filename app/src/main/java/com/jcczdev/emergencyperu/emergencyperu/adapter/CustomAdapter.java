package com.jcczdev.emergencyperu.emergencyperu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcczdev.emergencyperu.emergencyperu.MainActivity;
import com.jcczdev.emergencyperu.emergencyperu.R;

/**
 * Created by Juan on 19/02/2018.
 */

public class CustomAdapter extends BaseAdapter {

    String[] result;
    Context context;
    int[] imageId;
    private static LayoutInflater inflater = null;

    public CustomAdapter(MainActivity mainActivity, String[] osNameList, int[] osImages) {
        result = osNameList;
        context = mainActivity;
        imageId = osImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder {
        TextView itemText;
        ImageView itemImage;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.sample_gridlayout, null);
        holder.itemText =(TextView) rowView.findViewById(R.id.os_texts);
        holder.itemImage =(ImageView) rowView.findViewById(R.id.os_images);

        holder.itemText.setText(result[position]);
        holder.itemImage.setImageResource(imageId[position]);

        return rowView;
    }
}
