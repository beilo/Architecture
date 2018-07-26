import 'package:flutter/material.dart';
import 'package:flutter_architecture/view/Joke/JokeList.dart';
import 'package:flutter_architecture/view/JournalismList.dart';

class JokeAndJournalismTab extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 2,
      child: Scaffold(
        appBar: AppBar(
          title: Text('随意'),
          actions: <Widget>[
            IconButton(
                icon: Icon(Icons.sentiment_very_satisfied), onPressed: null),
          ],
          bottom: TabBar(
            isScrollable: false,
            indicator: UnderlineTabIndicator(),
            tabs: <Widget>[
              Tab(
                text: '11',
              ),
              Tab(
                text: '22',
              )
            ],
          ),
        ),
        body: TabBarView(
          children: <Widget>[
            SafeArea(
              top: false,
              bottom: false,
              child: JokeList(),
            ),
            SafeArea(
              top: false,
              bottom: false,
              child: JournalismList(),
            )
          ],
        ),
      ),
    );
  }
}
