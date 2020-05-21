package com.example.todolist;

import android.app.Activity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ItemsAdd extends AppCompatActivity {

    ItemDao itemDao;
    public EditText additem,adddescription,adddate;
    Button button1,button2;
    Date date;
    Item item;
    ImageView imageview;
    Bitmap bmpImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_items);

        // EditText adddate;

        additem = findViewById(R.id.additems);
        adddescription = findViewById(R.id.adddescription);
        adddate = findViewById(R.id.adddate);
        button2=findViewById(R.id.takeimage);
        imageview = findViewById(R.id.imageview);
        //bmpImage =null;

        adddate.setOnClickListener(new View.OnClickListener() {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.YEAR);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ItemsAdd.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        adddate.setText(year +"-"+(month+1)+"-"+day);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



        button1 = findViewById(R.id.savebutton);

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


        final String additem1 = additem.getText().toString().trim();
        final String adddescription1 = adddescription.getText().toString().trim();
        final String adddate1 = adddate.getText().toString().trim();
        //final Byte[] image1 = DataConverter.convertImage2ByteArray(bmpImage);

        if (additem1.isEmpty()) {
            additem.setError("Item required");
            additem.requestFocus();
            return;
        }

        if (adddescription1.isEmpty()) {
            adddescription.setError("Description required");
            adddescription.requestFocus();
            return;
        }

        if (adddate1.isEmpty()) {
            adddate.setError("Date required");
            adddate.requestFocus();
            return;
        }

        //final int adddate11 = Integer.parseInt(adddate1);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(adddate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }




        class InsertItemAsyncTask extends AsyncTask<Void,Void ,Void> {
            private ItemDao itemDao;


            public InsertItemAsyncTask(ItemDao itemDao) {
                this.itemDao = itemDao;
            }

            @Override
            protected Void doInBackground(Void... voids) {

                if(bmpImage!=null) {

                    item = new Item(additem1, adddescription1, date, DataConverter.convertImage2ByteArray(bmpImage));
                }else {
                    item = new Item(additem1, adddescription1, date, null);
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
