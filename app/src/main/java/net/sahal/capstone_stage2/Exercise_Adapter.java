package net.sahal.capstone_stage2;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Exercise_Adapter extends RecyclerView.Adapter<Exercise_Adapter.MyViewHolder> {

    private List<Exercises> exercisesList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;


        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
        }
    }


    public Exercise_Adapter(List<Exercises> exercisesList) {
        this.exercisesList = exercisesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Exercises exercises = exercisesList.get(position);
        holder.title.setText(exercises.getExercise());
    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }
}
