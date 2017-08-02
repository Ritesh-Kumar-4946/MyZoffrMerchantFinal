package in.myzoffrmerchants.main_activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.sdsmdg.tastytoast.TastyToast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.myzoffrmerchants.Beans.BeanMainSubCategory;
import in.myzoffrmerchants.Constant.Appconstant;
import in.myzoffrmerchants.Constant.HttpUrlPath;
import in.myzoffrmerchants.R;

/**
 * Created by ritesh on 18/7/17.
 */

public class MainSubCategoryActivity extends AppCompatActivity {


    private static final String SUB_CATEGORY_URL = "sub_category?";
    @BindView(R.id.toolbar_sub_category_selection)
    Toolbar Toolbar_sub_category_selection;
    List<MainSubCategoryActivity> rowSubCategoryItems;
    List<BeanMainSubCategory> beanMainSubCategory0;
    String SubCategory_Id = "",
            GetMainCategory_Shr_Pref = "",
            sub_cat_list = "",
            SubCategory_Name = "",
            sub_cat_status = "",
            sub_cat_message = "",
            Get_Main_Cat_Id_Intent = "",
            Str_Main_Cat_Id_Send_Intent = "",
            SubCate_id = "",
            Sub_cat_list = "",
            SubCate_Name = "",
            SubCate_Parent_Id = "",
            StrGet_result_sub_list = "",
            SubCategory_Parent_Id = "";
    @BindView(R.id.rv_sub_category)
    RecyclerView Rv_sub_category;
    @BindView(R.id.cv_confirm_sub_category)
    CardView Cv_confirm_sub_category;
    @BindView(R.id.tv_sub_category)
    TextView Tv_sub_category;
    private ArrayList<String> sub_cat_names;
    private ArrayList<String> sub_cat_id;
    private ArrayList<String> sub_cat_parent_id;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_subcategory);
        ButterKnife.bind(this);


        Get_Main_Cat_Id_Intent = getIntent().getStringExtra("MAIN_CATEGORY_ID");
        Log.e("Get_Main_Cat_Id_Intent  from MainCategoryActivity:", "" + Get_Main_Cat_Id_Intent);

        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        GetMainCategory_Shr_Pref = Appconstant.sh.getString("MAIN_CATEGORY_ID", null);
        Log.e("GetMainCategory_Shr_Pref  from MainCategoryActivity:", "" + GetMainCategory_Shr_Pref);

        Tv_sub_category.setVisibility(View.GONE);
        Cv_confirm_sub_category.setVisibility(View.GONE);


        /*Progress dialog*/
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        setSupportActionBar(Toolbar_sub_category_selection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        rowSubCategoryItems = new ArrayList<MainSubCategoryActivity>();
        sub_cat_id = new ArrayList<>();
        sub_cat_names = new ArrayList<>();
        sub_cat_parent_id = new ArrayList<>();


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        Rv_sub_category.setLayoutManager(mLayoutManager);
//        Rv_main_category.addItemDecoration(new EqualSpaceItemDecoration(5));
        Rv_sub_category.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        Rv_sub_category.setNestedScrollingEnabled(false);

        SubCategoryJsontask task = new SubCategoryJsontask();
        task.execute();


//        Cv_confirm_sub_category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent SubCatPage = new Intent(getApplicationContext(), MainSubChildCategoryActivity.class);
//                SubCatPage.putExtra("MAIN_CATE_ID", Str_Main_Cat_Id_Send_Intent);
//                SubCatPage.putExtra("MAIN_SUB_CATEGORY_ID", SubCategory_Id);
//                MainSubCategoryActivity.this.startActivity(SubCatPage);
//                finish();
//
//            }
//        });


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
        Intent GoMainCategoryScreen = new Intent(getApplicationContext(), MainCategoryActivity.class);
        startActivity(GoMainCategoryScreen);
        finish();


    }

    private class SubCategoryJsontask extends AsyncTask<String, Void, List<BeanMainSubCategory>> {

        boolean iserror = false;
        String result = " ";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e("************SubCategoryJsontask  ************", "OK");
            showDialog();
        }

        @Override
        protected List<BeanMainSubCategory> doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPathMain + SUB_CATEGORY_URL + "cat_id=" + Get_Main_Cat_Id_Intent);

            Log.e(" ************ URL :", " " + HttpUrlPath.urlPathMain + SUB_CATEGORY_URL + "cat_id=" + Get_Main_Cat_Id_Intent);

            try {
                HttpResponse response = client.execute(post);
                String obj = EntityUtils.toString(response.getEntity());
                beanMainSubCategory0 = new ArrayList<>();
                Log.e("************ All App USER list ************", "" + obj);

                JSONObject jsonObject = new JSONObject(obj);
                sub_cat_status = jsonObject.getString("status");
                sub_cat_message = jsonObject.getString("message");


                if (sub_cat_status.equalsIgnoreCase("1")) {

                    sub_cat_list = jsonObject.getString("result");
                    Log.e("************Json sub_cat_list data*******************", " " + sub_cat_list);

                    JSONArray jaaray = new JSONArray(sub_cat_list);
                    for (int i = 0; i < jaaray.length(); i++) {
                        StrGet_result_sub_list = jaaray.getJSONObject(i).getString("result");
                        if (StrGet_result_sub_list.equalsIgnoreCase("success")) {
                            BeanMainSubCategory beanMainSubCategory = new BeanMainSubCategory();
                            beanMainSubCategory.setSubCatId(jaaray.getJSONObject(i).getString("id"));
                            beanMainSubCategory.setSubCatName(jaaray.getJSONObject(i).getString("name"));
                            beanMainSubCategory.setSubCatParentId(jaaray.getJSONObject(i).getString("category_id"));
                            beanMainSubCategory0.add(beanMainSubCategory);


                            SubCate_id = jaaray.getJSONObject(i).getString("id");
                            SubCate_Name = jaaray.getJSONObject(i).getString("name");
                            SubCate_Parent_Id = jaaray.getJSONObject(i).getString("category_id");

                            sub_cat_id.add(SubCate_id);
                            sub_cat_names.add(SubCate_Name);
                            sub_cat_parent_id.add(SubCate_Parent_Id);
                            Log.e(" ********** sub_cat_id **********", "" + sub_cat_id);
                            Log.e(" ********** sub_cat_names **********", "" + sub_cat_names);
                            Log.e(" ********** sub_cat_parent_id **********", "" + sub_cat_parent_id);


                            String Subcat_idlist = sub_cat_id.get(i);
                            Log.e(" ********** Subcat_idlist **********", "" + Subcat_idlist);

                            String Subcat_nameslist = sub_cat_names.get(i);
                            Log.e(" ********** Subcat_nameslist **********", "" + Subcat_nameslist);

                            String Subcat_ParentIDlist = sub_cat_parent_id.get(i);
                            Log.e(" ********** Subcat_ParentIDlist **********", "" + Subcat_ParentIDlist);


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
            return beanMainSubCategory0;
        }

        @Override
        protected void onPostExecute(List<BeanMainSubCategory> mystring) {
            super.onPostExecute(mystring);


            if (sub_cat_id.size() > 0) {
                Log.e(" ********** sub_cat_id ********** ", "" + sub_cat_id);
                SubSubCategoryAdapter subSubCategoryAdapter = new SubSubCategoryAdapter(MainSubCategoryActivity.this, mystring);
                Rv_sub_category.setAdapter(subSubCategoryAdapter);


                Log.e(" ********** sub_cat_id.size() > 0 **********", "YES");

            } else {

                Log.e("sub_cat_id size is :", "0");
                TastyToast.makeText(getApplicationContext(),
                        "No Business Found Please Choose Another Business",
                        TastyToast.LENGTH_LONG, TastyToast.INFO);

            }
            hideDialog();
        }

    }

    private class SubSubCategoryAdapter extends RecyclerView.Adapter<SubSubCategoryAdapter.MyViewHolder> {

        private Context mContext;
        private List<BeanMainSubCategory> arrayList;


        public SubSubCategoryAdapter(Context mContext, List<BeanMainSubCategory> arrayList) {
            this.mContext = mContext;
            this.arrayList = arrayList;
        }

        @Override
        public SubSubCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rv_business_main_subcategory_list_items, parent, false);

            return new SubSubCategoryAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final SubSubCategoryAdapter.MyViewHolder holder, final int position) {

            holder.SubCatName.setText(Html.fromHtml(arrayList.get(position).getSubCatname()));

//

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();

//                    holder.Catimage_transparent_overlay.setVisibility(View.GONE);
                    Log.e(" Item Position :", "" + arrayList.get(holder.getLayoutPosition()));

                    Log.e(" Adapter Position :", "" + position);

                    Log.e("Sub Categery id", "" + arrayList.get(position).getSubCatId());
                    SubCategory_Id = arrayList.get(position).getSubCatId();
                    SubCategory_Parent_Id = arrayList.get(position).getSubCatParentId();
                    Log.e("SubCategory_Id :", "" + SubCategory_Id);
                    Log.e("SubCategory_Parent_Id :", "" + SubCategory_Parent_Id);


                    SubCategory_Name = String.valueOf(Html.fromHtml(arrayList.get(position).getSubCatname()));
                    SubCategory_Parent_Id = String.valueOf(Html.fromHtml(arrayList.get(position).getSubCatParentId()));

                    Log.e("Item Name", "" + String.valueOf(Html.fromHtml(arrayList.get(position).getSubCatname())));

                    Log.e("Item Name SubCategory_Name", "" + SubCategory_Name);

                    Log.e(" Sub Category List Size :", "" + arrayList.size());


                    if (!Get_Main_Cat_Id_Intent.equalsIgnoreCase("")) {
                        Log.e("Get_Main_Cat_Id_Intent :", "Found so Cv_confirm visible");
//                        Tv_sub_category.setText(SubCategory_Name);
//                        Tv_sub_category.setVisibility(View.VISIBLE);
//                        Cv_confirm_sub_category.setVisibility(View.VISIBLE);

                        Appconstant.editor.putString("MAIN_SUB_CATEGORY_ID", SubCategory_Id);
                        Appconstant.editor.commit();

//                        Toast.makeText(MainSubCategoryActivity.this, "Click Confirm below", Toast.LENGTH_LONG).show();


                        Intent SubCatPage = new Intent(getApplicationContext(), MainSubChildCategoryActivity.class);
                        SubCatPage.putExtra("MAIN_CATE_ID", Get_Main_Cat_Id_Intent);
                        SubCatPage.putExtra("MAIN_SUB_CATEGORY_ID", SubCategory_Id);
                        MainSubCategoryActivity.this.startActivity(SubCatPage);
                        finish();


                    } else {
                        Log.e("MainCategory_ID :", " Not Found so Cv_confirm_category Gone");
                        Tv_sub_category.setVisibility(View.GONE);
                        Cv_confirm_sub_category.setVisibility(View.GONE);

                    }

                }
            });


        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView SubCatName;

            public MyViewHolder(View view) {
                super(view);
                SubCatName = (TextView) view.findViewById(R.id.tv_sub_cat_name);

            }
        }


    }


}
