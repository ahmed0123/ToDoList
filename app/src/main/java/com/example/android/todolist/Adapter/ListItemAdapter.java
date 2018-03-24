package com.example.android.todolist.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.todolist.Model.ToDo;
import com.example.android.todolist.R;
import com.example.android.todolist.ViewHolder.ListItemViewHolder;

import java.util.List;

/**
 * Created by AHmed El Hendawy on 2018/03/24.
 */

public class ListItemAdapter extends RecyclerView.Adapter<ListItemViewHolder> {
	
	Context context;
	List<ToDo> toDoList;
	
	public ListItemAdapter(Context context, List<ToDo> toDoList) {
		this.context = context;
		this.toDoList = toDoList;
	}
	
	@NonNull
	@Override
	public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.list_item, parent, false);
		return new ListItemViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
	
	}
	
	@Override
	public int getItemCount() {
		return toDoList.size();
	}
}
