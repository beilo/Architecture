import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_architecture/bean/LatestJokeBean.dart';
import 'package:flutter_architecture/util/HttpUtil.dart';
import 'package:flutter_architecture/widget/MyErrorWidget.dart';

class JokeItem extends StatelessWidget {
  LatestJokeItem item;

  JokeItem({this.item}) : super(key: ObjectKey(item.hashId));

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.grey,
      padding: EdgeInsets.only(top: 2.0),
      child: Container(
        color: Colors.white,
        padding:
            EdgeInsets.only(top: 10.0, left: 10.0, right: 10.0, bottom: 5.0),
        child: Column(
          children: <Widget>[
            Align(
              child: Text(item.content),
              alignment: Alignment.topLeft,
            ),
            Align(
              heightFactor: 2.0,
              child: Text(item.updatetime),
              alignment: Alignment.bottomRight,
            )
          ],
        ),
      ),
    );
  }
}

class JokeList extends StatefulWidget {
  @override
  _JokeListState createState() => _JokeListState();
}

class _JokeListState extends State<JokeList> {
  LatestJokeBean _latestJoke;
  bool _httpError = false;
  bool _isLoading = true;

  Future<LatestJokeBean> _fetchGet() async {
    var params = {
      'page': 1,
      'pagesize': 20,
      'key': 'fc00c21487c00893b75747a5565c595d'
    };
    Response response =
        await getJokeDio().get('joke/content/text.php', data: params);
    var latestJoke = LatestJokeBean(response.data);

    if (latestJoke.error_code == 0) {
      return latestJoke;
    } else {
      throw Exception(latestJoke.reason);
    }
  }

  @override
  void initState() {
    super.initState();
    _fetchGet().then((lastJoke) {
      setState(() {
        _latestJoke = lastJoke;
        _isLoading = false;
      });
    }, onError: (e) {
      setState(() {
        _httpError = true;
        _isLoading = false;
      });
    });
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
  }

  @override
  Widget build(BuildContext context) {
    Map<dynamic, Widget> map = Map();
    map[_isLoading] = Center(child: CircularProgressIndicator());
    map[_httpError] = MyErrorWidget(prompt: _latestJoke?.reason ?? '',);
    map[_latestJoke?.error_code == 0] = _listWidget();

    Widget _statusWidget = Container();
    for (var value in map.keys.toList()) {
      if (value == true) {
        _statusWidget = map[value];
        break;
      }
    }
    return _statusWidget;
  }

  Widget _listWidget() {
    List<Widget> widgets = List();
    List<LatestJokeItem> list = _latestJoke?.result?.data ?? List();
    for (int i = 0; i < list.length; i++) {
      widgets.add(JokeItem(item: list[i]));
    }
    return ListView(
      children: widgets,
    );
  }
}
