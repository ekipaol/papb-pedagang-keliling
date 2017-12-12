package com.example.eki.pedagangkelilingpapb;

/**
 * Created by Eki on 10/12/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private Context context;
    private List<MyData> my_data;
    int positionGlobal;
    String namaPedagang;
    public CustomAdapter(Context context, List<MyData> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position1) {
        holder.description.setText(my_data.get(position1).getDescription());
        holder.jarak.setText(my_data.get(position1).getJarak()+" m Dari Lokasi Anda");
       positionGlobal=my_data.get(position1).getId();
        namaPedagang=my_data.get(position1).getDescription();
        Glide.with(context).load(my_data.get(position1).getImage_link()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView description, jarak;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.description);
            imageView =(ImageView) itemView.findViewById(R.id.image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(imageView.getContext(), MapPedagang.class);
                    String emailPedagang="pedagang5";
                    if(my_data.get(positionGlobal).getId()==12){
                         emailPedagang="pedagang12";
                    }
                    else if (my_data.get(positionGlobal).getId()==6){
                         emailPedagang="pedagang6";
                    }


                    intent.putExtra("data", emailPedagang);
                    intent.putExtra("nama_pedagang",namaPedagang);
                    imageView.getContext().startActivity(intent);

//                    Intent intent = new Intent(imageView.getContext(), Home.class);
//                    intent.putExtra("email", "e");
//                    imageView.getContext(). startActivity(intent);
                }
            });
            jarak = (TextView) itemView.findViewById(R.id.jarak);
        }
    }
}
