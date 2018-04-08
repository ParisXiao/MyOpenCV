#include <jni.h>
#include <string>
#include <iostream>
#include "opencv2/core/core.hpp"
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <android/bitmap.h>

using namespace cv;
using namespace std;
extern "C"
JNIEXPORT void JNICALL
Java_com_opencv_myopencv_MainActivity_grabCutFromJNI(JNIEnv *env, jobject instance,
                                                     jobject bitmap) {

    // TODO
    AndroidBitmapInfo bitmapInfo;
    void *pixels;
    CV_Assert(AndroidBitmap_getInfo(env, bitmap, &bitmapInfo) >= 0);
    CV_Assert(bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888 || bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGB_565);
    CV_Assert(AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0);
    CV_Assert(pixels);
    int width, height, ret; //解析bitmap
    if (bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        width = bitmapInfo.width;
        height = bitmapInfo.height;
        Mat srcImg(height, width, CV_8UC4, pixels);
        Mat temp, mask;
        cvtColor(srcImg,temp,COLOR_RGBA2BGR);
        cvtColor(srcImg,temp,COLOR_BGR2HSV);
        inRange(temp,Scalar(78,43,46),Scalar(110,255,255),mask);
        Mat kernel=getStructuringElement(MORPH_RECT,Size(3,3));
        morphologyEx(mask,mask,MORPH_OPEN,kernel);
        int  w=srcImg.cols;
        int  h=srcImg.cols;
        if (srcImg.isContinuous()&&mask.isContinuous()){
            width=width*height;
            height=1;
        }
        for (int i = 0; i <height ; ++i) {
            Ptr<Vec3b> data=srcImg.ptr<Vec3b>(i);
            Ptr<uchar> m_maks=mask.ptr<uchar>(i);
            for (int j = 0; j <width ; ++j) {
                if (m_maks[j]==255){
                    data[j]=Vec3b(0,0,255);
                }

            }
        }
    } else {
        width = bitmapInfo.width;
        height = bitmapInfo.height;
        Mat srcImg(height, width, CV_8UC2, pixels);
        Mat temp, mask;
        cvtColor(srcImg,temp,COLOR_RGB2HSV);
        inRange(temp,Scalar(78,43,46),Scalar(110,255,255),mask);
        Mat kernel=getStructuringElement(MORPH_RECT,Size(3,3));
        morphologyEx(mask,mask,MORPH_OPEN,kernel);
        int  w=srcImg.cols;
        int  h=srcImg.cols;
        if (srcImg.isContinuous()&&mask.isContinuous()){
            width=width*height;
            height=1;
        }
        for (int i = 0; i <height ; ++i) {
            Ptr<Vec3b> data=srcImg.ptr<Vec3b>(i);
            Ptr<uchar> m_maks=mask.ptr<uchar>(i);
            for (int j = 0; j <width ; ++j) {
                if (m_maks[j]==255){
                    data[j]=Vec3b(0,0,255);
                }

            }
        }
    }
    AndroidBitmap_unlockPixels(env, bitmap);
}