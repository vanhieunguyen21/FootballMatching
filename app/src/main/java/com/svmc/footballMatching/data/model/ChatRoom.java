package com.svmc.footballMatching.data.model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private String id;
    private String name;
    private String roomType;
    private List<ChatUser> users = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public List<ChatUser> getUsers() {
        return users;
    }

    public void setUsers(List<ChatUser> users) {
        this.users = users;
    }

    public static class ChatUser extends User {
        private String nickName;
        private Timestamp lastSentTimestamp;
        private Timestamp lastReadTimestamp;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Timestamp getLastSentTimestamp() {
            return lastSentTimestamp;
        }

        public void setLastSentTimestamp(Timestamp lastSentTimestamp) {
            this.lastSentTimestamp = lastSentTimestamp;
        }

        public Timestamp getLastReadTimestamp() {
            return lastReadTimestamp;
        }

        public void setLastReadTimestamp(Timestamp lastReadTimestamp) {
            this.lastReadTimestamp = lastReadTimestamp;
        }
    }

    private static class Message {
        private String id;
        private String messageType;
        private String text;
        private String imageUrl;
        private String attachmentUrl;
        private ChatUser sender;
        private Timestamp sentTimestamp;
        private String status;

        public Message() {
        }

        public Message(String id, String messageType, String text, String imageUrl, String attachmentUrl, ChatUser sender, Timestamp sentTimestamp, String status) {
            this.id = id;
            this.messageType = messageType;
            this.text = text;
            this.imageUrl = imageUrl;
            this.attachmentUrl = attachmentUrl;
            this.sender = sender;
            this.sentTimestamp = sentTimestamp;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getAttachmentUrl() {
            return attachmentUrl;
        }

        public void setAttachmentUrl(String attachmentUrl) {
            this.attachmentUrl = attachmentUrl;
        }

        public ChatUser getSender() {
            return sender;
        }

        public void setSender(ChatUser sender) {
            this.sender = sender;
        }

        public Timestamp getSentTimestamp() {
            return sentTimestamp;
        }

        public void setSentTimestamp(Timestamp sentTimestamp) {
            this.sentTimestamp = sentTimestamp;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
