package com.jacob.newsapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jacob.newsapp.models.User;

public class FirebaseAuthRepository {
	private static final String              USERS        = "users";
	private final        FirebaseAuth        firebaseAuth = FirebaseAuth.getInstance();
	private final        FirebaseFirestore   rootRef      = FirebaseFirestore.getInstance();
	private final        CollectionReference usersRef     = rootRef.collection(USERS);

	public LiveData<User> firebaseSignInWithGoogle(AuthCredential googleAuthCredential) {
		MutableLiveData<User> authenticatedUserLiveData = new MutableLiveData<>();
		firebaseAuth.signInWithCredential(googleAuthCredential).addOnCompleteListener(authTask -> {
			if (authTask.isSuccessful()) {
				boolean isNewUser = authTask.getResult()
				                            .getAdditionalUserInfo()
				                            .isNewUser();
				FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
				if (firebaseUser != null) {
					String uid   = firebaseUser.getUid();
					String name  = firebaseUser.getDisplayName();
					String email = firebaseUser.getEmail();
					User   user  = new User(uid, name, email);
					user.setNew(isNewUser);
					authenticatedUserLiveData.setValue(user);
				}
			} else {
//				logErrorMessage(authTask.getException().getMessage());
			}
		});
		return authenticatedUserLiveData;
	}

	public LiveData<User> createUserInFireStoreIfNotExists(User authenticatedUser) {
		MutableLiveData<User> newUserLiveData = new MutableLiveData<>();
		DocumentReference     userRefByUid    = usersRef.document(authenticatedUser.getUid());
		userRefByUid.get()
		            .addOnCompleteListener(uidTask -> {
			            if (uidTask.isSuccessful()) {
				            DocumentSnapshot document = uidTask.getResult();
				            if (!document.exists()) {
					            userRefByUid.set(authenticatedUser)
					                        .addOnCompleteListener(userCreationTask -> {
						                        if (userCreationTask.isSuccessful()) {
							                        authenticatedUser.setCreated(true);
							                        newUserLiveData.setValue(authenticatedUser);
						                        } else {
//							logErrorMessage(userCreationTask.getException().getMessage());
						                        }
					                        });
				            } else {
					            newUserLiveData.setValue(authenticatedUser);
				            }
			            } else {
//				logErrorMessage(uidTask.getException().getMessage());
			            }
		            });
		return newUserLiveData;
	}

	public boolean isUserLoggedIn() {
		return firebaseAuth.getCurrentUser() != null;
	}

	public void logout() {
		firebaseAuth.signOut();
	}
}
