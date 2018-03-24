package com.example.android.todolist.Interfaces;

import android.view.View;

/**
 * Created by Ahmed El Hendawy on 2018/03/24.
 */

public interface ItemClickListiner {
	void onClick(View view, int position, boolean isLongClick);
	
}
