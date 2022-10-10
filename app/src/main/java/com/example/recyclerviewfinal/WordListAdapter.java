package com.example.recyclerviewfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private LinkedList<Item> mItemList;
    private final LayoutInflater mInflater;

    public void resetAdapter(LinkedList<Item> items) {
        this.mItemList = items;
    }

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        public WordViewHolder(@NonNull View itemView, WordListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            Item element = mItemList.get(mPosition);
            if(!element.isClicked()) {
                mItemList.set(mPosition, new Item("Clicked! " + element.getWord(), true));
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public WordListAdapter(Context context, LinkedList<Item> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mItemList = wordList;
    }

    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder holder, int position) {
        Item mCurrent = mItemList.get(position);
        holder.wordItemView.setText(mCurrent.getWord());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


}
