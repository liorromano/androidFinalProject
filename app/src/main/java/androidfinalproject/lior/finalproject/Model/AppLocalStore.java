package androidfinalproject.lior.finalproject.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import androidfinalproject.lior.finalproject.MyApplication;

@Database(entities = {Post.class}, version = 2)
abstract class AppLocalStoreDb extends RoomDatabase {
    public abstract PostsDao postDao();
}

public class AppLocalStore{
    static public AppLocalStoreDb db = Room.databaseBuilder(MyApplication.getMyContext(),
            AppLocalStoreDb.class,
            "database-name").fallbackToDestructiveMigration().build();
}