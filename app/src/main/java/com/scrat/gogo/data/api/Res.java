package com.scrat.gogo.data.api;

import com.scrat.gogo.data.model.Comment;
import com.scrat.gogo.data.model.News;
import com.scrat.gogo.data.model.NewsDetail;
import com.scrat.gogo.data.model.TokenInfo;
import com.scrat.gogo.data.model.User;
import com.scrat.gogo.framework.common.BaseResponse;

import java.util.List;

/**
 * Created by scrat on 2017/11/4.
 */

public class Res {
    public class DefaultStrRes extends BaseResponse<String> {
    }

    public class TokenRes extends BaseResponse<TokenInfo> {
    }

    public static class ListRes<T> {
        private static final String NO_MORE_DATA_INDEX = "-1";
        private String index;
        private List<T> items;

        public static boolean hasMoreData(String index) {
            return !NO_MORE_DATA_INDEX.equals(index);
        }

        public boolean hasMoreData() {
            return !NO_MORE_DATA_INDEX.equals(index);
        }

        public boolean isEmpty() {
            return items == null || items.isEmpty();
        }

        public String getIndex() {
            return index;
        }

        public List<T> getItems() {
            return items;
        }
    }

    public class NewsListRes extends BaseResponse<ListRes<News>> {
    }

    public class NewsDetailRes extends BaseResponse<NewsDetail> {}

    public class CommentItem {
        private Comment comment;
        private User user;

        public Comment getComment() {
            return comment;
        }

        public User getUser() {
            return user;
        }
    }

    public class CommentItemListRes extends BaseResponse<ListRes<CommentItem>> {}
}
