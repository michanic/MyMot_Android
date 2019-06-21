package ru.michanic.mymot.UI.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.michanic.mymot.Models.Category;
import ru.michanic.mymot.Protocols.ClickListener;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Cells.CatalogSliderCell;

public class ClassesSliderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Category> categories;
    ClickListener clickListener;

    public ClassesSliderAdapter(Context context, List<Category> categories, ClickListener clickListener) {
        this.context = context;
        this.categories = categories;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        RecyclerView.ViewHolder viewHolder = null;
        if (position == 0) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_catalog_slide, viewGroup, false);
            viewHolder = new CatalogSliderCell(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CatalogSliderCell) {
            ((CatalogSliderCell) holder).fillWithCategory(categories.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(0, position);
                }
            });
        }
    }

    /*@Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }*/

    @Override
    public int getItemCount() {
        return categories.size();
    }


}
