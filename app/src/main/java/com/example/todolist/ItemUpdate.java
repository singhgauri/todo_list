package com.example.todolist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ItemUpdate extends AppCompatActivity {

    public EditText name, description, date1;
    public ItemDao itemDao;

    Item item,item1;
    Button button1,button2;
    String name1,description1,date0,date11,strDate;
    //int date11;
    Date date;
    int i;
    Date date111;
    ImageView imageview1;
    Bitmap bmpImage,bmpImage1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_update);

        name = findViewById(R.id.updateitem);
        description = findViewById(R.id.updatedescription);
        date1 = findViewById(R.id.updatedate);
        button1 = findViewById(R.id.reminderbutton);
        button2 = findViewById(R.id.takeimage1);
        imageview1 = findViewById(R.id.imageview1);

        //Item item = (Item) getIntent().getSerializableExtra("item");

        item = (Item) getIntent().getSerializableExtra("item");
        loadItem(item);
        i = item.getId();

        item1 = item;

        date0 = date1.getText().toString().trim();


        /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            date111 = format.parse(date0);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        date111 = new Date(date0);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        strDate = formatter.format(date111);


        date1.setText(strDate);

        date1.setOnClickListener(new View.OnClickListener() {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.YEAR);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ItemUpdate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        date1.setText(year +"-"+(month+1)+"-"+day);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        Log.d("ABC","RRR");

        findViewById(R.id.updatebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(item);
                Log.d("ABC","BBB");

            }
        });

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //String strdt =date1.getText().toString();
                Intent intent = new Intent(ItemUpdate.this,Reminder.class);
               intent.putExtra("id", i);
                intent.putExtra("name", name.getText().toString().trim());
                intent.putExtra("description", description.getText().toString().trim());
                intent.putExtra("date", strDate);

                startActivity(intent);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture1(v);
            }
        });
    }

    final int CAMERA_INTENT = 51;

    public void takePicture1(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) !=null){
            startActivityForResult(intent,CAMERA_INTENT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case CAMERA_INTENT:
                //if (requestCode== Activity.RESULT_OK){
                bmpImage = (Bitmap) data.getExtras().get("data");
                if(bmpImage !=null){
                    imageview1.setImageBitmap(bmpImage);
                }
                //}
                break;
        }
    }




    public void update(Item item){

        //name = findViewById(R.id.updateitem);
       // description = findViewById(R.id.updatedescription);
        //date = findViewById(R.id.updatedate);

        name1= name.getText().toString().trim();
        description1 = description.getText().toString().trim();
        date11= date1.getText().toString().trim();
        if (imageview1.getDrawable()!=null) {
            bmpImage1 = ((BitmapDrawable) imageview1.getDrawable()).getBitmap();
        }else{
            bmpImage1 = null;
        }
        Log.d("ABC","A");

        if (name1.isEmpty()) {
            name.setError("Item required");
            name.requestFocus();
            return;
        }

        if (description1.isEmpty()) {
            description.setError("Description required");
            description.requestFocus();
            return;
        }

        if (date11.isEmpty()) {
            date1.setError("Date required");
            date1.requestFocus();
            return;
        }

        //date11 = Integer.parseInt(date1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(date11);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        class UpdateItemAsyncTask extends AsyncTask<Item,Void ,Void> {


            public ItemDao itemDao;
            public UpdateItemAsyncTask(ItemDao itemDao) {
                this.itemDao = itemDao;
            }

            @Override
            protected Void doInBackground(Item... items) {

                Log.d("ABC","B");
                if(bmpImage1!=null) {
                    ItemDatabase.getInstance(ItemUpdate.this).itemDao().update(i, name1, description1, date,DataConverter.convertImage2ByteArray(bmpImage1));
                } else {
                    ItemDatabase.getInstance(ItemUpdate.this).itemDao().update(i, name1, description1, date,null);
                }
                Log.d("ABC","updated");
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                //adapter.notifyDataSetChanged();
                startActivity(new Intent(ItemUpdate.this, MainActivity.class));
            }
        }

        new UpdateItemAsyncTask(itemDao).execute(item);
        Log.d("ABC","executed");

    }

    private void loadItem(Item item) {

        name.setText(item.getName());
        description.setText(item.getDescription());
        date1.setText(String.valueOf(item.getDate()));
        if (item.getImage()!=null) {
            imageview1.setImageBitmap(DataConverter.convertByteArray2Image(item.getImage()));
        }
        Log.d("ABC","finally");
        //item1 =new Item(item.getName(),);
    }
}
