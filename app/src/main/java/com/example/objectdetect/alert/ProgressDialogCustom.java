package com.example.objectdetect.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.objectdetect.R;

public class ProgressDialogCustom {
    private Context context;
    private  AlertDialog progressDialog;
    private ProgressBar progressBar;
    private TextView percentView;

    public ProgressDialogCustom(Context context) {
        this.context=context;
        AlertDialog.Builder builder=new AlertDialog.Builder(this.context);
        LayoutInflater inflater=LayoutInflater.from(this.context);
        View dialog=inflater.inflate(R.layout.progress_dialog_custom,null);
        percentView=dialog.findViewById(R.id.percent);
        progressBar=dialog.findViewById(R.id.progressBar2);
        // progressBar.setProgress(50);
        progressBar.setMax(100);
        builder.setView(dialog).setCancelable(false);
        progressDialog=builder.create();
    }

    public void showProgressBard(){
        progressDialog.setTitle("Descargando");
        progressDialog.setMessage("");
        progressDialog.show();
    }

    public void setProgressInfo(String info){
        this.progressDialog.setMessage(info);
    }

    public void setTitle(String title){
        this.progressDialog.setTitle(title);
    }
    public void setProgressBar(final int progress) {
        Log.d("PROGRES",progress+"");
        this.progressBar.setProgress(progress);
        this.percentView.setText(progress+"%");
    }
    public void hidenProgressBard(){
        if(progressDialog!=null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

}