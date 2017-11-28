package com.scrat.gogo.module.news.list;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scrat.gogo.R;
import com.scrat.gogo.data.model.News;
import com.scrat.gogo.databinding.BottomNewsLoadMoreBinding;
import com.scrat.gogo.databinding.FragmentHomeBinding;
import com.scrat.gogo.framework.common.BaseFragment;
import com.scrat.gogo.framework.common.BaseOnItemClickListener;
import com.scrat.gogo.framework.common.BaseRecyclerViewAdapter;
import com.scrat.gogo.framework.common.BaseRecyclerViewHolder;
import com.scrat.gogo.framework.common.BaseRecyclerViewOnScrollListener;
import com.scrat.gogo.framework.glide.GlideApp;
import com.scrat.gogo.framework.glide.GlideRequest;
import com.scrat.gogo.framework.glide.GlideRequests;
import com.scrat.gogo.framework.util.L;
import com.scrat.gogo.framework.util.Utils;
import com.scrat.gogo.module.news.detail.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scrat on 2017/10/24.
 */

public class HomeFragment extends BaseFragment implements HomeContract.HomeView {

    private static final int REQUEST_CODE_NEWS_DETAIL = 11;
    private FragmentHomeBinding binding;
    private HomeContract.HomePresenter presenter;
    private Adapter adapter;
    private BaseRecyclerViewOnScrollListener loadMoreListener;
//    private HeaderBannerBinding headerBinding;
//    private BannerAdapter bannerAdapter;
    private BottomNewsLoadMoreBinding loadMoreBinding;

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
        adapter = new Adapter(glideRequests, new OnItemClickListener() {
            @Override
            public void onItemClick(News news) {
                NewsDetailActivity.show(getActivity(), REQUEST_CODE_NEWS_DETAIL, news);
            }

            @Override
            public void onVideoClick(String url) {
                Utils.showVideo(getContext(), Uri.parse(url));
            }
        });
//        headerBinding = HeaderBannerBinding.inflate(inflater, binding.list, false);
//        bannerAdapter = new BannerAdapter(glideRequests, new BaseOnItemClickListener<News>() {
//            @Override
//            public void onItemClick(News news) {
//                NewsDetailActivity.show(getActivity(), REQUEST_CODE_NEWS_DETAIL, news);
//            }
//        });
//        headerBinding.pager.setAdapter(bannerAdapter);
//        adapter.setHeader(headerBinding.getRoot());
        loadMoreBinding = BottomNewsLoadMoreBinding.inflate(inflater, binding.list, false);
        adapter.setFooter(loadMoreBinding.getRoot());
        binding.list.setAdapter(adapter);

        loadMoreListener = new BaseRecyclerViewOnScrollListener(
                layoutManager, () -> {
                    presenter.loadData(false);
    //                presenter.loadBanner();
                });
        binding.list.addOnScrollListener(loadMoreListener);
        binding.srl.setOnRefreshListener(() -> presenter.loadData(true));

        new HomePresenter(this);
        presenter.loadData(true);
//        presenter.loadBanner();
        return binding.getRoot();
    }

    @Override
    public void setPresenter(HomeContract.HomePresenter homePresenter) {
        presenter = homePresenter;
    }

    @Override
    public void showLoadingList() {
        binding.srl.setRefreshing(true);
        loadMoreBinding.sf.setVisibility(View.VISIBLE);
        loadMoreBinding.sf.startShimmerAnimation();
        loadMoreBinding.noMore.setVisibility(View.GONE);
    }

    @Override
    public void showListData(List<News> list, boolean replace) {
        hideLoading();
        adapter.setData(list, replace);
    }

    @Override
    public void showNoMoreListData() {
        hideLoading();
        loadMoreBinding.noMore.setVisibility(View.VISIBLE);
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
        loadMoreBinding.sf.stopShimmerAnimation();
        loadMoreBinding.sf.setVisibility(View.GONE);
    }

    @Override
    public void showBanner(List<News> list) {
//        bannerAdapter.setData(list);
//        headerBinding.pager.setCurrentItem(300);
    }

    interface OnItemClickListener {
        void onItemClick(News news);

        void onVideoClick(String url);
    }

    private static class Adapter extends BaseRecyclerViewAdapter<News> {
        private final GlideRequest<Bitmap> requestBuilder;
        private final OnItemClickListener listener;

        private Adapter(GlideRequests requestBuilder, OnItemClickListener listener) {
            this.listener = listener;
            this.requestBuilder = requestBuilder.asBitmap().centerCrop();
        }

        @Override
        protected void onBindItemViewHolder(
                BaseRecyclerViewHolder holder, int position, final News news) {
            if (news.isVideoNews()) {
                holder.setVisibility(R.id.news_container, false)
                        .setVisibility(R.id.video_container, true)
                        .setText(R.id.video_title, news.getTitle())
                        .setText(R.id.video_tp, news.getTp())
                        .setText(R.id.video_count, String.valueOf(news.getTotalComment()))
                        .setOnClickListener(R.id.video_container, view -> listener.onItemClick(news))
                        .setOnClickListener(R.id.video_cover, view -> listener.onVideoClick(news.getVideo()));

                requestBuilder.load(news.getCover())
                        .into(holder.getImageView(R.id.video_cover));
            } else {
                holder.setVisibility(R.id.news_container, true)
                        .setVisibility(R.id.video_container, false)
                        .setText(R.id.title, news.getTitle())
                        .setText(R.id.tp, news.getTp())
                        .setText(R.id.count, String.valueOf(news.getTotalComment()))
                        .setVisibility(R.id.video_tip, news.isVideoNews())
                        .setOnClickListener(R.id.news_container, view -> listener.onItemClick(news));

                requestBuilder.load(news.getCover())
                        .into(holder.getImageView(R.id.img));
            }
        }

        @Override
        protected BaseRecyclerViewHolder onCreateRecycleItemView(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_home, parent, false);
            return new BaseRecyclerViewHolder(v);
        }
    }

    private static class BannerAdapter extends PagerAdapter {

        private List<News> newsList;
        private GlideRequest<Drawable> request;
        private BaseOnItemClickListener<News> onItemClickListener;

        private BannerAdapter(
                GlideRequests glideRequests, BaseOnItemClickListener<News> onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            newsList = new ArrayList<>();
            request = glideRequests.asDrawable().centerCrop();
        }

        private void setData(List<News> list) {
            newsList.clear();
            if (list != null) {
                newsList.addAll(list);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return newsList.isEmpty() ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            View view = inflater.inflate(R.layout.list_item_banner, container, false);

            final News news = newsList.get(position % newsList.size());

            ImageView imageView = view.findViewById(R.id.cover);
            request.load(news.getCover()).into(imageView);

            TextView textView = view.findViewById(R.id.title);
            textView.setText(news.getTitle());

            if (news.isVideoNews()) {
                view.findViewById(R.id.video_tip).setVisibility(View.VISIBLE);
            } else {
                view.findViewById(R.id.video_tip).setVisibility(View.GONE);
            }

            view.setOnClickListener(view1 -> onItemClickListener.onItemClick(news));

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

}
