package com.jacob.newsapp.repositories;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.jacob.newsapp.R;
import com.jacob.newsapp.models.User;

import java.util.HashMap;
import java.util.Map;

import static com.jacob.newsapp.utilities.Constants.USERS;

public class SavedArticleRepository extends AppCompatActivity {

    private static final String TAG = "SavedArticleRepository";

    private static final String KEY_TITLE = "title";
    private static final String KEY_SOURCE = "source";
    private static final String KEY_ID = "id";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final MutableLiveData<Boolean> authenticationResultLiveData = new MutableLiveData<>();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private TextView news_title;
    private TextView news_source;
    private String id;
    private final DocumentReference savedPostRef = db.collection("users").document(id);
    private final DocumentReference savedSourcesRef = db.collection("users").document(id);
    private TextView txt_source;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        news_title = findViewById(R.id.tvTitle);
        news_source = findViewById(R.id.tvSource);
        txt_source = findViewById(R.id.txtSource);
    }

    @Override
    protected void onStart() {
        super.onStart();
        savedPostRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(SavedArticleRepository.this, "Error while loading", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, error.toString());
                    return;
                }
                if (documentSnapshot.exists()) {
                    String title = documentSnapshot.getString(KEY_TITLE);
                    String source = documentSnapshot.getString(KEY_SOURCE);
                }
            }
        });

        savedSourcesRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(SavedArticleRepository.this, "Error while loading news source", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, error.toString());
                    return;
                }
                if (documentSnapshot.exists()) {
                    String source = documentSnapshot.getString(KEY_SOURCE);
                    txt_source.setText(source);
                }
            }
        });
    }

    public void save_news(View v) {

        String title = news_title.getText().toString();
        String source = news_source.getText().toString();

        Map<String, Object> savedPosts = new HashMap<>();
        savedPosts.put(KEY_TITLE, title);
        savedPosts.put(KEY_SOURCE, source);

        id = isUserLoggedIn();
        savedPostRef.update(savedPosts)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SavedArticleRepository.this, "News Article Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SavedArticleRepository.this, "Error Saving!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

        Map<String, Object> savedSources = new HashMap<>();
        savedSources.put(KEY_SOURCE, source);
        savedSourcesRef.update(savedSources)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SavedArticleRepository.this, "News Source Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SavedArticleRepository.this, "Error Saving News Source!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    public void load_news(View v) {

        savedPostRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String title = documentSnapshot.getString(KEY_TITLE);
                            String source = documentSnapshot.getString(KEY_SOURCE);
                        } else {
                            Toast.makeText(SavedArticleRepository.this, "News Post does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SavedArticleRepository.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

        savedSourcesRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String source = documentSnapshot.getString(KEY_SOURCE);
                            txt_source.setText(source);
                        } else {
                            Toast.makeText(SavedArticleRepository.this, "News Source does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SavedArticleRepository.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    public String isUserLoggedIn() {
        return firebaseAuth.getCurrentUser().getUid();
    }

    private void getUserFromFireStore(String uid) {
        DocumentReference userRefByUid =
                FirebaseFirestore.getInstance().collection(USERS).document(uid);

        userRefByUid
                .get()
                .addOnSuccessListener(
                        documentSnapshot -> {
                            if (documentSnapshot == null || !documentSnapshot.exists()) {
                                authenticationResultLiveData.setValue(false);
                                return;
                            }

                            User user = documentSnapshot.toObject(User.class);
                            userLiveData.setValue(user);
                            authenticationResultLiveData.setValue(true);
                        });
    }

}
