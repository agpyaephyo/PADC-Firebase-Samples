package xyz.aungpyaephyo.padc.firebase.data.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 8/18/17.
 */

public class CommentVO {

    private long commentId;

    private String userId;

    private String comment;

    private long timestamp;

    public long getCommentId() {
        return commentId;
    }

    public String getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
