package androidfinalproject.lior.finalproject.Profile;






import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidfinalproject.lior.finalproject.Helper.BottomNavigationViewHelper;
import androidfinalproject.lior.finalproject.Home.MainActivity;
import androidfinalproject.lior.finalproject.Home.MainFragment;
import androidfinalproject.lior.finalproject.Model.Post;
import androidfinalproject.lior.finalproject.R;

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupNevigationBar();

        getProfileFragment();
    }

    private void getProfileFragment()
    {
        ProfileFragment profileFragment = ProfileFragment.newInstance();
        FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,profileFragment);
        transaction.addToBackStack("ProfileFragment");
        transaction.commit();
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

    @Override
    public void onBackPressed() {
        ///do nothing
    }

    @Override
    public void onItemSelected(Post post) {
        Log.d("TAG", "clicked row");
    }


}

