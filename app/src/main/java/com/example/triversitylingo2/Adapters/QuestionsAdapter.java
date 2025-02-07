package com.example.triversitylingo2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.triversitylingo2.Models.QuestionModel;
import com.example.triversitylingo2.R;
import com.example.triversitylingo2.databinding.ItemQuestionsBinding;

import java.util.ArrayList;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.viewHolder>{

    Context context;
    ArrayList<QuestionModel> list;

    public QuestionsAdapter(Context context, ArrayList<QuestionModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_questions, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        QuestionModel model = list.get(position);
        holder.binding.question2.setText(model.getQuestion());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ItemQuestionsBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemQuestionsBinding.bind(itemView);
        }
    }
}
