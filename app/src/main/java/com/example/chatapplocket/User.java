package com.example.chatapplocket;

public class User {
    String profilepic = "https://firebasestorage.googleapis.com/v0/b/chatapplocket.appspot.com/o/",mail,userName,password,userId,lastMessage,status;


    public User(String  password, String email, String userName, String userId, String status) {
        this.mail = email;
        this.password = password;
        this.status = status;
        this.userId = userId;
        this.userName = userName;
    }

    public User() {}

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
