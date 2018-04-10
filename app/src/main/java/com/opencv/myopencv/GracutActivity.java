package com.opencv.myopencv;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.opencv.core.Core.compare;
import static org.opencv.imgproc.Imgproc.GC_PR_FGD;

/**
 * Created by Administrator on 2018/4/8 0008.
 */

public class GracutActivity extends Activity {
    private ImageView img;
    private Button btn;

    static {
        System.loadLibrary("opencv_java3");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabcut);
        img = (ImageView) findViewById(R.id.img);
        btn = (Button) findViewById(R.id.change);

        Bitmap bit = BitmapFactory.decodeFile("/sdcard/temp.jpg"); //自定义//路径
        img.setImageBitmap(bit);
//        FileInputStream f = null;
//        try {
//            f = new FileInputStream("/sdcard/grabCut.jpg");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Log.d("fff",e.toString());
//        }
//        Bitmap bm = null;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 8;//图片的长宽都是原来的1/8
//        BufferedInputStream bis = new BufferedInputStream(f);
//        bm = BitmapFactory.decodeStream(bis, null, options);
//        img.setImageBitmap(bm);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Mat source = Imgcodecs.imread("/sdcard/temp.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
                grabCut(source);
//                floodFull(source);
                Bitmap bit = BitmapFactory.decodeFile("/sdcard/temp1.jpg"); //自定义//路径
                img.setImageBitmap(bit);
            }
        });

    }
    private void floodFull(Mat img){
        Mat mask=new Mat();
        Rect rect = new Rect();
        Imgproc.floodFill(img,mask,new Point(20,20), new Scalar(255, 0, 0), rect, new Scalar(10, 10, 10), new Scalar(10, 10, 10),4);
        Imgcodecs.imwrite("/sdcard/temp1.jpg", mask);
    }
    private void grabCut(Mat img) {
        Mat firstMask = new Mat();
        Mat bgModel = new Mat();
        Mat fgModel = new Mat();
        Mat mask ;

        Mat source = new Mat(1, 1, CvType.CV_8U, new Scalar(Imgproc.GC_PR_FGD));
        //定义矩形
        Rect rect = new Rect(0, 0, 200, 300);

        //执行grabcut分割
        Imgproc.grabCut(img, firstMask, rect, bgModel, fgModel, 1, 0);

        //得到前景
        compare(firstMask,source, firstMask, Core.CMP_EQ);

        //生成输出图像
        Mat foregound = new Mat(img.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));
        //复制前景数据，符合挖出的区域
        img.copyTo(foregound, firstMask);
        mask = new Mat(foregound.size(), CvType.CV_8UC1, new Scalar(255, 255, 255));
        Imgproc.cvtColor(foregound, mask, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(mask, mask, 254, 255, Imgproc.THRESH_BINARY_INV);
        //挖出后的替代color
        Mat vols = new Mat(1, 1, CvType.CV_8UC3, new Scalar(255,0,0));
        img.setTo(vols, mask);
        Imgcodecs.imwrite("/sdcard/temp1.jpg", img);


    }
}
