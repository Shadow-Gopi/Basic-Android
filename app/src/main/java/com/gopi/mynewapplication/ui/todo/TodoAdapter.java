package com.gopi.mynewapplication.ui.todo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gopi.mynewapplication.data.database.entity.TodoEntity;
import com.gopi.mynewapplication.databinding.ItemTodoBinding;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TodoAdapter extends ListAdapter<TodoEntity, TodoAdapter.ViewHolder> {

    private OnClickListener onClickListener;

    private static final DiffUtil.ItemCallback<TodoEntity> DOWNLOADED_VIDEO_ITEM_CALLBACK = new DiffUtil.ItemCallback<TodoEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull TodoEntity oldItem, @NonNull @NotNull TodoEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull TodoEntity oldItem, @NonNull @NotNull TodoEntity newItem) {
            return oldItem.equals(newItem);
        }
    };


    public TodoAdapter(OnClickListener onClickListener) {
        super(DOWNLOADED_VIDEO_ITEM_CALLBACK);
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TodoAdapter.ViewHolder(ItemTodoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(TodoAdapter.ViewHolder holder, int position) {
        TodoEntity item = getItem(position);
        if (item != null) {
            holder.binding.tvCardTitle.setText(item.getTitle());
            holder.binding.tvCardDetail.setText(item.getDetail());
            long epoch = item.getDate() * 1000;

            SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            String formattedDate = sdf.format(new Date(epoch));

            holder.binding.tvCardDate.setText(formattedDate);

            holder.binding.ivEdit.setOnClickListener(v -> {
                if (onClickListener != null){
                    onClickListener.onEditClick(item);
                }
            });

            holder.binding.ivDelete.setOnClickListener(v -> {
                if (onClickListener != null){
                    onClickListener.onDeleteClick(item.getId());
                }
            });
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemTodoBinding binding;

        public ViewHolder(ItemTodoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnClickListener{
        void onEditClick(TodoEntity item);
        void onDeleteClick(int id);
    }

}
