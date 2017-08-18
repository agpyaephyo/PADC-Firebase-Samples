package xyz.aungpyaephyo.padc.firebase.data.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 8/18/17.
 */

public class LikeVO {

    @SerializedName("like_id")
    private long likeId;

    @SerializedName("user_id")
    private long userId;

    @SerializedName("timestamp")
    private long timeStamp;

    public long getLikeId() {
        return likeId;
    }

    public long getUserId() {
        return userId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
