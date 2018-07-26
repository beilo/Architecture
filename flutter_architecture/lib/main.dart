import 'package:flutter/material.dart';
import 'package:flutter_architecture/widget/BottomTab.dart';

void main() => runApp(new MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Architecture",
      theme: ThemeData(primaryColor: Colors.black),
      home: BottomTab(),
    );
  }
}
