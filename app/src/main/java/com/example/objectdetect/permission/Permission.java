package com.example.objectdetect.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

public class Permission {
   private final String[] permissionList;
   public static final int PERMISSION_CODE = 2023;

    public  Permission(){
       this.permissionList=new String[]{
               Manifest.permission.WRITE_EXTERNAL_STORAGE,
               Manifest.permission.CAMERA,
               Manifest.permission.READ_EXTERNAL_STORAGE,
               Manifest.permission.INTERNET
       };
   }

 public void requestPermission(Activity activity){
     ActivityCompat.requestPermissions(activity,this.permissionList, PERMISSION_CODE);
    }

 public boolean checkPermission(Activity activity){
     for (String permission : permissionList) {
         if(ActivityCompat.checkSelfPermission(activity,permission)
                 != PackageManager.PERMISSION_GRANTED){
             return false;
         }
     }
     return true;
 }
}
