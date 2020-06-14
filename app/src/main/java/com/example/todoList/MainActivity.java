package com.example.todoList;

import android.content.Intent;
import android.os.AsyncTask;
//import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private List<Item> items;
    private ItemDao itemDao;
    private int i1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        items = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ItemAdapter(this,items);
        //adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


        FloatingActionButton buttonAdd = findViewById(R.id.fab);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ItemsAdd.class);
                startActivity(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                delete(adapter.getItemAt(viewHolder.getAdapterPosition()));
                i1 = viewHolder.getAdapterPosition();
                Toast.makeText(MainActivity.this,"Deleted",Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);


        getAllItems();

        /*if(items.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch( item.getItemId() ){

            case R.id.delete_all_items:

                deleteAllItems();
            Toast.makeText(this, "All Items Deleted", Toast.LENGTH_SHORT).show();
            return true;

            case R.id.logout:

            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            startActivity(new Intent(MainActivity.this, FirstActivity.class));
            finish();
            return true;


            case R.id.deleteAccount:

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Account Deleted", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainActivity.this,FirstActivity.class));
                                Log.d(TAG, "User account deleted.");
                            }else {
                                Toast.makeText(MainActivity.this, "Failed to delete Account", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            return true;

            default :
                return super.onOptionsItemSelected(item);
        }}




    private void delete(Item item) {

        class DeleteItemAsyncTask extends AsyncTask<Item,Void ,Void> {


            DeleteItemAsyncTask(ItemDao itemDao) {
                new MainActivity().itemDao = itemDao;
            }


            @Override
            protected Void doInBackground(Item... items) {
                ItemDatabase.getInstance(MainActivity.this).itemDao().delete(items[0]);

                //items[0] = null;

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                items.remove(0);
                adapter.notifyItemRemoved(i1);

            }
        }


        new DeleteItemAsyncTask(itemDao).execute(item);

    }


    private void deleteAllItems(){


        class DeleteAllItemsAsyncTask extends AsyncTask<Void,Void ,Void> {

            DeleteAllItemsAsyncTask(ItemDao itemDao) {
                new MainActivity().itemDao = itemDao;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                ItemDatabase.getInstance(MainActivity.this).itemDao().deleteAllItems();
                items.clear();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();

            }
        }

        new DeleteAllItemsAsyncTask(itemDao).execute();

    }
    private void getAllItems(){

        //final List<Item> items = new ArrayList<>();
        class GetAllItems extends AsyncTask<Void, Void, List<Item>> {


            GetAllItems(ItemDao itemDao) {
                new MainActivity().itemDao = itemDao;
            }

            @Override
            protected List<Item> doInBackground(Void... voids) {
                items = ItemDatabase.getInstance(MainActivity.this).itemDao().getAllItems();
                return items;

            }

            @Override
            protected void onPostExecute(List<Item> items) {
                super.onPostExecute(items);
                adapter.setItems(items);
                adapter.notifyDataSetChanged();

            }
        }

        GetAllItems gt = new GetAllItems(itemDao);
        gt.execute();

        Log.d(TAG,"executed");

    }

}





















