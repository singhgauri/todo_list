package com.example.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemAdapter adapter;
    RecyclerView.LayoutManager LinearLayoutManger;
    List<Item> items;
    Button buttonAdd;
    public ItemDao itemDao;
    RecyclerView.ViewHolder viewHolder;
    Iterator iterator;
    int i1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

         ItemDao itemDao;
         items = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ItemAdapter(this,items);
        //adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


        buttonAdd = findViewById(R.id.addbutton);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.delete_all_items:
                deleteAllItems();
                Toast.makeText(this,"All Items Deleted" ,Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //private ItemDao itemDao;
    //private List<Item> allItems;


    public void delete(Item item) {

        class DeleteItemAsyncTask extends AsyncTask<Item,Void ,Void> {
            private ItemDao itemDao;
            Item item;


            public DeleteItemAsyncTask(ItemDao itemDao) {
                this.itemDao = itemDao;
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

                /*Iterator i = items.iterator();
                String str = "";
                while (i.hasNext()) {
                    str = (String) i.next();
                    if (str.equals(item)) {
                        i.remove();
                        break;
                    }
                }*/


                items.remove(0);
                adapter.notifyItemRemoved(i1);

            }
        }


        new DeleteItemAsyncTask(itemDao).execute(item);

    }


    public void deleteAllItems(){


        class DeleteAllItemsAsyncTask extends AsyncTask<Void,Void ,Void> {
            private ItemDao itemDao;

            public DeleteAllItemsAsyncTask(ItemDao itemDao) {
                this.itemDao = itemDao;
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
                //items.remove(adapter.getItemAt(getAdapterPosition()));
                //adapter.setItems(items);
                adapter.notifyDataSetChanged();

            }
        }

        new DeleteAllItemsAsyncTask(itemDao).execute();

    }
    public void getAllItems(){

        //final List<Item> items = new ArrayList<>();
        class GetAllItems extends AsyncTask<Void, Void, List<Item>> {
            private ItemDao itemDao;

            public GetAllItems(ItemDao itemDao) {
                this.itemDao = itemDao;
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





















