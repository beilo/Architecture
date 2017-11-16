package com.minister.architecture.model.http;

import com.minister.architecture.model.bean.DailyListBean;
import com.minister.architecture.model.bean.HotListBean;
import com.minister.architecture.util.DataTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leipe on 2017/11/8.
 * @author leipe
 */
@RunWith(JUnit4.class)
public class ZhihuApisTest {

    private static final String HOTLIST_JSON = "{\"recent\":[{\"news_id\":9655658,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655658\",\"thumbnail\":\"https:\\/\\/pic4.zhimg.com\\/v2-42e60fd65b1ef82b5fdeeb47de9de387.jpg\",\"title\":\"- iPhone X 会「烧屏」吗？\\r\\n- 会\"},{\"news_id\":9654597,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9654597\",\"thumbnail\":\"https:\\/\\/pic2.zhimg.com\\/v2-3e9040e48c27a7b9e1001f5fc46798a5.jpg\",\"title\":\"不浪费一丝一毫，下脚料也能做得有滋有味\"},{\"news_id\":9655400,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655400\",\"thumbnail\":\"https:\\/\\/pic2.zhimg.com\\/v2-d508070650bd4060d41ddbf19338f361.jpg\",\"title\":\"20℃ 不能说是 10℃ 的两倍，上学时总是做错这道题\"},{\"news_id\":9655324,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655324\",\"thumbnail\":\"https:\\/\\/pic2.zhimg.com\\/v2-dae78d8a1b121e220177bcea13fac6a1.jpg\",\"title\":\"晚上睡不着、早上睡不够，先甩锅给枕边人，没有的话再甩给自己\"},{\"news_id\":9654623,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9654623\",\"thumbnail\":\"https:\\/\\/pic1.zhimg.com\\/v2-3d7be1b5ee45ebb91be0faf65aa14884.jpg\",\"title\":\"瞎扯 · 如何正确地吐槽\"},{\"news_id\":9655651,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655651\",\"thumbnail\":\"https:\\/\\/pic4.zhimg.com\\/v2-4082ed37fb7ef5ea0f566c216206be37.jpg\",\"title\":\"国内高校推行「非升即走」，当上大学教师还是没有安全感\"},{\"news_id\":9655575,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655575\",\"thumbnail\":\"https:\\/\\/pic3.zhimg.com\\/v2-31e7d37db8abceccb075a6d7515a6572.jpg\",\"title\":\"这些汽车上的小配置，几年前还没普及，现在竟然离不开了\"},{\"news_id\":9655435,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655435\",\"thumbnail\":\"https:\\/\\/pic4.zhimg.com\\/v2-e372106883041e0496ce1f48ae64d837.jpg\",\"title\":\"瞎扯 · 如何正确地吐槽\"},{\"news_id\":9655793,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655793\",\"thumbnail\":\"https:\\/\\/pic3.zhimg.com\\/v2-a47c8eef4bdc87d862eb8682cbbfaa36.jpg\",\"title\":\"业内人士怎么评价《英雄联盟》的原画水平？\"},{\"news_id\":9655509,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655509\",\"thumbnail\":\"https:\\/\\/pic1.zhimg.com\\/v2-fc835201edd6217a8b58c0e6b7100a04.jpg\",\"title\":\"一首曲子为什么「听着舒服」，其实不是光靠「感觉」的\"},{\"news_id\":9655445,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655445\",\"thumbnail\":\"https:\\/\\/pic1.zhimg.com\\/v2-6ca7823ca6c7acc8ca5481258e5f3628.jpg\",\"title\":\"教你个简单方法，以后到了火星这么刹车最「省油」\"},{\"news_id\":9655390,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655390\",\"thumbnail\":\"https:\\/\\/pic2.zhimg.com\\/v2-70d2d437faf4e0e90f14c5fb7732acb9.jpg\",\"title\":\"美国人在日常生活中有多浪费？\"},{\"news_id\":9655478,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655478\",\"thumbnail\":\"https:\\/\\/pic3.zhimg.com\\/v2-1d54f20fd974d7f864c5cfbbc2f4b6ae.jpg\",\"title\":\"电梯内吸烟，老人被劝后猝死，医生遭索赔 40 万冤吗？\"},{\"news_id\":9654850,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9654850\",\"thumbnail\":\"https:\\/\\/pic4.zhimg.com\\/v2-ac1eea255935917b635b83689cb5d933.jpg\",\"title\":\"瞎扯 · 如何正确地吐槽\"},{\"news_id\":9655426,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655426\",\"thumbnail\":\"https:\\/\\/pic2.zhimg.com\\/v2-e0f002ca6fb775b5e0bbe5a118e5e581.jpg\",\"title\":\"《雷神 3：诸神黄昏》是一部塑料化电影，好看但不堪重用\"},{\"news_id\":9655470,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655470\",\"thumbnail\":\"https:\\/\\/pic3.zhimg.com\\/v2-e5c9b0cac6fba2efa9b46ef5d37718aa.jpg\",\"title\":\"- 电影很好看，只可惜动作戏太「水」\\r\\n- 唉，我是真不愿意……\"},{\"news_id\":9654625,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9654625\",\"thumbnail\":\"https:\\/\\/pic2.zhimg.com\\/v2-22f89580757464149916b01b252b464d.jpg\",\"title\":\"宜家也要做电商了，那么最让人关心的包邮问题来了……\"},{\"news_id\":9655795,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655795\",\"thumbnail\":\"https:\\/\\/pic1.zhimg.com\\/v2-bef925e3d585f7731f7d077b084d45ec.jpg\",\"title\":\"程序员的好日子什么时候才到头？\"},{\"news_id\":9655120,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655120\",\"thumbnail\":\"https:\\/\\/pic2.zhimg.com\\/v2-7bd8a79b936698db248ae61138768a41.jpg\",\"title\":\"以秋裤为生的网红「亿元村」\"},{\"news_id\":9655726,\"url\":\"http:\\/\\/news-at.zhihu.com\\/api\\/2\\/news\\/9655726\",\"thumbnail\":\"https:\\/\\/pic2.zhimg.com\\/v2-b7f681e5d498706748c9f46705e3e075.jpg\",\"title\":\"《魔兽世界》推出了官方怀旧服和新资料片，你会选哪个？\"}]}";
    private static final String DAILYLIST_JSON = "{\"date\":\"20171108\",\"stories\":[{\"images\":[\"https:\\/\\/pic2.zhimg.com\\/v2-fd0487130830fb68b47cc71dbf8e07cd.jpg\"],\"type\":0,\"id\":9655837,\"ga_prefix\":\"110813\",\"title\":\"有些心理问题，是你拿孩子的尊严，甚至性命去纠正的\"},{\"images\":[\"https:\\/\\/pic3.zhimg.com\\/v2-42e831ae8561a1bd3725568f8fb6f67e.jpg\"],\"type\":0,\"id\":9655131,\"ga_prefix\":\"110812\",\"title\":\"大误 · 就是这个味道，让人睁不开眼\"},{\"images\":[\"https:\\/\\/pic1.zhimg.com\\/v2-7930262c565e8314bf644035a65bdc08.jpg\"],\"type\":0,\"id\":9655915,\"ga_prefix\":\"110811\",\"title\":\"银行给租房提供贷款，直接瞄向月入过万的白领们\"},{\"images\":[\"https:\\/\\/pic2.zhimg.com\\/v2-2e16fa4c490b828d84c3bae49298b4e1.jpg\"],\"type\":0,\"id\":9655751,\"ga_prefix\":\"110810\",\"title\":\"很瘦的人要先吃胖，才能练出肌肉吗？\"},{\"images\":[\"https:\\/\\/pic3.zhimg.com\\/v2-2b74d74963ed68e103aa825ea905260e.jpg\"],\"type\":0,\"id\":9655564,\"ga_prefix\":\"110809\",\"title\":\"要不要借钱给你？银行看了看你的流水记录，陷入沉思\"},{\"images\":[\"https:\\/\\/pic2.zhimg.com\\/v2-af3e889b9de41bf45500ae9698ab3d61.jpg\"],\"type\":0,\"id\":9655601,\"ga_prefix\":\"110808\",\"title\":\"一种可怕的基因，要么让你不知痛为何物，要么痛不欲生\"},{\"images\":[\"https:\\/\\/pic4.zhimg.com\\/v2-6dc26d3c7d50e5cff2f86c11abbac0e7.jpg\"],\"type\":0,\"id\":9655585,\"ga_prefix\":\"110807\",\"title\":\"- 作为一个女孩子，如何成为赛车手？\\r\\n- 先开卡丁车，再开房车\"},{\"images\":[\"https:\\/\\/pic1.zhimg.com\\/v2-bef925e3d585f7731f7d077b084d45ec.jpg\"],\"type\":0,\"id\":9655795,\"ga_prefix\":\"110807\",\"title\":\"程序员的好日子什么时候才到头？\"},{\"images\":[\"https:\\/\\/pic1.zhimg.com\\/v2-d87347a1411d066a243f3491bfd486b4.jpg\"],\"type\":0,\"id\":9655266,\"ga_prefix\":\"110807\",\"title\":\"- 你猜我在想什么？\\r\\n- 稍等，这就看一下……\"},{\"images\":[\"https:\\/\\/pic4.zhimg.com\\/v2-ac1eea255935917b635b83689cb5d933.jpg\"],\"type\":0,\"id\":9654850,\"ga_prefix\":\"110806\",\"title\":\"瞎扯 · 如何正确地吐槽\"}],\"top_stories\":[{\"image\":\"https:\\/\\/pic2.zhimg.com\\/v2-85fd1954bde5c2991697a72dfda09a95.jpg\",\"type\":0,\"id\":9655837,\"ga_prefix\":\"110813\",\"title\":\"有些心理问题，是你拿孩子的尊严，甚至性命去纠正的\"},{\"image\":\"https:\\/\\/pic2.zhimg.com\\/v2-1b2d0dbace0b350284ce81721aa16a9d.jpg\",\"type\":0,\"id\":9655585,\"ga_prefix\":\"110807\",\"title\":\"- 作为一个女孩子，如何成为赛车手？\\r\\n- 先开卡丁车，再开房车\"},{\"image\":\"https:\\/\\/pic4.zhimg.com\\/v2-86f73ea4681cbc05b585b3e9c371dd3f.jpg\",\"type\":0,\"id\":9655795,\"ga_prefix\":\"110807\",\"title\":\"程序员的好日子什么时候才到头？\"},{\"image\":\"https:\\/\\/pic4.zhimg.com\\/v2-983763e70a8315ba6787f6e3aae0aa5b.jpg\",\"type\":0,\"id\":9655793,\"ga_prefix\":\"110719\",\"title\":\"业内人士怎么评价《英雄联盟》的原画水平？\"},{\"image\":\"https:\\/\\/pic2.zhimg.com\\/v2-dbff2012900ab47ea82c01f4ab4cecd1.jpg\",\"type\":0,\"id\":9655803,\"ga_prefix\":\"110717\",\"title\":\"索尔为什么换发型？浩克又为何缺席内战？让我们从头说起\"}]}";



    private ZhihuApis service;
    private MockWebServer mockWebServer;
    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        service = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ZhihuApis.class);
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void getHotList() throws Exception {
        enqueueResponse(HOTLIST_JSON);
        HotListBean data
                = DataTestUtil.getValue(service.getHotList());

        RecordedRequest request = mockWebServer.takeRequest();
        String path = request.getPath();
        Assert.assertEquals(path,"/news/hot");

        Assert.assertEquals(data.getRecent().get(0).getNews_id(),9655658);
        Assert.assertEquals(data.getRecent().get(0).getUrl(),"http://news-at.zhihu.com/api/2/news/9655658");

    }

    @Test
    public void getDetailInfo() throws Exception {
        // 涉及到里面全是html代码,我也不知道怎么验证,就这样吧
    }

    @Test
    public void getDetailExtraInfo() throws Exception {
    }

    @Test
    public void getDailyList() throws Exception {
        enqueueResponse(DAILYLIST_JSON);

        DailyListBean
                data = DataTestUtil.getValue(service.getDailyList());

        RecordedRequest request = mockWebServer.takeRequest();
        String path = request.getPath();
        Assert.assertEquals(path,"/news/latest");

        Assert.assertEquals(data.getDate(),"20171108");
    }

    @Test
    public void getWelcomeInfo() throws Exception {
    }


    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.<String, String>emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mockWebServer.enqueue(mockResponse
                .setBody(fileName));
    }
}