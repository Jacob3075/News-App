package com.jacob.newsapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.AuthResult;
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
    private final MutableLiveData<Boolean> authenticationResultLiveData = new MutableLiveData<>();

    private FirebaseAuthRepository() {}

    public static FirebaseAuthRepository getInstance() {
        if (firebaseAuthRepository == null) {
            firebaseAuthRepository = new FirebaseAuthRepository();
        }
        return firebaseAuthRepository;
    }

    public void register(String email, String password, String userName) {
        firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                if (currentUser != null) {
                                    User newUser = getUserFromFireBaseUser(currentUser);
                                    newUser.setName(userName);
                                    createUserInFireStoreIfNotExists(newUser);
                                }
                            } else {
                                authenticationResultLiveData.setValue(false);
                            }
                        });
    }

    @NotNull
    private User getUserFromFireBaseUser(@NotNull FirebaseUser firebaseUser) {
        String uid = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        return new User(uid, name, email);
    }

    public void createUserInFireStoreIfNotExists(@NotNull User authenticatedUser) {
        DocumentReference userRefByUid =
                FirebaseFirestore.getInstance()
                        .collection(USERS)
                        .document(authenticatedUser.getUid());
        userRefByUid
                .get()
                .addOnCompleteListener(
                        uidTask -> {
                            if (!uidTask.isSuccessful()) {
                                authenticationResultLiveData.setValue(false);
                                return;
                            }
                            DocumentSnapshot document = uidTask.getResult();
                            if (document == null) {
                                authenticationResultLiveData.setValue(false);
                                return;
                            }

                            userRefByUid
                                    .set(authenticatedUser)
                                    .addOnCompleteListener(
                                            userCreationTask -> {
                                                if (userCreationTask.isSuccessful()) {
                                                    authenticatedUser.setCreated(true);
                                                    userLiveData.setValue(authenticatedUser);
                                                    authenticationResultLiveData.setValue(true);
                                                }
                                            });
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

                            User user = getUserFromFireBaseUser(firebaseUser);
                            userLiveData.setValue(user);
                            authenticationResultLiveData.setValue(true);
                        });
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

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Boolean> getAuthenticationResultLiveData() {
        return authenticationResultLiveData;
    }
}
