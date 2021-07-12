package com.frndzcode.task_webskitters.view.adapters;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.frndzcode.task_webskitters.R;
import com.frndzcode.task_webskitters.databinding.ItemHomeBinding;
import com.frndzcode.task_webskitters.databinding.ItemUserBinding;
import com.frndzcode.task_webskitters.model.HomeModel;
import com.frndzcode.task_webskitters.model.UserModel;
import com.frndzcode.task_webskitters.view.fragments.HomeFragment;
import com.frndzcode.task_webskitters.view.fragments.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = UserAdapter.class.getSimpleName();
    private List<UserModel> userList;
    private AppCompatActivity activity;
    private Fragment fragment;

    public UserAdapter(List<UserModel> userList, AppCompatActivity activity, UserFragment fragment) {
        this.userList = userList;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserAdapter.UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserModel model = userList.get(position);
        setUpEntries(model,(UserAdapter.UserViewHolder)holder);
    }

    private void setUpEntries(UserModel model, UserViewHolder holder) {
        holder.binding.name.setText(model.getName());
        holder.binding.address.setText(model.getAddress());

        Glide.with(activity)
                .load(model.getEmail())
                .placeholder(R.drawable.person)
                .into(holder.binding.image);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteUser(model);
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UserFragment)fragment).OnUpdateUser(model);
            }
        });
    }

    private void deleteUser(UserModel model) {
        new AlertDialog.Builder(activity)
                .setTitle("User Delete")
                .setMessage("Are you sure to delete user")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((UserFragment)fragment).OnDeleteUser(model);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create().show();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private ItemUserBinding binding;

        public UserViewHolder(ItemUserBinding view) {
            super(view.getRoot());
            this.binding = view;
        }
    }
}
