package com.minister.architecture.util;

/**
 *
 * 作者：键盘男
 * 链接：http://www.jianshu.com/p/cdfeb6c3d099
 * 來源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */

public class RxUnitTestTools {
    private static boolean isInitRxTools = false;

    /**
     * 把异步变成同步，方便测试
     */
    public static void openRxTools() {
        if (isInitRxTools) {
            return;
        }
        isInitRxTools = true;

//        RxAndroidSchedulersHook rxAndroidSchedulersHook = new RxAndroidSchedulersHook() {
//            @Override
//            public Scheduler getMainThreadScheduler() {
//                return Schedulers.immediate();
//            }
//        };
//
//        RxJavaSchedulersHook rxJavaSchedulersHook = new RxJavaSchedulersHook() {
//            @Override
//            public Scheduler getIOScheduler() {
//                return Schedulers.immediate();
//            }
//        };
//
//        // reset()不是必要，实践中发现不写reset()，偶尔会出错，所以写上保险^_^
//        RxAndroidPlugins.reset();
//        RxAndroidPlugins.getInstance().registerSchedulersHook(rxAndroidSchedulersHook);
//        RxJavaPlugins.getInstance().reset();
//        RxJavaPlugins.getInstance().registerSchedulersHook(rxJavaSchedulersHook);
    }

}
