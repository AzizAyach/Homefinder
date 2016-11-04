package com.ayach.org.homefinder.Adapter;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayach.org.homefinder.DAO.Favoris_Dao;
import com.ayach.org.homefinder.Entity.Logement;
import com.ayach.org.homefinder.Entity.Photo;
import com.ayach.org.homefinder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by aziz on 02/01/2016.
 */
public class RecycleviewFavorisAdapter extends RecyclerView.Adapter<RecycleviewFavorisAdapter.AdapterHolderFavoris>  {

        OnItemClickListener mItemClickListener;
    Favoris_Dao favoris_dao;
private ArrayList<Logement> listloge = new ArrayList<Logement>();
public RecycleviewFavorisAdapter(ArrayList<Logement>list){
        this.listloge=list;

        }


    @Override
    public RecycleviewFavorisAdapter.AdapterHolderFavoris onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.champlayout, parent, false);
        return  new AdapterHolderFavoris(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterHolderFavoris holder, int position) {
        Logement loge = listloge.get(position);
        try {
            holder.nom.setText(loge.getId());
            holder.pays.setText(loge.getLocation().getCountry());
            holder.ville.setText(loge.getLocation().getState());
            holder.prix.setText("" + loge.getPrice().getNightly()+"$/Night");
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
    public void remove(int position,Favoris_Dao favoris_dao) {
        Logement l = this.getItem(position);
        listloge.remove(position);
        favoris_dao.deleteLogement(l);
        notifyItemRemoved(position);
    }

    public Logement getItem(int position) {

        return listloge.get(position);
        }


@Override
public int getItemCount() {
        return listloge.size();
        }


    public class AdapterHolderFavoris extends AnimateViewHolder implements View.OnClickListener
{   Logement log;
    ImageView image;
    TextView nom;
    TextView pays;
    TextView ville;
    TextView prix;
    TextView description;
    public AdapterHolderFavoris(View row) {
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
    public void animateAddImpl(ViewPropertyAnimatorListener listener) {
        ViewCompat.setTranslationY(itemView, +itemView.getHeight() * 0.3f);
        ViewCompat.setAlpha(itemView, 0);
    }

    @Override
    public void preAnimateAddImpl() {
        ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
        ViewCompat.setAlpha(itemView, 0);
    }

    @Override
    public void animateRemoveImpl(ViewPropertyAnimatorListener listener) {
        ViewCompat.animate(this.itemView)
                .translationY(0)
                .alpha(1)
                .setDuration(300)
                .setListener(listener)
                .start();
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
