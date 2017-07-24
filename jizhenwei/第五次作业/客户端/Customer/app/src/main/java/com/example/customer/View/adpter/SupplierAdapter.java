package com.example.customer.View.adpter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.customer.Model.Supplier;
import com.example.customer.R;
import com.example.customer.View.frag.OrderFrag;

import java.util.List;

/**
 * Created by 小吉哥哥 on 2017/7/22.
 */

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.ViewHolder>{
    Context context;
    List<Supplier> list;
    OrderFrag frag;
    public SupplierAdapter(Context context, List<Supplier> list, OrderFrag frag) {
        this.context = context;
        this.list = list;
        this.frag = frag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplier_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag.supplierSelected = list.get(holder.getAdapterPosition());
                frag.mode = OrderFrag.MODE_DISH;
                frag.onRefresh();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Supplier supplier = list.get(position);
        holder.name.setText(supplier.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.supplier_item_card);
            name = (TextView) itemView.findViewById(R.id.supplier_item_name);
        }
    }
}
