package com.minister.architecture.ui.weather;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.minister.architecture.R;
import com.minister.architecture.base.BaseSupportFragment;
import com.minister.architecture.model.bean.WeatherBean;
import com.minister.architecture.util.RxHelp;
import com.minister.architecture.viewmodel.WeatherViewModel;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * @author 被咯苏州
 *         Created by leipe on 2018/2/7.
 */

public class WeatherFragment extends BaseSupportFragment {

    Toolbar toolbar;
    TextView tvDate;
    TextView tvNowTemperature;
    TextView tvWeather;
    TextView tvTemperature;
    ImageView imgWeatherIco;
    ConstraintLayout clWeatherTop;
    SwipeRefreshLayout refresh;

    // 是否初始化成功
    private static boolean IS_INIT_TTS = false;

    WeatherViewModel mViewModel;


    // 语音合成对象
    private SpeechSynthesizer mTts;
    private boolean mIsPlayWeather = false;
    private RxPermissions mRxPermissions;

    public static WeatherFragment newInstance(boolean isPlayWeather) {
        Bundle args = new Bundle();
        args.putBoolean("isPlayWeather", isPlayWeather);
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsPlayWeather = getArguments().getBoolean("isPlayWeather");
    }
private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_weather_home, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar = mView.findViewById(R.id.toolbar);
        tvDate = mView.findViewById(R.id.tv_date);
        tvNowTemperature = mView.findViewById(R.id.tv_nowTemperature);
        tvWeather = mView.findViewById(R.id.tv_weather);
        tvTemperature = mView.findViewById(R.id.tv_temperature);
        imgWeatherIco = mView.findViewById(R.id.img_weatherIco);
        clWeatherTop = mView.findViewById(R.id.cl_weather_top);
        refresh = mView.findViewById(R.id.refresh);

        mViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        initTTS();

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        toolbar.inflateMenu(R.menu.weather_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_new) {
                    AddWeatherSettingDialogFragment.newInstance()
                            .show(getFragmentManager());
                }
                return true;
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        refresh.setRefreshing(true);
        loadData();

        if (mIsPlayWeather)
            checkPermissionAndAction();
    }

    private void loadData() {
        mDisposable.add(mViewModel.getWeather(WeacConstants.CITY)
                .compose(RxHelp.<WeatherBean>rxScheduler())
                .subscribe(new Consumer<WeatherBean>() {
                    @Override
                    public void accept(WeatherBean weatherBean) throws Exception {
                        fillView(weatherBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(_mActivity, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        stopRefresh();
                    }
                }));
    }

    private void fillView(WeatherBean weatherBean) {
        if (weatherBean != null) {
            Glide.with(_mActivity).load(weatherBean.getWeatherIco()).into(imgWeatherIco);

            setToolbar(toolbar, weatherBean.getCityName(), 0);
            tvDate.setText(weatherBean.getDate());
            tvNowTemperature.setText(weatherBean.getNowTemperature() + " ℃");
            tvWeather.setText(weatherBean.getWeather());
            tvTemperature.setText(weatherBean.getTemperature());
            if (refresh.isRefreshing()) {
                refresh.setRefreshing(false);
            }
        }
    }

    private void stopRefresh() {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
        }
    }

    private void initTTS() {
        Setting.setLocationEnable(false);
        mTts = SpeechSynthesizer.createSynthesizer(_mActivity, mTtsInitListener);

        mTts.setParameter(SpeechConstant.PARAMS, null);
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);

        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.e(TAG, "初始化失败,错误码：" + code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
                // final String strTextToSpeech = "科大讯飞，让世界聆听我们的声音";
                // mTts.startSpeaking(strTextToSpeech, mTtsListener);
                IS_INIT_TTS = true;
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            Log.d(TAG, "开始播放");
        }

        @Override
        public void onSpeakPaused() {
            Log.d(TAG, "暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            Log.d(TAG, "继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
            Log.d(TAG, "合成进度" + percent);
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
            Log.d(TAG, "播放进度" + percent);
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                Log.d(TAG, "播放完成");
            } else if (error != null) {
                Log.e(TAG, "初始化失败,错误码：" + error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void checkPermissionAndAction() {
        if (mRxPermissions == null) {
            mRxPermissions = new RxPermissions(_mActivity);
        }
        // http://blog.csdn.net/u013553529/article/details/68948971
        mRxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(@NonNull Permission permission) throws Exception {
                        if (permission.granted) { // 用户已经同意该权限
                            if (IS_INIT_TTS) {
                                mDisposable.add(
                                        mViewModel.getBroadcastWeather(WeacConstants.CITY)
                                                .compose(RxHelp.<String>rxScheduler())
                                                .subscribe(new Consumer<String>() {
                                                    @Override
                                                    public void accept(String s) throws Exception {
                                                        mTts.startSpeaking(s, mTtsListener);
                                                        Toast.makeText(_mActivity, s, Toast.LENGTH_SHORT).show();
                                                    }
                                                }, new Consumer<Throwable>() {
                                                    @Override
                                                    public void accept(Throwable throwable) throws Exception {
                                                        Toast.makeText(_mActivity, "语音播放发生异常" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }));
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) { // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Toast.makeText(_mActivity, "没有选中『不再询问』", Toast.LENGTH_SHORT).show();
                        } else { // 用户拒绝了该权限，并且选中『不再询问』
                            Toast.makeText(_mActivity, "『不再询问』", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
