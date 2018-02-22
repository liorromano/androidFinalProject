package androidfinalproject.lior.finalproject.Login;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import androidfinalproject.lior.finalproject.R;


/**
 * Created by Lior on 22/02/2018.
 */

public class RegisterActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getMainFragment();
    }

    private void getMainFragment()
    {
        RegisterFragment listFragment = RegisterFragment.newInstance();
        FragmentTransaction transaction = RegisterActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,listFragment);
        transaction.addToBackStack("RegisterFragment");
        transaction.commit();
    }
}
