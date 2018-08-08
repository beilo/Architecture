object Versions {
    val applicationId = "com.minister.architecture"
    val applicationId_zhihu = "com.minister.architecture.zhihu"
    val applicationId_xinwen = "com.minister.architecture.xinwen"
    val applicationId_gank = "com.minister.architecture.gank"

    val compileSdkVersion = 26
    val buildToolsVersion = "26.0.2"

    val minSdkVersion = 22
    val targetSdkVersion = 26
    val versionCode = 1
    val versionName = "1.0"

    val support_lib = "26.1.0"
    val constraint_layout = "1.1.1"
    val lifecycle_lib = "1.1.1"
    val fragmentation = "1.3.3"
    val baseRecyclerViewAdapterHelper = "2.9.22"
    val glide = "3.8.0"
    val barlibrary = "2.3.0"
    val butterknife = "8.5.1"
    val rx = "2.0.1"
    val rxPermission = "0.9.4@aar"
    val greendao = "3.2.2"
    val retrofit_lib = "2.2.0"
    val okhttp_lib = "3.8.1"
    val stetho_lib = "1.5.0"
    val logger = "2.1.1"
    val banner = "1.4.10"
    val eventbus = "3.0.0"
    val eventbus_activity_scope = "1.0.0"

    val test_junit = "4.12"
    val test_mockito_core = "2.10.0"
    val test_robolectric = "3.4.2"
    val test_robolectric_shadows_support_v4 = "3.4-rc2"
    val test_okhttp3_mockwebserver = "3.8.1"
    val test_core_testing = "1.0.0-alpha9-1"
    val bugly_crashreport = "2.6.5"
    val bugly_nativecrashreport = "3.3.1"

    val arouter_api = "1.3.1";
    val arouter_compiler = "1.1.4";
}

object Libs {
    val support_appcompat_v7 = "com.android.support:appcompat-v7:${Versions.support_lib}"
    val support_cardview = "com.android.support:cardview-v7:${Versions.support_lib}"
    val support_recyclerview = "com.android.support:recyclerview-v7:${Versions.support_lib}"
    val support_design = "com.android.support:design:${Versions.support_lib}"
    val support_support_v4 = "com.android.support:support-v4:${Versions.support_lib}"
    val support_constraint_layout = "com.android.support.constraint:constraint-layout:${Versions.constraint_layout}"

    val arch_lifecycle_runtime = "android.arch.lifecycle:runtime:${Versions.lifecycle_lib}"
    val arch_lifecycle_extensions = "android.arch.lifecycle:extensions:${Versions.lifecycle_lib}"
    val arch_lifecycle_compiler = "android.arch.lifecycle:compiler:${Versions.lifecycle_lib}"


    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val barlibrary = "com.gyf.barlibrary:barlibrary:${Versions.barlibrary}"
    val fragmentation = "me.yokeyword:fragmentation:${Versions.fragmentation}"
    val baseRecyclerViewAdapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.baseRecyclerViewAdapterHelper}"
//    val butterknife = "com.jakewharton:butterknife:${Versions.butterknife}"
//    val butterknife_compiler = "com.jakewharton:butterknife-compiler:${Versions.butterknife}"
    val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rx}"
    val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rx}"
    val rxPermission = "com.tbruyelle.rxpermissions2:rxpermissions:${Versions.rxPermission}"
    val greendao = "org.greenrobot:greendao:${Versions.greendao}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_lib}"
    val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_lib}"
    val retrofit_adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit_lib}"
    val okhttp3_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_lib}"
    val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp_lib}"
    val stetho = "com.facebook.stetho:stetho:${Versions.stetho_lib}"
    val stetho_okhttp3 = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho_lib}"
    val logger = "com.orhanobut:logger:${Versions.logger}"
    val banner = "com.youth.banner:banner:${Versions.banner}"
    val eventbus_activity_scope = "me.yokeyword:eventbus-activity-scope:${Versions.eventbus_activity_scope}"
    val eventbus = "org.greenrobot:eventbus:${Versions.eventbus}"
    val test_junit = "junit:junit:${Versions.test_junit}"
    val test_mockito_core = "org.mockito:mockito-core:${Versions.test_mockito_core}"
    val robolectric = "org.robolectric:robolectric:${Versions.test_robolectric}"
    val robolectric_shadows_support_v4 = "org.robolectric:shadows-support-v4:${Versions.test_robolectric_shadows_support_v4}"
    val okhttp3_mockwebserver = "com.squareup.okhttp3:mockwebserver:${Versions.test_okhttp3_mockwebserver}"
    val core_testing = "android.arch.core:core-testing:${Versions.test_core_testing}"
    val bugly_crashreport = "com.tencent.bugly:crashreport:${Versions.bugly_crashreport}"
    val bugly_nativecrashreport = "com.tencent.bugly:nativecrashreport:${Versions.bugly_nativecrashreport}"
    val javax_inject = "javax.inject:javax.inject:1"

    val arouter_api = "com.alibaba:arouter-api:${Versions.arouter_api}"
    val arouter_annotation = "com.alibaba:arouter-annotation:1.0.4"
    val arouter_compiler = "com.alibaba:arouter-compiler:${Versions.arouter_compiler}"
}