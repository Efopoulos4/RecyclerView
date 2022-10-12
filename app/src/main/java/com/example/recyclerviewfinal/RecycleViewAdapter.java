package com.example.recyclerviewfinal;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.LinkedList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final int VIEW_TYPE_LOADING = 1;
    final int VIEW_TYPE_ITEM = 0;
    private LinkedList<Item> mItemList;
    private final LayoutInflater mInflater;

    public void resetAdapter(LinkedList<Item> items) {
        this.mItemList = items;
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView wordItemView;
        final RecycleViewAdapter mAdapter;

        public WordViewHolder(@NonNull View itemView, RecycleViewAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
        }
    }

    static class LoadingHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public RecycleViewAdapter(Context context, LinkedList<Item> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mItemList = wordList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
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
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wordlist_loading, parent, false);
            return new LoadingHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            populateItemRows((WordViewHolder) holder, position);
        } else if (holder.getItemViewType() == 1) {
            showLoadingView((LoadingHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mItemList.get(position) == null) {
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    private void populateItemRows(WordViewHolder viewHolder, int position) {
        String item = mItemList.get(position).getWord();
        viewHolder.wordItemView.setText(item);
    }

    private void showLoadingView(LoadingHolder viewHolder, int position) {

    }

}
