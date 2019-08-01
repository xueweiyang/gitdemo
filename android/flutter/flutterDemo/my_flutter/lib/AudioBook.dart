import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:async';
import 'package:dio/dio.dart';
import 'HttpHelper.dart';
import 'dart:convert';
import 'User.dart';

class AudioBook extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return AudioBookState();
  }
}

class AudioBookState extends State<AudioBook> {
  static const eventChannel = MethodChannel("com.example.flutterDemo/test");
  static const streamChannel = EventChannel("com.example.flutterDemo/stream");
  String message = 'click me';

  StreamSubscription timerSubsc = null;

  void enableTimer() {
    if (timerSubsc == null) {
//      timerSubsc = streamChannel.receiveBroadcastStream().listen(updateTimer);
    }
  }

  void updateTimer(timer) {
    setState(() {
      message = timer.toString();
    });
  }

  void getData() async {
    var topic = HttpHelper().getAudioBookTopic();
  }

  @override
  Widget build(BuildContext context) {
    enableTimer();
    getData();
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        leading: Icon(
          Icons.arrow_back,
          color: Colors.black,
        ),
        backgroundColor: Colors.white,
        title: Text(
          "绘本",
          style: TextStyle(fontSize: 16, color: Colors.black),
        ),
      ),
      body: ListView(
        itemExtent: 60,
        children: List.generate(30, (index) {
          return Container(
            alignment: Alignment.center,
            decoration: BoxDecoration(border: Border.all(color: Colors.red)),
            child: Text('item${index}'),
          );
        }),
      ),
//      body: Container(
//        child: Column(
//          children: <Widget>[
//            Row(
//              children: <Widget>[
//                Column(
//                  children: <Widget>[
//                    Image.asset(
//                      'images/ic_homework.png',
//                      width: 40,
//                      height: 40,
//                      fit: BoxFit.fill,
//                    ),
//                    Text("配音榜"),
//                  ],
//                ),
//                Column(
//                  children: <Widget>[
//                    Image.network(
//                      'https://flutter.io/images/homepage/header-illustration.png',
//                      width: 40,
//                      height: 40,
//                    ),
//                    Text("配音圈"),
//                  ],
//                ),
//                Column(
//                  children: <Widget>[
//                    Image.network(
//                      'https://flutter.io/images/homepage/header-illustration.png',
//                      width: 40,
//                      height: 40,
//                    ),
//                    Text("我的绘本"),
//                  ],
//                ),
//                Column(
//                  children: <Widget>[
//                    Image.network(
//                      'https://flutter.io/images/homepage/header-illustration.png',
//                      width: 40,
//                      height: 40,
//                    ),
//                    Text("我的配音"),
//                  ],
//                ),
//              ],
//              mainAxisAlignment: MainAxisAlignment.spaceAround,
//              crossAxisAlignment: CrossAxisAlignment.center,
//            ),
//            ListView(
//              children: List.generate(30, (index) {
//                return Container(
//                  alignment: Alignment.center,
//                  decoration: BoxDecoration(
//                    border: Border.all(color: Colors.red)
//                  ),
//                  child: Text('item${index}'),
//                );
//              }),
//            )
//          ],
//        ),
//      ),
    );
  }

  Future<Null> doNativeStuff() async {
    String _message;
    try {
      String result = await eventChannel.invokeMethod('changeLife');
      _message = result;
      print(result);
    } on PlatformException catch (e) {
      _message = "Sadly i can not change your life:${e.message}";
    }
    setState(() {
      message = _message;
    });
  }
}
