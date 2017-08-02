package in.myzoffrmerchants.main_activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.myzoffrmerchants.Constant.Appconstant;
import in.myzoffrmerchants.Constant.CustomMultipartRequest;
import in.myzoffrmerchants.Constant.HttpUrlPath;
import in.myzoffrmerchants.R;
import in.myzoffrmerchants.ucropImagepicker.PickerBuilder;
import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by ritesh on 24/7/17.
 */
@SuppressWarnings("deprecation")
public class ProfileCreateActivity extends ActivityManagePermission
        implements EasyPermissions.PermissionCallbacks {


    private static final int TIME_DELAY = 2000;
    private static final String GET_MERCHANT_DETAIL = "get_profile?";
    private static final String GET_CITY_LIST_DETAIL = "city_list";


    /*https://github.com/amitshekhariitbhu/Fast-Android-Networking#uploading-a-file-to-server*/
    /*https://github.com/amitshekhariitbhu/Fast-Android-Networking#uploading-a-file-to-server*/
    /*https://github.com/amitshekhariitbhu/Fast-Android-Networking#uploading-a-file-to-server*/
    /*https://github.com/amitshekhariitbhu/Fast-Android-Networking#uploading-a-file-to-server*/
    private static final String GET_AREA_LIST_DETAIL = "get_sub_city";
    private static final String UPDATE_MERCHANT_DETAIL = "user_update?";
    private static final int RC_CAMERA_PERM = 123;
    private static final String TAG = "MainCategoryActivity";
    private static long back_pressed;
    @BindView(R.id.toolbar_create_profile)
    Toolbar Toolbar_create_profile;
    @BindView(R.id.tv_create_merchant_profile_name)
    TextView Tv_create_merchant_profile_name;
    @BindView(R.id.tv_create_merchant_email)
    TextView Tv_create_merchant_email;
    @BindView(R.id.tv_create_merchant_mobile)
    TextView Tv_create_merchant_mobile;
    @BindView(R.id.mtspn_places)
    MaterialSpinner Mtspn_places;
    @BindView(R.id.mtspn_area)
    MaterialSpinner Mtspn_area;
    @BindView(R.id.civ_create_profile_image)
    CircleImageView Civ_create_profile_image;
    @BindView(R.id.iv_create_pan_card)
    ImageView Iv_create_pan_card;
    @BindView(R.id.iv_create_adhar_voter_image)
    ImageView Iv_create_adhar_voter_image;
    @BindView(R.id.iv_create_highest_qualification)
    ImageView Iv_create_highest_qualification;
    @BindView(R.id.iv_create_profile_latest_address_proof)
    ImageView Iv_create_profile_latest_address_proof;
    @BindView(R.id.cv_create_profile_btn)
    CardView cv_create_profile_btn;
    @BindView(R.id.cv_create_profile_pan_card)
    CardView Cv_create_profile_pan_card;
    @BindView(R.id.cv_create_profile_adhar_card)
    CardView Cv_create_profile_adhar_card;
    @BindView(R.id.cv_create_profile_qualification)
    CardView Cv_create_profile_qualification;
    @BindView(R.id.cv_create_profile_latest_address_proof)
    CardView Cv_create_profile_latest_address_proof;
    @BindView(R.id.edt_residential_address)
    EditText Edt_residential_address;
    @BindView(R.id.edt_office_address)
    EditText Edt_office_address;
    @BindView(R.id.rl_create_profile_btn)
    RelativeLayout Rl_browse_create_profile_btn;
    @BindView(R.id.btn_browse_pan_card)
    Button Btn_browse_pan_card;
    @BindView(R.id.btn_browse_adhar_card)
    Button Btn_browse_adhar_voter_card;
    @BindView(R.id.btn_browse_qualification)
    Button Btn_browse_create_qualification;
    @BindView(R.id.btn_browse_voter_id)
    Button Btn_browse_latest_address_proof;
    ArrayList<String> City_typelist = new ArrayList<String>();
    ArrayList<String> Area_typelist = new ArrayList<String>();
    String Sh_pre_User_ID = "",
            Sh_pre_Merchant_email = "",
            Sh_pre_Merchant_username = "",
            Sh_pre_Merchant_mobile = "",
            Str_Get_Profile_Status = "",
            Str_Get_message = "",
            Str_Get_result = "",
            Str_Get_User_ID = "",
            Str_Get_user_name = "",
            Str_Get_phone_number = "",
            Str_Get_emailID = "",
            Str_Get_office_address = "",
            Str_Get_residential_address = "",
            Str_Get_profile_image = "";
    /*string spinner (Start)*/
    String StrAll_Get_work_location_name = "",
            Str_Get_work_location_cityname = "",
            Str_Get_work_location_areaname = "",
            Str_Get_work_location_cityid = "",
            Str_Get_work_location_areaid = "",
            Str_Get_City_List_default,
            Str_Get_City_List_Status = "",
            Str_Get_City_List_Message = "",
            Str_Get_City_List_Result = "",
            Str_Get_City_List_single_id = "",
            Str_Get_City_List_single_name = "",
            Str_Get_Area_List_default,
            Str_Get_Area_List_Status = "",
            Str_Get_Area_List_Message = "",
            Str_Get_Area_List_Result = "",
            Str_Get_Area_List_single_id = "",
            Str_Get_MainCityID_List_single_id = "",
            Str_Get_Area_List_single_name = "";
    /*string image (Start)*/
    String Str_Pan_Card_path = "",
            Str_profileImage_path = "",
            Str_Get_Status = "",
            Str_Aadhaar_Card_path = "",
            Str_HighestQualification_path = "",
            Str_Latest_Add_Proof_path = "";
    /*string spinner (End)*/
    Dialog QuickTipDialog;


    /*string image (End)*/
    File ProfileImage, Pan_Card_Image, Aadhaar_Card_Image, HighestQualification_Image, AddressProof_Image;
    FileBody ProdileImagefilebody,
            PanCardImagefilebody,
            AadhaarVoterImagefilebody,
            HighestQualificationImagefilebody,
            AddressProofImagefilebody;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_create);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());

        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        Sh_pre_User_ID = Appconstant.sh.getString("id", null);
        Sh_pre_Merchant_email = Appconstant.sh.getString("email", null);
        Sh_pre_Merchant_username = Appconstant.sh.getString("username", null);
        Sh_pre_Merchant_mobile = Appconstant.sh.getString("mobile", null);


        Log.e("Merchant_ID from SharedPref :", "" + Sh_pre_User_ID);
        Log.e("Merchant_email from SharedPref :", "" + Sh_pre_Merchant_email);
        Log.e("Merchant_username from SharedPref :", "" + Sh_pre_Merchant_username);
        Log.e("Merchant_mobile from SharedPref :", "" + Sh_pre_Merchant_mobile);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        setSupportActionBar(Toolbar_create_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Get_MerchantDetail_Fast();

        Get_City_List_Fast();


        Edt_residential_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Edt_office_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


//        Mtspn_places.setItems(USER_TYPE);
        Mtspn_places.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                android.support.design.widget.Snackbar.make(view, "Register as " + item, android.support.design.widget.Snackbar.LENGTH_LONG).show();
//                User_type = item;
                int posi = position;
                Str_Get_work_location_cityid = String.valueOf(posi);
                Str_Get_work_location_cityname = item;
                Log.e("posi ID:", "" + posi);
                Log.e("Str_Get_work_location_cityname:", "" + Str_Get_work_location_cityname);
                Log.e("Str_Get_work_location_cityid:", "" + Str_Get_work_location_cityid);

//                Log.e("User_type :", "" + User_type);
                Log.e("User_type Detail :", "Position :" + "" + position
                        + "\t" + "ID :" + "" + id
                        + "\t" + "Name :" + "" + item);

                /*save value in sharepreference*/
                Appconstant.editor.putString("location_cityname", Str_Get_work_location_cityname);
                Appconstant.editor.putString("location_cityid", Str_Get_work_location_cityid);

                Appconstant.editor.commit();



                /*get value from sharedpreference*/
                Str_Get_work_location_cityname = Appconstant.sh.getString("location_cityname", null);
                Str_Get_work_location_areaname = Appconstant.sh.getString("location_areaname", null);
                Str_Get_work_location_cityid = Appconstant.sh.getString("location_cityid", null);
                Str_Get_work_location_areaid = Appconstant.sh.getString("location_areaid", null);

                Log.e("location_cityname from shared preference :", "" + Str_Get_work_location_cityname);
                Log.e("location_cityid from shared preference:", "" + Str_Get_work_location_cityid);

                Get_Area_List_Fast();

//                UpdateMerchant_work_location_Fast();

//                GetSet_user_Type_ID = String.valueOf(posi);
//                GetSet_user_Type = item;
//                Log.e("GetSet_user_Type :", "" + GetSet_user_Type);
            }
        });
        Mtspn_places.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                android.support.design.widget.Snackbar.make(spinner, "Please Select Your City",
                        android.support.design.widget.Snackbar.LENGTH_SHORT).show();

            }
        });

//        Mtspn_places.setItems(USER_TYPE);
        Mtspn_area.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                android.support.design.widget.Snackbar.make(view, "Register as " + item, android.support.design.widget.Snackbar.LENGTH_LONG).show();
//                User_type = item;
                int posi = position;
                Str_Get_work_location_areaid = String.valueOf(posi);
                Str_Get_work_location_areaname = item;


                Log.e("posi ID:", "" + posi);
                Log.e("Str_Get_work_location_areaname:", "" + Str_Get_work_location_areaname);
                Log.e("Str_Get_work_location_areaid:", "" + Str_Get_work_location_areaid);


                Str_Get_Area_List_single_id = String.valueOf(posi);
                Log.e("Str_Get_Area_List_single_id :", "" + Str_Get_Area_List_single_id);

//                Log.e("User_type :", "" + User_type);
                Log.e("User_type Detail :", "Position :" + "" + position
                        + "\t" + "ID :" + "" + id
                        + "\t" + "Name :" + "" + item);

                /*save value in sharepreference*/
                Appconstant.editor.putString("location_areaname", Str_Get_work_location_areaname);
                Appconstant.editor.putString("location_areaid", Str_Get_work_location_areaid);

                Appconstant.editor.commit();



                /*get value from sharedpreference*/
                /*get value from sharedpreference*/
                Str_Get_work_location_cityname = Appconstant.sh.getString("location_cityname", null);
                Str_Get_work_location_cityid = Appconstant.sh.getString("location_cityid", null);

                Str_Get_work_location_areaname = Appconstant.sh.getString("location_areaname", null);
                Str_Get_work_location_areaid = Appconstant.sh.getString("location_areaid", null);


                Log.e("location_cityname from shared preference :", "" + Str_Get_work_location_cityname);
                Log.e("location_cityid from shared preference:", "" + Str_Get_work_location_cityid);

                Log.e("location_areaname from shared preference :", "" + Str_Get_work_location_areaname);
                Log.e("location_areaid from shared preference:", "" + Str_Get_work_location_areaid);

//                Get_Area_List_Fast();

//                UpdateMerchant_work_location_Fast();

//                GetSet_user_Type_ID = String.valueOf(posi);
//                GetSet_user_Type = item;
//                Log.e("GetSet_user_Type :", "" + GetSet_user_Type);
            }
        });
        Mtspn_places.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                android.support.design.widget.Snackbar.make(spinner, "Please Select Your City",
                        android.support.design.widget.Snackbar.LENGTH_SHORT).show();

            }
        });


//        Get_City_List_Fast();


        Rl_browse_create_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ProfileImagePicker();

                cameraTask();
            }
        });

        Btn_browse_pan_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PanCardImagePicker();
            }
        });


        Btn_browse_adhar_voter_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PanCardImagePicker();
                AadhaarVoterImagePicker();
            }
        });


        Btn_browse_create_qualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PanCardImagePicker();
                HighestQualificationImagePicker();
            }
        });


        Btn_browse_latest_address_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PanCardImagePicker();
                LatestAddressProofImagePicker();
            }
        });


        cv_create_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PanCardImagePicker();

                boolean iserror = false;

                Str_Get_office_address = Edt_office_address.getText().toString().trim();
                Str_Get_residential_address = Edt_residential_address.getText().toString().trim();

                Log.e(" Create Profile Fields data :", "\n"
                        + "Str_Get_office_address :" + "" + Str_Get_office_address + "\n"
                        + "Str_Get_residential_address :" + "" + Str_Get_residential_address + "\n"
                        + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                        + "Sh_pre_Merchant_mobile :" + "" + Sh_pre_Merchant_mobile + "\n"
                        + "Str_Get_work_location_cityname :" + "" + Str_Get_work_location_cityname + "\n"
                        + "Str_Get_work_location_cityid :" + "" + Str_Get_work_location_cityid + "\n"
                        + "Str_Get_work_location_areaname :" + "" + Str_Get_work_location_areaname + "\n"
                        + "Str_Get_work_location_areaid :" + "" + Str_Get_work_location_areaid + "\n"
                        + "Str_profileImage_path :" + "" + Str_profileImage_path + "\n"
                        + "Str_AadhaarVoter_path :" + "" + Str_Aadhaar_Card_path + "\n"
                        + "Str_HighestQualification_path :" + "" + Str_HighestQualification_path + "\n"
                        + "Str_Latest_Add_Proof_path :" + "" + Str_Latest_Add_Proof_path + "\n"
                        + "Str_Pan_Card_path :" + "" + Str_Pan_Card_path + "\n"
                        + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n");


//                CreateProfileJsontask task = new CreateProfileJsontask();
//                task.execute();


                if (Str_Get_work_location_cityid.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Mtspn_places);
                    /**************** End Animation ****************/


                    Toast.makeText(getApplicationContext(), "Please select service City",
                            Toast.LENGTH_SHORT).show();


                } else if (Str_Get_work_location_areaid.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Mtspn_area);
                    /**************** End Animation ****************/


                    Toast.makeText(getApplicationContext(),
                            "Please select service area", Toast.LENGTH_SHORT).show();

                } else if (Str_Get_residential_address.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_residential_address);
                    /**************** End Animation ****************/


                    Toast.makeText(getApplicationContext(),
                            "Please type Residential Address", Toast.LENGTH_SHORT).show();

                } else if (Str_Get_office_address.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_office_address);
                    /**************** End Animation ****************/


                    Toast.makeText(getApplicationContext(),
                            "Please type Office Address", Toast.LENGTH_SHORT).show();

                } else if (Str_profileImage_path.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Civ_create_profile_image);
                    /**************** End Animation ****************/


                    Toast.makeText(getApplicationContext(),
                            "Please select your Profile Image", Toast.LENGTH_SHORT).show();

                } else if (Str_Pan_Card_path.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Cv_create_profile_pan_card);
                    /**************** End Animation ****************/


                    Toast.makeText(getApplicationContext(),
                            "Please select your Pan Card Image", Toast.LENGTH_SHORT).show();

                } else if (Str_Aadhaar_Card_path.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Cv_create_profile_adhar_card);
                    /**************** End Animation ****************/


                    Toast.makeText(getApplicationContext(),
                            "Please select your Aadhaar/Voter Image", Toast.LENGTH_SHORT).show();

                } else if (Str_HighestQualification_path.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Cv_create_profile_qualification);
                    /**************** End Animation ****************/


                    Toast.makeText(getApplicationContext(),
                            "Please select your Highest Qualification Image", Toast.LENGTH_SHORT).show();

                } else if (Str_Latest_Add_Proof_path.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Cv_create_profile_latest_address_proof);
                    /**************** End Animation ****************/


                    Toast.makeText(getApplicationContext(),
                            "Please select your latest address proof Image", Toast.LENGTH_SHORT).show();

                }
                if (!iserror) {
                    Log.e("No Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                    Toast.makeText(getApplicationContext(), "No Error", Toast.LENGTH_SHORT).show();

//                    Update_Profile_Fast();


                    CreateProfileJsontask task = new CreateProfileJsontask();
                    task.execute();

                }


            }
        });

    }


    /*using Fast Android Networking */
    private void Get_MerchantDetail_Fast() {

        Log.e("Get_MerchantDetail_Fast ********************* :", "*************************");

        Log.e("merchantID :", "" + Sh_pre_User_ID);
        String tag_string_req = "req_login";
        pDialog.setMessage("Logging in ...");
        showDialog();


        AndroidNetworking.post(HttpUrlPath.urlPathMain + GET_MERCHANT_DETAIL)
                .addBodyParameter("user_id", Sh_pre_User_ID)
//                .addBodyParameter("lastname", "Shekhar")
                .setTag("test")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject responsee) {
                        // do anything with response
                        hideDialog();
                        Log.e("Response :", "" + responsee);

                        try {
                            JSONObject jobjresponse = new JSONObject(String.valueOf(responsee));
                            Log.e("jobjresponse :", "" + jobjresponse);
                            Str_Get_Profile_Status = jobjresponse.getString("status");
                            Log.e("Str_Get_Status :", "" + Str_Get_Profile_Status);

                            Str_Get_message = jobjresponse.getString("message");
                            Log.e("Str_Get_message :", "" + Str_Get_message);


                            if (Str_Get_Profile_Status.equalsIgnoreCase("1")) {
                                Log.e("StrGet_Status is:", "1");

                                Str_Get_result = jobjresponse.getString("result");
                                Log.e("Str_Get_result :", "" + Str_Get_result);

                                JSONObject jobjresult = jobjresponse.getJSONObject("result");
                                Log.e("jobjresult :", "" + jobjresult.toString());
//                                tv_status.setText(StrGet_Status);

                                Str_Get_User_ID = jobjresult.getString("id");
                                Str_Get_user_name = jobjresult.getString("username");
                                Str_Get_phone_number = jobjresult.getString("mobile");
                                Str_Get_emailID = jobjresult.getString("email");

                                Tv_create_merchant_profile_name.setText(Str_Get_user_name);
                                Tv_create_merchant_email.setText(Str_Get_emailID);
                                Tv_create_merchant_mobile.setText(Str_Get_phone_number);


                            } else {
                                Log.e("Str_Get_Profile_Status is:", "" + "0");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
//                        Toast.makeText(MainVolleyActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());
                    }
                });


    }
    /*using Fast Android Networking */


    /*using Fast Android Networking */
    private void Get_City_List_Fast() {

        Log.e("Get_City_List_Fast********************* :", "*************************");

        pDialog.setMessage("Fetching City List...");
        showDialog();


        AndroidNetworking.post(HttpUrlPath.urlPathMain + GET_CITY_LIST_DETAIL)
//                .addBodyParameter("user_id", Sh_pre_User_ID)
//                .addBodyParameter("lastname", "Shekhar")
                .setTag("citylist")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject responsee) {
                        // do anything with response
                        hideDialog();
                        Log.e("Response :", "" + responsee);

                        hideDialog();
                        Log.e("Response City List:", "" + responsee);
                        try {
                            City_typelist.add("Please select City");
                            JSONObject jobjresponse = new JSONObject(String.valueOf(responsee));
                            Log.e("jobjresponse List:", "" + jobjresponse);
                            Str_Get_City_List_Status = jobjresponse.getString("status");
                            Log.e("Str_Get_City_List_Status List:", "" + Str_Get_City_List_Status);

                            Str_Get_City_List_Message = jobjresponse.getString("message");
                            Log.e("Str_Get_City_List_Message :", "" + Str_Get_City_List_Message);


                            if (Str_Get_City_List_Status.equalsIgnoreCase("1")) {
                                Log.e("Str_Get_City_List_Status is:", "1");

//                                tv_status.setText(StrGet_Status);
                                Str_Get_City_List_Result = jobjresponse.getString("result");
                                Log.e("Str_Get_City_List_Result :", "" + Str_Get_City_List_Result);


                                JSONArray jsonArray = jobjresponse.getJSONArray("result");
                                Log.e("jsonArray List:", "" + jsonArray.toString());

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Str_Get_City_List_single_name = jsonObject.getString("name");
                                    Str_Get_City_List_single_id = jsonObject.getString("id");
                                    City_typelist.add(Str_Get_City_List_single_name);


                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("response ListList:", "" + responsee);

                        if (Str_Get_City_List_Status.equalsIgnoreCase("1")) {
                            Log.e("Str_Get_City_List_Status is:", "1");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileCreateActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item, City_typelist);
                            Mtspn_places.setItems(City_typelist);
                            Str_Get_City_List_default = City_typelist.get(0);
                            Log.e("Str_Get_City_List_default ", "" + Str_Get_City_List_default);

                            Log.e(" City list result :", "" + City_typelist.size());
                            Log.e("All City list Names:", "" + City_typelist
                                    + "\n" + "item 0 :" + "" + City_typelist.size());
                        } else {
                            Log.e("Str_Get_City_List_Status is:", "0");
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
//                        Toast.makeText(MainVolleyActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());
                    }
                });
    }
    /*using Fast Android Networking */


    /*using Fast Android Networking */
    private void Get_Area_List_Fast() {

        Log.e("Get_Area_List_Fast********************* :", "*************************");

        pDialog.setMessage("Fetching Area List...");
        showDialog();


        AndroidNetworking.post(HttpUrlPath.urlPathMain + GET_AREA_LIST_DETAIL)
                .addBodyParameter("city_id", Str_Get_work_location_cityid)
//                .addBodyParameter("lastname", "Shekhar")
                .setTag("Arealist")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject responsee) {
                        // do anything with response
                        hideDialog();
                        Log.e("Response :", "" + responsee);

                        hideDialog();
                        Log.e("Response Area List:", "" + responsee);
                        try {
                            Area_typelist.add("Please select Area");
                            JSONObject jobjresponse = new JSONObject(String.valueOf(responsee));
                            Log.e("jobjresponse List:", "" + jobjresponse);
                            Str_Get_Area_List_Status = jobjresponse.getString("status");
                            Log.e("Str_Get_Area_List_Status List:", "" + Str_Get_Area_List_Status);

                            Str_Get_Area_List_Message = jobjresponse.getString("message");
                            Log.e("Str_Get_Area_List_Message :", "" + Str_Get_Area_List_Message);


                            if (Str_Get_Area_List_Status.equalsIgnoreCase("1")) {
                                Log.e("Str_Get_Area_List_Status is:", "1");

//                                tv_status.setText(StrGet_Status);
                                Str_Get_Area_List_Result = jobjresponse.getString("result");
                                Log.e("Str_Get_Area_List_Result :", "" + Str_Get_Area_List_Result);


                                JSONArray jsonArray = jobjresponse.getJSONArray("result");
                                Log.e("jsonArray List:", "" + jsonArray.toString());

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Str_Get_Area_List_single_name = jsonObject.getString("name");
                                    Str_Get_City_List_single_id = jsonObject.getString("id");
                                    Str_Get_MainCityID_List_single_id = jsonObject.getString("city_id");
                                    Area_typelist.add(Str_Get_Area_List_single_name);
//                                    Area_typelist.add(Str_Get_MainCityID_List_single_id);


                                    Log.e("Str_Get_Area_List_single_name :", "" + Str_Get_Area_List_single_name);
                                    Log.e("Str_Get_City_List_single_id :", "" + Str_Get_City_List_single_id);
                                    Log.e("Str_Get_MainCityID_List_single_id :", "" + Str_Get_MainCityID_List_single_id);


                                }


                                Mtspn_area.setEnabled(true);
                                Mtspn_area.setSelected(true);

                            }
//                            else {
//
//                                Log.e("Str_Get_Area_List_Status is:", "0");
//                                Log.e("Str_Get_Area_List_Result :", "" + Str_Get_Area_List_Result);
//
////
//                                Mtspn_area.setEnabled(false);
//                                Mtspn_area.setSelected(false);
//
//                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfileActivity.this,
//                                        android.R.layout.simple_spinner_dropdown_item, Area_typelist);
//                                Mtspn_area.setItems(Area_typelist);
//                                Str_Get_Area_List_default = Area_typelist.get(0);
//                                Mtspn_area.setSelectedIndex(0);
//                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("response AreaList:", "" + responsee);

                        if (Str_Get_Area_List_Status.equalsIgnoreCase("1")) {
                            Log.e("Str_Get_Area_List_Status is:", "1");

                            Mtspn_area.setEnabled(true);
                            Mtspn_area.setSelected(true);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileCreateActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item, Area_typelist);
                            Mtspn_area.setItems(Area_typelist);
                            Str_Get_Area_List_default = Area_typelist.get(0);
                            Log.e("Str_Get_Area_List_default ", "" + Str_Get_Area_List_default);

                            Log.e(" Area list result :", "" + Area_typelist.size());
                            Log.e("All City list Names:", "" + Area_typelist
                                    + "\n" + "item 0 :" + "" + Area_typelist.size());
                        } else {
                            Log.e("Str_Get_Area_List_Status is:", "0");

                            Area_typelist.clear();
                            Mtspn_area.setEnabled(false);
                            Mtspn_area.setSelected(false);


//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfileActivity.this,
//                                    android.R.layout.simple_spinner_dropdown_item, Area_typelist);
//                            Mtspn_area.setItems(Area_typelist);
//                            Str_Get_Area_List_default = Area_typelist.get(0);
//                            Mtspn_area.setSelectedIndex(0);


                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
//                        Toast.makeText(MainVolleyActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());
                    }
                });
    }
    /*using Fast Android Networking */


    private void ProfileImagePicker() {
        QuickTipDialog = new Dialog(ProfileCreateActivity.this);
//                callFeeDialog = new Dialog(MainBuyerActivity.this);
        QuickTipDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        QuickTipDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        QuickTipDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        QuickTipDialog.setContentView(R.layout.activity_dialog_image_picker);
        final RelativeLayout RlCamera = (RelativeLayout) QuickTipDialog.findViewById(R.id.rl_inner_camera);
        final RelativeLayout RlGallery = (RelativeLayout) QuickTipDialog.findViewById(R.id.rl_inner_gallery);
        final TextView TvCancel = (TextView) QuickTipDialog.findViewById(R.id.tv_dialog_cancel);

        TvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickTipDialog.dismiss();
            }
        });

        RlCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(ProfileCreateActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri ProfileimageUriCamera) {
//                                Toast.makeText(EditProfileActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Civ_edit_profile_image.setImageURI(imageUri);

                                Glide.with(Civ_create_profile_image.getContext())
                                        .load(ProfileimageUriCamera)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Civ_create_profile_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Civ_create_profile_image
                                                        .getContext())
                                                        .load(ProfileimageUriCamera)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Civ_create_profile_image);
                                            }
                                        });


                                Log.e("imageUri path SELECT_FROM_CAMERA :", "" + ProfileimageUriCamera);
                                File f = new File(ProfileimageUriCamera.getPath());
                                Str_profileImage_path = ProfileimageUriCamera.getPath();

                                Log.e("Str_profileImage_path SELECT_FROM_CAMERA :", "" + Str_profileImage_path);
                                if (!Str_profileImage_path.equals("")) {

                                    Log.e("Str_profileImage_path File Image availabe :", "" + Str_profileImage_path);
                                    ProfileImage = new File(Str_profileImage_path);
                                    Log.e("profile File Image:", "" + ProfileImage);

//                                    Update_MerchantProfile_Image_Volley();

                                } else {

                                    Log.e("Str_profileImage_path File Image blank:", "" + Str_profileImage_path);
                                    ProfileImage = new File("profileiamge");
                                    Log.e("profile File Image:", "" + ProfileImage);

                                }

                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("profileImage")
                        .setImageFolderName("ZoffrBusiness")
                        .withTimeStamp(false)
                        .setCropScreenColor(Color.CYAN)
                        .start();

            }
        });

        RlGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(ProfileCreateActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri ProfileimageUriGallery) {
//                                Civ_edit_profile_image.setImageURI(imageUri);
//                                Toast.makeText(EditProfileActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
                                Glide.with(Civ_create_profile_image.getContext())
                                        .load(ProfileimageUriGallery)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Civ_create_profile_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Civ_create_profile_image
                                                        .getContext())
                                                        .load(ProfileimageUriGallery)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Civ_create_profile_image);
                                            }
                                        });

                                Log.e("imageUri path SELECT_FROM_GALLERY :", "" + ProfileimageUriGallery);
                                File f = new File(ProfileimageUriGallery.getPath());
                                Str_profileImage_path = ProfileimageUriGallery.getPath();
                                Log.e("Str_profileImage_path SELECT_FROM_GALLERY :", "" + Str_profileImage_path);

                                if (!Str_profileImage_path.equals("")) {

                                    Log.e("Str_profileImage_path File Image availabe :", "" + Str_profileImage_path);
                                    ProfileImage = new File(Str_profileImage_path);
                                    Log.e("profile File Image:", "" + ProfileImage);

//                                    Update_MerchantProfile_Image_Volley();

                                } else {

                                    Log.e("Str_profileImage_path File Image blank:", "" + Str_profileImage_path);
                                    ProfileImage = new File("profileiamge");
                                    Log.e("profile File Image:", "" + ProfileImage);

                                }


                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("profileImage")
                        .setImageFolderName("ZoffrBusiness")
                        .setCropScreenColor(Color.CYAN)
                        .setOnPermissionRefusedListener(new PickerBuilder.onPermissionRefusedListener() {
                            @Override
                            public void onPermissionRefused() {

                            }
                        })
                        .start();


            }
        });


        QuickTipDialog.show();
    }

    /*using volley */
    private void Update_MerchantProfile_Image_Volley() {
        Log.e("Update_MerchantProfile_Image_Fast********************* :", "*************************");
        Str_Get_work_location_areaid = Appconstant.sh.getString("location_areaid", null);
        Log.e("Update_MerchantProfile_Image_Fast work_location_id from SharedPref :", "" + Str_Get_work_location_areaid);

        Log.e("Fast Update_MerchantProfile_Image_Fast Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                + "Str_profileImage_path :" + "" + Str_profileImage_path + "\n"
                + "work_location_id :" + "" + Str_Get_work_location_areaid + "\n"
                + "Str_Get_office_address :" + "" + Str_Get_office_address + "\n"
                + "Str_Get_residential_address :" + "" + Str_Get_residential_address + "\n"
                + "Str_Get_work_location_cityid :" + "" + Str_Get_work_location_cityid + "\n"
                + "Str_Get_work_location_areaid :" + "" + Str_Get_work_location_areaid + "\n"
                + "Sh_pre_Merchant_mobile :" + "" + Sh_pre_Merchant_mobile + "\n");

        pDialog.setMessage("Updating Profile...");
        showDialog();
        //Auth header
        Map<String, String> mHeaderPart = new HashMap<>();
        mHeaderPart.put("Content-type", "multipart/form-data;");
//        mHeaderPart.put("access_token", accessToken);

        //File part
        Map<String, File> mFilePartData = new HashMap<>();
        mFilePartData.put("marchant_img", new File(Str_profileImage_path));
//        mFilePartData.put("pan_card", new File(Str_Pan_Card_path));
//        mFilePartData.put("adhar_card", new File(Str_Aadhaar_Card_path));
//        mFilePartData.put("voter_id", new File(Str_Latest_Add_Proof_path));
//        mFilePartData.put("license", new File(Str_HighestQualification_path));
//        mFilePartData.put("file", new File(mFilePath));

        //String part
        Map<String, String> mStringPart = new HashMap<>();
        mStringPart.put("user_id", Sh_pre_User_ID);
        mStringPart.put("email", Sh_pre_Merchant_email);
        mStringPart.put("username", Sh_pre_Merchant_username);
        mStringPart.put("mobile", Sh_pre_Merchant_mobile);
        mStringPart.put("work_address", Str_Get_office_address);
        mStringPart.put("home_address", Str_Get_residential_address);
        mStringPart.put("work_location", Str_Get_work_location_cityid);
        mStringPart.put("work_sub_location", Str_Get_work_location_areaid);


        CustomMultipartRequest mCustomRequest = new CustomMultipartRequest(Request.Method.POST,
                ProfileCreateActivity.this, HttpUrlPath.urlPathMain + UPDATE_MERCHANT_DETAIL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        hideDialog();
                        Log.e("Response onResponse:", "" + jsonObject);
//                        merchantDetail();
                        Get_MerchantDetail_Fast();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                listener.onErrorResponse(volleyError);
                hideDialog();
                Log.e("Response volleyError:", "" + volleyError);

            }
        }, mFilePartData, mStringPart, mHeaderPart);

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(mCustomRequest);

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Adding request to the queue
        mCustomRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(mCustomRequest);
    }
    /*using volley */


    private void PanCardImagePicker() {
        QuickTipDialog = new Dialog(ProfileCreateActivity.this);
//                callFeeDialog = new Dialog(MainBuyerActivity.this);
        QuickTipDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        QuickTipDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        QuickTipDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        QuickTipDialog.setContentView(R.layout.activity_dialog_image_picker);
        final RelativeLayout RlCamera = (RelativeLayout) QuickTipDialog.findViewById(R.id.rl_inner_camera);
        final RelativeLayout RlGallery = (RelativeLayout) QuickTipDialog.findViewById(R.id.rl_inner_gallery);
        final TextView TvCancel = (TextView) QuickTipDialog.findViewById(R.id.tv_dialog_cancel);

        TvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickTipDialog.dismiss();
            }
        });

        RlCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(ProfileCreateActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Toast.makeText(EditProfileActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Iv_pan_card.setImageURI(imageUri);


                                Glide.with(Iv_create_pan_card.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_create_pan_card) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_create_pan_card
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_create_pan_card);
                                            }
                                        });


                                Log.e("Pan Card imageUri path SELECT_FROM_CAMERA :", "" + imageUri);
                                File f = new File(imageUri.getPath());
                                Str_Pan_Card_path = imageUri.getPath();
                                Log.e("Str_Pan_Card_path SELECT_FROM_CAMERA :", "" + Str_Pan_Card_path);

                                if (!Str_Pan_Card_path.equals("http:\\/\\/whatsapphindistatus.com\\/ZOF\\/uploads\\/images\\/")) {

                                    Log.e("Pan_Card_Image File Image availabe :", "" + Pan_Card_Image);
                                    Pan_Card_Image = new File(Str_Pan_Card_path);
                                    Log.e("Pan_Card_Image File Image:", "" + Pan_Card_Image);
                                    Iv_create_pan_card.setVisibility(View.VISIBLE);
//                                    UpdateMerchant_Pan_Card_Image_Fast();

                                } else {

                                    Log.e("Pan_Card_Image File Image blank:", "" + Pan_Card_Image);
                                    Pan_Card_Image = new File("Pan_Card_Image");
                                    Log.e("Pan_Card_Image File Image:", "" + Pan_Card_Image);
                                    Iv_create_pan_card.setVisibility(View.GONE);

                                }

                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("PanCardImage")
                        .setImageFolderName("ZoffrBusiness")
                        .withTimeStamp(false)
                        .setCropScreenColor(Color.CYAN)
                        .start();

            }
        });

        RlGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(ProfileCreateActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Iv_pan_card.setImageURI(imageUri);
                                Glide.with(Iv_create_pan_card.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_create_pan_card) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_create_pan_card
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_create_pan_card);
                                            }
                                        });


                                Log.e("Iv_pan_card imageUri path SELECT_FROM_GALLERY :", "" + imageUri);
                                File f = new File(imageUri.getPath());
                                Str_Pan_Card_path = imageUri.getPath();
                                Log.e("Str_Pan_Card_path SELECT_FROM_GALLERY :", "" + Str_Pan_Card_path);
//                                Pan_Card_Image = new File(Str_Pan_Card_path);
//                                Log.e("Pan_Card_Image File Image:", "" + Pan_Card_Image);

                                if (!Str_Pan_Card_path.equals("")) {

                                    Log.e("Pan_Card_Image File Image availabe :", "" + Pan_Card_Image);
                                    Pan_Card_Image = new File(Str_Pan_Card_path);
                                    Log.e("Pan_Card_Image File Image:", "" + Pan_Card_Image);
                                    Iv_create_pan_card.setVisibility(View.VISIBLE);
//                                    UpdateMerchant_Pan_Card_Image_Fast();

                                } else {

                                    Log.e("Pan_Card_Image File Image blank:", "" + Pan_Card_Image);
                                    Pan_Card_Image = new File("Pan_Card_Image");
                                    Log.e("Pan_Card_Image File Image:", "" + Pan_Card_Image);
                                    Iv_create_pan_card.setVisibility(View.GONE);

                                }

                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("PanCardImage")
                        .setImageFolderName("ZoffrBusiness")
                        .setCropScreenColor(Color.CYAN)
                        .setOnPermissionRefusedListener(new PickerBuilder.onPermissionRefusedListener() {
                            @Override
                            public void onPermissionRefused() {

                            }
                        })
                        .start();


            }
        });

        QuickTipDialog.show();
    }

    /*using Fast */
    private void UpdateMerchant_Pan_Card_Image_Fast() {
        Log.e("Update Pan_Card_Image_Fast********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                + "Str_Pan_Card_path :" + "" + Str_Pan_Card_path + "\n"
                + "Str_Get_office_address :" + "" + Str_Get_office_address + "\n"
                + "Str_Get_residential_address :" + "" + Str_Get_residential_address + "\n"
                + "Str_Get_work_location_cityid :" + "" + Str_Get_work_location_cityid + "\n"
                + "Str_Get_work_location_areaid :" + "" + Str_Get_work_location_areaid + "\n"
                + "Sh_pre_Merchant_mobile :" + "" + Sh_pre_Merchant_mobile + "\n");

        pDialog.setMessage("Updating Profile...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + UPDATE_MERCHANT_DETAIL)
//                .addMultipartFile("marchant_img", ProfileImage)
                .addMultipartFile("pan_card", Pan_Card_Image)
                .addMultipartParameter("user_id", Sh_pre_User_ID)
                .addMultipartParameter("email", Sh_pre_Merchant_email)
                .addMultipartParameter("username", Sh_pre_Merchant_username)
                .addMultipartParameter("work_location", Str_Get_work_location_areaid)
                .addMultipartParameter("mobile", Sh_pre_Merchant_mobile)
                .addMultipartParameter("work_address", Str_Get_office_address)
                .addMultipartParameter("home_address", Str_Get_residential_address)
                .addMultipartParameter("work_location", Str_Get_work_location_cityid)
                .addMultipartParameter("work_sub_location", Str_Get_work_location_areaid)
                .setPriority(Priority.MEDIUM)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
//                        hideDialog();
                        Log.e("Response onProgress bytesUploaded:", "" + bytesUploaded);
                        Log.e("Response onProgress totalBytes:", "" + totalBytes);

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        hideDialog();
                        Log.e("Response upload:", "" + String.valueOf(response));
//                        merchantDetailFast();

                        try {
                            JSONObject jobjresponse = new JSONObject(String.valueOf(response));
                            Log.e("jobjresponse upload :", "" + jobjresponse);
                            Str_Get_Status = jobjresponse.getString("status");
                            Log.e("Str_Get_Status upload :", "" + Str_Get_Status);

                            Str_Get_message = jobjresponse.getString("message");
                            Log.e("Str_Get_message upload :", "" + Str_Get_message);

                            Str_Get_result = jobjresponse.getString("result");
                            Log.e("Str_Get_result upload :", "" + Str_Get_result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (Str_Get_Status.equalsIgnoreCase("1")) {


                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                    Get_MerchantDetail_Fast();

                                }

                            }, 2000);
                        } else {
                            Log.e("Str_Get_Status :", "0");
                        }
//


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
                        Log.e("Response error:", "" + error.toString());
                    }
                });


    }
    /*using Fast */


    private void AadhaarVoterImagePicker() {
        QuickTipDialog = new Dialog(ProfileCreateActivity.this);
//                callFeeDialog = new Dialog(MainBuyerActivity.this);
        QuickTipDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        QuickTipDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        QuickTipDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        QuickTipDialog.setContentView(R.layout.activity_dialog_image_picker);
        final RelativeLayout RlCamera = (RelativeLayout) QuickTipDialog.findViewById(R.id.rl_inner_camera);
        final RelativeLayout RlGallery = (RelativeLayout) QuickTipDialog.findViewById(R.id.rl_inner_gallery);
        final TextView TvCancel = (TextView) QuickTipDialog.findViewById(R.id.tv_dialog_cancel);

        TvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickTipDialog.dismiss();
            }
        });

        RlCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(ProfileCreateActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Toast.makeText(EditProfileActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Iv_adhar_card.setImageURI(imageUri);


                                Glide.with(Iv_create_adhar_voter_image.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_create_adhar_voter_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_create_adhar_voter_image
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_create_adhar_voter_image);
                                            }
                                        });


                                Log.e("AadhaarCard imageUri path SELECT_FROM_CAMERA :", "" + imageUri);
                                File f = new File(imageUri.getPath());
                                Str_Aadhaar_Card_path = imageUri.getPath();
                                Log.e("Str_Aadhaar_Card_path SELECT_FROM_CAMERA :", "" + Str_Aadhaar_Card_path);

                                if (!Str_Aadhaar_Card_path.equals("")) {

                                    Log.e("Str_Aadhaar_Card_path File Image availabe :", "" + Str_Aadhaar_Card_path);
                                    Aadhaar_Card_Image = new File(Str_Aadhaar_Card_path);
                                    Log.e("Aadhaar_Card_Image File Image:", "" + Aadhaar_Card_Image);
                                    Iv_create_adhar_voter_image.setVisibility(View.VISIBLE);

//                                    UpdateMerchant_Aadhaar_Voter_Image_Fast();
                                } else {

                                    Log.e("Str_Aadhaar_Card_path File Image blank:", "" + Str_Aadhaar_Card_path);
                                    Aadhaar_Card_Image = new File("Str_Aadhaar_Card_path");
                                    Log.e("Aadhaar_Card_Image File Image:", "" + Aadhaar_Card_Image);
                                    Iv_create_adhar_voter_image.setVisibility(View.GONE);

                                }


                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("AadhaarCardImage")
                        .setImageFolderName("ZoffrBusiness")
                        .withTimeStamp(false)
                        .setCropScreenColor(Color.CYAN)
                        .start();

            }
        });

        RlGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(ProfileCreateActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Iv_adhar_card.setImageURI(imageUri);

                                Glide.with(Iv_create_adhar_voter_image.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_create_adhar_voter_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_create_adhar_voter_image
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_create_adhar_voter_image);
                                            }
                                        });


                                Log.e("Iv_adhar_card imageUri path SELECT_FROM_GALLERY :", "" + imageUri);
//                                File file_aadhaar = new File(imageUri.getPath());
//                                long length = file_aadhaar.length();
//                                long lengths = file_aadhaar.length();
//                                length = length / 1024;
//                                lengths = length;
//                                Log.e("file_aadhaar size in KB:", "" + length);
//                                Log.e("file_aadhaar sssssize in KB:", "" + lengths);

                                Str_Aadhaar_Card_path = imageUri.getPath();
//                                String filename = imageUri.getLastPathSegment();
//                                Log.e("filename :", "" + filename);
//                                Str_Get_adhar_card_image_name = Str_Aadhaar_Card_path.substring(Str_Aadhaar_Card_path.lastIndexOf("/") + 1);
//                                Log.e("Str_Get_adhar_card_image_name SELECT_FROM_GALLERY :", "" + Str_Get_adhar_card_image_name);
//

//                                Log.e("Str_Aadhaar_Card_path SELECT_FROM_GALLERY :", "" + Str_Aadhaar_Card_path);
//                                Aadhaar_Card_Image = new File(Str_Aadhaar_Card_path);
//                                Log.e("Aadhaar_Card_Image File Image:", "" + Aadhaar_Card_Image);


                                if (!Str_Aadhaar_Card_path.equals("")) {

                                    Log.e("Str_Aadhaar_Card_path File Image availabe :", "" + Str_Aadhaar_Card_path);

                                    File file_aadhaar = new File(imageUri.getPath());
                                    long length = file_aadhaar.length();
                                    length = length / 1024;
                                    Log.e("file_aadhaar size in KB:", "" + length);

                                    String dir = getFilesDir().getAbsolutePath();
                                    Log.e("Aadhaar_Card filename dir:", "" + dir);

                                    String filename = imageUri.getLastPathSegment();
                                    Log.e("Aadhaar_Card filename :", "" + filename);
//                                    Str_Get_adhar_card_image_name = Str_Aadhaar_Card_path.substring(Str_Aadhaar_Card_path.lastIndexOf("/") + 1);
//                                    Log.e("Str_Get_adhar_card_image_name SELECT_FROM_GALLERY :", "" + Str_Get_adhar_card_image_name);
////


                                    Aadhaar_Card_Image = new File(Str_Aadhaar_Card_path);
                                    Log.e("Aadhaar_Card_Image File Image:", "" + Aadhaar_Card_Image);

                                    Iv_create_adhar_voter_image.setVisibility(View.VISIBLE);

//                                    UpdateMerchant_Aadhaar_Voter_Image_Fast();
                                } else {

                                    Log.e("Str_Aadhaar_Card_path File Image blank:", "" + Str_Aadhaar_Card_path);
                                    Aadhaar_Card_Image = new File("Str_Aadhaar_Card_path");
                                    Log.e("Aadhaar_Card_Image File Image:", "" + Aadhaar_Card_Image);
                                    Iv_create_adhar_voter_image.setVisibility(View.GONE);

                                }


                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("AadhaarCardImage")
                        .setImageFolderName("ZoffrBusiness")
                        .setCropScreenColor(Color.CYAN)
                        .setOnPermissionRefusedListener(new PickerBuilder.onPermissionRefusedListener() {
                            @Override
                            public void onPermissionRefused() {

                            }
                        })
                        .start();


            }
        });


        QuickTipDialog.show();
    }

    /*using Fast */
    private void UpdateMerchant_Aadhaar_Voter_Image_Fast() {
        Log.e("Update AadhaarCard/Voter Card DetailFast********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                + "Str_Aadhaar_Card_path :" + "" + Str_Aadhaar_Card_path + "\n"
                + "Str_Get_work_location_id :" + "" + Str_Get_work_location_areaid + "\n"
                + "Str_Get_office_address :" + "" + Str_Get_office_address + "\n"
                + "Str_Get_residential_address :" + "" + Str_Get_residential_address + "\n"
                + "Str_Get_work_location_cityid :" + "" + Str_Get_work_location_cityid + "\n"
                + "Str_Get_work_location_areaid :" + "" + Str_Get_work_location_areaid + "\n"
                + "Sh_pre_Merchant_mobile :" + "" + Sh_pre_Merchant_mobile + "\n");

        pDialog.setMessage("Updating Profile...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + UPDATE_MERCHANT_DETAIL)
                .addMultipartFile("adhar_card", Aadhaar_Card_Image)
                .addMultipartParameter("user_id", Sh_pre_User_ID)
                .addMultipartParameter("email", Sh_pre_Merchant_email)
                .addMultipartParameter("username", Sh_pre_Merchant_username)
                .addMultipartParameter("work_location", Str_Get_work_location_areaid)
                .addMultipartParameter("mobile", Sh_pre_Merchant_mobile)
                .addMultipartParameter("work_address", Str_Get_office_address)
                .addMultipartParameter("home_address", Str_Get_residential_address)
                .addMultipartParameter("work_location", Str_Get_work_location_cityid)
                .addMultipartParameter("work_sub_location", Str_Get_work_location_areaid)
                .setPriority(Priority.MEDIUM)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
//                        hideDialog();
                        Log.e("Response onProgress bytesUploaded:", "" + bytesUploaded);
                        Log.e("Response onProgress totalBytes:", "" + totalBytes);

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        hideDialog();
                        Log.e("Response upload:", "" + String.valueOf(response));
//                        merchantDetailFast();

                        try {
                            JSONObject jobjresponse = new JSONObject(String.valueOf(response));
                            Log.e("jobjresponse upload :", "" + jobjresponse);
                            Str_Get_Status = jobjresponse.getString("status");
                            Log.e("Str_Get_Status upload :", "" + Str_Get_Status);

                            Str_Get_message = jobjresponse.getString("message");
                            Log.e("Str_Get_message upload :", "" + Str_Get_message);

                            Str_Get_result = jobjresponse.getString("result");
                            Log.e("Str_Get_result upload :", "" + Str_Get_result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (Str_Get_Status.equalsIgnoreCase("1")) {


                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                    Get_MerchantDetail_Fast();

                                }

                            }, 2000);
                        } else {
                            Log.e("Str_Get_Status :", "0");
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
                        Log.e("Response error:", "" + error.toString());
                    }
                });


    }
    /*using Fast */


    private void HighestQualificationImagePicker() {
        QuickTipDialog = new Dialog(ProfileCreateActivity.this);
//                callFeeDialog = new Dialog(MainBuyerActivity.this);
        QuickTipDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        QuickTipDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        QuickTipDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        QuickTipDialog.setContentView(R.layout.activity_dialog_image_picker);
        final RelativeLayout RlCamera = (RelativeLayout) QuickTipDialog.findViewById(R.id.rl_inner_camera);
        final RelativeLayout RlGallery = (RelativeLayout) QuickTipDialog.findViewById(R.id.rl_inner_gallery);
        final TextView TvCancel = (TextView) QuickTipDialog.findViewById(R.id.tv_dialog_cancel);

        TvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickTipDialog.dismiss();
            }
        });

        RlCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(ProfileCreateActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Toast.makeText(EditProfileActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Iv_qualification.setImageURI(imageUri);

                                Glide.with(Iv_create_highest_qualification.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_create_highest_qualification) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_create_highest_qualification
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_create_highest_qualification);
                                            }
                                        });


                                Log.e("HighestQualification imageUri path SELECT_FROM_CAMERA :", "" + imageUri);
                                File f = new File(imageUri.getPath());
                                Str_HighestQualification_path = imageUri.getPath();
                                Log.e("Str_HighestQualification_path SELECT_FROM_CAMERA :", "" + Str_HighestQualification_path);

                                if (!Str_HighestQualification_path.equals("")) {

                                    Log.e("Str_HighestQualification_path File Image availabe :", "" + Str_HighestQualification_path);
                                    HighestQualification_Image = new File(Str_HighestQualification_path);
                                    Log.e("HighestQualification_Image File Image:", "" + HighestQualification_Image);
                                    Iv_create_highest_qualification.setVisibility(View.VISIBLE);
//                                    UpdateMerchant_HighestQualification_Image_Fast();

                                } else {

                                    Log.e("Str_HighestQualification_path File Image blank:", "" + Str_HighestQualification_path);
                                    HighestQualification_Image = new File("Str_HighestQualification_path");
                                    Log.e("HighestQualification_Image File Image:", "" + HighestQualification_Image);
                                    Iv_create_highest_qualification.setVisibility(View.GONE);

                                }
                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("HighestQualificationImage")
                        .setImageFolderName("ZoffrBusiness")
                        .withTimeStamp(false)
                        .setCropScreenColor(Color.CYAN)
                        .start();

            }
        });

        RlGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(ProfileCreateActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Iv_qualification.setImageURI(imageUri);
                                Glide.with(Iv_create_highest_qualification.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_create_highest_qualification) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_create_highest_qualification
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_create_highest_qualification);
                                            }
                                        });


                                Log.e("Iv_qualification imageUri path SELECT_FROM_GALLERY :", "" + imageUri);
                                File f = new File(imageUri.getPath());
                                Str_HighestQualification_path = imageUri.getPath();
                                Log.e("Str_HighestQualification_path SELECT_FROM_GALLERY :", "" + Str_HighestQualification_path);
//                                HighestQualification_Image = new File(Str_HighestQualification_path);
//                                Log.e("HighestQualification_Image File Image:", "" + HighestQualification_Image);

                                if (!Str_HighestQualification_path.equals("")) {

                                    Log.e("Str_HighestQualification_path File Image availabe :", "" + Str_HighestQualification_path);
                                    HighestQualification_Image = new File(Str_HighestQualification_path);
                                    Log.e("HighestQualification_Image File Image:", "" + HighestQualification_Image);
                                    Iv_create_highest_qualification.setVisibility(View.VISIBLE);
//                                    UpdateMerchant_HighestQualification_Image_Fast();

                                } else {

                                    Log.e("Str_HighestQualification_path File Image blank:", "" + Str_HighestQualification_path);
                                    HighestQualification_Image = new File("Str_HighestQualification_path");
                                    Log.e("HighestQualification_Image File Image:", "" + HighestQualification_Image);
                                    Iv_create_highest_qualification.setVisibility(View.GONE);

                                }

                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("HighestQualificationImage")
                        .setImageFolderName("ZoffrBusiness")
                        .setCropScreenColor(Color.CYAN)
                        .setOnPermissionRefusedListener(new PickerBuilder.onPermissionRefusedListener() {
                            @Override
                            public void onPermissionRefused() {

                            }
                        })
                        .start();


            }
        });


        QuickTipDialog.show();
    }

    /*using Fast */
    private void UpdateMerchant_HighestQualification_Image_Fast() {
        Log.e("Update HighestQualification DetailFast********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                + "Str_Get_work_location_id :" + "" + Str_Get_work_location_areaid + "\n"
                + "Str_HighestQualification_path :" + "" + Str_HighestQualification_path + "\n"
                + "Str_Get_office_address :" + "" + Str_Get_office_address + "\n"
                + "Str_Get_residential_address :" + "" + Str_Get_residential_address + "\n"
                + "Str_Get_work_location_cityid :" + "" + Str_Get_work_location_cityid + "\n"
                + "Str_Get_work_location_areaid :" + "" + Str_Get_work_location_areaid + "\n"
                + "Sh_pre_Merchant_mobile :" + "" + Sh_pre_Merchant_mobile + "\n");

        pDialog.setMessage("Updating Profile...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + UPDATE_MERCHANT_DETAIL)
                .addMultipartFile("qualification", HighestQualification_Image)
                .addMultipartParameter("user_id", Sh_pre_User_ID)
                .addMultipartParameter("email", Sh_pre_Merchant_email)
                .addMultipartParameter("username", Sh_pre_Merchant_username)
                .addMultipartParameter("work_location", Str_Get_work_location_areaid)
                .addMultipartParameter("mobile", Sh_pre_Merchant_mobile)
                .addMultipartParameter("work_address", Str_Get_office_address)
                .addMultipartParameter("home_address", Str_Get_residential_address)
                .addMultipartParameter("work_location", Str_Get_work_location_cityid)
                .addMultipartParameter("work_sub_location", Str_Get_work_location_areaid)
                .setPriority(Priority.MEDIUM)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
//                        hideDialog();
                        Log.e("Response onProgress bytesUploaded:", "" + bytesUploaded);
                        Log.e("Response onProgress totalBytes:", "" + totalBytes);

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        hideDialog();
                        Log.e("Response upload:", "" + String.valueOf(response));
//                        merchantDetailFast();

                        try {
                            JSONObject jobjresponse = new JSONObject(String.valueOf(response));
                            Log.e("jobjresponse upload :", "" + jobjresponse);
                            Str_Get_Status = jobjresponse.getString("status");
                            Log.e("Str_Get_Status upload :", "" + Str_Get_Status);

                            Str_Get_message = jobjresponse.getString("message");
                            Log.e("Str_Get_message upload :", "" + Str_Get_message);

                            if (Str_Get_Status.equalsIgnoreCase("1")) {

                                Str_Get_result = jobjresponse.getString("result");
                                Log.e("Str_Get_result upload :", "" + Str_Get_result);

                                Get_MerchantDetail_Fast();
//                                new Handler().postDelayed(new Runnable() {
//
//                                    @Override
//                                    public void run() {
//
//                                        Get_MerchantDetail_Fast();
//
//                                    }
//
//                                }, 2000);
                            } else {
                                Log.e("Str_Get_Status :", "0");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
                        Log.e("Response error:", "" + error.toString());
                    }
                });


    }
    /*using Fast */


    private void LatestAddressProofImagePicker() {
        QuickTipDialog = new Dialog(ProfileCreateActivity.this);
//                callFeeDialog = new Dialog(MainBuyerActivity.this);
        QuickTipDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        QuickTipDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        QuickTipDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        QuickTipDialog.setContentView(R.layout.activity_dialog_image_picker);
        final RelativeLayout RlCamera = (RelativeLayout) QuickTipDialog.findViewById(R.id.rl_inner_camera);
        final RelativeLayout RlGallery = (RelativeLayout) QuickTipDialog.findViewById(R.id.rl_inner_gallery);
        final TextView TvCancel = (TextView) QuickTipDialog.findViewById(R.id.tv_dialog_cancel);

        TvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickTipDialog.dismiss();
            }
        });

        RlCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(ProfileCreateActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Toast.makeText(EditProfileActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Iv_voter_id.setImageURI(imageUri);
                                Glide.with(Iv_create_profile_latest_address_proof.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_create_profile_latest_address_proof) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_create_profile_latest_address_proof
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_create_profile_latest_address_proof);
                                            }
                                        });


                                Log.e("AddressProof imageUri path SELECT_FROM_CAMERA :", "" + imageUri);
                                File f = new File(imageUri.getPath());
                                Str_Latest_Add_Proof_path = imageUri.getPath();
                                Log.e("AddressProof Path SELECT_FROM_CAMERA :", "" + Str_Latest_Add_Proof_path);


                                if (!Str_Latest_Add_Proof_path.equals("")) {

                                    Log.e("Str_Latest_Add_Proof_path File Image availabe :", "" + Str_Latest_Add_Proof_path);
                                    AddressProof_Image = new File(Str_Latest_Add_Proof_path);
//                                    ProdileImagefilebody = new FileBody(AddressProof_Image);
//                                    Log.e("ProdileImagefilebody image :", "" + ProdileImagefilebody.toString());
                                    Log.e("AddressProof_Image File Image:", "" + AddressProof_Image);
                                    Iv_create_profile_latest_address_proof.setVisibility(View.VISIBLE);
//                                    UpdateMerchant_LatestAddressProof_Image_Fast();

                                } else {

                                    Log.e("Str_Latest_Add_Proof_path File Image blank:", "" + Str_Latest_Add_Proof_path);
                                    AddressProof_Image = new File("Str_Latest_Add_Proof_path");
//                                    ProdileImagefilebody = new FileBody(AddressProof_Image);
//                                    Log.e("ProdileImagefilebody image :", "" + ProdileImagefilebody.toString());
                                    Log.e("AddressProof_Image File Image:", "" + AddressProof_Image);
                                    Iv_create_profile_latest_address_proof.setVisibility(View.GONE);

                                }

                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("AddressProofImage")
                        .setImageFolderName("ZoffrBusiness")
                        .withTimeStamp(false)
                        .setCropScreenColor(Color.CYAN)
                        .start();

            }
        });

        RlGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(ProfileCreateActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Iv_voter_id.setImageURI(imageUri);
                                Glide.with(Iv_create_profile_latest_address_proof.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_create_profile_latest_address_proof) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_create_profile_latest_address_proof
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_create_profile_latest_address_proof);
                                            }
                                        });

                                Log.e("AddressProof imageUri path SELECT_FROM_GALLERY :", "" + imageUri);
                                File f = new File(imageUri.getPath());
                                Str_Latest_Add_Proof_path = imageUri.getPath();
                                Log.e("Str_Latest_Add_Proof_path SELECT_FROM_GALLERY :", "" + Str_Latest_Add_Proof_path);
//                                AddressProof_Image = new File(Str_Latest_Add_Proof_path);
//                                Log.e("AddressProof_Image File Image:", "" + AddressProof_Image);


                                if (!Str_Latest_Add_Proof_path.equals("")) {

                                    Log.e("Str_Latest_Add_Proof_path File Image availabe :", "" + Str_Latest_Add_Proof_path);
                                    AddressProof_Image = new File(Str_Latest_Add_Proof_path);
//                                    ProdileImagefilebody = new FileBody(AddressProof_Image);
//                                    Log.e("ProdileImagefilebody image :", "" + ProdileImagefilebody.toString());
                                    Log.e("AddressProof_Image File Image:", "" + AddressProof_Image);

                                    Iv_create_profile_latest_address_proof.setVisibility(View.VISIBLE);
//                                    UpdateMerchant_LatestAddressProof_Image_Fast();

                                } else {

                                    Log.e("Str_Latest_Add_Proof_path File Image blank:", "" + Str_Latest_Add_Proof_path);
                                    AddressProof_Image = new File("Str_Latest_Add_Proof_path");
//                                    ProdileImagefilebody = new FileBody(AddressProof_Image);
//                                    Log.e("ProdileImagefilebody image :", "" + ProdileImagefilebody.toString());
                                    Log.e("AddressProof_Image File Image:", "" + AddressProof_Image);
                                    Iv_create_profile_latest_address_proof.setVisibility(View.GONE);

                                }

                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("AddressProofImage")
                        .setImageFolderName("ZoffrBusiness")
                        .setCropScreenColor(Color.CYAN)
                        .setOnPermissionRefusedListener(new PickerBuilder.onPermissionRefusedListener() {
                            @Override
                            public void onPermissionRefused() {

                            }
                        })
                        .start();


            }
        });


        QuickTipDialog.show();
    }

    /*using Fast */
    private void UpdateMerchant_LatestAddressProof_Image_Fast() {
        Log.e("Update latest_address_proof ********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                + "Str_Latest_Add_Proof_path :" + "" + Str_Latest_Add_Proof_path + "\n"
                + "Str_Get_work_location_id :" + "" + Str_Get_work_location_areaid + "\n"
                + "Str_Get_office_address :" + "" + Str_Get_office_address + "\n"
                + "Str_Get_residential_address :" + "" + Str_Get_residential_address + "\n"
                + "Str_Get_work_location_cityid :" + "" + Str_Get_work_location_cityid + "\n"
                + "Str_Get_work_location_areaid :" + "" + Str_Get_work_location_areaid + "\n"
                + "Sh_pre_Merchant_mobile :" + "" + Sh_pre_Merchant_mobile + "\n");

        pDialog.setMessage("Updating Profile...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + UPDATE_MERCHANT_DETAIL)
                .addMultipartFile("voter_id", AddressProof_Image)
                .addMultipartParameter("user_id", Sh_pre_User_ID)
                .addMultipartParameter("email", Sh_pre_Merchant_email)
                .addMultipartParameter("username", Sh_pre_Merchant_username)
                .addMultipartParameter("work_location", Str_Get_work_location_areaid)
                .addMultipartParameter("mobile", Sh_pre_Merchant_mobile)
                .addMultipartParameter("work_address", Str_Get_office_address)
                .addMultipartParameter("home_address", Str_Get_residential_address)
                .addMultipartParameter("work_location", Str_Get_work_location_cityid)
                .addMultipartParameter("work_sub_location", Str_Get_work_location_areaid)
                .setPriority(Priority.MEDIUM)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
//                        hideDialog();
                        Log.e("Response onProgress bytesUploaded:", "" + bytesUploaded);
                        Log.e("Response onProgress totalBytes:", "" + totalBytes);

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        hideDialog();
                        Log.e("Response upload:", "" + String.valueOf(response));
//                        merchantDetailFast();

                        try {
                            JSONObject jobjresponse = new JSONObject(String.valueOf(response));
                            Log.e("jobjresponse upload :", "" + jobjresponse);
                            Str_Get_Status = jobjresponse.getString("status");
                            Log.e("Str_Get_Status upload :", "" + Str_Get_Status);

                            Str_Get_message = jobjresponse.getString("message");
                            Log.e("Str_Get_message upload :", "" + Str_Get_message);

                            Str_Get_result = jobjresponse.getString("result");
                            Log.e("Str_Get_result upload :", "" + Str_Get_result);


                            if (Str_Get_Status.equalsIgnoreCase("1")) {
                                Get_MerchantDetail_Fast();

//                                new Handler().postDelayed(new Runnable() {
//
//                                    @Override
//                                    public void run() {
//
//                                        Get_MerchantDetail_Fast();
//
//                                    }
//
//                                }, 2000);
                            } else {
                                Log.e("Str_Get_Status :", "0");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
                        Log.e("Response error:", "" + error.toString());
                    }
                });


    }
    /*using Fast */


    /*using Fast */
    private void Update_Profile_Fast() {
        Log.e("Update Profile_Fast ********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                + "Str_Latest_Add_Proof_path :" + "" + Str_Latest_Add_Proof_path + "\n"
                + "Str_profileImage_path :" + "" + Str_profileImage_path + "\n"
                + "Str_Aadhaar_Card_path :" + "" + Str_Aadhaar_Card_path + "\n"
                + "Str_HighestQualification_path :" + "" + Str_HighestQualification_path + "\n"
                + "Str_Pan_Card_path :" + "" + Str_Pan_Card_path + "\n"
                + "Str_Get_work_location_id :" + "" + Str_Get_work_location_areaid + "\n"
                + "Str_Get_office_address :" + "" + Str_Get_office_address + "\n"
                + "Str_Get_residential_address :" + "" + Str_Get_residential_address + "\n"
                + "Str_Get_work_location_cityid :" + "" + Str_Get_work_location_cityid + "\n"
                + "Str_Get_work_location_areaid :" + "" + Str_Get_work_location_areaid + "\n"
                + "Sh_pre_Merchant_mobile :" + "" + Sh_pre_Merchant_mobile + "\n");

        pDialog.setMessage("Updating Profile...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + UPDATE_MERCHANT_DETAIL)
                .addMultipartFile("voter_id", AddressProof_Image)
                .addMultipartParameter("user_id", Sh_pre_User_ID)
                .addMultipartParameter("email", Sh_pre_Merchant_email)
                .addMultipartParameter("username", Sh_pre_Merchant_username)
                .addMultipartParameter("work_location", Str_Get_work_location_areaid)
                .addMultipartParameter("mobile", Sh_pre_Merchant_mobile)
                .addMultipartParameter("work_address", Str_Get_office_address)
                .addMultipartParameter("home_address", Str_Get_residential_address)
                .addMultipartParameter("work_location", Str_Get_work_location_cityid)
                .addMultipartParameter("work_sub_location", Str_Get_work_location_areaid)
                .setPriority(Priority.MEDIUM)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        // do anything with progress
//                        hideDialog();
                        Log.e("Response onProgress bytesUploaded:", "" + bytesUploaded);
                        Log.e("Response onProgress totalBytes:", "" + totalBytes);

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        hideDialog();
                        Log.e("Response upload:", "" + String.valueOf(response));
//                        merchantDetailFast();

                        try {
                            JSONObject jobjresponse = new JSONObject(String.valueOf(response));
                            Log.e("jobjresponse upload :", "" + jobjresponse);
                            Str_Get_Status = jobjresponse.getString("status");
                            Log.e("Str_Get_Status upload :", "" + Str_Get_Status);

                            Str_Get_message = jobjresponse.getString("message");
                            Log.e("Str_Get_message upload :", "" + Str_Get_message);

                            Str_Get_result = jobjresponse.getString("result");
                            Log.e("Str_Get_result upload :", "" + Str_Get_result);


                            if (Str_Get_Status.equalsIgnoreCase("1")) {
                                Get_MerchantDetail_Fast();

//                                new Handler().postDelayed(new Runnable() {
//
//                                    @Override
//                                    public void run() {
//
//                                        Get_MerchantDetail_Fast();
//
//                                    }
//
//                                }, 2000);
                            } else {
                                Log.e("Str_Get_Status :", "0");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
                        Log.e("Response error:", "" + error.toString());
                    }
                });


    }
    /*using Fast */

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
//            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show();
            ProfileImagePicker();
//            PanCardImagePicker();
//            AadhaarCardImagePicker();
//            HighestQualificationImagePicker();
//            VoterCardImagePicker();


        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.e(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.e(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT).show();

            cameraTask();

            Log.e("Data :", "" + data);
            Log.e("requestCode :", "" + requestCode);
            Log.e("resultCode :", "" + resultCode);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private class CreateProfileJsontask extends AsyncTask<String, Void, String> {

        boolean iserror = false;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            //  loginprogressbar.setVisibility(View.VISIBLE);
            Log.e("******* NOW CreateProfileJsontask WEB SERVICE IS RUNNING *******", "YES");
            pDialog.setMessage("Updating Profile...");
            showDialog();

            Log.e("onPreExecute Update data :", "\n"
                    + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                    + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                    + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                    + "Str_Get_work_location_id :" + "" + Str_Get_work_location_areaid + "\n"
                    + "Str_Get_office_address :" + "" + Str_Get_office_address + "\n"
                    + "Str_Get_residential_address :" + "" + Str_Get_residential_address + "\n"
                    + "Str_Get_work_location_cityid :" + "" + Str_Get_work_location_cityid + "\n"
                    + "Str_Get_work_location_areaid :" + "" + Str_Get_work_location_areaid + "\n"
                    + "Sh_pre_Merchant_mobile :" + "" + Sh_pre_Merchant_mobile + "\n"
                    + "Str_profileImage_path :" + "" + Str_profileImage_path + "\n"
                    + "Str_Aadhaar_Card_path :" + "" + Str_Aadhaar_Card_path + "\n"
                    + "Str_HighestQualification_path :" + "" + Str_HighestQualification_path + "\n"
                    + "Str_Pan_Card_path :" + "" + Str_Pan_Card_path + "\n"
                    + "Str_Latest_Add_Proof_path :" + "" + Str_Latest_Add_Proof_path + "\n");

            if (Str_profileImage_path.equalsIgnoreCase("")) {
                Str_profileImage_path = "ProfileImage";
            }


            if (Str_Aadhaar_Card_path.equalsIgnoreCase("")) {
                Str_Aadhaar_Card_path = "AadhaarVoterImage";
            }


            if (Str_HighestQualification_path.equalsIgnoreCase("")) {
                Str_HighestQualification_path = "QualificationImage";
            }


            if (Str_Pan_Card_path.equalsIgnoreCase("")) {
                Str_Pan_Card_path = "PanCardImage";
            }


            if (Str_Latest_Add_Proof_path.equalsIgnoreCase("")) {
                Str_Latest_Add_Proof_path = "LatestAddressProofImage";
            }

        }

        @Override
        protected String doInBackground(String... params) {
            Log.e("******* NOW doInBackground TASK IS RUNNING *******", "YES");


            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(HttpUrlPath.urlPathMain + UPDATE_MERCHANT_DETAIL + "user_id=" + Sh_pre_User_ID);
                Log.e("URL :", "" + HttpUrlPath.urlPathMain + UPDATE_MERCHANT_DETAIL + "user_id=" + Sh_pre_User_ID +
                        "&email=" + Sh_pre_Merchant_email +
                        "&username=" + Sh_pre_Merchant_username +
                        "&mobile=" + Sh_pre_Merchant_mobile +
                        "&home_address=" + Str_Get_residential_address +
                        "&work_address=" + Str_Get_office_address +
                        "&work_location=" + Str_Get_work_location_cityid +
                        "&work_sub_location=" + Str_Get_work_location_areaid +
                        "&voter_id=" + Str_Latest_Add_Proof_path +
                        "&adhar_card=" + Str_Aadhaar_Card_path +
                        "&qualification=" + Str_HighestQualification_path +
                        "&pan_card=" + Str_Pan_Card_path +
                        "&marchant_img=" + Str_profileImage_path);

                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("email", new StringBody(Sh_pre_Merchant_email));
                reqEntity.addPart("username", new StringBody(Sh_pre_Merchant_username));
                reqEntity.addPart("mobile", new StringBody(Sh_pre_Merchant_mobile));
                reqEntity.addPart("home_address", new StringBody(Str_Get_residential_address));
                reqEntity.addPart("work_address", new StringBody(Str_Get_office_address));
                reqEntity.addPart("work_location", new StringBody(Str_Get_work_location_cityid));
                reqEntity.addPart("work_sub_location", new StringBody(Str_Get_work_location_areaid));

                if (ProfileImage == null) {
                    Log.e("ProfileImage File is :", "NULL");
                    Str_profileImage_path = "ProfileImage";

                } else {
                    /******************* file for post the profile image to the server *******************/
                    ProdileImagefilebody = new FileBody(ProfileImage);
                    reqEntity.addPart("marchant_img", ProdileImagefilebody);
                    Log.e("ProdileImagefilebody image :", "" + ProdileImagefilebody.toString());
                    /******************* file for post the profile image to the server *******************/
                }

                if (Pan_Card_Image == null) {
                    Log.e("Pan_Card File is :", "NULL");
                    Str_Pan_Card_path = "PanCardImage";

                } else {
                    /******************* file for post the profile image to the server *******************/
                    PanCardImagefilebody = new FileBody(Pan_Card_Image);
                    reqEntity.addPart("pan_card", PanCardImagefilebody);
                    Log.e("PanCardImagefilebody image :", "" + PanCardImagefilebody.toString());
                    /******************* file for post the profile image to the server *******************/
                }

                if (Aadhaar_Card_Image == null) {
                    Log.e("Aadhaar_Card_Image File is :", "NULL");
                    Str_Aadhaar_Card_path = "AadhaarVoterImage";

                } else {
                    /******************* file for post the profile image to the server *******************/
                    AadhaarVoterImagefilebody = new FileBody(Aadhaar_Card_Image);
                    reqEntity.addPart("adhar_card", AadhaarVoterImagefilebody);
                    Log.e("AadhaarVoterImagefilebody image :", "" + AadhaarVoterImagefilebody.toString());
                    /******************* file for post the profile image to the server *******************/
                }

                if (HighestQualification_Image == null) {
                    Log.e("HighestQualification File is :", "NULL");
                    Str_HighestQualification_path = "QualificationImage";

                } else {
                    /******************* file for post the profile image to the server *******************/
                    HighestQualificationImagefilebody = new FileBody(HighestQualification_Image);
                    reqEntity.addPart("qualification", HighestQualificationImagefilebody);
                    Log.e("HighestQualification image :", "" + HighestQualificationImagefilebody.toString());
                    /******************* file for post the profile image to the server *******************/
                }

                if (AddressProof_Image == null) {
                    Log.e("HighestQualification File is :", "NULL");
                    Str_Latest_Add_Proof_path = "LatestAddressProofImage";

                } else {
                    /******************* file for post the profile image to the server *******************/
                    AddressProofImagefilebody = new FileBody(AddressProof_Image);
                    reqEntity.addPart("voter_id", AddressProofImagefilebody);
                    Log.e("AddressProofImage image :", "" + AddressProofImagefilebody.toString());
                    /******************* file for post the profile image to the server *******************/
                }

                /*List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("user_id", User_ID));
                Log.e("******* ID TO SERVER FOR MACHING*******", "" + User_ID);*/

                post.setEntity(reqEntity);
                HttpResponse response = client.execute(post);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                String sResponse;
                StringBuilder s = new StringBuilder();
                while ((sResponse = reader.readLine()) != null) {
                    s = s.append(sResponse);
                }

                String Jsondata = s.toString();
                Log.e("Jsondata : ", Jsondata);
                JSONObject parentObject = new JSONObject(Jsondata);
                Str_Get_Status = parentObject.getString("status");
                Log.e("*********** Str_Get_Status *********** : ", Str_Get_Status);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return Str_Get_Status;
        }

        @Override
        protected void onPostExecute(String result1) {
            // TODO Auto-generated method stub
            super.onPostExecute(result1);
//            RL_edit_profile_main_progress.setVisibility(View.GONE);
            hideDialog();
            if (!iserror) {


                if (Str_Get_Status.equalsIgnoreCase("1")) {
                    Log.e("onPostExecute Str_Get_Status :", "1");
                    Intent GoGetProfilescreen = new Intent(getApplicationContext(), ProfileGetActivity.class);
                    startActivity(GoGetProfilescreen);
                    finish();
                } else {

                    Intent GoGetProfilescreen = new Intent(getApplicationContext(), ProfileGetActivity.class);
                    startActivity(GoGetProfilescreen);
                    finish();


                    Log.e("onPostExecute Str_Get_Status :", "0");
                }


            } else {
                Log.e("onPostExecute Sub_cat_id size is Zero ", "ooppss");
            }
        }

    }

}
