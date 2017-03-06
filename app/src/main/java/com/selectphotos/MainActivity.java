package com.selectphotos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Toast toast;
    private MainActivity context;
    private GridLayoutManager mGridManager;
    private String[] otherLables = new String[]{"拍照", "相冊"};
    private PostPhotoAdapter mAdapter;
    private RecyclerView gridRecyclerView;
    private ArrayList<String> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        images.add("add");
        images.add("1");
        images.add("2");
        images.add("3");
        images.add("4");

        createAdapter();
        createPhotoView();
    }

    private void createAdapter() {
        mAdapter = new PostPhotoAdapter(context);
        mAdapter.setOnItemClickListener(new PostPhotoAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == images.size() - 1)
                    show();
                toast.setText(position + "");
                toast.show();
            }
        });
    }

    private void createPhotoView() {
        gridRecyclerView = (RecyclerView) findViewById(R.id.post_photo_view);
        mGridManager = new GridLayoutManager(context, 4);
        gridRecyclerView.setLayoutManager(mGridManager);
        gridRecyclerView.setHasFixedSize(true);
        gridRecyclerView.setAdapter(mAdapter);
        mAdapter.setDeleteListener(new PostPhotoAdapter.DeletePhoto() {
            @Override
            public void delete(int position) {
                images.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter.setData(images);
    }

    private void show() {
        NormalActionSheet as = new NormalActionSheet(this);
        as.builder().setTitle("title").setItems(Arrays.asList(otherLables)).
                setListener(new NormalActionSheet.OnNormalItemClickListener() {
                    @Override
                    public void onClick(String value) {
                        if (value.equals("拍照"))
                            takePhoto();

                        if (value.equals("相冊"))
                            selectPhoto();

                    }
                }).show();
    }

    private void selectPhoto() {
        toast.setText("相册");
        toast.show();
    }

    private void takePhoto() {
        toast.setText("拍照");
        toast.show();
    }
}
