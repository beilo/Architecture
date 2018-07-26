import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_advanced_networkimage/flutter_advanced_networkimage.dart';
import 'package:flutter_architecture/bean/JournalismBean.dart';
import 'package:flutter_architecture/util/HttpUtil.dart';
import 'package:flutter_architecture/widget/MyErrorWidget.dart';

class JournalismItemWidget extends StatelessWidget {
  JournalismItem item;

  JournalismItemWidget({this.item}) : super(key: ObjectKey(item.title));

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(top: 2.0),
      height: 100.0,
      color: Colors.grey,
      child: Container(
        padding: EdgeInsets.all(2.0),
        color: Colors.white,
        child: Row(
          children: <Widget>[
            Image(
              height: 80.0,
              width: 80.0,
              fit: BoxFit.cover,
              image: AdvancedNetworkImage(item.pic),
            ),
            Expanded(
                child: Padding(
              padding: EdgeInsets.only(left: 5.0, top: 2.0, bottom: 3.5),
              child: Column(
                children: <Widget>[
                  Align(
                    child: Text(item.title),
                    alignment: Alignment.topLeft,
                  ),
                  Expanded(
                    child: Row(
                      children: <Widget>[
                        Align(
                          child: Text(item.src),
                          alignment: Alignment.bottomLeft,
                        ),
                        Expanded(
                          child: Align(
                            child: Text(item.time),
                            alignment: Alignment.bottomRight,
                          ),
                        )
                      ],
                    ),
                  )
                ],
              ),
            ))
          ],
        ),
      ),
    );
  }
}

class JournalismList extends StatefulWidget {
  @override
  _JournalismListState createState() => _JournalismListState();
}

class _JournalismListState extends State<JournalismList> {
  JournalismBean _journalismBean;
  bool _isLoading = true;
  bool _httpError = false;

  Future<JournalismBean> _fetchGet() async {
    var params = {
      'apiKey': 'GZdx6516fc9e1b0f5871765d6d7c3cc93e1dc3762b946f3',
      'channel': '头条',
      'num': 40,
      'start': 0,
    };
    Response response =
        await getJournalismDio().get('common/news/getNews', data: params);
    JournalismBean journalismBean = JournalismBean(response.data);
    if (journalismBean.statusCode == '000000') {
      return journalismBean;
    } else {
      throw Exception(journalismBean.desc);
    }
  }

  @override
  void initState() {
    super.initState();

    _fetchGet().then((journalismBean) {
      checkSetState(() {
        _journalismBean = journalismBean;
        _isLoading = false;
      });
    }, onError: (e) {
      _httpError = true;
      _isLoading = false;
    });
  }

  void checkSetState(VoidCallback callback) {
    if (this.mounted) {
      setState(callback);
    }
  }

  @override
  Widget build(BuildContext context) {
    if (_isLoading) {
      return Center(child: CircularProgressIndicator());
    } else if (_httpError) {
      return MyErrorWidget(prompt: _journalismBean?.result?.list ?? '');
    } else {
      List<Widget> listWidget = List();
      if (_journalismBean != null) {
        for (int i = 0; i < _journalismBean.result.list.length; i++) {
          listWidget
              .add(JournalismItemWidget(item: _journalismBean.result.list[i]));
        }
      }
      return ListView(
        children: listWidget,
      );
    }
  }
}
