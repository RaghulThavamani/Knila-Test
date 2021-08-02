package com.example.editor.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.editor.ExtensionFileKt;
import com.example.editor.Utils.GetHomePageChangeListener;
import com.example.editor.R;
import com.example.editor.abstarctClass.UserData;
import com.example.editor.model.Data;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapters extends RecyclerView.Adapter<ChatListAdapters.ViewHolder> {

    List<Data> chatLists = new ArrayList<>();
    GetHomePageChangeListener getHomePageChangeListener;

    public void setChatLists(List<Data> chatLists) {
        this.chatLists = chatLists;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {


        if (chatLists.get(position).getId() == 999) {
            byte[] imageBytes = Base64.decode(chatLists.get(position).getAvatar(), 0);
            Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            ExtensionFileKt.getGlideFunctionBitmap(holder.userImage, image);
        } else {
            ExtensionFileKt.getGlideFunction(holder.userImage, chatLists.get(position).getAvatar());
        }
        holder.userName.setText(chatLists.get(position).getFirst_name() + chatLists.get(position).getLast_name());
        holder.email.setText(chatLists.get(position).getEmail());
        holder.chatMsg.setOnClickListener(v -> {
            try{
                getHomePageChangeListener = (GetHomePageChangeListener) holder.userName.getContext();
            }catch (Exception e){
                e.printStackTrace();
                getHomePageChangeListener = null;
            }
            if (getHomePageChangeListener != null){
                getHomePageChangeListener.changeFragment("ChangeAddUser");
            }

            UserData.setUser(chatLists.get(position));
            UserData.setIsEdit(false);

        });

    }




    @Override
    public int getItemCount() {
        return chatLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView userName, email;
        ImageView userImage;
        LinearLayout chatMsg;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            userImage = itemView.findViewById(R.id.userImage);
            email = itemView.findViewById(R.id.email);
            chatMsg = itemView.findViewById(R.id.chatMsg);
        }
    }


}
