package com.scrat.gogo.module.news.detail;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.scrat.gogo.data.DataRepository;
import com.scrat.gogo.data.api.Api;
import com.scrat.gogo.data.api.Res;
import com.scrat.gogo.data.callback.DefaultLoadObjCallback;
import com.scrat.gogo.data.model.Comment;
import com.scrat.gogo.data.model.News;
import com.scrat.gogo.data.model.NewsDetail;

import okhttp3.Call;

/**
 * Created by scrat on 2017/11/12.
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter {
    private NewsDetailContract.View view;
    private String newsId;
    private Call call;
    private String index;
    private volatile boolean like;

    public NewsDetailPresenter(NewsDetailContract.View view, News news) {
        this.view = view;
        this.newsId = news.getNewsId();
        view.setPresenter(this);
    }

    @Override
    public void loadNewsDetail() {
        view.showLoadingNewsDetail();
        DataRepository.getInstance().getApi()
                .getNewsDetail(newsId, new DefaultLoadObjCallback<NewsDetail, Res.NewsDetailRes>() {
                    @Override
                    protected void onSuccess(NewsDetail detail) {
                        like = detail.isLike();
                        view.showNewsDetail(detail);
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showLoadNewsDetailError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.NewsDetailRes> getResClass() {
                        return Res.NewsDetailRes.class;
                    }
                });
    }

    @Override
    public void loadComment(final boolean refresh) {
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }

        if (refresh) {
            index = "0";
        }

        if (!Res.ListRes.hasMoreData(index)) {
            view.showNoMoreListData();
            return;
        }

        view.showLoadingList();
        call = DataRepository.getInstance().getApi().getNewsComments(
                newsId,
                index,
                new DefaultLoadObjCallback<Res.ListRes<Res.CommentItem>, Res.CommentItemListRes>() {
                    @Override
                    protected void onSuccess(Res.ListRes<Res.CommentItem> commentItemListRes) {
                        index = commentItemListRes.getIndex();
                        if (commentItemListRes.isEmpty()) {
                            if (refresh) {
                                view.showEmptyList();
                                return;
                            }
                            view.showNoMoreListData();
                            return;
                        }

                        view.showListData(commentItemListRes.getItems(), refresh);
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showLoadingListError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.CommentItemListRes> getResClass() {
                        return Res.CommentItemListRes.class;
                    }
                });
    }

    @Override
    public void sendComment(String comment) {
        if (TextUtils.isEmpty(comment)) {
            return;
        }

        view.showSendingComment();
        DataRepository.getInstance().getApi().addNewsComment(
                newsId, comment, new DefaultLoadObjCallback<Comment, Res.CommentRes>() {
                    @Override
                    protected void onSuccess(Comment comment) {
                        view.showSendCommentSuccess(comment);
                    }

                    @Override
                    public void onError(Exception e) {
                        view.showSendCommentError(e.getMessage());
                    }

                    @NonNull
                    @Override
                    protected Class<Res.CommentRes> getResClass() {
                        return Res.CommentRes.class;
                    }
                });
    }

    @Override
    public void likeNews() {
        Api api = DataRepository.getInstance().getApi();
        if (like) {
            api.unLikeNews(newsId, new DefaultLoadObjCallback<String, Res.DefaultStrRes>() {
                @Override
                protected void onSuccess(String s) {
                    like = !like;
                    view.showNewsLike(like);
                }

                @Override
                public void onError(Exception e) {
                    // ignore
                }

                @NonNull
                @Override
                protected Class<Res.DefaultStrRes> getResClass() {
                    return Res.DefaultStrRes.class;
                }
            });
        } else {
            api.likeNews(newsId, new DefaultLoadObjCallback<String, Res.DefaultStrRes>() {
                @Override
                protected void onSuccess(String s) {
                    like = !like;
                    view.showNewsLike(like);
                }

                @Override
                public void onError(Exception e) {
                    // ignore
                }

                @NonNull
                @Override
                protected Class<Res.DefaultStrRes> getResClass() {
                    return Res.DefaultStrRes.class;
                }
            });
        }
    }

    @Override
    public void likeComment(String commentId, boolean like) {
        Api api = DataRepository.getInstance().getApi();
        if (like) {
            api.likeComment(commentId, new DefaultLoadObjCallback<String, Res.DefaultStrRes>() {
                @Override
                protected void onSuccess(String s) {

                }

                @Override
                public void onError(Exception e) {
                    // ignore
                }

                @NonNull
                @Override
                protected Class<Res.DefaultStrRes> getResClass() {
                    return Res.DefaultStrRes.class;
                }
            });
        } else {
            api.unLikeComment(commentId, new DefaultLoadObjCallback<String, Res.DefaultStrRes>() {
                @Override
                protected void onSuccess(String s) {

                }

                @Override
                public void onError(Exception e) {
                    // ignore
                }

                @NonNull
                @Override
                protected Class<Res.DefaultStrRes> getResClass() {
                    return Res.DefaultStrRes.class;
                }
            });
        }
    }
}
