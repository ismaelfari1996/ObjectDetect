package com.example.objectdetect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.objectdetect.alert.ProgressDialogCustom;
import com.example.objectdetect.databinding.ActivityMainBinding;
import com.example.objectdetect.fileManager.FileDonwloader;
import com.example.objectdetect.fileManager.FileManagement;
import com.example.objectdetect.permission.Permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'objectdetect' library on application startup.
    static String msg="hola";
    private static final List<String> systemFiles=new ArrayList<>();
    private static final LinkedHashMap<String,String> downloadUrl=new LinkedHashMap<>();
    static {
        System.loadLibrary("objectdetect");
        systemFiles.add("classifier");
        downloadUrl.put("/classifier/lbpcascade_face.xml",
                "https://raw.githubusercontent.com/opencv/opencv/master/data/haarcascades/haarcascade_frontalface_default.xml");
    }

    private ActivityMainBinding binding;
    private Permission permission=new Permission();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button btn=binding.button;
        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(msg);

        // create all system files, folder
        if(!permission.checkPermission(this))
            permission.requestPermission(this);
        else
            if (!new FileManagement(this).createSystemFiles(systemFiles))
                Toast.makeText(this,"Se produjo un error al crear lo archivos",
                                Toast.LENGTH_LONG)
                                .show();
            else {
                new FileManagement(this).donwloadFiles(this,downloadUrl);
            }


        btn.setOnClickListener(view -> {
            if(permission.checkPermission(this)) {
                Intent intent = new Intent(this, CameraView.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }else{
                permission.requestPermission(this);
            }
        });
    }

    /**
     * A native method that is implemented by the 'objectdetect' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}