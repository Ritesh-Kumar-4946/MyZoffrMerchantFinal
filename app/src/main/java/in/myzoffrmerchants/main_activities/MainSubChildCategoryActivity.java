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
import in.myzoffrmerchants.Beans.BeanMainSubChildCategory;
import in.myzoffrmerchants.Constant.Appconstant;
import in.myzoffrmerchants.Constant.HttpUrlPath;
import in.myzoffrmerchants.R;

/**
 * Created by ritesh on 19/7/17.
 */

public class MainSubChildCategoryActivity extends AppCompatActivity {


    private static final String CHILD_CATEGORY_URL = "child_category?";
    @BindView(R.id.toolbar_child_category_selection)
    Toolbar Toolbar_child_category_selection;
    ArrayList<MainSubChildCategoryActivity> rowSubChildCategoryItems;
    List<BeanMainSubChildCategory> beanMainSubChildCategory0;
    String ChildCategory_Id = "",
            GetMainCategory_Shr_Pref = "",
            GetSubCategory_Shr_Pref = "",
            StrGetIntent_Main_Category_ID = "",
            StrGetIntent_Sub_Category_ID = "",
            child_cat_list = "",
            ChildCategory_Name = "",
            child_cat_status = "",
            child_cat_message = "",
            Get_Sub_Cat_Id_Intent = "",
            ChildCate_id = "",
            ChildCate_Name = "",
            ChildCate_Parent_Id = "",
            Child_cat_list = "",
            Child_cat_list_result = "",
            StrGet_Child_Result = "",
            StrGet_result_child_list = "",
            ChildCategory_Parent_Id = "";
    @BindView(R.id.rv_child_category)
    RecyclerView Rv_child_category;
    @BindView(R.id.cv_confirm_category)
    CardView Cv_confirm_category;
    @BindView(R.id.tv_final_child_category)
    TextView Tv_final_child_category;
    private ArrayList<String> sub_Child_cat_names;
    private ArrayList<String> sub_Child_cat_id;
    private ArrayList<String> sub_Child_cat_parent_id;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_subchildcategory);
        ButterKnife.bind(this);

        Cv_confirm_category.setVisibility(View.GONE);
        Tv_final_child_category.setVisibility(View.GONE);


        StrGetIntent_Main_Category_ID = getIntent().getStringExtra("MAIN_CATE_ID");
        Get_Sub_Cat_Id_Intent = getIntent().getStringExtra("MAIN_SUB_CATEGORY_ID");
        Log.e("MAIN_CATEGORY_ID  from Intent:", "" + StrGetIntent_Main_Category_ID);
        Log.e("SUB_CATEGORY_ID  from Intent:", "" + Get_Sub_Cat_Id_Intent);

        Appconstant.sh = getSharedPreferences(Appconstant.MyPREFERENCES, Context.MODE_PRIVATE);
        GetMainCategory_Shr_Pref = Appconstant.sh.getString("MAIN_CATEGORY_ID", null);
        GetSubCategory_Shr_Pref = Appconstant.sh.getString("MAIN_SUB_CATEGORY_ID", null);
        Log.e("GetMainCategory_Shr_Pref  from MainCategoryActivity:", "" + GetMainCategory_Shr_Pref);
        Log.e("GetSubCategory_Shr_Pref  from MainCategoryActivity:", "" + GetSubCategory_Shr_Pref);


        /*Progress dialog*/
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        setSupportActionBar(Toolbar_child_category_selection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        rowSubChildCategoryItems = new ArrayList<>();
        sub_Child_cat_id = new ArrayList<>();
        sub_Child_cat_names = new ArrayList<>();
        sub_Child_cat_parent_id = new ArrayList<>();


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        Rv_child_category.setLayoutManager(mLayoutManager);
//        Rv_main_category.addItemDecoration(new EqualSpaceItemDecoration(5));
        Rv_child_category.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));
        Rv_child_category.setNestedScrollingEnabled(false);


//        Cv_confirm_category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent SubChildCatPage = new Intent(getApplicationContext(), BusinessMainActivity.class);
//                SubChildCatPage.putExtra("MAINBUSINESS_CATEGORY_ID", StrGetIntent_Main_Category_ID);
//                SubChildCatPage.putExtra("SUBBUSINESS_CATEGORY_ID", Get_Sub_Cat_Id_Intent);
//                SubChildCatPage.putExtra("CHILDBUSINESS_CATEGORY_ID", ChildCategory_Id);
//                MainSubChildCategoryActivity.this.startActivity(SubChildCatPage);
//                finish();
//            }
//        });


        ChildCategoryJsontask task = new ChildCategoryJsontask();
        task.execute();


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

        Log.e("onBackPressed go Sub Category screen :", "OK");
        Intent GoMainSubCategoryScreen = new Intent(getApplicationContext(), MainCategoryActivity.class);
        startActivity(GoMainSubCategoryScreen);
        finish();


    }

    private class ChildCategoryJsontask extends AsyncTask<String, Void, List<BeanMainSubChildCategory>> {

        boolean iserror = false;
        String result = " ";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e("************ChildCategoryJsontask  ************", "OK");
            showDialog();
        }

        @Override
        protected List<BeanMainSubChildCategory> doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPathMain + CHILD_CATEGORY_URL + "sub_category_id=" + Get_Sub_Cat_Id_Intent);

            Log.e(" ************ URL :", " " + HttpUrlPath.urlPathMain + CHILD_CATEGORY_URL + "sub_category_id=" + Get_Sub_Cat_Id_Intent);

            try {
                HttpResponse response = client.execute(post);
                String obj = EntityUtils.toString(response.getEntity());
                beanMainSubChildCategory0 = new ArrayList<>();
                Log.e("************ All App USER list ************", "" + obj);

                JSONObject jsonObject = new JSONObject(obj);
                child_cat_status = jsonObject.getString("status");
                child_cat_message = jsonObject.getString("message");


                if (child_cat_status.equalsIgnoreCase("1")) {

                    Child_cat_list = jsonObject.getString("result");
                    Log.e("************Json Child_cat_list data*******************", " " + Child_cat_list);

                    JSONArray jaaray = new JSONArray(Child_cat_list);
                    for (int i = 0; i < jaaray.length(); i++) {
                        Child_cat_list_result = jaaray.getJSONObject(i).getString("result");
                        if (Child_cat_list_result.equalsIgnoreCase("success")) {

                            BeanMainSubChildCategory beanMainSubChildCategory = new BeanMainSubChildCategory();
                            beanMainSubChildCategory.setChildCatid(jaaray.getJSONObject(i).getString("id"));
                            beanMainSubChildCategory.setChildCatName(jaaray.getJSONObject(i).getString("name"));
                            beanMainSubChildCategory.setChildCatParentid(jaaray.getJSONObject(i).getString("sub_category_id"));
                            beanMainSubChildCategory0.add(beanMainSubChildCategory);


                            ChildCate_id = jaaray.getJSONObject(i).getString("id");
                            ChildCate_Name = jaaray.getJSONObject(i).getString("name");
                            ChildCate_Parent_Id = jaaray.getJSONObject(i).getString("sub_category_id");

                            sub_Child_cat_id.add(ChildCate_id);
                            sub_Child_cat_names.add(ChildCate_Name);
                            sub_Child_cat_parent_id.add(ChildCate_Parent_Id);
                            Log.e(" ********** sub_Child_cat_id **********", "" + sub_Child_cat_id);
                            Log.e(" ********** sub_Child_cat_names **********", "" + sub_Child_cat_names);
                            Log.e(" ********** sub_Child_cat_parent_id **********", "" + sub_Child_cat_parent_id);


                            String SubcatChild_idlist = sub_Child_cat_id.get(i);
                            Log.e(" ********** SubcatChild_idlist **********", "" + SubcatChild_idlist);

                            String SubcatChild_nameslist = sub_Child_cat_names.get(i);
                            Log.e(" ********** SubcatChild_nameslist **********", "" + SubcatChild_nameslist);

                            String SubcatChild_ParentIDlist = sub_Child_cat_parent_id.get(i);
                            Log.e(" ********** SubcatChild_ParentIDlist **********", "" + SubcatChild_ParentIDlist);


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
            return beanMainSubChildCategory0;
        }

        @Override
        protected void onPostExecute(List<BeanMainSubChildCategory> mystring) {
            super.onPostExecute(mystring);


            if (sub_Child_cat_id.size() > 0) {
                Log.e(" ********** sub_Child_cat_id ********** ", "" + sub_Child_cat_id);
                ChildCategoryAdapter childCategoryAdapter = new ChildCategoryAdapter(MainSubChildCategoryActivity.this, mystring);
                Rv_child_category.setAdapter(childCategoryAdapter);


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

    private class ChildCategoryAdapter extends RecyclerView.Adapter<ChildCategoryAdapter.MyViewHolder> {

        private Context mContext;
        private List<BeanMainSubChildCategory> arrayList;


        public ChildCategoryAdapter(Context mContext, List<BeanMainSubChildCategory> arrayList) {
            this.mContext = mContext;
            this.arrayList = arrayList;
        }

        @Override
        public ChildCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rv_business_main_subcategory_list_items, parent, false);

            return new ChildCategoryAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ChildCategoryAdapter.MyViewHolder holder, final int position) {

            holder.SubCatName.setText(Html.fromHtml(arrayList.get(position).getChildCatName()));

//

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();

//                    holder.Catimage_transparent_overlay.setVisibility(View.GONE);
                    Log.e(" Item Position :", "" + arrayList.get(holder.getLayoutPosition()));

                    Log.e(" Adapter Position :", "" + position);

                    Log.e("Child Categery id", "" + arrayList.get(position).getChildCatid());
                    ChildCategory_Id = arrayList.get(position).getChildCatid();
                    ChildCategory_Parent_Id = arrayList.get(position).getChildCatParentid();
                    Log.e("ChildCategory_Id :", "" + ChildCategory_Id);
                    Log.e("Parent_Id :", "" + ChildCategory_Parent_Id);


                    ChildCategory_Name = String.valueOf(Html.fromHtml(arrayList.get(position).getChildCatName()));
                    ChildCategory_Parent_Id = String.valueOf(Html.fromHtml(arrayList.get(position).getChildCatParentid()));

                    Log.e("Item Name", "" + String.valueOf(Html.fromHtml(arrayList.get(position).getChildCatName())));

                    Log.e("Item Name ChildCategory_Name", "" + ChildCategory_Name);

                    Log.e(" Child Category List Size :", "" + arrayList.size());


                    if (!ChildCategory_Id.equalsIgnoreCase("")) {
                        Log.e("ChildCategory_Id :", "Found so Cv_confirm_category visible");
//                        Cv_confirm_category.setVisibility(View.VISIBLE);
//                        Tv_final_child_category.setVisibility(View.VISIBLE);

                        Tv_final_child_category.setText(ChildCategory_Name);
                        Appconstant.editor.putString("CHILD_CATEGORY_ID", ChildCategory_Id);
                        Appconstant.editor.commit();


                        Intent SubChildCatPage = new Intent(getApplicationContext(), BusinessCreateActivity.class);
                        SubChildCatPage.putExtra("MAINBUSINESS_CATEGORY_ID", StrGetIntent_Main_Category_ID);
                        SubChildCatPage.putExtra("SUBBUSINESS_CATEGORY_ID", Get_Sub_Cat_Id_Intent);
                        SubChildCatPage.putExtra("CHILDBUSINESS_CATEGORY_ID", ChildCategory_Id);
                        MainSubChildCategoryActivity.this.startActivity(SubChildCatPage);
                        finish();


                    } else {
                        Log.e("ChildCategory_Id :", "Not Found so Cv_confirm_category Gone");
                        Cv_confirm_category.setVisibility(View.GONE);
                        Tv_final_child_category.setVisibility(View.GONE);
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
