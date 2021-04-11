package com.jacob.newsapp.repositories;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SavedArticleRepository extends AppCompatActivity {

    ArrayList<String> savedCategories = new ArrayList<String>();
    //List<Map<String,String>> savedPosts = new ArrayList<Map<String,String>>();
    ArrayList<String> savedSources = new ArrayList<String>();
    FirebaseFirestore db;

    /*@Override
    protected void onStart(){
        super.onStart();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(SavedArticleRepository.this, "User Verified", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SavedArticleRepository.this, "User Not Verified", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        void SavedPosts(){
            db = FirebaseFirestore.getInstance();
            Map<TextView, Object> savedPosts = new HashMap<>();
            TextView title = (TextView) findViewById(R.id.tvTitle);
            TextView source = (TextView) findViewById(R.id.tvSource);
            savedPosts.put(title, source);

        /*db.collection("users").document("String Tests").set(savedPosts).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SavedArticleRepository.this, "Post saved", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        /*db.collection("users").add(savedPosts).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        public void onSuccess(DocumentReference documentReference) {
            Toast.makeText(SavedArticleRepository.this, "Post saved", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SavedArticleRepository.this, "Error saving post", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }*/
}
