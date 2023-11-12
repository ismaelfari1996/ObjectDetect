package com.example.objectdetect.fileManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.example.objectdetect.alert.ProgresInfo;
import com.example.objectdetect.alert.ProgressDialogCustom;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FileDonwloader extends AsyncTask<LinkedHashMap<String,String>, ProgresInfo,Boolean> {
    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final ProgressDialogCustom progressDialogCustom;
    public FileDonwloader(Context context) {
        this.context = context;
        progressDialogCustom=new ProgressDialogCustom(this.context);
    }

    @SafeVarargs
    @Override
    protected final Boolean doInBackground(LinkedHashMap<String, String>... params) {
        String enviromentPath=new FileManagement(this.context).getEnvironmentPath();
        for (Map.Entry<String, String > value:params[0].entrySet()){
            String savePath=enviromentPath+value.getKey();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(value.getValue())
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    return false;
                }

                long fileSize = Objects.requireNonNull(response.body()).contentLength();
                InputStream inputStream = Objects.requireNonNull(response.body()).byteStream();
                OutputStream outputStream = new FileOutputStream(savePath);

                byte[] buffer = new byte[4096];
                int bytesRead;
                long totalBytesRead = 0;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                    int progress =fileSize>0? (int) ((totalBytesRead * 100) / fileSize):(int)((totalBytesRead*100)/(totalBytesRead+1024));
                    publishProgress(new ProgresInfo(value.getValue(),progress));
                }

                outputStream.close();
                inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialogCustom.showProgressBard();
    }

    @Override
    protected void onProgressUpdate(ProgresInfo... values) {
        super.onProgressUpdate(values);
        progressDialogCustom.setProgressBar(values[0].getProgress());
        progressDialogCustom.setProgressInfo(values[0].getInfo().substring(values[0].getInfo().lastIndexOf("/")+1));
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean)
            progressDialogCustom.hidenProgressBard();
    }
}
