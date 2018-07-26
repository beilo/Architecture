import 'package:flutter/material.dart';
import 'package:flutter_architecture/view/zhihu/ZhiHuDailyList.dart';
import 'package:flutter_architecture/view/zhihu/ZhiHuHotList.dart';

final List<Widget> _page = [ZhiHuHotList(), ZhiHuDailyList()];
final List<String> _title = ['热门', '日报'];

class ZhiHuTab extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: _page.length,
      child: NestedScrollView(
          headerSliverBuilder: (BuildContext context, bool innerBoxIsScrolled) {
            return <Widget>[
//              TabBar(
//                tabs: _title.map((String title) {
//                  Tab(text: title);
//                }).toList(),
//              ),









              new SliverOverlapAbsorber(
                handle: NestedScrollView.sliverOverlapAbsorberHandleFor(context),
                child: TabBar(
                  tabs: _title.map(
                        (String title) => new Tab(text: title),
                  ).toList(),
                ),
              ),


            ];
          },
          body: TabBarView(
            children: _page,
          )),
//      child: TabBarView(
//        children: _page,
//      ),
    );
  }
}
