import 'package:flutter/material.dart';

class MyErrorWidget extends StatelessWidget {

  String prompt;


  MyErrorWidget({this.prompt});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Image(
            image: AssetImage('assets/error.jpg'),
            height: 120.0,
            fit: BoxFit.cover,
          ),
          Text(
              prompt
          )
        ],
      ),
    );

  }
}
