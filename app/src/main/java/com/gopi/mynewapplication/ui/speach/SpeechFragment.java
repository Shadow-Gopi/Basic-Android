package com.gopi.mynewapplication.ui.speach;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.gopi.mynewapplication.databinding.FragmentSpeechBinding;


import java.util.ArrayList;
import java.util.Locale;



public class SpeechFragment extends Fragment {
    FragmentSpeechBinding binding;
    private SpeechViewModel viewModel;
    private TextToSpeech textToSpeech;


    private final ActivityResultLauncher<Intent> speechLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == android.app.Activity.RESULT_OK && result.getData() != null) {
                    ArrayList<String> resultList = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (resultList != null && !resultList.isEmpty()) {
                        String recognizedText = resultList.get(0);
                        binding.tvSpeechToText.setText(recognizedText);
                        viewModel.insertText(recognizedText);
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSpeechBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
        ).get(SpeechViewModel.class);
        binding.ivMic.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

            try {
                speechLauncher.launch(intent);
            } catch (Exception e) {
                Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        textToSpeech = new TextToSpeech(requireContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.US);
            }
        });

        SpeechAdapter.OnClickListener listener = new SpeechAdapter.OnClickListener() {
            @Override
            public void onDelete(int id) {
                viewModel.deleteById(id);
            }

            @Override
            public void onSpeech(String text) {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        };

        SpeechAdapter adapter = new SpeechAdapter(requireContext(), listener);
        binding.speechRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.speechRecyclerView.setAdapter(adapter);

        viewModel.getAllSpeech().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}
