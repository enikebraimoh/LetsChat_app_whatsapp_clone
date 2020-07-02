package com.enikebraimoh.flashchatnewfirebase;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    Activity mActivity;
    DatabaseReference mDatabaseReference;
    String DisplayName;
    ArrayList<DataSnapshot> mArrayList;


    private ChildEventListener mChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            mArrayList.add(snapshot);
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public Adapter (Activity activity , DatabaseReference ref, String displayName){

        mActivity = activity;
        mDatabaseReference = ref.child("messages");
        mDatabaseReference.addChildEventListener(mChildEventListener);
        DisplayName = displayName;
        mArrayList = new ArrayList<>();

    }

    static class ViewHolder{
            TextView authorName;
            TextView message;
            LinearLayout.LayoutParams mParams;
    }


    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public model getItem(int position) {

        DataSnapshot snapshot = mArrayList.get(position);
        return snapshot.getValue(model.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.chat_msg_row, parent, false);

         final   ViewHolder holder = new ViewHolder();

            holder.authorName = convertView.findViewById(R.id.theauthor);
            holder.message = convertView.findViewById(R.id.themessage);
            holder.mParams = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            convertView.setTag(holder);
        }

      final  model chat = getItem(position);

       final ViewHolder holder = (ViewHolder) convertView.getTag();

        boolean isMe = chat.getAuthor().equals(DisplayName);
        alignment(isMe,holder);

        String Author = chat.getAuthor();
        holder.authorName.setText(Author);

        String Message = chat.getMessage();
        holder.message.setText(Message);



        return convertView;
    }


    public void alignment (boolean isMe, ViewHolder holder){


        if (isMe){
            holder.mParams.gravity = Gravity.END;
            holder.authorName.setTextColor(Color.GREEN);
            holder.message.setBackgroundResource(R.drawable.bubble2);


        }else{
            holder.mParams.gravity = Gravity.START;
            holder.authorName.setTextColor(Color.BLUE);
            holder.message.setBackgroundResource(R.drawable.bubble1);

        }

        holder.authorName.setLayoutParams(holder.mParams);
        holder.message.setLayoutParams(holder.mParams);


    }


    public void StopUpdates(){
        mDatabaseReference.removeEventListener(mChildEventListener);
    }


}
