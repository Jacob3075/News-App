package com.jacob.newsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.appbar.MaterialToolbar;
import com.jacob.newsapp.R;
import com.jacob.newsapp.databinding.ArticleViewerFragmentBinding;
import com.jacob.newsapp.viewmodels.ArticleViewerViewModel;

public class ArticleViewer extends Fragment {

    private ArticleViewerViewModel viewModel;
    private ArticleViewerFragmentBinding binding;

    public static ArticleViewer newInstance() {
        return new ArticleViewer();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ArticleViewerFragmentBinding.inflate(inflater, container, false);
        CoordinatorLayout root = binding.getRoot();
        binding.btnOpenDrawer.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_articleViewer_to_bottomSheetFragment));

//        Might Cause Errors
//        app: liftOnScroll = "true"
//        app: layout_scrollFlags = "scroll|enterAlways|snap"

        MaterialToolbar topAppBar = binding.topAppBar;
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu) {
                Navigation.findNavController(root).navigate(R.id.action_articleViewer_to_bottomSheetFragment);
                return true;
            }
            return false;
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ArticleViewerViewModel.class);
    }

}