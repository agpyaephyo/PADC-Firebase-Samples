package xyz.aungpyaephyo.padc.firebase.data.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 8/18/17.
 */

public class NewsAuthorVO {

    @SerializedName("user_id")
    private long userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("profile_image")
    private String profileImage;

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
