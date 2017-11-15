package com.minister.architecture.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by leipe on 2017/11/14.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ShareUtil.class, Intent.class})
public class ShareUtilTest {

    @Mock
    private Context context;
    @Mock
    private Intent intent;

    @Before
    public void setUp() throws Exception {
        whenNew(Intent.class).withArguments(Intent.ACTION_SEND).thenReturn(intent);

    }

    @Test
    public void shareText() throws Exception {
        mockStatic(Intent.class);

        assertNotNull(context);
        assertNotNull(intent);

        Intent intentCreateChooser = mock(Intent.class);
        when(Intent.createChooser(intent,"title")).thenReturn(intentCreateChooser);

        ShareUtil.shareText(context, "text", "title");

        verify(context, times(1)).startActivity(intentCreateChooser);
        verify(intent, times(1)).setType("text/plain");
        verify(intent, times(1)).putExtra(Intent.EXTRA_TEXT,"text");

    }

    @Test
    public void shareImage() throws Exception {
        mockStatic(Intent.class);

        assertNotNull(context);
        assertNotNull(intent);

        Intent intentCreateChooser = mock(Intent.class);
        when(Intent.createChooser(intent,"title")).thenReturn(intentCreateChooser);

        Uri uri = mock(Uri.class);
        ShareUtil.shareImage(context,uri,"title");

        verify(context).startActivity(intentCreateChooser);
        verify(intent).setType("image/png");
        verify(intent).putExtra(Intent.EXTRA_STREAM, uri);
    }

    @Test
    public void sendEmail() throws Exception {
        mockStatic(Uri.class);
        mockStatic(Intent.class);

        whenNew(Intent.class).withArguments(Intent.ACTION_SENDTO,Uri.parse("mailto:codeest.dev@gmail.com")).thenReturn(intent);

        Intent intentCreateChooser = mock(Intent.class);
        when(Intent.createChooser(intent,"title")).thenReturn(intentCreateChooser);

        ShareUtil.sendEmail(context,"title");

        verify(context).startActivity(intentCreateChooser);
    }

}