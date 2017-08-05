package thanhhai.com.toeicpractice.RoomChat;

import com.google.firebase.database.Exclude;

public class ChatMessage {

    private String message;
    private String userId;
    private String userName;
    private String timeStamp;

    private int mRecipientOrSenderStatus;

    public ChatMessage() {
    }

    public ChatMessage(String message, String userId, String userName, String timeStamp) {
        this.message = message;
        this.userId = userId;
        this.userName = userName;
        this.timeStamp = timeStamp;
    }

    public void setRecipientOrSenderStatus(int recipientOrSenderStatus) {
        this.mRecipientOrSenderStatus = recipientOrSenderStatus;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    @Exclude
    public int getRecipientOrSenderStatus() {
        return mRecipientOrSenderStatus;
    }
}
