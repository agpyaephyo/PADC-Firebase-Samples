package xyz.aungpyaephyo.padc.firebase.data.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 8/18/17.
 */

public class NewsAuthorVO {

    private String userId;

    private String userName;

    private String profileImage;

    public NewsAuthorVO() {
    }

    public NewsAuthorVO(String userId, String userName, String profileImage) {
        this.userId = userId;
        this.userName = userName;
        this.profileImage = profileImage;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
