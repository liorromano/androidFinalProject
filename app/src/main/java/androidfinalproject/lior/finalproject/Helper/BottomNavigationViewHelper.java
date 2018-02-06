package androidfinalproject.lior.finalproject.Helper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import androidfinalproject.lior.finalproject.Add.AddActivity;
import androidfinalproject.lior.finalproject.Home.MainActivity;
import androidfinalproject.lior.finalproject.Profile.ProfileActivity;
import androidfinalproject.lior.finalproject.R;

public class BottomNavigationViewHelper {

        public static  void setupBottomNavigtion(BottomNavigationView bottomNavigationView)
        {
           bottomNavigationView.setAnimation(null);

        }
        public static void enableNavigationClick ( final Context context, BottomNavigationView view)
        {
            view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home:
                            context.startActivity(new Intent(context, MainActivity.class));
                            break;
                        case R.id.add:
                            context.startActivity(new Intent(context, AddActivity.class));
                            break;
                        case R.id.profile:
                            context.startActivity(new Intent(context, ProfileActivity.class));
                            break;
                    }
                    return false;
                }
            });
        }

}

