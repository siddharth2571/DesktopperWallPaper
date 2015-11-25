package com.pemikir.desktopper.adapater;

import android.content.Context;
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

import java.util.List;

/**
 * Created by iconflux-android on 9/29/2015.
 */
public class CardListAdapater extends SelectableAdapter<CardListAdapater.CardListViewHolder> {

    List<Response> cardList;
    Context context;
    itemClickLister itemclick;
    private SparseBooleanArray selectedItems;

    public CardListAdapater(Context context, List<Response> cardlist) {
        this.cardList = cardlist;
        this.context = context;
    }

    public void setItemclick(itemClickLister itemclick) {
        this.itemclick = itemclick;
    }

    @Override
    public CardListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_card, null);

        CardListViewHolder viewHolder = new CardListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CardListViewHolder holder, final int position) {

        holder.card_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemclick != null) {
                    itemclick.imageItemClicklistioner(holder.card_image, position);
                }

            }
        });

        holder.card_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(context, "Select Multiple Image", Toast.LENGTH_SHORT).show();

                if (itemclick != null) {
                    itemclick.imageItemLongClicklistioner(holder.card_image, position);
                }
                return true;
            }
        });


        Log.i("Palete", "" + cardList.get(position).getPalette().get(1).toString() + "=====" + cardList.get(position).getPalette().get(0));
        try {
            holder.bottom_plate.setBackgroundColor(Color.parseColor("#" + cardList.get(position).getPalette().get(1).toString()));
            holder.bottom_plate.bringToFront();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Picasso.with(context).load(cardList.get(position).getImage().getPreview().getUrl()).into(holder.card_image, null);

        Glide.with(context).load(cardList.get(position).getImage().getPreview().getUrl()).into(holder.card_image);

    }


    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public interface itemClickLister {

        void imageItemClicklistioner(View v, int pos);

        void imageItemLongClicklistioner(View v, int pos);

    }

    public class CardListViewHolder extends RecyclerView.ViewHolder {
        ImageView card_image;
        View bottom_plate;
        TextView tv_card_name, tv_card_description, tv_card_link;

        public CardListViewHolder(View itemView) {
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
