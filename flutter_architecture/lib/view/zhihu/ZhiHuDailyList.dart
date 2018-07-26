import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_advanced_networkimage/flutter_advanced_networkimage.dart';
import 'package:flutter_architecture/bean/DailyListBean.dart';
import 'package:flutter_architecture/util/HttpUtil.dart';
import 'package:banner/banner.dart';
import 'package:dio/dio.dart';
import 'package:flutter_architecture/widget/MyErrorWidget.dart';

class DailyItem extends StatelessWidget {
  StoriesBean item;

  DailyItem({this.item}) : super(key: ObjectKey(item.id));

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(top: 2.0),
      color: Colors.grey,
      height: 100.0,
      child: Container(
        color: Colors.white,
        child: Row(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.all(5.0),
              child: Image(
                image: AdvancedNetworkImage(
                    item?.images != null ? item.images[0] : '',
                    useDiskCache: true),
                fit: BoxFit.cover,
              ),
            ),
            Expanded(
              flex: 1,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Padding(
                    child: Text(
                      item.title,
                      overflow: TextOverflow.ellipsis,
                      maxLines: 3,
                    ),
                    padding: EdgeInsets.all(5.0),
                  ),
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}

class ZhiHuDailyList extends StatefulWidget {
  @override
  _ZhiHuDailyListState createState() => _ZhiHuDailyListState();
}

class _ZhiHuDailyListState extends State<ZhiHuDailyList> {
  List<StoriesBean> stories = List();
  List<TopStoriesBean> top_stories = List();

  bool _isLoading = true;
  bool _httpError = false;

  Future<DailyListBean> fetchGet() async {
    Response response = await getZhiHuDio().get('news/latest');
    if (response.statusCode == 200) {
      DailyListBean dailyListBean = DailyListBean.fromJson(response.data);
      return dailyListBean;
    } else {
      throw Exception('Failed to load post');
    }
  }

  Widget _getBody(List<Widget> widgets) {
    return CustomScrollView(
      slivers: <Widget>[
        SliverToBoxAdapter(
          child: BannerView(
            data: top_stories,
            height: 150.0,
            buildShowView: (index, item) {
              return Stack(
                children: <Widget>[
                  Image(
                    image: AdvancedNetworkImage(
                        item.image != null ? item.image : '',
                        useDiskCache: true),
                    fit: BoxFit.cover,
                    width: double.infinity,
                  ),
                  Positioned(
                    child: Container(
                      color: Color.fromARGB(50, 0, 0, 0),
                      height: 30.0,
                    ),
                    bottom: 0.0,
                    right: 0.0,
                    left: 0.0,
                  ),
                  Positioned(
                    child: Text(
                      item.title,
                      style: TextStyle(color: Colors.white),
                      overflow: TextOverflow.ellipsis,
                    ),
                    left: 10.0,
                    right: 10.0,
                    bottom: 6.0,
                  )
                ],
              );
            },
          ),
        ),
        SliverSafeArea(
          top: false,
          sliver: SliverList(delegate: SliverChildListDelegate(widgets)),
        )
      ],
    );
  }

  @override
  void initState() {
    super.initState();
    fetchGet().then((dailyListBean) {
      setState(() {
        stories = dailyListBean.stories;
        top_stories = dailyListBean.top_stories;
        _isLoading = false;
      });
    }, onError: (e) {
      _httpError = true;
      _isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> widgets = List();
    int i = 0;
    while (i < stories.length) {
      widgets.add(DailyItem(item: stories[i]));
      i++;
    }

    Widget body;
    if (_isLoading) {
      body = Center(child: CircularProgressIndicator());
    } else if (_httpError) {
      body = MyErrorWidget(prompt: 'Failed to load post');
    } else {
      body = _getBody(widgets);
    }

    return Scaffold(
      key: GlobalKey(debugLabel: 'zhihu daily'),
      appBar: AppBar(
        title: Text('日报'),
      ),
      body: body,
    );
  }
}
