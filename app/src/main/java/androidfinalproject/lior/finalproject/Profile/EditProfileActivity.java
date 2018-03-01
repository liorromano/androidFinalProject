package androidfinalproject.lior.finalproject.Profile;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import androidfinalproject.lior.finalproject.R;

/**
 * Created by Lior on 01/03/2018.
 */

public class EditProfileActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getProfileFragment();
    }


    private void getProfileFragment()
    {
        EditProfileFragment editProfileFragment = EditProfileFragment.newInstance();
        FragmentTransaction transaction = EditProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,editProfileFragment);
        transaction.addToBackStack("EditProfileFragment");
        transaction.commit();
    }


}
