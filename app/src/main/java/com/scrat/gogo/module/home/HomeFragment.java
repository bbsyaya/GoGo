package com.scrat.gogo.module.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scrat.gogo.R;
import com.scrat.gogo.data.model.News;
import com.scrat.gogo.databinding.FragmentHomeBinding;
import com.scrat.gogo.framework.common.BaseFragment;
import com.scrat.gogo.framework.common.BaseRecyclerViewAdapter;
import com.scrat.gogo.framework.common.BaseRecyclerViewHolder;
import com.scrat.gogo.framework.common.BaseRecyclerViewOnScrollListener;
import com.scrat.gogo.framework.common.GlideApp;
import com.scrat.gogo.framework.common.GlideRequest;
import com.scrat.gogo.framework.common.GlideRequests;
import com.scrat.gogo.framework.util.L;

import java.util.List;

/**
 * Created by scrat on 2017/10/24.
 */

public class HomeFragment extends BaseFragment implements HomeContract.HomeView {

    private FragmentHomeBinding binding;
    private HomeContract.HomePresenter presenter;
    private Adapter adapter;
    private BaseRecyclerViewOnScrollListener loadMoreListener;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected String getFragmentName() {
        return "HomeFragment";
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        GlideRequests glideRequests = GlideApp.with(this);

        binding.list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.list.setLayoutManager(layoutManager);
        adapter = new Adapter(glideRequests);
        binding.list.setAdapter(adapter);

        loadMoreListener = new BaseRecyclerViewOnScrollListener(
                layoutManager, new BaseRecyclerViewOnScrollListener.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadData(false);
            }
        });
        binding.list.addOnScrollListener(loadMoreListener);
        binding.srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadData(true);
            }
        });

        new HomePresenter(this);
        presenter.loadData(true);
        return binding.getRoot();
    }

    @Override
    public void setPresenter(HomeContract.HomePresenter homePresenter) {
        presenter = homePresenter;
    }

    @Override
    public void showLoadingList() {
        binding.srl.setRefreshing(true);
    }

    @Override
    public void showListData(List<News> list, boolean replace) {
        hideLoading();
        adapter.setData(list, replace);
    }

    @Override
    public void showNoMoreListData() {
        hideLoading();
    }

    @Override
    public void showEmptyList() {
        hideLoading();
    }

    @Override
    public void showLoadingListError(String e) {
        hideLoading();
        L.e(e);
    }

    private void hideLoading() {
        loadMoreListener.setLoading(false);
        binding.srl.setRefreshing(false);
    }

    private static class Adapter extends BaseRecyclerViewAdapter<News> {
        private final GlideRequest<Bitmap> requestBuilder;

        private Adapter(GlideRequests requestBuilder) {
            this.requestBuilder = requestBuilder.asBitmap().centerCrop();
        }

        @Override
        protected void onBindItemViewHolder(
                BaseRecyclerViewHolder holder, int position, News news) {
            holder.setText(R.id.title, news.getTitle())
                    .setText(R.id.tp, news.getTp())
                    .setText(R.id.count, String.valueOf(news.getTotalComment()));

            requestBuilder.load(news.getCover())
                    .into(holder.getImageView(R.id.img));
        }

        @Override
        protected BaseRecyclerViewHolder onCreateRecycleItemView(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_home, parent, false);
            return new BaseRecyclerViewHolder(v);
        }
    }

}
