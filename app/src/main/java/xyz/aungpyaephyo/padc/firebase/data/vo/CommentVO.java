package xyz.aungpyaephyo.padc.firebase.data.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 8/18/17.
 */

public class CommentVO {

    @SerializedName("comment_id")
    private long commentId;

    @SerializedName("user_id")
    private long userId;

    @SerializedName("comment")
    private String comment;

    @SerializedName("timestamp")
    private long timestamp;

    public long getCommentId() {
        return commentId;
    }

    public long getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
