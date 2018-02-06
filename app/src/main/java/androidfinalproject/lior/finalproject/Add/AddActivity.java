package androidfinalproject.lior.finalproject.Add;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import androidfinalproject.lior.finalproject.Helper.BottomNavigationViewHelper;
import androidfinalproject.lior.finalproject.R;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupNevigationBar();

    }

    private void setupNevigationBar()
    {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.Bottom_navigation_icon);
        BottomNavigationViewHelper.setupBottomNavigtion(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigationClick(AddActivity.this,bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

    }
}

