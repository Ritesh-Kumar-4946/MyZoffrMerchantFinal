package in.myzoffrmerchants.main_activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.sdsmdg.tastytoast.TastyToast;

import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
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

import static android.util.Base64.encodeToString;
import static java.text.DateFormat.DEFAULT;

/**
 * Created by ritesh on 9/3/17.
 */
@SuppressWarnings("deprecation")
public class ProfileEditActivity extends ActivityManagePermission implements View.OnClickListener,
        EasyPermissions.PermissionCallbacks {


    /*https://github.com/amitshekhariitbhu/Fast-Android-Networking#uploading-a-file-to-server*/
    /*https://github.com/amitshekhariitbhu/Fast-Android-Networking#uploading-a-file-to-server*/
    /*https://github.com/amitshekhariitbhu/Fast-Android-Networking#uploading-a-file-to-server*/
    /*https://github.com/amitshekhariitbhu/Fast-Android-Networking#uploading-a-file-to-server*/


    private static final String[] USER_TYPE = {"Mumbai", "Navi Mumbai", "Thane", "Pune"};
    private static final String[] AREA_TYPE = {"NO AREA"};
    private static final String GET_MERCHANT_DETAIL = "get_profile?";
    private static final String GET_CITY_LIST_DETAIL = "city_list";
    private static final String GET_AREA_LIST_DETAIL = "get_sub_city";
    private static final String UPDATE_MERCHANT_DETAIL = "user_update?";
    private static final String CHANGE_PASSWORD = "update_password?";
    /*
        @BindView(R.id.edt_et_edit_profile_password)
        EditText EDT_et_edit_profile_password;

        @BindView(R.id.edt_et_edit_profile_re_password)
        EditText EDT_et_edit_profile_re_password;*/
    private static final int RC_CAMERA_PERM = 123;
    private static final String TAG = "MainCategoryActivity";
    @BindView(R.id.rl_edit_profile_btn)
    RelativeLayout Rl_edit_profile_btn;
    @BindView(R.id.civ_edit_profile_image)
    CircleImageView Civ_edit_profile_image;
    @BindView(R.id.tv_edit_merchant_profile_name)
    TextView Tv_edit_merchant_profile_name;
    @BindView(R.id.tv_edit_merchant_email)
    TextView Tv_edit_merchant_email;
    @BindView(R.id.tv_edit_merchant_mobile)
    TextView Tv_edit_merchant_mobile;
    @BindView(R.id.tv_edit_merchant_working_place)
    TextView Tv_edit_merchant_working_place;
    @BindView(R.id.edt_et_edit_profile_phone_number)
    EditText EDT_et_edit_profile_phone_number;
    @BindView(R.id.cv_edit_profile)
    CardView Cv_edit_profile;
    @BindView(R.id.cv_edit_profile_clicked)
    CardView Cv_edit_profile_clicked;
    @BindView(R.id.cv_et_edit_profile_change_password)
    CardView Cv_et_edit_profile_change_password;
    @BindView(R.id.mtspn_places)
    MaterialSpinner Mtspn_places;
    @BindView(R.id.mtspn_area)
    MaterialSpinner Mtspn_area;

    //    @BindView(R.id.iv_demo)
//    ImageView Iv_demo;
    @BindView(R.id.toolbar_edit_profile)
    Toolbar Toolbar_edit_profile;
    @BindView(R.id.btn_browse_pan_card)
    Button Btn_browse_pan_card;
    @BindView(R.id.btn_browse_adhar_card)
    Button Btn_browse_adhar_card;
    @BindView(R.id.btn_browse_qualification)
    Button Btn_browse_qualification;
    @BindView(R.id.btn_browse_voter_id)
    Button Btn_browse_voter_id;
    @BindView(R.id.iv_pan_card)
    ImageView Iv_pan_card;
    @BindView(R.id.iv_adhar_card)
    ImageView Iv_adhar_card;
    @BindView(R.id.iv_qualification)
    ImageView Iv_qualification;
    @BindView(R.id.iv_voter_id)
    ImageView Iv_voter_id;
    @BindView(R.id.pb_pan_card)
    ProgressBar Pb_pan_card;
    String
            Sh_pre_User_ID = "",
            Sh_pre_Merchant_email = "",
            Sh_pre_Merchant_username = "",
            Sh_pre_Merchant_mobile = "",
            Str_Get_User_ID = "",
            Str_Get_message = "",
            Str_Get_result = "",
            Str_Get_user_image = "",
            Str_Set_user_image = "",
            Str_Get_user_name = "",
            Str_Set_user_name = "",
            Str_Get_phone_number = "",
            Str_Get_emailID = "",
            Str_Get_profile_image = "",
            Str_Get_profile_image_name = "",
            Str_Get_voter_id_image = "",
            Str_Get_voter_id_image_name = "",
            Str_Get_adhar_card_image = "",
            Str_Get_adhar_card_image_name = "",
            Str_Get_HighestQualification_image = "",
            Str_Get_HighestQualification_image_name = "",
            Str_Get_pan_card_image = "",
            Str_Get_pan_card_image_name = "",
            Str_Set_phone_number = "",
            StrAll_Get_work_location_name = "",
            Str_Get_work_location_cityname = "",
            Str_Get_work_location_areaname = "",
            Str_Get_work_location_cityid = "",
            Str_Get_work_location_areaid = "",
            Str_Get_work_Area_name = "",
            Str_Get_work_Area_id = "",
            Str_Set_Old_password = "",
            Str_Set_New_password = "",
            Str_Set_Confirm_new_password = "",
            Str_profileImage_path = "",
            Str_Pan_Card_path = "",
            Str_Aadhaar_Card_path = "",
            Str_HighestQualification_path = "",
            Str_Voter_Card_path = "",
            result = "",
            Str_Get_update_password_result = "",
            StrGet_City_list = "",
            Str_Get_Status = "",
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
            Str_Get_Area_List_single_name = "",
            Str_Get_City_List_single_Selected_name = "Please Select Your City",
            Str_Get_Area_List_single_Selected_name = "Please Select Your Area";
    ArrayList<String> City_typelist = new ArrayList<String>();
    ArrayList<String> Area_typelist = new ArrayList<String>();
    Dialog QuickTipDialog;
    Dialog ChangePassword;
    File ProfileImage, Pan_Card_Image, Aadhaar_Card_Image, HighestQualification_Image, VoterCard_Image;
    FileBody ProdileImagefilebody;
    private ProgressDialog pDialog;
    private Rect rect;    // Variable rect to hold the bounds of the view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        ButterKnife.bind(this);

        AndroidNetworking.initialize(getApplicationContext());
//        OkHttpClient okHttpClient = new OkHttpClient() .newBuilder()
//                .addNetworkInterceptor(new StethoInterceptor())
//                .build();
//        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);


        setSupportActionBar(Toolbar_edit_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        ((ActionBarActivity) getApplicationContext()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((ActionBarActivity) getApplicationContext()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        Sh_pre_User_ID = Appconstant.sh.getString("id", null);
        Sh_pre_Merchant_email = Appconstant.sh.getString("email", null);
        Sh_pre_Merchant_username = Appconstant.sh.getString("username", null);
        Sh_pre_Merchant_mobile = Appconstant.sh.getString("mobile", null);
        Str_Get_work_location_cityid = Appconstant.sh.getString("location_cityid", null);
        Str_Get_work_location_areaid = Appconstant.sh.getString("location_areaid", null);
        Str_Get_work_location_cityname = Appconstant.sh.getString("location_cityname", null);
        Str_Get_work_location_areaname = Appconstant.sh.getString("location_areaname", null);

        Log.e("Merchant_ID from SharedPref :", "" + Sh_pre_User_ID);
        Log.e("Sh_pre_Merchant_email from SharedPref :", "" + Sh_pre_Merchant_email);
        Log.e("Sh_pre_Merchant_username from SharedPref :", "" + Sh_pre_Merchant_username);
        Log.e("Sh_pre_Merchant_mobile from SharedPref :", "" + Sh_pre_Merchant_mobile);
        Log.e("Str_Get_work_location_cityid from SharedPref :", "" + Str_Get_work_location_cityid);
        Log.e("Str_Get_work_location_areaid from SharedPref :", "" + Str_Get_work_location_areaid);
        Log.e("Str_Get_work_location_cityname from SharedPref :", "" + Str_Get_work_location_cityname);
        Log.e("Str_Get_work_location_areaname from SharedPref :", "" + Str_Get_work_location_areaname);


//        merchantDetail();
        Get_MerchantDetail_Fast();

//        Get_City_List();
//        Get_City_List_Fast();


        Cv_edit_profile_clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Update Click :", "OK");
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

                UpdateMerchant_work_location_Fast();

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


        editprofileActivitywidgetOnclickLisner();


        Cv_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

//                EditDataImageCheck();

                Log.e(" Update Fields data :", "\n"
                        + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                        + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                        + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                        + "Str_profileImage_path :" + "" + Str_profileImage_path + "\n"
                        + "Str_Pan_Card_path :" + "" + Str_Pan_Card_path + "\n"
                        + "Str_Aadhaar_Card_path :" + "" + Str_Aadhaar_Card_path + "\n"
                        + "Str_HighestQualification_path :" + "" + Str_HighestQualification_path + "\n"
                        + "Str_Latest_Add_Proof_path :" + "" + Str_Voter_Card_path + "\n"
                        + "Str_Get_work_location_cityid :" + "" + Str_Get_work_location_cityid + "\n"
                        + "Str_Get_work_location_cityname :" + "" + Str_Get_work_location_cityname + "\n"
                        + "Str_Get_work_location_areaid :" + "" + Str_Get_work_location_areaid + "\n"
                        + "Str_Get_work_location_areaname :" + "" + Str_Get_work_location_areaname + "\n"
                        + "Sh_pre_Merchant_mobile :" + "" + Sh_pre_Merchant_mobile + "\n");


                UpdateMerchant_work_location_Fast();

            }
        });


        Cv_et_edit_profile_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Cv_et_edit_profile_change_password Click :", "OK");
//                ChangePassword();
                Intent GoChangePasswordScreen = new Intent(getApplicationContext(), ChangePasaswordActivity.class);
                startActivity(GoChangePasswordScreen);
                finish();


            }
        });

        Rl_edit_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                Toast.makeText(ProfileEditActivity.this, "Image Picker", Toast.LENGTH_SHORT).show();


                cameraTask();
//                ProfileImagePicker();
            }
        });


    }


    private void ChangePassword() {

        Log.e("Under ChangePassword Method :", "OK");


        ChangePassword = new Dialog(getApplicationContext());
//                callFeeDialog = new Dialog(MainBuyerActivity.this);
        ChangePassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ChangePassword.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ChangePassword.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ChangePassword.setContentView(R.layout.activity_dialog_change_password);
        final EditText Edt_user_old_password = (EditText) ChangePassword.findViewById(R.id.edt_user_old_password);
        final EditText Edt_user_new_password = (EditText) ChangePassword.findViewById(R.id.edt_user_new_password_one);
        final EditText Edt_user_confirm_password = (EditText) ChangePassword.findViewById(R.id.edt_user_confirm_new_password_one);
//        final TextInputLayout Til_user_old_password = (TextInputLayout) ChangePassword.findViewById(R.id.til_user_old_password);
//        final TextInputLayout Til_user_new_password_one = (TextInputLayout) ChangePassword.findViewById(R.id.til_user_new_password_one);
//        final TextInputLayout Til_user_confirm_new_password_one = (TextInputLayout) ChangePassword.findViewById(R.id.til_user_confirm_new_password_one);

        final TextView Tv_dialog_password_cancel = (TextView) ChangePassword.findViewById(R.id.tv_dialog_password_cancel);
        final TextView Tv_dialog_password_ok = (TextView) ChangePassword.findViewById(R.id.tv_dialog_password_ok);

        Tv_dialog_password_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Rl_cancel_btn.setBackground(R.drawable.dialog_change_password_btn_bg);
                ChangePassword.dismiss();
            }
        });

        Tv_dialog_password_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ChangePassword.dismiss();
                boolean iserror = false;

                Str_Set_Old_password = Edt_user_old_password.getText().toString().trim();
                Str_Set_New_password = Edt_user_new_password.getText().toString().trim();
                Str_Set_Confirm_new_password = Edt_user_confirm_password.getText().toString().trim();


                Log.e(" Change Password Fields data :", "\n"
                        + "Str_Set_Old_password :" + "" + Str_Set_Old_password + "\n"
                        + "Str_Set_New_password :" + "" + Str_Set_New_password + "\n"
                        + "Str_Set_Confirm_new_password :" + "" + Str_Set_Confirm_new_password + "\n");


                if (Str_Set_Old_password.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/


                    Toast.makeText(getApplicationContext(), "Please enter your Old Password",
                            Toast.LENGTH_SHORT).show();


                } else if (Str_Set_New_password.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);


                    Toast.makeText(getApplicationContext(),
                            "Please enter your Password", Toast.LENGTH_SHORT).show();


                } else if (Str_Set_New_password.length() < 5) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);


                    Toast.makeText(getApplicationContext(), "Please enter at least 5 character in password.",
                            Toast.LENGTH_SHORT).show();


                } else if (Str_Set_New_password.contains(" ")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);


                    Toast.makeText(getApplicationContext(), "Space is not allowed in password.",
                            Toast.LENGTH_SHORT).show();


                } else if (!Str_Set_Confirm_new_password.equals(Str_Set_New_password)) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);


                    Toast.makeText(getApplicationContext(),
                            "oopsss....\n Password not Match Please try again", Toast.LENGTH_SHORT).show();


                }
                if (!iserror) {

                    Log.e("No Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                        Toast.makeText(getApplicationContext(), "You are valid User", Toast.LENGTH_SHORT).show();
                    Log.e("No Error :", "Password valid By User");


//                    Change_Password_Fast();


                }


            }
        });


    }


    private void Change_Password_Fast() {
        final String user_old_password = Str_Set_Old_password;
        final String user_new_password = Str_Set_New_password;

        /*http://whatsapphindistatus.com/ZOF/webservice/update_password?user_id=2&old_password=123456&new_password=456
*/
        Log.e("Volley Update Password Data :", ""
                + "\n" + "user_old_password" + "" + user_old_password
                + "\n" + "user_old_password" + "" + user_new_password
        );

        // Tag used to cancel the request
        String tag_string_req = "req_registration";
        pDialog.setMessage("Updaing password.....");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlPath.urlPathMain + CHANGE_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Response :", "" + response);
                        try {
                            JSONObject jobjresponse = new JSONObject(response);
                            Log.e("Update password jobjresponse :", "" + jobjresponse);


                            Str_Get_update_password_result = jobjresponse.getString("result");
                            Log.e("Str_Get_update_password_result is:", "" + Str_Get_update_password_result);


                            if (Str_Get_update_password_result.equalsIgnoreCase("successfully")) {
                                Log.e("StrGet_status is:", "1");


                                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();

                            } else {
                                Log.e("Str_Get_update_password_result is:", "" + Str_Get_update_password_result);

//                                Toast.makeText(getApplicationContext(), Str_Get_update_password_result, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("Update Pass response :", "" + response);

                        if (Str_Get_update_password_result.equalsIgnoreCase("successfully")) {
                            Log.e("Str_Get_update_password_result is:", "successfully");
                            TastyToast.makeText(getApplicationContext(),
                                    "Password Updated",
                                    TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                            ChangePassword.dismiss();
                        } else {
                            Log.e("StrGet_status is:", "Updating Password Error");
                            TastyToast.makeText(getApplicationContext(),
                                    "Old Password not match",
                                    TastyToast.LENGTH_LONG, TastyToast.WARNING);
                        }
                        hideDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        Log.e("Error Response :", "" + error.toString());
                        TastyToast.makeText(getApplicationContext(),
                                "Server Error Please Contact Service Provider",
                                TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        ChangePassword.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", Sh_pre_User_ID);
                params.put("old_password", user_old_password);
                params.put("new_password", user_new_password);
                return params;
            }

        };

        // Adding request to request queue

        /*http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/*/
//        AppControllerVolley.getInstance().addToRequestQueue(stringRequest, tag_string_req);
        /*http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/*/

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void ProfileImagePicker() {
        QuickTipDialog = new Dialog(ProfileEditActivity.this);
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

                new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Civ_edit_profile_image.setImageURI(imageUri);

                                Glide.with(Civ_edit_profile_image.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Civ_edit_profile_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Civ_edit_profile_image
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Civ_edit_profile_image);
                                            }
                                        });


                                Log.e("imageUri path SELECT_FROM_CAMERA :", "" + imageUri);
                                File f = new File(imageUri.getPath());
                                Str_profileImage_path = imageUri.getPath();

                                Log.e("Str_profileImage_path SELECT_FROM_CAMERA :", "" + Str_profileImage_path);
                                if (!Str_profileImage_path.equals("")) {

                                    Log.e("Str_profileImage_path File Image availabe :", "" + Str_profileImage_path);
                                    ProfileImage = new File(Str_profileImage_path);
                                    Log.e("profile File Image:", "" + ProfileImage);

                                    Update_MerchantProfile_Image_Fast();

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

                new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Civ_edit_profile_image.setImageURI(imageUri);
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
                                Glide.with(Civ_edit_profile_image.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Civ_edit_profile_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Civ_edit_profile_image
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Civ_edit_profile_image);
                                            }
                                        });

                                Log.e("imageUri path SELECT_FROM_GALLERY :", "" + imageUri);
                                File f = new File(imageUri.getPath());
                                Str_profileImage_path = imageUri.getPath();
                                Log.e("Str_profileImage_path SELECT_FROM_GALLERY :", "" + Str_profileImage_path);

                                if (!Str_profileImage_path.equals("")) {

                                    Log.e("Str_profileImage_path File Image availabe :", "" + Str_profileImage_path);
                                    ProfileImage = new File(Str_profileImage_path);
                                    Log.e("profile File Image:", "" + ProfileImage);

                                    Update_MerchantProfile_Image_Fast();

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
    private void Update_MerchantProfile_Image_Fast() {
        Log.e("Update_MerchantProfile_Image_Fast********************* :", "*************************");
        Str_Get_work_location_areaid = Appconstant.sh.getString("location_areaid", null);
        Log.e("Update_MerchantProfile_Image_Fast work_location_id from SharedPref :", "" + Str_Get_work_location_areaid);

        Log.e("Fast Update_MerchantProfile_Image_Fast Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                + "Str_profileImage_path :" + "" + Str_profileImage_path + "\n"
                + "work_location_id :" + "" + Str_Get_work_location_areaid + "\n"
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
        mStringPart.put("work_location", Str_Get_work_location_areaid);


        CustomMultipartRequest mCustomRequest = new CustomMultipartRequest(Request.Method.POST,
                ProfileEditActivity.this, HttpUrlPath.urlPathMain + UPDATE_MERCHANT_DETAIL,
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
        QuickTipDialog = new Dialog(ProfileEditActivity.this);
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

                new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Iv_pan_card.setImageURI(imageUri);


                                Glide.with(Iv_pan_card.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_pan_card) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_pan_card
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_pan_card);
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
                                    Iv_pan_card.setVisibility(View.VISIBLE);
                                    UpdateMerchant_Pan_Card_Image_Fast();

                                } else {

                                    Log.e("Pan_Card_Image File Image blank:", "" + Pan_Card_Image);
                                    Pan_Card_Image = new File("Pan_Card_Image");
                                    Log.e("Pan_Card_Image File Image:", "" + Pan_Card_Image);
                                    Iv_pan_card.setVisibility(View.GONE);

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

                new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Iv_pan_card.setImageURI(imageUri);
                                Glide.with(Iv_pan_card.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_pan_card) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_pan_card
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_pan_card);
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
                                    Iv_pan_card.setVisibility(View.VISIBLE);
                                    UpdateMerchant_Pan_Card_Image_Fast();

                                } else {

                                    Log.e("Pan_Card_Image File Image blank:", "" + Pan_Card_Image);
                                    Pan_Card_Image = new File("Pan_Card_Image");
                                    Log.e("Pan_Card_Image File Image:", "" + Pan_Card_Image);
                                    Iv_pan_card.setVisibility(View.GONE);

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
        Log.e("UpdateMerchant_Pan_Card_Image_Fast********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                + "Str_Pan_Card_path :" + "" + Str_Pan_Card_path + "\n"
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


    private void AadhaarCardImagePicker() {
        QuickTipDialog = new Dialog(ProfileEditActivity.this);
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

                new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Iv_adhar_card.setImageURI(imageUri);


                                Glide.with(Iv_adhar_card.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_adhar_card) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_adhar_card
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_adhar_card);
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
                                    Iv_adhar_card.setVisibility(View.VISIBLE);

                                    UpdateMerchant_Aadhaar_Card_Image_Fast();
                                } else {

                                    Log.e("Str_Aadhaar_Card_path File Image blank:", "" + Str_Aadhaar_Card_path);
                                    Aadhaar_Card_Image = new File("Str_Aadhaar_Card_path");
                                    Log.e("Aadhaar_Card_Image File Image:", "" + Aadhaar_Card_Image);
                                    Iv_adhar_card.setVisibility(View.GONE);

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

                new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Iv_adhar_card.setImageURI(imageUri);

                                Glide.with(Iv_adhar_card.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_adhar_card) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_adhar_card
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_adhar_card);
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

                                    Iv_adhar_card.setVisibility(View.VISIBLE);

                                    UpdateMerchant_Aadhaar_Card_Image_Fast();
                                } else {

                                    Log.e("Str_Aadhaar_Card_path File Image blank:", "" + Str_Aadhaar_Card_path);
                                    Aadhaar_Card_Image = new File("Str_Aadhaar_Card_path");
                                    Log.e("Aadhaar_Card_Image File Image:", "" + Aadhaar_Card_Image);
                                    Iv_adhar_card.setVisibility(View.GONE);

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
    private void UpdateMerchant_Aadhaar_Card_Image_Fast() {
        Log.e("UpdateMerchantDetailFast********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                + "Str_Aadhaar_Card_path :" + "" + Str_Aadhaar_Card_path + "\n"
                + "Str_Get_work_location_id :" + "" + Str_Get_work_location_areaid + "\n"
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
        QuickTipDialog = new Dialog(ProfileEditActivity.this);
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

                new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Iv_qualification.setImageURI(imageUri);

                                Glide.with(Iv_qualification.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_qualification) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_qualification
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_qualification);
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
                                    Iv_qualification.setVisibility(View.VISIBLE);
                                    UpdateMerchant_HighestQualification_Image_Fast();

                                } else {

                                    Log.e("Str_HighestQualification_path File Image blank:", "" + Str_HighestQualification_path);
                                    HighestQualification_Image = new File("Str_HighestQualification_path");
                                    Log.e("HighestQualification_Image File Image:", "" + HighestQualification_Image);
                                    Iv_qualification.setVisibility(View.GONE);

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

                new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Iv_qualification.setImageURI(imageUri);
                                Glide.with(Iv_qualification.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_qualification) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_qualification
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_qualification);
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
                                    Iv_qualification.setVisibility(View.VISIBLE);
                                    UpdateMerchant_HighestQualification_Image_Fast();

                                } else {

                                    Log.e("Str_HighestQualification_path File Image blank:", "" + Str_HighestQualification_path);
                                    HighestQualification_Image = new File("Str_HighestQualification_path");
                                    Log.e("HighestQualification_Image File Image:", "" + HighestQualification_Image);
                                    Iv_qualification.setVisibility(View.GONE);

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
        Log.e("UpdateMerchantDetailFast********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                + "Str_profileImage_path :" + "" + Str_profileImage_path + "\n"
                + "Str_Get_work_location_id :" + "" + Str_Get_work_location_areaid + "\n"
                + "Str_HighestQualification_path :" + "" + Str_HighestQualification_path + "\n"
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


    private void VoterCardImagePicker() {
        QuickTipDialog = new Dialog(ProfileEditActivity.this);
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

                new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Iv_voter_id.setImageURI(imageUri);
                                Glide.with(Iv_voter_id.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_voter_id) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_voter_id
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_voter_id);
                                            }
                                        });


                                Log.e("VoterCard imageUri path SELECT_FROM_CAMERA :", "" + imageUri);
                                File f = new File(imageUri.getPath());
                                Str_Voter_Card_path = imageUri.getPath();
                                Log.e("Str_VoterCard_path SELECT_FROM_CAMERA :", "" + Str_Voter_Card_path);


                                if (!Str_Voter_Card_path.equals("")) {

                                    Log.e("Str_Latest_Add_Proof_path File Image availabe :", "" + Str_Voter_Card_path);
                                    VoterCard_Image = new File(Str_Voter_Card_path);
//                                    ProdileImagefilebody = new FileBody(VoterCard_Image);
//                                    Log.e("ProdileImagefilebody image :", "" + ProdileImagefilebody.toString());
                                    Log.e("VoterCard_Image File Image:", "" + VoterCard_Image);
                                    Iv_voter_id.setVisibility(View.VISIBLE);
                                    UpdateMerchant_VoterCard_Image_Fast();

                                } else {

                                    Log.e("Str_Latest_Add_Proof_path File Image blank:", "" + Str_Voter_Card_path);
                                    VoterCard_Image = new File("Str_Latest_Add_Proof_path");
//                                    ProdileImagefilebody = new FileBody(VoterCard_Image);
//                                    Log.e("ProdileImagefilebody image :", "" + ProdileImagefilebody.toString());
                                    Log.e("VoterCard_Image File Image:", "" + VoterCard_Image);
                                    Iv_voter_id.setVisibility(View.GONE);

                                }

                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("VoterCardImage")
                        .setImageFolderName("ZoffrBusiness")
                        .withTimeStamp(false)
                        .setCropScreenColor(Color.CYAN)
                        .start();

            }
        });

        RlGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri) {
//                                Iv_voter_id.setImageURI(imageUri);
                                Glide.with(Iv_voter_id.getContext())
                                        .load(imageUri)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_voter_id) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_voter_id
                                                        .getContext())
                                                        .load(imageUri)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_voter_id);
                                            }
                                        });

                                Log.e("Voter_Card imageUri path SELECT_FROM_GALLERY :", "" + imageUri);
                                File f = new File(imageUri.getPath());
                                Str_Voter_Card_path = imageUri.getPath();
                                Log.e("Str_Latest_Add_Proof_path SELECT_FROM_GALLERY :", "" + Str_Voter_Card_path);
//                                VoterCard_Image = new File(Str_Latest_Add_Proof_path);
//                                Log.e("VoterCard_Image File Image:", "" + VoterCard_Image);


                                if (!Str_Voter_Card_path.equals("")) {

                                    Log.e("Str_Latest_Add_Proof_path File Image availabe :", "" + Str_Voter_Card_path);
                                    VoterCard_Image = new File(Str_Voter_Card_path);
//                                    ProdileImagefilebody = new FileBody(VoterCard_Image);
//                                    Log.e("ProdileImagefilebody image :", "" + ProdileImagefilebody.toString());
                                    Log.e("VoterCard_Image File Image:", "" + VoterCard_Image);

                                    Iv_voter_id.setVisibility(View.VISIBLE);
                                    UpdateMerchant_VoterCard_Image_Fast();

                                } else {

                                    Log.e("Str_Latest_Add_Proof_path File Image blank:", "" + Str_Voter_Card_path);
                                    VoterCard_Image = new File("Str_Latest_Add_Proof_path");
//                                    ProdileImagefilebody = new FileBody(VoterCard_Image);
//                                    Log.e("ProdileImagefilebody image :", "" + ProdileImagefilebody.toString());
                                    Log.e("VoterCard_Image File Image:", "" + VoterCard_Image);
                                    Iv_voter_id.setVisibility(View.GONE);

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
    private void UpdateMerchant_VoterCard_Image_Fast() {
        Log.e("UpdateMerchantDetailFast********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                + "Str_Latest_Add_Proof_path :" + "" + Str_Voter_Card_path + "\n"
                + "Str_Get_work_location_id :" + "" + Str_Get_work_location_areaid + "\n"
                + "Sh_pre_Merchant_mobile :" + "" + Sh_pre_Merchant_mobile + "\n");

        pDialog.setMessage("Updating Profile...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + UPDATE_MERCHANT_DETAIL)
                .addMultipartFile("voter_id", VoterCard_Image)
                .addMultipartParameter("user_id", Sh_pre_User_ID)
                .addMultipartParameter("email", Sh_pre_Merchant_email)
                .addMultipartParameter("username", Sh_pre_Merchant_username)
                .addMultipartParameter("work_location", Str_Get_work_location_areaid)
                .addMultipartParameter("mobile", Sh_pre_Merchant_mobile)
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


    private void editprofileActivitywidgetOnclickLisner() {
        Btn_browse_pan_card.setOnClickListener(this);
        Btn_browse_adhar_card.setOnClickListener(this);
        Btn_browse_qualification.setOnClickListener(this);
        Btn_browse_voter_id.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_browse_pan_card:
                // do your code
//                Toast.makeText(ProfileEditActivity.this, "PanCard Clicked", Toast.LENGTH_SHORT).show();
                PanCardImagePicker();

                break;

            case R.id.btn_browse_adhar_card:
                // do your code
//                Toast.makeText(ProfileEditActivity.this, "PanCard Clicked", Toast.LENGTH_SHORT).show();
                AadhaarCardImagePicker();

                break;

            case R.id.btn_browse_qualification:
                // do your code
//                Toast.makeText(ProfileEditActivity.this, "PanCard Clicked", Toast.LENGTH_SHORT).show();
                HighestQualificationImagePicker();

                break;

            case R.id.btn_browse_voter_id:
                // do your code
//                Toast.makeText(ProfileEditActivity.this, "PanCard Clicked", Toast.LENGTH_SHORT).show();
                VoterCardImagePicker();

                break;

            default:
                break;

        }
    }


    /*using volley */
    private void merchantDetail() {
        final String merchantID = Sh_pre_User_ID;

        Log.e("merchantID :", "" + merchantID);
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog.setMessage("Logging in ...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                HttpUrlPath.urlPathMain + GET_MERCHANT_DETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(MainVolleyActivity.this,response,Toast.LENGTH_LONG).show();
                        hideDialog();
                        Log.e("Response :", "" + response);
                        try {
                            JSONObject jobjresponse = new JSONObject(response);
                            Log.e("jobjresponse :", "" + jobjresponse);
                            Str_Get_Status = jobjresponse.getString("status");
                            Log.e("Str_Get_Status :", "" + Str_Get_Status);

                            Str_Get_message = jobjresponse.getString("message");
                            Log.e("Str_Get_message :", "" + Str_Get_message);

                            Str_Get_result = jobjresponse.getString("result");
                            Log.e("Str_Get_result :", "" + Str_Get_result);

                            JSONObject jobjresult = jobjresponse.getJSONObject("result");
                            Log.e("jobjresult :", "" + jobjresult.toString());

                            if (Str_Get_Status.equalsIgnoreCase("1")) {
                                Log.e("StrGet_Status is:", "1");

//                                tv_status.setText(StrGet_Status);

                                Str_Get_User_ID = jobjresult.getString("id");
                                Log.e("Str_Get_User_ID is:", "" + Str_Get_User_ID);

                                Str_Get_user_name = jobjresult.getString("username");
                                Log.e("Str_Get_user_name is:", "" + Str_Get_user_name);

                                Str_Get_phone_number = jobjresult.getString("mobile");
                                Log.e("Str_Get_phone_number is:", "" + Str_Get_phone_number);

                                Str_Get_emailID = jobjresult.getString("email");
                                Log.e("Str_Get_emailID is:", "" + Str_Get_emailID);

                                Str_Get_work_location_areaname = jobjresult.getString("work_location");
                                Log.e("Str_Get_work_location is:", "" + Str_Get_work_location_areaname);

                                Str_Get_profile_image = jobjresult.getString("image");
                                Log.e("Str_Get_profile_image is:", "" + Str_Get_profile_image);

                                Str_Get_voter_id_image = jobjresult.getString("voter_id");
                                Log.e("Str_Get_voter_id_image is:", "" + Str_Get_voter_id_image);

                                Str_Get_adhar_card_image = jobjresult.getString("adhar_card");
                                Log.e("Str_Get_adhar_card_image is:", "" + Str_Get_adhar_card_image);

                                Str_Get_HighestQualification_image = jobjresult.getString("license");
                                Log.e("Str_Get_HighestQualification_image is:", "" + Str_Get_HighestQualification_image);

                                Str_Get_pan_card_image = jobjresult.getString("pan_card");
                                Log.e("Str_Get_pan_card_image is:", "" + Str_Get_pan_card_image);


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("response :", "" + response);
//                        TV_volley.setText(response);
                        Tv_edit_merchant_profile_name.setText(Str_Get_user_name);
                        Tv_edit_merchant_email.setText(Str_Get_emailID);
                        Tv_edit_merchant_mobile.setText(Str_Get_phone_number);
                        Tv_edit_merchant_working_place.setText(Str_Get_work_location_areaname);

                        if (!Str_Get_profile_image.equalsIgnoreCase("http://whatsapphindistatus.com/ZOF/uploads/images/")) {
                            Log.e("Str_Get_profile_image found:", "" + Str_Get_profile_image);
                            Glide.with(Civ_edit_profile_image.getContext())
                                    .load(Str_Get_profile_image)
                                    .asBitmap().centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(new BitmapImageViewTarget(Civ_edit_profile_image) {
                                        @Override
                                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                            super.onResourceReady(bitmap, anim);
                                            Glide.with(Civ_edit_profile_image.getContext()).load(Str_Get_profile_image).centerCrop().into(Civ_edit_profile_image);
                                        }
                                    });
                        } else {
                            Log.e("StrGet_Merc_image Not found:", "OK");

                            Glide.with(Civ_edit_profile_image.getContext())
                                    .load(R.drawable.ic_the_app_guru)
                                    .asBitmap().centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(new BitmapImageViewTarget(Civ_edit_profile_image) {
                                        @Override
                                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                            super.onResourceReady(bitmap, anim);
                                            Glide.with(Civ_edit_profile_image.getContext()).load(R.drawable.ic_the_app_guru).centerCrop().into(Civ_edit_profile_image);
                                        }
                                    });

                        }

                        if (!Str_Get_voter_id_image.equalsIgnoreCase("http://whatsapphindistatus.com/ZOF/uploads/images/")) {
                            Log.e("Str_Get_voter_id_image found:", "" + Str_Get_voter_id_image);
                            Glide.with(Iv_pan_card.getContext())
                                    .load(Str_Get_voter_id_image)
                                    .asBitmap().centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(new BitmapImageViewTarget(Iv_pan_card) {
                                        @Override
                                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                            super.onResourceReady(bitmap, anim);
                                            Glide.with(Iv_pan_card.getContext()).load(Str_Get_profile_image).centerCrop().into(Iv_pan_card);
                                        }
                                    });
                        } else {
                            Log.e("Str_Get_voter_id_image Not found:", "OK");

                            Iv_pan_card.setVisibility(View.GONE);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
//                        Toast.makeText(MainVolleyActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());

//                        TV_volley.setText(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", merchantID);
                return params;
            }

        };

        // Adding request to request queue

        /*http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/*/
//        AppControllerVolley.getInstance().addToRequestQueue(stringRequest, tag_string_req);
        /*http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/*/

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    /*using volley */


    /*using Fast Android Networking */
    private void Get_MerchantDetail_Fast() {

        Log.e("Get_MerchantDetail_Fast ********************* :", "*************************");

        Log.e("merchantID :", "" + Sh_pre_User_ID);
        String tag_string_req = "req_login";
        pDialog.setMessage("Please Wait...");
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
                            Str_Get_Status = jobjresponse.getString("status");
                            Log.e("Str_Get_Status :", "" + Str_Get_Status);

                            Str_Get_message = jobjresponse.getString("message");
                            Log.e("Str_Get_message :", "" + Str_Get_message);

                            Str_Get_result = jobjresponse.getString("result");
                            Log.e("Str_Get_result :", "" + Str_Get_result);

                            JSONObject jobjresult = jobjresponse.getJSONObject("result");
                            Log.e("jobjresult :", "" + jobjresult.toString());

                            if (Str_Get_Status.equalsIgnoreCase("1")) {
                                Log.e("StrGet_Status is:", "1");

//                                tv_status.setText(StrGet_Status);

                                Str_Get_User_ID = jobjresult.getString("id");
                                Str_Get_user_name = jobjresult.getString("username");

                                Str_Get_phone_number = jobjresult.getString("mobile");

                                Str_Get_emailID = jobjresult.getString("email");

                                StrAll_Get_work_location_name = jobjresult.getString("work_location");

                                Str_Get_profile_image = jobjresult.getString("marchant_img");
                                Str_Get_voter_id_image = jobjresult.getString("voter_id");

                                Str_Get_adhar_card_image = jobjresult.getString("adhar_card");

                                Str_Get_HighestQualification_image = jobjresult.getString("qualification");

                                Str_Get_pan_card_image = jobjresult.getString("pan_card");


                                Log.e("response :", "" + responsee);
                                if (Str_Get_Status.equalsIgnoreCase("1")) {

//                        TV_volley.setText(response);
                                    Tv_edit_merchant_profile_name.setText(Str_Get_user_name);
                                    Tv_edit_merchant_email.setText(Str_Get_emailID);
                                    Tv_edit_merchant_mobile.setText(Str_Get_phone_number);
                                    Tv_edit_merchant_working_place.setText(StrAll_Get_work_location_name);


                                    Appconstant.editor.putString("location_name", StrAll_Get_work_location_name);
                                    Appconstant.editor.commit();


                                    if (!Str_Get_profile_image.equalsIgnoreCase("")) {
                                        Log.e("Str_Get_profile_image found:", "" + Str_Get_profile_image);

                                        Glide.with(Civ_edit_profile_image.getContext())
                                                .load(Str_Get_profile_image)
                                                .asBitmap().centerCrop()
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(new BitmapImageViewTarget(Civ_edit_profile_image) {
                                                    @Override
                                                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                        super.onResourceReady(bitmap, anim);
                                                        Glide.with(Civ_edit_profile_image
                                                                .getContext())
                                                                .load(Str_Get_profile_image)
                                                                .centerCrop()
                                                                .crossFade()
                                                                .into(Civ_edit_profile_image);
                                                    }
                                                });


                                    } else {
                                        Log.e("StrGet_Merc_image Not found:", "OK");

                                        Glide.with(Civ_edit_profile_image.getContext())
                                                .load(R.drawable.ic_the_app_guru)
                                                .asBitmap().centerCrop()
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(new BitmapImageViewTarget(Civ_edit_profile_image) {
                                                    @Override
                                                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                        super.onResourceReady(bitmap, anim);
                                                        Glide.with(Civ_edit_profile_image
                                                                .getContext())
                                                                .load(R.drawable.ic_the_app_guru)
                                                                .centerCrop()
                                                                .crossFade()
                                                                .into(Civ_edit_profile_image);
                                                    }
                                                });

                                    }

                                    if (!Str_Get_voter_id_image.equalsIgnoreCase("")) {
                                        Log.e("Str_Get_voter_id_image found:", "" + Str_Get_voter_id_image);
                                        Iv_voter_id.setVisibility(View.VISIBLE);
                                        Glide.with(Iv_voter_id.getContext())
                                                .load(Str_Get_voter_id_image)
                                                .asBitmap().centerCrop()
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(new BitmapImageViewTarget(Iv_voter_id) {
                                                    @Override
                                                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                        super.onResourceReady(bitmap, anim);
                                                        Glide.with(Iv_voter_id
                                                                .getContext())
                                                                .load(Str_Get_voter_id_image)
                                                                .centerCrop()
                                                                .crossFade()
                                                                .into(Iv_voter_id);
                                                    }
                                                });


                                    } else {
                                        Log.e("Str_Get_voter_id_image Not found:", "OK");

                                        Iv_voter_id.setVisibility(View.GONE);

                                    }

                                    if (!Str_Get_adhar_card_image.equalsIgnoreCase("")) {
                                        Log.e("Str_Get_adhar_card_image found:", "" + Str_Get_adhar_card_image);
                                        Iv_adhar_card.setVisibility(View.VISIBLE);
                                        Glide.with(Iv_voter_id.getContext())
                                                .load(Str_Get_adhar_card_image)
                                                .asBitmap().centerCrop()
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(new BitmapImageViewTarget(Iv_adhar_card) {
                                                    @Override
                                                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                        super.onResourceReady(bitmap, anim);
                                                        Glide.with(Iv_adhar_card
                                                                .getContext())
                                                                .load(Str_Get_adhar_card_image)
                                                                .centerCrop()
                                                                .crossFade()
                                                                .into(Iv_adhar_card);
                                                    }
                                                });


                                    } else {
                                        Log.e("Str_Get_adhar_card_image Not found:", "OK");

                                        Iv_adhar_card.setVisibility(View.GONE);

                                    }

                                    if (!Str_Get_HighestQualification_image.equalsIgnoreCase("")) {
                                        Log.e("Str_Get_HighestQualification_image found:", "" + Str_Get_HighestQualification_image);
                                        Iv_qualification.setVisibility(View.VISIBLE);
                                        Glide.with(Iv_qualification.getContext())
                                                .load(Str_Get_HighestQualification_image)
                                                .asBitmap().centerCrop()
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(new BitmapImageViewTarget(Iv_qualification) {
                                                    @Override
                                                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                        super.onResourceReady(bitmap, anim);
                                                        Glide.with(Iv_qualification
                                                                .getContext())
                                                                .load(Str_Get_HighestQualification_image)
                                                                .centerCrop()
                                                                .crossFade()
                                                                .into(Iv_qualification);
                                                    }
                                                });


                                    } else {
                                        Log.e("Str_Get_HighestQualification_image Not found:", "OK");

                                        Iv_qualification.setVisibility(View.GONE);

                                    }

                                    if (!Str_Get_pan_card_image.equalsIgnoreCase("")) {
                                        Log.e("Str_Get_pan_card_image found:", "" + Str_Get_pan_card_image);
                                        Iv_pan_card.setVisibility(View.VISIBLE);
                                        Glide.with(Iv_pan_card.getContext())
                                                .load(Str_Get_pan_card_image)
                                                .asBitmap().centerCrop()
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(new BitmapImageViewTarget(Iv_pan_card) {
                                                    @Override
                                                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                        super.onResourceReady(bitmap, anim);
                                                        Glide.with(Iv_pan_card
                                                                .getContext())
                                                                .load(Str_Get_pan_card_image)
                                                                .centerCrop()
                                                                .crossFade()
                                                                .into(Iv_pan_card);
                                                    }
                                                });


                                    } else {
                                        Log.e("Str_Get_pan_card_image Not found:", "OK");

                                        Iv_pan_card.setVisibility(View.GONE);

                                    }

                                } else {
                                    Log.e("Str_Get_User_ID is:", "" + Str_Get_User_ID);
                                    Log.e("Str_Get_user_name is:", "" + Str_Get_user_name);
                                    Log.e("Str_Get_phone_number is:", "" + Str_Get_phone_number);
                                    Log.e("Str_Get_emailID is:", "" + Str_Get_emailID);
                                    Log.e("StrAll_Get_work_location_name is:", "" + StrAll_Get_work_location_name);
                                    Log.e("Str_Get_profile_image is:", "" + Str_Get_profile_image);
                                    Log.e("Str_Get_voter_id_image is:", "" + Str_Get_voter_id_image);
                                    Log.e("Str_Get_adhar_card_image is:", "" + Str_Get_adhar_card_image);
                                    Log.e("Str_Get_HighestQualification_image is:", "" + Str_Get_HighestQualification_image);
                                    Log.e("Str_Get_pan_card_image is:", "" + Str_Get_pan_card_image);
                                }


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


    /*using volley */
    private void Get_City_List() {

        pDialog.setMessage("Fetching City List...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                HttpUrlPath.urlPathMain + GET_CITY_LIST_DETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(MainVolleyActivity.this,response,Toast.LENGTH_LONG).show();
                        hideDialog();
                        Log.e("Response List:", "" + response);
                        try {
                            JSONObject jobjresponse = new JSONObject(response);
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

                                City_typelist.add("Please select City");
                                JSONArray jsonArray = jobjresponse.getJSONArray("result");
                                Log.e("jsonArray List City:", "" + jsonArray.toString());

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

                        Log.e("response ListList:", "" + response);

                        if (Str_Get_City_List_Status.equalsIgnoreCase("1")) {
                            Log.e("Str_Get_City_List_Status is:", "1");
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileEditActivity.this,
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
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
//                        Toast.makeText(MainVolleyActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.e("Error Response ListList:", "" + error.toString());

//                        TV_volley.setText(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("user_id", merchantID);
                return params;
            }

        };

        // Adding request to request queue

        /*http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/*/
//        AppControllerVolley.getInstance().addToRequestQueue(stringRequest, tag_string_req);
        /*http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/*/

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    /*using volley */


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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileEditActivity.this,
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
//                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileEditActivity.this,
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileEditActivity.this,
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


//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileEditActivity.this,
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


    /*using Fast Android Networking */
    private void Update_MerchantLocation_Fast() {
        Log.e("UpdateMerchantDetailFast********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                + "Str_profileImage_path :" + "" + Str_profileImage_path + "\n"
                + "Str_Pan_Card_path :" + "" + Str_Pan_Card_path + "\n"
                + "Str_Aadhaar_Card_path :" + "" + Str_Aadhaar_Card_path + "\n"
                + "Str_profileImage_path :" + "" + Str_HighestQualification_path + "\n"
                + "Str_profileImage_path :" + "" + Str_Voter_Card_path + "\n"
                + "Str_Get_work_location_id :" + "" + Str_Get_work_location_areaid + "\n"
                + "Sh_pre_Merchant_mobile :" + "" + Sh_pre_Merchant_mobile + "\n");

        pDialog.setMessage("Updating Profile...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + UPDATE_MERCHANT_DETAIL)
//                .addMultipartFile("marchant_img", ProfileImage)
//                .addMultipartFile("adhar_card", Aadhaar_Card_Image)
//                .addMultipartFile("pan_card", Pan_Card_Image)
//                .addMultipartFile("adhar_card", Aadhaar_Card_Image)
//                .addMultipartFile("voter_id", VoterCard_Image)
//                .addMultipartFile("qualification", HighestQualification_Image)
                .addMultipartParameter("user_id", Sh_pre_User_ID)
                .addMultipartParameter("email", Sh_pre_Merchant_email)
                .addMultipartParameter("username", Sh_pre_Merchant_username)
                .addMultipartParameter("work_location", Str_Get_work_location_areaid)
                .addMultipartParameter("mobile", Sh_pre_Merchant_mobile)
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

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
                        Log.e("Response error:", "" + error.toString());
                    }
                });


    }


    private void UpdateMerchant_work_location_Fast() {
        Log.e("UpdateMerchant_work_location_Fast ********************* :", "*************************");

        Log.e("Fast Networking Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Sh_pre_Merchant_email :" + "" + Sh_pre_Merchant_email + "\n"
                + "Sh_pre_Merchant_username :" + "" + Sh_pre_Merchant_username + "\n"
                + "Str_Get_work_location_id :" + "" + Str_Get_work_location_areaid + "\n"
                + "Sh_pre_Merchant_mobile :" + "" + Sh_pre_Merchant_mobile + "\n");

        pDialog.setMessage("Updating Profile...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + UPDATE_MERCHANT_DETAIL)
//                .addMultipartFile("marchant_img", ProfileImage)
//                .addMultipartFile("adhar_card", Aadhaar_Card_Image)
//                .addMultipartFile("pan_card", Pan_Card_Image)
//                .addMultipartFile("adhar_card", Aadhaar_Card_Image)
//                .addMultipartFile("voter_id", VoterCard_Image)
//                .addMultipartFile("qualification", HighestQualification_Image)
                .addMultipartParameter("user_id", Sh_pre_User_ID)
                .addMultipartParameter("email", Sh_pre_Merchant_email)
                .addMultipartParameter("username", Sh_pre_Merchant_username)
                .addMultipartParameter("work_location", Str_Get_work_location_areaid)
                .addMultipartParameter("mobile", Sh_pre_Merchant_mobile)
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

                        /*things goes herer*/

                        Get_MerchantDetail_Fast();

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
                        Log.e("Response error:", "" + error.toString());
                    }
                });


    }




    /*using Fast Android Networking */


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return encodeToString(imageBytes, DEFAULT);
    }


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
        /*new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        WelcomeActivity.super.onBackPressed();
                    }
                }).create().show();*/

        Log.e("onBackPressed go ProfileGetActivity screen :", "OK");
        Intent GoMainScreen = new Intent(getApplicationContext(), ProfileGetActivity.class);
        startActivity(GoMainScreen);
        finish();


    }


}
