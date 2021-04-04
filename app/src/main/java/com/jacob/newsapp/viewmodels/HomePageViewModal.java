package com.jacob.newsapp.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jacob.newsapp.R;
import com.jacob.newsapp.models.MediaStackResponse;
import com.jacob.newsapp.repositories.ArticleRepository;

import java.util.HashMap;
import java.util.Map;

public class HomePageViewModal extends ViewModel {

    private static final String TAG = "HomePageViewModel";
    private final ArticleRepository articleRepository = new ArticleRepository();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public LiveData<MediaStackResponse> getLatestNews() {
        return articleRepository.getLatestNews();
    }

    public void searchButtonClicked(View view) {
        Navigation.findNavController(view).navigate(R.id.homePage_to_searchPage);
    }

    public void openArticle(View view) {
        Navigation.findNavController(view).navigate(R.id.homePage_to_articleViewer);
    }

    public void testFireStoreWrite(View view) {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void testFireStoreRead(View view) {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void getTrendingNews() {
        articleRepository.getTrendingByNews();
    }
}
