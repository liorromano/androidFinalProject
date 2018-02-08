package androidfinalproject.lior.finalproject.Add;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidfinalproject.lior.finalproject.Model.Post;
import androidfinalproject.lior.finalproject.R;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.solver.widgets.ConstraintWidget.GONE;

/**
 * Created by Lior on 07/02/2018.
 */

public class AddFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = 0;
    //final static int RESAULT_SUCCESS = 0;
    final static int RESAULT_FAIL = 1;

    ImageView imageView;
    Bitmap imageBitmap;
    ProgressBar progressBar;


    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.addProgressBar);
        progressBar.setVisibility(View.GONE);

        final EditText description = (EditText) view.findViewById(R.id.add_editText);

        Button saveBtn = (Button) view.findViewById(R.id.add_saveBtn);
        Button cancelBtn = (Button) view.findViewById(R.id.add_cancelBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {/*

                progressBar.setVisibility(View.VISIBLE);

                Log.d("TAG","Btn Save click");
                final Student st = new Student();
                st.id = idEt.getText().toString();
                st.name = nameEt.getText().toString();
                st.imageUrl = "";
                st.checked = false;
                if (imageBitmap != null) {
                    Model.instace.saveImage(imageBitmap, st.id + ".jpeg", new Model.SaveImageListener() {
                        @Override
                        public void complete(String url) {
                            st.imageUrl = url;
                            Model.instace.addStudent(st);
                            setResult(RESAULT_SUCCESS);
                            progressBar.setVisibility(GONE);
                            finish();
                        }

                        @Override
                        public void fail() {
                            //notify operation fail,...
                            setResult(RESAULT_SUCCESS);
                            progressBar.setVisibility(GONE);
                            finish();
                        }
                    });
                }else{
                    Model.instace.addStudent(st);
                    setResult(RESAULT_SUCCESS);
                    progressBar.setVisibility(GONE);
                    finish();
                }
            */}
        });

        imageView = (ImageView) view.findViewById(R.id.add_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        return view;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }


}
