package in.myzoffrmerchants.main_activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fujiyuu75.sequent.Animation;
import com.fujiyuu75.sequent.Sequent;
import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.myzoffrmerchants.Constant.Appconstant;
import in.myzoffrmerchants.Constant.HttpUrlPath;
import in.myzoffrmerchants.R;

/**
 * Created by ritesh on 10/7/17.
 */

public class SplashActivity extends AppCompatActivity {

    private static final String GET_MERCHANT_DETAIL = "get_profile?";
    @BindView(R.id.iv_zoffr_logo)
    ImageView IV_zoffr_logo;
    String Str_Get_Business_Status;
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
            Str_Get_work_address = "",
            Str_Get_home_address = "";
    @BindView(R.id.layout)
    LinearLayout layout;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());

        YoYo.with(Techniques.FadeIn).duration(1500)
                .interpolate(new AccelerateDecelerateInterpolator())
                .listen(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Toast.makeText(SplashActivity.this, "canceled", Toast.LENGTH_SHORT).show();
                    }
                })
                .playOn(IV_zoffr_logo);

        Sequent.origin(layout).anim(getApplicationContext(), Animation.BOUNCE_IN).start();
//        Sequent.origin(layout).anim(getApplicationContext(), Animation.FADE_IN).start();
//        Sequent.origin(layout).anim(getApplicationContext(), Animation.FADE_IN_DOWN).start();
//        Sequent.origin(layout).anim(getApplicationContext(), Animation.FADE_IN_LEFT).start();
//        Sequent.origin(layout).anim(getApplicationContext(), Animation.FADE_IN_RIGHT).start();
//        Sequent.origin(layout).anim(getApplicationContext(), Animation.FADE_IN_UP).start();
//        Sequent.origin(layout).anim(getApplicationContext(), Animation.ROTATE_IN).start();
        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        Sh_pre_User_ID = Appconstant.sh.getString("id", null);
//        Sh_pre_Merchant_email = Appconstant.sh.getString("email", null);
//        Sh_pre_Merchant_username = Appconstant.sh.getString("username", null);
//        Sh_pre_Merchant_mobile = Appconstant.sh.getString("mobile", null);
//        Log.e("Merchant_ID from SharedPref :", "" + Sh_pre_User_ID);
//        Log.e("Merchant_email from SharedPref :", "" + Sh_pre_Merchant_email);
//        Log.e("Merchant_username from SharedPref :", "" + Sh_pre_Merchant_username);
//        Log.e("Merchant_mobile from SharedPref :", "" + Sh_pre_Merchant_mobile);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


//        Str_Get_Business_Status = Appconstant.sh.getString("BusinessStatus", null);
//        Log.e("Business_Status from SharedPref :", "" + Str_Get_Business_Status);


        // here initializing the shared preference
        Appconstant.sh = getSharedPreferences("myprefe", 0);
        Appconstant.editor = Appconstant.sh.edit();

        // check here if user is login or not
        Appconstant.str_login_test = Appconstant.sh.getString("loginTest", null);
        Appconstant.str_login_usertype = Appconstant.sh.getString("usertype", null);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }


//        Get_MerchantDetail_Fast();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                /* if user login test is true on oncreate then redirect the user to result page */

//                Intent Gomainscreen = new Intent(getApplicationContext(), GradientBackgroundExampleActivity.class);
////                Intent Gomainscreen = new Intent(getApplicationContext(), LoginSignupActivity.class);
//                startActivity(Gomainscreen);
//                finish();


                if (Appconstant.str_login_test != null
                        && !Appconstant.str_login_test.toString().trim().equals("")) {
                    Log.e("Login detail found :", " Now Check Business Create Status");


                    Get_MerchantDetail_Fast();

//                    if (Str_Get_Business_Status.equalsIgnoreCase("YES")) {
//                        Log.e("Business_Status detail found :", " YES");
//                        Intent Gomainscreen = new Intent(getApplicationContext(), BusinessMainActivity.class);
//                        startActivity(Gomainscreen);
//                        finish();
//
//
//                    } else if (Str_Get_Business_Status.equalsIgnoreCase("NO")) {
//                        Log.e("Business_Status detail NOT found :", " NO");
//                        Intent Gomainscreen = new Intent(getApplicationContext(), MainCategoryActivity.class);
//                        startActivity(Gomainscreen);
//                        finish();
//                    }


                }

//
                /* if user login test is false on oncreate then redirect the user to login & registration page */
                else {

                    Log.e("Login detail not found :", "Go to Login Screen");
                    Intent Gologincreen = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(Gologincreen);
                    finish();

                }
            }

        }, 3000);


    }


    private void Get_MerchantDetail_Fast() {

        Log.e("Get_MerchantDetail_Fast ********************* :", "*************************");

        Log.e("merchantID :", "" + Sh_pre_User_ID);
        String tag_string_req = "req_login";
        pDialog.setMessage("Checking in ...");
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

//                                tv_status.setText(StrGet_Status);

                                Str_Get_result = jobjresponse.getString("result");
                                Log.e("Str_Get_result :", "" + Str_Get_result);

                                JSONObject jobjresult = jobjresponse.getJSONObject("result");
                                Log.e("jobjresult :", "" + jobjresult.toString());


                                Str_Get_User_ID = jobjresult.getString("id");
                                Str_Get_user_name = jobjresult.getString("username");
                                Str_Get_phone_number = jobjresult.getString("mobile");
                                Str_Get_emailID = jobjresult.getString("email");
                                Str_Get_work_address = jobjresult.getString("work_address");
                                Str_Get_home_address = jobjresult.getString("home_address");

                                if (!Str_Get_work_address.equalsIgnoreCase("")) {
                                    Log.e("Str_Get_work_address is Found :", "" + Str_Get_work_address);
                                    Intent GoGetProfilecreen = new Intent(getApplicationContext(), ProfileGetActivity.class);
                                    startActivity(GoGetProfilecreen);
                                    finish();

                                } else {
                                    Log.e("Str_Get_work_address is:", "BLANK So Go to Create Profile Screen");
                                    Intent GoCreateProfilecreen = new Intent(getApplicationContext(), ProfileCreateActivity.class);
                                    startActivity(GoCreateProfilecreen);
                                    finish();
                                }

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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private class LongOperation extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            Log.e("Check Detail :", "OK");
        }

        @Override
        protected String doInBackground(String... params) {
            //some heavy processing resulting in a Data String
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "whatever result you have";
        }

        @Override
        protected void onPostExecute(String result) {


//            Intent i = new Intent(SplashScreen.this, MainActivity.class);
//            i.putExtra("data", result);
//            startActivity(i);
//            finish();
        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


}