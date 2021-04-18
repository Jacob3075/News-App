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
    private Article article;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = ArticleViewerFragmentBinding.inflate(inflater, container, false);
        ConstraintLayout root = binding.getRoot();

        article = ArticleViewerArgs.fromBundle(getArguments()).getArticle();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ArticleViewerViewModel.class);
        setUpUI();
    }

    private void setUpUI() {
        setUpTopBar();
        setUpWebView();
    }

    private void setUpTopBar() {
        MaterialToolbar topAppBar = binding.topAppBar;
        topAppBar.setOnMenuItemClickListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.saveArticle:
                            handleSaveArticleItemClicked();
                            return true;
                        case R.id.saveCategory:
                            handleSaveCategoryItemClicked();
                            return true;
                        case R.id.saveSource:
                            handleSaveSourceItemClicked();
                            return true;
                        default:
                            return false;
                    }
                });

        topAppBar.setTitle(article.getTitle());
        topAppBar.setSubtitle(article.getSource());
    }

    private void setUpWebView() {
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

    private void handleSaveArticleItemClicked() {}

    private void handleSaveCategoryItemClicked() {}

    private void handleSaveSourceItemClicked() {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
