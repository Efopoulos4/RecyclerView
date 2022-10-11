package com.example.recyclerviewfinal;

import android.content.Context;
import android.util.Log;
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

    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        public WordViewHolder(@NonNull View itemView, WordListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
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
        final WordViewHolder holder = new WordViewHolder(mItemView, this);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = holder.getLayoutPosition();
                Item element = mItemList.get(mPosition);
                if (!element.isClicked()) {
                    mItemList.set(mPosition, new Item("Clicked! " + element.getWord(), true));
                    holder.mAdapter.notifyDataSetChanged();
                }
            }
        });
        return holder;
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
