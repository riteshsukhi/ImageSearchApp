package com.codepath.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by rsukhi on 10/19/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {
    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result , images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageinfo = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result,parent, false);
        }
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        ivImage.setImageResource(0);
        //Populate the title and remote download  image url
        tvTitle.setText(Html.fromHtml(imageinfo.title));
        //Remotely download the image data in background(with Picasso)
        Picasso.with(getContext()).load(imageinfo.thumbUrl).into(ivImage);
        return convertView;

    }
}
