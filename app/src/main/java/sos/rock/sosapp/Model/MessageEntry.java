package sos.rock.sosapp.Model;

import org.json.JSONObject;

/**
 * Created by rock on 7/9/2017.
 */

public class MessageEntry {
    int userId;
    String booktitle;
    int bookId;
    User otherUser;
    public MessageEntry(JSONObject object)
    {
        try{
            userId = object.getInt("USER_ID");
            booktitle = object.getString("BOOK_TITLE");
            bookId = object.getInt("BOOK_ID");
            otherUser = new User(object.getJSONObject("USER"));
        }
        catch (Exception e)
        {
            userId = 0;
        }
    }

    public User getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(User otherUser) {
        this.otherUser = otherUser;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
