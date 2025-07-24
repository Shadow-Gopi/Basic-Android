package com.gopi.mynewapplication.ui.speach;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.gopi.mynewapplication.data.database.entity.SpeechEntity;
import com.gopi.mynewapplication.databinding.ItemSpeechBinding;

import java.util.Locale;

public class SpeechAdapter extends ListAdapter<SpeechEntity, SpeechAdapter.ViewHolder> {
    private final OnClickListener onClickListener;

    public SpeechAdapter(Context context, OnClickListener onClickListener) {
        super(DIFF_CALLBACK);
        this.onClickListener = onClickListener;
    }

    private static final DiffUtil.ItemCallback<SpeechEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<SpeechEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull SpeechEntity oldItem, @NonNull SpeechEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull SpeechEntity oldItem, @NonNull SpeechEntity newItem) {
            return oldItem.getText().equals(newItem.getText());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSpeechBinding binding = ItemSpeechBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpeechEntity item = getItem(position);
        holder.binding.tvCardText.setText(item.getText());
        holder.binding.ivDelete.setOnClickListener(v -> onClickListener.onDelete(item.getId()));
        holder.binding.ivSpeech.setOnClickListener(v -> {
            String text = item.getText();
            onClickListener.onSpeech(text);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSpeechBinding binding;

        public ViewHolder(ItemSpeechBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnClickListener {
        void onDelete(int id);
        void onSpeech(String text);
    }
}
