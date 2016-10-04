package io.github.kolacbb.kolaweibo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.kolacbb.kolaweibo.widget.KRecyclerView;

public class TestActivity extends AppCompatActivity {

    private KRecyclerView mRecView;
    private KRecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mRecView = (KRecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new TimeLineAdapter<String>();
        mRecView.setAdapter(mAdapter);
        mRecView.setLoadingEnable(true);
        //mAdapter.remove("aa");

        mRecView.setOnRefreshListener(new KRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> list = new ArrayList<String>();
                        for (int i = 0; i < 35; i++) {
                            list.add(i + "");
                        }
                        mAdapter.addToFront(list);
                        mAdapter.notifyDataSetChanged();
                        mRecView.setRefreshing(false);
                    }
                }, 500);


            }
        });

        mRecView.setOnLoadingListener(new KRecyclerView.OnLoadingListener() {
            @Override
            public void onLoading() {
                mRecView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> list = new ArrayList<String>();
                        for (int i = 100; i < 105; i++) {
                            list.add(i + "la");
                        }
                        mAdapter.addToRear(list);
                        mAdapter.notifyDataSetChanged();
                    }
                }, 500);
            }
        });
    }

    class TimeLineAdapter<String> extends KRecyclerView.Adapter<String> {

        @Override
        public RecyclerView.ViewHolder onCreateKViewHolder(ViewGroup parent, int viewType) {

            return new MyViewHold(new TextView(parent.getContext()));
        }

        @Override
        public void onBindKViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHold hold = (MyViewHold) holder;
            hold.t.setText(getData(position).toString());
        }

        class MyViewHold extends RecyclerView.ViewHolder {

            public TextView t;
            public MyViewHold(View itemView) {
                super(itemView);
                t = (TextView) itemView;
            }
        }
    }
}
