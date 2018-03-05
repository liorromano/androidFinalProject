package androidfinalproject.lior.finalproject.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by menachi on 26/12/2017.
 */

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    public String id;

    public String name;
    public String imageUrl;
    public long lastUpdated;
    public String uId;
    public String description;
    public Boolean deleted = false;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void lastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getlastUpdated() {
        return lastUpdated;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    HashMap<String,Object> toJson(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("imageUrl", imageUrl);
        result.put("lastUpdated", lastUpdated);
        result.put("uId", uId);
        result.put("description", description);
        result.put("deleted", deleted);
        return result;
    }
}
