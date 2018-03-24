package com.example.android.todolist.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.android.todolist.Interfaces.ItemClickListiner;
import com.example.android.todolist.R;

/**
 * Created by Ahmed El Hendawy on 2018/03/24.
 */

public class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
	
	public TextView txtTitle, txtDescription;
	private ItemClickListiner itemClickListiner;
	
	public ListItemViewHolder(View itemView) {
		super(itemView);
		
		txtTitle = itemView.findViewById(R.id.list_title);
		txtDescription = itemView.findViewById(R.id.list_description);
		
		itemView.setOnClickListener(this);
		itemView.setOnCreateContextMenuListener(this);
	}
	
	public void setItemClickListiner(ItemClickListiner itemClickListiner) {
		this.itemClickListiner = itemClickListiner;
	}
	
	@Override
	public void onClick(View view) {
		itemClickListiner.onClick(view, getAdapterPosition(), false);
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
		
		contextMenu.setHeaderTitle("Select The Action");
		contextMenu.add(0, 0, getAdapterPosition(), "Delete");
	}
}
