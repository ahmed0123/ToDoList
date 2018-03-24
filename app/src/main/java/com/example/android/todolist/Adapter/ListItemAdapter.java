package com.example.android.todolist.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.todolist.Interfaces.ItemClickListiner;
import com.example.android.todolist.MainActivity;
import com.example.android.todolist.Model.ToDo;
import com.example.android.todolist.R;
import com.example.android.todolist.ViewHolder.ListItemViewHolder;

import java.util.List;

/**
 * Created by AHmed El Hendawy on 2018/03/24.
 */

public class ListItemAdapter extends RecyclerView.Adapter<ListItemViewHolder> {
	
	MainActivity mainActivity;
	List<ToDo> toDoList;
	
	public ListItemAdapter(MainActivity mainActivity, List<ToDo> toDoList) {
		this.mainActivity = mainActivity;
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
		
		holder.txtTitle.setText(toDoList.get(position).getTitle());
		holder.txtDescription.setText(toDoList.get(position).getDescription());
		holder.setItemClickListiner(new ItemClickListiner() {
			@Override
			public void onClick(View view, int position, boolean isLongClick) {
				mainActivity.titleText.setText(toDoList.get(position).getTitle());
				mainActivity.descriptionText.setText(toDoList.get(position).getDescription());
				
				mainActivity.isUpdate = true;
				mainActivity.idUpadte = toDoList.get(position).getId();
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return toDoList.size();
	}
	
}
