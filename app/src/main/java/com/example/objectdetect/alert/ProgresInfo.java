package com.example.objectdetect.alert;

public class ProgresInfo {
    private String info;
    private int progress;

    public ProgresInfo(String info, int progress) {
        this.info = info;
        this.progress=progress;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
