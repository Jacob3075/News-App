package com.jacob.newsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jacob.newsapp.R;
import com.jacob.newsapp.models.Article;

import java.util.List;
import java.util.function.Consumer;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CardViewholder> {

	private final List<Article> articleList;

	public RecyclerViewAdapter(List<Article> articleList) {
		this.articleList = articleList;
	}

	@NonNull
	@Override
	public CardViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
		                          .inflate(R.layout.article_card, parent, false);
		return new CardViewholder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull CardViewholder holder, int position) {
		holder.setData(articleList.get(position));
		holder.setClickListner(view -> {

		});
	}

	@Override
	public int getItemCount() {
		return articleList.size();
	}

	static class CardViewholder extends RecyclerView.ViewHolder {

		private final ImageView articleImg;
		private final Button    saveArtlcleButton;
		private final TextView  title;
		private final TextView  source;


		public CardViewholder(@NonNull View itemView) {
			super(itemView);

			articleImg        = itemView.findViewById(R.id.imgArticleImage);
			saveArtlcleButton = itemView.findViewById(R.id.btnSaveArticle);
			title             = itemView.findViewById(R.id.tvTitle);
			source            = itemView.findViewById(R.id.tvSource);
		}

		public void setData(Article article) {
//            TODO: GLIDE
//            articleImg.
			title.setText(article.getTitle());
			source.setText(article.getSource());
		}

		public void setClickListner(Consumer<View> onClickListener) {
			saveArtlcleButton.setOnClickListener(view -> {
			});
		}
	}
}
