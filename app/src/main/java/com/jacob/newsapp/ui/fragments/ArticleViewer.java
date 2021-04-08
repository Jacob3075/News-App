package com.jacob.newsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.appbar.MaterialToolbar;
import com.jacob.newsapp.R;
import com.jacob.newsapp.databinding.ArticleViewerFragmentBinding;
import com.jacob.newsapp.viewmodels.ArticleViewerViewModel;

public class ArticleViewer extends Fragment {

    private ArticleViewerViewModel viewModel;
    private ArticleViewerFragmentBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = ArticleViewerFragmentBinding.inflate(inflater, container, false);
        CoordinatorLayout root = binding.getRoot();

        MaterialToolbar topAppBar = binding.topAppBar;
        topAppBar.setOnMenuItemClickListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.saveArticle:
                        case R.id.saveCategory:
                        case R.id.saveSource:
                        case R.id.searchCategory:
                        case R.id.searchSource:
                            {
                                return false;
                            }
                        default:
                            return false;
                    }
                });

        topAppBar.setTitle("Some New Title");
        topAppBar.setSubtitle("Some New SubTitle");

        WebView webView = binding.webView;
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("www.google.com");
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ArticleViewerViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
