package androidfinalproject.lior.finalproject.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by Lior on 21/02/2018.
 */

public class UserFirebase {


    public static void addUserAuth(String password, String email, final Callback<String> callback) {
        FirebaseAuth auth= FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful())
                {
                    callback.onComplete(null);
                }
                else
                {
                    String uid = task.getResult().getUser().getUid();
                    callback.onComplete(uid);
                }
            }
        });
    }

    public interface Callback<T> {
        void onComplete(T data);
    }

    public static void logout() {
        FirebaseAuth auth= FirebaseAuth.getInstance();
        auth.signOut();
    }

    public static void addUser(final User user,String uid){
        Log.d("TAG", "add user to firebase");
        user.setuId(uid);
        HashMap<String, Object> json = user.toJson();
        json.put("lastUpdated", ServerValue.TIMESTAMP);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.child(user.uId).setValue(json);
    }


    public static void saveImage(Bitmap imageBmp, String name, final UserRepository.SaveImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imagesRef = storage.getReference("profile").child(name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.fail();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                listener.complete(downloadUrl.toString());
            }
        });
    }

    public static void login(String email, String password,final Callback<Boolean> callback)
    {
        FirebaseAuth auth= FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                           callback.onComplete(false);
                } else {
                    callback.onComplete(true);
                }
            }
        });
    }

    public static void checkIfloggedIn(Callback<Boolean> callback) {
        FirebaseAuth auth= FirebaseAuth.getInstance();
        String uid= auth.getUid();
        if(uid != null)
        {
            callback.onComplete(true);
        }
        else
        {
            callback.onComplete(false);
        }
    }

    public static void whoLoggedIn(Callback<String> callback) {
        FirebaseAuth auth= FirebaseAuth.getInstance();
        String uid= auth.getUid();
        if(uid != null)
        {
            callback.onComplete(uid);
        }
        else
        {
            callback.onComplete(null);
        }
    }

    interface GetUserCallback {
        void onComplete(User user);

        void onCancel();
    }

    public static void getUser(String uId, final GetUserCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.onComplete(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }

    public static void updateUserPosts(final String uid) {
        getUser(uid, new GetUserCallback() {
            @Override
            public void onComplete(User user) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                try {
                    myRef.child(uid).child("numberOfPosts").setValue(user.getNumberOfPosts()+1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancel() {

            }
        });
    }

    public static void getImage(String url, final PostRepository.GetImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference httpsReference = storage.getReferenceFromUrl(url);
        final long ONE_MEGABYTE = 1024 * 1024;
        httpsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
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
