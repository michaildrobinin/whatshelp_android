package sos.rock.sosapp.Model;

import org.json.JSONObject;

/**
 * Created by rock on 22/8/2017.
 */

public class ChatModel {
    int fromId, toId, bookId;
    String content, dateTime;

    public ChatModel(JSONObject chatObject) {
        try
        {
            fromId = chatObject.getInt("FROM_ID");
            toId = chatObject.getInt("TO_ID");
            bookId = chatObject.getInt("BOOK_ID");
            content = chatObject.getString("CONTENT");
            dateTime = chatObject.getString("CREATED_AT");
        }
        catch (Exception e)
        {
            fromId = 0;
            toId = 0;
            bookId = 0;
            content = "";
            dateTime = "";
        }
    }

    public int getFromId() {
        return fromId;
    }

    public int getToId() {
        return toId;
    }

    public int getBookId() {
        return bookId;
    }

    public String getContent() {
        return content;
    }

    public String getDateTime() {
        return dateTime;
    }

}
