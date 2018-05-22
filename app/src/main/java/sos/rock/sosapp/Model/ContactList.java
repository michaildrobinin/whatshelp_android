package sos.rock.sosapp.Model;


import java.io.Serializable;


public class ContactList implements Serializable {
    int id;
    boolean isMale;  // true: male, false: female
    boolean ownerApprove, otherApprove;
    String phone, nickName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public boolean isOwnerApprove() {
        return ownerApprove;
    }

    public void setOwnerApprove(boolean ownerApprove) {
        this.ownerApprove = ownerApprove;
    }

    public boolean isOtherApprove() {
        return otherApprove;
    }

    public void setOtherApprove(boolean otherApprove) {
        this.otherApprove = otherApprove;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
