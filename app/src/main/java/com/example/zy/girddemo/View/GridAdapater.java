package com.example.zy.girddemo.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zy.girddemo.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zy on 2015/9/23.
 */
public class GridAdapater extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> itemName;
    private ArrayList<Integer> itemImages;

    public GridAdapater(Context context, ArrayList<String> itemName, ArrayList<Integer> itemImages) {
        this.context = context;
        this.itemName = itemName;
        this.itemImages = itemImages;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemName.size();
    }

    @Override
    public Object getItem(int position) {
        return itemName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewItemTag  viewItemTag;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.nitht_item, null);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.bookCover);
            TextView textView = (TextView) convertView.findViewById(R.id.bookName);
            viewItemTag = new ViewItemTag(imageView, textView);
            convertView.setTag(viewItemTag);
        } else {
            viewItemTag = (ViewItemTag) convertView.getTag();
        }

        viewItemTag.imageview.setBackgroundResource(itemImages.get(position));
        viewItemTag.textView.setText(itemName.get(position));
        return convertView;
    }

    class ViewItemTag {
        protected  ImageView imageview;
        protected  TextView textView;

        public ViewItemTag(ImageView imageview, TextView textView) {
            this.imageview = imageview;
            this.textView = textView;
        }
    }
}
