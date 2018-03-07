package androidfinalproject.lior.finalproject;

import android.app.Application;
import android.content.Context;



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
