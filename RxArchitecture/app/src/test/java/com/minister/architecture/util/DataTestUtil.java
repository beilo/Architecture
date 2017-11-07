package com.minister.architecture.util;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Flowable;
import io.reactivex.subscribers.DefaultSubscriber;

/**
 * Created by leipe on 2017/11/7.
 */

public class DataTestUtil {
    public static <T> T getValue(Flowable<T> flowable) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);

        DefaultSubscriber<T>
                defaultSubscriber = new DefaultSubscriber<T>() {
            @Override
            public void onNext(T t) {
                data[0] = t;
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
                latch.countDown();
            }
        };
        flowable
                .subscribe(defaultSubscriber);
        latch.await(2, TimeUnit.SECONDS);

        return (T) data[0];
    }


    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    public static String getUnicode(String s) {
        try {
            StringBuffer out = new StringBuffer("");
            byte[] bytes = s.getBytes("unicode");
            for (int i = 0; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                out.append(str1);
                out.append(str);

            }
            return out.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
