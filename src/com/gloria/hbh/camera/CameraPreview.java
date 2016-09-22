/*
 * Copyright (C) 2007 The Android Open Source Project Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.gloria.hbh.camera;

import java.io.IOException;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gloria.hbh.adapter.WaterImgGalleryAdapter;
import com.gloria.hbh.constant.BaseConstants;
import com.gloria.hbh.main.Activity_Base;
import com.gloria.hbh.main.R;
import com.gloria.hbh.myview.MyGallery;
import com.gloria.hbh.myview.PageIndicatorView;
import com.gloria.hbh.util.ImageUtils;

// ----------------------------------------------------------------------
public class CameraPreview extends Activity_Base implements SurfaceHolder.Callback,OnClickListener
{
    // private SurfaceView mSurfaceView;
    // private SurfaceHolder mSurfaceHolder;
    // private boolean bifPrieview = false;
    // private Camera mCamera;
    public static int ScrrenWidth;
    public static int ScrrenHeight;
    Button btn_back;
    Button btn_pic;
    TextView btn_takephoto;
    LinearLayout layout_takephoto;
    private SaveThread mSaveThread=null;
    private static int PicPhotoCount=-1;
    MyGallery gallery;
    PageIndicatorView view_page;
    ArrayList<String> imgLists;
    WaterImgGalleryAdapter imgGalleryAdapter;
    int isSelectPos=0;
    private CameraPreview instanceCameraPreview;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_camera);
        CameraManager.init(getApplication());
        this.instanceCameraPreview=this;
        // DisplayMetrics dm = new DisplayMetrics();
        // getWindowManager().getDefaultDisplay().getMetrics(dm);
        // ScrrenWidth = dm.widthPixels;
        // ScrrenHeight=dm.heightPixels;
        // mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);
        btn_back=(Button)findViewById(R.id.btn_back);
        btn_takephoto=(TextView)findViewById(R.id.btn_takephoto);
        btn_pic=(Button)findViewById(R.id.btn_pic);
        layout_takephoto=(LinearLayout)findViewById(R.id.layout_takephoto);
        // mSurfaceHolder = mSurfaceView.getHolder();
        // mSurfaceHolder.addCallback(CameraPreview.this);
        // mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        SurfaceView surfaceView=(SurfaceView)findViewById(R.id.surface_camera);
        SurfaceHolder surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        btn_back.setOnClickListener(this);
        btn_takephoto.setOnClickListener(this);
        btn_pic.setOnClickListener(this);
        layout_takephoto.setOnClickListener(this);
        PicPhotoCount=0;
        initData();
        gallery=(MyGallery)findViewById(R.id.gallery);
        view_page=(PageIndicatorView)findViewById(R.id.view_page);
        view_page.setTotalPage(getImgLists().size());
        gallery.setOnItemSelectedListener(new OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0,View arg1,int position,long id)
            {
                isSelectPos=position;
                view_page.setCurrentPage(position);
            }
            public void onNothingSelected(AdapterView<?> arg0)
            {
            }
        });
        imgGalleryAdapter=new WaterImgGalleryAdapter(imgLists,imageLoader);
        gallery.setAdapter(imgGalleryAdapter);
    }
    private void initData()
    {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        getImgLists().add("drawable://" + R.drawable.water_0);
        getImgLists().add("drawable://" + R.drawable.water_1);
        getImgLists().add("drawable://" + R.drawable.water_2);
        getImgLists().add("drawable://" + R.drawable.water_3);
        getImgLists().add("drawable://" + R.drawable.water_4);
        getImgLists().add("drawable://" + R.drawable.water_5);
    }
    public ArrayList<String> getImgLists()
    {
        if(imgLists == null)
        {
            imgLists=new ArrayList<String>(1);
        }
        return imgLists;
    }
    public void setImgLists(ArrayList<String> imgLists)
    {
        this.imgLists=imgLists;
    }
    public void onClick(View v)
    {
        if(v == btn_back)
        {
            finish();
        }
        else
            if(v == btn_takephoto || v == layout_takephoto)
            {
                btn_takephoto.setClickable(false);
                layout_takephoto.setClickable(false);
                btn_pic.setClickable(false);
                CameraManager.get().requestPreviewFrame(mHandler,R.id.save,instanceCameraPreview);
            }
            else
                if(v == btn_pic)
                {
                    // TODO
                    if(PicPhotoCount == 0)
                    {
                        PicPhotoCount++;
                        btn_takephoto.setClickable(false);
                        layout_takephoto.setClickable(false);
                        btn_pic.setClickable(false);
                        CameraManager.get().requestPreviewFrame(mHandler,R.id.save,instanceCameraPreview);
                    }
                }
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        CameraManager.get().stopPreview();
        // if (mSaveThread != null) {
        // Message quit = Message.obtain(mSaveThread.mHandler, R.id.quit);
        // quit.sendToTarget();
        // try {
        // mSaveThread.join();
        // } catch (InterruptedException e) {
        // }
        // mSaveThread = null;
        // }
        CameraManager.get().closeDriver();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        // if (mSaveThread == null ) {
        // mSaveThread = new SaveThread(this);
        // mSaveThread.start();
        // }
    }
    
    public final Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message message)
        {
            switch(message.what)
            {
                case R.id.save:
                    byte buf[]=(byte[])message.obj;
                    // 生成水印图片
                    Bitmap bm=BitmapFactory.decodeByteArray(buf,0,buf.length);
                    // Bitmap bitmap = ImageUtils.getScaleImage(bm);
                    int res=0;
                    switch(isSelectPos)
                    {
                        case 0:
                            res=R.drawable.water_0;
                        break;
                        case 1:
                            res=R.drawable.water_1;
                        break;
                        case 2:
                            res=R.drawable.water_2;
                        break;
                        case 3:
                            res=R.drawable.water_3;
                        break;
                        default:
                        break;
                    }
                    Bitmap markbitmap=ImageUtils.getBitmapByDrawable(res);
                    Bitmap resultbitmap=watermarkBitmap(bm,markbitmap,"");
                    String filePath=ImageUtils.getCameraFileName(BaseConstants.CACHE_SAVE_IMG_PATH);
                    try
                    {
                        // 水印图片
                        ImageUtils.saveBitmap(resultbitmap,filePath);
                    }
                    catch(IOException e)
                    {
                        Toast.makeText(CameraPreview.this,"拍照错误,请重新拍摄",Toast.LENGTH_SHORT).show();
                        instanceCameraPreview.finish();
                    }
                    finish();
                break;
            }
        }
    };
    
    /**
     * 加水印 也可以加文字
     * @param src
     * @param watermark
     * @param title
     * @return
     */
    public static Bitmap watermarkBitmap(Bitmap src,Bitmap watermark,String title)
    {
        if(src == null)
        {
            return null;
        }
        int w=src.getWidth();
        int h=src.getHeight();
        // 需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
        Bitmap newb=Bitmap.createBitmap(w,h,Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv=new Canvas(newb);
        cv.drawBitmap(src,0,0,null);// 在 0，0坐标开始画入src
        Paint paint=new Paint();
        // 加入图片
        if(watermark != null)
        {
            int ww=watermark.getWidth();
            int wh=watermark.getHeight();
            // paint.setAlpha(50);
            paint.setAlpha(100);
            // cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);//
            // 在src的右下角画入水印
            // cv.drawBitmap(watermark, 0, 0, paint);// 在src的左上角画入水印
            cv.drawBitmap(watermark,(w - ww + 5) / 2,h - wh + 5,paint);
        }
        else
        {
            Log.i("i","water mark failed");
        }
        // 加入文字
        if(title != null)
        {
            String familyName="宋体";
            Typeface font=Typeface.create(familyName,Typeface.NORMAL);
            TextPaint textPaint=new TextPaint();
            textPaint.setColor(Color.RED);
            textPaint.setTypeface(font);
            textPaint.setTextSize(40);
            // 这里是自动换行的
            // StaticLayout layout = new
            // StaticLayout(title,textPaint,w,Alignment.ALIGN_OPPOSITE,1.0F,0.0F,true);
            // layout.draw(cv);
            // 文字就加左上角算了
            cv.drawText(title,w - 400,h - 40,textPaint);
        }
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.restore();// 存储
        if(src != null && !src.isRecycled())
        {
            src.recycle();
        }
        if(watermark != null && !watermark.isRecycled())
        {
            watermark.recycle();
        }
        return newb;
    }
    public void surfaceChanged(SurfaceHolder holder,int format,int width,int height)
    {
        // CameraManager.get().DriverSurfaceChanged(holder,format, width,
        // height);
        // Log.d("digilinx-Camera surfaceChanged",
        // "width="+width+"height="+height+"Format="+format);
    }
    public void surfaceCreated(SurfaceHolder holder)
    {
        // try {
        // initCamera(holder);
        // } catch (IOException e) {
        // throw new RuntimeException(e);
        // }
        try
        {
            CameraManager.get().openDriver(holder);
            CameraManager.get().startPreview();
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void surfaceDestroyed(SurfaceHolder holder)
    {
    }
    // void initCamera(SurfaceHolder holder)throws IOException {
    //
    // if (!bifPrieview) {
    // if(mCamera==null){
    // mCamera = Camera.open();
    // }
    // if(mCamera==null)
    // {
    // throw new IOException();
    // }
    // }
    // if (mCamera != null && !bifPrieview) {
    // Camera.Parameters p = mCamera.getParameters();
    // p.setPictureFormat(PixelFormat.JPEG);
    // p.setPictureSize(ScrrenWidth, ScrrenHeight);
    // mCamera.setParameters(p);
    // try {
    // mCamera.setPreviewDisplay(holder);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // mCamera.startPreview();
    // bifPrieview = true;
    // }
    //
    // }
    // private void takePicture() {
    // mCamera.takePicture(shuuterCallback, rawCallback, jpegCallback);
    // }
    // private void resetCamera() {
    // if (mCamera != null && bifPrieview) {
    // mCamera.stopPreview();
    // mCamera = null;
    // bifPrieview = false;
    // }
    // }
    // private ShutterCallback shuuterCallback = new ShutterCallback() {
    //
    // public void onShutter() {
    //
    // }
    //
    // };
    // private PictureCallback jpegCallback = new PictureCallback() {
    //
    // public void onPictureTaken(byte[] data, Camera camera) {
    // getIntent().putExtra("pic", data);
    // setResult(20, getIntent());
    // finish();
    // }
    //
    // };
    // private PictureCallback rawCallback = new PictureCallback() {
    //
    // public void onPictureTaken(byte[] data, Camera camera) {
    //
    // }
    //
    // };
    // public void stopPreview() {
    // if(mCamera!=null&&bifPrieview){
    // mCamera.stopPreview();
    // bifPrieview=false;
    // }
    // }
    //
    // public void closeDriver() {
    // if (mCamera != null) {
    // mCamera.release();
    // mCamera = null;
    // }
    //
    // }
    // private void displayFrameworkBugMessageAndExit() {
    // AlertDialog.Builder builder = new AlertDialog.Builder(this);
    // builder.setTitle(getString(R.string.app_name));
    // builder.setMessage(getString(R.string.msg_camera_framework_bug));
    // builder.setPositiveButton("确定", new FinishListener(this));
    // builder.setOnCancelListener(new FinishListener(this));
    // builder.show();
    //
    // public void onError(int error, Camera camera) {
    // /**
    // * 媒体服务器死亡。在这种情况下，应用程序必须释放Camera对象和实例化一个新的。
    // * */
    // if(error==android.hardware.Camera.CAMERA_ERROR_SERVER_DIED)
    // {
    // Log.d(TAG, "CAMERA_ERROR_SERVER_DIED");
    // camera.release();
    // camera = null;
    //
    //
    // AlertDialog.Builder builder = new AlertDialog.Builder(this);
    // builder.setTitle(getString(R.string.app_name));
    // builder.setMessage("CAMERA_ERROR_SERVER_DIED");
    // builder.setPositiveButton("确定", new FinishListener(this));
    // builder.setOnCancelListener(new FinishListener(this));
    // builder.show();
    // }
    // /**
    // * 未指定的错误camerar
    // * */
    // else if(error==android.hardware.Camera.CAMERA_ERROR_UNKNOWN)
    // {
    // Log.d(TAG, "CAMERA_ERROR_UNKNOWN");
    // camera.release();
    // camera = null;
    //
    // AlertDialog.Builder builder = new AlertDialog.Builder(this);
    // builder.setTitle(getString(R.string.app_name));
    // builder.setMessage("CAMERA_ERROR_UNKNOWN");
    // builder.setPositiveButton("确定", new FinishListener(this));
    // builder.setOnCancelListener(new FinishListener(this));
    // builder.show();
    // }
    //
    // }
}
