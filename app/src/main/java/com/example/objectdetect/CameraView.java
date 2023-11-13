package com.example.objectdetect;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.objectdetect.fileManager.FileManagement;

import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.Collections;
import java.util.List;

public class CameraView extends CameraActivity {
  private CameraBridgeViewBase javaCameraViewBase;
  private CascadeClassifier classifier;
  private Mat  mGray, mRgb;
  private MatOfRect rect;
  private int currentCameraIndex = JavaCameraView.CAMERA_ID_BACK;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_camera_view);
    javaCameraViewBase=findViewById(R.id.frame_Surface);
    javaCameraViewBase.setCameraIndex(JavaCameraView.CAMERA_ID_BACK);
    /*Button btn=findViewById(R.id.button2);
    btn.setOnClickListener(view -> {
      toggleCamera();
    });*/
    javaCameraViewBase.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener2() {
      @Override
      public void onCameraViewStarted(int width, int height) {
        mRgb=new Mat();
        mGray=new Mat();
        rect=new MatOfRect();
      }

      @Override
      public void onCameraViewStopped() {
        mRgb.release();
        mGray.release();
        rect.release();
      }

      @Override
      public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mGray=inputFrame.gray().t();
        mRgb=inputFrame.rgba().t();
        classifier.detectMultiScale(mGray,rect,1.3,5);
        for (Rect r:rect.toList()){
          Imgproc.rectangle(mRgb,r,new Scalar(0,255,0),5);
        }
        return mRgb.t();
      }
    });
    if(OpenCVLoader.initDebug()) {
      javaCameraViewBase.enableView();
      classifier=new CascadeClassifier(new FileManagement(this)
              .getEnvironmentPath()+"/classifier/lbpcascade_face.xml");
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    javaCameraViewBase.enableView();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    javaCameraViewBase.disableView();
  }

  @Override
  protected void onPause() {
    super.onPause();
    javaCameraViewBase.disableView();
  }

  @Override
  protected List<? extends CameraBridgeViewBase> getCameraViewList() {
    return Collections.singletonList(javaCameraViewBase);
  }

  private void toggleCamera() {
    if (currentCameraIndex == JavaCameraView.CAMERA_ID_BACK) {
      currentCameraIndex = JavaCameraView.CAMERA_ID_FRONT;
    } else {
      currentCameraIndex = JavaCameraView.CAMERA_ID_BACK;
    }

    javaCameraViewBase.disableView();
    javaCameraViewBase.setCameraIndex(currentCameraIndex);
    javaCameraViewBase.enableView();
  }
}