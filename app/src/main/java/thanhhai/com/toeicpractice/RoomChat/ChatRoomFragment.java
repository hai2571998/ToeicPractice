package thanhhai.com.toeicpractice.RoomChat;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import thanhhai.com.toeicpractice.R;

import static android.content.Context.MODE_PRIVATE;

public class ChatRoomFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mChatRoomRecyclerView;
    private EditText mUserMessageChatText;
    private Button btnSendMessage;
    private MessageChatAdapter messageChatAdapter;
    SharedPreferences sharedPreferences;
    private DatabaseReference messageChatRoomDatabase;
    private ChildEventListener messageChatListener;

    public ChatRoomFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_chat_room, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((SplashLoginActivity) getActivity()).getSupportActionBar().setTitle("Chat Room English");
        mChatRoomRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_chat_room);
        mUserMessageChatText = (EditText) getActivity().findViewById(R.id.edit_text_message_chat_room);
        btnSendMessage = (Button) getActivity().findViewById(R.id.btn_send_message_chat_room);

        sharedPreferences = getActivity().getSharedPreferences(Config.KEY_USER_INFO, MODE_PRIVATE);
        setDatabaseInstance();
        setChatRoomRecyclerView();
        btnSendMessage.setOnClickListener(this);
    }

    private void setDatabaseInstance() {
        createMessageChatRoomDatabase(Config.KEY_ROOM_ONE);
    }

    private void setChatRoomRecyclerView() {
        mChatRoomRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mChatRoomRecyclerView.setHasFixedSize(true);
        messageChatAdapter = new MessageChatAdapter(getContext(), new ArrayList<ChatMessage>());
        mChatRoomRecyclerView.setAdapter(messageChatAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_message_chat_room:
                handleSendMessage();
                break;
            default:
                break;
        }
    }

    private void handleSendMessage() {
        String senderMessage = mUserMessageChatText.getText().toString().trim();
        if (!senderMessage.isEmpty()) {
            ChatMessage newMessage = new ChatMessage(senderMessage, getCurrentUserId(), getDisplayName(), Calendar.getInstance().getTime().getTime() + "");
            pushMessageChatDatabase(newMessage);
            mUserMessageChatText.setText("");
            KeyboardHelper.HideKeyboard(mUserMessageChatText, getContext());
        }
    }

    public void notifyMessageChatRoomAdapter(ChatMessage newMessage) {
        messageChatAdapter.refillAdapter(newMessage);
        mChatRoomRecyclerView.scrollToPosition(messageChatAdapter.getItemCount() - 1);
    }

    public String getDisplayName() {
        return sharedPreferences.getString(Config.KEY_DISPLAY_NAME, "");
    }

    private String getCurrentUserId() {
        SharedPreferences pref = getActivity().getSharedPreferences(Config.KEY_USER_INFO, MODE_PRIVATE);
        return pref.getString(Config.KEY_USER_ID, "");
    }

    @Override
    public void onStart() {
        super.onStart();
        onMessageChatRoomListener(getCurrentUserId());
    }

    @Override
    public void onStop() {
        super.onStop();
        removeEventListener();
        messageChatAdapter.cleanUp();
    }

    public void createMessageChatRoomDatabase(String chatRef) {
        messageChatRoomDatabase = FirebaseDatabase.getInstance().getReference(chatRef);
    }

    public void onMessageChatRoomListener(final String currentUserId) {
        messageChatListener = messageChatRoomDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    if (chatMessage.getUserId().equals(currentUserId)) {
                        chatMessage.setRecipientOrSenderStatus(MessageChatAdapter.SENDER);
                    } else {
                        chatMessage.setRecipientOrSenderStatus(MessageChatAdapter.RECIPIENT);
                    }
                    notifyMessageChatRoomAdapter(chatMessage);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void pushMessageChatDatabase(ChatMessage newMessage) {
        messageChatRoomDatabase.push().setValue(newMessage);
    }

    public void removeEventListener() {
        if (messageChatListener != null) {
            messageChatRoomDatabase.removeEventListener(messageChatListener);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_chat, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_change_password:
                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.main, changePasswordFragment).addToBackStack(null).commit();
                return false;
            case R.id.nav_log_out:
                onCheckRememberPassword();
                LoginFragment loginFragment = new LoginFragment();
                FragmentManager manager1 = getActivity().getSupportFragmentManager();
                manager1.beginTransaction().replace(R.id.main, loginFragment).commit();
                return false;
            default:
                break;
        }
        return false;
    }

    private void onCheckRememberPassword() {
        SharedPreferences pref = getContext().getSharedPreferences(Config.KEY_USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.putBoolean(Config.KEY_CHECK_REMEMBER_PASS, false);
        editor.commit();
    }
}
