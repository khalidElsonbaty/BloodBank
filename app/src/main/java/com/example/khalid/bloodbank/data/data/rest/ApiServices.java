package com.example.khalid.bloodbank.data.data.rest;

import com.example.khalid.bloodbank.data.data.model.cities.Cities;
import com.example.khalid.bloodbank.data.data.model.donationRequest.DonationRequest;
import com.example.khalid.bloodbank.data.data.model.donationRequestDetails.DonationRequestDetails;
import com.example.khalid.bloodbank.data.data.model.governorates.Governorates;
import com.example.khalid.bloodbank.data.data.model.newPassword.NewPassword;
import com.example.khalid.bloodbank.data.data.model.notificationsList.NotificationsList;
import com.example.khalid.bloodbank.data.data.model.notificationsSettings.NotificationsSettings;
import com.example.khalid.bloodbank.data.data.model.posts.Posts;
import com.example.khalid.bloodbank.data.data.model.postsDetails.PostsDetails;
import com.example.khalid.bloodbank.data.data.model.registerToken.RegisterToken;
import com.example.khalid.bloodbank.data.data.model.resetPassword.ResetPassword;
import com.example.khalid.bloodbank.data.data.model.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {



//    @POST("reset-password")
//    @FormUrlEncoded
//    Call<User> addResetPassword(@Field("phone") int phone);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassword> sendPhone(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPassword> addNewPassword(@Field("password") String password,
                                     @Field("password_confirmation") String password_confirmation,
                                     @Field("pin_code") String pin_code);

    @POST("profile")
    @FormUrlEncoded
    Call<User> addProfile(@Field("name") String name,
                          @Field("email") String email,
                          @Field("birth_date") String birth_date,
                          @Field("city_id") int city_id,
                          @Field("phone") int phone,
                          @Field("donation_last_date") String donation_last_date,
                          @Field("password") String password,
                          @Field("password_confirmation") String password_confirmation,
                          @Field("blood_type") String blood_type,
                          @Field("api_token") String api_token);

    @POST("donation-request/create")
    @FormUrlEncoded
    Call<DonationRequest> onCreate(@Field("api_token") String api_token,
                                    @Field("patient_name") String patient_name,
                                    @Field("patient_age") String patient_age,
                                    @Field("blood_type") String blood_type,
                                    @Field("bags_num") String bags_num,
                                    @Field("hospital_name") String hospital_name,
                                    @Field("hospital_address") String hospital_address,
                                    @Field("city_id") int city_id,
                                    @Field("phone") String phone,
                                    @Field("notes") String notes,
                                    @Field("latitude") double latitude,
                                    @Field("longitude") double longitude);

    @GET("donation-requests")
    Call<DonationRequest> getDonationRequests(@Query("api_token") String api_token);

    @GET("donation-request")
    Call<DonationRequestDetails> getDonationDetails(@Query("api_token") String api_token,
                                                    @Query("donation_id") int donation_id);

    @POST("register-token")
    @FormUrlEncoded
    Call<RegisterToken> addRegisterToken(@Field("token") String token,
                                         @Field("api_token") String api_token,
                                         @Field("platform") String platform);

    @POST("remove-token")
    @FormUrlEncoded
    Call<RegisterToken> addRemoveToken(@Field("token") String token,
                                           @Field("api_token") String api_token);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationsSettings> addNotificationsSettings(@Field("api_token") String api_token,
                                                         @Field("cities[]") List<String> citiesList,
                                                         @Field("blood_types[]") List<String> blood_typesList);

    @GET("notifications")
    Call<NotificationsList> getNotifications(@Query("api_token") String api_token);

    @GET("notifications-count")
    Call<NotificationsSettings> getNotificationsCount(@Query("api_token") String api_token);


    @GET("posts")
    Call<Posts> getPosts(@Query("api_token") String api_token,
                         @Query("page") int page);

    @GET("post")
    Call<PostsDetails> getPostsDetails(@Query("api_token") String api_token,
                                       @Query("post_id") int post_id);

    @GET("my-favourites")
    Call<Posts> getFavouritesList(@Query("api_token") String api_token);


    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<Posts> addPostToggleFavourite(@Field("post_id") String post_id,
                                       @Field("api_token") String api_token);


    @POST("register")
    @FormUrlEncoded
    Call<User> onRgister(@Field("name") String name,
                         @Field("email") String email,
                         @Field("birth_date") String birth_date,
                         @Field("city_id") int city_id,
                         @Field("phone") String phone,
                         @Field("donation_last_date") String donation_last_date,
                         @Field("password") String password,
                         @Field("password_confirmation") String password_confirmation,
                         @Field("blood_type") String blood_type);
    @POST("login")
    @FormUrlEncoded
    Call<User> onLogin(@Field("phone") String phone,
                        @Field("password") String password);

    @GET("governorates")
    Call<Governorates> getGovernorates();

    @GET("cities")
    Call<Cities> getCities(@Query("governorate_id") int governorate_id);

}
