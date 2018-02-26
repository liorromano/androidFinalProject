package androidfinalproject.lior.finalproject.Helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import androidfinalproject.lior.finalproject.Home.MainActivity;
import androidfinalproject.lior.finalproject.Login.LoginActivity;
import androidfinalproject.lior.finalproject.Model.UserRepository;
import androidfinalproject.lior.finalproject.R;

/**
 * Created by Lior on 24/02/2018.
 */

public class PageChooserActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserRepository.instance.checkLoggedIn(new UserRepository.LoginListener() {
            @Override
            public void answer(Boolean answer) {
                if(answer == true)
                {
                    Intent intent = new Intent(PageChooserActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(PageChooserActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
