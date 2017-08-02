package in.myzoffrmerchants.main_activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import in.myzoffrmerchants.R;

/**
 * Created by ritesh on 28/7/17.
 */

public class RegistrationModeActivity extends AppCompatActivity {


    @BindView(R.id.tv_account_type_free)
    TextView Tv_btn_account_type_free;

    @BindView(R.id.tv_account_type_paid)
    TextView Tv_btn_account_type_paid;

    @BindView(R.id.tv_sales_person_name)
    TextView Tv_sales_person_name;

    @BindView(R.id.tv_sales_person_phone_number)
    TextView Tv_sales_person_phone_number;

    @BindView(R.id.tv_sales_person_emp_id)
    TextView Tv_sales_person_emp_id;

    @BindView(R.id.tv_cheque_dated_selected)
    TextView Tv_cheque_dated_selected;


    @BindView(R.id.edt_sales_exe_id)
    EditText Edt_sales_exe_id;

    @BindView(R.id.edt_sales_exe_code)
    EditText Edt_sales_exe_code;

    @BindView(R.id.edt_cheque_no)
    EditText Edt_cheque_no;

    @BindView(R.id.edt_cash)
    EditText Edt_cash;

    @BindView(R.id.edt_cash_confirm)
    EditText Edt_cash_confirm;

    @BindView(R.id.edt_cheque_cash_amt)
    EditText Edt_cheque_cash_amt;

    @BindView(R.id.edt_cheque_cash_amt_confirm)
    EditText Edt_cheque_cash_amt_confirm;

//    @BindView(R.id.tv_cheque_dated)
//    TextView Tv_cheque_dated;


    @BindView(R.id.ll_sales_code_detail)
    LinearLayout LL_sales_code_detail;

    @BindView(R.id.ll_get_sales_person_detail)
    LinearLayout LL_get_sales_person_detail;

    @BindView(R.id.ll_cheque_detail)
    LinearLayout LL_cheque_detail;

    @BindView(R.id.ll_cash_detail)
    LinearLayout LL_cash_detail;


    @BindView(R.id.rl_btn_submit_free)
    RelativeLayout Rl_btn_submit_free;

    @BindView(R.id.tv_btn_submit_paid_cheque)
    TextView Tv_btn_submit_paid_cheque;

    @BindView(R.id.tv_btn_submit_paid_cash)
    TextView Tv_btn_submit_paid_cash;

    @BindView(R.id.tv_btn_get_sales_person_done)
    TextView Tv_btn_get_sales_person_done;

    @BindView(R.id.tv_btn_payment_type_cheque)
    TextView Tv_btn_payment_type_cheque;

    @BindView(R.id.tv_btn_payment_type_cash)
    TextView Tv_btn_payment_type_cash;

    @BindView(R.id.tv_btn_mail_us_paid_account)
    TextView Tv_btn_mail_us_paid_account;

    @BindView(R.id.iv_sales_person_image)
    CircleImageView Civ_sales_person_image;

    @BindView(R.id.tv_text_or)
    TextView tv_text_or;

    @BindView(R.id.rl_paid_enquiry)
    View Vrl_paid_enquiry;


    String Str_sales_exe_id = "",
            Str_sales_exe_code = "",
            StrGet_sales_person_image = "",
            StrGet_sales_person_name = "",
            StrGet_sales_person_email_id = "",
            StrGet_cheque_no = "",
            StrGet_cheque_date = "",
            StrGet_cash = "";


    private HorizontalCalendar horizontalCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_mode);
        ButterKnife.bind(this);


        LL_sales_code_detail.setVisibility(View.GONE);
        Rl_btn_submit_free.setVisibility(View.GONE);


        /** end after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        /** start before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        final Calendar defaultDate = Calendar.getInstance();
        defaultDate.add(Calendar.MONTH, -1);
        defaultDate.add(Calendar.DAY_OF_WEEK, +5);


        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(4)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .showDayName(true)
                .showMonthName(true)
                .defaultSelectedDate(defaultDate.getTime())
                .textColor(Color.LTGRAY, Color.WHITE)
                .selectedDateBackground(Color.TRANSPARENT)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
//                Toast.makeText(RegistrationModeActivity.this, DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show();

                Tv_cheque_dated_selected.setText(DateFormat.getDateInstance().format(date));
            }

        });


        Edt_sales_exe_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        Edt_sales_exe_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        Edt_cheque_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Edt_cash.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Edt_cash_confirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Edt_cheque_cash_amt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Edt_cheque_cash_amt_confirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Tv_btn_account_type_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LL_sales_code_detail.setVisibility(View.GONE);
                LL_get_sales_person_detail.setVisibility(View.GONE);
                Vrl_paid_enquiry.setVisibility(View.GONE);
                tv_text_or.setVisibility(View.GONE);
                Rl_btn_submit_free.setVisibility(View.VISIBLE);
            }
        });


        Tv_btn_account_type_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LL_sales_code_detail.setVisibility(View.VISIBLE);
                Vrl_paid_enquiry.setVisibility(View.VISIBLE);
                tv_text_or.setVisibility(View.VISIBLE);
                LL_cheque_detail.setVisibility(View.GONE);
                LL_cash_detail.setVisibility(View.GONE);
                LL_get_sales_person_detail.setVisibility(View.GONE);
                Rl_btn_submit_free.setVisibility(View.GONE);
            }
        });


        Tv_btn_get_sales_person_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LL_sales_code_detail.setVisibility(View.GONE);
                LL_get_sales_person_detail.setVisibility(View.VISIBLE);
            }
        });


        Tv_btn_payment_type_cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LL_cash_detail.setVisibility(View.GONE);
                LL_cheque_detail.setVisibility(View.VISIBLE);
            }
        });


        Tv_btn_payment_type_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LL_cheque_detail.setVisibility(View.GONE);
                LL_cash_detail.setVisibility(View.VISIBLE);
            }
        });


        Tv_btn_submit_paid_cheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Button cheque Clicked So:", " Cheque Detail Submit");
            }
        });


        Tv_btn_submit_paid_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Button Cash Clicked So:", " Cash Detail Submit");
            }
        });


        Tv_btn_mail_us_paid_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* intent with subject and attachment (Start)*/
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@zoffr.in"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                        "Merchant Name :" + " " + "Demo HendyMan Name" + " "
                                + "&" + " "
                                + "Merchant Id :" + " " + "Demo HandyMan ID 100");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello Zoffr help me for getting paid account." +
                        "\n" + "\n" + "Thank You.");

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


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
