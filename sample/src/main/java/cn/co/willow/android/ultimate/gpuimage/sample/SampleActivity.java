package cn.co.willow.android.ultimate.gpuimage.sample;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static android.Manifest.permission.CAMERA;
import static android.content.pm.PackageManager.PERMISSION_DENIED;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        initFunctionContainer();
    }


    /*how to use UltimateGPUImage===================================================================*/
    private ConstraintLayout mFunctionContainer;
    private VideoRecordHolder mVideoRecordHolder;

    public void initFunctionContainer() {
        mFunctionContainer = (ConstraintLayout) findViewById(R.id.cl_function_container);
        mVideoRecordHolder = new VideoRecordHolder(this);
        mFunctionContainer.addView(mVideoRecordHolder.getRootView());
        requestPermission(CAMERA);
        mVideoRecordHolder.openCamera();
    }

    public void openCamera() {
        mVideoRecordHolder.openCamera();
    }


    /*权限处理 Premission Handler===================================================================*/
    /** 申请权限 request premission */
    public void requestPermission(String... permissions) {
        if (checkPremission(permissions)) return;
        ActivityCompat.requestPermissions(this, permissions, 114);
    }

    /** 权限检测 check premission */
    public boolean checkPremission(String... permissions) {
        boolean allHave = true;
        PackageManager pm = getPackageManager();
        for (String permission : permissions) {
            switch (pm.checkPermission(permission, SampleApplication.getApplication().getPackageName())) {
                case PERMISSION_GRANTED:
                    allHave = allHave && true;
                    continue;
                case PERMISSION_DENIED:
                    allHave = allHave && false;
                    continue;
            }
        }
        return allHave;
    }

    /** 权限处理 premission result dealer */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 114 && permissions != null && permissions.length > 0) {
            String permission = "";
            for (int i = 0; i < permissions.length; i++) {
                permission = permissions[i];
                grantedResultDeal(
                        permission,
                        grantResults.length > i && grantResults[i] == PERMISSION_GRANTED);
            }
        }
    }

    /** 权限返回值处理 */
    protected void grantedResultDeal(String permission, boolean isGranted) {
        switch (permission) {
            case CAMERA:
                if (isGranted) {
                    openCamera();
                }
                break;
        }
    }

}