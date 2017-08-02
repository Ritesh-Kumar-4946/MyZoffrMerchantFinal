package in.myzoffrmerchants.permission_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import in.myzoffrmerchants.permission_fragment.PermissionFragment;
import in.myzoffrmerchants.permission_model.PermissionModel;


public class PermissionPagerAdapter extends FragmentStatePagerAdapter {

    private List<PermissionModel> permissions;

    public PermissionPagerAdapter(FragmentManager fm, List<PermissionModel> permissions) {
        super(fm);
        this.permissions = permissions;
    }

    @Override
    public Fragment getItem(int position) {
        return PermissionFragment.newInstance(permissions.get(position));
    }

    @Override
    public int getCount() {
        return permissions.size();
    }
}
