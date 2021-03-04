package cst.zju.edu.PhotoClip;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import cst.zju.edu.PhotoClip.utils.Constants;
import cst.zju.edu.PhotoClip.adapter.ImageAdapter;
import com.donkingliang.imageselector.utils.ImageSelector;
import cn.jarlen.photoedit.operate.OperateUtils;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cst.zju.edu.PhotoClip.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Toolbar.OnMenuItemClickListener,EasyPermissions.PermissionCallbacks{
    /* 用来标识请求gallery的activity */
    private static final int PHOTO_PICKED_WITH_DATA = 3021;

    /* 边框 */
    private static final int PHOTO_FRAME_WITH_DATA = 3024;

    /* 马赛克 */
    private static final int PHOTO_MOSAIC_WITH_DATA = 3025;

    /* 涂鸦 */
    private static final int PHOTO_DRAW_WITH_DATA = 3026;

    /* 增强 */
    private static final int PHOTO_ENHANCE_WITH_DATA = 3029;

    /* 旋转 */
    private static final int PHOTO_REVOLVE_WITH_DATA = 3030;

    /* 图像变形 */
    private static final int PHOTO_WARP_WITH_DATA = 3031;

    /* 添加水印图片 */
    private static final int PHOTO_ADD_WATERMARK_DATA = 3032;
    /* 添加文字 */
    private static final int PHOTO_ADD_TEXT_DATA = 3033;

    private static final int REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012;
    private RecyclerView rvImage;
    private ImageAdapter mAdapter;
    private RadioButton shot;
    private RadioButton SingleSelect;
    private RadioButton MultiSelect;
    private RadioButton Slice;
    private RadioButton Edit;
    private OperateUtils operateUtils;
    private Class<?> intentClass;
    private int intentType = 0;
    private int layoutHeight,layoutwidth;
    private String photoPath = null, camera_path;
    int width = 0;
    private ImageView pictureShow;
    private LinearLayout content_layout;
    private final String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvImage = findViewById(R.id.rv_image);
        shot=findViewById(R.id.rb_user);
        SingleSelect=findViewById(R.id.rb_home);
        MultiSelect=findViewById(R.id.rb_classify);
        Slice=findViewById(R.id.rb_read);
        Edit=findViewById(R.id.rb_cart);
        //修改用瀑布流式布局实现
        rvImage.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new ImageAdapter(this);
        rvImage.setAdapter(mAdapter);
        shot.setOnClickListener(this);
        SingleSelect.setOnClickListener(this);
        MultiSelect.setOnClickListener(this);
        Slice.setOnClickListener(this);
        Edit.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_launcher);
        toolbar.setTitle("编辑选项请点击右侧按钮");
        toolbar.inflateMenu(R.menu.base_toolbar_menu);
        toolbar.setOnMenuItemClickListener(this);
        operateUtils = new OperateUtils(this);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels; // 屏幕宽度（像素）
        pictureShow = (ImageView) findViewById(R.id.pictureShow);
        content_layout = (LinearLayout) findViewById(R.id.mainLayout);
        content_layout.post(new Runnable() {
            @Override
            public void run() {
                layoutwidth = content_layout.getWidth();
                layoutHeight = content_layout.getHeight();
            }
        });
        int hasWriteExternalPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED) {
            //预加载手机图片。加载图片前，请确保app有读取储存卡权限
            ImageSelector.preload(this);
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_REQUEST_CODE);
        }
        if (!EasyPermissions.hasPermissions(this, permissions)) {
            EasyPermissions.requestPermissions(new PermissionRequest.Builder(this, 1, permissions).build());
        }
    }

    //后来为了编辑图片加的
    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if (photoPath == null) {
            Toast.makeText(MainActivity.this, "请选择图片", Toast.LENGTH_SHORT)
                    .show();
            return true;//巨坑中间加了个FrameLayout选项条出不来。原本只是用来给RecyclerView指定上边界。
        }
//
        switch (item.getItemId()) {
            case R.id.action_wrap:
                intentClass = WarpActivity.class;
                intentType = PHOTO_WARP_WITH_DATA;
                break;

            case R.id.action_draw:
                intentClass = DrawBaseActivity.class;
                intentType = PHOTO_DRAW_WITH_DATA;
                break;
            case R.id.action_frame:
                intentClass = PhotoFrameActivity.class;
                intentType = PHOTO_FRAME_WITH_DATA;
                break;
            case R.id.action_addtv:
                intentClass = AddTextActivity.class;
                intentType = PHOTO_ADD_TEXT_DATA;
                break;
            case R.id.action_addwm:
                intentClass = AddWatermarkActivity.class;
                intentType = PHOTO_ADD_WATERMARK_DATA;
                break;
            case R.id.action_mosaic:
                intentClass = MosaicActivity.class;
                intentType = PHOTO_MOSAIC_WITH_DATA;
                break;
            case R.id.action_enchance:
                intentClass = EnhanceActivity.class;
                intentType = PHOTO_ENHANCE_WITH_DATA;
                break;
            case R.id.action_rotate:
                intentClass = RevolveActivity.class;
                intentType = PHOTO_REVOLVE_WITH_DATA;
                break;
            default:
                intentClass = null;
                intentType = 0;
                break;

        }

        Toast.makeText(MainActivity.this, "请点击编辑按钮", Toast.LENGTH_SHORT).show();

        return false;
    }

    // 将生成的图片保存到内存中，后来补充的，强行加的函数
    public String SaveBitmap(Bitmap bitmap, String name) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File dir = new File(Constants.filePath);
            if (!dir.exists())
                dir.mkdir();
            File file = new File(Constants.filePath + name + ".jpg");
            FileOutputStream out;
            try {
                out = new FileOutputStream(file);
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                    out.flush();
                    out.close();
                }
                return file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
//强行加的函数

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;//看看这句能不能让取消编辑后的照片正常显示
        }
        if(requestCode==PHOTO_PICKED_WITH_DATA){
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            for(String image:images){
                photoPath=image;
            }
            if (content_layout.getWidth() == 0) {
                timer.schedule(task, 10, 1000);
            } else {
                compressed();
            }

        }
        else if(requestCode==PHOTO_MOSAIC_WITH_DATA||requestCode==PHOTO_DRAW_WITH_DATA||
                requestCode==PHOTO_ADD_TEXT_DATA||requestCode==PHOTO_ADD_WATERMARK_DATA||requestCode==PHOTO_ENHANCE_WITH_DATA||requestCode==PHOTO_REVOLVE_WITH_DATA
                ||requestCode==PHOTO_WARP_WITH_DATA||requestCode==PHOTO_FRAME_WITH_DATA){
            String resultPath = data.getStringExtra("camera_path");
            Bitmap resultBitmap = BitmapFactory.decodeFile(resultPath);
            pictureShow.setImageBitmap(resultBitmap);
        }
        //原来不管拍没拍照都走下面。
        else if (requestCode == REQUEST_CODE && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            boolean isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false);
            //这里已经要处理返回数据了，应该是要按下相机键就拿照片的
            mAdapter.refresh(images);//其实只通知了RecyclerView更新
        }
    }


    /**
     * 处理权限申请的回调。
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_WRITE_EXTERNAL_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //预加载手机图片
                ImageSelector.preload(this);
            } else {
                //拒绝权限。
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_home:
                //单选
                SingleSelect.setChecked(false);
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .start(this,PHOTO_PICKED_WITH_DATA); //
                break;

            case R.id.rb_classify:
                //多选(最多9张)
                MultiSelect.setChecked(false);
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(this, REQUEST_CODE); // 打开相册
                break;



            case R.id.rb_read:
                //单选并剪裁
                Slice.setChecked(false);
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setCrop(true)  // 设置是否使用图片剪切功能。
                        .setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                        .setSingle(true)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .start(this, REQUEST_CODE); // 打开相册
                break;

            case R.id.rb_user:
                //仅拍照
                shot.setChecked(false);
                //强行拿照片；通过Intent传数据，StartActivity,再让OnActivityResult处理。
                //OnResult企图拿到的和两个start传给他的不一致
                ImageSelector.builder()
                        .onlyTakePhoto(true)  // 仅拍照，不打开相册
                        .start(this, REQUEST_CODE);
                break;

            case R.id.rb_cart:
                if (photoPath == null) {
                    Toast.makeText(MainActivity.this, "请选择图片",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (intentClass == null) {
                    Toast.makeText(MainActivity.this, "请图片操作类型",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 将图片路径photoPath传到所要调试的模块
                Intent photoFrameIntent = new Intent(MainActivity.this,
                        intentClass);
                photoFrameIntent.putExtra("camera_path", camera_path);
                MainActivity.this.startActivityForResult(photoFrameIntent,
                        intentType);
                break;
        }
    }

    //各种辅助函数
    private void compressed() {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, opt);
        System.out.println(bitmap);
        int layoutHeight = content_layout.getHeight();
        System.out.println(layoutHeight);
        System.out.println(layoutwidth);
        float scale = 0f;
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        scale = bitmapHeight > bitmapWidth
                ? layoutHeight / (bitmapHeight * 1f)
                : width / (bitmapWidth * 1f);
        System.out.println(scale);
        Bitmap resizeBmp;
        if (scale != 0) {
            int bitmapheight = bitmap.getHeight();
            int bitmapwidth = bitmap.getWidth();
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale); // 长和宽放大缩小的比例
            resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmapwidth,
                    bitmapheight, matrix, true);
        } else {
            resizeBmp = bitmap;
        }
        pictureShow.setImageBitmap(resizeBmp);
        camera_path = SaveBitmap(resizeBmp, "saveTemp");
    }

    final Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (content_layout.getWidth() != 0) {
                    Log.i("LinearLayoutW", content_layout.getWidth() + "");
                    Log.i("LinearLayoutH", content_layout.getHeight() + "");
                    // 取消定时器
                    timer.cancel();
                    compressed();
                }
            }
        }
    };
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            myHandler.sendMessage(message);
        }
    };


    //各种辅助函数
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(MainActivity.this, "请赋予必要的权限", Toast.LENGTH_SHORT).show();
        finish();
    }
}
