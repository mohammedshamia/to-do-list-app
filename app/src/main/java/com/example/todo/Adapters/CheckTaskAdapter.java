package com.example.todo.Adapters;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Pages.CheckTaskClass;
import com.example.todo.R;

import java.util.List;

public class CheckTaskAdapter extends RecyclerView.Adapter<CheckTaskAdapter.ViewHolder> {

    private final Context context;
    List<CheckTaskClass> tasksList;
    ListItemClickListener mListItemClickListener;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox checkBox;
        ListItemClickListener listItemClickListener;
        public ViewHolder(View view,ListItemClickListener listItemClickListener) {
            super(view);
            // Define click listener for the ViewHolder's View
            checkBox = itemView.findViewById(R.id.Checkbox);
            this.listItemClickListener = listItemClickListener;
            itemView.setOnClickListener(this);
            view.setOnClickListener(this);


        }

        public void setData(final CheckTaskClass task) {
            checkBox.setText(task.getTitle());
            checkBox.setSelected(task.getIsChecked());
        }

        @Override
        public void onClick(View v) {
            listItemClickListener.onListItemClick(getAdapterPosition());
        }
    }

    public CheckTaskAdapter(Context context, List<CheckTaskClass> tasksList, ListItemClickListener listItemClickListener) {
        this.context = context;
        this.tasksList = tasksList;
        this.mListItemClickListener=listItemClickListener;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_choice, viewGroup, false);

        return new ViewHolder(view,mListItemClickListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setData(tasksList.get(position));
        CheckTaskClass taskEntity = tasksList.get(position);
        if(taskEntity.getIsChecked()){
            holder.checkBox.setChecked(true);
            holder.checkBox.setPaintFlags( holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                taskEntity.setIsChecked(isChecked);
                holder.checkBox.setSelected(isChecked);
                if(isChecked){
                    holder.checkBox.setText(taskEntity.getTitle());
                    holder.checkBox.setPaintFlags( holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else {
                    holder.checkBox.setText(taskEntity.getTitle());
                    holder.checkBox.setPaintFlags( holder.checkBox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }

            }
        });
    }
    public interface ListItemClickListener {
        void onListItemClick(int position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tasksList.size();
    }
}


