package com.scrat.gogo.module.news;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scrat.gogo.R;
import com.scrat.gogo.data.model.News;
import com.scrat.gogo.data.model.NewsDetail;
import com.scrat.gogo.databinding.ActivityNewsDetailBinding;
import com.scrat.gogo.databinding.HeaderNewsDetailBinding;
import com.scrat.gogo.framework.common.BaseActivity;
import com.scrat.gogo.framework.common.BaseRecyclerViewAdapter;
import com.scrat.gogo.framework.common.BaseRecyclerViewHolder;

/**
 * Created by scrat on 2017/11/12.
 */

public class NewsDetailActivity extends BaseActivity implements NewsDetailContract.View {

    private static final String NEWS = "news";
    private ActivityNewsDetailBinding binding;
    private Adapter adapter;
    private NewsDetailContract.Presenter presenter;
    private HeaderNewsDetailBinding headerBinding;

    public static void show(Activity activity, int requestCode, News news) {
        Intent i = new Intent(activity, NewsDetailActivity.class);
        i.putExtra(NEWS, news);
        activity.startActivityForResult(i, requestCode);
    }

    @NonNull
    @Override
    protected String getActivityName() {
        return "NewsDetailActivity";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail);

        binding.list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.list.setLayoutManager(layoutManager);
        adapter = new Adapter();
        headerBinding = HeaderNewsDetailBinding.inflate(getLayoutInflater(), binding.list, false);
        adapter.setHeader(headerBinding.getRoot());
        binding.list.setAdapter(adapter);

        News news = (News) getIntent().getSerializableExtra(NEWS);
        showNews(news);
        new NewsDetailPresenter(this, news);
        presenter.loadNewsDetail();
    }

    @Override
    public void setPresenter(NewsDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoadingNewsDetail() {

    }

    @Override
    public void showLoadNewsDetailError(String msg) {

    }

    @Override
    public void showNewsDetail(NewsDetail detail) {
        if (detail.isWebViewNews()) {
            toast("web");
        } else {
            headerBinding.body.fromHtml(detail.getBody());
        }

        showNews(detail);
    }

    private void showNews(News news) {
        binding.topBar.subject.setText(news.getTitle());
        if (news.getTotalComment() > 0) {
            headerBinding.groupTitle.setText(String.format("评论 %s", news.getTotalComment()));
        } else {
            headerBinding.groupTitle.setText("评论");
        }
    }

    private static class Adapter extends BaseRecyclerViewAdapter<News> {

        @Override
        protected void onBindItemViewHolder(BaseRecyclerViewHolder holder, int position, News news) {

        }

        @Override
        protected BaseRecyclerViewHolder onCreateRecycleItemView(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_comment, parent, false);
            return new BaseRecyclerViewHolder(v);
        }
    }
}
