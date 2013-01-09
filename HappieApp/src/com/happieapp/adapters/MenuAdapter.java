package com.happieapp.adapters;

import java.util.List;

import com.happieapp.model.Article;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MenuAdapter extends ArrayAdapter<Article> {

	public MenuAdapter(Context context, int resource, int textViewResourceId,
			List<Article> objects) {
		super(context, resource, textViewResourceId, objects);
	}

}
