package xyz.aungpyaephyo.padc.firebase.data.vo;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 8/18/17.
 */

public class NewsFeedVO {

    private long feedId;

    private long posedDate;

    private String content;

    private String image;

    private int sendToCount;

    private NewsAuthorVO newsAuthor;

    private List<LikeVO> likes;

    private List<CommentVO> comments;

    public long getFeedId() {
        return feedId;
    }

    public long getPosedDate() {
        return posedDate;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public int getSendToCount() {
        return sendToCount;
    }

    public NewsAuthorVO getNewsAuthor() {
        return newsAuthor;
    }

    public List<LikeVO> getLikes() {
        if (likes == null)
            return new ArrayList<>();

        return likes;
    }

    public List<CommentVO> getComments() {
        if (comments == null)
            return new ArrayList<>();

        return comments;
    }

    public static NewsFeedVO initNews(String newsContent, String image, FirebaseUser firebaseUser) {
        NewsFeedVO newsFeed = new NewsFeedVO();
        newsFeed.content = newsContent;
        newsFeed.posedDate = System.currentTimeMillis() / 1000;
        newsFeed.image = image;

        NewsAuthorVO newsAuthor = new NewsAuthorVO(Long.parseLong(firebaseUser.getUid()),
                firebaseUser.getDisplayName(),
                firebaseUser.getPhotoUrl().toString());

        newsFeed.newsAuthor = newsAuthor;
        return newsFeed;
    }
}
