package com.frndzcode.task_webskitters.view.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.frndzcode.task_webskitters.databinding.ItemFirstIntroBinding;
import com.frndzcode.task_webskitters.model.IntroModel;

import java.util.ArrayList;

public class IntroPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<IntroModel> introList;
    private AppCompatActivity activity;

    public IntroPagerAdapter(ArrayList<IntroModel> introList, AppCompatActivity activity) {
        this.introList = introList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IntroPagerAdapter
                .IntroViewHolder(ItemFirstIntroBinding
                        .inflate(LayoutInflater
                                .from(parent.getContext()),
                                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IntroModel model = introList.get(position);
        setupIntro(model, (IntroPagerAdapter.IntroViewHolder) holder, position);
    }

    private void setupIntro(IntroModel model, IntroViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .centerCrop()
                .dontAnimate()
                .dontTransform()
                .priority(Priority.IMMEDIATE)
                .encodeFormat(Bitmap.CompressFormat.PNG)
                .format(DecodeFormat.DEFAULT);

      /*  Glide.with(activity)
                .load(model.getImage())
                .thumbnail(0.5f)
                .error(R.drawable.photo_placeholder)
                .into(holder.image);*/

        Glide.with(activity)
                .load(model.getImage())
                .thumbnail(0.5f)
                .apply(requestOptions)
                .into(holder.binding.image);

        holder.binding.title.setText(model.getTitle());
        holder.binding.details.setText(model.getDetails());

    }

    @Override
    public int getItemCount() {
        return introList.size();
    }

    public class IntroViewHolder extends RecyclerView.ViewHolder {
        private ItemFirstIntroBinding binding;

        public IntroViewHolder(ItemFirstIntroBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
