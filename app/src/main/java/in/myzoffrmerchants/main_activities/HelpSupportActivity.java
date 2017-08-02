package in.myzoffrmerchants.main_activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.myzoffrmerchants.Constant.Appconstant;
import in.myzoffrmerchants.R;

/**
 * Created by ritesh on 15/7/17.
 */

public class HelpSupportActivity extends AppCompatActivity {


    @BindView(R.id.tv_your_query)
    EditText Tv_your_query;


    @BindView(R.id.tv_your_query_description)
    EditText Tv_your_query_description;


    @BindView(R.id.cv_help_support_send_query)
    CardView Cv_help_support_send_query;


    @BindView(R.id.cv_mail_us)
    CardView Cv_mail_us;
    String StrGet_Edit_query = "",
            StrGet_Edit_query_description = "",
            Sh_pre_User_ID = "",
            Sh_pre_User_Name = "";
    @BindView(R.id.toolbar_help_support)
    Toolbar Toolbar_help_support;
    private String blockCharacterSet = "~#^|$%*!<>+@";
    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            for (int i = start; i < end; i++) {
                if (!Character.isLetterOrDigit(source.charAt(i))) {
                    return "";
                }
            }

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support);
        ButterKnife.bind(this);

        Tv_your_query.setFilters(new InputFilter[]{filter});
        Tv_your_query_description.setFilters(new InputFilter[]{filter});


        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        Sh_pre_User_ID = Appconstant.sh.getString("id", null);
        Sh_pre_User_Name = Appconstant.sh.getString("username", null);

        setSupportActionBar(Toolbar_help_support);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Cv_help_support_send_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrGet_Edit_query = Tv_your_query.getText().toString().trim();
                StrGet_Edit_query_description = Tv_your_query_description.getText().toString();

                Log.e(" Query Fields data :", "\n"
                        + "StrGet_Edit_query :" + "" + StrGet_Edit_query + "\n"
                        + "StrGet_Edit_query_description :" + "" + StrGet_Edit_query_description + "\n");


                if (StrGet_Edit_query.equals("")) {
                    Log.e(" Error :", "Ok");
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Tv_your_query);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Query", Toast.LENGTH_SHORT).show();


                } else if (StrGet_Edit_query_description.equals("")) {
                    Log.e(" Error :", "Ok");
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Tv_your_query_description);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter your Query Description ", Toast.LENGTH_SHORT).show();


                } else {

                    new SweetAlertDialog(HelpSupportActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Query Submited!")
                            .setContentText("We will contact you shortly...")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent GoMainScreen = new Intent(HelpSupportActivity.this, MainCategoryActivity.class);
                                    startActivity(GoMainScreen);
                                    finish();
                                }
                            });
                }
            }
        });


        Cv_mail_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*single line intent (Start)*/
//                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:support@zoffr.in")));
                /*single line intent (End)*/


                //get to, subject and content from the user input and store as string.
//                String emailTo = editTextEailTo.getText().toString();
//                String emailSubject = editTextEmailSubject.getText().toString();
//                String emailContent = editTextEmailContent.getText().toString();


                /* intent with subject and attachment (Start)*/
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@zoffr.in"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                        "Merchant Name :" + " " + Sh_pre_User_Name + " "
                                + "&" + " "
                                + "Merchant Id :" + " " + Sh_pre_User_ID);
//                emailIntent.putExtra(Intent.EXTRA_TEXT, emailContent);

                /*use below 2 commented lines if need to use BCC an CC feature in email*/
                //emailIntent.putExtra(Intent.EXTRA_CC, new String[]{ to});
                //emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{to});
                /*use below 2 commented lines if need to use BCC an CC feature in email*/

                /*use below 3 commented lines if need to send attachment*/
//                emailIntent.setType("image/jpeg");
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Picture");
//                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://sdcard/captureimage.png"));
                /*use below 3 commented lines if need to send attachment*/

                //need this to prompts email client only
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Select an Email Client:"));
                /* intent with subject and attachment (Start)*/


            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {

        Log.e("onBackPressed go main screen :", "OK");
        Intent GoMainScreen = new Intent(getApplicationContext(), BusinessMainActivity.class);
        startActivity(GoMainScreen);
        finish();


    }


}
