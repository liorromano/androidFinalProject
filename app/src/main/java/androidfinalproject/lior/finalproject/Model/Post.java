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
    public boolean checked;
    public String imageUrl;
    public long lastUpdated;

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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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

    HashMap<String,Object> toJson(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("imageUrl", imageUrl);
        result.put("checked", checked);
        result.put("lastUpdated", lastUpdated);
        return result;
    }
}
