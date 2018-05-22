package sos.rock.sosapp.Model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rock on 1/9/2017.
 */

public class Shelve implements Serializable{
    int id;
    String title, description;

    public Shelve(JSONObject jsonObject)
    {
        try
        {
            id = jsonObject.getInt("ID");
            title = jsonObject.getString("TITLE");
            description = jsonObject.getString("DESCRIPTION");
        }
        catch (Exception e)
        {
            id = 0;
            title = "";
            description = "";
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
