package com.example.objectdetect.fileManager;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileManagement {
    private final String environmentPath;

    public FileManagement(Context context) {
        File path=context.getExternalFilesDir(null);
        this.environmentPath=path.getAbsolutePath();
    }

    public boolean newFolder(String folderName) {
        File folder = new File(this.environmentPath,folderName);
        if (!folder.exists())
            return folder.mkdir();
        return true;
    }
    public boolean createSystemFiles(List<String> systemFileList){
        for(String file: systemFileList){
            if (!newFolder(file))
                return false;
        }
        return true;
    }

    public String getEnvironmentPath() {
        return environmentPath;
    }

    public LinkedHashMap<String,String> checkSystemFile(LinkedHashMap<String, String> fileSystemList){
        Iterator<Map.Entry<String, String>> iterator = fileSystemList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String filePath = getEnvironmentPath() + "/" + entry.getKey();
            if (new File(filePath).exists()) {
                iterator.remove(); // Utilizamos el iterator para eliminar de la colección mientras iteramos
            }
        }
        return fileSystemList;
    }
}
