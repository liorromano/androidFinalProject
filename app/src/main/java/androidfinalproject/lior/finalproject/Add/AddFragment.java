package androidfinalproject.lior.finalproject.Add;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidfinalproject.lior.finalproject.Model.Post;
import androidfinalproject.lior.finalproject.Model.PostRepository;
import androidfinalproject.lior.finalproject.R;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;


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
        progressBar.setVisibility(GONE);

        final EditText description = (EditText) view.findViewById(R.id.add_editText);

        Button saveBtn = (Button) view.findViewById(R.id.add_saveBtn);
        Button cancelBtn = (Button) view.findViewById(R.id.add_cancelBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(VISIBLE);

                Log.d("TAG","Btn Save click");
                final Post post = new Post();
                post.id = "7";
                post.name = "lior";
                post.imageUrl = "";
                if (imageBitmap != null) {
                    PostRepository.instance.saveImage(imageBitmap, post.id + ".jpeg", new PostRepository.SaveImageListener() {
                        @Override
                        public void complete(String url) {
                            post.imageUrl = url;
                            PostRepository.instance.addPost(post);
                            getActivity().setResult(RESAULT_SUCCESS);
                            progressBar.setVisibility(GONE);
                            getActivity().finish();
                        }

                        @Override
                        public void fail() {
                            //notify operation fail,...
                            getActivity().setResult(RESAULT_SUCCESS);
                            progressBar.setVisibility(GONE);
                            getActivity().finish();
                        }
                    });
                }else{
                    PostRepository.instance.addPost(post);
                    getActivity().setResult(RESAULT_SUCCESS);
                    progressBar.setVisibility(GONE);
                    getActivity().finish();
                }
            }
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
