package androidfinalproject.lior.finalproject.Model;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by Lior on 21/02/2018.
 */

public class User {

    @PrimaryKey
    @NonNull
    public String userName;

    public String imageUrl;
    public String uId;
    public int numberOfPosts = 0;


    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    HashMap<String,Object> toJson(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("imageUrl", imageUrl);
        result.put("uId", uId);
        result.put("numberOfPosts", numberOfPosts);
        return result;
    }
}
