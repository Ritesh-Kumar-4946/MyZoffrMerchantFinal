package in.myzoffrmerchants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;

/**
 * Created by ritesh on 14/7/17.
 */

public class FileUploadTest extends Activity {

    private static final int SELECT_FILE1 = 1;
    private static final int SELECT_FILE2 = 2;
    private static final int SELECT_FILE3 = 3;
    private static final int SELECT_FILE4 = 4;
    String selectedPath1 = "NONE";
    String selectedPath2 = "NONE";
    String selectedPath3 = "NONE";
    String selectedPath4 = "NONE";
    TextView tv, res, rest, resf;
    ProgressDialog progressDialog;
    Button b1, b2, b3, b4, b5;
    HttpEntity resEntity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_demo);

        tv = (TextView) findViewById(R.id.tv);
        res = (TextView) findViewById(R.id.res);
        rest = (TextView) findViewById(R.id.rest);
        resf = (TextView) findViewById(R.id.resf);
        tv.setText(tv.getText() + selectedPath1 + "," + selectedPath2 + "," + selectedPath3 + "," + selectedPath4);
        b1 = (Button) findViewById(R.id.Button01);
        b2 = (Button) findViewById(R.id.Button02);
        b3 = (Button) findViewById(R.id.upload);
        b4 = (Button) findViewById(R.id.Button03);
        b5 = (Button) findViewById(R.id.Button04);
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(SELECT_FILE1);
            }
        });
        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(SELECT_FILE2);
            }
        });
        b4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(SELECT_FILE3);
            }
        });
        b5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(SELECT_FILE4);
            }
        });
        b3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(selectedPath1.trim().equalsIgnoreCase("NONE"))
                        && !(selectedPath2.trim().equalsIgnoreCase("NONE"))
                        && !(selectedPath3.trim().equalsIgnoreCase("NONE"))
                        && !(selectedPath4.trim().equalsIgnoreCase("NONE"))) {
                    progressDialog = ProgressDialog.show(FileUploadTest.this, "", "Uploading files to server.....", false);
                    Thread thread = new Thread(new Runnable() {
                        public void run() {
                            doFileUpload();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    if (progressDialog.isShowing())
                                        progressDialog.dismiss();
                                }
                            });
                        }
                    });
                    thread.start();
                } else {
                    Toast.makeText(getApplicationContext(), "Please select two files to upload.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void openGallery(int req_code) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file to upload "), req_code);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (requestCode == SELECT_FILE1) {
                selectedPath1 = getPath(selectedImageUri);
                System.out.println("selectedPath1 : " + selectedPath1);
                Log.e("selectedPath1 :", "" + selectedPath1);
            }
            if (requestCode == SELECT_FILE2) {
                selectedPath2 = getPath(selectedImageUri);
                System.out.println("selectedPath2 : " + selectedPath2);
                Log.e("selectedPath2 :", "" + selectedPath2);
            }
            if (requestCode == SELECT_FILE3) {
                selectedPath3 = getPath(selectedImageUri);
                System.out.println("selectedPath3 : " + selectedPath3);
                Log.e("selectedPath3 :", "" + selectedPath3);
            }
            if (requestCode == SELECT_FILE4) {
                selectedPath4 = getPath(selectedImageUri);
                System.out.println("selectedPath4 : " + selectedPath4);
                Log.e("selectedPath4 :", "" + selectedPath4);
            }
            tv.setText("Selected File paths : " + selectedPath1 + "," + selectedPath2 + "," + selectedPath3 + "," + selectedPath4);
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void doFileUpload() {

        File file1 = new File(selectedPath1);
        File file2 = new File(selectedPath2);
        File file3 = new File(selectedPath3);
        File file4 = new File(selectedPath4);
//        String urlString = "http://10.0.2.2/upload_test/upload_media_test.php";
        String urlString = "http://whatsapphindistatus.com/ZOF/webservice/user_update?user_id=2&email=r@rr.com&username=Ritesh&mobile=7415984946&work_address=&home_address=";


        /*http://whatsapphindistatus.com/ZOF/webservice/user_update?user_id=1&email=vgurjar86@gmail.com&username=vijju&mobile=8889994272&work_address=jjj&home_address=123&work_location=citylist(mumbai)&image=&voter_id=&license=&adhar_card=&light_home_bill=&light_work_bill=*/
        Log.e("urlString :", "" + urlString);


        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urlString);
            FileBody bin1 = new FileBody(file1);
            FileBody bin2 = new FileBody(file2);
            FileBody bin3 = new FileBody(file3);
            FileBody bin4 = new FileBody(file4);
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("image", bin1);
            reqEntity.addPart("license", bin2);
            reqEntity.addPart("voter_id", bin3);
            reqEntity.addPart("pan_card", bin4);
            reqEntity.addPart("work_location", new StringBody("2"));
            post.setEntity(reqEntity);
            Log.e("post :", "" + post);
            HttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            final String response_str = EntityUtils.toString(resEntity);
            if (resEntity != null) {
                Log.e("RESPONSE", response_str);
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            res.setTextColor(Color.GREEN);
                            res.setText("n Response from server : n " + response_str);
                            Toast.makeText(getApplicationContext(), "Upload Complete. Check the server uploads directory.", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        }
    }

}
