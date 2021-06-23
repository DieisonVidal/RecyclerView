package com.dieisonvidal.recyclerview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder>{

    private List<Item> items = new ArrayList<>();
    private Context context;
    private OnNoteListiner onNoteListiner;

    public  ItemAdapter(Context context, OnNoteListiner onNoteListiner){
        this.context = context;
        this.onNoteListiner = onNoteListiner;
        for (int i = 0; i < 100; i++){
            Item item = new Item("Item " + (i + 1), "Item " + (i + 1)
                    , "" + (i + 1));
            items.add(item);
        }
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            ItemHolder holder = new ItemHolder(view, onNoteListiner);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item item = items.get(position);

        holder.itemName.setText(item.getItemName());
        holder.itemDescription.setText(item.getItemDescription());
        holder.itemColor.setBackgroundResource(R.drawable.bg_circle_blue);
        holder.itemNumber.setText(item.getItemNumber());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemName, itemDescription, itemNumber;
        public RelativeLayout itemColor, relButton, imgViewDelete;
        OnNoteListiner onNoteListiner;

        public ItemHolder(View itemView, OnNoteListiner onNoteListiner) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemDescription = itemView.findViewById(R.id.item_description);
            itemNumber = itemView.findViewById(R.id.item_number);
            itemColor = itemView.findViewById(R.id.item_background);
            /*imgViewDelete = itemView.findViewById(R.id.imgViewDelete);
            imgViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/
            this.onNoteListiner = onNoteListiner;
            itemView.setOnClickListener(this);
            /*relButton = itemView.findViewById(R.id.relButton);
            relButton.setOnClickListener(this);*/

        }

        @Override
        public void onClick(View v) {
            onNoteListiner.onNoteClick(getAdapterPosition());
            //confirmDeletion();

        }
       public void confirmDeletion() {
           AlertDialog.Builder deletBox = new AlertDialog.Builder(context);
           deletBox.setTitle("Deleted...");
           deletBox.setMessage("Are you sure you want to delete this item?");

           deletBox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int id) {
                   int pos = getAdapterPosition();
                   items.remove(pos);
                   Toast.makeText(context, "Item removed!", Toast.LENGTH_SHORT).show();
                   notifyItemRemoved(pos);
               }
           });
           deletBox.setNegativeButton("No", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int id) {
                   Toast.makeText(context,"No removed item!",Toast.LENGTH_SHORT).show();
               }
           });

           deletBox.show();
         }

    }
    public interface OnNoteListiner{
        void onNoteClick(int position);
    }
}
