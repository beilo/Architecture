package com.minister.architecture.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.File;
import java.io.FileOutputStream;

import static com.minister.architecture.util.SystemUtil.PATH_SDCARD;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by leipe on 2017/11/14.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SystemUtil.class, Environment.class, File.class, Toast.class, Uri.class})
public class SystemUtilTest {
    @Mock
    private Context context;
    @Mock
    private Bitmap bitmap;
    @Mock
    private FileOutputStream fos;

    @Before
    public void setUp() throws Exception {

        mockStatic(File.class);

        Whitebox.setInternalState(SystemUtil.class, "SEPARATOR", "/");

        mockStatic(Uri.class);

        mockStatic(Toast.class);

        mockStatic(Environment.class);

    }

    @Test
    public void saveBitmapToFile() throws Exception {
        // 初始化fileName
        String url = "http://pic2.16pic.com/00/24/38/16pic_2438497_b.png";
        String fileName = url.substring(url.lastIndexOf("/"), url.lastIndexOf(".")) + ".png";

        // mock fileDir
        File fileDir = mock(File.class);
        // 重新new file 赋予参数PATH_SDCARD(该参数要跟实际调用的一模一样),并且返回mock(fileDir)
        whenNew(File.class).withArguments(PATH_SDCARD).thenReturn(fileDir);
        // 设置静态函数getExternalStorageDirectory ,返回值为fileDir(随便设置的)
        when(Environment.class, "getExternalStorageDirectory").thenReturn(fileDir);
        // 返回fileDir.exists()=false
        when(fileDir.exists()).thenReturn(false);
        Assert.assertFalse(fileDir.exists());

        // mock imgFile
        File imgFile = mock(File.class);
        // 重新new file 赋予参数(fileDir, fileName)(该参数要跟实际调用的一模一样),并且返回mock(imgFile)
        whenNew(File.class).withArguments(fileDir, fileName).thenReturn(imgFile);
        // 返回imgFile.exists()=false
        when(imgFile.exists()).thenReturn(false);
        Assert.assertFalse(imgFile.exists());

        // mock uri
        Uri uri = mock(Uri.class);
        // 设置静态函数fromFile ,传入参数为uri(要和实际相同)
        when(Uri.class, "fromFile", imgFile).thenReturn(uri);

        //
        whenNew(FileOutputStream.class).withArguments(imgFile).thenReturn(fos);
        when(bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)).thenReturn(true);


        Toast toast = mock(Toast.class);
        when(Toast.class, "makeText", eq(context), anyString(), eq(Toast.LENGTH_SHORT)).thenReturn(toast);


        Intent sendIntent = mock(Intent.class);
        whenNew(Intent.class).withArguments(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri).thenReturn(sendIntent);

        Uri saveUri = SystemUtil.saveBitmapToFile(context, url, bitmap, false);

        verify(context).sendBroadcast(sendIntent);

        Assert.assertEquals(saveUri,uri);

        verifyStatic();
        Toast.makeText(context, "保存图片成功", Toast.LENGTH_SHORT);
        verifyStatic(times(1));
        Uri.fromFile(imgFile);
    }


}