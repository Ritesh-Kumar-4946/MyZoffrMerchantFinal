package in.myzoffrmerchants.main_activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
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
 * Created by ritesh on 18/7/17.
 */

public class ChangePasaswordActivity extends AppCompatActivity {

    private static final String CHANGE_PASSWORD = "update_password?";
    @BindView(R.id.edt_user_old_password)
    EditText Edt_user_old_password;
    @BindView(R.id.edt_user_new_password_one)
    EditText Edt_user_new_password_one;
    @BindView(R.id.edt_user_confirm_new_password_one)
    EditText Edt_user_confirm_new_password_one;
    @BindView(R.id.rl_cancel_btn)
    RelativeLayout Rl_cancel_btn;
    @BindView(R.id.rl_ok_btn)
    RelativeLayout Rl_ok_btn;
    @BindView(R.id.toolbar_change_password)
    Toolbar Toolbar_change_password;
    String
            Str_Set_Old_password = "",
            Str_Set_New_password = "",
            Str_Get_update_password_result = "",
            Sh_pre_User_ID = "",
            Str_Set_Confirm_new_password = "";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());

        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        Sh_pre_User_ID = Appconstant.sh.getString("id", null);
        Log.e("Merchant_ID from SharedPref :", "" + Sh_pre_User_ID);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        setSupportActionBar(Toolbar_change_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Rl_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();

                Intent GoEditScreen = new Intent(getApplicationContext(), ProfileEditActivity.class);
                startActivity(GoEditScreen);
                finish();


            }
        });


        Rl_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean iserror = false;

                Str_Set_Old_password = Edt_user_old_password.getText().toString().trim();
                Str_Set_New_password = Edt_user_new_password_one.getText().toString().trim();
                Str_Set_Confirm_new_password = Edt_user_confirm_new_password_one.getText().toString().trim();


                Log.e(" Change Password Fields data :", "\n"
                        + "Str_Set_Old_password :" + "" + Str_Set_Old_password + "\n"
                        + "Str_Set_New_password :" + "" + Edt_user_new_password_one + "\n"
                        + "Str_Set_Confirm_new_password :" + "" + Edt_user_confirm_new_password_one + "\n");


                if (Str_Set_Old_password.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/


//                    Toast.makeText(getApplicationContext(), "Please enter your Old Password",
//                            Toast.LENGTH_SHORT).show();

                    TastyToast.makeText(getApplicationContext(),
                            "Please enter your Old Password",
                            TastyToast.LENGTH_SHORT, TastyToast.WARNING);


                } else if (Str_Set_New_password.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);


//                    Toast.makeText(getApplicationContext(),
//                            "Please enter your Password", Toast.LENGTH_SHORT).show();


                    TastyToast.makeText(getApplicationContext(),
                            "Please enter your New Password",
                            TastyToast.LENGTH_SHORT, TastyToast.WARNING);


                } else if (Str_Set_New_password.length() < 5) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);


//                    Toast.makeText(getApplicationContext(), "Please enter at least 5 character in password.",
//                            Toast.LENGTH_SHORT).show();


                    TastyToast.makeText(getApplicationContext(),
                            "Please enter at least 5 character in password",
                            TastyToast.LENGTH_SHORT, TastyToast.WARNING);


                } else if (Str_Set_New_password.contains(" ")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);


//                    Toast.makeText(getApplicationContext(), "Space is not allowed in password.",
//                            Toast.LENGTH_SHORT).show();


                    TastyToast.makeText(getApplicationContext(),
                            "Space is not allowed in password.",
                            TastyToast.LENGTH_SHORT, TastyToast.WARNING);


                } else if (!Str_Set_Confirm_new_password.equals(Str_Set_New_password)) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);


//                    Toast.makeText(getApplicationContext(),
//                            "oopsss....\n Password not Match Please try again", Toast.LENGTH_SHORT).show();

                    TastyToast.makeText(getApplicationContext(),
                            "oopsss....\n Password not Match Please try again",
                            TastyToast.LENGTH_SHORT, TastyToast.WARNING);


                }
                if (!iserror) {

                    Log.e("No Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                        Toast.makeText(getApplicationContext(), "You are valid User", Toast.LENGTH_SHORT).show();
                    Log.e("No Error :", "Password valid By User");


                    Change_Password_Fast();


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


//                                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();

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
//                            ChangePassword.dismiss();

                            Intent GoEditScreen = new Intent(getApplicationContext(), ProfileEditActivity.class);
                            startActivity(GoEditScreen);
                            finish();


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
//                        ChangePassword.dismiss();

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


        Log.e("onBackPressed go ProfileEdit screen :", "OK");
        Intent GoProfileEditScreen = new Intent(getApplicationContext(), ProfileEditActivity.class);
        startActivity(GoProfileEditScreen);
        finish();


    }


}
