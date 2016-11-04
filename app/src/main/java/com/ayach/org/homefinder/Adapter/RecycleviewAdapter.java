package com.ayach.org.homefinder.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayach.org.homefinder.Entity.Logement;
import com.ayach.org.homefinder.Entity.Photo;
import com.ayach.org.homefinder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aziz on 16/12/2015.
 */


public class RecycleviewAdapter extends RecyclerView.Adapter<RecycleviewAdapter.AdapterHolder> {

    OnItemClickListener mItemClickListener;

    private ArrayList<Logement> listloge = new ArrayList<Logement>();
    public RecycleviewAdapter(ArrayList<Logement>list){
        this.listloge=list;

    }
    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.champlayout, parent, false);
        return  new AdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {


        Logement loge = listloge.get(position);
        try {
            holder.nom.setText(loge.getId());
            holder.pays.setText(loge.getLocation().getCountry());
            holder.ville.setText(loge.getLocation().getState());
            holder.prix.setText("" + loge.getPrice().getNightly()+"$");
            holder.description.setText(loge.getAttr().getDescription());
            List<Photo> ph = loge.getPhotos();
            Photo phot = ph.get(0);
            Picasso.with(holder.image.getContext()).load(phot.getLarge()).into(holder.image);
        }
        catch (Exception e){
            System.out.println("erreur de chargement de contenue");


        }
        holder.log =getItem(position);
    }

    public Logement getItem(int position) {
        // TODO Auto-generated method stub
        return listloge.get(position);
    }


    @Override
    public int getItemCount() {
        return listloge.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {   Logement log;
        ImageView image;
        TextView nom;
        TextView pays;
        TextView ville;
        TextView prix;
        TextView description;

        public AdapterHolder(View row) {
            super(row);
            row.setOnClickListener(this);
            image = (ImageView) row.findViewById(R.id.imagelog);
            nom = (TextView) row.findViewById(R.id.nomRow);
            pays = (TextView) row.findViewById(R.id.paysRow);
            ville = (TextView) row.findViewById(R.id.cityRow);
            prix = (TextView) row.findViewById(R.id.PrixRow);
            description = (TextView) row.findViewById(R.id.DescRow);
        }
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());

            }
        }

    }
    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
