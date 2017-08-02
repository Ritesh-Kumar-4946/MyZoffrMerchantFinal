package in.myzoffrmerchants.main_activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.myzoffrmerchants.Constant.Appconstant;
import in.myzoffrmerchants.Constant.HttpUrlPath;
import in.myzoffrmerchants.R;

/**
 * Created by ritesh on 10/7/17.
 */

public class LoginActivity extends AppCompatActivity {


    private static final String LOGIN_URL = "login?";
    private static final int TIME_DELAY = 2000;

    /*please commit directory*/

//    @BindView(R.id.til_user_login_email_id)
//    TextInputLayout Til_user_login_email_id;
//
//    @BindView(R.id.til_user_login_password)
//    TextInputLayout Til_user_login_password;


    //    @BindView(R.id.tv_counter_user_login_password)
//    TextView TV_counter_user_login_password;
    private static long back_pressed;
    @BindView(R.id.edt_login_user_email)
    EditText Edt_login_user_email;
    @BindView(R.id.edt_login_password)
    EditText Edt_login_password;
    @BindView(R.id.tv_forgetpassword)
    TextView Tv_forgetpassword;
    //
    @BindView(R.id.tv_signup_below)
    TextView Tv_signup_below;
    @BindView(R.id.cv_login_email)
    CardView Cv_login_email;
    @BindView(R.id.rl_signup_below)
    RelativeLayout Rl_signup_below;
    @BindView(R.id.rl_forgetpassword)
    RelativeLayout Rl_forgetpassword;
    String strPut_emailid = "",
            strPut_password = "",
            StrGet_status = "",
            StrGet_message = "",
            StrGet_result = "",
            StrGet_work_address = "",
            StrGet_home_address = "",
            StrGet_work_location = "",
            StrGet_work_sub_location = "",
            StrGet_user_id = "",
            StrGet_user_name = "",
            StrGet_user_mobile = "",
            StrGet_user_email = "",
            StrGet_user_active_status = "",
            StrGet_business_status = "",
            StrGet_business_id = "";
    String Empty_String = "http://whatsapphindistatus.com/ZOF/uploads/images/Empty";
    private ProgressDialog pDialog;

    public final static boolean isValidEmaill(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        ButterKnife.bind(this);


        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

//        hideSoftKeyboard(this);


        Cv_login_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean iserror = false;

                strPut_emailid = Edt_login_user_email.getText().toString().trim();
                strPut_password = Edt_login_password.getText().toString().trim();

                Log.e(" Sign Up Fields data :", "\n"
                        + "strPut_emailid :" + "" + strPut_emailid + "\n"
                        + "strPut_password :" + "" + strPut_password + "\n");

                if (strPut_emailid.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_login_user_email);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Email Id", Toast.LENGTH_SHORT).show();


                } else if (!isValidEmaill(strPut_emailid)) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    //	emailedit.requestFocus();
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_login_user_email);
                    /**************** End Animation ****************/
                    Toast.makeText(getApplicationContext(),
                            "Please enter valid email address.", Toast.LENGTH_SHORT).show();


                } else if (strPut_password.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_login_password);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Password", Toast.LENGTH_SHORT).show();

                } else if (strPut_password.length() < 5) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_login_password);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Please enter at least 5 character in password.",
                            Toast.LENGTH_SHORT).show();


                } else if (strPut_password.contains(" ")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_login_password);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Space is not allowed in password.",
                            Toast.LENGTH_SHORT).show();


                }
                if (!iserror) {
                    Log.e("No Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                    userLogin();


                }

            }
        });


        Rl_signup_below.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Gosignupscreen = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(Gosignupscreen);
                finish();
            }
        });


        Rl_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Goforgotscreen = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(Goforgotscreen);
            }
        });


        Edt_login_user_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Edt_login_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


    }

    private void userLogin() {
        final String user_email_id = strPut_emailid;
        final String user_password = strPut_password;

        Log.e("Volley userLogin Data :", ""
                + "\n" + "user_email_id" + "" + user_email_id
                + "\n" + "user_password" + "" + user_password
        );

        // Tag used to cancel the request
        String tag_string_req = "req_registration";
        pDialog.setMessage("Login.....");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlPath.urlPathMain + LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(MainVolleyActivity.this,response,Toast.LENGTH_LONG).show();
                        hideDialog();
                        Log.e("Response :", "" + response);
                        try {
                            JSONObject jobjresponse = new JSONObject(response);
                            Log.e("jobjresponse :", "" + jobjresponse);
                            Log.e("response :", "" + response);

                            StrGet_status = jobjresponse.getString("status");
                            StrGet_message = jobjresponse.getString("message");
                            Log.e("StrGet_message is:", "" + StrGet_message);

                            if (StrGet_status.equalsIgnoreCase("1")) {
                                Log.e("StrGet_status is:", "1");
                                JSONObject jobjresult = jobjresponse.getJSONObject("result");
                                Log.e("jobjresult :", "" + jobjresult);

                                StrGet_user_id = jobjresult.getString("id");
                                StrGet_user_name = jobjresult.getString("username");
                                StrGet_user_mobile = jobjresult.getString("mobile");
                                StrGet_user_email = jobjresult.getString("email");
                                StrGet_user_active_status = jobjresult.getString("status");
                                StrGet_business_status = jobjresult.getString("business_status");
                                StrGet_business_id = jobjresult.getString("business_id");
                                StrGet_home_address = jobjresult.getString("home_address");
                                StrGet_work_address = jobjresult.getString("work_address");

                                Log.e("StrGet_status is:", "1");
                                Log.e("StrGet_user_id is:", "" + StrGet_user_id);
                                Log.e("StrGet_user_name is:", "" + StrGet_user_name);
                                Log.e("StrGet_user_mobile is:", "" + StrGet_user_mobile);
                                Log.e("StrGet_user_email is:", "" + StrGet_user_email);
                                Log.e("StrGet_user_active_status is:", "" + StrGet_user_active_status);

                                Appconstant.editor.putString("id", StrGet_user_id);
                                Appconstant.editor.putString("email", StrGet_user_email);
                                Appconstant.editor.putString("username", StrGet_user_name);
                                Appconstant.editor.putString("mobile", StrGet_user_mobile);
                                Appconstant.editor.putString("loginTest", "true");
                                Appconstant.editor.commit();


                                if (!StrGet_business_id.equalsIgnoreCase("0")) {
                                    Log.e("StrGet_business_id is:", "" + StrGet_business_id);
                                } else {
                                    Log.e("StrGet_business_id is:", "" + "0");
                                }

                                if (!StrGet_work_address.equalsIgnoreCase("")) {
                                    Log.e("StrGet_work_address is:", "" + StrGet_work_address);
                                } else {
                                    Log.e("StrGet_work_address is:", "" + "Not Found");
                                }

                                if (!StrGet_home_address.equalsIgnoreCase("")) {
                                    Log.e("StrGet_home_address is:", "" + "Found So go to Get profile screen");
                                    Log.e("StrGet_home_address is:", "" + StrGet_home_address);
                                    Intent GoGetProfileScreen = new Intent(getApplicationContext(), ProfileGetActivity.class);
                                    startActivity(GoGetProfileScreen);
                                    finish();
                                } else {
                                    Log.e("StrGet_home_address is:", "" + "Not Found So go to create profile screen");

                                    Intent GoGetProfileScreen = new Intent(getApplicationContext(), ProfileCreateActivity.class);
                                    startActivity(GoGetProfileScreen);
                                    finish();
                                }

                            } else if (StrGet_status.equalsIgnoreCase("0")) {
                                Log.e("StrGet_status is:", "0");
                                Log.e("Login Detail Not Match :", "Error");

//                            Toast.makeText(LoginActivity.this, "User detail not found", Toast.LENGTH_LONG).show();

                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Logine Error!")
                                        .setContentText("User detail not found")
                                        .show();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
//                        Toast.makeText(LoginActivity.this, "Server error try again", Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());

                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Server error try again")
                                .show();

//                        TV_volley.setText(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", user_email_id);
                params.put("password", user_password);
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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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


//    public static void hideSoftKeyboard(Activity activity) {
//        InputMethodManager inputMethodManager =
//                (InputMethodManager) activity.getSystemService(
//                        Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(
//                activity.getCurrentFocus().getWindowToken(), 0);
//    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
