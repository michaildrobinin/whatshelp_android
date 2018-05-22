package sos.rock.sosapp.Model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rock on 29/7/2017.
 */

public class User implements Serializable{
    int id;
    String email, fName, lName, addr;

    public User(JSONObject object){
        try{
            id = object.getInt("ID");
            email = object.getString("EMAIL");
            fName = object.getString("FNAME");
            lName = object.getString("LNAME");
            addr = object.getString("ADDR");
        }catch (Exception e){
            id = 0;
            email = "";
            fName  = "";
            lName = "";
            addr = "";
            e.printStackTrace();
        }
    }

    public User(){
        id = 0;
        email = "";
        fName  = "";
        lName = "";
        addr = "";
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
}
