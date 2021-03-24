package com.jacob.newsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.jacob.newsapp.R;
import com.jacob.newsapp.databinding.FragmentHomePageBinding;

public class HomePage extends Fragment {

    private FragmentHomePageBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomePageBinding.inflate(inflater, container, false);
        ConstraintLayout root = binding.getRoot();
        binding.btnOpenArticle.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_homePage_to_articleViewer));
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}