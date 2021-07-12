package com.frndzcode.task_webskitters.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.frndzcode.task_webskitters.R;
import com.frndzcode.task_webskitters.databinding.ItemHomeBinding;
import com.frndzcode.task_webskitters.model.HomeModel;
import com.frndzcode.task_webskitters.view.fragments.HomeFragment;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = HomeAdapter.class.getSimpleName();
    private ArrayList<HomeModel> entriesList;
    private AppCompatActivity activity;
    private ArrayList<HomeModel> selectionList;
    private Fragment fragment;
    //private final InterfaceItemSelection itemSelection;

    public HomeAdapter(ArrayList<HomeModel> entriesList, AppCompatActivity activity, HomeFragment fragment) {
        this.entriesList = entriesList;
        this.activity = activity;
        this.fragment = fragment;
        selectionList = new ArrayList<>();
        //itemSelection =  (InterfaceItemSelection) activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeAdapter.EntriesViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeModel model = entriesList.get(position);
        //Log.e(TAG, "onBindViewHolder: size "+entriesList.size() );
        setUpEntries(model,(HomeAdapter.EntriesViewHolder)holder);
    }

    private void setUpEntries(HomeModel model, EntriesViewHolder holder) {
        holder.binding.title.setText(model.getTitle());
        /*RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .centerCrop()
                .dontAnimate()
                .dontTransform()
                .priority(Priority.IMMEDIATE)
                .encodeFormat(Bitmap.CompressFormat.PNG)
                .format(DecodeFormat.DEFAULT);

        Glide.with(activity)
                .load(model.getThumbnailUrl())
                .thumbnail(0.5f)
                .apply(requestOptions)
                .fitCenter()
                .into(holder.binding.photo);*/
        //Log.e(TAG, "setUpEntries: image path =>"+ model.getUrl());
        Glide.with(activity)
                .asBitmap()
                .load(model.getThumbnailUrl())
                .into(holder.binding.photo);

        if (model.getSelected()){
            model.setSelected(true);
            selectView(holder);
        }else {
            model.setSelected(false);
            unSelectView(holder);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectionList.contains(model)){
                    selectionList.remove(model);
                    model.setSelected(false);
                    unSelectView(holder);
                } else{
                    selectionList.add(model);
                    model.setSelected(true);
                    selectView(holder);
                }
                ((HomeFragment)fragment).clickItem(selectionList.size());
                //itemSelection.onItemSelected(model);
            }
        });

    }

    private void selectView(EntriesViewHolder holder) {
        holder.binding.itemArea.setBackgroundResource(R.color.card_blue);
        holder.binding.title.setTextColor(ContextCompat.getColor(activity,R.color.white));
    }

    private void unSelectView(EntriesViewHolder holder) {
        holder.binding.itemArea.setBackgroundResource(R.color.white);
        holder.binding.title.setTextColor(ContextCompat.getColor(activity,R.color.blue_light));
    }

    public ArrayList<HomeModel> getSelectedItem() {
//        ArrayList<HomeModel> list = new ArrayList<>();
//
//        for (HomeModel model : selectionList) {
//            if (model.getCount()>0) {
//                list.add(model);
//            }
//        }
//        selectionList.addAll(list);
        return selectionList;
    }

    @Override
    public int getItemCount() {
        return entriesList.size();
    }

    public class EntriesViewHolder extends RecyclerView.ViewHolder {
        private ItemHomeBinding binding;

        public EntriesViewHolder(ItemHomeBinding view) {
            super(view.getRoot());
            this.binding = view;
        }
    }
}
