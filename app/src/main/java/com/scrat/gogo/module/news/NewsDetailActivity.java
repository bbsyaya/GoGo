package com.scrat.gogo.module.news;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.RequestOptions;
import com.scrat.gogo.R;
import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.local.Preferences;
import com.scrat.gogo.data.model.Comment;
import com.scrat.gogo.data.model.News;
import com.scrat.gogo.data.model.NewsDetail;
import com.scrat.gogo.databinding.ActivityNewsDetailBinding;
import com.scrat.gogo.databinding.BottomNewsDetailCommentBinding;
import com.scrat.gogo.databinding.HeaderNewsDetailBinding;
import com.scrat.gogo.framework.common.BaseActivity;
import com.scrat.gogo.framework.common.BaseRecyclerViewAdapter;
import com.scrat.gogo.framework.common.BaseRecyclerViewHolder;
import com.scrat.gogo.framework.common.BaseRecyclerViewOnScrollListener;
import com.scrat.gogo.framework.glide.GlideApp;
import com.scrat.gogo.framework.glide.GlideCircleTransform;
import com.scrat.gogo.framework.glide.GlideRequests;
import com.scrat.gogo.framework.util.L;
import com.scrat.gogo.framework.util.Utils;
import com.scrat.gogo.framework.view.IosDialog;
import com.scrat.gogo.framework.view.LoginDialog;

import java.util.List;

/**
 * Created by scrat on 2017/11/12.
 */

public class NewsDetailActivity extends BaseActivity implements NewsDetailContract.View {

    private static final String NEWS = "news";
    private ActivityNewsDetailBinding binding;
    private Adapter adapter;
    private NewsDetailContract.Presenter presenter;
    private HeaderNewsDetailBinding headerBinding;
    private GlideRequests glideRequests;
    private BaseRecyclerViewOnScrollListener loadMoreListener;
    private BottomNewsDetailCommentBinding commentBinding;
    private IosDialog loginDialog;

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

        glideRequests = GlideApp.with(this);

        binding.list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.list.setLayoutManager(layoutManager);
        adapter = new Adapter(glideRequests);
        headerBinding = HeaderNewsDetailBinding.inflate(getLayoutInflater(), binding.list, false);
        adapter.setHeader(headerBinding.getRoot());
        commentBinding = BottomNewsDetailCommentBinding.inflate(getLayoutInflater(), binding.list, false);
        commentBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Preferences.getInstance().isLogin()) {
                    loginDialog.show(view);
                    return;
                }
                requestFocus(binding.comment);
            }
        });
        adapter.setFooter(commentBinding.getRoot());
        binding.list.setAdapter(adapter);

        loadMoreListener = new BaseRecyclerViewOnScrollListener(
                layoutManager, new BaseRecyclerViewOnScrollListener.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadComment(false);
            }
        });
        binding.list.addOnScrollListener(loadMoreListener);

        News news = (News) getIntent().getSerializableExtra(NEWS);
        showNews(news);
        new NewsDetailPresenter(this, news);
        presenter.loadNewsDetail();
        presenter.loadComment(true);

        binding.comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    binding.sendBtn.setVisibility(View.VISIBLE);
                } else {
                    binding.sendBtn.setVisibility(View.GONE);
                }
            }
        });
        loginDialog = LoginDialog.build(this);
    }

    @Override
    protected void onDestroy() {
        if (loginDialog.isShowing()) {
            loginDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public void setPresenter(NewsDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoadingList() {
        showLoadingComment();
    }

    @Override
    public void showListData(List<Res.CommentItem> list, boolean replace) {
        hideLoadingComment();
        adapter.setData(list, replace);
    }

    @Override
    public void showNoMoreListData() {
        hideLoadingComment();
    }

    @Override
    public void showEmptyList() {
        hideLoadingComment();
    }

    @Override
    public void showLoadingListError(String e) {
        hideLoadingComment();
        showMessage(e);
    }

    @Override
    public void showSendingComment() {

    }

    @Override
    public void showSendCommentError(String e) {
        showMessage(e);
    }

    @Override
    public void showSendCommentSuccess(Comment comment) {
        toast("发送成功");
        binding.comment.setText("");
        binding.sendBtn.setVisibility(View.GONE);
        presenter.loadNewsDetail();
        presenter.loadComment(true);
        binding.comment.clearFocus();
    }

    @Override
    public void showLoadingNewsDetail() {

    }

    @Override
    public void showLoadNewsDetailError(String msg) {
        showMessage(msg);
    }

    private void showLoadingComment() {

    }

    private void hideLoadingComment() {
        loadMoreListener.setLoading(false);
    }

    @Override
    public void showNewsDetail(NewsDetail detail) {
        headerBinding.title.setText(detail.getTitle());
        headerBinding.date.setText(Utils.formatDate(detail.getNewsTs()));
        if (detail.isWebViewNews()) {
            toast("web");
        } else if (detail.isVideoNews()) {
            headerBinding.body.setVisibility(View.GONE);
            glideRequests.load(detail.getCover()).into(headerBinding.cover);
            headerBinding.coverItem.setVisibility(View.VISIBLE);
            final Uri uri = Uri.parse(detail.getVideo());
            headerBinding.coverItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //调用系统自带的播放器
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "video/mp4");
                        startActivity(intent);
                    } catch (Exception e) {
                        L.e(e);
                    }
                }
            });
        } else {
            headerBinding.body.fromHtml(detail.getBody());
        }

        showNews(detail);
    }

    private void showNews(News news) {
        binding.topBar.subject.setText(news.getTp());
        if (news.getTotalComment() > 0) {
            headerBinding.groupTitle.setVisibility(View.VISIBLE);
            headerBinding.groupTitle.setText(String.format("评论 %s", news.getTotalComment()));
        } else {
            headerBinding.groupTitle.setVisibility(View.GONE);
        }
    }

    public void sendCommend(View view) {
        hideSoftInput();
        if (!Preferences.getInstance().isLogin()) {
            loginDialog.show(view);
            return;
        }
        presenter.sendComment(binding.comment.getText().toString());
    }

    private static class Adapter extends BaseRecyclerViewAdapter<Res.CommentItem> {

        private final GlideRequests GlideRequests;
        private RequestOptions options;

        private Adapter(GlideRequests GlideRequests) {
            this.GlideRequests = GlideRequests;
            options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.place_holder_circle_80dp)
                    .transform(new GlideCircleTransform());
        }

        @Override
        protected void onBindItemViewHolder(
                BaseRecyclerViewHolder holder, int position, Res.CommentItem item) {

            holder.setText(R.id.nickname, item.getUser().getUsername())
                    .setText(R.id.date, Utils.formatDate(item.getComment().getCreateTs()))
                    .setText(R.id.comment, item.getComment().getContent());

            GlideRequests.load(item.getUser().getAvatar())
                    .apply(options)
                    .into(holder.getImageView(R.id.img));
        }

        @Override
        protected BaseRecyclerViewHolder onCreateRecycleItemView(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_comment, parent, false);
            return new BaseRecyclerViewHolder(v);
        }
    }
}
