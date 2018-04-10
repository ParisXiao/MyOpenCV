package com.opencv.myopencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity {
    private ImageView img;
    private Button  btn;
    static {
        System.loadLibrary("opencv_java3");
        System.loadLibrary("native-lib");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img= (ImageView) findViewById(R.id.img);
        btn= (Button) findViewById(R.id.change);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.temp);
        img.setImageBitmap(bitmap);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grabCutFromJNI("/sdcard/temp.jpg");
                Bitmap bit = BitmapFactory.decodeFile("/sdcard/temp2.jpg"); //自定义//路径
                img.setImageBitmap(bit);

            }
        });
        // Example of a call to a native method
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public  native void grabCutFromJNI(String path);
    // Used to load the 'native-lib' library on application startup.

}
