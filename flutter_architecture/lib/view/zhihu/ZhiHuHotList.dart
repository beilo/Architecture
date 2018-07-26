import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_architecture/bean/HotBean.dart';
import 'package:flutter_architecture/util/HttpUtil.dart';
import 'package:flutter_advanced_networkimage/flutter_advanced_networkimage.dart';
import 'package:flutter_architecture/widget/MyErrorWidget.dart';

class HotItem extends StatelessWidget {
  final RecentBean item;

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
                image: AdvancedNetworkImage(item.thumbnail, useDiskCache: true),
                height: 80.0,
                width: 80.0,
                fit: BoxFit.cover,
              ),
            ),
            Expanded(
              flex: 1,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Container(
                    padding:
                        new EdgeInsets.only(top: 5.0, left: 5.0, right: 13.0),
                    child: new Text(
                      item.title,
                      overflow: TextOverflow.ellipsis,
                      maxLines: 2,
                    ),
                  ),
                ],
              ),
            )
          ],
        ),
      ),
    );
  }

  HotItem({this.item}) : super(key: ObjectKey(item.news_id));
}

class ZhiHuHotList extends StatefulWidget {
  @override
  _HotListState createState() => _HotListState();
}

class _HotListState extends State<ZhiHuHotList> {
  HotBean hot = new HotBean();

  bool _isLoading = true;
  bool _httpError = false;

  Future<HotBean> fetchPost() async {
    Response response = await getZhiHuDio().get('news/hot');
    if (response.statusCode == 200) {
      HotBean hotBean = HotBean.fromJson(response.data);
      return hotBean;
    } else {
      throw Exception('Failed to load post');
    }
  }

  @override
  void initState() {
    super.initState();
    fetchPost().then((hotBean) {
      setState(() {
        this.hot = hotBean;
        _isLoading = false;
      });
    }, onError: (e) {
      _httpError = true;
      _isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    Widget body;
    if (_isLoading) {
      body = Center(child: CircularProgressIndicator());
    } else if (_httpError) {
      body = MyErrorWidget(prompt: 'Failed to load post');
    } else {
      body = ListView.builder(
        itemBuilder: (context, index) {
          return HotItem(
            item: hot.recent[index],
          );
        },
        itemCount: hot?.recent?.length ?? 0,
      );
    }

    return Scaffold(
      key: GlobalKey(debugLabel: 'zhihu hot list'),
      appBar: AppBar(title: Text('热门')),
      body: body,
    );
  }
}
