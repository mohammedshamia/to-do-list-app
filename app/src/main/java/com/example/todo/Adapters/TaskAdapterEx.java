package com.example.todo.Adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.Pages.TaskClass;

import java.util.List;

public class TaskAdapterEx extends RecyclerView.Adapter<TaskAdapterEx.ViewHolder> {
    ListItemClickListener mListItemClickListener;
    List<TaskClass> taskClassList;
    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView title, count;
        ListItemClickListener listItemClickListener;

        public ViewHolder(View view, ListItemClickListener listItemClickListener) {
            super(view);
            this.listItemClickListener = listItemClickListener;
            title = (TextView) view.findViewById(R.id.titleTask);
            count = (TextView) view.findViewById(R.id.count);
            view.setOnClickListener(this);
        }

        public void setData(TaskClass taskClass) {
            title.setText(taskClass.getTitle());
            count.setText("" + taskClass.getCount());
        }
        public void onClick(View v) {
            listItemClickListener.onListItemClick(getAdapterPosition());
        }
    }

    public TaskAdapterEx(List<TaskClass> taskClassList, ListItemClickListener listItemClickListener) {
       this.taskClassList = taskClassList;
        this.mListItemClickListener = listItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_taskitem, viewGroup, false);

        return new ViewHolder(view,mListItemClickListener);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.setData(taskClassList.get(position));
    }
    @Override
    public int getItemCount() {
        return taskClassList.size();
    }
    public interface ListItemClickListener {
        void onListItemClick(int position);
    }
}


