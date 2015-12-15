package com.pemikir.desktopper.adapater;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pemikir.desktopper.Model.Response;
import com.pemikir.desktopper.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by iconflux-android on 9/29/2015.
 */
public class CardListAdapater extends SelectableAdapter<CardListAdapater.CardListViewHolder> {

    List<Response> cardList;
    Context context;
    itemClickLister itemclick;


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
                    itemclick.imageItemClicklistioner(position);
                }

            }
        });

        holder.card_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(context, "Select Multiple Image", Toast.LENGTH_SHORT).show();

                if (itemclick != null) {
                    itemclick.imageItemLongClicklistioner(position);
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
        String[] MaterialColor = new String[]{"#f44336", "#E91E63", "#9C27B0", "#673AB7", "#3F51B5", "#03A9F4", "#009688", "#4CAF50", "#FF9800"};
        int randomColor = new Random().nextInt(MaterialColor.length);
        holder.card_image.setBackgroundColor(Color.parseColor(MaterialColor[randomColor]));

        Glide.with(context).load(cardList.get(position).getImage().getPreview().getUrl()).into(holder.card_image);
        holder.selected_overlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

    }


    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public interface itemClickLister {


        void imageItemClicklistioner(int pos);

        void imageItemLongClicklistioner(int pos);

    }

    public class CardListViewHolder extends RecyclerView.ViewHolder {
        ImageView card_image;
        View bottom_plate;
        TextView tv_card_name, tv_card_description, tv_card_link;

        RelativeLayout img_selection_relative;

        RelativeLayout selected_overlay;


        public CardListViewHolder(View itemView) {
            super(itemView);
            this.card_image = (ImageView) itemView.findViewById(R.id.card_image);
//            this.bottom_plate = (RelativeLayout) itemView.findViewById(R.id.bottom_plate);
            this.bottom_plate = itemView.findViewById(R.id.bottom_plate);

            this.tv_card_name = (TextView) itemView.findViewById(R.id.tv_card_name);
            this.tv_card_description = (TextView) itemView.findViewById(R.id.tv_card_description);
            this.tv_card_link = (TextView) itemView.findViewById(R.id.tv_card_link);

//            this.img_selection_relative = (RelativeLayout) itemView.findViewById(R.id.img_selection_relative);

            this.selected_overlay = (RelativeLayout) itemView.findViewById(R.id.img_selection_relative);
        }
    }

}
