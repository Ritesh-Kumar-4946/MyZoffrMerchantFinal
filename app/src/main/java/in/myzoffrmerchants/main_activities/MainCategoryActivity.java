package in.myzoffrmerchants.main_activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;

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
import in.myzoffrmerchants.Beans.BeanMainCategory;
import in.myzoffrmerchants.Constant.Appconstant;
import in.myzoffrmerchants.Constant.HttpUrlPath;
import in.myzoffrmerchants.R;

public class MainCategoryActivity extends AppCompatActivity {

    // temporary string to show the parsed response.

    private static final int TIME_DELAY = 2000;
    private static final String MERCHANT_DETAIL_URL = "get_profile?";
    private static final String MAIN_CATEGORY_URL = "main_category";
    private static long back_pressed;
    boolean doubleBackToExitPressedOnce = false;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_main_category)
    RecyclerView Rv_main_category;
    List<MainCategoryActivity> rowMainCategoryItems;
    List<BeanMainCategory> beanMainCategory0;
    String Category_Id = "",
            main_cat_list = "",
            Category_Name = "";
    String MainCategory_ID = "",
            Category_Name_for_Sub_Category = "",
            StrGet_result_list = "";
    String Sh_pre_User_ID = "",
            Get_Empty_String_Shared_Pref = "",
            StrGet_Merc_status = "",
            StrGet_Merc_message = "",
            StrGet_Merc_result = "",
            StrGet_Merc_id = "",
            StrGet_Merc_activation_status = "";
    @BindView(R.id.tv_main_category)
    TextView Tv_main_category;
    @BindView(R.id.cv_confirm_main_category)
    CardView Cv_confirm_main_category;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayList<String> cat_names;
    private ArrayList<String> cat_id;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);
        ButterKnife.bind(this);


        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        Sh_pre_User_ID = Appconstant.sh.getString("id", null);
        Get_Empty_String_Shared_Pref = Appconstant.sh.getString("EmptyString", null);
        Log.e("Merchant_ID from SharedPref :", "" + Sh_pre_User_ID);
        Log.e("Get_Empty_String_Shared_Pref from SharedPref :", "" + Get_Empty_String_Shared_Pref);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        Tv_main_category.setVisibility(View.GONE);
        Cv_confirm_main_category.setVisibility(View.GONE);


        rowMainCategoryItems = new ArrayList<MainCategoryActivity>();
        cat_id = new ArrayList<>();
        cat_names = new ArrayList<>();


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        Rv_main_category.setLayoutManager(mLayoutManager);

//        Rv_main_category.addItemDecoration(new EqualSpaceItemDecoration(5));
        Rv_main_category.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        Rv_main_category.setNestedScrollingEnabled(false);
//        Rv_main_category.setHasFixedSize(true);

        GetMerchantDetail();

//        Cv_confirm_main_category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent SubCatPage = new Intent(getApplicationContext(), MainSubCategoryActivity.class);
//                SubCatPage.putExtra("MAIN_CATEGORY_ID", MainCategory_ID);
//                MainCategoryActivity.this.startActivity(SubCatPage);
//                finish();
//            }
//        });


    }

    private void GetMerchantDetail() {
        final String user_id = Sh_pre_User_ID;

        Log.e("Volley GetMerchantDetail Data *************:", ""
                        + "\n" + "user_id :" + "" + user_id
//                + "\n" + "user_password" + "" + user_password
        );

        // Tag used to cancel the request
        String tag_string_req = "req_registration";
        pDialog.setMessage("Please Wait Fetching Detail.....");
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


                    StrGet_Merc_message = jobjresponse.getString("message");
                    Log.e("StrGet_Merc_message is:", "" + StrGet_Merc_message);


                    if (StrGet_Merc_status.equalsIgnoreCase("1")) {
                        Log.e("StrGet_Merc_status jobjresponse is:", "1");

                        JSONObject jobjresult = jobjresponse.getJSONObject("result");
                        StrGet_Merc_result = jobjresult.toString();
                        Log.e("StrGet_Merc_result :", "" + StrGet_Merc_result);
                        Log.e("StrGet_Merc_result jobjresult :", "" + jobjresult);

                        StrGet_Merc_id = jobjresult.getString("id");
                        Log.e("StrGet_Merc_id is:", "" + StrGet_Merc_id);

                        StrGet_Merc_activation_status = jobjresult.getString("status");
                        Log.e("StrGet_Merc_activation_status is:", "" + StrGet_Merc_activation_status);

                        hideDialog();
                    }


                } catch (JSONException e) {
                    hideDialog();
                    e.printStackTrace();
                }

//                Log.e("response :", "" + response);

                if (StrGet_Merc_status.equalsIgnoreCase("1")) {
                    Log.e("StrGet_status if is:", "1");

                    MainCategoryOJsontask task = new MainCategoryOJsontask();
                    task.execute();

                } else if (StrGet_Merc_status.equalsIgnoreCase("0")) {
                    hideDialog();

                    MainCategoryOJsontask task = new MainCategoryOJsontask();
                    task.execute();
                    Log.e("StrGet_status is:", "0");
                    Log.e("Email already exist:", "0");

//                            Toast.makeText(LoginActivity.this, "User detail not found", Toast.LENGTH_LONG).show();

                    new SweetAlertDialog(MainCategoryActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error!")
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

                        new SweetAlertDialog(MainCategoryActivity.this, SweetAlertDialog.ERROR_TYPE)
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {


//        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
//            super.onBackPressed();
//        } else {
//            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
//        }
//        back_pressed = System.currentTimeMillis();


        Intent GoGetProfileScreen = new Intent(getApplicationContext(), ProfileGetActivity.class);
        startActivity(GoGetProfileScreen);
        finish();


    }

    private class MainCategoryOJsontask extends AsyncTask<String, Void, List<BeanMainCategory>> {

        boolean iserror = false;
        String result = " ";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<BeanMainCategory> doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPathMain + MAIN_CATEGORY_URL);

            Log.e(" ************ URL :", " " + HttpUrlPath.urlPathMain + MAIN_CATEGORY_URL);

            try {
                HttpResponse response = client.execute(post);
                String obj = EntityUtils.toString(response.getEntity());
                beanMainCategory0 = new ArrayList<>();
                Log.e("************ All App USER list ************", "" + obj);

                JSONObject jsonObject = new JSONObject(obj);
                main_cat_list = jsonObject.getString("result");
                Log.e("************Json main_cat_list data*******************", " " + main_cat_list);

                JSONArray jaaray = new JSONArray(main_cat_list);

                for (int i = 0; i < jaaray.length(); i++) {
                    StrGet_result_list = jaaray.getJSONObject(i).getString("result");
                    if (StrGet_result_list.equalsIgnoreCase("success")) {
                        BeanMainCategory beanMainCategory = new BeanMainCategory();
                        beanMainCategory.setId(jaaray.getJSONObject(i).getString("id"));
                        beanMainCategory.setUsername(jaaray.getJSONObject(i).getString("name"));
                        beanMainCategory0.add(beanMainCategory);


                        Category_Id = jaaray.getJSONObject(i).getString("id");
                        Category_Name = jaaray.getJSONObject(i).getString("name");

                        cat_id.add(Category_Id);
                        cat_names.add(Category_Name);
                        Log.e(" ********** SellerProduct_id **********", "" + cat_id);
                        Log.e(" ********** SellerProduct_names **********", "" + cat_names);


                        String cat_idlist = cat_id.get(i);
                        Log.e(" ********** cat_idlist **********", "" + cat_idlist);

                        String cat_nameslist = cat_names.get(i);
                        Log.e(" ********** cat_nameslist **********", "" + cat_nameslist);
                    }
                }

            } catch (Exception e) {
                System.out.println("Errror in gettting by ID" + e);
                Log.e("22", "22" + e.getMessage());
                e.printStackTrace();
                iserror = true;
            }
            return beanMainCategory0;
        }

        @Override
        protected void onPostExecute(List<BeanMainCategory> mystring) {
            super.onPostExecute(mystring);

            if (cat_id.size() > 0) {
                Log.e(" ********** SellerProduct_id ********** ", "" + cat_id);
                CategoryAdapter categoryAdapter = new MainCategoryActivity.CategoryAdapter(MainCategoryActivity.this, mystring);
//                Rv_main_category.addItemDecoration(new DividerItemDecoration(MainCategoryActivity.this, LinearLayoutManager.VERTICAL));
                Rv_main_category.setAdapter(categoryAdapter);


                Log.e(" ********** listvideoid.size() > 0 **********", "YES");

            } else {

                Log.e("cat_id size is :", "0");

                TastyToast.makeText(getApplicationContext(),
                        "No Business Found Please Contact Service Provider",
                        TastyToast.LENGTH_LONG, TastyToast.ERROR);

            }
        }

    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

        private Context mContext;
        private List<BeanMainCategory> arrayList;


        public CategoryAdapter(Context mContext, List<BeanMainCategory> arrayList) {
            this.mContext = mContext;
            this.arrayList = arrayList;
        }

        @Override
        public CategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rv_business_main_category_list_items, parent, false);

            return new CategoryAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final CategoryAdapter.MyViewHolder holder, final int position) {

            holder.CatName.setText(Html.fromHtml(arrayList.get(position).getCatname()));
//

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();

//                    holder.Catimage_transparent_overlay.setVisibility(View.GONE);
                    Log.e(" Item Position :", "" + arrayList.get(holder.getLayoutPosition()));

                    Log.e(" Adapter Position :", "" + position);

                    Log.e("Main Categery id", "" + arrayList.get(position).getId());
                    MainCategory_ID = arrayList.get(position).getId();
                    Log.e("MainCategory_ID :", "" + MainCategory_ID);
                    Category_Name_for_Sub_Category = String.valueOf(Html.fromHtml(arrayList.get(position).getCatname()));

                    Log.e("Item Name", "" + String.valueOf(Html.fromHtml(arrayList.get(position).getCatname())));

                    Log.e("Item Name Category_Name_for_Sub_Category", "" + Category_Name_for_Sub_Category);

                    Log.e(" List Size :", "" + arrayList.size());


//                    Intent intent = new Intent(getBaseContext(), MainSubCategoryActivity.class);
//
//                    startActivity(intent);


                    if (!MainCategory_ID.equalsIgnoreCase("")) {
                        Log.e("MainCategory_ID :", "Found so Cv_confirm_category visible");
//                        Tv_main_category.setText(Category_Name_for_Sub_Category);
//                        Tv_main_category.setVisibility(View.VISIBLE);
//                        Cv_confirm_main_category.setVisibility(View.VISIBLE);


                        Appconstant.editor.putString("MAIN_CATEGORY_ID", MainCategory_ID);
                        Appconstant.editor.commit();

//                        Tv_main_category.setFocusable(true);
//                        Tv_main_category.requestFocus();
//                        Toast.makeText(MainCategoryActivity.this, "Click Confirm below", Toast.LENGTH_LONG).show();


                        Intent SubCatPage = new Intent(getApplicationContext(), MainSubCategoryActivity.class);
                        SubCatPage.putExtra("MAIN_CATEGORY_ID", MainCategory_ID);
                        MainCategoryActivity.this.startActivity(SubCatPage);
                        finish();


                    } else {
                        Log.e("MainCategory_ID :", " Not Found so Cv_confirm_category Gone");
                        Tv_main_category.setVisibility(View.GONE);
                        Cv_confirm_main_category.setVisibility(View.GONE);

                    }


                }
            });


        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView CatName;

            public MyViewHolder(View view) {
                super(view);
                CatName = (TextView) view.findViewById(R.id.gv_main_tv);

            }
        }


    }


}
