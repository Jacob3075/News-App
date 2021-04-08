package com.jacob.newsapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jacob.newsapp.models.User;
import org.jetbrains.annotations.NotNull;

public class FirebaseAuthRepository {

    private static final String USERS = "users";
    private static FirebaseAuthRepository firebaseAuthRepository;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    private FirebaseAuthRepository() {}

    public static FirebaseAuthRepository getInstance() {
        if (firebaseAuthRepository == null) {
            firebaseAuthRepository = new FirebaseAuthRepository();
        }
        return firebaseAuthRepository;
    }

    public LiveData<User> firebaseSignInWithGoogle(AuthCredential googleAuthCredential) {
        MutableLiveData<User> authenticatedUserLiveData = new MutableLiveData<>();
        firebaseAuth
                .signInWithCredential(googleAuthCredential)
                .addOnCompleteListener(
                        authTask -> {
                            if (authTask.isSuccessful()) {
                                boolean isNewUser =
                                        authTask.getResult().getAdditionalUserInfo().isNewUser();
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                if (firebaseUser != null) {
                                    User user = getUserFromFireBaseUser(firebaseUser);
                                    user.setNew(isNewUser);
                                    authenticatedUserLiveData.setValue(user);
                                }
                            }
                        });
        return authenticatedUserLiveData;
    }

    @NotNull
    private User getUserFromFireBaseUser(FirebaseUser firebaseUser) {
        String uid = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        String photoUrl = firebaseUser.getPhotoUrl().toString();
        return new User(uid, name, email, photoUrl);
    }

    public LiveData<User> createUserInFireStoreIfNotExists(User authenticatedUser) {
        DocumentReference userRefByUid =
                FirebaseFirestore.getInstance()
                        .collection(USERS)
                        .document(authenticatedUser.getUid());
        userRefByUid
                .get()
                .addOnCompleteListener(
                        uidTask -> {
                            if (uidTask.isSuccessful()) {
                                DocumentSnapshot document = uidTask.getResult();
                                if (!document.exists()) {
                                    userRefByUid
                                            .set(authenticatedUser)
                                            .addOnCompleteListener(
                                                    userCreationTask -> {
                                                        if (userCreationTask.isSuccessful()) {
                                                            authenticatedUser.setCreated(true);
                                                            userLiveData.setValue(
                                                                    authenticatedUser);
                                                        } else {
                                                        }
                                                    });
                                } else {
                                    userLiveData.setValue(authenticatedUser);
                                }
                            }
                        });
        return userLiveData;
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public boolean isUserLoggedIn() {
        boolean isUserLoggedIn = firebaseAuth.getCurrentUser() != null;
        if (isUserLoggedIn) {
            userLiveData.setValue(getUserFromFireBaseUser(firebaseAuth.getCurrentUser()));
        }
        return isUserLoggedIn;
    }

    public void logout() {
        firebaseAuth.signOut();
    }
}
