package com.example.triversitylingo2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triversitylingo2.Models.SetModel;
import com.example.triversitylingo2.R;
import com.example.triversitylingo2.databinding.ItemSetsBinding;

import java.util.ArrayList;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.viewHolder>{

    private final Context context;
    private final ArrayList<SetModel> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(SetModel setModel);
    }

    public SetAdapter(Context context, ArrayList<SetModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sets, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final SetModel model = list.get(position);
        holder.binding.setName.setText(model.getSetName());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        ItemSetsBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSetsBinding.bind(itemView);
        }
    }
}
