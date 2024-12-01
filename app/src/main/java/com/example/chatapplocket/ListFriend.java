package com.example.chatapplocket;

public class ListFriend {
    String Id;
    String EmailTKC;
    String Name;
    String Email;

    public ListFriend(String id, String emailTKC, String email, String name) {
        Id = id;
        EmailTKC = emailTKC;
        Email = email;
        Name = name;
    }

    public ListFriend() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmailTKC() {
        return EmailTKC;
    }

    public void setEmailTKC(String emailTKC) {
        EmailTKC = emailTKC;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
