package com.jacob.newsapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.jacob.newsapp.MainActivity;
import com.jacob.newsapp.R;
import com.jacob.newsapp.databinding.LoginScreenBinding;
import com.jacob.newsapp.models.User;
import com.jacob.newsapp.viewmodels.LoginScreenViewModel;
import org.jetbrains.annotations.NotNull;

public class LoginScreen extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    private LoginScreenBinding binding;
    private LoginScreenViewModel viewModel;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginScreenBinding.inflate(getLayoutInflater());
        ConstraintLayout root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(LoginScreenViewModel.class);

        setContentView(root);
        initGoogleSignInClient();
        setUpButtons();
    }

    private void initGoogleSignInClient() {
        GoogleSignInOptions googleSignInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    private void setUpButtons() {
        binding.loginButton.setOnClickListener(this::signIn);
        binding.signUpButton.setOnClickListener(this::signIn);
        binding.homePage.setOnClickListener(this::openHomePage);
    }

    private void signIn(View view) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        googleSignInClient.signOut();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void openHomePage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInAccount googleSignInAccount =
                    GoogleSignIn.getSignedInAccountFromIntent(data).getResult();
            if (googleSignInAccount != null) {
                getGoogleAuthCredential(googleSignInAccount);
            }
        }
    }

    private void getGoogleAuthCredential(@NotNull GoogleSignInAccount googleSignInAccount) {
        String googleTokenId = googleSignInAccount.getIdToken();
        AuthCredential googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null);
        signInWithGoogleAuthCredential(googleAuthCredential);
    }

    private void signInWithGoogleAuthCredential(AuthCredential googleAuthCredential) {
        viewModel.signInWithGoogle(googleAuthCredential);
        viewModel
                .getAuthenticatedUserLiveData()
                .observe(
                        this,
                        authenticatedUser -> {
                            if (authenticatedUser.isNew()) {
                                createNewUser(authenticatedUser);
                            } else {
                                openHomePage();
                            }
                        });
    }

    private void createNewUser(User authenticatedUser) {
        viewModel.createUser(authenticatedUser);
        viewModel.getCreatedUserLiveData().observe(this, user -> openHomePage());
    }

    private void openHomePage() {
        openHomePage(null);
    }

    private void openSignUpPage(View view) {
        Intent intent = new Intent(this, SignUpScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }
}
