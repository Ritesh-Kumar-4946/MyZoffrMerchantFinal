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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
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
 * Created by ritesh on 26/7/17.
 */

public class BusinessEditActivity extends ActivityManagePermission {

    private static final String GET_BUSINESS_DETAIL = "get_business_and_package?";
    private static final String EDIT_BUSINESS = "create_business?";
    @BindView(R.id.toolbar_edit_business)
    Toolbar Toolbar_edit_business;
    @BindView(R.id.etCounter_business_name)
    EditText EtCounter_business_name;
    @BindView(R.id.etCounter_business_shortname)
    EditText EtCounter_business_shortname;
    @BindView(R.id.etCounter_business_description)
    EditText EtCounter_business_description;
    @BindView(R.id.iv_business_main_image)
    ImageView Iv_business_main_image;
    @BindView(R.id.iv_business_2_image)
    ImageView Iv_business_2_image;
    @BindView(R.id.iv_business_3_image)
    ImageView Iv_business_3_image;
    @BindView(R.id.iv_business_4_image)
    ImageView Iv_business_4_image;
    @BindView(R.id.tvCounter_business_name)
    CharCountTextView TvCounter_business_name;
    @BindView(R.id.tvCounter_business_shortname)
    CharCountTextView TvCounter_business_shortname;
    @BindView(R.id.tvCounter_business_description)
    CharCountTextView TvCounter_business_description;
    @BindView(R.id.cv_go_business)
    CardView Cv_go_business;
    Dialog QuickTipDialog;
    @BindView(R.id.btn_browse_business_main_image)
    Button Btn_browse_business_main_image;
    @BindView(R.id.btn_browse_business_2_image)
    Button Btn_browse_business_2_image;
    @BindView(R.id.btn_browse_business_3_image)
    Button Btn_browse_business_3_image;
    @BindView(R.id.btn_browse_business_4_image)
    Button Btn_browse_business_4_image;
    File MainBusiness_Image_File, Business_2_Image_File, Business_3_Image_File, Business_4_Image_File;
    FileBody MainBusiness_Imagefilebody,
            Business_2_Imagefilebody,
            Business_3_ImageImagefilebody,
            Business_4_Imagefilebody;
    String Str_business_main_image_path = "",
            Str_business_2_image_path = "",
            Str_business_3_image_path = "",
            Str_business_4_image_path = "",
            Sh_pre_User_ID = "",
            Dummy_Lat = "7000",
            Dummy_Long = "8000",
            Str_business_Name = "",
            Str_business_Short_name = "",
            Str_business_Tittle_description = "",
            Get_Merchant_Create_Business_Status = "",
            Intent_MAINBUSINESS_CATEGORY_ID = "",
            Intent_SUBBUSINESS_CATEGORY_ID = "",
            Intent_CHILDBUSINESS_CATEGORY_ID = "",
            Business_ID_Shar_Pref = "",
            StrGet_Merc_package_plan = "",
            Business_STATUS_Shar_Pref = "";
    String
            STr_Get_BUSINESS_DETAIL_status = "",
            STr_Get_BUSINESS_DETAIL_message = "",
            STr_Get_BUSINESS_DETAIL_Merchant_ID = "",
            STr_Get_BUSINESS_DETAIL_Business_ID = "",
            STr_Get_business_name = "",
            STr_Get_sub_name = "",
            STr_Get_description = "",
            STr_Get_city = "",
            STr_Get_sub_city = "",
            STr_Get_image1 = "",
            STr_Get_image2 = "",
            STr_Get_image3 = "",
            STr_Get_image4 = "",
            STr_Get_Business_Activation_stetus = "";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_edit);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        setSupportActionBar(Toolbar_edit_business);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        Sh_pre_User_ID = Appconstant.sh.getString("id", null);
        Business_ID_Shar_Pref = Appconstant.sh.getString("BusinessID", null);

        Log.e("Business_ID_Shar_Pref :", "" + Business_ID_Shar_Pref);
        Log.e("Business_STATUS_Shar_Pref :", "" + Business_STATUS_Shar_Pref);


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


        TvCounter_business_name.setEditText(EtCounter_business_name);
        TvCounter_business_name.setCharCountChangedListener(new CharCountTextView.CharCountChangedListener() {
            @Override
            public void onCountChanged(int countRemaining, boolean hasExceededLimit) {

//                if (hasExceededLimit) {
//                    Btn_set_business_name.setEnabled(false);
//                    Btn_set_business_name.setBackgroundColor(getResources().getColor(R.color.grey));
//                } else {
//                    Btn_set_business_name.setEnabled(true);
//                    Btn_set_business_name.setBackgroundColor(getResources().getColor(R.color.reddark));
//
//
//                }

            }
        });


        TvCounter_business_shortname.setEditText(EtCounter_business_shortname);
        TvCounter_business_shortname.setCharCountChangedListener(new CharCountTextView.CharCountChangedListener() {
            @Override
            public void onCountChanged(int countRemaining, boolean hasExceededLimit) {

//                if (hasExceededLimit) {
//                    Btn_set_business_shortname.setEnabled(false);
//                    Btn_set_business_shortname.setBackgroundColor(getResources().getColor(R.color.grey));
//                } else {
//                    Btn_set_business_shortname.setEnabled(true);
//                    Btn_set_business_shortname.setBackgroundColor(getResources().getColor(R.color.reddark));
//                }

            }
        });


        TvCounter_business_description.setEditText(EtCounter_business_description);
        TvCounter_business_description.setCharCountChangedListener(new CharCountTextView.CharCountChangedListener() {
            @Override
            public void onCountChanged(int countRemaining, boolean hasExceededLimit) {

//                if (hasExceededLimit) {
//                    Btn_set_business_description.setEnabled(false);
//                    Btn_set_business_description.setBackgroundColor(getResources().getColor(R.color.grey));
//                } else {
//                    Btn_set_business_description.setEnabled(true);
//                    Btn_set_business_description.setBackgroundColor(getResources().getColor(R.color.reddark));
//                }

            }
        });


        GET_Business_Detail_Fast();


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
//                    iserror = true;

                    Log.e("Str_business_Name Blank:", "" + Str_business_Name);

//                    YoYo.with(Techniques.Tada)
//                            .duration(700)
//                            .playOn(EtCounter_business_name);
//
//                    Toast.makeText(getApplicationContext(),
//                            "Please enter your Business Name", Toast.LENGTH_SHORT).show();

                } else if (Str_business_Short_name.equals("")) {
//                    iserror = true;

                    Log.e("Str_business_Short_name Blank:", "" + Str_business_Short_name);

//                    YoYo.with(Techniques.Tada)
//                            .duration(700)
//                            .playOn(EtCounter_business_shortname);
//
//                    Toast.makeText(getApplicationContext(),
//                            "Please enter your Business Short Name", Toast.LENGTH_SHORT).show();

                } else if (Str_business_Tittle_description.equals("")) {
//                    iserror = true;

                    Log.e("Str_business_Tittle_description Blank:", "" + Str_business_Tittle_description);

//                    YoYo.with(Techniques.Tada)
//                            .duration(700)
//                            .playOn(EtCounter_business_description);
//
//                    Toast.makeText(getApplicationContext(),
//                            "Please enter your Business Description", Toast.LENGTH_SHORT).show();

                } else if (Str_business_main_image_path.equals("")) {
//                    iserror = true;

                    Log.e("Str_business_main_image_path Blank:", "" + Str_business_main_image_path);

//                    YoYo.with(Techniques.Tada)
//                            .duration(700)
//                            .playOn(Cv_business_main_image);
//
//                    Toast.makeText(getApplicationContext(),
//                            "Please select your Business Front Image", Toast.LENGTH_SHORT).show();

                } else if (Str_business_2_image_path.equals("")) {
//                    iserror = true;

                    Log.e("Str_business_2_image_path Blank:", "" + Str_business_2_image_path);

//                    YoYo.with(Techniques.Tada)
//                            .duration(700)
//                            .playOn(Cv_business_2_image);
//
//                    Toast.makeText(getApplicationContext(),
//                            "Please select your Business Second Image", Toast.LENGTH_SHORT).show();

                } else if (Str_business_3_image_path.equals("")) {
//                    iserror = true;

                    Log.e("Str_business_3_image_path Blank:", "" + Str_business_3_image_path);

//                    YoYo.with(Techniques.Tada)
//                            .duration(700)
//                            .playOn(Cv_business_3_image);
//
//                    Toast.makeText(getApplicationContext(),
//                            "Please select your Business Third Image", Toast.LENGTH_SHORT).show();

                } else if (Str_business_4_image_path.equals("")) {
//                    iserror = true;

                    Log.e("Str_business_4_image_path Blank:", "" + Str_business_4_image_path);

//                    YoYo.with(Techniques.Tada)
//                            .duration(700)
//                            .playOn(Cv_business_4_image);
//
//                    Toast.makeText(getApplicationContext(),
//                            "Please select your Business Fourth Image", Toast.LENGTH_SHORT).show();

                }
                if (!iserror) {
                    Log.e("No Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                    Toast.makeText(getApplicationContext(), "No Error", Toast.LENGTH_SHORT).show();


                    EditBusinessJsontask task = new EditBusinessJsontask();
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

    private void GET_Business_Detail_Fast() {
        final String user_id = Sh_pre_User_ID;

        Log.e("Volley GET_Business_Detail_Fast Data :", ""
                        + "\n" + "user_id :" + "" + user_id
//                + "\n" + "user_password" + "" + user_password
        );

        // Tag used to cancel the request
        String tag_string_req = "req_registration";
        pDialog.setMessage("Please Wait Fetching Detail.....");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlPath.urlPathMain
                + GET_BUSINESS_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                        Toast.makeText(MainVolleyActivity.this,response,Toast.LENGTH_LONG).show();

                Log.e("Response :", "" + response);
                try {
                    JSONObject jobjresponse = new JSONObject(response);
                    Log.e("jobjresponse :", "" + jobjresponse);

                    STr_Get_BUSINESS_DETAIL_status = jobjresponse.getString("status");
                    Log.e("STr_Get_BUSINESS_DETAIL_status :", "" + STr_Get_BUSINESS_DETAIL_status);

                    STr_Get_BUSINESS_DETAIL_message = jobjresponse.getString("message");
                    Log.e("STr_Get_BUSINESS_DETAIL_message :", "" + STr_Get_BUSINESS_DETAIL_message);

                    if (STr_Get_BUSINESS_DETAIL_status.equalsIgnoreCase("1")) {
                        Log.e("STr_Get_BUSINESS_DETAIL_status jobjresponse is:", "1");
                        JSONObject jobjGetresult = jobjresponse.getJSONObject("result");
                        STr_Get_BUSINESS_DETAIL_Merchant_ID = jobjGetresult.getString("marchent_id");
                        STr_Get_BUSINESS_DETAIL_Business_ID = jobjGetresult.getString("id");
                        STr_Get_business_name = jobjGetresult.getString("business_name");
                        STr_Get_sub_name = jobjGetresult.getString("sub_name");
                        STr_Get_description = jobjGetresult.getString("description");
                        STr_Get_Business_Activation_stetus = jobjGetresult.getString("stetus");
                        STr_Get_city = jobjGetresult.getString("city");
                        STr_Get_sub_city = jobjGetresult.getString("sub_city");
                        StrGet_Merc_package_plan = jobjGetresult.getString("package_plan");
                        STr_Get_image1 = jobjGetresult.getString("image1");
                        STr_Get_image2 = jobjGetresult.getString("image2");
                        STr_Get_image3 = jobjGetresult.getString("image3");
                        STr_Get_image4 = jobjGetresult.getString("image4");


                        if (STr_Get_BUSINESS_DETAIL_status.equalsIgnoreCase("1")) {
                            Log.e("Get_Merchant_Business_detail_status is:", "1");

                            Log.e("STr_Get_stetus ", "" + STr_Get_Business_Activation_stetus);

                            EtCounter_business_name.setText(STr_Get_business_name);
                            EtCounter_business_shortname.setText(STr_Get_sub_name);
                            EtCounter_business_description.setText(STr_Get_description);

                            Iv_business_main_image.setVisibility(View.VISIBLE);
                            Iv_business_2_image.setVisibility(View.VISIBLE);
                            Iv_business_3_image.setVisibility(View.VISIBLE);
                            Iv_business_4_image.setVisibility(View.VISIBLE);


                            Glide.with(Iv_business_main_image.getContext())
                                    .load(STr_Get_image1)
                                    .asBitmap().centerCrop()
                                    .crossFade()
                                    .into(new BitmapImageViewTarget(Iv_business_main_image) {
                                        @Override
                                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                            super.onResourceReady(bitmap, anim);
                                            Glide.with(Iv_business_main_image
                                                    .getContext())
                                                    .load(STr_Get_image1)
                                                    .centerCrop()
                                                    .crossFade()
                                                    .into(Iv_business_main_image);
                                        }
                                    });


                            Glide.with(Iv_business_2_image.getContext())
                                    .load(STr_Get_image2)
                                    .asBitmap().centerCrop()
                                    .crossFade()
                                    .into(new BitmapImageViewTarget(Iv_business_2_image) {
                                        @Override
                                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                            super.onResourceReady(bitmap, anim);
                                            Glide.with(Iv_business_2_image
                                                    .getContext())
                                                    .load(STr_Get_image2)
                                                    .centerCrop()
                                                    .crossFade()
                                                    .into(Iv_business_2_image);
                                        }
                                    });


                            Glide.with(Iv_business_3_image.getContext())
                                    .load(STr_Get_image3)
                                    .asBitmap().centerCrop()
                                    .crossFade()
                                    .into(new BitmapImageViewTarget(Iv_business_3_image) {
                                        @Override
                                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                            super.onResourceReady(bitmap, anim);
                                            Glide.with(Iv_business_3_image
                                                    .getContext())
                                                    .load(STr_Get_image3)
                                                    .centerCrop()
                                                    .crossFade()
                                                    .into(Iv_business_3_image);
                                        }
                                    });


                            Glide.with(Iv_business_4_image.getContext())
                                    .load(STr_Get_image4)
                                    .asBitmap().centerCrop()
                                    .crossFade()
                                    .into(new BitmapImageViewTarget(Iv_business_4_image) {
                                        @Override
                                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                            super.onResourceReady(bitmap, anim);
                                            Glide.with(Iv_business_4_image
                                                    .getContext())
                                                    .load(STr_Get_image4)
                                                    .centerCrop()
                                                    .crossFade()
                                                    .into(Iv_business_4_image);
                                        }
                                    });


                        } else {
                            Log.e("Get_Merchant_Business_detail_status is:", "0");
                        }


                        hideDialog();
                    }


                } catch (
                        JSONException e)

                {
                    hideDialog();
                    e.printStackTrace();
                }

//                Log.e("response :", "" + response);


            }
        },
                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
//                        Toast.makeText(LoginActivity.this, "Server error try again", Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());

                        new SweetAlertDialog(BusinessEditActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Server error try again")
                                .show();

//                        TV_volley.setText(error.toString());
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("marchent_id", user_id);
//                params.put("password", user_password);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void BusinessMainImagePicker() {
        QuickTipDialog = new Dialog(BusinessEditActivity.this);
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

                new PickerBuilder(BusinessEditActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
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

                new PickerBuilder(BusinessEditActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
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

    private void BusinessImage_2_Picker() {
        QuickTipDialog = new Dialog(BusinessEditActivity.this);
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

                new PickerBuilder(BusinessEditActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
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

                new PickerBuilder(BusinessEditActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
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

    private void BusinessImage_3_Picker() {
        QuickTipDialog = new Dialog(BusinessEditActivity.this);
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

                new PickerBuilder(BusinessEditActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
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

                new PickerBuilder(BusinessEditActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
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

    private void BusinessImage_4_Picker() {
        QuickTipDialog = new Dialog(BusinessEditActivity.this);
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

                new PickerBuilder(BusinessEditActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
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

                new PickerBuilder(BusinessEditActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
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

    private void showDialogs() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(BusinessEditActivity.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.attention);
        builder.setMessage(R.string.messageperm);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openSettingsApp(BusinessEditActivity.this);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {

        Log.e("onBackPressed go main screen :", "OK");
        Intent GoMainScreen = new Intent(BusinessEditActivity.this, BusinessMainActivity.class);
        startActivity(GoMainScreen);
        finish();


    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private class EditBusinessJsontask extends AsyncTask<String, Void, String> {

        boolean iserror = false;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            //  loginprogressbar.setVisibility(View.VISIBLE);
            Log.e("******* NOW Update Business IS RUNNING *******", "YES");
            pDialog.setMessage("Updating Business...");
            showDialog();


            Log.e("onPreExecute Update data :", "\n"
                    + "Merchant_ID :" + "" + Sh_pre_User_ID + "\n"
                    + "Str_business_Name :" + "" + Str_business_Name + "\n"
                    + "Str_business_Short_name :" + "" + Str_business_Short_name + "\n"
                    + "Str_business_Tittle_description :" + "" + Str_business_Tittle_description + "\n"
                    + "Str_business_main_image_path :" + "" + Str_business_main_image_path + "\n"
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
                HttpPost post = new HttpPost(HttpUrlPath.urlPathMain + EDIT_BUSINESS + "marchent_id=" + Sh_pre_User_ID);
                Log.e("URL Create Business:", "" + HttpUrlPath.urlPathMain + EDIT_BUSINESS + "marchent_id=" + Sh_pre_User_ID +
                        "&business_name=" + Str_business_Name +
                        "&sub_name=" + Str_business_Short_name +
                        "&tittel_description=" + Str_business_Tittle_description +
                        "&description=" + Str_business_Tittle_description +
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
//                reqEntity.addPart("lat", new StringBody(Dummy_Lat));
//                reqEntity.addPart("lon", new StringBody(Dummy_Long));
//                reqEntity.addPart("main_category_id", new StringBody(Intent_MAINBUSINESS_CATEGORY_ID));
//                reqEntity.addPart("sub_category_id", new StringBody(Intent_SUBBUSINESS_CATEGORY_ID));
//                reqEntity.addPart("child_category_id", new StringBody(Intent_CHILDBUSINESS_CATEGORY_ID));

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
                Intent GoBusinessMainscreen = new Intent(getApplicationContext(), BusinessMainActivity.class);
                startActivity(GoBusinessMainscreen);
                finish();

            } else {
                Intent GoBusinessMainscreen = new Intent(getApplicationContext(), BusinessMainActivity.class);
                startActivity(GoBusinessMainscreen);
                finish();
                Log.e("onPostExecute Sub_cat_id size is Zero ", "ooppss");
            }
        }

    }


}
