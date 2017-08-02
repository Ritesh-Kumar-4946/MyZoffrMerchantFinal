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
import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.myzoffrmerchants.Constant.Appconstant;
import in.myzoffrmerchants.Constant.HttpUrlPath;
import in.myzoffrmerchants.R;

/**
 * Created by ritesh on 10/7/17.
 */

public class SignupActivity extends AppCompatActivity {

    private static final String REGISTER_URL = "marchant_signup?";
    @BindView(R.id.edt_et_signup_username)
    EditText Edt_et_signup_username;
    @BindView(R.id.edt_et_signup_email)
    EditText Edt_et_signup_email;
    @BindView(R.id.edt_et_signup_phone_number)
    EditText Edt_et_signup_phone_number;
    @BindView(R.id.edt_et_sign_password)
    EditText Edt_et_sign_password;
    @BindView(R.id.edt_et_sign_re_password)
    EditText Edt_et_sign_re_password;
    @BindView(R.id.cv_et_sign_btn_signup)
    CardView Cv_et_sign_btn_signup;

    //
//    @BindView(R.id.btn_Signup)
//    Button Btn_Signup;
//
//
//    @BindView(R.id.rl_already_account)
//    RelativeLayout Rl_already_account;
    @BindView(R.id.rl_already_account)
    RelativeLayout Rl_already_account;
    String str_user_signup_name = "",
            str_signup_emailid = "",
            str_signup_phone_number = "",
            str_signup_password = "",
            str_signup_confirm_password = "",
            StrGet_message = "",
            StrGet_status = "",
            StrGet_result = "",
            StrGet_user_id = "",
            StrGet_user_name = "",
            StrGet_user_mobile = "",
            StrGet_user_email = "",
            StrGet_user_active_status = "";
    String Empty_String = "http://whatsapphindistatus.com/ZOF/uploads/images/Empty";
    private ProgressDialog pDialog;

    /*email varification pattern (Start) 02*/
    public final static boolean isValidEmaill(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /*http://whatsapphindistatus.com/ZOF/webservice/marchant_signup?
    email=vgurjar86@gmail.com&username=vijju&mobile=8889994272&password=123*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_new);
        ButterKnife.bind(this);

        AndroidNetworking.initialize(getApplicationContext());

        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        Rl_already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Intent Gologinscreen = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(Gologinscreen);
                finish();
            }
        });


        Edt_et_signup_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Edt_et_signup_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Edt_et_signup_phone_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        Edt_et_sign_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Edt_et_sign_re_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Cv_et_sign_btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                boolean iserror = false;
                str_user_signup_name = Edt_et_signup_username.getText().toString().trim();
                str_signup_emailid = Edt_et_signup_email.getText().toString().trim();
                str_signup_phone_number = Edt_et_signup_phone_number.getText().toString().trim();
                str_signup_password = Edt_et_sign_password.getText().toString().trim();
                str_signup_confirm_password = Edt_et_sign_re_password.getText().toString().trim();

                Log.e(" Sign Up Fields data :", "\n"
                        + "str_user_signup_name :" + "" + str_user_signup_name + "\n"
                        + "str_signup_emailid :" + "" + str_signup_emailid + "\n"
                        + "str_signup_phone_number :" + "" + str_signup_phone_number + "\n"
                        + "str_signup_password :" + "" + str_signup_password + "\n"
                        + "str_signup_confirm_password :" + "" + str_signup_confirm_password);

                if (str_user_signup_name.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                /*https://github.com/daimajia/AndroidViewAnimations/blob/master/README.md*/

                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_et_signup_username);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Please enter your Name",
                            Toast.LENGTH_SHORT).show();


                } else if (str_signup_emailid.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_et_signup_email);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Email Id", Toast.LENGTH_SHORT).show();


                } else if (!isValidEmaill(str_signup_emailid)) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    //	emailedit.requestFocus();
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_et_signup_email);
                    /**************** End Animation ****************/
                    Toast.makeText(getApplicationContext(),
                            "Please enter valid email address.", Toast.LENGTH_SHORT).show();


                } else if (str_signup_phone_number.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_et_signup_phone_number);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Phone Number", Toast.LENGTH_SHORT).show();


                } else if (str_signup_phone_number.length() < 10) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_et_signup_phone_number);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Please enter correct Phone Number",
                            Toast.LENGTH_SHORT).show();


                } else if (str_signup_password.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_et_sign_password);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Password", Toast.LENGTH_SHORT).show();


                } else if (str_signup_password.length() < 5) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_et_sign_password);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Please enter at least 5 character in password.",
                            Toast.LENGTH_SHORT).show();


                } else if (str_signup_password.contains(" ")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Edt_et_sign_password);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(), "Space is not allowed in password.",
                            Toast.LENGTH_SHORT).show();


                } else if (!str_signup_confirm_password.equals(str_signup_password)) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Swing)
                            .duration(700)
                            .playOn(Edt_et_sign_password);

                    YoYo.with(Techniques.Swing)
                            .duration(700)
                            .playOn(Edt_et_sign_re_password);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "oopsss....\n Password not Match Please try again", Toast.LENGTH_SHORT).show();


                }

                if (!iserror) {

                    Log.e("No Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                        Toast.makeText(getApplicationContext(), "You are valid User", Toast.LENGTH_SHORT).show();
                    Log.e("No Error :", "You are valid User");


                    userRegister();
//                        Merchant_Register_Fast();


                }

            }
        });

    }

    private void userRegister() {
        final String user_name = str_user_signup_name;
        final String user_email_id = str_signup_emailid;
        final String user_phone_number = str_signup_phone_number;
        final String user_password = str_signup_password;

        Log.e("Volley Data :", ""
                + "\n" + "user_name :" + "" + user_name
                + "\n" + "user_email_id :" + "" + user_email_id
                + "\n" + "user_phone_number :" + "" + user_phone_number
                + "\n" + "user_password :" + "" + user_password
        );

        // Tag used to cancel the request
        String tag_string_req = "req_registration";
        pDialog.setMessage("Register.....");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlPath.urlPathMain + REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(MainVolleyActivity.this,response,Toast.LENGTH_LONG).show();
                        hideDialog();
                        Log.e("Response :", "" + response);
                        try {
                            JSONObject jobjresponse = new JSONObject(response);
                            Log.e("jobjresponse :", "" + jobjresponse);

                            StrGet_status = jobjresponse.getString("status");
                            Log.e("StrGet_status is:", "" + StrGet_status);

                            StrGet_message = jobjresponse.getString("message");
                            Log.e("StrGet_message is:", "" + StrGet_message);

                            if (StrGet_status.equalsIgnoreCase("1")) {
                                Log.e("StrGet_status is:", "1");
                                JSONObject jobjresult = jobjresponse.getJSONObject("result");
                                Log.e("jobjresult :", "" + jobjresult.toString());

                                StrGet_user_id = jobjresult.getString("id");
                                StrGet_user_name = jobjresult.getString("username");
                                StrGet_user_mobile = jobjresult.getString("mobile");
                                StrGet_user_email = jobjresult.getString("email");

                                Appconstant.editor.putString("id", StrGet_user_id);
                                Appconstant.editor.putString("email", StrGet_user_email);
                                Appconstant.editor.putString("username", StrGet_user_name);
                                Appconstant.editor.putString("mobile", StrGet_user_mobile);
                                Appconstant.editor.putString("EmptyString", Empty_String);
                                Appconstant.editor.putString("loginTest", "true");
                                Appconstant.editor.commit();

                                Log.e("StrGet_user_id is:", "" + StrGet_user_id);
                                Log.e("StrGet_user_name is:", "" + StrGet_user_name);
                                Log.e("StrGet_user_mobile is:", "" + StrGet_user_mobile);
                                Log.e("StrGet_user_email is:", "" + StrGet_user_email);
                                Log.e("Signup complete successfully :", "Now Create Your Profile");


                                Intent GoCreateProfileScreen = new Intent(SignupActivity.this, ProfileCreateActivity.class);
                                startActivity(GoCreateProfileScreen);
                                finish();


                            } else if (StrGet_status.equalsIgnoreCase("0")) {
                                Log.e("StrGet_status is:", "0");
                                Log.e("Email already exist:", "0");
//                            Toast.makeText(getApplicationContext(), StrGet_message, Toast.LENGTH_SHORT).show();

                                TastyToast.makeText(getApplicationContext(),
                                        "Email already exist",
                                        TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                            }
//                            else {
//                                Log.e("StrGet_result is:", "" + StrGet_result);
//                                Toast.makeText(getApplicationContext(), StrGet_result, Toast.LENGTH_SHORT).show();
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("email", user_email_id);
                params.put("username", user_name);
                params.put("mobile", user_phone_number);
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

    private void Merchant_Register_Fast() {

        Log.e("Merchant_Register_Fast ********************* :", "*************************");

        String tag_string_req = "req_login";
        pDialog.setMessage("Register in ...");
        showDialog();


        AndroidNetworking.post(HttpUrlPath.urlPathMain + REGISTER_URL)
                .addBodyParameter("email", str_signup_emailid)
                .addBodyParameter("username", str_user_signup_name)
                .addBodyParameter("mobile", str_signup_phone_number)
                .addBodyParameter("password", str_signup_password)

//                .addBodyParameter("lastname", "Shekhar")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
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

                            StrGet_status = jobjresponse.getString("status");
                            Log.e("StrGet_status is:", "" + StrGet_status);

                            StrGet_message = jobjresponse.getString("message");
                            Log.e("StrGet_message is:", "" + StrGet_message);

                            StrGet_result = jobjresponse.getString("result");
                            Log.e("StrGet_result is:", "" + StrGet_result);

                            JSONObject jobjresult = jobjresponse.getJSONObject("result");
                            Log.e("jobjresult :", "" + jobjresult.toString());


                            if (StrGet_status.equalsIgnoreCase("1")) {
                                Log.e("StrGet_status is:", "1");

                                StrGet_user_id = jobjresult.getString("id");
                                Log.e("StrGet_user_id is:", "" + StrGet_user_id);

                                StrGet_user_name = jobjresult.getString("username");
                                Log.e("StrGet_user_name is:", "" + StrGet_user_name);

                                StrGet_user_mobile = jobjresult.getString("mobile");
                                Log.e("StrGet_user_mobile is:", "" + StrGet_user_mobile);

                                StrGet_user_email = jobjresult.getString("email");
                                Log.e("StrGet_user_email is:", "" + StrGet_user_email);


//                                Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();

                            } else {
                                Log.e("StrGet_result is:", "" + StrGet_result);
                                Toast.makeText(getApplicationContext(), StrGet_result, Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.e("response :", "" + responsee);

                        if (StrGet_status.equalsIgnoreCase("1")) {
                            Log.e("StrGet_status is:", "1");


                            Appconstant.editor.putString("id", StrGet_user_id);
                            Appconstant.editor.putString("email", StrGet_user_email);
                            Appconstant.editor.putString("username", StrGet_user_name);
                            Appconstant.editor.putString("mobile", StrGet_user_mobile);
                            Appconstant.editor.putString("loginTest", "true");
                            Appconstant.editor.commit();


                            Intent Gologincreen = new Intent(SignupActivity.this, MainCategoryActivity.class);
                            startActivity(Gologincreen);
                            finish();
                        } else if (StrGet_status.equalsIgnoreCase("0")) {
                            Log.e("StrGet_status is:", "0");
                            Log.e("Email already exist:", "0");
//                            Toast.makeText(getApplicationContext(), StrGet_message, Toast.LENGTH_SHORT).show();

                            TastyToast.makeText(getApplicationContext(),
                                    "Email already exist",
                                    TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                        }


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        hideDialog();
//                        Toast.makeText(MainVolleyActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());

                        TastyToast.makeText(getApplicationContext(),
                                "Server error please try again",
                                TastyToast.LENGTH_SHORT, TastyToast.ERROR);


                    }
                });


    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }


    /*email varification pattern (Start) 01*/
//    public final static boolean isValidEmail(CharSequence target) {
//        if (TextUtils.isEmpty(target)) {
//            return false;
//        } else {
//            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
//        }
//    }
    /*email varification pattern (End) 01*/

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    /*email varification pattern (End) 02*/


    /*email varification pattern (Start) 03*/
//    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
//            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
//                    "\\@" +
//                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
//                    "(" +
//                    "\\." +
//                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
//                    ")+"
//    );
//
//    private boolean checkEmail(String email) {
//        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
//    }
    /*email varification pattern (End) 03*/


    /*email varification pattern (End) 04*/
//    private boolean isValidEmaillId(String email) {
//        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
//                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
//                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
//                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
//                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
//                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
//    }
    /*email varification pattern (End) 04*/

    @Override
    public void onBackPressed() {

        Log.e("onBackPressed go Sub Category screen :", "OK");
        Intent GoMainSubCategoryScreen = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(GoMainSubCategoryScreen);
        finish();


    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
