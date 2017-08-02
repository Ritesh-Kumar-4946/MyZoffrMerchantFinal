package in.myzoffrmerchants.main_activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.myzoffrmerchants.Constant.HttpUrlPath;
import in.myzoffrmerchants.R;

/**
 * Created by ritesh on 15/7/17.
 */

public class ForgotPasswordActivity extends AppCompatActivity {

    private static final String FORGOT_PASSWORD_URL = "forgot_password?";
    @BindView(R.id.edt_user_forgot_email)
    EditText Edt_user_forgot_email;
    @BindView(R.id.til_user_forgot_email)
    TextInputLayout Til_user_forgot_email;
    @BindView(R.id.btn_send_forgot_password)
    Button Btn_send_forgot_password;
    String StrGet_forgot_email = "";
    boolean iserror = false;
    String StrGet_status = "",
            StrGet_message = "",
            StrGet_result = "";
    private ProgressDialog pDialog;

    /*email varification pattern (Start) 02*/
    public final static boolean isValidEmaill(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        /*http://whatsapphindistatus.com/ZOF/webservice/forgot_password?email=eamit007@gmail.com*/


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        Btn_send_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrGet_forgot_email = Edt_user_forgot_email.getText().toString().trim();

                Log.e(" StrGet_forgot_email data :", "\n"
                        + "StrGet_forgot_email :" + "" + StrGet_forgot_email + "\n");


                if (StrGet_forgot_email.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Til_user_forgot_email);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Email Id", Toast.LENGTH_SHORT).show();


                } else if (!isValidEmaill(StrGet_forgot_email)) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
//                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    //	emailedit.requestFocus();
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(Til_user_forgot_email);
                    /**************** End Animation ****************/
                    Toast.makeText(getApplicationContext(),
                            "Please enter valid email address.", Toast.LENGTH_SHORT).show();


                } else {

                    Toast.makeText(getApplicationContext(),
                            "Password Reset Request Submitted!" + "\n" + "Please check your email", Toast.LENGTH_LONG).show();

//                    Intent GoMainScreen = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
//                    startActivity(GoMainScreen);
//                    new SweetAlertDialog(ForgotPasswordActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                            .setTitleText("Password Reset Request Submitted!")
//                            .setContentText("Please check your email")
//                            .setConfirmText("OK")
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    Intent GoMainScreen = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
//                                    startActivity(GoMainScreen);
//                                    finish();
//                                }
//                            });
                }
            }
        });


    }
    /*email varification pattern (End) 02*/

    private void ForGotPassword_Fast() {
        final String user_email_id = StrGet_forgot_email;

        Log.e("Volley Data :", ""
                        + "\n" + user_email_id + "" + user_email_id
//                + "\n" + "user_password" + "" + user_password
        );

        // Tag used to cancel the request
        String tag_string_req = "req_registration";
        pDialog.setMessage("Please Wait Fetching Detail.....");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlPath.urlPathMain,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(MainVolleyActivity.this,response,Toast.LENGTH_LONG).show();

                        Log.e("Response :", "" + response);
                        try {
                            JSONObject jobjresponse = new JSONObject(response);
                            Log.e("jobjresponse :", "" + jobjresponse);

                            StrGet_status = jobjresponse.getString("status");
                            StrGet_message = jobjresponse.getString("message");
                            StrGet_result = jobjresponse.getString("result");
                            Log.e("StrGet_status :", "" + StrGet_status);
                            if (StrGet_status.equalsIgnoreCase("1")) {
                                Log.e("StrGet_status jobjresponse is:", "1");

                                TastyToast.makeText(getApplicationContext(),
                                        "Check Your Email",
                                        TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                Intent GoLoginScreen = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                startActivity(GoLoginScreen);
                                finish();

                                hideDialog();
                            } else {
                                TastyToast.makeText(getApplicationContext(),
                                        "Email not exist",
                                        TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            }


                        } catch (JSONException e) {
                            hideDialog();
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

                        new SweetAlertDialog(ForgotPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
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
//                params.put("password", user_password);
                return params;
            }

        };


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


}
