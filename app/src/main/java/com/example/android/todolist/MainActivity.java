package com.example.android.todolist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.todolist.Adapter.ListItemAdapter;
import com.example.android.todolist.Model.ToDo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
	
	public MaterialEditText titleText, descriptionText;
	public boolean isUpdate = false;
	public String idUpadte = "";
	List<ToDo> toDoList = new ArrayList<>();
	FirebaseFirestore database;
	RecyclerView listItems;
	RecyclerView.LayoutManager layoutManager;
	ListItemAdapter adapter;
	android.app.AlertDialog dialog;
	FloatingActionButton addButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//init firestore
		database = FirebaseFirestore.getInstance();
		
		//views static setup
		dialog = new SpotsDialog(this);
		
		titleText = findViewById(R.id.title);
		descriptionText = findViewById(R.id.description);
		
		addButton = findViewById(R.id.add_button);
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// to adding new task
				if (!isUpdate) {
					setData(titleText.getText().toString(), descriptionText.getText().toString());
				} else {
					updateData(titleText.getText().toString(), descriptionText.getText().toString());
					isUpdate = !isUpdate;
				}
				
			}
		});
		
		listItems = findViewById(R.id.todoList);
		listItems.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(this);
		listItems.setLayoutManager(layoutManager);
		
		loadData();

	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getTitle().equals("Delete"))
			deleteItem(item.getOrder());
		
		return super.onContextItemSelected(item);
		
	}
	
	private void deleteItem(int index) {
		database.collection("ToDoList")
				.document(toDoList.get(index).getId())
				.delete()
				.addOnSuccessListener(new OnSuccessListener<Void>() {
					@Override
					public void onSuccess(Void aVoid) {
						loadData();
					}
				});
		
	}
	
	private void updateData(String title, String description) {
		database.collection("ToDoList").document(idUpadte)
				.update("title", title, "description", description)
				.addOnSuccessListener(new OnSuccessListener<Void>() {
					@Override
					public void onSuccess(Void aVoid) {
						Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
						
					}
				});
		
		database.collection("ToDoList").document(idUpadte)
				.addSnapshotListener(new EventListener<DocumentSnapshot>() {
					@Override
					public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
						loadData();
					}
				});
	}
	
	private void setData(String title, String description) {
		String id = UUID.randomUUID().toString();
		Map<String, Object> todo = new HashMap<>();
		todo.put("id", id);
		todo.put("title", title);
		todo.put("description", description);
		database.collection("ToDoList").document(id)
				.set(todo).addOnCompleteListener(new OnCompleteListener<Void>() {
			@Override
			public void onComplete(@NonNull Task<Void> task) {
				loadData();
			}
		});
		
	}
	
	private void loadData() {
		
		dialog.show();
		if (toDoList.size() > 0) {
			toDoList.clear();
		}
		database.collection("ToDoList")
				.get()
				.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
					@Override
					public void onComplete(@NonNull Task<QuerySnapshot> task) {
						
						for (DocumentSnapshot doc : task.getResult()) {
							
							ToDo toDo = new ToDo(doc.getString("id"),
									doc.getString("title"),
									doc.getString("description"));
							
							toDoList.add(toDo);
							
						}
						adapter = new ListItemAdapter(MainActivity.this, toDoList);
						listItems.setAdapter(adapter);
						dialog.dismiss();
						
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
	}
}
