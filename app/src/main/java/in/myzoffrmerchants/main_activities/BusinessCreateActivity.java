package in.myzoffrmerchants.main_activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;
import com.wafflecopter.charcounttextview.CharCountTextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.myzoffrmerchants.Constant.Appconstant;
import in.myzoffrmerchants.Constant.HttpUrlPath;
import in.myzoffrmerchants.R;
import in.myzoffrmerchants.ucropImagepicker.PickerBuilder;
import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;

/**
 * Created by ritesh on 15/7/17.
 */
@SuppressWarnings("deprecation")
public class BusinessCreateActivity extends ActivityManagePermission {


    private static final int RC_CAMERA_PERM = 123;
    private static final String CREATE_BUSINESS = "create_business?";
    private static final String GET_BUSINESS = "get_business_and_package?";
    private static final String BLANK_IMAGE = "http://whatsapphindistatus.com/ZOF/uploads/images/";
    private static final int TIME_DELAY = 2000;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_CAPTURE_TWO = 1;
    private static final int REQUEST_IMAGE_CAPTURE_THREE = 1;
    private static final int REQUEST_IMAGE_CAPTURE_FOUR = 1;
    public static String TAG = BusinessCreateActivity.class.getSimpleName();
    private static long back_pressed;
    @BindView(R.id.iv_business_main_image)
    ImageView Iv_business_main_image;
    @BindView(R.id.iv_business_2_image)
    ImageView Iv_business_2_image;
    @BindView(R.id.iv_business_3_image)
    ImageView Iv_business_3_image;
    @BindView(R.id.iv_business_4_image)
    ImageView Iv_business_4_image;
    @BindView(R.id.cv_go_business)
    CardView Cv_go_business;
    @BindView(R.id.btn_set_business_name)
    Button Btn_set_business_name;
    @BindView(R.id.btn_set_business_shortname)
    Button Btn_set_business_shortname;
    @BindView(R.id.btn_set_business_description)
    Button Btn_set_business_description;
    @BindView(R.id.btn_browse_business_main_image)
    Button Btn_browse_business_main_image;
    @BindView(R.id.btn_browse_business_2_image)
    Button Btn_browse_business_2_image;
    @BindView(R.id.btn_browse_business_3_image)
    Button Btn_browse_business_3_image;
    @BindView(R.id.btn_browse_business_4_image)
    Button Btn_browse_business_4_image;
    @BindView(R.id.etCounter_business_name)
    EditText EtCounter_business_name;
    @BindView(R.id.etCounter_business_shortname)
    EditText EtCounter_business_shortname;
    @BindView(R.id.etCounter_business_description)
    EditText EtCounter_business_description;
    @BindView(R.id.toolbar_create_business)
    Toolbar Toolbar_create_business;
    @BindView(R.id.cv_business_main_image)
    CardView Cv_business_main_image;
    @BindView(R.id.cv_business_2_image)
    CardView Cv_business_2_image;
    @BindView(R.id.cv_business_3_image)
    CardView Cv_business_3_image;
    @BindView(R.id.cv_business_4_image)
    CardView Cv_business_4_image;
    File MainBusiness_Image_File, Business_2_Image_File, Business_3_Image_File, Business_4_Image_File;
    FileBody MainBusiness_Imagefilebody,
            Business_2_Imagefilebody,
            Business_3_ImageImagefilebody,
            Business_4_Imagefilebody;
    boolean doubleBackToExitPressedOnce = false;
    Dialog QuickTipDialog;
    String Str_business_main_image_path = "",
            Str_business_2_image_path = "",
            Str_business_3_image_path = "",
            Str_business_4_image_path = "",
            Sh_pre_User_ID = "",
            Sh_pre_User_city_ID = "",
            Sh_pre_User_city_Name = "",
            Sh_pre_User_Area_ID = "",
            Sh_pre_User_Area_Name = "",
            Dummy_Lat = "7000",
            Dummy_Long = "8000",
            Sh_pre_User_Business_Name = "",
            Sh_pre_User_Business_short_Name = "",
            Sh_pre_User_Business_description = "",
            Str_business_Name = "",
            Str_business_Short_name = "",
            Str_business_Tittle_description = "",
            Get_Merchant_Business_detail_message = "",
            Get_Merchant_Business_detail_status = "",
            Get_Merchant_ID = "",
            Get_Merchant_Business_ID = "",
            Get_Merchant_Business_Name = "",
            Get_Merchant_Business_Short_Name = "",
            Get_Merchant_Business_tittle_description = "",
            Get_Merchant_Business_description = "",
            Get_Merchant_Business_city = "",
            Get_Merchant_Create_Business_Status = "",
            Get_Merchant_Create_Business_result = "",
            Get_Merchant_Create_Business_message = "",
            Get_Merchant_Create_Business_ID = "",
            Get_Merchant_Create_Business_ID_Shared_Pref = "",
            Get_Merchant_Business_sub_city = "",
            Get_Merchant_Business_image1 = "",
            Get_Merchant_Business_image2 = "",
            Get_Merchant_Business_image3 = "",
            Get_Merchant_Business_image4 = "",
            Intent_MAINBUSINESS_CATEGORY_ID = "",
            Intent_SUBBUSINESS_CATEGORY_ID = "",
            Intent_CHILDBUSINESS_CATEGORY_ID = "",
            Get_Merchant_Business_activation_status = "";
    @BindView(R.id.tvCounter_business_name)
    CharCountTextView TvCounter_business_name;
    @BindView(R.id.tvCounter_business_shortname)
    CharCountTextView TvCounter_business_shortname;
    @BindView(R.id.tvCounter_business_description)
    CharCountTextView TvCounter_business_description;
    private ProgressDialog pDialogssssss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussiness_create);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());

        pDialogssssss = new ProgressDialog(this);
        pDialogssssss.setCancelable(false);


        setSupportActionBar(Toolbar_create_business);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        EtCounter_business_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        EtCounter_business_shortname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        EtCounter_business_description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Intent_MAINBUSINESS_CATEGORY_ID = getIntent().getStringExtra("MAINBUSINESS_CATEGORY_ID");
        Intent_SUBBUSINESS_CATEGORY_ID = getIntent().getStringExtra("SUBBUSINESS_CATEGORY_ID");
        Intent_CHILDBUSINESS_CATEGORY_ID = getIntent().getStringExtra("CHILDBUSINESS_CATEGORY_ID");
        Log.e("Intent_MAINBUSINESS_CATEGORY_ID  :", "" + Intent_MAINBUSINESS_CATEGORY_ID);
        Log.e("Intent_SUBBUSINESS_CATEGORY_ID  :", "" + Intent_SUBBUSINESS_CATEGORY_ID);
        Log.e("Intent_CHILDBUSINESS_CATEGORY_ID  :", "" + Intent_CHILDBUSINESS_CATEGORY_ID);


        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        Sh_pre_User_ID = Appconstant.sh.getString("id", null);
        Sh_pre_User_city_ID = Appconstant.sh.getString("location_cityid", null);
        Sh_pre_User_Area_ID = Appconstant.sh.getString("location_areaid", null);
        Sh_pre_User_city_Name = Appconstant.sh.getString("location_cityname", null);
        Sh_pre_User_Area_Name = Appconstant.sh.getString("location_areaname", null);
        Log.e("Merchant_ID from SharedPref :", "" + Sh_pre_User_ID);
        Log.e("city_ID from SharedPref :", "" + Sh_pre_User_city_ID);
        Log.e("Area_ID from SharedPref :", "" + Sh_pre_User_Area_ID);
        Log.e("city_Name from SharedPref :", "" + Sh_pre_User_city_Name);
        Log.e("Area_Name from SharedPref :", "" + Sh_pre_User_Area_Name);


//        Sh_pre_User_city_Name = Appconstant.sh.getString("location_cityname", null);
//        Sh_pre_User_Area_Name = Appconstant.sh.getString("location_areaname", null);
//        Sh_pre_User_Business_Name = Appconstant.sh.getString("Business_name", null);
//        Sh_pre_User_Business_short_Name = Appconstant.sh.getString("Business_shotname", null);
//        Sh_pre_User_Business_description = Appconstant.sh.getString("Business_description", null);
//        Sh_pre_User_Business_description = Appconstant.sh.getString("Business_description", null);
//        Get_Merchant_Create_Business_ID_Shared_Pref = Appconstant.sh.getString("BUSINESS_ID", null);

        /*Appconstant.editor.putString("location_name", StrAll_Get_work_location_name);
          Appconstant.editor.commit();*/


//        Log.e("Sh_pre_User_city_ID from SharedPref :", "" + Sh_pre_User_city_ID);
//        Log.e("Sh_pre_User_city_Name from SharedPref :", "" + Sh_pre_User_city_Name);
//
//        Log.e("Sh_pre_User_Area_ID from SharedPref :", "" + Sh_pre_User_Area_ID);
//        Log.e("Sh_pre_User_Area_Name from SharedPref :", "" + Sh_pre_User_Area_Name);
//
//        Log.e("Sh_pre_User_Business_Name from SharedPref :", "" + Sh_pre_User_Business_Name);
//        Log.e("Sh_pre_User_Business_short_Name from SharedPref :", "" + Sh_pre_User_Business_short_Name);
//        Log.e("Sh_pre_User_Business_description from SharedPref :", "" + Sh_pre_User_Business_description);
//
//        Log.e("Business_ID from SharedPref :", "" + Get_Merchant_Create_Business_ID_Shared_Pref);


//        GET_Business_Detail_Fast();

        TvCounter_business_name.setEditText(EtCounter_business_name);
        TvCounter_business_name.setCharCountChangedListener(new CharCountTextView.CharCountChangedListener() {
            @Override
            public void onCountChanged(int countRemaining, boolean hasExceededLimit) {

                if (hasExceededLimit) {
                    Btn_set_business_name.setEnabled(false);
                    Btn_set_business_name.setBackgroundColor(getResources().getColor(R.color.grey));
                } else {
                    Btn_set_business_name.setEnabled(true);
                    Btn_set_business_name.setBackgroundColor(getResources().getColor(R.color.reddark));


                }

            }
        });


        TvCounter_business_shortname.setEditText(EtCounter_business_shortname);
        TvCounter_business_shortname.setCharCountChangedListener(new CharCountTextView.CharCountChangedListener() {
            @Override
            public void onCountChanged(int countRemaining, boolean hasExceededLimit) {

                if (hasExceededLimit) {
                    Btn_set_business_shortname.setEnabled(false);
                    Btn_set_business_shortname.setBackgroundColor(getResources().getColor(R.color.grey));
                } else {
                    Btn_set_business_shortname.setEnabled(true);
                    Btn_set_business_shortname.setBackgroundColor(getResources().getColor(R.color.reddark));
                }

            }
        });


        TvCounter_business_description.setEditText(EtCounter_business_description);
        TvCounter_business_description.setCharCountChangedListener(new CharCountTextView.CharCountChangedListener() {
            @Override
            public void onCountChanged(int countRemaining, boolean hasExceededLimit) {

                if (hasExceededLimit) {
                    Btn_set_business_description.setEnabled(false);
                    Btn_set_business_description.setBackgroundColor(getResources().getColor(R.color.grey));
                } else {
                    Btn_set_business_description.setEnabled(true);
                    Btn_set_business_description.setBackgroundColor(getResources().getColor(R.color.reddark));
                }

            }
        });


        Btn_set_business_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean iserror = false;

                Str_business_Name = EtCounter_business_name.getText().toString().trim();
                Log.e(" Set Business Name Fields data :", "\n"
                        + "Str_business_name :" + "" + Str_business_Name + "\n");

                if (Str_business_Name.equals("")) {
                    iserror = true;

                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(EtCounter_business_name);

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Business Name", Toast.LENGTH_SHORT).show();

                } else {
                    Log.e("Btn_set_business_name :", "OK");

                    Sh_pre_User_Business_Name = EtCounter_business_name.getText().toString().trim();
                    Log.e("Sh_pre_User_Business_Name Set Button :", "" + Sh_pre_User_Business_Name);
                    Appconstant.editor.putString("Business_name", Sh_pre_User_Business_Name);
                    Appconstant.editor.commit();

                    Update_Business_Name_Fast();

                }

            }
        });


        Btn_set_business_shortname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean iserror = false;
                Str_business_Short_name = EtCounter_business_shortname.getText().toString().trim();
                Log.e(" business_shortname Fields data :", "\n"
                        + "Str_business_Short_name :" + "" + Str_business_Short_name + "\n");


                if (Str_business_Short_name.equals("")) {
                    iserror = true;

                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(EtCounter_business_shortname);

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Business Short Name", Toast.LENGTH_SHORT).show();

                } else {
                    Log.e("Btn_set_business_shortname :", "OK");

                    Sh_pre_User_Business_short_Name = EtCounter_business_shortname.getText().toString().trim();
                    Log.e("Sh_pre_User_Business_short_Name Set Button :", "" + Sh_pre_User_Business_short_Name);
                    Appconstant.editor.putString("Business_shotname", Sh_pre_User_Business_short_Name);
                    Appconstant.editor.commit();

                    Update_Business_Short_Name_Fast();


                }

            }
        });


        Btn_set_business_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean iserror = false;
                Str_business_Tittle_description = EtCounter_business_description.getText().toString().trim();
                Log.e(" Create Fields data :", "\n"
                        + "Str_business_Tittle_description :" + "" + Str_business_Tittle_description + "\n");


                if (Str_business_Tittle_description.equals("")) {
                    iserror = true;

                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(EtCounter_business_description);

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Business Description", Toast.LENGTH_SHORT).show();

                } else {
                    Log.e("Btn_set_business_description :", "OK");

                    Sh_pre_User_Business_description = EtCounter_business_description.getText().toString().trim();
                    Log.e("Sh_pre_User_Business_description Set Button :", "" + Sh_pre_User_Business_description);
                    Appconstant.editor.putString("Business_description", Sh_pre_User_Business_description);
                    Appconstant.editor.commit();

                    Update_Business_Tittle_description_Fast();

                }

            }
        });


        Cv_go_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

//                CreateBusinessDataImageCheck();

//                Update_Business_Detail_Fast();

//                Intent GoBusinessScreen = new Intent(getApplicationContext(), BusinessMainActivity.class);
//                startActivity(GoBusinessScreen);
//                finish();

                boolean iserror = false;

                Str_business_Name = EtCounter_business_name.getText().toString().trim();
                Str_business_Short_name = EtCounter_business_shortname.getText().toString().trim();
                Str_business_Tittle_description = EtCounter_business_description.getText().toString().trim();


                Log.e(" Set Business Text Fields data :", "\n"
                        + "Str_business_name :" + "" + Str_business_Name + "\n"
                        + "Str_business_Short_name :" + "" + Str_business_Short_name + "\n"
                        + "Str_business_Tittle_description :" + "" + Str_business_Tittle_description + "\n"
                        + "Dummy_Lat :" + "" + Dummy_Lat + "\n"
                        + "Dummy_Long :" + "" + Dummy_Long + "\n"
                        + "Intent_MAINBUSINESS_CATEGORY_ID :" + "" + Intent_MAINBUSINESS_CATEGORY_ID + "\n"
                        + "Intent_SUBBUSINESS_CATEGORY_ID :" + "" + Intent_SUBBUSINESS_CATEGORY_ID + "\n"
                        + "Intent_CHILDBUSINESS_CATEGORY_ID :" + "" + Intent_CHILDBUSINESS_CATEGORY_ID + "\n"
                        + "Str_business_main_image_path :" + "" + Str_business_main_image_path + "\n"
                        + "Str_business_2_image_path :" + "" + Str_business_2_image_path + "\n"
                        + "Str_business_3_image_path :" + "" + Str_business_3_image_path + "\n"
                        + "Str_business_4_image_path :" + "" + Str_business_4_image_path + "\n");


                if (Str_business_Name.equals("")) {
                    iserror = true;

                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(EtCounter_business_name);

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Business Name", Toast.LENGTH_SHORT).show();

                } else if (Str_business_Short_name.equals("")) {
                    iserror = true;

                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(EtCounter_business_shortname);

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Business Short Name", Toast.LENGTH_SHORT).show();

                } else if (Str_business_Tittle_description.equals("")) {
                    iserror = true;

                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(EtCounter_business_description);

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Business Description", Toast.LENGTH_SHORT).show();

                } else if (Str_business_main_image_path.equals("")) {
                    iserror = true;

                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Cv_business_main_image);

                    Toast.makeText(getApplicationContext(),
                            "Please select your Business Front Image", Toast.LENGTH_SHORT).show();

                } else if (Str_business_2_image_path.equals("")) {
                    iserror = true;

                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Cv_business_2_image);

                    Toast.makeText(getApplicationContext(),
                            "Please select your Business Second Image", Toast.LENGTH_SHORT).show();

                } else if (Str_business_3_image_path.equals("")) {
                    iserror = true;

                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Cv_business_3_image);

                    Toast.makeText(getApplicationContext(),
                            "Please select your Business Third Image", Toast.LENGTH_SHORT).show();

                } else if (Str_business_4_image_path.equals("")) {
                    iserror = true;

                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Cv_business_4_image);

                    Toast.makeText(getApplicationContext(),
                            "Please select your Business Fourth Image", Toast.LENGTH_SHORT).show();

                }
                if (!iserror) {
                    Log.e("No Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                    Toast.makeText(getApplicationContext(), "No Error", Toast.LENGTH_SHORT).show();


                    CreateBusinessJsontask task = new CreateBusinessJsontask();
                    task.execute();

                }


            }
        });

        Btn_browse_business_main_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                //single permission
//                askCompactPermission(PermissionUtils.Manifest_CAMERA, new PermissionResult() {
//                    @Override
//                    public void permissionGranted() {
//                        //permission granted
//                        //replace with your action
//                        Log.e("HEllo ", "" + "permissionGranted");
////                        dispatchTakePictureIntent();
////                        BusinessMainImagePicker();
//                        sampleAskMultiplePermission();
//
//                    }
//
//                    @Override
//                    public void permissionDenied() {
//                        //permission denied
//                        //replace with your action
//                        Log.e("HEllo", "" + "permissionDenied");
//                    }
//
//                    @Override
//                    public void permissionForeverDenied() {
//                        //permission denied
//                        //replace with your action
////                        QuickTipDialog.dismiss();
//                        Log.e("HEllo", "" + "permissionForeverDenied");
//                        showDialogs();
//
//                    }
//                });


                String permissionAsk[] = {PermissionUtils.Manifest_CAMERA, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE};

                askCompactPermissions(permissionAsk, new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        //permission granted
                        //replace with your action
                        Log.e("HEllo ", "" + "permissionGranted");
//                        dispatchTakePictureIntent();
                        BusinessMainImagePicker();
                    }

                    @Override
                    public void permissionDenied() {
                        //permission denied
                        //replace with your action
                        Log.e("HEllo ", "" + "permissionDenied");
                    }

                    @Override
                    public void permissionForeverDenied() {

                        Log.e("HEllo", "" + "permissionForeverDenied");
                        showDialogs();

                    }
                });

            }
        });


        Btn_browse_business_2_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //single permission
//                askCompactPermission(PermissionUtils.Manifest_CAMERA, new PermissionResult() {
//                    @Override
//                    public void permissionGranted() {
//                        //permission granted
//                        //replace with your action
//                        Log.e("HEllo ", "" + "permissionGranted");
////                        dispatchTakePictureIntent();
//
//                        sampleAskMultiplePermissionTwo();
//
//
//                    }
//
//                    @Override
//                    public void permissionDenied() {
//                        //permission denied
//                        //replace with your action
//                        Log.e("HEllo", "" + "permissionDenied");
//                    }
//
//                    @Override
//                    public void permissionForeverDenied() {
//                        //permission denied
//                        //replace with your action
////                        QuickTipDialog.dismiss();
//                        Log.e("HEllo", "" + "permissionForeverDenied");
//                        showDialogs();
//
//                    }
//                });

                String permissionAsk[] = {PermissionUtils.Manifest_CAMERA, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE};

                askCompactPermissions(permissionAsk, new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        //permission granted
                        //replace with your action
                        Log.e("HEllo ", "" + "permissionGranted");
//                        dispatchTakePictureIntent();
                        BusinessImage_2_Picker();
                    }

                    @Override
                    public void permissionDenied() {
                        //permission denied
                        //replace with your action
                        Log.e("HEllo ", "" + "permissionDenied");
                    }

                    @Override
                    public void permissionForeverDenied() {

                        Log.e("HEllo", "" + "permissionForeverDenied");
                        showDialogs();

                    }
                });


            }
        });


        Btn_browse_business_3_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //single permission
//                askCompactPermission(PermissionUtils.Manifest_CAMERA, new PermissionResult() {
//                    @Override
//                    public void permissionGranted() {
//                        //permission granted
//                        //replace with your action
//                        Log.e("HEllo ", "" + "permissionGranted");
////                        dispatchTakePictureIntent();
//                        BusinessImage_3_Picker();
//
//                    }
//
//                    @Override
//                    public void permissionDenied() {
//                        //permission denied
//                        //replace with your action
//                        Log.e("HEllo", "" + "permissionDenied");
//                    }
//
//                    @Override
//                    public void permissionForeverDenied() {
//                        //permission denied
//                        //replace with your action
////                        QuickTipDialog.dismiss();
//                        Log.e("HEllo", "" + "permissionForeverDenied");
//                        showDialogs();
//
//                    }
//                });

                String permissionAsk[] = {PermissionUtils.Manifest_CAMERA, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE};

                askCompactPermissions(permissionAsk, new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        //permission granted
                        //replace with your action
                        Log.e("HEllo ", "" + "permissionGranted");
//                        dispatchTakePictureIntent();
                        BusinessImage_3_Picker();
                    }

                    @Override
                    public void permissionDenied() {
                        //permission denied
                        //replace with your action
                        Log.e("HEllo ", "" + "permissionDenied");
                    }

                    @Override
                    public void permissionForeverDenied() {

                        Log.e("HEllo", "" + "permissionForeverDenied");
                        showDialogs();

                    }
                });


            }
        });


        Btn_browse_business_4_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //single permission
//                askCompactPermission(PermissionUtils.Manifest_CAMERA, new PermissionResult() {
//                    @Override
//                    public void permissionGranted() {
//                        //permission granted
//                        //replace with your action
//                        Log.e("HEllo ", "" + "permissionGranted");
////                        dispatchTakePictureIntent();
//                        BusinessImage_4_Picker();
//
//                    }
//
//                    @Override
//                    public void permissionDenied() {
//                        //permission denied
//                        //replace with your action
//                        Log.e("HEllo", "" + "permissionDenied");
//                    }
//
//                    @Override
//                    public void permissionForeverDenied() {
//                        //permission denied
//                        //replace with your action
////                        QuickTipDialog.dismiss();
//                        Log.e("HEllo", "" + "permissionForeverDenied");
//                        showDialogs();
//
//                    }
//                });


                String permissionAsk[] = {PermissionUtils.Manifest_CAMERA, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE};

                askCompactPermissions(permissionAsk, new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        //permission granted
                        //replace with your action
                        Log.e("HEllo ", "" + "permissionGranted");
//                        dispatchTakePictureIntent();
                        BusinessImage_4_Picker();
                    }

                    @Override
                    public void permissionDenied() {
                        //permission denied
                        //replace with your action
                        Log.e("HEllo ", "" + "permissionDenied");
                    }

                    @Override
                    public void permissionForeverDenied() {

                        Log.e("HEllo", "" + "permissionForeverDenied");
                        showDialogs();

                    }
                });


            }
        });


    }

    private void sampleAskMultiplePermission() {
        String permissionAsk[] = {PermissionUtils.Manifest_CAMERA, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE};

        askCompactPermissions(permissionAsk, new PermissionResult() {
            @Override
            public void permissionGranted() {
                //permission granted
                //replace with your action
//                dispatchTakePictureIntent();
            }

            @Override
            public void permissionDenied() {
                //permission denied
                //replace with your action
            }

            @Override
            public void permissionForeverDenied() {

                showDialog();

            }
        });

    }

    private void sampleAskMultiplePermissionTwo() {
        String permissionAsk[] = {PermissionUtils.Manifest_CAMERA, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE};

        askCompactPermissions(permissionAsk, new PermissionResult() {
            @Override
            public void permissionGranted() {
                //permission granted
                //replace with your action
                dispatchTakePictureIntentTwo();
            }

            @Override
            public void permissionDenied() {
                //permission denied
                //replace with your action
            }

            @Override
            public void permissionForeverDenied() {

                showDialog();

            }
        });

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntentMainImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntentMainImage.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntentMainImage, REQUEST_IMAGE_CAPTURE);
            BusinessMainImagePicker();
        }
    }

    private void dispatchTakePictureIntentTwo() {
        Intent takePictureIntentTWOImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntentTWOImage.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntentTWOImage, REQUEST_IMAGE_CAPTURE_TWO);
            BusinessImage_2_Picker();
        }
    }

    /*using Fast Android Networking */
    private void CreateBusinessFast() {
        Log.e("CreateBusinessFast ********************* :", "*************************");

        Log.e("Volley Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Str_business_name :" + "" + Str_business_Name + "\n"
                + "Str_business_Short_name :" + "" + Str_business_Short_name + "\n"
                + "Str_business_Tittle_description :" + "" + Str_business_Tittle_description + "\n"
                + "Str_business_main_image_path :" + "" + Str_business_main_image_path + "\n"
                + "Str_business_2_image_path :" + "" + Str_business_2_image_path + "\n"
                + "Str_business_3_image_path :" + "" + Str_business_3_image_path + "\n"
                + "Str_business_4_image_path :" + "" + Str_business_4_image_path + "\n");

        pDialogssssss.setMessage("Creating Business...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + CREATE_BUSINESS)
                .addMultipartFile("image1", MainBusiness_Image_File)
                .addMultipartFile("image2", Business_2_Image_File)
                .addMultipartFile("image3", Business_3_Image_File)
                .addMultipartFile("image4", Business_4_Image_File)
                .addMultipartParameter("marchent_id", Sh_pre_User_ID)
                .addMultipartParameter("business_name", Str_business_Name)
                .addMultipartParameter("sub_name", Str_business_Short_name)
                .addMultipartParameter("description", Str_business_Tittle_description)
                .addMultipartParameter("tittel_description", Str_business_Tittle_description)
                .addMultipartParameter("city", Str_business_Name)
                .addMultipartParameter("sub_city", Str_business_Name)
//                .addMultipartParameter("lat", Str_business_Name)
//                .addMultipartParameter("lon", Str_business_Name)
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
                        Log.e("Response upload:", "" + response);
//                        merchantDetailFast();

//                        try {
//                            JSONObject jobjresponse = new JSONObject(String.valueOf(response));
//                            Log.e("jobjresponse upload :", "" + jobjresponse);
//                            Str_Get_Status = jobjresponse.getString("status");
//                            Log.e("Str_Get_Status upload :", "" + Str_Get_Status);
//
//                            Str_Get_message = jobjresponse.getString("message");
//                            Log.e("Str_Get_message upload :", "" + Str_Get_message);
//
//                            Str_Get_result = jobjresponse.getString("result");
//                            Log.e("Str_Get_result upload :", "" + Str_Get_result);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

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
    private void Update_Business_Detail_Fast() {
        Log.e("GET_Business_Detail_Fast ********************* :", "*************************");

        Log.e("FAST Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Str_business_name :" + "" + Str_business_Name + "\n"
                + "Str_business_Short_name :" + "" + Str_business_Short_name + "\n"
                + "Str_business_Tittle_description :" + "" + Str_business_Tittle_description + "\n"
                + "Str_business_main_image_path :" + "" + Str_business_main_image_path + "\n"
                + "Str_business_2_image_path :" + "" + Str_business_2_image_path + "\n"
                + "Str_business_3_image_path :" + "" + Str_business_3_image_path + "\n"
                + "Str_business_4_image_path :" + "" + Str_business_4_image_path + "\n");

        pDialogssssss.setMessage("Creating Business...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + CREATE_BUSINESS)
                .addMultipartFile("image1", MainBusiness_Image_File)
//                .addMultipartFile("image2", Business_2_Image_File)
//                .addMultipartFile("image3", Business_3_Image_File)
//                .addMultipartFile("image4", Business_4_Image_File)
                .addMultipartParameter("marchent_id", Sh_pre_User_ID)
                .addMultipartParameter("business_name", Sh_pre_User_Business_Name)
                .addMultipartParameter("sub_name", Sh_pre_User_Business_short_Name)
                .addMultipartParameter("description", Sh_pre_User_Business_description)
                .addMultipartParameter("tittel_description", Str_business_Tittle_description)
//                .addMultipartParameter("city", Str_business_Name)
//                .addMultipartParameter("sub_city", Str_business_Name)
//                .addMultipartParameter("lat", Str_business_Name)
//                .addMultipartParameter("lon", Str_business_Name)
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
                        Log.e("Update business Response upload:", "" + response);

//                        Intent GoBusinessScreen = new Intent(getApplicationContext(), BusinessMainActivity.class);
//                        startActivity(GoBusinessScreen);
//                        finish();

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
//                        GET_Business_Detail_Fast();
                        Log.e("Response error:", "" + error.toString());

//                        Intent GoBusinessScreen = new Intent(getApplicationContext(), BusinessMainActivity.class);
//                        startActivity(GoBusinessScreen);
//                        finish();
                    }
                });


    }
    /*using Fast Android Networking */

    private void Update_Business_Name_Fast() {
        Log.e("GET_Business_Detail_Fast ********************* :", "*************************");

        Log.e("FAST Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Str_business_name :" + "" + Str_business_Name + "\n"
                + "Str_business_Short_name :" + "" + Str_business_Short_name + "\n"
                + "Str_business_Tittle_description :" + "" + Str_business_Tittle_description + "\n"
                + "Str_business_main_image_path :" + "" + Str_business_main_image_path + "\n"
                + "Str_business_2_image_path :" + "" + Str_business_2_image_path + "\n"
                + "Str_business_3_image_path :" + "" + Str_business_3_image_path + "\n"
                + "Str_business_4_image_path :" + "" + Str_business_4_image_path + "\n");

        pDialogssssss.setMessage("Creating Business...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + CREATE_BUSINESS)
                .addMultipartFile("image1", MainBusiness_Image_File)
                .addMultipartParameter("marchent_id", Sh_pre_User_ID)
                .addMultipartParameter("business_name", Sh_pre_User_Business_Name)
                .addMultipartParameter("sub_name", Sh_pre_User_Business_short_Name)
                .addMultipartParameter("description", Sh_pre_User_Business_description)
                .addMultipartParameter("tittel_description", Sh_pre_User_Business_description)
                .addMultipartParameter("city", Str_business_Name)
                .addMultipartParameter("sub_city", Str_business_Name)
//                .addMultipartParameter("lat", Str_business_Name)
//                .addMultipartParameter("lon", Str_business_Name)
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
                        Log.e("Response upload:", "" + response);

                        try {
                            JSONObject jobjresponse = new JSONObject(String.valueOf(response));
                            Log.e("jobjresponse :", "" + jobjresponse);

                            Get_Merchant_Create_Business_Status = jobjresponse.getString("status");
                            Log.e("Get_Merchant_Create_Business_Status :", "" + Get_Merchant_Create_Business_Status);
                            if (Get_Merchant_Create_Business_Status.equalsIgnoreCase("1")) {
                                Log.e("Get_Merchant_Create_Business_Status jobjresponse is:", "1");

                                Get_Merchant_Create_Business_message = jobjresponse.getString("message");
                                Log.e("StrGet_Merc_message is:", "" + Get_Merchant_Create_Business_message);

                                JSONObject jobjresult = jobjresponse.getJSONObject("result");
                                Get_Merchant_Create_Business_result = jobjresult.toString();
                                Log.e("Get_Merchant_Create_Business_result :", "" + Get_Merchant_Create_Business_result);

                                Get_Merchant_Create_Business_ID = jobjresult.getString("id");
                                Log.e("Get_Merchant_Create_Business_ID is:", "" + Get_Merchant_Create_Business_ID);

                                hideDialog();
                            }


                        } catch (JSONException e) {
                            hideDialog();
                            e.printStackTrace();
                        }

                        if (Get_Merchant_Create_Business_Status.equalsIgnoreCase("1")) {
                            Log.e("Get_Merchant_Create_Business_Status jobjresponse is:", "1");

                            Log.e("Get_Merchant_Create_Business_ID is:", "" + Get_Merchant_Create_Business_ID);

                            Appconstant.editor.putString("BUSINESS_ID", Get_Merchant_Create_Business_ID);
                            Appconstant.editor.commit();


                            hideDialog();
                        } else {

                            Log.e("Get_Merchant_Create_Business_Status jobjresponse is:", "0");
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
                        GET_Business_Detail_Fast();
                        Log.e("Response error:", "" + error.toString());
                    }
                });


    }
    /*using Fast Android Networking */

    private void Update_Business_Short_Name_Fast() {
        Log.e("GET_Business_Detail_Fast ********************* :", "*************************");

        Log.e("FAST Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Str_business_name :" + "" + Str_business_Name + "\n"
                + "Str_business_Short_name :" + "" + Str_business_Short_name + "\n"
                + "Str_business_Tittle_description :" + "" + Str_business_Tittle_description + "\n"
                + "Str_business_main_image_path :" + "" + Str_business_main_image_path + "\n"
                + "Str_business_2_image_path :" + "" + Str_business_2_image_path + "\n"
                + "Str_business_3_image_path :" + "" + Str_business_3_image_path + "\n"
                + "Str_business_4_image_path :" + "" + Str_business_4_image_path + "\n");

        pDialogssssss.setMessage("Creating Business...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + CREATE_BUSINESS)
                .addMultipartFile("image1", MainBusiness_Image_File)
                .addMultipartParameter("marchent_id", Sh_pre_User_ID)
                .addMultipartParameter("business_name", Str_business_Name)
                .addMultipartParameter("sub_name", Str_business_Short_name)
                .addMultipartParameter("description", Str_business_Tittle_description)
                .addMultipartParameter("tittel_description", Str_business_Tittle_description)
                .addMultipartParameter("city", Str_business_Name)
                .addMultipartParameter("sub_city", Str_business_Name)
//                .addMultipartParameter("lat", Str_business_Name)
//                .addMultipartParameter("lon", Str_business_Name)
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
                        Log.e("Response upload:", "" + response);

                        try {
                            JSONObject jobjresponse = new JSONObject(String.valueOf(response));
                            Log.e("jobjresponse :", "" + jobjresponse);

                            Get_Merchant_Create_Business_Status = jobjresponse.getString("status");
                            Log.e("Get_Merchant_Create_Business_Status :", "" + Get_Merchant_Create_Business_Status);
                            if (Get_Merchant_Create_Business_Status.equalsIgnoreCase("1")) {
                                Log.e("Get_Merchant_Create_Business_Status jobjresponse is:", "1");

                                Get_Merchant_Create_Business_message = jobjresponse.getString("message");
                                Log.e("StrGet_Merc_message is:", "" + Get_Merchant_Create_Business_message);

                                JSONObject jobjresult = jobjresponse.getJSONObject("result");
                                Get_Merchant_Create_Business_result = jobjresult.toString();
                                Log.e("Get_Merchant_Create_Business_result :", "" + Get_Merchant_Create_Business_result);

                                Get_Merchant_Create_Business_ID = jobjresult.getString("id");
                                Log.e("Get_Merchant_Create_Business_ID is:", "" + Get_Merchant_Create_Business_ID);

                                hideDialog();
                            }


                        } catch (JSONException e) {
                            hideDialog();
                            e.printStackTrace();
                        }

                        if (Get_Merchant_Create_Business_Status.equalsIgnoreCase("1")) {
                            Log.e("Get_Merchant_Create_Business_Status jobjresponse is:", "1");

                            Log.e("Get_Merchant_Create_Business_ID is:", "" + Get_Merchant_Create_Business_ID);

                            Appconstant.editor.putString("BUSINESS_ID", Get_Merchant_Create_Business_ID);
                            Appconstant.editor.commit();


                            hideDialog();
                        } else {

                            Log.e("Get_Merchant_Create_Business_Status jobjresponse is:", "0");
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
                        GET_Business_Detail_Fast();
                        Log.e("Response error:", "" + error.toString());
                    }
                });


    }

    private void Update_Business_Tittle_description_Fast() {
        Log.e("GET_Business_Detail_Fast ********************* :", "*************************");

        Log.e("FAST Update data :", "\n"
                + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Str_business_name :" + "" + Str_business_Name + "\n"
                + "Str_business_Short_name :" + "" + Str_business_Short_name + "\n"
                + "Str_business_Tittle_description :" + "" + Str_business_Tittle_description + "\n"
                + "Str_business_main_image_path :" + "" + Str_business_main_image_path + "\n"
                + "Str_business_2_image_path :" + "" + Str_business_2_image_path + "\n"
                + "Str_business_3_image_path :" + "" + Str_business_3_image_path + "\n"
                + "Str_business_4_image_path :" + "" + Str_business_4_image_path + "\n");

        pDialogssssss.setMessage("Creating Business...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + CREATE_BUSINESS)
                .addMultipartFile("image1", MainBusiness_Image_File)
                .addMultipartParameter("marchent_id", Sh_pre_User_ID)
                .addMultipartParameter("business_name", Str_business_Name)
                .addMultipartParameter("sub_name", Str_business_Name)
                .addMultipartParameter("description", Str_business_Name)
                .addMultipartParameter("tittel_description", Str_business_Tittle_description)
                .addMultipartParameter("city", Str_business_Name)
                .addMultipartParameter("sub_city", Str_business_Name)
//                .addMultipartParameter("lat", Str_business_Name)
//                .addMultipartParameter("lon", Str_business_Name)
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
                        Log.e("Response upload:", "" + response);

                        try {
                            JSONObject jobjresponse = new JSONObject(String.valueOf(response));
                            Log.e("jobjresponse :", "" + jobjresponse);

                            Get_Merchant_Create_Business_Status = jobjresponse.getString("status");
                            Log.e("Get_Merchant_Create_Business_Status :", "" + Get_Merchant_Create_Business_Status);
                            if (Get_Merchant_Create_Business_Status.equalsIgnoreCase("1")) {
                                Log.e("Get_Merchant_Create_Business_Status jobjresponse is:", "1");

                                Get_Merchant_Create_Business_message = jobjresponse.getString("message");
                                Log.e("StrGet_Merc_message is:", "" + Get_Merchant_Create_Business_message);

                                JSONObject jobjresult = jobjresponse.getJSONObject("result");
                                Get_Merchant_Create_Business_result = jobjresult.toString();
                                Log.e("Get_Merchant_Create_Business_result :", "" + Get_Merchant_Create_Business_result);

                                Get_Merchant_Create_Business_ID = jobjresult.getString("id");
                                Log.e("Get_Merchant_Create_Business_ID is:", "" + Get_Merchant_Create_Business_ID);

                                hideDialog();
                            }


                        } catch (JSONException e) {
                            hideDialog();
                            e.printStackTrace();
                        }

                        if (Get_Merchant_Create_Business_Status.equalsIgnoreCase("1")) {
                            Log.e("Get_Merchant_Create_Business_Status jobjresponse is:", "1");

                            Log.e("Get_Merchant_Create_Business_ID is:", "" + Get_Merchant_Create_Business_ID);

                            Appconstant.editor.putString("BUSINESS_ID", Get_Merchant_Create_Business_ID);
                            Appconstant.editor.commit();


                            hideDialog();
                        } else {

                            Log.e("Get_Merchant_Create_Business_Status jobjresponse is:", "0");
                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
                        GET_Business_Detail_Fast();
                        Log.e("Response error:", "" + error.toString());
                    }
                });


    }

    private void GET_Business_Detail_Fast() {
        final String user_id = Sh_pre_User_ID;

        Log.e("Volley Data :", ""
                        + "\n" + "user_id :" + "" + user_id
//                + "\n" + "user_password" + "" + user_password
        );

        // Tag used to cancel the request
        String tag_string_req = "req_registration";
        pDialogssssss.setMessage("Please Wait Fetching Detail.....");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlPath.urlPathMain
                + GET_BUSINESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                        Toast.makeText(MainVolleyActivity.this,response,Toast.LENGTH_LONG).show();
                hideDialog();
                Log.e("Response :", "" + response);
                try {
                    JSONObject jobjresponse = new JSONObject(response);
                    Log.e("jobjresponse :", "" + jobjresponse);

                    Get_Merchant_Business_detail_status = jobjresponse.getString("status");
                    Log.e("Get_Merchant_Business_detail_status :", "" + Get_Merchant_Business_detail_status);

                    Get_Merchant_Business_detail_message = jobjresponse.getString("message");
                    Log.e("Get_Merchant_Business_detail_message :", "" + Get_Merchant_Business_detail_message);

                    if (Get_Merchant_Business_detail_status.equalsIgnoreCase("1")) {
                        Log.e("StrGet_Merc_status jobjresponse is:", "1");


                        JSONObject jobjGetresult = jobjresponse.getJSONObject("result");

                        Get_Merchant_ID = jobjGetresult.getString("marchent_id");

                        Get_Merchant_Business_ID = jobjGetresult.getString("id");
                        Get_Merchant_Business_Name = jobjGetresult.getString("business_name");


                        Get_Merchant_Business_Short_Name = jobjGetresult.getString("sub_name");

                        Get_Merchant_Business_description = jobjGetresult.getString("description");
//
//                        Get_Merchant_Business_tittle_description = jobjGetresult.getString("tittel_description");
//                        Log.e("StrGet_Merc_email is:", "" + Get_Merchant_Business_tittle_description);

                        Get_Merchant_Business_activation_status = jobjGetresult.getString("stetus");

//                        Get_Merchant_Business_city = jobjGetresult.getString("city");
//                        Log.e("Get_Merchant_Business_city is:", "" + Get_Merchant_Business_city);
//
//                        Get_Merchant_Business_sub_city = jobjGetresult.getString("sub_city");
//                        Log.e("Get_Merchant_Business_sub_city is:", "" + Get_Merchant_Business_sub_city);

                        Str_business_main_image_path = jobjGetresult.getString("image1");

                        Str_business_2_image_path = jobjGetresult.getString("image2");
                        Str_business_3_image_path = jobjGetresult.getString("image3");

                        Str_business_4_image_path = jobjGetresult.getString("image4");


                    }


                } catch (JSONException e) {
                    hideDialog();
                    e.printStackTrace();
                }

//                Log.e("response :", "" + response);

                if (Get_Merchant_Business_detail_status.equalsIgnoreCase("1")) {

                    EtCounter_business_name.setText(Sh_pre_User_Business_Name);
                    EtCounter_business_shortname.setText(Sh_pre_User_Business_short_Name);
                    EtCounter_business_description.setText(Sh_pre_User_Business_description);

//                    if (Get_Merchant_Create_Business_Status.equalsIgnoreCase("1")) {
                    Log.e("Merchant_Create_Business Shared jobjresponse is:", "1");
                    Appconstant.editor.putString("BUSINESS_ID", Get_Merchant_Business_ID);
                    Appconstant.editor.commit();
                    hideDialog();
//                    }

                    if (!Str_business_main_image_path.equalsIgnoreCase(BLANK_IMAGE)) {

                        Iv_business_main_image.setVisibility(View.VISIBLE);

                        Glide.with(Iv_business_main_image.getContext())
                                .load(Str_business_main_image_path)
                                .asBitmap().centerCrop()
                                .crossFade()
                                .into(new BitmapImageViewTarget(Iv_business_main_image) {
                                    @Override
                                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                        super.onResourceReady(bitmap, anim);
                                        Glide.with(Iv_business_main_image
                                                .getContext())
                                                .load(Str_business_main_image_path)
                                                .centerCrop()
                                                .crossFade()
                                                .into(Iv_business_main_image);
                                    }
                                });

                    } else {
                        Iv_business_main_image.setVisibility(View.GONE);
                    }


                    if (!Str_business_2_image_path.equalsIgnoreCase(BLANK_IMAGE)) {

                        Iv_business_2_image.setVisibility(View.VISIBLE);

                        Glide.with(Iv_business_2_image.getContext())
                                .load(Str_business_2_image_path)
                                .asBitmap().centerCrop()
                                .crossFade()
                                .into(new BitmapImageViewTarget(Iv_business_2_image) {
                                    @Override
                                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                        super.onResourceReady(bitmap, anim);
                                        Glide.with(Iv_business_2_image
                                                .getContext())
                                                .load(Str_business_2_image_path)
                                                .centerCrop()
                                                .crossFade()
                                                .into(Iv_business_2_image);
                                    }
                                });

                    } else {
                        Iv_business_2_image.setVisibility(View.GONE);
                    }


                    if (!Str_business_3_image_path.equalsIgnoreCase(BLANK_IMAGE)) {

                        Iv_business_3_image.setVisibility(View.VISIBLE);

                        Glide.with(Iv_business_3_image.getContext())
                                .load(Str_business_3_image_path)
                                .asBitmap().centerCrop()
                                .crossFade()
                                .into(new BitmapImageViewTarget(Iv_business_3_image) {
                                    @Override
                                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                        super.onResourceReady(bitmap, anim);
                                        Glide.with(Iv_business_3_image
                                                .getContext())
                                                .load(Str_business_3_image_path)
                                                .centerCrop()
                                                .crossFade()
                                                .into(Iv_business_3_image);
                                    }
                                });

                    } else {
                        Iv_business_3_image.setVisibility(View.GONE);
                    }


                    if (!Str_business_4_image_path.equalsIgnoreCase(BLANK_IMAGE)) {

                        Iv_business_4_image.setVisibility(View.VISIBLE);

                        Glide.with(Iv_business_4_image.getContext())
                                .load(Str_business_4_image_path)
                                .asBitmap().centerCrop()
                                .crossFade()
                                .into(new BitmapImageViewTarget(Iv_business_4_image) {
                                    @Override
                                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                        super.onResourceReady(bitmap, anim);
                                        Glide.with(Iv_business_4_image
                                                .getContext())
                                                .load(Str_business_4_image_path)
                                                .centerCrop()
                                                .crossFade()
                                                .into(Iv_business_4_image);
                                    }
                                });

                    } else {
                        Iv_business_4_image.setVisibility(View.GONE);
                    }


//                    if (Get_Merchant_Business_activation_status.equalsIgnoreCase("Active")) {
//                        Log.e("StrGet_Merc_activation_status is:", "Active");
//
////                        Stv_varify.setVisibility(View.VISIBLE);
////                        Stv_not_varify.setVisibility(View.GONE);
//
//                    } else {
//                        Log.e("StrGet_Merc_activation_status is:", "Deactive");
//
////                        Stv_varify.setVisibility(View.GONE);
////                        Stv_not_varify.setVisibility(View.VISIBLE);
//
//
//                    }


                    Log.e("Get_Merchant_Business_detail_status is:", "1");
                    Log.e("Get_Merchant_Business_Name is:", "" + Get_Merchant_Business_Name);
                    Log.e("Get_Merchant_Business_Short_Name is:", "" + Get_Merchant_Business_Short_Name);
                    Log.e("Get_Merchant_Business_tittle_description is:", "" + Get_Merchant_Business_tittle_description);
                    Log.e("Get_Merchant_Business_ID is:", "" + Get_Merchant_Business_ID);
                    Log.e("Get_Merchant_ID is:", "" + Get_Merchant_ID);
                    Log.e("Get_Merchant_Business_description is:", "" + Get_Merchant_Business_description);
                    Log.e("Get_Merchant_Business_activation_status is:", "" + Get_Merchant_Business_activation_status);
                    Log.e("Str_business_main_image_path is:", "" + Str_business_main_image_path);
                    Log.e("Str_business_2_image_path is:", "" + Str_business_2_image_path);
                    Log.e("Str_business_2_image_path is:", "" + Str_business_2_image_path);
                    Log.e("Str_business_4_image_path is:", "" + Str_business_4_image_path);


                } else {
                    Log.e("Get_Merchant_Business_detail_status is:", "0");
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
//                        Toast.makeText(LoginActivity.this, "Server error try again", Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());

                        new SweetAlertDialog(BusinessCreateActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Server error try again")
                                .show();

//                        TV_volley.setText(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("marchent_id", user_id);
//                params.put("password", user_password);
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

    private void BusinessMainImagePicker() {
        QuickTipDialog = new Dialog(BusinessCreateActivity.this);
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

                new PickerBuilder(BusinessCreateActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri_IMG_main_camera) {
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Civ_edit_profile_image.setImageURI(imageUri);

                                Glide.with(Iv_business_main_image.getContext())
                                        .load(imageUri_IMG_main_camera)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .into(new BitmapImageViewTarget(Iv_business_main_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_business_main_image
                                                        .getContext())
                                                        .load(imageUri_IMG_main_camera)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_business_main_image);
                                            }
                                        });


                                Log.e("Str_business_main_image_path Uri path SELECT_FROM_CAMERA :", "" + imageUri_IMG_main_camera);
                                File f = new File(imageUri_IMG_main_camera.getPath());
                                Str_business_main_image_path = imageUri_IMG_main_camera.getPath();

                                Log.e("Str_business_main_image_path SELECT_FROM_CAMERA :", "" + Str_business_main_image_path);
                                if (!Str_business_main_image_path.equals("")) {
                                    Iv_business_main_image.setVisibility(View.VISIBLE);
                                    Log.e("Str_business_main_image_path File Image availabe :", "" + Str_business_main_image_path);
                                    MainBusiness_Image_File = new File(Str_business_main_image_path);
                                    Log.e("MainBusiness_Image_File File Image:", "" + MainBusiness_Image_File);
//                                    Update_BusinessMainImage_Fast();

                                } else {
                                    Iv_business_main_image.setVisibility(View.GONE);
                                    Log.e("Str_business_main_image_path File Image blank:", "" + Str_business_main_image_path);
                                    MainBusiness_Image_File = new File("MainBusiness_Image_File");
                                    Log.e("MainBusiness_Image_File File Image:", "" + MainBusiness_Image_File);

                                }

                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("business_main_Image")
                        .setImageFolderName("ZoffrBusiness")
                        .withTimeStamp(false)
                        .setCropScreenColor(Color.CYAN)
                        .start();

            }
        });

        RlGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(BusinessCreateActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri_IMG_main_gellery) {
//                                Civ_edit_profile_image.setImageURI(imageUri);
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
                                Glide.with(Iv_business_main_image.getContext())
                                        .load(imageUri_IMG_main_gellery)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .into(new BitmapImageViewTarget(Iv_business_main_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_business_main_image
                                                        .getContext())
                                                        .load(imageUri_IMG_main_gellery)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_business_main_image);
                                            }
                                        });

                                Log.e("Main Business image Uri path SELECT_FROM_GALLERY :", "" + imageUri_IMG_main_gellery);
                                File f = new File(imageUri_IMG_main_gellery.getPath());
                                Str_business_main_image_path = imageUri_IMG_main_gellery.getPath();
                                Log.e("Str_business_main_image_path SELECT_FROM_GALLERY :", "" + Str_business_main_image_path);

                                if (!Str_business_main_image_path.equals("")) {
                                    Iv_business_main_image.setVisibility(View.VISIBLE);
                                    Log.e("Str_business_main_image_path File Image availabe :", "" + Str_business_main_image_path);
                                    MainBusiness_Image_File = new File(Str_business_main_image_path);
                                    Log.e("MainBusiness_Image_File File Image:", "" + MainBusiness_Image_File);
//                                    Update_BusinessMainImage_Fast();

                                } else {
                                    Iv_business_main_image.setVisibility(View.GONE);
                                    Log.e("Str_business_main_image_path File Image blank:", "" + Str_business_main_image_path);
                                    MainBusiness_Image_File = new File("MainBusiness_Image_File");
                                    Log.e("MainBusiness_Image_File File Image:", "" + MainBusiness_Image_File);

                                }


                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("business_main_Image")
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

    private void Update_BusinessMainImage_Fast() {
        Log.e("Update_BusinessMainImage_Fast ********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Str_business_name :" + "" + Str_business_Name + "\n"
                + "Str_business_Short_name :" + "" + Str_business_Short_name + "\n"
                + "Str_business_Tittle_description :" + "" + Str_business_Tittle_description + "\n"
                + "Str_business_main_image_path :" + "" + Str_business_main_image_path + "\n");


        pDialogssssss.setMessage("Updating Profile...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + CREATE_BUSINESS)
                .addMultipartFile("image1", MainBusiness_Image_File)
                .addMultipartParameter("marchent_id", Sh_pre_User_ID)
                .addMultipartParameter("business_name", Sh_pre_User_Business_Name)
                .addMultipartParameter("sub_name", Sh_pre_User_Business_short_Name)
                .addMultipartParameter("description", Sh_pre_User_Business_description)
                .addMultipartParameter("tittel_description", Str_business_Tittle_description)
                .addMultipartParameter("city", Str_business_Name)
                .addMultipartParameter("sub_city", Str_business_Name)
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


                            Get_Merchant_Create_Business_Status = jobjresponse.getString("status");
                            Log.e("Get_Merchant_Create_Business_Status :", "" + Get_Merchant_Create_Business_Status);
                            if (Get_Merchant_Create_Business_Status.equalsIgnoreCase("1")) {
                                Log.e("Get_Merchant_Create_Business_Status jobjresponse is:", "1");

                                Get_Merchant_Create_Business_message = jobjresponse.getString("message");
                                Log.e("StrGet_Merc_message is:", "" + Get_Merchant_Create_Business_message);

                                JSONObject jobjresult = jobjresponse.getJSONObject("result");
                                Get_Merchant_Create_Business_result = jobjresult.toString();
                                Log.e("Get_Merchant_Create_Business_result :", "" + Get_Merchant_Create_Business_result);

                                Get_Merchant_Create_Business_ID = jobjresult.getString("id");
                                Log.e("Get_Merchant_Create_Business_ID is:", "" + Get_Merchant_Create_Business_ID);


                                Get_Merchant_Business_image1 = jobjresult.getString("image1");
                                Log.e("Get_Merchant_Business_image1 is:", "" + Get_Merchant_Business_image1);


                                hideDialog();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (Get_Merchant_Create_Business_Status.equalsIgnoreCase("1")) {
                            Log.e("Get_Merchant_Create_Business_Status jobjresponse is:", "1");
                            Appconstant.editor.putString("BUSINESS_ID", Get_Merchant_Business_ID);
                            Appconstant.editor.commit();
                            hideDialog();
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

    private void BusinessImage_2_Picker() {
        QuickTipDialog = new Dialog(BusinessCreateActivity.this);
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

                new PickerBuilder(BusinessCreateActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri_IMG_2_camera) {
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Civ_edit_profile_image.setImageURI(imageUri);

                                Glide.with(Iv_business_2_image.getContext())
                                        .load(imageUri_IMG_2_camera)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .into(new BitmapImageViewTarget(Iv_business_2_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_business_2_image
                                                        .getContext())
                                                        .load(imageUri_IMG_2_camera)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_business_2_image);
                                            }
                                        });


                                Log.e("Str_business_2_image_path Uri path SELECT_FROM_CAMERA :", "" + imageUri_IMG_2_camera);
                                File f = new File(imageUri_IMG_2_camera.getPath());
                                Str_business_2_image_path = imageUri_IMG_2_camera.getPath();

                                Log.e("Str_business_2_image_path SELECT_FROM_CAMERA :", "" + Str_business_2_image_path);
                                if (!Str_business_2_image_path.equals("")) {
                                    Iv_business_2_image.setVisibility(View.VISIBLE);
                                    Log.e("Str_business_2_image_path File Image availabe :", "" + Str_business_2_image_path);
                                    Business_2_Image_File = new File(Str_business_2_image_path);
                                    Log.e("Business_2_Image_File File Image:", "" + Business_2_Image_File);

//                                    Update_BusinessMainImage2_Fast();
                                } else {
                                    Iv_business_2_image.setVisibility(View.GONE);
                                    Log.e("Str_business_2_image_path File Image blank:", "" + Str_business_2_image_path);
                                    Business_2_Image_File = new File("Business_2_Image_File");
                                    Log.e("Business_2_Image_File File Image:", "" + Business_2_Image_File);

                                }

                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("business_2_Image")
                        .setImageFolderName("ZoffrBusiness")
                        .withTimeStamp(false)
                        .setCropScreenColor(Color.CYAN)
                        .start();

            }
        });

        RlGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(BusinessCreateActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri_IMG_2_gallery) {
//                                Civ_edit_profile_image.setImageURI(imageUri);
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
                                Glide.with(Iv_business_2_image.getContext())
                                        .load(imageUri_IMG_2_gallery)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .into(new BitmapImageViewTarget(Iv_business_2_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_business_2_image
                                                        .getContext())
                                                        .load(imageUri_IMG_2_gallery)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_business_2_image);
                                            }
                                        });

                                Log.e("Business 2 image Uri path SELECT_FROM_GALLERY :", "" + imageUri_IMG_2_gallery);
                                File f = new File(imageUri_IMG_2_gallery.getPath());
                                Str_business_2_image_path = imageUri_IMG_2_gallery.getPath();
                                Log.e("Str_business_2_image_path SELECT_FROM_GALLERY :", "" + Str_business_2_image_path);

                                if (!Str_business_2_image_path.equals("")) {
                                    Iv_business_2_image.setVisibility(View.VISIBLE);
                                    Log.e("Str_business_2_image_path File Image availabe :", "" + Str_business_2_image_path);
                                    Business_2_Image_File = new File(Str_business_2_image_path);
                                    Log.e("Business_2_Image_File File Image:", "" + Business_2_Image_File);
//                                    Update_BusinessMainImage2_Fast();
                                } else {
                                    Iv_business_2_image.setVisibility(View.GONE);
                                    Log.e("Str_business_main_image_path File Image blank:", "" + Str_business_2_image_path);
                                    Business_2_Image_File = new File("Business_2_Image_File");
                                    Log.e("Business_2_Image_File File Image:", "" + Business_2_Image_File);

                                }


                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("business_2_Image")
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

    private void Update_BusinessMainImage2_Fast() {
        Log.e("Update_BusinessMainImage2_Fast ********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Str_business_name :" + "" + Str_business_Name + "\n"
                + "Str_business_Short_name :" + "" + Str_business_Short_name + "\n"
                + "Str_business_Tittle_description :" + "" + Str_business_Tittle_description + "\n"
                + "Str_business_2_image_path :" + "" + Str_business_2_image_path + "\n");


        pDialogssssss.setMessage("Updating Profile...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + CREATE_BUSINESS)
                .addMultipartFile("image2", Business_2_Image_File)
                .addMultipartParameter("marchent_id", Sh_pre_User_ID)
                .addMultipartParameter("business_name", Sh_pre_User_Business_Name)
                .addMultipartParameter("sub_name", Sh_pre_User_Business_short_Name)
                .addMultipartParameter("description", Sh_pre_User_Business_description)
                .addMultipartParameter("tittel_description", Str_business_Tittle_description)
                .addMultipartParameter("city", Str_business_Name)
                .addMultipartParameter("sub_city", Str_business_Name)
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

    private void BusinessImage_3_Picker() {
        QuickTipDialog = new Dialog(BusinessCreateActivity.this);
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

                new PickerBuilder(BusinessCreateActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri_IMG_3) {
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Civ_edit_profile_image.setImageURI(imageUri);

                                Glide.with(Iv_business_3_image.getContext()).load(imageUri_IMG_3)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .into(new BitmapImageViewTarget(Iv_business_3_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_business_3_image
                                                        .getContext())
                                                        .load(imageUri_IMG_3)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_business_3_image);
                                            }
                                        });


                                Log.e("Str_business_3_image_path Uri path SELECT_FROM_CAMERA :", "" + imageUri_IMG_3);
                                File f = new File(imageUri_IMG_3.getPath());
                                Str_business_3_image_path = imageUri_IMG_3.getPath();

                                Log.e("Str_business_3_image_path SELECT_FROM_CAMERA :", "" + Str_business_3_image_path);
                                if (!Str_business_3_image_path.equals("")) {
                                    Iv_business_3_image.setVisibility(View.VISIBLE);
                                    Log.e("Str_business_3_image_path File Image availabe :", "" + Str_business_3_image_path);
                                    Business_3_Image_File = new File(Str_business_3_image_path);
                                    Log.e("Business_3_Image_File File Image:", "" + Business_3_Image_File);
//                                    Update_BusinessMainImage3_Fast();
                                } else {
                                    Iv_business_3_image.setVisibility(View.GONE);
                                    Log.e("Str_business_3_image_path File Image blank:", "" + Str_business_3_image_path);
                                    Business_3_Image_File = new File("Business_3_Image_File");
                                    Log.e("Business_3_Image_File File Image:", "" + Business_3_Image_File);

                                }

                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("business_3_Image")
                        .setImageFolderName("ZoffrBusiness")
                        .withTimeStamp(false)
                        .setCropScreenColor(Color.CYAN)
                        .start();

            }
        });

        RlGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(BusinessCreateActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri_IMG_3) {
//                                Civ_edit_profile_image.setImageURI(imageUri);
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
                                Glide.with(Iv_business_3_image.getContext())
                                        .load(imageUri_IMG_3)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .into(new BitmapImageViewTarget(Iv_business_3_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_business_3_image
                                                        .getContext())
                                                        .load(imageUri_IMG_3)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_business_3_image);
                                            }
                                        });

                                Log.e("Business 3 image Uri path SELECT_FROM_GALLERY :", "" + imageUri_IMG_3);
                                File f = new File(imageUri_IMG_3.getPath());
                                Str_business_3_image_path = imageUri_IMG_3.getPath();
                                Log.e("Str_business_3_image_path SELECT_FROM_GALLERY :", "" + Str_business_3_image_path);

                                if (!Str_business_3_image_path.equals("")) {
                                    Iv_business_3_image.setVisibility(View.VISIBLE);
                                    Log.e("Str_business_3_image_path File Image availabe :", "" + Str_business_3_image_path);
                                    Business_3_Image_File = new File(Str_business_3_image_path);
                                    Log.e("Business_3_Image_File File Image:", "" + Business_3_Image_File);
//                                    Update_BusinessMainImage3_Fast();
                                } else {
                                    Iv_business_3_image.setVisibility(View.GONE);
                                    Log.e("Str_business_3_image_path File Image blank:", "" + Str_business_3_image_path);
                                    Business_3_Image_File = new File("Business_3_Image_File");
                                    Log.e("Business_3_Image_File File Image:", "" + Business_3_Image_File);

                                }


                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("business_3_Image")
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

    private void Update_BusinessMainImage3_Fast() {
        Log.e("Update_BusinessMainImage3_Fast ********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Str_business_name :" + "" + Str_business_Name + "\n"
                + "Str_business_Short_name :" + "" + Str_business_Short_name + "\n"
                + "Str_business_Tittle_description :" + "" + Str_business_Tittle_description + "\n"
                + "Str_business_3_image_path :" + "" + Str_business_3_image_path + "\n");


        pDialogssssss.setMessage("Updating Profile...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + CREATE_BUSINESS)
                .addMultipartFile("image3", Business_3_Image_File)
                .addMultipartParameter("marchent_id", Sh_pre_User_ID)
                .addMultipartParameter("business_name", Sh_pre_User_Business_Name)
                .addMultipartParameter("sub_name", Sh_pre_User_Business_short_Name)
                .addMultipartParameter("description", Sh_pre_User_Business_description)
                .addMultipartParameter("tittel_description", Str_business_Tittle_description)
                .addMultipartParameter("city", Str_business_Name)
                .addMultipartParameter("sub_city", Str_business_Name)
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

    private void BusinessImage_4_Picker() {
        QuickTipDialog = new Dialog(BusinessCreateActivity.this);
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

                new PickerBuilder(BusinessCreateActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri_IMG_4_camera) {
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
//                                Civ_edit_profile_image.setImageURI(imageUri);

                                Glide.with(Iv_business_4_image.getContext()).load(imageUri_IMG_4_camera)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .into(new BitmapImageViewTarget(Iv_business_4_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_business_4_image
                                                        .getContext())
                                                        .load(imageUri_IMG_4_camera)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_business_4_image);
                                            }
                                        });


                                Log.e("Str_business_4_image_path Uri path SELECT_FROM_CAMERA :", "" + imageUri_IMG_4_camera);
                                File f = new File(imageUri_IMG_4_camera.getPath());
                                Str_business_4_image_path = imageUri_IMG_4_camera.getPath();

                                Log.e("Str_business_4_image_path SELECT_FROM_CAMERA :", "" + Str_business_4_image_path);
                                if (!Str_business_4_image_path.equals("")) {
                                    Iv_business_4_image.setVisibility(View.VISIBLE);
                                    Log.e("Str_business_4_image_path File Image availabe :", "" + Str_business_4_image_path);
                                    Business_4_Image_File = new File(Str_business_4_image_path);
                                    Log.e("Business_4_Image_File File Image:", "" + Business_4_Image_File);
//                                    Update_BusinessMainImage4_Fast();
                                } else {
                                    Iv_business_4_image.setVisibility(View.GONE);
                                    Log.e("Str_business_4_image_path File Image blank:", "" + Str_business_4_image_path);
                                    Business_4_Image_File = new File("Business_4_Image_File");
                                    Log.e("Business_4_Image_File File Image:", "" + Business_4_Image_File);

                                }

                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("business_4_Image")
                        .setImageFolderName("ZoffrBusiness")
                        .withTimeStamp(false)
                        .setCropScreenColor(Color.CYAN)
                        .start();

            }
        });

        RlGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerBuilder(BusinessCreateActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                        .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                            @Override
                            public void onImageReceived(final Uri imageUri_IMG_4_gallery) {
//                                Civ_edit_profile_image.setImageURI(imageUri);
//                                Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
                                Glide.with(Iv_business_4_image.getContext())
                                        .load(imageUri_IMG_4_gallery)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .into(new BitmapImageViewTarget(Iv_business_4_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_business_4_image
                                                        .getContext())
                                                        .load(imageUri_IMG_4_gallery)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_business_4_image);
                                            }
                                        });

                                Log.e("Business 4 image Uri path SELECT_FROM_GALLERY :", "" + imageUri_IMG_4_gallery);
                                File f = new File(imageUri_IMG_4_gallery.getPath());
                                Str_business_4_image_path = imageUri_IMG_4_gallery.getPath();
                                Log.e("Str_business_4_image_path SELECT_FROM_GALLERY :", "" + Str_business_4_image_path);

                                if (!Str_business_4_image_path.equals("")) {
                                    Iv_business_4_image.setVisibility(View.VISIBLE);
                                    Log.e("Str_business_4_image_path File Image availabe :", "" + Str_business_4_image_path);
                                    Business_4_Image_File = new File(Str_business_4_image_path);
                                    Log.e("Business_4_Image_File File Image:", "" + Business_4_Image_File);
//                                    Update_BusinessMainImage4_Fast();
                                } else {
                                    Iv_business_4_image.setVisibility(View.GONE);
                                    Log.e("Str_business_4_image_path File Image blank:", "" + Str_business_4_image_path);
                                    Business_4_Image_File = new File("Business_4_Image_File");
                                    Log.e("Business_4_Image_File File Image:", "" + Business_4_Image_File);

                                }


                                QuickTipDialog.dismiss();
                            }
                        })
                        .setImageName("business_4_Image")
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

    private void Update_BusinessMainImage4_Fast() {
        Log.e("Update_BusinessMainImage4_Fast ********************* :", "*************************");

        Log.e("Fsat Networking Update data :", "\n"
                + "Str_business_name :" + "" + Str_business_Name + "\n"
                + "Str_business_Short_name :" + "" + Str_business_Short_name + "\n"
                + "Str_business_Tittle_description :" + "" + Str_business_Tittle_description + "\n"
                + "Str_business_4_image_path :" + "" + Str_business_4_image_path + "\n");


        pDialogssssss.setMessage("Updating Profile...");
        showDialog();


        AndroidNetworking.upload(HttpUrlPath.urlPathMain + CREATE_BUSINESS)
                .addMultipartFile("image4", Business_4_Image_File)
                .addMultipartParameter("marchent_id", Sh_pre_User_ID)
                .addMultipartParameter("business_name", Sh_pre_User_Business_Name)
                .addMultipartParameter("sub_name", Sh_pre_User_Business_short_Name)
                .addMultipartParameter("description", Sh_pre_User_Business_description)
                .addMultipartParameter("tittel_description", Str_business_Tittle_description)
                .addMultipartParameter("city", Str_business_Name)
                .addMultipartParameter("sub_city", Str_business_Name)
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

    private void showDialogs() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(BusinessCreateActivity.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.attention);
        builder.setMessage(R.string.messageperm);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openSettingsApp(BusinessCreateActivity.this);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDialog() {
        if (!pDialogssssss.isShowing())
            pDialogssssss.show();
    }

    private void hideDialog() {
        if (pDialogssssss.isShowing())
            pDialogssssss.dismiss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.e("onBackPressed go main screen :", "OK");
//        Intent GoMainScreen = new Intent(getApplicationContext(), BusinessMainActivity.class);
//        startActivity(GoMainScreen);
//        finish();


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

    private class CreateBusinessJsontask extends AsyncTask<String, Void, String> {

        boolean iserror = false;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            //  loginprogressbar.setVisibility(View.VISIBLE);
            Log.e("******* NOW CreateProfileJsontask WEB SERVICE IS RUNNING *******", "YES");
            pDialogssssss.setMessage("Creating Business...");
            showDialog();


            Log.e("onPreExecute Update data :", "\n"
                    + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                    + "Str_business_Name :" + "" + Str_business_Name + "\n"
                    + "Str_business_Short_name :" + "" + Str_business_Short_name + "\n"
                    + "Str_business_Tittle_description :" + "" + Str_business_Tittle_description + "\n"
                    + "Str_business_main_image_path :" + "" + Str_business_main_image_path + "\n"
                    + "Sh_pre_User_city_ID :" + "" + Sh_pre_User_city_ID + "\n"
                    + "Sh_pre_User_Area_ID :" + "" + Sh_pre_User_Area_ID + "\n"
                    + "Sh_pre_User_city_Name :" + "" + Sh_pre_User_city_Name + "\n"
                    + "Sh_pre_User_Area_Name :" + "" + Sh_pre_User_Area_Name + "\n"
                    + "Dummy_Lat :" + "" + Dummy_Lat + "\n"
                    + "Dummy_Long :" + "" + Dummy_Long + "\n"
                    + "Intent_MAINBUSINESS_CATEGORY_ID :" + "" + Intent_MAINBUSINESS_CATEGORY_ID + "\n"
                    + "Intent_SUBBUSINESS_CATEGORY_ID :" + "" + Intent_SUBBUSINESS_CATEGORY_ID + "\n"
                    + "Intent_CHILDBUSINESS_CATEGORY_ID :" + "" + Intent_CHILDBUSINESS_CATEGORY_ID + "\n"
                    + "Str_business_2_image_path :" + "" + Str_business_2_image_path + "\n"
                    + "Str_business_3_image_path :" + "" + Str_business_3_image_path + "\n"
                    + "Str_business_4_image_path :" + "" + Str_business_4_image_path + "\n");

            if (Str_business_main_image_path.equalsIgnoreCase("")) {
                Str_business_main_image_path = "MainBusinessImage";
            }
            if (Str_business_2_image_path.equalsIgnoreCase("")) {
                Str_business_2_image_path = "SecondBusinessImage";
            }
            if (Str_business_3_image_path.equalsIgnoreCase("")) {
                Str_business_3_image_path = "ThirdBusinessImage";
            }
            if (Str_business_4_image_path.equalsIgnoreCase("")) {
                Str_business_4_image_path = "FourthBusinessImage";
            }


        }

        @Override
        protected String doInBackground(String... params) {
            Log.e("******* NOW doInBackground TASK IS RUNNING *******", "YES");

            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(HttpUrlPath.urlPathMain + CREATE_BUSINESS + "marchent_id=" + Sh_pre_User_ID);
                Log.e("URL Create Business:", "" + HttpUrlPath.urlPathMain + CREATE_BUSINESS + "marchent_id=" + Sh_pre_User_ID +
                        "&business_name=" + Str_business_Name +
                        "&sub_name=" + Str_business_Short_name +
                        "&tittel_description=" + Str_business_Tittle_description +
                        "&description=" + Str_business_Tittle_description +
                        "&lat=" + Dummy_Lat +
                        "&lon=" + Dummy_Long +
                        "&main_category_id=" + Intent_MAINBUSINESS_CATEGORY_ID +
                        "&sub_category_id=" + Intent_SUBBUSINESS_CATEGORY_ID +
                        "&child_category_id=" + Intent_CHILDBUSINESS_CATEGORY_ID +
                        "&image1=" + Str_business_main_image_path +
                        "&image2=" + Str_business_2_image_path +
                        "&image3=" + Str_business_3_image_path +
                        "&image4=" + Str_business_4_image_path);

                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("business_name", new StringBody(Str_business_Name));
                reqEntity.addPart("sub_name", new StringBody(Str_business_Short_name));
                reqEntity.addPart("tittel_description", new StringBody(Str_business_Tittle_description));
                reqEntity.addPart("description", new StringBody(Str_business_Tittle_description));
//                reqEntity.addPart("city", new StringBody(Sh_pre_User_city_ID));
//                reqEntity.addPart("sub_city", new StringBody(Sh_pre_User_Area_ID));
                reqEntity.addPart("lat", new StringBody(Dummy_Lat));
                reqEntity.addPart("lon", new StringBody(Dummy_Long));
                reqEntity.addPart("main_category_id", new StringBody(Intent_MAINBUSINESS_CATEGORY_ID));
                reqEntity.addPart("sub_category_id", new StringBody(Intent_SUBBUSINESS_CATEGORY_ID));
                reqEntity.addPart("child_category_id", new StringBody(Intent_CHILDBUSINESS_CATEGORY_ID));

                if (MainBusiness_Image_File == null) {
                    Log.e("MainBusiness_Image File is :", "NULL");
                    Str_business_main_image_path = "MainBusinessImage";

                } else {
                    /******************* file for post the profile image to the server *******************/
                    MainBusiness_Imagefilebody = new FileBody(MainBusiness_Image_File);
                    reqEntity.addPart("image1", MainBusiness_Imagefilebody);
                    Log.e("MainBusiness_Imagefilebody image :", "" + MainBusiness_Imagefilebody.toString());
                    /******************* file for post the profile image to the server *******************/
                }

                if (Business_2_Image_File == null) {
                    Log.e("Business_2_Image File is :", "NULL");
                    Str_business_2_image_path = "SecondBusinessImage";

                } else {
                    /******************* file for post the profile image to the server *******************/
                    Business_2_Imagefilebody = new FileBody(Business_2_Image_File);
                    reqEntity.addPart("image2", Business_2_Imagefilebody);
                    Log.e("Business_2_Imagefilebody image :", "" + Business_2_Imagefilebody.toString());
                    /******************* file for post the profile image to the server *******************/
                }

                if (Business_3_Image_File == null) {
                    Log.e("Business_3_Image File is :", "NULL");
                    Str_business_3_image_path = "ThirdBusinessImage";

                } else {
                    /******************* file for post the profile image to the server *******************/
                    Business_3_ImageImagefilebody = new FileBody(Business_3_Image_File);
                    reqEntity.addPart("image3", Business_3_ImageImagefilebody);
                    Log.e("Business_3_ImageImagefilebody image :", "" + Business_3_ImageImagefilebody.toString());
                    /******************* file for post the profile image to the server *******************/
                }

                if (Business_4_Image_File == null) {
                    Log.e("Business_4_Image File is :", "NULL");
                    Str_business_4_image_path = "FourthBusinessImage";

                } else {
                    /******************* file for post the profile image to the server *******************/
                    Business_4_Imagefilebody = new FileBody(Business_4_Image_File);
                    reqEntity.addPart("image4", Business_4_Imagefilebody);
                    Log.e("Business_4_Imagefilebody image :", "" + Business_4_Imagefilebody.toString());
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
                Get_Merchant_Create_Business_Status = parentObject.getString("status");
                Log.e("Get_Merchant_Create_Business_Status : ", Get_Merchant_Create_Business_Status);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return Get_Merchant_Create_Business_Status;
        }

        @Override
        protected void onPostExecute(String result1) {
            // TODO Auto-generated method stub
            super.onPostExecute(result1);
//            RL_edit_profile_main_progress.setVisibility(View.GONE);
            hideDialog();
            if (!iserror) {


                if (Get_Merchant_Create_Business_Status.equalsIgnoreCase("1")) {
                    Log.e("onPostExecute Get_Merchant_Create_Business_Status :", "1");


                    Intent GoBusinessMainscreen = new Intent(getApplicationContext(), BusinessMainActivity.class);
                    startActivity(GoBusinessMainscreen);
                    finish();


                } else {

//                    Intent GoGetProfilescreen = new Intent(getApplicationContext(), ProfileGetActivity.class);
//                    startActivity(GoGetProfilescreen);
//                    finish();
                    Intent GoBusinessMainscreen = new Intent(getApplicationContext(), BusinessMainActivity.class);
                    startActivity(GoBusinessMainscreen);
                    finish();


                    Log.e("onPostExecute Str_Get_Status :", "0");
                }


            } else {
                Intent GoBusinessMainscreen = new Intent(getApplicationContext(), BusinessMainActivity.class);
                startActivity(GoBusinessMainscreen);
                finish();
                Log.e("onPostExecute Sub_cat_id size is Zero ", "ooppss");
            }
        }

    }


}
