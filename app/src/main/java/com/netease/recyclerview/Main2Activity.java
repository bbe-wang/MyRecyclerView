package com.netease.recyclerview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FeedAdapter adapter;

    private RelativeLayout mSuspensionBar;

    private TextView mSuspensionTv;
    private ImageView mSuspensionIv;

    private int mSuspensionHeight;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mSuspensionBar = findViewById(R.id.suspension_bar);
        mSuspensionTv = findViewById(R.id.tv_nickname);
        mSuspensionIv = findViewById(R.id.iv_avatar);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
     final  LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new FeedAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取悬浮条的高度
                mSuspensionHeight = mSuspensionBar.getHeight();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //对于悬浮位置进行调整
                //找到下一个itemView
                View view = layoutManager.findViewByPosition(mCurrentPosition +  1);
                if (view != null){
                    //就去取这个view到顶端的距离
                    if (view.getTop() <= mSuspensionHeight){
                        mSuspensionBar .setY(-(mSuspensionHeight - view.getTop()));
                    }else {
                        //保持再原来的位置
                        mSuspensionBar.setY(0);

                    }

                }
                if ( mCurrentPosition != layoutManager.findFirstVisibleItemPosition()){
                    mCurrentPosition = layoutManager.findFirstVisibleItemPosition();
                    updateSuspentionBar();
                }


            }


        });



    }


    private void updateSuspentionBar() {
        Picasso.with(this)
                .load(getAvatarResId(mCurrentPosition))
               .centerInside()
                .fit().into(mSuspensionIv);
        mSuspensionTv.setText("NetEase" +mCurrentPosition);
    }


    private int getAvatarResId(int position){
        switch (position % 4){
            case 0:
                return R.drawable.avatar1;
            case 1:
                return R.drawable.avatar2;
            case 2:
                return R.drawable.avatar3;
            case 3:
                return R.drawable.avatar4;
        }
        return 0;
    }

}
