package com.minister.architecture.model.http;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.minister.architecture.model.bean.GankItemBean;
import com.minister.architecture.model.http.result.GankHttpResponse;
import com.minister.architecture.util.DataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author leipe
 */
@RunWith(JUnit4.class)
public class GankApiTest {

    private static final String TECHLIST_JSON = "{\"error\":false,\"results\":[{\"_id\":\"59fa764b421aa90fe72535ea\",\"createdAt\":\"2017-11-02T09:35:07.929Z\",\"desc\":\"\\u5e26\\u4f60\\u73a9\\u8f6cAndroid Studio 3.0\\u7684\\u6027\\u80fd\\u5206\\u6790\\u5de5\\u5177\",\"publishedAt\":\"2017-11-06T12:40:39.976Z\",\"source\":\"web\",\"type\":\"Android\",\"url\":\"https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247487907&idx=1&sn=202a662ccf28a9b00d7daf2067eb22d6\",\"used\":true,\"who\":\"\\u9648\\u5b87\\u660e\"},{\"_id\":\"59fec807421aa90fe50c01d9\",\"createdAt\":\"2017-11-05T16:12:55.594Z\",\"desc\":\"\\u81ea\\u5b9a\\u4e49ViewPager\\u5b9e\\u73b03D\\u753b\\u5eca\\u6548\\u679c\",\"publishedAt\":\"2017-11-06T12:40:39.976Z\",\"source\":\"web\",\"type\":\"Android\",\"url\":\"http://www.jianshu.com/p/7590431176c1\",\"used\":true,\"who\":\"\\u963f\\u97e6\"},{\"_id\":\"59ffaffe421aa90fef203501\",\"createdAt\":\"2017-11-06T08:42:38.889Z\",\"desc\":\"Dagger2\\u7684\\u8f7b\\u677e\\u6109\\u60a6\\u89e3\\u6790 \\u3002\\u4f9d\\u8d56\\u6ce8\\u5165\\u6846\\u67b6\\uff0c\\u4e00\\u4e2a\\u521a\\u63a5\\u89e6\\u65f6\\u611f\\u89c9\\u9ebb\\u70e6\\uff0c\\u7528\\u4e45\\u4e86\\u5c31\\u4f1a\\u201c\\u5634\\u4e0a\\u8bf4\\u4e0d\\u8981\\uff0c\\u8eab\\u4f53\\u5374\\u5f88\\u8bda\\u5b9e\\u201d\\u7684\\u5f00\\u53d1\\u6da6\\u6ed1\\u5242\\u3002\",\"images\":[\"http://img.gank.io/89aeaed4-00b6-41a5-a6f2-89ca03e8a536\"],\"publishedAt\":\"2017-11-06T12:40:39.976Z\",\"source\":\"web\",\"type\":\"Android\",\"url\":\"http://www.jianshu.com/p/9e5d2dbc4ad6\",\"used\":true,\"who\":\"Shuyu Guo\"},{\"_id\":\"59ffbbe4421aa90fe2f02c28\",\"createdAt\":\"2017-11-06T09:33:24.183Z\",\"desc\":\"\\u8001\\u7a0b\\u5e8f\\u5458\\u603b\\u7ed3\\u768416\\u6761\\u7ecf\\u9a8c\\u6559\\u8bad\",\"publishedAt\":\"2017-11-06T12:40:39.976Z\",\"source\":\"web\",\"type\":\"Android\",\"url\":\"https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247488018&idx=1&sn=0eebb7eb8d783320c2e3f9513b736be8\",\"used\":true,\"who\":\"\\u9648\\u5b87\\u660e\"},{\"_id\":\"59ffc9a2421aa90fe2f02c29\",\"createdAt\":\"2017-11-06T10:32:02.209Z\",\"desc\":\"\\u8fd9\\u53ef\\u80fd\\u662f\\u81f3\\u4eca\\u4e3a\\u6b62\\u6700\\u8be6\\u7ec6\\u5b9e\\u7528\\u7684 Kotlin \\u534f\\u7a0b\\u5e93\\u8be6\\u89e3\\u4e86\\u3002 \",\"images\":[\"http://img.gank.io/aa40ee86-6934-4e3e-95ec-536422e362be\"],\"publishedAt\":\"2017-11-06T12:40:39.976Z\",\"source\":\"web\",\"type\":\"Android\",\"url\":\"https://kymjs.com/code/2017/11/06/01/\",\"used\":true,\"who\":\"\\u5f20\\u6d9b\"},{\"_id\":\"59f8553f421aa90fef2034e9\",\"createdAt\":\"2017-10-31T18:49:35.980Z\",\"desc\":\"\\u6df1\\u5ea6\\u5b66\\u4e60js\\u4e0e\\u5b89\\u5353\\u7684\\u4ea4\\u4e92\\u4ee5\\u53caWebView\\u7684\\u90a3\\u4e9b\\u5751\",\"images\":[\"http://img.gank.io/d1d4f7b4-9291-499a-8b20-c3c485c46119\"],\"publishedAt\":\"2017-11-01T14:20:59.209Z\",\"source\":\"web\",\"type\":\"Android\",\"url\":\"http://www.jianshu.com/p/b9164500d3fb\",\"used\":true,\"who\":\"\\u963f\\u97e6\"},{\"_id\":\"59f92869421aa90fe50c01c1\",\"createdAt\":\"2017-11-01T09:50:33.515Z\",\"desc\":\"Android\\u542f\\u52a8\\u9875\\u9ed1\\u5c4f\\u53ca\\u6700\\u4f18\\u89e3\\u51b3\\u65b9\\u6848\",\"publishedAt\":\"2017-11-01T14:20:59.209Z\",\"source\":\"web\",\"type\":\"Android\",\"url\":\"https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247487886&idx=1&sn=6fbe4d971e873ee351aef213eedba0ae\",\"used\":true,\"who\":\"\\u9648\\u5b87\\u660e\"},{\"_id\":\"59f92b44421aa90fe50c01c3\",\"createdAt\":\"2017-11-01T10:02:44.598Z\",\"desc\":\"\\u53ef\\u8bbe\\u5b9a\\u9634\\u5f71\\u989c\\u8272\\u7684shadow-layout\",\"images\":[\"http://img.gank.io/d3acd780-a1a6-4529-a026-b8bd7967626a\"],\"publishedAt\":\"2017-11-01T14:20:59.209Z\",\"source\":\"chrome\",\"type\":\"Android\",\"url\":\"https://github.com/dmytrodanylyk/shadow-layout\",\"used\":true,\"who\":\"galois\"},{\"_id\":\"59f937f1421aa90fe50c01c4\",\"createdAt\":\"2017-11-01T10:56:49.711Z\",\"desc\":\"LeetCode\\u7684Java\\u9898\\u89e3(updating)\",\"publishedAt\":\"2017-11-01T14:20:59.209Z\",\"source\":\"web\",\"type\":\"Android\",\"url\":\"https://github.com/Blankj/awesome-java-leetcode\",\"used\":true,\"who\":\"Mengjie Cai\"},{\"_id\":\"59f95971421aa90fef2034ec\",\"createdAt\":\"2017-11-01T13:19:45.187Z\",\"desc\":\"Facebook\\u9762\\u7ecf\\u8bb0\",\"images\":[\"http://img.gank.io/7aa86243-57c4-43a4-80ba-de2809dd106e\"],\"publishedAt\":\"2017-11-01T14:20:59.209Z\",\"source\":\"web\",\"type\":\"Android\",\"url\":\"http://www.jianshu.com/p/fd8d3478f6ee\",\"used\":true,\"who\":\"Mengjie Cai\"}]}";
    private static final String GIRLLIST_JSON = "{\"error\":false,\"results\":[{\"_id\":\"59fa7379421aa90fe50c01cc\",\"createdAt\":\"2017-11-02T09:23:05.497Z\",\"desc\":\"11-2\",\"publishedAt\":\"2017-11-06T12:40:39.976Z\",\"source\":\"chrome\",\"type\":\"\\u798f\\u5229\",\"url\":\"http://7xi8d6.com1.z0.glb.clouddn.com/20171102092251_AY0l4b_alrisaa_2_11_2017_9_22_44_335.jpeg\",\"used\":true,\"who\":\"daimajia\"},{\"_id\":\"59f9674c421aa90fe50c01c6\",\"createdAt\":\"2017-11-01T14:18:52.937Z\",\"desc\":\"11-1\",\"publishedAt\":\"2017-11-01T14:20:59.209Z\",\"source\":\"chrome\",\"type\":\"\\u798f\\u5229\",\"url\":\"http://7xi8d6.com1.z0.glb.clouddn.com/20171101141835_yQYTXc_enakorin_1_11_2017_14_16_45_351.jpeg\",\"used\":true,\"who\":\"daimajia\"},{\"_id\":\"59f7e677421aa90fe72535de\",\"createdAt\":\"2017-10-31T10:56:55.988Z\",\"desc\":\"10-31\",\"publishedAt\":\"2017-10-31T12:25:55.217Z\",\"source\":\"chrome\",\"type\":\"\\u798f\\u5229\",\"url\":\"http://7xi8d6.com1.z0.glb.clouddn.com/2017-10-31-nozomisasaki_official_31_10_2017_10_49_17_24.jpg\",\"used\":true,\"who\":\"\\u4ee3\\u7801\\u5bb6\"},{\"_id\":\"59f2aabb421aa90fef2034d5\",\"createdAt\":\"2017-10-27T11:40:43.793Z\",\"desc\":\"10-27\",\"publishedAt\":\"2017-10-27T12:02:30.376Z\",\"source\":\"chrome\",\"type\":\"\\u798f\\u5229\",\"url\":\"http://7xi8d6.com1.z0.glb.clouddn.com/20171027114026_v8VFwP_joanne_722_27_10_2017_11_40_17_370.jpeg\",\"used\":true,\"who\":\"daimajia\"},{\"_id\":\"59f0054a421aa90fe2f02bf4\",\"createdAt\":\"2017-10-25T11:30:18.697Z\",\"desc\":\"2017-10-25\",\"publishedAt\":\"2017-10-25T11:39:10.950Z\",\"source\":\"chrome\",\"type\":\"\\u798f\\u5229\",\"url\":\"http://7xi8d6.com1.z0.glb.clouddn.com/20171025112955_lmesMu_katyteiko_25_10_2017_11_29_43_270.jpeg\",\"used\":true,\"who\":\"\\u4ee3\\u7801\\u5bb6\"},{\"_id\":\"59ee8adf421aa90fe50c019b\",\"createdAt\":\"2017-10-24T08:35:43.61Z\",\"desc\":\"10-24\",\"publishedAt\":\"2017-10-24T11:50:49.1Z\",\"source\":\"chrome\",\"type\":\"\\u798f\\u5229\",\"url\":\"http://7xi8d6.com1.z0.glb.clouddn.com/20171024083526_Hq4gO6_bluenamchu_24_10_2017_8_34_28_246.jpeg\",\"used\":true,\"who\":\"\\u4ee3\\u7801\\u5bb6\"},{\"_id\":\"59ed70a4421aa90fef2034bc\",\"createdAt\":\"2017-10-23T12:31:32.639Z\",\"desc\":\"10-23\",\"publishedAt\":\"2017-10-23T12:44:23.660Z\",\"source\":\"chrome\",\"type\":\"\\u798f\\u5229\",\"url\":\"https://img.gank.io/anri.kumaki_23_10_2017_12_27_30_151.jpg\",\"used\":true,\"who\":\"\\u4ee3\\u7801\\u5bb6\"},{\"_id\":\"59e6aadf421aa90fef2034a0\",\"createdAt\":\"2017-10-18T09:14:07.966Z\",\"desc\":\"10-18\",\"publishedAt\":\"2017-10-20T10:26:24.673Z\",\"source\":\"chrome\",\"type\":\"\\u798f\\u5229\",\"url\":\"http://7xi8d6.com1.z0.glb.clouddn.com/20171018091347_Z81Beh_nini.nicky_18_10_2017_9_13_35_727.jpeg\",\"used\":true,\"who\":\"\\u4ee3\\u7801\\u5bb6\"},{\"_id\":\"59deaa0c421aa90fe7253583\",\"createdAt\":\"2017-10-12T07:32:28.644Z\",\"desc\":\"10-13\",\"publishedAt\":\"2017-10-17T13:10:43.731Z\",\"source\":\"chrome\",\"type\":\"\\u798f\\u5229\",\"url\":\"http://7xi8d6.com1.z0.glb.clouddn.com/20171012073213_p4H630_joycechu_syc_12_10_2017_7_32_7_433.jpeg\",\"used\":true,\"who\":\"daimajia\"},{\"_id\":\"59dea9cf421aa90fef203477\",\"createdAt\":\"2017-10-12T07:31:27.363Z\",\"desc\":\"10-12\",\"publishedAt\":\"2017-10-16T12:19:20.311Z\",\"source\":\"chrome\",\"type\":\"\\u798f\\u5229\",\"url\":\"http://7xi8d6.com1.z0.glb.clouddn.com/20171012073108_0y12KR_anri.kumaki_12_10_2017_7_30_58_141.jpeg\",\"used\":true,\"who\":\"daimajia\"}]}";
    private static final String RANDOMGIRL_JSON = "{\"error\":false,\"results\":[{\"_id\":\"56cc6d1d421aa95caa707762\",\"createdAt\":\"2015-08-13T01:38:11.889Z\",\"desc\":\"8.13\",\"publishedAt\":\"2015-08-13T03:58:48.460Z\",\"type\":\"\\u798f\\u5229\",\"url\":\"http://ww1.sinaimg.cn/large/7a8aed7bgw1ev0qk6r38pj20hs0qoq58.jpg\",\"used\":true,\"who\":\"\\u5f20\\u6db5\\u5b87\"}]}";

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private GankApi service;

    private MockWebServer mockWebServer;

    @Before
    public void createService() throws IOException {
        mockWebServer = new MockWebServer();
        service = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GankApi.class);
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void getTechList() throws Exception {
        enqueueResponse(TECHLIST_JSON);
        GankHttpResponse<List<GankItemBean>>
                data = DataTestUtil.getValue(service.getTechList("Android", 10, 1));

        RecordedRequest request = mockWebServer.takeRequest();
        String path = request.getPath();

        assertThat(URLDecoder.decode(path,"UTF-8"), is("/data/Android/10/1"));

        assertThat(data, notNullValue());
        assertEquals(data.getError(),false);
        assertEquals(data.getResults().get(0).get_id(), "59fa764b421aa90fe72535ea");
        assertEquals(data.getResults().get(0).getDesc(), "带你玩转Android Studio 3.0的性能分析工具");
        assertEquals(data.getResults().get(0).getType(), "Android");

    }

    @Test
    public void getGirlList() throws Exception {
        enqueueResponse(GIRLLIST_JSON);
        GankHttpResponse<List<GankItemBean>>
                data = DataTestUtil.getValue(service.getGirlList(10, 1));

        RecordedRequest request = mockWebServer.takeRequest();
        String path = request.getPath();
        assertThat(URLDecoder.decode(path,"UTF-8"),is("/data/福利/10/1"));

        assertThat(data,notNullValue());
        assertEquals(data.getError(),false);
        assertEquals(data.getResults().get(0).get_id(),"59fa7379421aa90fe50c01cc");
        assertEquals(data.getResults().get(0).getDesc(),"11-2");
        assertEquals(data.getResults().get(0).getType(),"福利");
    }

    @Test
    public void getRandomGirl() throws Exception {
        enqueueResponse(RANDOMGIRL_JSON);
        GankHttpResponse<List<GankItemBean>>
                data = DataTestUtil.getValue(service.getRandomGirl(1));

        RecordedRequest request = mockWebServer.takeRequest();
        String path = request.getPath();
        assertEquals(URLDecoder.decode(path,"UTF-8"),"/random/data/福利/1");

        assertThat(data,notNullValue());
        assertEquals(data.getError(),false);
        assertEquals(data.getResults().get(0).get_id(),"56cc6d1d421aa95caa707762");
        assertEquals(data.getResults().get(0).getDesc(),"8.13");
        assertEquals(data.getResults().get(0).getType(),DataTestUtil.unicodeToString("\\u798f\\u5229"));
    }


    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.<String, String>emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        //        File file = new File(fileName);
        //        InputStream inputStream = new FileInputStream(file);

        //        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        //        MockResponse mockResponse = new MockResponse();
        //        for (Map.Entry<String, String> header : headers.entrySet()) {
        //            mockResponse.addHeader(header.getKey(), header.getValue());
        //        }
        //        mockWebServer.enqueue(mockResponse
        //                .setBody(source.readString(StandardCharsets.UTF_8)));


        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mockWebServer.enqueue(mockResponse
                .setBody(fileName));
    }
}