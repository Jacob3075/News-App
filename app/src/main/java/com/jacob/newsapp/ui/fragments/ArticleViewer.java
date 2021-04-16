package com.jacob.newsapp.ui.fragments;

import android.os.Bundle;
import android.view.*;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.jacob.newsapp.R;
import com.jacob.newsapp.databinding.ArticleViewerFragmentBinding;
import com.jacob.newsapp.models.Article;
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
        ConstraintLayout root = binding.getRoot();

        Article article = ArticleViewerArgs.fromBundle(getArguments()).getArticle();

        setUpTopBar(article);
        setUpWebView(article);

        return root;
    }

    private void setUpTopBar(Article article) {
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

        topAppBar.setTitle(article.getTitle());
        topAppBar.setSubtitle(article.getSource());
    }

    private void setUpWebView(Article article) {
        WebView webView = binding.webView;
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(article.getUrl());
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setOnKeyListener(
                (v, keyCode, event) ->
                        keyCode == KeyEvent.KEYCODE_BACK
                                && event.getAction() == MotionEvent.ACTION_UP
                                && webView.canGoBack());
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
