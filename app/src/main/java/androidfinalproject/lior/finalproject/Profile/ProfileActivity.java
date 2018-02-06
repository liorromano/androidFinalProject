package androidfinalproject.lior.finalproject.Profile;




import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import androidfinalproject.lior.finalproject.Helper.BottomNavigationViewHelper;
import androidfinalproject.lior.finalproject.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupToolBar();
        //setupNevigationBar();

    }

    private void setupToolBar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolBar);
        setSupportActionBar(toolbar);

    }

    private void setupNevigationBar()
    {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.Bottom_navigation_icon);
        BottomNavigationViewHelper.setupBottomNavigtion(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigationClick(ProfileActivity.this,bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

    }
}

