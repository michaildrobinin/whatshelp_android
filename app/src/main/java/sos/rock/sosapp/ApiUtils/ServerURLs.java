package sos.rock.sosapp.ApiUtils;

/**
 * Created by rock on 2/8/2017.
 */

public class ServerURLs {
    //    private static final String BASE_URL = "http://192.168.1.102/bookmap/api/";
//    private static final String BASE_URL = "https://bookmapp.com/api/";
    public static final String BASE_URL = "https://192.168.1.82/";
    public static final String REGISTER_URL = BASE_URL + "auth/register";
    public static final String LOGIN_URL = BASE_URL + "";
    public static final String FACEBOOK_URL = BASE_URL + "auth/facebooksign";
    public static final String VERIFYTOKEN_URL = BASE_URL + "auth/checktoken";
    public static final String CHANGEPASSWORD_URL = BASE_URL + "auth/resetpass";
    public static final String CHANGEEMAIL_URL = BASE_URL + "auth/changemail";
    public static final String ADDSHELVE_URL = BASE_URL + "book/addshelve";
    public static final String UPDATESHELVE_URL = BASE_URL + "book/updateshelve";
    public static final String GETSHELVES_URL = BASE_URL + "book/getshelvelist";
    public static final String REMOVESHELVE_URL = BASE_URL + "book/delshelve";

    public static final String ADDBOOK_URL = BASE_URL + "book/addbook";
    public static final String UPDATEBOOK_URL = BASE_URL + "book/updatebook";
    public static final String SEARCHBOOK_URL = BASE_URL + "book/search";
    public static final String REMOVEBOOK_URL = BASE_URL + "book/removebook";

    public static final String MYBOOK_URL = BASE_URL + "book/getmybook";
    public static final String BOOKMAP_URL = BASE_URL + "book/getbookformap";
    public static final String DETAILMAP_URL = BASE_URL + "book/getmapdetail";
    public static final String ADDCOMMENT_URL = BASE_URL + "book/addcomment";
    public static final String GETCOMMENT_URL = BASE_URL + "book/getcomment";
    public static final String GETBOOKCOMMENT_URL = BASE_URL + "book/getbookcomment";
    public static final String PAYPALTOTAL_URL = BASE_URL + "pay/totalpay";
    public static final String PAYPAL_URL = BASE_URL + "pay/paycheck";

    public static final String CHAT_HISTORY_URL = BASE_URL + "chat/history";
    public static final String CHAT_SEND_URL = BASE_URL + "chat/send";
    public static final String CONTACT_LIST_URL = BASE_URL + "chat/getcontacts";
    public static final String MESSAGE_LIST_URL = BASE_URL + "chat/getmessageentries";


    public static final String PRIVACY_URL = "https://bookmapp.com/welcome/privacy";
    public static final String PRIVACY_STATUS = BASE_URL + "auth/privacy";

}
