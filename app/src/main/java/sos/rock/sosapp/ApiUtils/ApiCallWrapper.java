package sos.rock.sosapp.ApiUtils;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import java.util.Map;
import sos.rock.sosapp.Utils.ShowProgressDialog;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiCallWrapper extends AsyncTask{
    private OkHttpClient okHttpClient;
    private String url, titleDialog;
    private ContentValues values;
    private AsyncTaskCallback callback;
    private Context context;
    private boolean showDialog;
    public ApiCallWrapper(Context context, boolean showDialog, String titleDialog, String url, ContentValues values, AsyncTaskCallback callback){
        this.context = context;
        this.showDialog = showDialog;
        this.url = url;
        this.titleDialog = titleDialog;
        this.values = values;
        this.callback = callback;
        okHttpClient = new OkHttpClient();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(showDialog)
            ShowProgressDialog.showProgressDialog(context, titleDialog);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(showDialog)
            ShowProgressDialog.hideProgressDialog();
        callback.onResultService(o);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {

            FormBody.Builder builder = new FormBody.Builder();
            for(Map.Entry<String, Object> key: values.valueSet()){
                builder.add(key.getKey(), key.getValue().toString());
            }

            RequestBody requestbody = builder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestbody)
                    .build();

            /*
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.github.help").newBuilder();
            urlBuilder.addQueryParameter("v", "1.0");
            urlBuilder.addQueryParameter("user", "vogella");
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            */
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
