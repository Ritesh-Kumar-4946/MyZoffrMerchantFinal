package in.myzoffrmerchants.main_activities;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.aakira.expandablelayout.Utils;
import com.haozhang.lib.SlantedTextView;
import com.hlab.fabrevealmenu.enums.Direction;
import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener;
import com.hlab.fabrevealmenu.view.FABRevealMenu;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.yalantis.guillotine.util.GuillotineInterpolator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import in.myzoffrmerchants.Beans.BeanPackagesList;
import in.myzoffrmerchants.Constant.Appconstant;
import in.myzoffrmerchants.Constant.HttpUrlPath;
import in.myzoffrmerchants.R;

/**
 * Created by ritesh on 19/7/17.
 */

public class BusinessMainActivity extends AppCompatActivity implements View.OnClickListener,
        OnFABMenuSelectedListener {

//    @BindView(R.id.toolbar_business_detail)
//    Toolbar Toolbar_business_detail;


    private static final int TIME_DELAY = 2000;
    private static final String GET_BUSINESS_DETAIL = "get_business_and_package?";
    private static final String DELETE_ALL_PACKAGES = "delete_multiple_package?";
    private static final String DELETE_SINGLE_PACKAGE = "delete_single_package?";


//    @BindView(R.id.rl_dr_home)
//    RelativeLayout Rl_dr_home;

    //    @BindView(R.id.rl_dr_my_business)
//    RelativeLayout Rl_dr_my_business;
    private static final String BLANK_IMAGE = "http://whatsapphindistatus.com/ZOF/uploads/images/";
    private static final String MERCHANT_DETAIL_URL = "get_profile?";
    private static long back_pressed;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawerLayout_business)
    DrawerLayout mDrawerLayout_drawerLayout_business;
    @BindView(R.id.iv_dr_profile_image)
    CircleImageView CIV_profile_image;
    @BindView(R.id.rl_dr_profile)
    RelativeLayout Rl_dr_profile;
    @BindView(R.id.rl_dr_my_orders)
    RelativeLayout Rl_dr_my_orders;
    @BindView(R.id.rl_dr_payment)
    RelativeLayout Rl_dr_payment;
    @BindView(R.id.rl_dr_notification)
    RelativeLayout Rl_dr_notification;
    @BindView(R.id.rl_dr_feedback)
    RelativeLayout Rl_dr_feedback;
    @BindView(R.id.rl_dr_rate_us)
    RelativeLayout Rl_dr_rate_us;
    @BindView(R.id.rl_dr_refer_earn)
    RelativeLayout Rl_dr_refer_earn;
    @BindView(R.id.rl_dr_help_support)
    RelativeLayout Rl_dr_help_support;
    @BindView(R.id.rl_dr_about_us)
    RelativeLayout Rl_dr_about_us;
    @BindView(R.id.stv_varify)
    SlantedTextView Stv_varify;
    @BindView(R.id.stv_not_varify)
    SlantedTextView Stv_not_varify;
    @BindView(R.id.stv_business_varify)
    SlantedTextView Stv_business_varify;
    @BindView(R.id.stv_business_not_varify)
    SlantedTextView Stv_business_not_varify;
    @BindView(R.id.tv_merchant_dr_name)
    TextView Tv_merchant_dr_name;
    @BindView(R.id.tv_merchant_dr_email)
    TextView Tv_merchant_dr_email;
    @BindView(R.id.tv_merchant_dr_phone_number)
    TextView Tv_merchant_dr_phone_number;
    @BindView(R.id.tv_terms_of_use)
    TextView TV_terms_of_use;
    @BindView(R.id.tv_privacy_policy)
    TextView TV_privacy_policy;
    @BindView(R.id.tv_logout)
    TextView TV_logout;
    @BindView(R.id.iv_business_image_main)
    ImageView Iv_business_image_main;
    @BindView(R.id.iv_business_image_2)
    ImageView Iv_business_image_2;
    @BindView(R.id.iv_business_image_3)
    ImageView Iv_business_image_3;
    @BindView(R.id.iv_business_image_4)
    ImageView Iv_business_image_4;


    //    @BindView(R.id.cv_edit_business)
//    CardView Cv_edit_business;
    @BindView(R.id.cv_create_business)
    CardView Cv_create_business;
    @BindView(R.id.cv_create_packagess)
    CardView Cv_create_packagess;
    @BindView(R.id.tv_text_no_package)
    TextView Tv_text_no_package;
    @BindView(R.id.tv_business_name)
    TextView Tv_business_name;

    //    private ProgressDialog pDialog;
    @BindView(R.id.tv_business_tag_line)
    TextView Tv_business_tag_line;
    @BindView(R.id.tv_business_description)
    TextView Tv_business_description;
    @BindView(R.id.rv_business_package_list)
    RelativeLayout Rv_business_package_list;
    String StrGet_App_Package_Name = "";
    String Sh_pre_User_ID = "",
            StrGet_Merc_status = "",
            StrGet_Merc_message = "",
            StrGet_Merc_result = "",
            StrGet_Merc_id = "",
            StrGet_Merc_username = "",
            StrGet_Merc_mobile = "",
            StrGet_Merc_email = "",
            StrGet_Merc_password = "",
            StrGet_Merc_package_plan = "",
            StrGet_Merc_business_created_status = "",
            StrGet_Merc_image = "",
            Set_Shar_Pref_ok = "OK",
            StrGet_Merc_activation_status = "",
            StrGet_Delete_Single_Package_Status = "",
            StrGet_Delete_Single_Package_Message = "",
            StrGet_Delete_Single_Package_Result = "",
            StrGet_Delete_All_Package_Status = "",
            StrGet_Delete_All_Package_Message = "",
            StrGet_Delete_All_Package_Result = "";
    String
            GetMainCategory_Shr_Pref = "",
            GetSubCategory_Shr_Pref = "",
            GetChildCategory_Shr_Pref = "",
            STr_Get_BUSINESS_DETAIL_status = "",
            STr_Get_BUSINESS_DETAIL_message = "",
            STr_Get_BUSINESS_DETAIL_Merchant_ID = "",
            STr_Get_BUSINESS_DETAIL_Business_ID = "",
            STr_Get_business_name = "",
            STr_Get_sub_name = "",
            STr_Get_tittel_description = "",
            STr_Get_description = "",
            STr_Get_city = "",
            STr_Get_sub_city = "",
            STr_Get_lat = "",
            STr_Get_lon = "",
            Business_ID_Shared_Pref = "",
            STr_Get_image1 = "",
            STr_Get_image2 = "",
            STr_Get_image3 = "",
            STr_Get_image4 = "",
            STr_Get_Business_Activation_stetus = "";
    boolean doubleBackToExitPressedOnce = false;
    List<BusinessMainActivity> rowBusinessPackagesItems;
    List<BeanPackagesList> beanPackage0;
    String Package_ID = "",
            Deleted_Package_ID = "",
            Package_Lists = "",
            Business_id = "",
            Package_name = "",
            Package_detail = "",
            Stock_status = "",
            Valid_parsons = "",
            Valid_days = "",
            Old_price = "",
            New_price = "",
            Timing_from = "",
            Timing_to = "",
            Home_delivery = "",
            Offer_validity_from = "",
            Offer_validity_to = "",
            PackageListComplete = "",
            Str_Get_package_list_result = "",
            Str_Get_package_list_Main_Status = "",
            Str_Get_package_list_Main_message = "",
            Str_Get_package_list_Main_result = "",
            Str_Get_package_list_Result = "",
            Str_Get_package_list = "",
            Str_Get_Business_ID = "",
            Str_Get_package_list_Package_id = "",
            Str_Get_package_list_business_id = "",
            Str_Get_package_list_package_name = "",
            Str_Get_package_list_package_detail = "",
            Str_Get_package_list_stock_status = "",
            Str_Get_package_list_valid_parsons = "",
            Str_Get_package_list_valid_day = "",
            Str_Get_package_list_old_price = "",
            Str_Get_package_list_new_price = "",
            Str_Get_package_list_home_delivery = "",
            Str_Get_package_list_offer_validity_from = "",
            Str_Get_package_list_offer_validity_to = "",
            Str_Get_package_list_timing_to = "",
            Str_Get_package_list_timing_from = "",
            Business_ID_Shar_Pref = "",
            Business_STATUS_Shar_Pref = "";
    @BindView(R.id.rv_business_packages)
    RecyclerView Rv_business_packages;
    @BindView(R.id.expandableLayout1)
    ExpandableRelativeLayout ExpandableLayout1;
    @BindView(R.id.button)
    RelativeLayout button;
    @BindView(R.id.fab_edit_business)
    FloatingActionButton fab_edit_business;
    @BindView(R.id.fabMenu)
    FABRevealMenu fabMenu;
    BusinessMainBaseFragment currentFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private ProgressDialog pDialog;
    private ArrayList<String> package_ID;
    private ArrayList<String> business_id;
    private ArrayList<String> package_name;
    private ArrayList<String> package_detail;
    private ArrayList<String> stock_status;
    private ArrayList<String> valid_parsons;
    private ArrayList<String> valid_day;
    private ArrayList<String> old_price;
    private ArrayList<String> new_price;
    private ArrayList<String> timing_from;
    private ArrayList<String> timing_to;
    private ArrayList<String> home_delivery;
    private ArrayList<String> offer_validity_from;
    private ArrayList<String> offer_validity_to;
    private Direction currentDirection = Direction.LEFT;

    //    final FABRevealMenu fabMenu = (FABRevealMenu) view.findViewById(R.id.fabMenu);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_detail);
        ButterKnife.bind(this);
        AndroidNetworking.initialize(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        Cv_create_business.setVisibility(View.GONE);
        Cv_create_packagess.setVisibility(View.GONE);
        Tv_text_no_package.setVisibility(View.GONE);
        fab_edit_business.setVisibility(View.GONE);
        Stv_varify.setVisibility(View.GONE);
        Stv_not_varify.setVisibility(View.GONE);
        Stv_business_varify.setVisibility(View.GONE);
        Stv_business_not_varify.setVisibility(View.GONE);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        Sh_pre_User_ID = Appconstant.sh.getString("id", null);
        GetMainCategory_Shr_Pref = Appconstant.sh.getString("MAIN_CATEGORY_ID", null);
        GetSubCategory_Shr_Pref = Appconstant.sh.getString("MAIN_SUB_CATEGORY_ID", null);
        GetChildCategory_Shr_Pref = Appconstant.sh.getString("CHILD_CATEGORY_ID", null);
        Business_ID_Shar_Pref = Appconstant.sh.getString("BusinessID", null);
        Business_STATUS_Shar_Pref = Appconstant.sh.getString("BusinessStatus", null);


        Log.e("GetMainCategory_Shr_Pref  from MainCategoryActivity:", "" + GetMainCategory_Shr_Pref);
        Log.e("GetSubCategory_Shr_Pref  from MainCategoryActivity:", "" + GetSubCategory_Shr_Pref);
        Log.e("GetChildCategory_Shr_Pref  from MainCategoryActivity:", "" + GetChildCategory_Shr_Pref);
        Log.e("Business_ID_Shar_Pref :", "" + Business_ID_Shar_Pref);
        Log.e("Business_STATUS_Shar_Pref :", "" + Business_STATUS_Shar_Pref);


        GET_Business_Detail_Fast();

        GetMerchantDetail();


        rowBusinessPackagesItems = new ArrayList<BusinessMainActivity>();
        package_ID = new ArrayList<>();
        business_id = new ArrayList<>();
        package_name = new ArrayList<>();
        package_detail = new ArrayList<>();
        stock_status = new ArrayList<>();
        valid_parsons = new ArrayList<>();
        valid_day = new ArrayList<>();
        old_price = new ArrayList<>();
        new_price = new ArrayList<>();
        timing_from = new ArrayList<>();
        timing_to = new ArrayList<>();
        home_delivery = new ArrayList<>();
        offer_validity_from = new ArrayList<>();
        offer_validity_to = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        Rv_business_packages.setLayoutManager(mLayoutManager);

//        Rv_main_category.addItemDecoration(new EqualSpaceItemDecoration(5));
        Rv_business_packages.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        Rv_business_packages.setNestedScrollingEnabled(false);

        StrGet_App_Package_Name = getPackageName(); // getPackageName() from Context or Activity object
        String Demo = getApplicationContext().getPackageName();

        Log.e("StrGet_App_Package_Name :", "" + StrGet_App_Package_Name);
        Log.e("StrGet_App_Package_Name Demo :", "" + Demo);



        /*Fab menu (Start)*/
        if (currentFragment != null) {
            if (!currentFragment.onBackPressed()) {
                return;
            }
        }


        if (fabMenu != null) {
//            currentDirection = Direction.LEFT;
//            fabMenu.setMenuDirection(Direction.LEFT);

            currentDirection = Direction.UP;
            fabMenu.setMenuDirection(Direction.UP);

        }

        if (fab_edit_business != null && fabMenu != null) {
//            setFabMenu(fabMenu);
            fabMenu.bindAncherView(fab_edit_business);
            fabMenu.setOnFABMenuSelectedListener(BusinessMainActivity.this);
        }
        /*Fab menu (End)*/


        /*image settin by universal image loader */
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(BusinessMainActivity.this).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);
        /*image settin by universal image loader */

        mDrawerToggle = new ActionBarDrawerToggle(
                this,              /* host Activity */
                mDrawerLayout_drawerLayout_business,    /* DrawerLayout object */
                mToolbar,
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */


        );

        mDrawerToggle = new ActionBarDrawerToggle
                (
                        this,
                        mDrawerLayout_drawerLayout_business,
                        mToolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close
                ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().hide();

                /*set drawer close if open (Start)*/
//                if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
//                    mDrawerLayout.closeDrawer(GravityCompat.START);
//                } else {
//                    mDrawerLayout.openDrawer(GravityCompat.START);
//                }
                /*set drawer close if open (End)*/


            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                getSupportActionBar().show();
            }
        };
        mDrawerLayout_drawerLayout_business.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        DrawerwidgetOnclickLisner();


        /*Expandable Layout (Start)*/
        ExpandableLayout1.setExpanded(true); // set default open false
        ExpandableLayout1.setInterpolator(new GuillotineInterpolator()); // set expand animation
        ExpandableLayout1.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(button, 0f, 180f).start();
                ExpandableLayout1.setExpanded(true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(button, 180f, 0f).end();
                ExpandableLayout1.setExpanded(false);
            }

        });

        button.setRotation(true ? 180f : 0f);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpandableLayout1.toggle(); // toggle expand and collapse
            }
        });
        /*Expandable Layout (End)*/


        Cv_create_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent GoCreateBusinessScreen = new Intent(getApplicationContext(), BusinessCreateActivity.class);
                startActivity(GoCreateBusinessScreen);
                finish();

            }
        });


//        fab_edit_business.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Intent GoCreateBusinessScreen = new Intent(getApplicationContext(), CreatePackageActivity.class);
////                startActivity(GoCreateBusinessScreen);
////                finish();
//
//                Intent GoCreateBusinessScreen = new Intent(getApplicationContext(), CreatePackageActivity.class);
//                startActivity(GoCreateBusinessScreen);
//                finish();
//
//            }
//        });


        Cv_create_packagess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent GoCreateBusinessScreen = new Intent(getApplicationContext(), CreatePackageActivity.class);
                startActivity(GoCreateBusinessScreen);
                finish();

            }
        });


    }


    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }


    /*Fab menu (Start)*/
    @Override
    public void onMenuItemSelected(View view) {
        int id = (int) view.getTag();
        if (id == R.id.menu_add_package) {
//            Toast.makeText(getApplicationContext(), "Attachment Selected", Toast.LENGTH_SHORT).show();
            Intent GoCreateBusinessScreen = new Intent(getApplicationContext(), CreatePackageActivity.class);
            startActivity(GoCreateBusinessScreen);
            finish();

        } else if (id == R.id.menu_delete_all_package) {

            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Won't be able to recover this Action!")
                    .setCancelText("No,cancel plx!")
                    .setConfirmText("Yes,delete it!")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            // reuse previous dialog instance, keep widget user state, reset them if you need
                            sDialog.setTitleText("Cancelled!")
                                    .setContentText("Your all packages is safe :)")
                                    .setConfirmText("OK")
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                            Log.e("Cancel Dialog Click :", "hmmm");

                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            DeleteAll_Packages_Fast();
                            Log.e("Ok Dialog Click :", "hmmm");
                        }
                    })
                    .show();


        } else if (id == R.id.menu_edit_business) {

            Intent GoEditBusinessScreen = new Intent(BusinessMainActivity.this, BusinessEditActivity.class);
            overridePendingTransition(R.anim.fade_inn, R.anim.fade_out);
            startActivity(GoEditBusinessScreen);
            finish();


        }
    }
    /*Fab menu (End)*/

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

                            Tv_business_name.setText(STr_Get_business_name);
                            Tv_business_tag_line.setText(STr_Get_sub_name);
                            Tv_business_description.setText(STr_Get_description);

                            if (STr_Get_Business_Activation_stetus.equalsIgnoreCase("Deactive")) {

                                Log.e("Business_Activation_stetus is:", "Deactive");
                                Stv_business_varify.setVisibility(View.GONE);
                                Stv_business_not_varify.setVisibility(View.VISIBLE);


                            } else if (STr_Get_Business_Activation_stetus.equalsIgnoreCase("Active")) {
                                Log.e("Business_Activation_stetus is:", "Active");

                                Stv_business_varify.setVisibility(View.VISIBLE);
                                Stv_business_not_varify.setVisibility(View.GONE);


                            }


                            if (!STr_Get_image1.equalsIgnoreCase(BLANK_IMAGE)) {

                                Iv_business_image_main.setVisibility(View.VISIBLE);

                                Glide.with(Iv_business_image_main.getContext())
                                        .load(STr_Get_image1)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .into(new BitmapImageViewTarget(Iv_business_image_main) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_business_image_main
                                                        .getContext())
                                                        .load(STr_Get_image1)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_business_image_main);
                                            }
                                        });

                            } else {
                                Iv_business_image_main.setVisibility(View.GONE);
                            }


                            if (!STr_Get_image2.equalsIgnoreCase(BLANK_IMAGE)) {

                                Iv_business_image_2.setVisibility(View.VISIBLE);

                                Glide.with(Iv_business_image_2.getContext())
                                        .load(STr_Get_image2)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .into(new BitmapImageViewTarget(Iv_business_image_2) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_business_image_2
                                                        .getContext())
                                                        .load(STr_Get_image2)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_business_image_2);
                                            }
                                        });

                            } else {
                                Iv_business_image_2.setVisibility(View.GONE);
                            }


                            if (!STr_Get_image3.equalsIgnoreCase(BLANK_IMAGE)) {

                                Iv_business_image_3.setVisibility(View.VISIBLE);

                                Glide.with(Iv_business_image_3.getContext())
                                        .load(STr_Get_image3)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .into(new BitmapImageViewTarget(Iv_business_image_3) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_business_image_3
                                                        .getContext())
                                                        .load(STr_Get_image3)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_business_image_3);
                                            }
                                        });

                            } else {
                                Iv_business_image_3.setVisibility(View.GONE);
                            }


                            if (!STr_Get_image4.equalsIgnoreCase(BLANK_IMAGE)) {

                                Iv_business_image_4.setVisibility(View.VISIBLE);

                                Glide.with(Iv_business_image_3.getContext())
                                        .load(STr_Get_image4)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .into(new BitmapImageViewTarget(Iv_business_image_4) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(Iv_business_image_4
                                                        .getContext())
                                                        .load(STr_Get_image4)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(Iv_business_image_4);
                                            }
                                        });

                            } else {
                                Iv_business_image_4.setVisibility(View.GONE);
                            }

                        } else {
                            Log.e("Get_Merchant_Business_detail_status is:", "0");
                        }


                        hideDialog();
                    }


                } catch (JSONException e) {
                    hideDialog();
                    e.printStackTrace();
                }

//                Log.e("response :", "" + response);


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
//                        Toast.makeText(LoginActivity.this, "Server error try again", Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());

                        new SweetAlertDialog(BusinessMainActivity.this, SweetAlertDialog.ERROR_TYPE)
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void DeleteAll_Packages_Fast() {
        final String Business_id = Str_Get_Business_ID;

        Log.e("Volley DeleteAll_Packages_Fast Data :", ""
                        + "\n" + "Business_id :" + "" + Business_id
//                + "\n" + "user_password" + "" + user_password
        );

        // Tag used to cancel the request
        String tag_string_req = "req_registration";
        pDialog.setMessage("Please Wait.....");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlPath.urlPathMain
                + DELETE_ALL_PACKAGES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response :", "" + response);
                try {
                    JSONObject jobjresponse = new JSONObject(response);
                    Log.e("jobjresponse :", "" + jobjresponse);

                    StrGet_Delete_All_Package_Status = jobjresponse.getString("status");
                    Log.e("StrGet_Delete_All_Package_Status :", "" + StrGet_Delete_All_Package_Status);

                    StrGet_Delete_All_Package_Message = jobjresponse.getString("message");
                    Log.e("StrGet_Delete_All_Package_Message :", "" + StrGet_Delete_All_Package_Message);

                    if (StrGet_Delete_All_Package_Status.equalsIgnoreCase("1")) {
                        Log.e("StrGet_Delete_All_Package_Status is:", "1");
                        Log.e("StrGet_Delete_All_Package_Message is:", "" + StrGet_Delete_All_Package_Message);

                        StrGet_Delete_All_Package_Result = jobjresponse.getString("result");

                        if (StrGet_Delete_All_Package_Status.equalsIgnoreCase("1")) {
                            Log.e("StrGet_Delete_All_Package_Status is:", "1");
                            Log.e("StrGet_Delete_All_Package_Status is:", "1");

//                            recyclerView.setAdapter(new RecyclerViewAdapter(newList));
//                            recyclerView.invalidate();
//                            Rv_business_package_list.invalidate();
                            Intent BusinessScreen = new Intent(BusinessMainActivity.this, BusinessMainActivity.class);
                            overridePendingTransition(R.anim.fade_inn, R.anim.fade_out);
                            startActivity(BusinessScreen);
                            finish();
//                            new SweetAlertDialog(BusinessMainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                                    .setTitleText("Deleted!")
//                                    .setContentText("Your all packages has been deleted!")
//                                    .setConfirmText("OK")
//                                    .showCancelButton(false)
//                                    .setCancelClickListener(null)
//                                    .setConfirmClickListener(null)
//                                    .show();


                        } else {
                            Log.e("StrGet_Delete_All_Package_status is:", "0");
                        }


                        hideDialog();
                    }


                } catch (JSONException e) {
                    hideDialog();
                    e.printStackTrace();
                }

//                Log.e("response :", "" + response);


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
//                        Toast.makeText(LoginActivity.this, "Server error try again", Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());

                        new SweetAlertDialog(BusinessMainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Server error try again")
                                .show();

//                        TV_volley.setText(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("business_id", Business_id);
//                params.put("password", user_password);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void DeleteSingle_Packages_Fast() {
        final String Delete_Package_id = Deleted_Package_ID;

        Log.e("Volley Delete Single Package Fast Data :", ""
                        + "\n" + "Deleted_Package_ID :" + "" + Deleted_Package_ID
//                + "\n" + "user_password" + "" + user_password
        );

        // Tag used to cancel the request
        String tag_string_req = "req_registration";
        pDialog.setMessage("Please Wait.....");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlPath.urlPathMain
                + DELETE_SINGLE_PACKAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response :", "" + response);
                try {
                    JSONObject jobjresponse = new JSONObject(response);
                    Log.e("jobjresponse :", "" + jobjresponse);

                    StrGet_Delete_Single_Package_Status = jobjresponse.getString("status");
                    Log.e("StrGet_Delete_Single_Package_Status :", "" + StrGet_Delete_Single_Package_Status);

                    StrGet_Delete_Single_Package_Message = jobjresponse.getString("message");
                    Log.e("StrGet_Delete_Single_Package_Message :", "" + StrGet_Delete_Single_Package_Message);

                    if (StrGet_Delete_Single_Package_Status.equalsIgnoreCase("1")) {
                        Log.e("StrGet_Delete_Single_Package_Status is:", "1");

                        StrGet_Delete_Single_Package_Result = jobjresponse.getString("result");
                        Intent BusinessScreen = new Intent(BusinessMainActivity.this, BusinessMainActivity.class);
                        overridePendingTransition(R.anim.fade_inn, R.anim.fade_out);
                        startActivity(BusinessScreen);
                        finish();
//                        if (StrGet_Delete_Single_Package_Result.equalsIgnoreCase("1")) {
//                            Log.e("StrGet_Delete_Single_Package_Result is:", "1");
//                            Log.e("StrGet_Delete_Single_Package_Result is:", "1");
//
//
//                        } else {
//                            Log.e("StrGet_Delete_All_Package_status is:", "0");
//                        }


                        hideDialog();
                    }
                    Intent BusinessScreen = new Intent(BusinessMainActivity.this, BusinessMainActivity.class);
                    overridePendingTransition(R.anim.fade_inn, R.anim.fade_out);
                    startActivity(BusinessScreen);
                    finish();

                } catch (JSONException e) {
                    hideDialog();
                    Intent BusinessScreen = new Intent(BusinessMainActivity.this, BusinessMainActivity.class);
                    overridePendingTransition(R.anim.fade_inn, R.anim.fade_out);
                    startActivity(BusinessScreen);
                    finish();
                    e.printStackTrace();
                }

//                Log.e("response :", "" + response);


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
//                        Toast.makeText(LoginActivity.this, "Server error try again", Toast.LENGTH_LONG).show();
                        Intent BusinessScreen = new Intent(BusinessMainActivity.this, BusinessMainActivity.class);
                        overridePendingTransition(R.anim.fade_inn, R.anim.fade_out);
                        startActivity(BusinessScreen);
                        finish();
                        Log.e("Error Response :", "" + error.toString());

                        new SweetAlertDialog(BusinessMainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Server error try again")
                                .show();

//                        TV_volley.setText(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("package_table_id", Delete_Package_id);
//                params.put("password", user_password);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void DrawerwidgetOnclickLisner() {
//        Rl_dr_home.setOnClickListener(this);
        Rl_dr_payment.setOnClickListener(this);
        Rl_dr_my_orders.setOnClickListener(this);
        Rl_dr_notification.setOnClickListener(this);
        Rl_dr_profile.setOnClickListener(this);
        Rl_dr_feedback.setOnClickListener(this);
        Rl_dr_rate_us.setOnClickListener(this);
        Rl_dr_refer_earn.setOnClickListener(this);
        Rl_dr_help_support.setOnClickListener(this);
        Rl_dr_about_us.setOnClickListener(this);
        TV_terms_of_use.setOnClickListener(this);
        TV_privacy_policy.setOnClickListener(this);
        TV_logout.setOnClickListener(this);
        CIV_profile_image.setOnClickListener(this);
        Stv_varify.setOnClickListener(this);
        Stv_not_varify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

//            case R.id.rl_dr_home:
            // do your code
//                Toast.makeText(MainCategoryActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
//                IV_home.setImageResource(R.drawable.ic_dr_home_click);
//                TV_home.setTextColor(ContextCompat.getColor(MainCategoryActivity.this, R.color.red));
//                Intent GoCreateBusinessScreen = new Intent(getApplicationContext(), BusinessCreateActivity.class);
//                startActivity(GoCreateBusinessScreen);

//                break;

            case R.id.rl_dr_payment:
                // do your code
//                Toast.makeText(MainCategoryActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
//                IV_home.setImageResource(R.drawable.ic_dr_home_click);
//                TV_home.setTextColor(ContextCompat.getColor(MainCategoryActivity.this, R.color.red));

//                Intent GoCreateBusinessScreen = new Intent(BusinessMainActivity.this, BusinessCreateActivity.class);
//                startActivity(GoCreateBusinessScreen);
//                finish();
                Toast.makeText(BusinessMainActivity.this, "currently you have no payment detail", Toast.LENGTH_SHORT).show();

                break;

            case R.id.rl_dr_my_orders:
                // do your code
                Toast.makeText(BusinessMainActivity.this, "You have no Order right now", Toast.LENGTH_SHORT).show();
//                IV_my_orders.setImageResource(R.drawable.ic_dr_my_orders_click);
//                TV_my_oders.setTextColor(ContextCompat.getColor(MainCategoryActivity.this, R.color.red));
                break;


            case R.id.rl_dr_notification:
                // do your code
                Toast.makeText(BusinessMainActivity.this, "no Notification is pending", Toast.LENGTH_SHORT).show();
//                IV_notification.setImageResource(R.drawable.ic_dr_notification_click);
//                TV_notification.setTextColor(ContextCompat.getColor(MainCategoryActivity.this, R.color.red));
                break;

            case R.id.rl_dr_profile:
                // do your code
//                Toast.makeText(MainCategoryActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                Intent GoMainScreen = new Intent(getApplicationContext(), ProfileGetActivity.class);
                startActivity(GoMainScreen);
                finish();


//                if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
//                    mDrawerLayout.closeDrawer(GravityCompat.START);
//                } else {
//                    mDrawerLayout.openDrawer(GravityCompat.START);
//                }


//                IV_profile.setImageResource(R.drawable.ic_dr_profile_click);
//                TV_profile.setTextColor(ContextCompat.getColor(MainCategoryActivity.this, R.color.red));
                break;


            case R.id.rl_dr_feedback:
                // do your code
                Toast.makeText(BusinessMainActivity.this, "Feedback Coming Soon", Toast.LENGTH_SHORT).show();
//                IV_feedback.setImageResource(R.drawable.ic_dr_feedback_click);
//                TV_feedback.setTextColor(ContextCompat.getColor(MainCategoryActivity.this, R.color.red));

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + StrGet_App_Package_Name)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + StrGet_App_Package_Name)));
                }


                break;

            case R.id.rl_dr_rate_us:
                // do your code
//                Toast.makeText(MainCategoryActivity.this, "Rate Us Coming Soon", Toast.LENGTH_SHORT).show();
//                IV_rate_us.setImageResource(R.drawable.ic_dr_rate_us_click);
//                TV_rate_us.setTextColor(ContextCompat.getColor(MainCategoryActivity.this, R.color.red));

                /*this is also working (Start)*/
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + StrGet_App_Package_Name)));
//                } catch (android.content.ActivityNotFoundException anfe) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + StrGet_App_Package_Name)));
//                }
                /*this is also working (End)*/


                Uri uri = Uri.parse("market://details?id=" + StrGet_App_Package_Name);
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + StrGet_App_Package_Name)));
                }


                break;

            case R.id.rl_dr_refer_earn:
                // do your code
                Toast.makeText(BusinessMainActivity.this, "Refer & Earn Coming Soon", Toast.LENGTH_SHORT).show();
//                IV_refer_earn.setImageResource(R.drawable.ic_dr_share_app_click);
//                TV_refer_earn.setTextColor(ContextCompat.getColor(MainCategoryActivity.this, R.color.red));
                break;

            case R.id.rl_dr_help_support:
                // do your code
//                Toast.makeText(MainCategoryActivity.this, "Help & Support Clicked", Toast.LENGTH_SHORT).show();
//                IV_help_support.setImageResource(R.drawable.ic_dr_customer_service_click);
//                TV_help_support.setTextColor(ContextCompat.getColor(MainCategoryActivity.this, R.color.red));

                Intent GoHelpSupport = new Intent(BusinessMainActivity.this, HelpSupportActivity.class);
                startActivity(GoHelpSupport);


                break;

            case R.id.rl_dr_about_us:
                // do your code
                Toast.makeText(BusinessMainActivity.this, "About Us Coming Soon", Toast.LENGTH_SHORT).show();
//                IV_about_us.setImageResource(R.drawable.ic_dr_about_us_click);
//                TV_about_us.setTextColor(ContextCompat.getColor(MainCategoryActivity.this, R.color.red));
                break;

            case R.id.tv_terms_of_use:
                // do your code
                Toast.makeText(BusinessMainActivity.this, "Terms of used Coming Soon", Toast.LENGTH_SHORT).show();
//                TV_terms_of_use.setTextColor(ContextCompat.getColor(MainCategoryActivity.this, R.color.red));
                break;

            case R.id.tv_privacy_policy:
                // do your code
                Toast.makeText(BusinessMainActivity.this, "Privacy Policy Coming Soon", Toast.LENGTH_SHORT).show();

//                TV_privacy_policy.setTextColor(ContextCompat.getColor(MainCategoryActivity.this, R.color.red));
                break;

            case R.id.tv_logout:
                // do your code
//                Toast.makeText(MainCategoryActivity.this, "Logout Clicked", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(BusinessMainActivity.this);
                builder.setMessage("Are you sure you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Appconstant.editor.remove("loginTest");
                                Appconstant.editor.clear();
                                Appconstant.editor.commit();
                                Toast.makeText(getApplicationContext(), "You have successfully logout",
                                        Toast.LENGTH_SHORT).show();
                                Intent GoLoginscreen = new Intent(BusinessMainActivity.this, LoginActivity.class);
                                GoLoginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                GoLoginscreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(GoLoginscreen);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setBackgroundColor(Color.TRANSPARENT);
                nbutton.setTextColor(Color.BLACK);
                Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setTextColor(Color.BLACK);
                pbutton.setBackgroundColor(Color.TRANSPARENT);


//                TV_logout.setTextColor(ContextCompat.getColor(MainCategoryActivity.this, R.color.red));
                break;


            case R.id.stv_varify:
                // do your code

                Log.e("Varify click :", "OK");
                Toast.makeText(BusinessMainActivity.this, "Profile Demo", Toast.LENGTH_SHORT).show();

                break;


            case R.id.stv_not_varify:
                // do your code
                Log.e("Not Varify click :", "OK");
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Verification Pending!")
                        .setContentText("It will take 4-7 working days")
                        .setConfirmText("OK")
                        .setConfirmClickListener(null)
                        .show();

                break;


            case R.id.iv_dr_profile_image:
                // do your code
                Log.e("Profile Image click :", "OK");
//                Toast.makeText(BusinessMainActivity.this, "Profile image click", Toast.LENGTH_SHORT).show();

                break;

            default:
                break;


        }
    }

    private void GetMerchantDetail() {
        final String user_id = Sh_pre_User_ID;

        Log.e("Volley GetMerchantDetail Data ************ :", ""
                + "\n" + "user_id :" + "" + user_id
        );

        // Tag used to cancel the request
        String tag_string_req = "req_registration";
        pDialog.setMessage("Please Wait.....");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlPath.urlPathMain
                + MERCHANT_DETAIL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                        Toast.makeText(MainVolleyActivity.this,response,Toast.LENGTH_LONG).show();

                Log.e("Response :", "" + response);
                try {
                    JSONObject jobjresponse = new JSONObject(response);
                    Log.e("jobjresponse :", "" + jobjresponse);

                    StrGet_Merc_status = jobjresponse.getString("status");
                    Log.e("StrGet_Merc_status :", "" + StrGet_Merc_status);
                    if (StrGet_Merc_status.equalsIgnoreCase("1")) {
                        Log.e("StrGet_Merc_status jobjresponse is:", "1");

                        StrGet_Merc_message = jobjresponse.getString("message");
//                        Log.e("StrGet_Merc_message is:", "" + StrGet_Merc_message);

                        JSONObject jobjresult = jobjresponse.getJSONObject("result");
                        StrGet_Merc_result = jobjresult.toString();
                        Log.e("StrGet_Merc_result :", "" + StrGet_Merc_result);
//                        Log.e("StrGet_Merc_result jobjresult :", "" + jobjresult);

                        StrGet_Merc_id = jobjresult.getString("id");
                        Log.e("StrGet_Merc_id is:", "" + StrGet_Merc_id);

                        StrGet_Merc_username = jobjresult.getString("username");
//                        Log.e("StrGet_Merc_username is:", "" + StrGet_Merc_username);

                        StrGet_Merc_mobile = jobjresult.getString("mobile");
//                        Log.e("StrGet_Merc_mobile is:", "" + StrGet_Merc_mobile);

                        StrGet_Merc_email = jobjresult.getString("email");
//                        Log.e("StrGet_Merc_email is:", "" + StrGet_Merc_email);

                        StrGet_Merc_business_created_status = jobjresult.getString("business_status");
//                        Log.e("StrGet_Merc_business_created_status is:", "" + StrGet_Merc_business_created_status);

                        StrGet_Merc_image = jobjresult.getString("marchant_img");
//                        Log.e("StrGet_Merc_image is:", "" + StrGet_Merc_image);

                        StrGet_Merc_activation_status = jobjresult.getString("status");
//                        Log.e("StrGet_Merc_activation_status is:", "" + StrGet_Merc_activation_status);


                        Str_Get_Business_ID = jobjresult.getString("business_id");
                        Log.e("Merchant Business_ID :", "" + Str_Get_Business_ID);

                        hideDialog();


                        if (StrGet_Merc_status.equalsIgnoreCase("1")) {
                            Log.e("StrGet_status if is:", "1");


                            Tv_merchant_dr_name.setText(StrGet_Merc_username);
                            Tv_merchant_dr_phone_number.setText(StrGet_Merc_mobile);
                            Tv_merchant_dr_email.setText(StrGet_Merc_email);

                            if (StrGet_Merc_activation_status.equalsIgnoreCase("Active")) {
                                Log.e("Merchant_activation_status is:", "Active");

                                Stv_varify.setVisibility(View.VISIBLE);
                                Stv_not_varify.setVisibility(View.GONE);

                            } else if (StrGet_Merc_activation_status.equalsIgnoreCase("Deactive")) {
                                Log.e("Merchant_activation_status is:", "Deactive");
                                Stv_varify.setVisibility(View.GONE);
                                Stv_not_varify.setVisibility(View.VISIBLE);

                            }

                            if (StrGet_Merc_business_created_status.equalsIgnoreCase("YES")) {
                                Log.e("StrGet_Merc_activation_status is:", "YES");
                                fab_edit_business.setVisibility(View.VISIBLE);
                                Cv_create_business.setVisibility(View.GONE);


                            } else {
                                Log.e("StrGet_Merc_activation_status is:", "NO");
                                Cv_create_business.setVisibility(View.VISIBLE);
                                fab_edit_business.setVisibility(View.GONE);
                            }


                            if (!StrGet_Merc_image.equalsIgnoreCase("")) {
                                Log.e("StrGet_Merc_image found:", "" + StrGet_Merc_image);


                                Glide.with(CIV_profile_image.getContext())
                                        .load(StrGet_Merc_image)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(CIV_profile_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(CIV_profile_image
                                                        .getContext())
                                                        .load(StrGet_Merc_image)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(CIV_profile_image);
                                            }
                                        });


                            } else {
                                Log.e("StrGet_Merc_image Not found:", "OK");


                                Glide.with(CIV_profile_image.getContext())
                                        .load(R.drawable.ic_the_app_guru)
                                        .asBitmap().centerCrop()
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(new BitmapImageViewTarget(CIV_profile_image) {
                                            @Override
                                            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                                super.onResourceReady(bitmap, anim);
                                                Glide.with(CIV_profile_image
                                                        .getContext())
                                                        .load(R.drawable.ic_the_app_guru)
                                                        .centerCrop()
                                                        .crossFade()
                                                        .into(CIV_profile_image);
                                            }
                                        });


                            }


                            PackageListJsontask task = new PackageListJsontask();
                            task.execute();


                        } else if (StrGet_Merc_status.equalsIgnoreCase("0")) {
                            hideDialog();

                            PackageListJsontask task = new PackageListJsontask();
                            task.execute();
                            new SweetAlertDialog(BusinessMainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error!")
                                    .setContentText("User detail not found")
                                    .show();
                        }


                    }


                } catch (JSONException e) {
                    hideDialog();
                    e.printStackTrace();
                }

//                Log.e("response :", "" + response);


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
//                        Toast.makeText(LoginActivity.this, "Server error try again", Toast.LENGTH_LONG).show();
                        Log.e("Error Response :", "" + error.toString());

                        new SweetAlertDialog(BusinessMainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Server error try again")
                                .show();

//                        TV_volley.setText(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", user_id);
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

    private class PackageListJsontask extends AsyncTask<String, Void, List<BeanPackagesList>> {

        boolean iserror = false;
        String result = " ";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            Log.e("*********PackageListJsontask ************:", "OK");
            Log.e("onPreExecute Merchant ID :", "" + Sh_pre_User_ID);
        }

        @Override
        protected List<BeanPackagesList> doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPathMain + GET_BUSINESS_DETAIL + "marchent_id=" + Sh_pre_User_ID);

            Log.e(" PackageList URL :", " " + HttpUrlPath.urlPathMain + GET_BUSINESS_DETAIL + "marchent_id=" + Sh_pre_User_ID);

            try {
                HttpResponse response = client.execute(post);
                String obj = EntityUtils.toString(response.getEntity());
                beanPackage0 = new ArrayList<>();
                Log.e("************ Merchant list ************", "" + obj);

                JSONObject jsonObject = new JSONObject(obj);
                Str_Get_package_list_Main_Status = jsonObject.getString("status");
                Str_Get_package_list_Main_message = jsonObject.getString("message");
                Log.e("Main_Status :", " " + Str_Get_package_list_Main_Status);

                if (Str_Get_package_list_Main_Status.equalsIgnoreCase("1")) {
                    Str_Get_package_list_Result = jsonObject.getString("result");
                    Log.e("Result :", "" + Str_Get_package_list_Result);
                    JSONObject jObjResult = jsonObject.optJSONObject("result");
                    Log.e("jObjResult :", "" + jObjResult);
                    JSONArray jArrayPkgPlan = jObjResult.getJSONArray("package_plan");
                    Log.e("jArrayPkgPlan :", "" + jArrayPkgPlan);


                    for (int i = 0; i < jArrayPkgPlan.length(); i++) {
                        Str_Get_package_list_result = jArrayPkgPlan.getJSONObject(i).getString("result");

                        if (Str_Get_package_list_result.equalsIgnoreCase("success")) {

                            BeanPackagesList beanPackagesList = new BeanPackagesList();
                            beanPackagesList.setPackage_ID(jArrayPkgPlan.getJSONObject(i).getString("id"));
                            beanPackagesList.setBusiness_id(jArrayPkgPlan.getJSONObject(i).getString("business_id"));
                            beanPackagesList.setPackage_name(jArrayPkgPlan.getJSONObject(i).getString("package_name"));
                            beanPackagesList.setPackage_detail(jArrayPkgPlan.getJSONObject(i).getString("package_detail"));
                            beanPackagesList.setStock_status(jArrayPkgPlan.getJSONObject(i).getString("stock_status"));
                            beanPackagesList.setValid_parsons(jArrayPkgPlan.getJSONObject(i).getString("valid_parsons"));
                            beanPackagesList.setValid_day(jArrayPkgPlan.getJSONObject(i).getString("valid_day"));
                            beanPackagesList.setOld_price(jArrayPkgPlan.getJSONObject(i).getString("old_price"));
                            beanPackagesList.setNew_price(jArrayPkgPlan.getJSONObject(i).getString("new_price"));
                            beanPackagesList.setTiming_from(jArrayPkgPlan.getJSONObject(i).getString("timing_from"));
                            beanPackagesList.setTiming_to(jArrayPkgPlan.getJSONObject(i).getString("timing_to"));
                            beanPackagesList.setHome_delivery(jArrayPkgPlan.getJSONObject(i).getString("home_delivery"));
                            beanPackagesList.setOffer_validity_from(jArrayPkgPlan.getJSONObject(i).getString("offer_validity_from"));
                            beanPackagesList.setOffer_validity_to(jArrayPkgPlan.getJSONObject(i).getString("offer_validity_to"));
                            beanPackage0.add(beanPackagesList);


                            Str_Get_package_list_Package_id = jArrayPkgPlan.getJSONObject(i).getString("id");
                            Str_Get_package_list_business_id = jArrayPkgPlan.getJSONObject(i).getString("business_id");
                            Str_Get_package_list_package_name = jArrayPkgPlan.getJSONObject(i).getString("package_name");
                            Str_Get_package_list_package_detail = jArrayPkgPlan.getJSONObject(i).getString("package_detail");
                            Str_Get_package_list_stock_status = jArrayPkgPlan.getJSONObject(i).getString("stock_status");
                            Str_Get_package_list_valid_parsons = jArrayPkgPlan.getJSONObject(i).getString("valid_parsons");
                            Str_Get_package_list_valid_day = jArrayPkgPlan.getJSONObject(i).getString("valid_day");
                            Str_Get_package_list_old_price = jArrayPkgPlan.getJSONObject(i).getString("old_price");
                            Str_Get_package_list_new_price = jArrayPkgPlan.getJSONObject(i).getString("new_price");
                            Str_Get_package_list_timing_from = jArrayPkgPlan.getJSONObject(i).getString("timing_from");
                            Str_Get_package_list_timing_to = jArrayPkgPlan.getJSONObject(i).getString("timing_to");
                            Str_Get_package_list_home_delivery = jArrayPkgPlan.getJSONObject(i).getString("home_delivery");
                            Str_Get_package_list_offer_validity_from = jArrayPkgPlan.getJSONObject(i).getString("offer_validity_from");
                            Str_Get_package_list_offer_validity_to = jArrayPkgPlan.getJSONObject(i).getString("offer_validity_to");


                            package_ID.add(Str_Get_package_list_Package_id);
                            business_id.add(Str_Get_package_list_business_id);
                            package_name.add(Str_Get_package_list_package_name);
                            package_detail.add(Str_Get_package_list_package_detail);
                            stock_status.add(Str_Get_package_list_stock_status);
                            valid_parsons.add(Str_Get_package_list_valid_parsons);
                            valid_day.add(Str_Get_package_list_valid_day);
                            old_price.add(Str_Get_package_list_old_price);
                            new_price.add(Str_Get_package_list_new_price);
                            timing_from.add(Str_Get_package_list_timing_from);
                            timing_to.add(Str_Get_package_list_timing_to);
                            home_delivery.add(Str_Get_package_list_home_delivery);
                            offer_validity_from.add(Str_Get_package_list_offer_validity_from);
                            offer_validity_to.add(Str_Get_package_list_offer_validity_to);


                            Log.e(" package_ID ", "" + package_ID);


                            String package_IDs = package_ID.get(i);
                            String business_ids = business_id.get(i);
                            String package_names = package_name.get(i);
                            String package_details = package_detail.get(i);
                            String stock_statuss = stock_status.get(i);
                            String valid_parsonss = valid_parsons.get(i);
                            String valid_days = valid_day.get(i);
                            String old_prices = old_price.get(i);
                            String new_prices = new_price.get(i);
                            String timing_froms = timing_from.get(i);
                            String timing_tos = timing_to.get(i);
                            String home_deliverys = home_delivery.get(i);
                            String offer_validity_froms = offer_validity_from.get(i);
                            String offer_validity_tos = offer_validity_to.get(i);

                            Log.e("package_IDs ", "" + package_IDs);
                            Log.e("business_ids ", "" + business_ids);
                            Log.e("package_names ", "" + package_names);
                            Log.e("package_detail ", "" + package_details);
                            Log.e("stock_statuss ", "" + stock_statuss);
                            Log.e("valid_parsonss ", "" + valid_parsonss);
                            Log.e("valid_days ", "" + valid_days);
                            Log.e("old_prices ", "" + old_prices);
                            Log.e("new_prices ", "" + new_prices);
                            Log.e("timing_froms ", "" + timing_froms);
                            Log.e("timing_tos ", "" + timing_tos);
                            Log.e("home_deliverys ", "" + home_deliverys);
                            Log.e("offer_validity_froms ", "" + offer_validity_froms);
                            Log.e("offer_validity_tos ", "" + offer_validity_tos);


                        }
                    }
                } else {
                    Log.e("No Data Found :", "OK");
                }


            } catch (Exception e) {
                System.out.println("Errror in gettting by ID" + e);
                Log.e("22", "22" + e.getMessage());
                e.printStackTrace();
                iserror = true;
            }
            return beanPackage0;
        }

        @Override
        protected void onPostExecute(List<BeanPackagesList> mystring) {
            super.onPostExecute(mystring);

            if (package_ID.size() > 0) {
                Log.e(" package_ID  ", "" + package_ID);
                Log.e("package_ID list.size() > 0 ", "YES");
                Cv_create_packagess.setVisibility(View.GONE);
                Tv_text_no_package.setVisibility(View.GONE);
                fab_edit_business.setVisibility(View.VISIBLE);

                PackagelistAdapter packagelistAdapter = new PackagelistAdapter(BusinessMainActivity.this, mystring);
                Rv_business_packages.setAdapter(packagelistAdapter);


            } else {

                Log.e("package_ID size is :", "0");

//                TastyToast.makeText(getApplicationContext(),
//                        "Please Create Business Packages",
//                        TastyToast.LENGTH_LONG, TastyToast.INFO);


                Cv_create_packagess.setVisibility(View.VISIBLE);
                Tv_text_no_package.setVisibility(View.VISIBLE);
                fab_edit_business.setVisibility(View.GONE);

            }
        }

    }

    private class PackagelistAdapter extends RecyclerView.Adapter<PackagelistAdapter.MyViewHolder> {

        private Context mContext;
        private List<BeanPackagesList> arrayList;


        public PackagelistAdapter(Context mContext, List<BeanPackagesList> arrayList) {
            this.mContext = mContext;
            this.arrayList = arrayList;
        }

        @Override
        public PackagelistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rv_business_package_list_item, parent, false);

            return new PackagelistAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final PackagelistAdapter.MyViewHolder holder, final int position) {

            holder.Tv_package_name.setText(Html.fromHtml(arrayList.get(position).getPackage_name()));
            holder.Tv_package_detail.setText(Html.fromHtml(arrayList.get(position).getPackage_detail()));
            holder.Tv_business_package_offer_price_list.setText(Html.fromHtml(arrayList.get(position).getNew_price()));
            holder.Tv_business_package_regular_price_list.setText(Html.fromHtml(arrayList.get(position).getOld_price()));
            holder.Tv_business_package_available_status.setText(Html.fromHtml(arrayList.get(position).getStock_status()));
            holder.Tv_business_package_valid_from_date.setText(Html.fromHtml(arrayList.get(position).getOffer_validity_from()));
            holder.Tv_business_package_valid_to_date.setText(Html.fromHtml(arrayList.get(position).getOffer_validity_to()));
            holder.Tv_business_package_availabel_time_from_time.setText(Html.fromHtml(arrayList.get(position).getTiming_from()));
            holder.Tv_business_package_available_to_time.setText(Html.fromHtml(arrayList.get(position).getTiming_to()));
            holder.Tv_business_package_availabel_days.setText(Html.fromHtml(arrayList.get(position).getValid_day()));
            holder.Tv_quantity.setText(Html.fromHtml(arrayList.get(position).getValid_parsons()));
//

            holder.Tv_business_package_regular_price_list.setPaintFlags(holder.
                    Tv_business_package_regular_price_list.getPaintFlags() |
                    Paint.STRIKE_THRU_TEXT_FLAG);


            holder.Iv_edit_single_package.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Edit Click :", "Yes");
                    Toast.makeText(BusinessMainActivity.this, "Edit Comming Soon", Toast.LENGTH_LONG).show();
                }
            });


            holder.Iv_delete_single_package.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Delete Click :", "Yes");
//                    Toast.makeText(BusinessMainActivity.this, "Comming Soon", Toast.LENGTH_LONG).show();
                    Deleted_Package_ID = arrayList.get(position).getPackage_ID();
                    Log.e("Delete Package_ID :", "" + Package_ID);
//
//                    new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.WARNING_TYPE)
//                            .setTitleText("Are you sure?")
//                            .setContentText("Won't be able to recover this Action!")
//                            .setCancelText("No,cancel plx!")
//                            .setConfirmText("Yes,delete it!")
//                            .showCancelButton(true)
//                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//                                    // reuse previous dialog instance, keep widget user state, reset them if you need
//                                    sDialog.setTitleText("Cancelled!")
//                                            .setContentText("Packages is safe :)")
//                                            .setConfirmText("OK")
//                                            .showCancelButton(false)
//                                            .setCancelClickListener(null)
//                                            .setConfirmClickListener(null)
//                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
//
//                                    Log.e("Cancel Dialog Click :", "hmmm");
//
//                                }
//                            })
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sDialog) {
//                                    DeleteSingle_Packages_Fast();
//                                    Log.e("Ok Dialog Click :", "hmmm");
//                                }
//                            })
//                            .show();


                    new SweetAlertDialog(BusinessMainActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("Won't be able to recover this Action!")
                            .setConfirmText("Yes,delete it!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    DeleteSingle_Packages_Fast();
                                    Log.e("Ok Dialog Click :", "hmmm");
                                }
                            })
                            .show();


                }
            });


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();

//                    holder.Catimage_transparent_overlay.setVisibility(View.GONE);
                    Log.e(" Item Position :", "" + arrayList.get(holder.getLayoutPosition()));
                    Log.e(" Adapter Position :", "" + position);
//                    Log.e("Package id", "" + arrayList.get(position).getPackage_ID());

                    Package_ID = arrayList.get(position).getPackage_ID();
                    Log.e("Package_ID :", "" + Package_ID);


                    Business_id = arrayList.get(position).getBusiness_id();
                    Log.e("Business_id :", "" + Business_id);


                    Package_name = String.valueOf(Html.fromHtml(arrayList.get(position).getPackage_name()));
                    Log.e("Package_name :", "" + Package_name);


                    Package_detail = String.valueOf(Html.fromHtml(arrayList.get(position).getPackage_detail()));
                    Log.e("Package_detail :", "" + Package_detail);


                    Stock_status = String.valueOf(Html.fromHtml(arrayList.get(position).getStock_status()));
                    Log.e("Stock_status :", "" + Stock_status);


                    Valid_parsons = String.valueOf(Html.fromHtml(arrayList.get(position).getValid_parsons()));
                    Log.e("Valid_parsons :", "" + Valid_parsons);


                    Valid_days = String.valueOf(Html.fromHtml(arrayList.get(position).getValid_day()));
                    Log.e("Valid_days :", "" + Valid_days);


                    Old_price = String.valueOf(Html.fromHtml(arrayList.get(position).getOld_price()));
                    Log.e("Old_price :", "" + Old_price);


                    New_price = String.valueOf(Html.fromHtml(arrayList.get(position).getNew_price()));
                    Log.e("New_price :", "" + New_price);


                    Timing_from = String.valueOf(Html.fromHtml(arrayList.get(position).getTiming_from()));
                    Log.e("Timing_from :", "" + Timing_from);


                    Timing_to = String.valueOf(Html.fromHtml(arrayList.get(position).getTiming_to()));
                    Log.e("Timing_to :", "" + Timing_to);


                    Offer_validity_from = String.valueOf(Html.fromHtml(arrayList.get(position).getOffer_validity_from()));
                    Log.e("Offer_validity_from :", "" + Offer_validity_from);


                    Offer_validity_to = String.valueOf(Html.fromHtml(arrayList.get(position).getOffer_validity_to()));
                    Log.e("Offer_validity_to :", "" + Offer_validity_to);


                    Home_delivery = String.valueOf(Html.fromHtml(arrayList.get(position).getHome_delivery()));
                    Log.e("Home_delivery :", "" + Home_delivery);


                    Log.e(" List Size :", "" + arrayList.size());

                    PackageListComplete = String.valueOf(arrayList.size());
                    Log.e(" PackageListComplete Size :", "" + PackageListComplete);

                }
            });


        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }


//        public void updateData(List<BeanPackagesList> arrayList) {
//            arrayList.clear();
//            arrayList.addAll(arrayList);
//            notifyDataSetChanged();
//        }
//
//
//        public void addItem(int position, BeanPackagesList viewModel) {
//            arrayList.add(position, viewModel);
//            notifyItemInserted(position);
//        }
//
//        public void removeItem(int position) {
//            arrayList.remove(position);
//            notifyItemRemoved(position);
//
//        }
//
//
//        public void swap(List list) {
//            if (arrayList != null) {
//                arrayList.clear();
//                arrayList.addAll(list);
//            } else {
//                arrayList = list;
//            }
//            notifyDataSetChanged();
//        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView Tv_package_name;
            TextView Tv_package_detail;
            TextView Tv_business_package_offer_price_list;
            TextView Tv_business_package_regular_price_list;
            TextView Tv_business_package_available_status;
            TextView Tv_business_package_valid_from_date;
            TextView Tv_business_package_valid_to_date;
            TextView Tv_business_package_availabel_time_from_time;
            TextView Tv_business_package_available_to_time;
            TextView Tv_business_package_availabel_days;
            ImageView Iv_edit_single_package;
            ImageView Iv_delete_single_package;
            TextView Tv_quantity;

            public MyViewHolder(View view) {
                super(view);

                Tv_package_name = (TextView) view.findViewById(R.id.tv_package_name);
                Tv_package_detail = (TextView) view.findViewById(R.id.tv_package_detail);
                Tv_business_package_offer_price_list = (TextView) view.findViewById(R.id.tv_offer_price);
                Tv_business_package_regular_price_list = (TextView) view.findViewById(R.id.tv_regular_price);
                Tv_business_package_available_status = (TextView) view.findViewById(R.id.tv_business_package_availabl);
                Tv_business_package_valid_from_date = (TextView) view.findViewById(R.id.tv_business_package_valid_from_date);
                Tv_business_package_valid_to_date = (TextView) view.findViewById(R.id.tv_business_package_valid_to_date);
                Tv_business_package_availabel_time_from_time = (TextView) view.findViewById(R.id.tv_business_package_availabel_time_from_time);
                Tv_business_package_available_to_time = (TextView) view.findViewById(R.id.tv_business_package_available_to_time);
                Tv_business_package_availabel_days = (TextView) view.findViewById(R.id.tv_business_package_availabel_days);
                Iv_edit_single_package = (ImageView) view.findViewById(R.id.iv_edit_single_package);
                Iv_delete_single_package = (ImageView) view.findViewById(R.id.iv_delete_single_package);
                Tv_quantity = (TextView) view.findViewById(R.id.tv_business_package_availabel_quantities);

            }
        }


    }

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }


}
