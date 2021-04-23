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
import com.jacob.newsapp.utilities.Utils;
import com.jacob.newsapp.viewmodels.ArticleViewerViewModel;
import org.jetbrains.annotations.NotNull;

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

        article = ArticleViewerArgs.fromBundle(requireArguments()).getArticle();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ArticleViewerViewModel.class);
        setUpUI();
    }

    private void setUpUI() {
        setUpMenuItems();
        setUpTopBar();
        setUpWebView();
    }

    private void setUpMenuItems() {
        MaterialToolbar topAppBar = binding.topAppBar;
        Menu menu = topAppBar.getMenu();

        updateMenuItems(menu.findItem(R.id.saveArticle));
        updateMenuItems(menu.findItem(R.id.saveSource));
        updateMenuItems(menu.findItem(R.id.saveCategory));

        topAppBar.setOnMenuItemClickListener(
                item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.saveArticle) {
                        handleSaveArticleItemClicked(item);
                        return true;
                    } else if (itemId == R.id.saveCategory) {
                        handleSaveCategoryItemClicked(item);
                        return true;
                    } else if (itemId == R.id.saveSource) {
                        handleSaveSourceItemClicked(item);
                        return true;
                    }
                    return false;
                });
    }

    private void setUpTopBar() {
        MaterialToolbar topAppBar = binding.topAppBar;
        topAppBar.setTitle(article.getTitle());

        String source = Utils.capitalize(article.getSource());
        String category = Utils.capitalize(article.getCategory());

        topAppBar.setSubtitle(source + "\t\t\t|\t\t\t" + category);
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

    private void updateMenuItems(@NotNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.saveArticle) {
            menuItem.setIcon(
                    viewModel.isArticleSaved(article)
                            ? R.drawable.saved_icon
                            : R.drawable.save_icon);
        } else if (itemId == R.id.saveSource) {
            menuItem.setTitle(
                    viewModel.isSourceSaved(article.getSource()) ? "Remove Source" : "Save Source");
        } else if (itemId == R.id.saveCategory) {
            menuItem.setTitle(
                    viewModel.isCategorySaved(article.getCategory())
                            ? "Remove Category"
                            : "Save Category");
        }
    }

    private void handleSaveArticleItemClicked(MenuItem item) {
        viewModel.saveArticleButtonPressed(article);
        updateMenuItems(item);
    }

    private void handleSaveCategoryItemClicked(MenuItem item) {
        viewModel.saveCategoryMenuItemPressed(article.getCategory());
        updateMenuItems(item);
    }

    private void handleSaveSourceItemClicked(MenuItem item) {
        viewModel.saveSourceMenuItemPressed(article.getSource());
        updateMenuItems(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(
            @NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.article_viewer_menu, menu);
    }
}
