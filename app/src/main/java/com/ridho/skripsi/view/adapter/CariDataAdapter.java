package com.ridho.skripsi.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ridho.skripsi.R;

public class CariDataAdapter extends RecyclerView.Adapter<CariDataAdapter.ListViewHolder> {
    private final String[] listData;

    public CariDataAdapter(String[] list) {
        this.listData = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cari_data, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        String data = listData[position];
        holder.tvText.setText(data);
    }

    @Override
    public int getItemCount() {
        return listData.length;
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;
        ListViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }
}
