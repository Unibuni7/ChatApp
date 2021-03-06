package com.example.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapp.MessageActivity;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.Model.User;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

// I copied the majority of the code from the UserAdapter class.
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    // These two variables will be used to determine how the message will be shown on the screen.
    // Either the message will be on the left of the screen or the right.
    // It depends if the user is reciving or sending.
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    // We make three variables one is context and the other one is a list of chats, the third one is for image url.
    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;

    FirebaseUser fuser;


    // This is the constructor.
    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl){
        this.mContext = mContext;
        this.mChat = mChat;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    // Each viewholder is in charge of displaying a single item with a view.
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // if the viewType is equal to MSG_Type_LEFT, the message will be shown on the left side of the screen.
        if (viewType == MSG_TYPE_LEFT){
            // Instantiates a layout XML file into its corresponding View objects.
            // From = Obtains the Layoutinflater from a given context.
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
            // If not the message will be shown on the right screen.
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }


    }

    @Override
    // onBindViewHolder is called by RecyclerView to display the data at the specified position.
    // This method should update the contents of the itemView to reflect the item at the given position.
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        holder.show_message.setText(chat.getMessage());

        // The imageurl equals default the profile picture will be the default picture.
        if (imageurl.equals("default")) {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            // if not the picture will be the same as the user want.
            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }

    }

    @Override
    public int getItemCount() {
        // The size of the list will be the same size of the users list.
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profile_image;

        public ViewHolder(View itemView){
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);


        }
    }

    @Override
    public int getItemViewType(int position) {


        fuser = FirebaseAuth.getInstance().getCurrentUser();
        // If the user is sending the message the message will be shown on the right side
        if (mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            // If the user is recieving the message will be shown on the left side.
            return MSG_TYPE_LEFT;
        }

    }
}
