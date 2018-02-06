package androidfinalproject.lior.finalproject;

import android.app.Application;
import android.content.Context;

/**
 * Created by menachi on 17/05/2017.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    public static Context getMyContext(){
        return context;
    }
}
