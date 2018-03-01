package androidfinalproject.lior.finalproject.Profile;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidfinalproject.lior.finalproject.Home.MainViewModel;
import androidfinalproject.lior.finalproject.Model.Post;
import androidfinalproject.lior.finalproject.Model.PostRepository;
import androidfinalproject.lior.finalproject.Model.User;
import androidfinalproject.lior.finalproject.Model.UserRepository;
import androidfinalproject.lior.finalproject.R;

/**
 * Created by Lior on 27/02/2018.
 */

public class PostDetailFragment extends Fragment{

    ProgressBar progressBar;
    String myValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        myValue = bundle.getString("key");
    }


    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        progressBar = view.findViewById(R.id.post_detail_progressBar);
        progressBar.setVisibility(View.GONE);

        PostRepository.instance.getPostByPostId(myValue, new PostRepository.GetPostListener() {
            @Override
            public void onSuccess(final Post post) {
                TextView name = (TextView) view.findViewById(R.id.post_detail_namerow_text);
                TextView description = (TextView) view.findViewById(R.id.post_detail_descriptionrow_text);
                final ImageView postImage = (ImageView) view.findViewById(R.id.post_detail_postrow_image);
                final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.post_detail_progressBar);
                final ImageView profileImage = (ImageView) view.findViewById(R.id.post_detail_profile_image);

                name.setText(post.name);
                description.setText(post.description);
                postImage.setTag(post.imageUrl);
                postImage.setImageDrawable(getContext().getDrawable(R.drawable.avatar));

                if (post.imageUrl != null && !post.imageUrl.isEmpty() && !post.imageUrl.equals("")){
                    progressBar.setVisibility(View.VISIBLE);
                    PostRepository.instance.getImage(post.imageUrl, new PostRepository.GetImageListener() {
                        @Override
                        public void onSuccess(Bitmap image) {
                            String tagUrl = postImage.getTag().toString();
                            if (tagUrl.equals(post.imageUrl)) {
                                postImage.setImageBitmap(image);
                                progressBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFail() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }

                UserRepository.instance.getUser(post.uId, new UserRepository.GetUserCallback() {
                    @Override
                    public void onComplete(final User user) {

                        if (user.imageUrl != null && !user.imageUrl.isEmpty() && !user.imageUrl.equals("")){
                            profileImage.setTag(user.imageUrl);
                            profileImage.setImageDrawable(getContext().getDrawable(R.drawable.avatar));
                            UserRepository.instance.getImage(user.imageUrl, new PostRepository.GetImageListener() {
                                @Override
                                public void onSuccess(Bitmap image) {
                                    String tagUrl =  profileImage.getTag().toString();
                                    if (tagUrl.equals(user.imageUrl)) {
                                        profileImage.setImageBitmap(image);

                                    }
                                }

                                @Override
                                public void onFail() {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancel() {

                    }
                });


            }
        });

        return view;
    }
}
