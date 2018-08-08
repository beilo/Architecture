import 'package:flutter/material.dart';
import 'package:flutter_architecture/view/Joke/JokeList.dart';
import 'package:flutter_architecture/view/JokeAndJournalismTab.dart';
import 'package:flutter_architecture/view/JournalismList.dart';
import 'package:flutter_architecture/view/gank/TechList.dart';
import 'package:flutter_architecture/view/zhihu/ZhiHuDailyList.dart';
import 'package:flutter_architecture/view/zhihu/ZhiHuHotList.dart';
import 'package:flutter_architecture/view/ZhiHuTab.dart';

class BottomTab extends StatefulWidget {
  @override
  _BottomTabState createState() => _BottomTabState();
}

class _BottomTabState extends State<BottomTab> {
  int _sIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: IndexedStack(
        children: <Widget>[
          Center(child: ZhiHuHotList()),
          Center(child: ZhiHuDailyList()),
          Center(child: JokeAndJournalismTab()),
          Center(child: JokeList()),
        ],
        index: _sIndex,
      ),
      bottomNavigationBar: BottomWidget(onTap: (int index) {
        setState(() {
          _sIndex = index;
        });
      }),
    );
  }

}

typedef void VoidItemClick(int value);

class BottomWidget extends StatefulWidget {
  final VoidItemClick onTap;

  BottomWidget({
    Key key,
    this.onTap,
  }) : super(key: key);

  @override
  _BottomWidgetState createState() => _BottomWidgetState();
}

class _BottomWidgetState extends State<BottomWidget> {
  int currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.grey[200],
      padding: EdgeInsets.only(top: 0.5),
      child: Container(
        color: Colors.white,
        child: Row(
          children: <Widget>[
            BottomWidgetTab(
              unSelectedIcon: Icon(Icons.chat, color: Colors.grey),
              selectedIcon: Icon(Icons.chat, color: Colors.red),
              title: '微信',
              index: 0,
              currentIndex: currentIndex,
              onClick: (int index) {
                setState(() {
                  currentIndex = index;
                });
                widget.onTap(index);
              },
            ),
            BottomWidgetTab(
              unSelectedIcon:
                  Icon(Icons.perm_contact_calendar, color: Colors.grey),
              selectedIcon:
                  Icon(Icons.perm_contact_calendar, color: Colors.red),
              title: '通讯录',
              index: 1,
              currentIndex: currentIndex,
              onClick: (int index) {
                setState(() {
                  currentIndex = index;
                });
                widget.onTap(index);
              },
            ),
            BottomWidgetTab(
              unSelectedIcon: Icon(Icons.room, color: Colors.grey),
              selectedIcon: Icon(Icons.room, color: Colors.red),
              title: '发现',
              index: 2,
              currentIndex: currentIndex,
              onClick: (int index) {
                setState(() {
                  currentIndex = index;
                });
                widget.onTap(index);
              },
            ),
            BottomWidgetTab(
              unSelectedIcon: Icon(Icons.perm_identity, color: Colors.grey),
              selectedIcon: Icon(Icons.perm_identity, color: Colors.red),
              title: '我',
              index: 3,
              currentIndex: currentIndex,
              onClick: (int index) {
                setState(() {
                  currentIndex = index;
                });
                widget.onTap(index);
              },
            )
          ],
        ),
      ),
    );
  }
}

class BottomWidgetTab extends StatelessWidget {
  final Widget unSelectedIcon;
  final Widget selectedIcon;
  final String title;
  final int currentIndex;
  final VoidItemClick onClick;
  final int index;

  BottomWidgetTab(
      {Key key,
      this.unSelectedIcon,
      this.selectedIcon,
      this.title,
      this.currentIndex,
      this.onClick,
      this.index})
      : assert(unSelectedIcon != null),
        assert(selectedIcon != null),
        assert(title != null),
        assert(index != null),
        super(key: key);

  @override
  Widget build(BuildContext context) {
    bool _isSelected = currentIndex == index;

    // Expanded 必须是顶级布局,因为flex要包起来
    return Expanded(
      child: GestureDetector(
        onTap: () {
          if (!_isSelected) {
            onClick(index);
          }
        },
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Container(
                height: 35.0,
                child: IconButton(
                  icon: _isSelected ? selectedIcon : unSelectedIcon,
                  onPressed: null,
                )),
            Container(
                margin: EdgeInsets.only(bottom: 5.0),
                child: Text(
                  title,
                  style: TextStyle(color: Colors.black54),
                ))
          ],
        ),
      ),
      flex: 1,
    );
  }
}
