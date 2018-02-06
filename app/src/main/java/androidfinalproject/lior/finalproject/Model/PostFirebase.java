package androidfinalproject.lior.finalproject.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by menachi on 27/12/2017.
 */

public class PostFirebase {
    public interface Callback<T> {
        void onComplete(T data);
    }

    public static void getAllPostsAndObserve(final Callback<List<Post>> callback) {
        Log.d("TAG", "getAllPostsAndObserve");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("posts");
        ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Post> list = new LinkedList<Post>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Post post = snap.getValue(Post.class);
                    list.add(post);
                }
                callback.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onComplete(null);
            }
        });
    }

    public static void getAllPostsAndObserve(long lastUpdate, final Callback<List<Post>> callback) {
        Log.d("TAG", "getAllPostsAndObserve " + lastUpdate);
        Log.d("TAG", "getAllPostsAndObserve");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("posts");
        Query query = myRef.orderByChild("lastUpdated").startAt(lastUpdate);
        ValueEventListener listener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Post> list = new LinkedList<Post>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Post post = snap.getValue(Post.class);
                    list.add(post);
                }
                callback.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onComplete(null);
            }
        });
    }

    public static void addPost(Post post){
        Log.d("TAG", "add post to firebase");
        HashMap<String, Object> json = post.toJson();
        json.put("lastUpdated", ServerValue.TIMESTAMP);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("posts");
        myRef.child(post.id).setValue(json);
    }

    public static void getImage(String url, final PostRepository.GetImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference httpsReference = storage.getReferenceFromUrl(url);
        final long ONE_MEGABYTE = 1024 * 1024;
        httpsReference.getBytes(3* ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                listener.onSuccess(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                Log.d("TAG",exception.getMessage());
                listener.onFail();
            }
        });
    }
}
