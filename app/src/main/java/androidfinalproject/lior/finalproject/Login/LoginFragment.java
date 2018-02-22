package androidfinalproject.lior.finalproject.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidfinalproject.lior.finalproject.Add.AddFragment;
import androidfinalproject.lior.finalproject.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Lior on 21/02/2018.
 */

public class LoginFragment extends Fragment {

    static final int REQUEST_ADD_ID = 1;
    ProgressBar progressBar;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        progressBar = view.findViewById(R.id.login_progressBar);
        progressBar.setVisibility(GONE);

        Button signUp = (Button) view.findViewById(R.id.login_signUp_Btn);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(VISIBLE);
                Intent intent = new Intent(getActivity(),RegisterActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
