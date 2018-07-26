import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_advanced_networkimage/flutter_advanced_networkimage.dart';
import 'package:flutter_architecture/util/HttpUtil.dart';
import 'package:flutter_architecture/bean/GankBean.dart';

class TechItem extends StatelessWidget {
  final GankItem gank;

  TechItem({this.gank}) : super(key: ObjectKey(gank.id));

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.grey,
      padding: EdgeInsets.only(top: 2.0),
      height: 100.0,
      width: double.infinity,
      child: Container(
        padding: EdgeInsets.all(2.0),
        color: Colors.white,
        child: Row(
          children: <Widget>[
            Container(
              width: 80.0,
              height: 80.0,
              child: Icon(
                Icons.widgets,
              ),
            ),
            Expanded(
              child: Column(
                children: <Widget>[
                  Align(
                    child: Text(gank.desc),
                    alignment:Alignment.topLeft,
                  ),
                  Expanded(
                    child: Row(
                      children: <Widget>[
                        Align(
                          child: Text(gank.who),
                          alignment: Alignment.bottomLeft,
                        ),
                        Expanded(
                          child: Align(
                            child: Text(gank.createdAt),
                            alignment: Alignment.bottomRight,
                          ),
                        )
                      ],
                    ),
                  )
                ],
              ),
            )
          ],
        ),
      ),
    );
  }
}

class TechList extends StatefulWidget {
  @override
  _TechListState createState() => _TechListState();
}

class _TechListState extends State<TechList> {
  final List<GankItem> datas = List();

  Future<GankBean> fetchGet() async {
    String url = 'data/Android/10/1';
    Response response = await getGankDio().get(url);
    GankBean gankBean = GankBean(response.data);
    if (gankBean?.error == true) {
      throw Exception('http error ${gankBean.toString()}');
    } else {
      return gankBean;
    }
  }

  @override
  void initState() {
    super.initState();
    fetchGet().then((GankBean gank) {
      setState(() {
        datas.addAll(gank.results);
      });
    }, onError: (e) {
      print(e.toString());
    });
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> widgets = List();

    for (int i = 0; i < datas.length; i++) {
      widgets.add(TechItem(
        gank: datas[i],
      ));
    }
    return CustomScrollView(
      slivers: <Widget>[
//        SliverToBoxAdapter(
//          child: Image(
//            image: AdvancedNetworkImage(
//                datas.length > 0
//                    ? datas[0]?.url ?? Icons.error
//                    : Icons.error),
//            fit: BoxFit.cover,
//          ),
//        ),
        SliverSafeArea(
//          top: false,
          sliver: SliverList(delegate: SliverChildListDelegate(widgets)),
        )
      ],
    );
  }
}
