package com.jacob.newsapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jacob.newsapp.models.User;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.jacob.newsapp.utilities.Constants.USERS;

public class FirebaseAuthRepository {

    private static FirebaseAuthRepository firebaseAuthRepository;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> authenticationResultLiveData = new MutableLiveData<>();

    private FirebaseAuthRepository() {}

    public static FirebaseAuthRepository getInstance() {
        if (firebaseAuthRepository == null) {
            firebaseAuthRepository = new FirebaseAuthRepository();
        }
        return firebaseAuthRepository;
    }

    public void register(String userName, String email, String password) {
        firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                if (currentUser != null) {
                                    User newUser = getUserFromFireBaseUser(currentUser);

                                    newUser.setName(userName);
                                    newUser.setSavedArticles(new ArrayList<>());
                                    newUser.setSavedCategories(new ArrayList<>());
                                    newUser.setSavedSources(new ArrayList<>());

                                    createUserInFireStoreIfNotExists(newUser);
                                }
                            } else {
                                authenticationResultLiveData.setValue(false);
                            }
                        });
    }

    public void login(String email, String password) {
        firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        authResultTask -> {
                            if (!authResultTask.isSuccessful()) {
                                authenticationResultLiveData.setValue(false);
                                return;
                            }

                            AuthResult authResultTaskResult = authResultTask.getResult();
                            if (authResultTaskResult == null) {
                                authenticationResultLiveData.setValue(false);
                                return;
                            }

                            FirebaseUser firebaseUser = authResultTaskResult.getUser();
                            if (firebaseUser == null) {
                                authenticationResultLiveData.setValue(false);
                                return;
                            }
                            getUserFromFireStore(firebaseUser.getUid());
                        });
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

    public void createUserInFireStoreIfNotExists(@NotNull User authenticatedUser) {
        DocumentReference userRefByUid =
                FirebaseFirestore.getInstance()
                        .collection(USERS)
                        .document(authenticatedUser.getUid());
        userRefByUid
                .get()
                .addOnSuccessListener(
                        documentSnapshot -> {
                            if (documentSnapshot == null) {
                                authenticationResultLiveData.setValue(false);
                                return;
                            }
                            if (documentSnapshot.exists()) return;

                            userRefByUid
                                    .set(authenticatedUser)
                                    .addOnCompleteListener(
                                            userCreationTask -> {
                                                if (userCreationTask.isSuccessful()) {
                                                    userLiveData.setValue(authenticatedUser);
                                                    authenticationResultLiveData.setValue(true);
                                                }
                                            });
                        })
                .addOnFailureListener(e -> authenticationResultLiveData.setValue(false));
    }

    public void logout() {
        firebaseAuth.signOut();
        authenticationResultLiveData.setValue(null);
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Boolean> getAuthenticationResultLiveData() {
        return authenticationResultLiveData;
    }

    public boolean isUserLoggedIn() {
        boolean isUserLoggedIn = firebaseAuth.getCurrentUser() != null;
        if (isUserLoggedIn) {
            getUserFromFireStore(firebaseAuth.getCurrentUser().getUid());
        }
        return isUserLoggedIn;
    }

    @NotNull
    private User getUserFromFireBaseUser(@NotNull FirebaseUser firebaseUser) {
        String uid = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        return new User(uid, name, email);
    }
}
