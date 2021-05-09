package com.example.smart_edu.Admin.QlySach.Theme.Vocabulary;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_edu.Admin.QlySach.Theme.Theme_adapter;
import com.example.smart_edu.Admin.QlySach.Theme.Theme_display;
import com.example.smart_edu.Model.Theme;
import com.example.smart_edu.Model.Vocab;
import com.example.smart_edu.R;

import java.util.ArrayList;
import java.util.Locale;

public class Vocab_adapter extends RecyclerView.Adapter<Vocab_adapter.ViewHolder>{

    Context context;
    ArrayList<Vocab> arrayListVocab;
    TextToSpeech textToSpeech;

    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public Vocab_adapter(Context context, ArrayList<Vocab> arrayListVocab) {
        this.context = context;
        this.arrayListVocab = arrayListVocab;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.qlysach_theme_vocab_adapter1,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vocab vocab = arrayListVocab.get(position);
        holder.Vocab.setText(vocab.getWord());
        holder.Meaning.setText(vocab.getMeaning());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }


    @Override
    public int getItemCount() {
        return arrayListVocab.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView Vocab ;
        TextView Meaning ;
        ImageButton bt_sound;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Vocab = (TextView) itemView.findViewById(R.id.tv_word);
            Meaning = (TextView) itemView.findViewById(R.id.tv_meaning);
            bt_sound = (ImageButton) itemView.findViewById(R.id.bt_sound_word);
            cardView = itemView.findViewById(R.id.cardview);
            bt_sound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Doc(Vocab.getText().toString());
                }
            });
            cardView.setOnCreateContextMenuListener(this);
        }
        //Phuong thức đọc câu hỏi từ text View
        public void Doc(String word) {
            textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i != TextToSpeech.ERROR) {
                        textToSpeech.setLanguage(Locale.UK);
                    } else {
                        Toast.makeText(context, "Errol", Toast.LENGTH_LONG).show();
                    }
                    if (word != null) {
                        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    //Dừng dọc câu hỏii khi back lạii
                    textToSpeech.playSilence(2000, TextToSpeech.QUEUE_ADD, null);
                }
            });
        }



        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo = null
            menu.add(this.getAdapterPosition(), R.id.update, Menu.NONE, "Update");
            menu.add(this.getAdapterPosition(), R.id.delete, Menu.NONE, "Delete");
        }

    }

}
