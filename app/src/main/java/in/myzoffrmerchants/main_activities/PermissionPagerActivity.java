package in.myzoffrmerchants.main_activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.myzoffrmerchants.R;
import in.myzoffrmerchants.permission_activity.BasePermissionActivity;
import in.myzoffrmerchants.permission_model.PermissionModel;
import in.myzoffrmerchants.permission_model.PermissionModelBuilder;

public class PermissionPagerActivity extends BasePermissionActivity {

    @NonNull
    @Override
    protected List<PermissionModel> permissions() {
        List<PermissionModel> permissions = new ArrayList<>();
        permissions.add(PermissionModelBuilder.withContext(this)
                .withCanSkip(true)
                .withPermissionName(Manifest.permission.CAMERA)
                .withTitle(R.string.text_camera)
                .withMessage(R.string.text_camera_description)
                .withExplanationMessage(R.string.text_camera_dialog_description)
                .withFontType("my_font.ttf")
                .withLayoutColorRes(R.color.colorPrimary)
                .withImageResourceId(R.drawable.ic_permission_camera)
                .build());

        permissions.add(PermissionModelBuilder.withContext(this)
                .withCanSkip(true)
                .withTitle(R.string.text_external_storage)
                .withPermissionName(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withMessage(R.string.text_external_storage_description)
                .withExplanationMessage(R.string.text_external_storage)
                .withFontType("my_font.ttf")
                .withLayoutColorRes(R.color.colorPrimary)
                .withImageResourceId(R.drawable.ic_permission_external_storage)
                .build());


        permissions.add(PermissionModelBuilder.withContext(this)
                .withTitle("ACCESS_FINE_LOCATION")
                .withCanSkip(false)
                .withPermissionName(Manifest.permission.ACCESS_FINE_LOCATION)
                .withMessage("PermissionHelper also prevents your app getting crashed if the " +
                        "requested permission never exists in your AndroidManifest" +
                        ". Android DOES!")
                .withExplanationMessage("We need this permission to access to your location to" +
                        " find nearby restaurants and places you like!")
                .withFontType("my_font.ttf")
                .withLayoutColorRes(R.color.colorAccent)
                .withImageResourceId(R.drawable.permission_two)
                .build());

        permissions.add(PermissionModelBuilder.withContext(this)
                .withCanSkip(false) /*explanation only once will be called otherwise we will
                            run into infinite request if the user never grant the permission.*/
                .withTitle("SYSTEM_ALERT_WINDOW")
                .withPermissionName(Manifest.permission.SYSTEM_ALERT_WINDOW)
                .withMessage("PermissionHelper handles requesting SYSTEM_ALERT_WINDOW permission")
                .withExplanationMessage("We need this permission to make our videoPlayer overlay on your screen.")
                .withFontType("my_font.ttf")
                .withLayoutColorRes(R.color.colorPrimaryDark)
                .withImageResourceId(R.drawable.permission_two).build());
        return permissions;
    }

    @Override
    protected int theme() {
        return R.style.noActionBar;
    }

    @Override
    protected void onIntroFinished() {
        Toast.makeText(this, "Intro Finished", Toast.LENGTH_SHORT).show();
        Log.e("onIntroFinished", "Intro has finished");
        Intent Gologincreen = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(Gologincreen);
        // do whatever you like!
        finish();
    }

    @Nullable
    @Override
    protected ViewPager.PageTransformer pagerTransformer() {
        return null;//use default
    }

    @Override
    protected boolean backPressIsEnabled() {
        return false;
    }

    @Override
    protected void permissionIsPermanentlyDenied(@NonNull String permissionName) {
        Log.e("DANGER", "Permission ( " + permissionName + " ) is permanentlyDenied and can only be granted via settings screen");
        /** {@link com.fastaccess.permission.base.PermissionHelper#openSettingsScreen(Context)} can help you to open it if you like */
    }

    @Override
    protected void onUserDeclinePermission(@NonNull String permissionName) {
        Log.w("Warning", "Permission ( " + permissionName + " ) is skipped you can request it again by calling doing such\n " +
                "if (permissionHelper.isExplanationNeeded(permissionName)) {\n" +
                "        permissionHelper.requestAfterExplanation(permissionName);\n" +
                "    }\n" +
                "    if (permissionHelper.isPermissionPermanentlyDenied(permissionName)) {\n" +
                "        /** read {@link #permissionIsPermanentlyDenied(String)} **/\n" +
                "    }");

    }

}