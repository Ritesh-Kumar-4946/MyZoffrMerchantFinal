package in.myzoffrmerchants.main_activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.philliphsu.bottomsheetpickers.time.numberpad.NumberPadTimePickerDialog;
import com.rm.rmswitch.RMSwitch;
import com.shawnlin.numberpicker.NumberPicker;
import com.touchboarder.weekdaysbuttons.WeekdaysDataItem;
import com.touchboarder.weekdaysbuttons.WeekdaysDataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.myzoffrmerchants.Constant.Appconstant;
import in.myzoffrmerchants.Constant.HttpUrlPath;
import in.myzoffrmerchants.R;

/**
 * Created by ritesh on 17/7/17.
 */

public class CreatePackageActivity extends AppCompatActivity implements
        WeekdaysDataSource.Callback,
        BottomSheetTimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {


    private static final String TAG = "Create Package Activity";
    private static final String CREATE_PACKAGES = "create_business_package?";
    private static final String GET_BUSINESS_DETAIL = "get_business_and_package?";
    private static final String GET_MERCHANT_DETAIL = "get_profile?";
    @BindView(R.id.rm_switch_availabel_status)
    RMSwitch mRMSwitch_availabel_status;
    @BindView(R.id.txt_rm_switch_state_availabel_status)
    TextView mTxtRMSwitchState_availabel_status;
    @BindView(R.id.rm_switch_homedelivery_status)
    RMSwitch mRMSwitch_homedelivery_status;
    @BindView(R.id.txt_rm_switch_state_homedelivery_status)
    TextView mTxtRMSwitchState_homedelivery_status;
    @BindView(R.id.np_quantity_numbers)
    NumberPicker numberPicker;
    @BindView(R.id.tv_final_quantity)
    TextView Tv_final_quantity;
    @BindView(R.id.tv_select_time_from)
    TextView Tv_select_time_from;
    @BindView(R.id.tv_select_time_to)
    TextView Tv_select_time_to;
    @BindView(R.id.tv_select_date_range)
    TextView Tv_select_date_range;
    @BindView(R.id.edt_package_name)
    EditText Edt_package_name;
    @BindView(R.id.edt_package_detail)
    EditText Edt_package_detail;
    @BindView(R.id.edt_package_price)
    EditText Edt_package_price;
    @BindView(R.id.edt_package_old_price)
    EditText Edt_package_old_price;
    @BindView(R.id.rl_availabel_status)
    RelativeLayout Rl_availabel_status;
    @BindView(R.id.rl_homedelivery_status)
    RelativeLayout Rl_homedelivery_status;
    @BindView(R.id.rl_quantity_main)
    RelativeLayout Rl_quantity_main;
    @BindView(R.id.rl_package_date)
    RelativeLayout Rl_package_date;
    @BindView(R.id.rl_package_time_from)
    RelativeLayout Rl_package_time_from;
    @BindView(R.id.rl_package_time_to)
    RelativeLayout Rl_package_time_to;
    @BindView(R.id.ll_week_day)
    LinearLayout LL_week_day;
    @BindView(R.id.iv_business_image_package)
    ImageView Iv_business_image_package;
    NumberPadTimePickerDialog dialogtimefrom;
    NumberPadTimePickerDialog dialogtimeto;
    /***********    Card View Data (End)    *************/

    @BindView(R.id.rl_btn_package_create)
    RelativeLayout Rl_btn_package_create;
    String Sh_pre_User_ID = "",
            Str_Time = "",
            Str_Date_From = "",
            Str_Date_To = "",
            Str_Get_Business_ID_Shared_pref = "",
            Str_Get_Create_package_status = "",
            Str_Get_Create_package_result = "",
            Str_Get_Create_package_message = "",
            Str_Get_Create_package_package_id = "",
            Str_Get_Create_package_business_id = "",
            Str_Get_Create_package_package_name = "",
            Str_Get_Create_package_package_detail = "",
            Str_Get_Create_package_stock_status = "",
            Str_Get_Create_package_valid_parsons = "",
            Str_Get_Create_package_valid_day = "",
            Str_Get_Create_package_old_price = "",
            Str_Get_Create_package_new_price = "",
            Str_Get_Create_package_home_delivery = "",
            Str_Get_Create_package_offer_validity_from = "",
            Str_Get_Create_package_offer_validity_to = "",
            Str_Get_Create_package_timing_from = "",
            Str_Get_Create_package_timing_to = "",
            Str_Get_package_detail = "",
            Str_Get_package_name = "",
            Str_Get_package_current_price = "",
            Str_Get_package_old_price = "",
            Str_Get_package_availabe_status = "",
            Str_Get_package_quantity_valid = "",
            Str_Get_package_available_days = "",
            Str_Get_package_home_delivery = "",
            Str_Get_package_offer_valid_from_date = "",
            Str_Get_package_offer_valid_to_date = "",
            Str_Get_package_available_from_time = "",
            Str_Get_package_available_to_time = "",
            Str_Get_Profile_Status = "",
            Str_Get_message = "",
            Str_Get_result = "",
            Str_Get_User_ID = "",
            Str_Get_business_id = "",
            Str_Get_quantity = "",
            STr_Get_BUSINESS_DETAIL_status = "",
            STr_Get_BUSINESS_DETAIL_message = "",
            STr_Get_BUSINESS_DETAIL_Merchant_ID = "",
            STr_Get_BUSINESS_DETAIL_Business_ID = "",
            STr_Get_business_name = "",
            STr_Get_sub_name = "",
            STr_Get_description = "",
            STr_Get_stetus = "",
            STr_Get_image1 = "";
    /***********    Card View Data (Start)    *************/
    private WeekdaysDataSource weekdaysDataSource1;


    /*http://whatsapphindistatus.com/ZOF/webservice/create_business_package?
    business_id=1&package_name=testing&stock_status=available&valid_parsons=4
    &valid_day=sunday,monday&old_price=10&new_price=15&home_delivery=ye
    s&offer_validity_from=18-feb-2017&offer_validity_to=19-feb-2017&timing=10am%20to%2007pm*/

    //    private static final String LOG_TAG = CreatePackageActivity.class.getSimpleName();
    private boolean mAutoHighlight;
    private ProgressDialog pDialog;
    /**
     * @return AppBarLayout
     */
    private AppBarLayout appbar;


    /******************************Collepsing Toolbar (Start)*********************************/
    /**
     * @return CollapsingToolbarLayout collapsingAppBarLayout
     */
    private CollapsingToolbarLayout collapsingAppBarLayout;
    private WeekdaysDataSource.Callback callback1 = new WeekdaysDataSource.Callback() {
        @Override
        public void onWeekdaysItemClicked(int attachId, WeekdaysDataItem item) {
            CheckBox checkBox = (CheckBox) findViewById(R.id.check_all_1);
            if (checkBox != null) checkBox.setChecked(weekdaysDataSource1.isAllDaysSelected());

            Calendar calendar = Calendar.getInstance();
            int today = calendar.get(Calendar.DAY_OF_WEEK);
            if (item.getCalendarDayId() == today && item.isSelected()) {


                Log.e("WeekDays attachId:", "" + attachId);
                Log.e("WeekDays today:", "" + today);
                Log.e("WeekDays item.getCalendarDayId():", "" + item.getCalendarDayId());

            }
//            Toast.makeText(CreatePackageActivity.this, "Carpe diem", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onWeekdaysSelected(int attachId, ArrayList<WeekdaysDataItem> items) {
            String selectedDays = getSelectedDaysFromWeekdaysData(items);
            if (!TextUtils.isEmpty(selectedDays)) {

                Log.e("selectedDays toString:", "" + selectedDays.toString());
                Log.e("selectedDays :", "" + selectedDays);

                showSnackbarShort(selectedDays);
                Str_Get_package_available_days = selectedDays;
                Log.e("Str_Get_package_available_days :", "" + Str_Get_package_available_days);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_create_package_new);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());
        setupAppBar();

        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        Sh_pre_User_ID = Appconstant.sh.getString("id", null);
        Log.e("Merchant_ID from SharedPref :", "" + Sh_pre_User_ID);
        Log.e("Business ID from SharedPref :", "" + Str_Get_Business_ID_Shared_pref);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


//        setSupportActionBar(Toolbar_create_business_packages);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Log.e(" OnCreate Packages Fields data :", "\n"
                + "Str_Get_business_id :" + "" + Str_Get_business_id + "\n"
                + "Sh_pre_User_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Str_Get_package_name :" + "" + Str_Get_package_name + "\n"
                + "Str_Get_package_detail :" + "" + Str_Get_package_detail + "\n"
                + "Str_Get_package_current_price :" + "" + Str_Get_package_current_price + "\n"
                + "Str_Get_package_old_price :" + "" + Str_Get_package_old_price + "\n"
                + "Str_Get_package_availabe_status :" + "" + Str_Get_package_availabe_status + "\n"
                + "Str_Get_package_quantity_valid :" + "" + Str_Get_package_quantity_valid + "\n"
                + "Str_Get_package_available_days :" + "" + Str_Get_package_available_days + "\n"
                + "Str_Get_package_home_delivery :" + "" + Str_Get_package_home_delivery + "\n"
                + "Str_Get_package_offer_valid_from_date :" + "" + Str_Get_package_offer_valid_from_date + "\n"
                + "Str_Get_package_offer_valid_to_date :" + "" + Str_Get_package_offer_valid_to_date + "\n"
                + "Str_Get_package_available_from_time :" + "" + Str_Get_package_available_from_time + "\n"
                + "Str_Get_package_available_to_time :" + "" + Str_Get_package_available_to_time + "\n");


        Get_MerchantDetail_Fast();

        GET_Business_Detail_Fast();


        Edt_package_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Edt_package_detail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Edt_package_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        Edt_package_old_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });




        /*week days (Start)*/
        weekdaysDataSource1 = WeekdaysDataSource.restoreState("wds1", savedInstanceState, this, callback1, null);
//        weekdaysDataSource1.setFillWidth(true);
        weekdaysDataSource1 = new WeekdaysDataSource(this, R.id.weekdays_stub)
                .start(callback1);
        /*week days (End)*/


        /*toggle button (Start)*/
        mRMSwitch_availabel_status.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
//                mTxtRMSwitchState_availabel_status.setText("Status: " + isChecked);

                if (isChecked) {
                    mTxtRMSwitchState_availabel_status.setText("Status: " + "In Stock");
                    Str_Get_package_availabe_status = "Yes";

                } else {
                    mTxtRMSwitchState_availabel_status.setText("Status: " + "Out Of Stock");
                    Str_Get_package_availabe_status = "NO";
                }
            }
        });
        mTxtRMSwitchState_availabel_status.setText("Status: " + "Out Of Stock");


        mRMSwitch_homedelivery_status.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
//                mTxtRMSwitchState_homedelivery_status.setText("Checked: " + isChecked);

                if (isChecked) {
                    mTxtRMSwitchState_homedelivery_status.setText("Delivery: " + "Available");
                    Str_Get_package_home_delivery = "YES";

                } else {
                    mTxtRMSwitchState_homedelivery_status.setText("Delivery: " + "Not Available");
                    Str_Get_package_home_delivery = "NO";
                }


            }
        });
        mTxtRMSwitchState_homedelivery_status.setText("Delivery: " + "Not Available");
        /*toggle button (End)*/



        /*quantity picker (Start)*/
        numberPicker.setDividerColor(ContextCompat.getColor(this, R.color.colorPrimary));
        numberPicker.setDividerColorResource(R.color.colorPrimary);

        // set formatter
        numberPicker.setFormatter(getString(R.string.number_picker_formatter));
        numberPicker.setFormatter(R.string.number_picker_formatter);

        // set text color
        numberPicker.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        numberPicker.setTextColorResource(R.color.colorPrimary);

        // set text size
        numberPicker.setTextSize(getResources().getDimension(R.dimen.text_size));
        numberPicker.setTextSize(R.dimen.text_size);

        // set typeface
        numberPicker.setTypeface(Typeface.create(getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setTypeface(getString(R.string.roboto_light), Typeface.NORMAL);
        numberPicker.setTypeface(getString(R.string.roboto_light));
        numberPicker.setTypeface(R.string.roboto_light, Typeface.NORMAL);
        numberPicker.setTypeface(R.string.roboto_light);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                Toast.makeText(SubCategorySingleProductActivity.this, "New Value : " + newVal , Toast.LENGTH_SHORT).show();
                Str_Get_quantity = String.valueOf(newVal);
                Log.e("Quantity on changed:", "" + Str_Get_quantity);
                Tv_final_quantity.setText(Str_Get_quantity);

                Str_Get_package_quantity_valid = Str_Get_quantity;
                Log.e("Quantity_valid :", "" + Str_Get_package_quantity_valid);

            }
        });


        Tv_select_time_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Str_Time = "From";
                Log.e("Str_Time :", "" + Str_Time);

                dialogtimefrom = NumberPadTimePickerDialog.newInstance(CreatePackageActivity.this);
                dialogtimefrom.setThemeDark(true);
                dialogtimefrom.show(getSupportFragmentManager(), TAG);
            }
        });

        Tv_select_time_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Str_Time = "To";
                Log.e("Str_Time :", "" + Str_Time);

                dialogtimeto = NumberPadTimePickerDialog.newInstance(CreatePackageActivity.this);
                dialogtimeto.setThemeDark(true);
                dialogtimeto.show(getSupportFragmentManager(), TAG);
            }
        });
        /*quantity picker (End)*/

        Tv_select_date_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        CreatePackageActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAutoHighlight(mAutoHighlight);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });


        Rl_btn_package_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean iserror = false;

                Str_Get_package_name = Edt_package_name.getText().toString().trim();
                Str_Get_package_detail = Edt_package_detail.getText().toString().trim();
                Str_Get_package_current_price = Edt_package_price.getText().toString().trim();
                Str_Get_package_old_price = Edt_package_old_price.getText().toString().trim();

                Str_Get_package_availabe_status = Str_Get_package_availabe_status.trim();
                Str_Get_package_quantity_valid = Str_Get_package_quantity_valid.trim();
                Str_Get_package_available_days = Str_Get_package_available_days.trim();
                Str_Get_package_home_delivery = Str_Get_package_home_delivery.trim();
                Str_Get_package_offer_valid_from_date = Str_Get_package_offer_valid_from_date.trim();
                Str_Get_package_offer_valid_to_date = Str_Get_package_offer_valid_to_date.trim();
                Str_Get_package_available_from_time = Str_Get_package_available_from_time.trim();
                Str_Get_package_available_to_time = Str_Get_package_available_to_time.trim();


                Log.e(" Onclick Packages Fields data :", "\n"
                        + "Str_Get_business_id :" + "" + Str_Get_business_id + "\n"
                        + "Sh_pre_User_ID :" + "" + Sh_pre_User_ID + "\n"
                        + "Str_Get_package_name :" + "" + Str_Get_package_name + "\n"
                        + "Str_Get_package_detail :" + "" + Str_Get_package_detail + "\n"
                        + "Str_Get_package_current_price :" + "" + Str_Get_package_current_price + "\n"
                        + "Str_Get_package_old_price :" + "" + Str_Get_package_old_price + "\n"
                        + "Str_Get_package_availabe_status :" + "" + Str_Get_package_availabe_status + "\n"
                        + "Str_Get_package_quantity_valid :" + "" + Str_Get_package_quantity_valid + "\n"
                        + "Str_Get_package_available_days :" + "" + Str_Get_package_available_days + "\n"
                        + "Str_Get_package_home_delivery :" + "" + Str_Get_package_home_delivery + "\n"
                        + "Str_Get_package_offer_valid_from_date :" + "" + Str_Get_package_offer_valid_from_date + "\n"
                        + "Str_Get_package_offer_valid_to_date :" + "" + Str_Get_package_offer_valid_to_date + "\n"
                        + "Str_Get_package_available_from_time :" + "" + Str_Get_package_available_from_time + "\n"
                        + "Str_Get_package_available_to_time :" + "" + Str_Get_package_available_to_time + "\n");


                if (Str_Get_package_name.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_package_name);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter Package Name", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_package_detail.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_package_detail);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter Package Detail", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_package_current_price.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_package_price);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter Package Offer Price", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_package_old_price.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Edt_package_old_price);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter Package Regular Price", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_package_availabe_status.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Rl_availabel_status);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter YES or NO", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_package_quantity_valid.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Rl_quantity_main);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter Package Quantity Number", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_package_available_days.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(LL_week_day);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter Package Avalilable Days", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_package_home_delivery.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Rl_homedelivery_status);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter YES or NO", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_package_available_from_time.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Tv_select_time_from);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter Package Time Start", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_package_available_to_time.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Tv_select_time_to);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter Package Time End", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_package_offer_valid_from_date.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Tv_select_date_range);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter Package Offer Date Start", Toast.LENGTH_SHORT).show();


                } else if (Str_Get_package_offer_valid_to_date.equals("")) {
                    iserror = true;
                    Log.e(" Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(Tv_select_date_range);
                    /**************** End Animation ****************/

                    Toast.makeText(getApplicationContext(),
                            "Please enter Package Offer Date End", Toast.LENGTH_SHORT).show();


                }
                if (!iserror) {
                    Log.e("No Error :", "Ok");
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                    Create_Packages_Volley();


                }


            }
        });


    }

    /**
     * Set up the Caoll
     */
    private void setupAppBar() {

        //Remove the title so we only  display the Image
        if (getCollapsingToolbarLayout() != null) {
            getCollapsingToolbarLayout().setTitle("");
        }
        // Retrieve the Toolbar from our content view, and set it as the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");

        //LANDSCAPE
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Hide the appbar in landscape, it is still possible to scroll it down
            if (getAppBar() != null)
                getAppBar().setExpanded(false);
            setAppBarLayoutHeight(70);
        }
        //PORTRAIT
        else {
            setAppBarLayoutHeight(30);
        }
    }

    /**
     * @param divide Set AppBar height to screen height divided by 2->5
     */
    protected void setAppBarLayoutHeightScreenDivide(@IntRange(from = 2, to = 5) int divide) {
        setAppBarLayoutHeight(100 / divide);
    }

    /**
     * @param percent Set AppBar height to 20->50% of screen height
     */
    protected void setAppBarLayoutHeight(@IntRange(from = 20, to = 80) int percent) {
        setAppBarLayoutHeight(percent / 100F);
    }

    @Nullable
    protected AppBarLayout getAppBar() {
        if (appbar == null) appbar = (AppBarLayout) findViewById(R.id.appbar);
        return appbar;
    }

    /**
     * @param weight Set AppBar height to 0.2->0.5 weight of screen height
     */
    protected void setAppBarLayoutHeight(@FloatRange(from = 0.2F, to = 0.5F) float weight) {
        if (getAppBar() != null) {
            ViewGroup.LayoutParams params = getAppBar().getLayoutParams();
            params.height = Math.round(getResources().getDisplayMetrics().heightPixels * weight);
            getAppBar().setLayoutParams(params);
        }
    }

    @Nullable
    protected CollapsingToolbarLayout getCollapsingToolbarLayout() {
        if (collapsingAppBarLayout == null)
            collapsingAppBarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_appbar);
        return collapsingAppBarLayout;
    }

    /******************************Collepsing Toolbar (End)*********************************/

    public void checkAll(View v) {
        switch (v.getId()) {
            case R.id.check_all_1:
                weekdaysDataSource1.selectAll(((CheckBox) v).isChecked());
                break;
        }
    }

    private String getSelectedDaysFromWeekdaysData(ArrayList<WeekdaysDataItem> items) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean selected = false;
        for (WeekdaysDataItem dataItem : items) {
            if (dataItem.isSelected()) {
                selected = true;
                stringBuilder.append(dataItem.getLabel());
                stringBuilder.append(", ");
            }
        }
        if (selected) {
            String result = stringBuilder.toString();
            return result.substring(0, result.lastIndexOf(","));
        } else return "No days selected";
    }

    public void showSnackbarShort(String message) {
//        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_create_package_main);
//        if (relativeLayout != null)
//            showSnackbar(relativeLayout, message, null, Snackbar.LENGTH_SHORT, null);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.col);
        if (coordinatorLayout != null)
            showSnackbar(coordinatorLayout, message, null, Snackbar.LENGTH_SHORT, null);
    }

    public Snackbar showSnackbar(@NonNull ViewGroup viewGroup, String message, String action,
                                 int length, View.OnClickListener onActionListener) {
        Snackbar snackbar = Snackbar.make(viewGroup, message, length);
        if (!TextUtils.isEmpty(action) && onActionListener != null)
            snackbar.setAction(action, onActionListener);
        snackbar.show();
        return snackbar;
    }


    @Override
    public void onWeekdaysItemClicked(int attachId, WeekdaysDataItem item) {


        //Filter on the attached id if there is multiple weekdays data sources
        if (attachId == R.id.weekdays_recycler_view) {
            Calendar calendar = Calendar.getInstance();
            int today = calendar.get(Calendar.DAY_OF_WEEK);
            if (item.getCalendarDayId() == today && item.isSelected()) {

                Log.e("WeekDays attachId:", "" + attachId);
                Log.e("WeekDays today:", "" + today);
                Log.e("WeekDays item.getCalendarDayId():", "" + item.getCalendarDayId());
            }
//                Toast.makeText(CreatePackageActivity.this, "Carpe diem", Toast.LENGTH_SHORT).show();


        }


    }

    @Override
    public void onWeekdaysSelected(int attachId, ArrayList<WeekdaysDataItem> items) {
        String selectedDays = getSelectedDaysFromWeekdaysData(items);
        if (!TextUtils.isEmpty(selectedDays)) {

//            showSnackbarShort(selectedDays);
            Log.e("selectedDays toString:", "" + selectedDays.toString());
            Log.e("selectedDays :", "" + selectedDays);
        }
    }


    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {

        if (Str_Time.equalsIgnoreCase("From")) {
            Log.e("Str_Time :", "" + Str_Time);

            Calendar cal = new java.util.GregorianCalendar();
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            Tv_select_time_from.setText("Time set: " + DateFormat.getTimeFormat(this).format(cal.getTime()));
            Str_Get_package_available_from_time = DateFormat.getTimeFormat(this).format(cal.getTime());

        } else if (Str_Time.equalsIgnoreCase("TO")) {
            Log.e("Str_Time :", "" + Str_Time);
            Calendar cals = new java.util.GregorianCalendar();
            cals.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cals.set(Calendar.MINUTE, minute);
            Tv_select_time_to.setText("Time set: " + DateFormat.getTimeFormat(this).format(cals.getTime()));
            Str_Get_package_available_to_time = DateFormat.getTimeFormat(this).format(cals.getTime());
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String date = "From- " +
                dayOfMonth + "/" + (++monthOfYear) + "/" + year
                + " To " +
                dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd;


        Tv_select_date_range.setText(date);

        Str_Date_From = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        Str_Date_To = dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd;
        Str_Get_package_offer_valid_from_date = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        Str_Get_package_offer_valid_to_date = dayOfMonthEnd + "/" + (++monthOfYearEnd) + "/" + yearEnd;

        Log.e("Str_Date_From :", "" + Str_Date_From);
        Log.e("Str_Date_To :", "" + Str_Date_To);
        Log.e("Str_Get_package_offer_valid_from_date :", "" + Str_Get_package_offer_valid_from_date);
        Log.e("Str_Get_package_offer_valid_to_date :", "" + Str_Get_package_offer_valid_to_date);
        Log.e("Full Date :", "" + date);
    }


    /*using Fast Android Networking */
    private void Get_MerchantDetail_Fast() {

        Log.e("Get_MerchantDetail_Fast ********************* :", "*************************");

        Log.e("merchantID :", "" + Sh_pre_User_ID);
        String tag_string_req = "req_login";
        pDialog.setMessage("Logging in ...");
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
                                Str_Get_business_id = jobjresult.getString("business_id");

                                Log.e("business_id :", "" + Str_Get_business_id);

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


    private void GET_Business_Detail_Fast() {
        final String user_id = Sh_pre_User_ID;

        Log.e("Volley GET_Business_Detail On Create Package_Fast Data :", ""
                        + "\n" + "user_id :" + "" + user_id
//                + "\n" + "user_password" + "" + user_password
        );

        // Tag used to cancel the request
        String tag_string_req = "req_registration";
        pDialog.setMessage("Please Wait.....");
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
                        STr_Get_stetus = jobjGetresult.getString("stetus");
                        STr_Get_image1 = jobjGetresult.getString("image1");
                        hideDialog();
                    }


                } catch (JSONException e) {
                    hideDialog();
                    e.printStackTrace();
                }

//                Log.e("response :", "" + response);

                if (STr_Get_BUSINESS_DETAIL_status.equalsIgnoreCase("1")) {
                    Log.e("Get_Merchant_Business_detail_status is:", "1");
                    Log.e("STr_Get_stetus ", "" + STr_Get_stetus);
                    Glide.with(Iv_business_image_package.getContext())
                            .load(STr_Get_image1)
                            .asBitmap().centerCrop()
                            .crossFade()
                            .into(new BitmapImageViewTarget(Iv_business_image_package) {
                                @Override
                                public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                    super.onResourceReady(bitmap, anim);
                                    Glide.with(Iv_business_image_package
                                            .getContext())
                                            .load(STr_Get_image1)
                                            .centerCrop()
                                            .crossFade()
                                            .into(Iv_business_image_package);
                                }
                            });


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

                        new SweetAlertDialog(CreatePackageActivity.this, SweetAlertDialog.ERROR_TYPE)
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


    private void Create_Packages_Volley() {

        Log.e("Volley Create_Packages Data :", ""
                + "Str_Get_business_id :" + "" + Str_Get_business_id + "\n"
                + "Sh_pre_User_ID :" + "" + Sh_pre_User_ID + "\n"
                + "Str_Get_package_name :" + "" + Str_Get_package_name + "\n"
                + "Str_Get_package_detail :" + "" + Str_Get_package_detail + "\n"
                + "Str_Get_package_current_price :" + "" + Str_Get_package_current_price + "\n"
                + "Str_Get_package_old_price :" + "" + Str_Get_package_old_price + "\n"
                + "Str_Get_package_availabe_status :" + "" + Str_Get_package_availabe_status + "\n"
                + "Str_Get_package_quantity_valid :" + "" + Str_Get_package_quantity_valid + "\n"
                + "Str_Get_package_available_days :" + "" + Str_Get_package_available_days + "\n"
                + "Str_Get_package_home_delivery :" + "" + Str_Get_package_home_delivery + "\n"
                + "Str_Get_package_offer_valid_from_date :" + "" + Str_Get_package_offer_valid_from_date + "\n"
                + "Str_Get_package_offer_valid_to_date :" + "" + Str_Get_package_offer_valid_to_date + "\n"
                + "Str_Get_package_available_from_time :" + "" + Str_Get_package_available_from_time + "\n"
                + "Str_Get_package_available_to_time :" + "" + Str_Get_package_available_to_time + "\n");

        // Tag used to cancel the request
        String tag_string_req = "req_registration";
        pDialog.setMessage("Please Wait.....");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlPath.urlPathMain + CREATE_PACKAGES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(MainVolleyActivity.this,response,Toast.LENGTH_LONG).show();
                        hideDialog();
                        Log.e("Response :", "" + response);
                        try {
                            JSONObject jobjresponse = new JSONObject(response);
                            Log.e("jobjresponse :", "" + jobjresponse);

                            Str_Get_Create_package_status = jobjresponse.getString("status");
                            Log.e("Str_Get_Create_package_status :", "" + Str_Get_Create_package_status);

                            Str_Get_Create_package_message = jobjresponse.getString("message");
                            Log.e("Str_Get_Create_package_message is:", "" + Str_Get_Create_package_message);


                            if (Str_Get_Create_package_status.equalsIgnoreCase("1")) {
                                Log.e("StrGet_status is:", "1");

                                JSONObject jobjresult = jobjresponse.getJSONObject("result");
                                Str_Get_Create_package_result = jobjresult.toString();
                                Log.e("Str_Get_Create_package_result :", "" + Str_Get_Create_package_result);

                                Str_Get_Create_package_package_id = jobjresult.getString("id");
                                Str_Get_Create_package_business_id = jobjresult.getString("business_id");
                                Str_Get_Create_package_package_name = jobjresult.getString("package_name");
                                Str_Get_Create_package_package_detail = jobjresult.getString("package_detail");
                                Str_Get_Create_package_stock_status = jobjresult.getString("stock_status");
                                Str_Get_Create_package_valid_parsons = jobjresult.getString("valid_parsons");
                                Str_Get_Create_package_valid_day = jobjresult.getString("valid_day");
                                Str_Get_Create_package_old_price = jobjresult.getString("old_price");
                                Str_Get_Create_package_new_price = jobjresult.getString("new_price");
                                Str_Get_Create_package_home_delivery = jobjresult.getString("home_delivery");
                                Str_Get_Create_package_offer_validity_from = jobjresult.getString("offer_validity_from");
                                Str_Get_Create_package_offer_validity_to = jobjresult.getString("offer_validity_to");
                                Str_Get_Create_package_timing_from = jobjresult.getString("timing_from");
                                Str_Get_Create_package_timing_to = jobjresult.getString("timing_to");


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("response :", "" + response);

                        if (Str_Get_Create_package_status.equalsIgnoreCase("1")) {
                            Log.e("Str_Get_Create_package_status is:", "1");


                            Intent GoBusinesScreen = new Intent(CreatePackageActivity.this, BusinessMainActivity.class);
                            startActivity(GoBusinesScreen);
                            finish();
                        } else if (Str_Get_Create_package_status.equalsIgnoreCase("0")) {
                            Log.e("Str_Get_Create_package_status is:", "0");

//                            Toast.makeText(LoginActivity.this, "User detail not found", Toast.LENGTH_LONG).show();

                            new SweetAlertDialog(CreatePackageActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Logine Error!")
                                    .setContentText("User detail not found")
                                    .show();


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
//                        Toast.makeText(LoginActivity.this, "Server error try again", Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());

                        new SweetAlertDialog(CreatePackageActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Server error try again")
                                .show();

//                        TV_volley.setText(error.toString());
                    }
                }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("business_id", Str_Get_business_id);
                params.put("package_name", Str_Get_package_name);
                params.put("package_detail", Str_Get_package_detail);
                params.put("stock_status", Str_Get_package_availabe_status);
                params.put("valid_parsons", Str_Get_package_quantity_valid);
                params.put("valid_day", Str_Get_package_available_days);
                params.put("old_price", Str_Get_package_old_price);
                params.put("new_price", Str_Get_package_current_price);
                params.put("home_delivery", Str_Get_package_home_delivery);
                params.put("offer_validity_from", Str_Get_package_offer_valid_from_date);
                params.put("offer_validity_to", Str_Get_package_offer_valid_to_date);
                params.put("timing_from", Str_Get_package_available_from_time);
                params.put("timing_to", Str_Get_package_available_to_time);
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


        Log.e("onBackPressed go main screen :", "OK");
        Intent GoBusinessScreen = new Intent(getApplicationContext(), BusinessMainActivity.class);
        startActivity(GoBusinessScreen);
        finish();


    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
