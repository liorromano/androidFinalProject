package androidfinalproject.lior.finalproject.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by menachi on 27/12/2017.
 */
@Dao
public interface PostsDao {
    @Query("SELECT * FROM Post ORDER BY lastUpdated DESC")
    List<Post> getAll();

    @Query("SELECT * FROM Post WHERE id IN (:userIds)")
    List<Post> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM Post WHERE id = :id")
    Post findById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);

    @Delete
    void delete(Post post);

}
