package sos.rock.sosapp.Model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rock on 29/7/2017.
 */

public class Book implements Serializable{

    private String title, author, publishDate, isbn;
    private double lat, lot;
    private int ownerId, id;
    private User bookOwner;
    private int commentCount, lentAccount;
    private int rate;
    private String uploadTimeStamp, content;
    private String subTitle, publisher;
    private String thumbImagePath;
    private int shelveId;
    private String shelveTitle;
    public Book(JSONObject object){
        try{
            title = object.getString("TITLE");
            author = object.getString("AUTHOR");
            publishDate = object.getString("PUBLISH");
            publisher = object.getString("PUBLISHER");
            isbn = object.getString("ISBN");
            lat = object.getDouble("LAT");
            lot = object.getDouble("LOT");
            ownerId = object.getInt("OWNER_ID");
            subTitle = object.getString("SUBTITLE");
            id = object.getInt("ID");
            bookOwner = new User(object.getJSONObject("OWNER"));
            commentCount = object.getInt("COMMENTCOUNT");
            lentAccount = object.getInt("LENT_ACCOUNT");
            rate = object.getInt("RATE");
            uploadTimeStamp = object.getString("CREATED_AT");
            content = object.getString("CONTENTS");
            thumbImagePath = object.getString("IMAGE1");
            shelveId = object.getInt("SHELVE_ID");
            shelveTitle = object.getString("SHIELVETITLE");
        }
        catch (Exception e){
            title = "";
            author = "";
            publishDate = "";
            publisher = "";
            isbn = "";
            lat = 0f;
            lot = 0f;
            ownerId  = 0;
            subTitle = "";
            id = 0;
            bookOwner = new User();
            commentCount = 0;
            lentAccount = 0;
            rate = 0;
            uploadTimeStamp = "";
            content = "";
            thumbImagePath = "";
            shelveId = 0;
            shelveTitle = "";
        }
    }
    public Book(){

    }

    public String getShelveTitle() {
        return shelveTitle;
    }

    public int getShelveId() {
        return shelveId;
    }

    public String getThumbImagePath() {
        return thumbImagePath;
    }

    public void setThumbImagePath(String thumbImagePath) {
        this.thumbImagePath = thumbImagePath;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLentAccount() {
        return lentAccount;
    }

    public void setLentAccount(int lentAccount) {
        this.lentAccount = lentAccount;
    }

    public String getUploadTimeStamp() {
        return uploadTimeStamp;
    }

    public void setUploadTimeStamp(String uploadTimeStamp) {
        this.uploadTimeStamp = uploadTimeStamp;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getBookOwner() {
        return bookOwner;
    }

    public void setBookOwner(User bookOwner) {
        this.bookOwner = bookOwner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLot() {
        return lot;
    }

    public void setLot(double lot) {
        this.lot = lot;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
