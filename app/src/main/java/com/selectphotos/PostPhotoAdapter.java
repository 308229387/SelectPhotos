package com.selectphotos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by songyongmeng on 2017/3/2.
 * 描    述：显示九宫格缩略图片的适配器
 */
public class PostPhotoAdapter extends RecyclerView.Adapter<PostPhotoAdapter.ViewHolder> {
    private MainActivity context;
    private LayoutInflater inflater;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private DeletePhoto deleteListener;
    private ArrayList<String> images;

    public PostPhotoAdapter(MainActivity context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.post_image_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (position == images.size() - 1) {
            Glide.with(context).load(R.mipmap.ic_launcher).into(holder.image);
            holder.delete.setVisibility(View.GONE);
        }else{
//            Glide.with(context).load(new File(images.get(position))).into(holder.image);
            holder.delete.setVisibility(View.VISIBLE);
        }
        if (position == 0 && images.size() > 1)
            holder.firstText.setVisibility(View.VISIBLE);
        else
            holder.firstText.setVisibility(View.GONE);


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteListener != null)
                    deleteListener.delete(position);
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(v, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return images == null ? 0 : images.size();
    }

    public void setData(ArrayList images) {
        this.images = images;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView image;
        public TextView firstText;
        public ImageView delete;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            delete = (ImageView) view.findViewById(R.id.delete_icon);
            image = (ImageView) view.findViewById(R.id.image);
            firstText = (TextView) view.findViewById(R.id.first_photo_text);
        }
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface DeletePhoto {
        void delete(int position);
    }

    public void setDeleteListener(DeletePhoto deleteListener) {
        this.deleteListener = deleteListener;
    }
}