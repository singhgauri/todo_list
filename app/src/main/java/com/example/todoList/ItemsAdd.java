package com.example.todoList;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.todoList.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ItemsAdd extends AppCompatActivity {

    ItemDao itemDao;
    public EditText addItem,addDescription,addDate;
    Button button1,button2;
    Date date;
    Item item;
    ImageView imageview;
    Bitmap bmpImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_items);



        addItem = findViewById(R.id.addItems);
        addDescription = findViewById(R.id.addDescription);
        addDate = findViewById(R.id.addDate);
        button2=findViewById(R.id.takeImage);
        imageview = findViewById(R.id.imageView);
        //bmpImage =null;

        addDate.setOnClickListener(new View.OnClickListener() {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.YEAR);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ItemsAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        addDate.setText(year +"-"+(month+1)+"-"+day);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



        button1 = findViewById(R.id.saveButton);

        button1.setOnClickListener(new View.OnClickListener() {

                                      @Override
                                      public void onClick(View v) {
                                          insert();
                                      }
                                  }
        );

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture(v);
            }
        });
    }


    final int CAMERA_INTENT = 51;

    public void takePicture(View view){
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
                    imageview.setImageBitmap(bmpImage);
                }
            //}
            break;
        }
    }

    public  void insert() {


        final String addItem1 = addItem.getText().toString().trim();
        final String addDescription1 = addDescription.getText().toString().trim();
        final String addDate1 = addDate.getText().toString().trim();
        //final Byte[] image1 = DataConverter.convertImage2ByteArray(bmpImage);

        if (addItem1.isEmpty()) {
            addItem.setError("Item required");
            addItem.requestFocus();
            return;
        }

        if (addDescription1.isEmpty()) {
            addDescription.setError("Description required");
            addDescription.requestFocus();
            return;
        }

        if (addDate1.isEmpty()) {
            addDate.setError("Date required");
            addDate.requestFocus();
            return;
        }



        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(addDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }




        class InsertItemAsyncTask extends AsyncTask<Void,Void ,Void> {


            public InsertItemAsyncTask(ItemDao itemDao) {
                new ItemsAdd().itemDao = itemDao;
            }

            @Override
            protected Void doInBackground(Void... voids) {

                if(bmpImage!=null) {

                    item = new Item(addItem1, addDescription1, date, DataConverter.convertImage2ByteArray(bmpImage));
                }else {
                    item = new Item(addItem1, addDescription1, date, null);
                }



                ItemDatabase.getInstance(ItemsAdd.this).itemDao().insert(item);
                return null;


            }



            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(ItemsAdd.this, MainActivity.class));
            }



        }

        new InsertItemAsyncTask(itemDao).execute();







    }




}
