package com.foozenergy.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        //check if the view is null, otherwise inflate the layout
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_container,parent,false);
        }

        News news = getItem(position);

        //find a reference to the image view and add an image
        ImageView imageView = convertView.findViewById(R.id.image_view);
        Glide.with(getContext()).load(news.getImage()).into(imageView);

        //find a reference to the title text view and set the title
        TextView title = convertView.findViewById(R.id.news_tittle);
        title.setText(news.getTitle());

        //find a reference to th category text view and set the category name
        TextView category = convertView.findViewById(R.id.news_category);
        category.setText(news.getCategory());

        return convertView;
    }
}
