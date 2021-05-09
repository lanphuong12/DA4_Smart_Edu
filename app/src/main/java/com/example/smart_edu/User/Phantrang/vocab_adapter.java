package com.example.smart_edu.User.Phantrang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_edu.R;

import java.util.ArrayList;

public class vocab_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private final int VIEW_TYPE_ITEM = 1, VIEW_TYPE_LOADING = 2;
    private ArrayList<vocab> listitems;
    private OnVocabListener mOnVocabListener;
    boolean isloadingadd;

    public vocab_adapter(ArrayList<vocab> listitems, OnVocabListener mOnVocabListener) {
        this.listitems = listitems;
        this.mOnVocabListener = mOnVocabListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (listitems != null && position == (listitems.size() -1) && isloadingadd){
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_ITEM;
    }

    public void setData(ArrayList<vocab> list){
        this.listitems = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (VIEW_TYPE_ITEM == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_voacb_adapter1,parent, false);
            return new VocabViewHolder(view, mOnVocabListener);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_vocab_main1,parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == VIEW_TYPE_ITEM){
            vocab tumoi = listitems.get(position);
            VocabViewHolder vocabViewHolder = (VocabViewHolder) holder;
            vocabViewHolder.word.setText(position+1+". "+ tumoi.getWord());
            vocabViewHolder.meaning.setText(tumoi.getMeaning());
        }
    }

    @Override
    public int getItemCount() {
        if (listitems != null){
            return listitems.size();
        }
        return 0;
    }

    public class VocabViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView word, meaning;
        OnVocabListener onVocabListener;
        public VocabViewHolder(@NonNull View itemView, OnVocabListener onVocabListener) {
            super(itemView);
            word = itemView.findViewById(R.id.tv_user_vocab);
            meaning = itemView.findViewById(R.id.tv_user_meaning);
            this.onVocabListener = onVocabListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onVocabListener.OnVocabClick(getAdapterPosition());
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{

        public ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

    public void addLoading(){
        isloadingadd = true;
        listitems.add(new vocab("",""));
    }

    public void removeLoading(){
        isloadingadd = false;

        int position = listitems.size() -1;
        vocab item = listitems.get(position);
        if (item != null){
            listitems.remove(position);
            notifyDataSetChanged();
        }

    }

    public interface OnVocabListener{
        void OnVocabClick(int position);
    }

}
