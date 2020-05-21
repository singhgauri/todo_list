package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    List<Item> items;
    public Context context;
    public String item;

    public ItemAdapter(Context context,List<Item> items) {

        this.context=context;
        this.items = items;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

        if(!items.isEmpty()) {
            Item currentItem = items.get(position);
            holder.textViewName.setText(currentItem.getName());
            holder.textViewDescription.setText(currentItem.getDescription());
            holder.textViewDate.setText(String.valueOf(currentItem.getDate()));
            if (currentItem.getImage()!=null) {
                holder.imageview2.setImageBitmap(DataConverter.convertByteArray2Image(currentItem.getImage()));
            }
        }

    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);

        return new ItemHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Item> items) {
       this.items=items;
    }

    public Item getItemAt(int position){
        return items.get(position);
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private TextView textViewName;
        private TextView textViewDescription;
        private TextView textViewDate;
        private ImageView imageview2;


        public ItemHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.name);
            textViewDescription = itemView.findViewById(R.id.description);
            textViewDate = itemView.findViewById(R.id.date);
            imageview2 = itemView.findViewById(R.id.imageview2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Item item = items.get(getAdapterPosition());

            Intent intent = new Intent(context, ItemUpdate.class);
            intent.putExtra("item", item);

            context.startActivity(intent);

            //Intent intent1 = new Intent(context, Reminder.class);
            //intent1.putExtra("item", item);

        }
    }
}
