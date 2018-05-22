package sos.rock.sosapp.Model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rock on 29/7/2017.
 */

public class Comment implements Serializable{
    int bookId, accountId, rate;
    String comment, accountName, time;

    public Comment(JSONObject jsonObject) {
        try
        {
            bookId = jsonObject.getInt("ID");
            accountId = jsonObject.getInt("ACCOUNT_ID");
            rate = jsonObject.getInt("RATE");
            comment = jsonObject.getString("COMMENT");
            accountName = jsonObject.getString("ACCOUNT_NAME");
            time = jsonObject.getString("CREATED_AT");
        }
        catch (Exception e)
        {
            bookId = 0;
            accountId = 0;
            comment = "";
            accountName = "";
            time = "";
        }
    }

    public int getBookId() {
        return bookId;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getRate() {
        return rate;
    }

    public String getComment() {
        return comment;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getTime() {
        return time;
    }
}
