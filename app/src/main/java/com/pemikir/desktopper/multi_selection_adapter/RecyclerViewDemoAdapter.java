package com.pemikir.desktopper.multi_selection_adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pemikir.desktopper.Model.Response;
import com.pemikir.desktopper.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDemoAdapter
        extends RecyclerView.Adapter
        <RecyclerViewDemoAdapter.ListItemViewHolder> {

    private List<Response> items;
    private SparseBooleanArray selectedItems;

    public RecyclerViewDemoAdapter(List<Response> modelData) {
        if (modelData == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        items = modelData;
        selectedItems = new SparseBooleanArray();
    }

    /**
     * Adds and item into the underlying data set
     * at the position passed into the method.
     *
     * @param newModelData The item to add to the data set.
     * @param position     The index of the item to remove.
     */
    public void addData(Response newModelData, int position) {
        items.add(position, newModelData);
        notifyItemInserted(position);
    }

    /**
     * Removes the item that currently is at the passed in position from the
     * underlying data set.
     *
     * @param position The index of the item to remove.
     */
    public void removeData(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public Response getItem(int position) {
        return items.get(position);
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.row_card, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {

        holder.card_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if (itemclick != null) {
                    itemclick.imageItemClicklistioner(holder.card_image, position);
                }*/

            }
        });

        holder.card_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                /*Toast.makeText(context, "Select Multiple Image", Toast.LENGTH_SHORT).show();

                if (itemclick != null) {
                    itemclick.imageItemLongClicklistioner(holder.card_image, position);
                }*/
                return true;
            }
        });


//        Log.i("Palete", "" + cardList.get(position).getPalette().get(1).toString() + "=====" + cardList.get(position).getPalette().get(0));
       /* try {
            holder.bottom_plate.setBackgroundColor(Color.parseColor("#" + cardList.get(position).getPalette().get(1).toString()));
            holder.bottom_plate.bringToFront();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

//        Picasso.with(context).load(cardList.get(position).getImage().getPreview().getUrl()).into(holder.card_image, null);

        // Glide.with(context).load(cardList.get(position).getImage().getPreview().getUrl()).into(holder.card_image);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        ImageView card_image;
        View bottom_plate;
        TextView tv_card_name, tv_card_description, tv_card_link;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            this.card_image = (ImageView) itemView.findViewById(R.id.card_image);
//            this.bottom_plate = (RelativeLayout) itemView.findViewById(R.id.bottom_plate);
            this.bottom_plate = itemView.findViewById(R.id.bottom_plate);

            this.tv_card_name = (TextView) itemView.findViewById(R.id.tv_card_name);
            this.tv_card_description = (TextView) itemView.findViewById(R.id.tv_card_description);
            this.tv_card_link = (TextView) itemView.findViewById(R.id.tv_card_link);

        }
    }
}