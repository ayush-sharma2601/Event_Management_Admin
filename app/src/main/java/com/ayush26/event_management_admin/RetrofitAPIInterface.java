package com.ayush26.event_management_admin;

import com.ayush26.event_management_admin.Models.APIModels.APICall;
import com.ayush26.event_management_admin.Models.APIModels.AddAgeGroupAPICall;
import com.ayush26.event_management_admin.Models.APIModels.AgeGroupCodeAPI;
import com.ayush26.event_management_admin.Models.APIModels.CompetitionAddAPI;
import com.ayush26.event_management_admin.Models.APIModels.CompetitionGetAPI;
import com.ayush26.event_management_admin.Models.APIModels.GetAgeGroupsAPICall;
import com.ayush26.event_management_admin.Models.APIModels.LoginAPICall;
import com.ayush26.event_management_admin.Models.APIModels.ParticipantDetailAPICall;
import com.ayush26.event_management_admin.Models.APIModels.SingleCompetitionAPICall;
import com.ayush26.event_management_admin.Models.APIModels.SubmissionAPI;
import com.ayush26.event_management_admin.Models.APIModels.SubmissionDetailAPICall;
import com.ayush26.event_management_admin.Models.CompetitionModel;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPIInterface {


    // admin register api
    @FormUrlEncoded
    @POST("auth/sign-up/admin")
    Call<APICall> SignupAdmin(@Field("name") String name, @Field("email") String email, @Field("password")String password, @Field("secret_code")String code);

    //admin login api
    @FormUrlEncoded
    @POST("auth/login/admin")
    Call<LoginAPICall> LoginAdmin(@Field("email") String email, @Field("password")String password);

    //judge login via judge code
    @FormUrlEncoded
    @POST("submission/judge-login")
    Call<LoginAPICall> JudgeLogin(@Field("code") String code);

    //to add a age-group in existing competition
    @FormUrlEncoded
    @POST("competition/age-group/add")
    Call<AddAgeGroupAPICall> AddAgeGroup(@Header("token") String token, @Field("competition_id") String id, @Field("start_age")String startAge,
                                         @Field("end_age")String endAge, @Field("name")String name);

    //to add new competition and set its status as upcoming
    @FormUrlEncoded
    @POST("competition/add")
    Call<CompetitionAddAPI> AddCompetition(@Header("token") String token, @Field("title") String title,@Field("description") String desc,@Field("start_date") String sDate,
                                           @Field("end_date") String eDate,@Field("first") String first,@Field("second") String second,@Field("third") String third,
                                           @Field("registration_fee") int fee,@Field("genre") String genre) ;

    //to get all competitions for admin
    @GET("competition/all/admin")
    Call<CompetitionGetAPI> GetCompetitions(@Header("token") String token) ;


    //to get details of particular competitions
    @GET("competition/{competition_id}/admin")
    Call<SingleCompetitionAPICall> getCompetitionDetails(@Header("token") String token, @Path("competition_id") String id) ;

    //to get number of registrations in each age-group
    @GET("competition/participants/{competition_id}")
    Call<GetAgeGroupsAPICall> getAgeGroupDetails(@Header("token") String token, @Path("competition_id") String id) ;


    //to set competition status as ongoing
    @FormUrlEncoded
    @POST("competition/open")
    Call<AddAgeGroupAPICall> openCompetition(@Header("token") String token, @Field("competition_id") String id) ;

    //set competition status as completed
    @FormUrlEncoded
    @POST("competition/close")
    Call<AddAgeGroupAPICall> closeCompetition(@Header("token") String token, @Field("competition_id") String id) ;




    //submission related apis for admin

    //to get submission for one age group
    @GET("submission/fetch/{age_group_id}")
    Call<SubmissionAPI> getSubmissionsAdmin(@Header("token") String token, @Path("age_group_id") String id) ;

    //to get contact details of participant
    @GET("participant/{participant_id}/contact-details/admin")
    Call<ParticipantDetailAPICall> getParticipantDetailsAdmin(@Header("token") String token, @Path("participant_id") String id) ;

    //to get single submission details
    @GET("submission/details/{submission_id}/admin")
    Call<SubmissionDetailAPICall> adminSubmissionDetails(@Header("token") String token, @Path("submission_id") String id) ;

    //submission related apis for judge

    //to get all submissions
    @GET("submission/age-group")
    Call<SubmissionAPI> getSubmissionsJudge(@Header("token") String token) ;

//    to get participant contact detail
    @GET("participant/{participant_id}/contact-details")
    Call<ParticipantDetailAPICall> getParticipantDetailsJudge(@Header("token") String token, @Path("participant_id") String id) ;

    //to get single submission details
    @GET("submission/details/{submission_id}/judge")
    Call<SubmissionDetailAPICall> judgeSubmissionDetails(@Header("token") String token, @Path("submission_id") String id) ;


    //to get judge code from age group id
    @GET("competition/age-group/{age_group_id}/code")
    Call<AgeGroupCodeAPI> getAgeGroupCode(@Header("token") String token, @Path("age_group_id") String id) ;

    //to edit competition
    @FormUrlEncoded
    @PUT("competition/edit")
    Call<APICall> updateCompetitionGeneral(@Header("token") String token, @Field("competition_id") String competitionId,
                                           @Field("title") String title,@Field("description") String desc,@Field("start_date") String sDate,
                                           @Field("end_date") String eDate,@Field("first") String first,@Field("second") String second,@Field("third") String third,
                                           @Field("registration_fee") int fee,@Field("genre") String genre) ;


    //to delete competition
    //to send notification to users


    // to edit single age group
    @FormUrlEncoded
    @PUT("competition/age-group/edit")
    Call<APICall> updateCompetitionAgeGroup(@Header("token") String token, @Field("competition_id") String competitionId,
                                            @Field("age_group_id") String ageGroupID,@Field("start_age") int startAge,
                                            @Field("end_age") int endAge, @Field("name") String name) ;

}
