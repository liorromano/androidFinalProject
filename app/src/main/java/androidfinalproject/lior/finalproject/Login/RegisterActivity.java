package androidfinalproject.lior.finalproject.Login;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import androidfinalproject.lior.finalproject.R;

/**
 * Created by Lior on 01/03/2018.
 */

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getProfileFragment();
    }


    private void getProfileFragment()
    {
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        FragmentTransaction transaction = RegisterActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,registerFragment);
        transaction.addToBackStack("RegisterFragment");
        transaction.commit();
    }

}
