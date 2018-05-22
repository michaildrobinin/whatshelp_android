package sos.rock.sosapp.ApiUtils;
/**
 * Created by Ivan on 8/24/2017.
 */



import retrofit2.http.*;
import rx.Observable;
import sos.rock.sosapp.Model.*;


public interface SosAPIService {
    @POST("sos/api/authcontroller/login/")
    Observable<LoginResponse> login(@Body LoginRequestInfo loginInfo);
//    @POST("api/register")
//    Observable<RegisterResponse> register(@Body RegisterRequestInfo registerInfo);
//    @POST("api/reset")
//    Observable<ResetResponse> reset(@Body ResetRequestInfo resetInfo);
//    @POST("api/update_profile")
//    Observable<UpdateResponse> update(@Body UpdateRequestInfo updateInfo);
//    @Multipart
//    @POST("api/fileUpload")
//    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("email") RequestBody email);
//
//    @GET
//    Call<FBProfilePictureRedirectResponse> fbProfilePictureLink(@Url String profilePicUrl);
//
//    @POST("api/modify_pw")
//    Observable<ModifyResponse> modify(@Body ModifyRequestInfo modifyInfo);

//    @Multipart
//    @POST("api/fileUpload")
//    Call<UploadResponse> uploadImage(@Part MultipartBody.Part image);
//    @POST("api/register")
//    Observable<RegisterResponse> register(@Body RegisterRequestInfo registerInfo);
//    @GET("/api/user_login/{userid}")
//    Observable<SettingResponse> getSettingInfo(@Path("userid") String uid);

//    @GET("/enrich_mobile/WzWpdatatable4/getSettingsNew/{userid}")
//    Observable<SettingResponse> getSettingInfo(@Path("userid") String uid);
//
//    @POST("/enrich_mobile/WzWpdatatable4/updateSettingNew")
//    Observable<SettingResponse> updateSettingInfo(@Body SettingInfo settingInfo);
//
//    @GET("/enrich_mobile/DapUsers/getAvailableCardsTest/{userid}")
//    Observable<GetMyContactsResponse> getMyContacts(@Path("userid") String uid);
//
//    @Multipart
//    @POST("user/updateprofile")
//    Observable<ResponseBody> uploadAudioFile(@Part MultipartBody.Part audioFile);

//    @POST("/hooks/catch/772315/t2hhp8/")
//    Call<ResponseBody> sendSmsMsgTestNow(@Body SmsMsgTestNowRequestBody body);
//
//
//
//    @GET("/pages/upload_from_bucket.php?")
//    Call<ResponseBody> sendTranscription(@Query("Message") String message);
//
//    @GET("/private/setting/pages/activate_mvm_campaign.php?")
//    Call<ResponseBody> activateMVMCampaign(@Query("user_id") String userId, @Query("audio_filename") String audioFileName);
//
//    @GET("/enrich_mobile/HtJobs/deleteContact/{deleteIndexString}")
//    Observable<DeleteContactResponse> deleteContacts(@Path("deleteIndexString") String indexes);
//
//    @GET("/{account_id}/{fileName}")
//    Call<ResponseBody> downloadAudioFileUrlAsync(@Path("account_id") String strAccountId, @Path("fileName") String fileName);
//
//    @GET("/enrich-scanner/{fileName}")
//    Call<ResponseBody> downloadScannerImageFileUrlAsync(@Path("fileName") String fileName);

}