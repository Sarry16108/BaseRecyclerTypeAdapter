package com.example.administrator.testall.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.testall.BR;
import com.example.administrator.testall.R;
import com.example.administrator.testall.adapter.BaseRecyclerTypeAdapter;
import com.example.administrator.testall.entity.ShowItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/27.
 */

public class MyListAct extends AppCompatActivity {

    private BaseRecyclerTypeAdapter<ShowItem>   mAdapter;
    private List<ShowItem>      mItems;
    private RecyclerView        mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.showList);

        mItems = new ArrayList<>(10);
        mItems.add(new ShowItem("aaaaa", 0));
        mItems.add(new ShowItem("bbbbb", 0));
        mItems.add(new ShowItem("ccccc", 0));
        mItems.add(new ShowItem("ddddd", 1));
        mItems.add(new ShowItem("eeeee", 1));
        mItems.add(new ShowItem("fffff", 2));
        mItems.add(new ShowItem("ggggg", 2));
        mItems.add(new ShowItem("hhhhh", 2));
        mItems.add(new ShowItem("iiiii", 3));

        mAdapter = new BaseRecyclerTypeAdapter<ShowItem>(this, mItems) {
            @Override
            public int getViewType(ShowItem value, int position) {
                if (1 == value.getType()) {
                    return 0;
                } else {
                    return 1;
                }
            }

            @Override
            public int getLayoutByType(int viewType) {
                if (0 == viewType || 3 == viewType) {
                    return R.layout.layout_item_type_0;
                } else if (1 == viewType) {
                    return R.layout.layout_item_type_1;
                } else {
                    return R.layout.layout_item_type_2;
                }
            }

            @Override
            public void showData(BaseRecyclerTypeHolder viewHolder, int position, ShowItem data) {
                viewHolder.setVariable(BR.value, data.getName());
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }
}
