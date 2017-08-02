package in.myzoffrmerchants.main_activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.myzoffrmerchants.Constant.Appconstant;
import in.myzoffrmerchants.Constant.HttpUrlPath;
import in.myzoffrmerchants.R;

/**
 * Created by ritesh on 24/7/17.
 */

public class ProfileGetActivity extends AppCompatActivity {

    private static final int TIME_DELAY = 2000;
    private static final String GET_MERCHANT_DETAIL = "get_profile?";
    private static long back_pressed;


    /*https://github.com/amitshekhariitbhu/Fast-Android-Networking#uploading-a-file-to-server*/
    /*https://github.com/amitshekhariitbhu/Fast-Android-Networking#uploading-a-file-to-server*/
    /*https://github.com/amitshekhariitbhu/Fast-Android-Networking#uploading-a-file-to-server*/
    /*https://github.com/amitshekhariitbhu/Fast-Android-Networking#uploading-a-file-to-server*/
    @BindView(R.id.toolbar_get_profile)
    Toolbar Toolbar_get_profile;
    @BindView(R.id.rl_edit_profile_fab_btn)
    RelativeLayout Rl_edit_profile_fab_btn;
    @BindView(R.id.tv_get_merchant_profile_name)
    TextView Tv_get_merchant_profile_name;
    @BindView(R.id.tv_get_merchant_email)
    TextView Tv_get_merchant_email;
    @BindView(R.id.tv_get_merchant_mobile)
    TextView Tv_get_merchant_mobile;
    @BindView(R.id.tv_get_main_city)
    TextView Tv_get_main_city;
    @BindView(R.id.tv_get_area)
    TextView Tv_get_area;
    @BindView(R.id.tv_get_residential_address)
    TextView Tv_get_residential_address;
    @BindView(R.id.tv_get_office_address)
    TextView Tv_get_office_address;
    @BindView(R.id.civ_get_profile_image)
    CircleImageView Civ_get_profile_image;
    @BindView(R.id.iv_get_pan_card)
    ImageView Iv_get_pan_card;
    @BindView(R.id.iv_get_adhar_voter_image)
    ImageView Iv_get_adhar_voter_image;
    @BindView(R.id.iv_get_qualification)
    ImageView Iv_get_qualification;
    @BindView(R.id.iv_get_latest_address_proof)
    ImageView Iv_get_latest_address_proof;
    @BindView(R.id.cv_go_business_screen_btn)
    CardView Cv_go_business_screen_btn;
    String Sh_pre_User_ID = "",
            Sh_pre_Merchant_email = "",
            Sh_pre_Merchant_username = "",
            Sh_pre_Merchant_mobile = "",
            Str_Get_Profile_Status = "",
            Str_Get_message = "",
            Str_Get_result = "",
            Str_Get_User_ID = "",
            Str_Get_user_name = "",
            Str_Get_user_name_text = "Name : ",
            Str_Get_phone_number = "",
            Str_Get_phone_number_text = "Mobile : ",
            Str_Get_emailID = "",
            Str_Get_emailID_text = "Email ID : ",
            Str_Get_work_address = "",
            Str_Get_home_address = "",
            Str_Get_work_location = "",
            Str_Get_work_sub_location = "",
            Str_Get_profile_image = "",
            Str_Get_Pan_Card_image = "",
            Str_Get_Highest_Qualification_image = "",
            Str_Get_aadhaar_voter_image = "",
            Str_Get_latest_address_proof_image = "",
            Str_Get_business_id = "";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_get);
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

//        setSupportActionBar(Toolbar_get_profile);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Get_MerchantDetail_Fast();

        Cv_go_business_screen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Str_Get_business_id.equalsIgnoreCase("0")) {
                    Log.e("Str_Get_business_id not found so go to create business screen :", "0");
                    Intent GoCreateBusinessScreen = new Intent(getApplicationContext(), MainCategoryActivity.class);
                    startActivity(GoCreateBusinessScreen);
                    finish();

                } else {
                    Log.e("Str_Get_business_id Found so go to Business dash board screen:", "" + Str_Get_business_id);
                    Intent GoBusinessDashBoardScreen = new Intent(getApplicationContext(), BusinessMainActivity.class);
                    startActivity(GoBusinessDashBoardScreen);
                    finish();
                }
            }
        });

        Rl_edit_profile_fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoBusinessScreen = new Intent(getApplicationContext(), ProfileEditActivity.class);
                startActivity(GoBusinessScreen);
                finish();
//                Log.e("Edit Click:", "OK");

            }
        });

    }


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
                                Str_Get_work_address = jobjresult.getString("work_address");
                                Str_Get_home_address = jobjresult.getString("home_address");
                                Str_Get_work_location = jobjresult.getString("work_location");
                                Str_Get_work_sub_location = jobjresult.getString("work_sub_location");
                                Str_Get_profile_image = jobjresult.getString("marchant_img");
                                Str_Get_Pan_Card_image = jobjresult.getString("pan_card");
                                Str_Get_latest_address_proof_image = jobjresult.getString("voter_id");
                                Str_Get_aadhaar_voter_image = jobjresult.getString("adhar_card");
                                Str_Get_Highest_Qualification_image = jobjresult.getString("qualification");
                                Str_Get_business_id = jobjresult.getString("business_id");

                                Log.e("Get Profile business_id :", "" + Str_Get_business_id);

                                Tv_get_merchant_profile_name.setText(Str_Get_user_name_text + Str_Get_user_name);
                                Tv_get_merchant_email.setText(Str_Get_emailID_text + Str_Get_emailID);
                                Tv_get_merchant_mobile.setText(Str_Get_phone_number_text + Str_Get_phone_number);
                                Tv_get_main_city.setText(Str_Get_work_location);
                                Tv_get_area.setText(Str_Get_work_sub_location);
                                Tv_get_residential_address.setText(Str_Get_home_address);
                                Tv_get_office_address.setText(Str_Get_work_address);


                                Glide.with(Civ_get_profile_image.getContext())
                                        .load(Str_Get_profile_image)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Civ_get_profile_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Civ_get_profile_image
                                                        .getContext())
                                                        .load(Str_Get_profile_image)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Civ_get_profile_image);
                                            }
                                        });


                                Glide.with(Iv_get_pan_card.getContext())
                                        .load(Str_Get_Pan_Card_image)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_get_pan_card) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_get_pan_card
                                                        .getContext())
                                                        .load(Str_Get_Pan_Card_image)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_get_pan_card);
                                            }
                                        });


                                Glide.with(Iv_get_adhar_voter_image.getContext())
                                        .load(Str_Get_aadhaar_voter_image)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_get_adhar_voter_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_get_adhar_voter_image
                                                        .getContext())
                                                        .load(Str_Get_aadhaar_voter_image)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_get_adhar_voter_image);
                                            }
                                        });


                                Glide.with(Iv_get_qualification.getContext())
                                        .load(Str_Get_Highest_Qualification_image)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_get_qualification) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_get_qualification
                                                        .getContext())
                                                        .load(Str_Get_Highest_Qualification_image)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_get_qualification);
                                            }
                                        });


                                Glide.with(Iv_get_latest_address_proof.getContext())
                                        .load(Str_Get_latest_address_proof_image)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(Iv_get_latest_address_proof) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_get_latest_address_proof
                                                        .getContext())
                                                        .load(Str_Get_latest_address_proof_image)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_get_latest_address_proof);
                                            }
                                        });


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


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }


    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }


}
