package com.jacob.newsapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.jacob.newsapp.R;
import com.jacob.newsapp.ui.ArticleCard;

public class HomePage extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        MaterialButton button = view.findViewById(R.id.button);
        button.setOnClickListener(this::openArticleCard);
        return view;
    }

    private void openArticleCard(View onClick) {
        Intent intent = new Intent(getActivity(), ArticleCard.class);
        startActivity(intent);
    }
}