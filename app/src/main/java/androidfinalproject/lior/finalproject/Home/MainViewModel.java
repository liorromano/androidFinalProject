package androidfinalproject.lior.finalproject.Home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;


import java.util.List;

import androidfinalproject.lior.finalproject.Model.Post;
import androidfinalproject.lior.finalproject.Model.PostRepository;

public class MainViewModel extends ViewModel {
    private LiveData<List<Post>> posts;

    public MainViewModel() {
        posts = PostRepository.instance.getAllPosts();
    }

    public LiveData<List<Post>> getPostsList() {
        return posts;
    }

}
