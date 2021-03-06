package com.lmroom.baselib.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 系统帮助类
 * 用于提供涉及到系统操作的快捷方法
 * @author leipe
 */
public class SystemUtil {

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "blsz" + File.separator + "Architecture";

    /**
     * 将图片保存到本地
     * @param context 上下文
     * @param url 网络图片url,用于设置图片名
     * @param bitmap 图片Bitmap
     * @param isShare 是否分享
     * @return 保存后的uri
     */
    public static Uri saveBitmapToFile(Context context, String url, Bitmap bitmap, boolean isShare) {
        String fileName = url.substring(url.lastIndexOf("/"), url.lastIndexOf(".")) + ".png";
        File fileDir = new File(PATH_SDCARD);
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        File imageFile = new File(fileDir,fileName);
        Uri uri = Uri.fromFile(imageFile);
        if (isShare && imageFile.exists()){
            return uri;
        }
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            boolean isCompress = bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            if (isCompress) {
                Toast.makeText(context, "保存图片成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "保存图片失败", Toast.LENGTH_SHORT).show();
            }
            fos.flush();
            fos.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            Toast.makeText(context, "保存图片失败", Toast.LENGTH_SHORT).show();
        }
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imageFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
        return uri;
    }
}
