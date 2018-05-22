package sos.rock.sosapp.Model;

import java.io.Serializable;


public class ContactListHome implements Serializable {
    int id;
    String phone, userName;
    int callResult;     // 0:init, 1:call success, 2: call failed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCallResult() {
        return callResult;
    }

    public void setCallResult(int callResult) {
        this.callResult = callResult;
    }
}
