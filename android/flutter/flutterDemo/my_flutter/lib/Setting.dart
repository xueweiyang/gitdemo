import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:async';

class Setting extends StatefulWidget {



  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return SettingState();
  }

}

class SettingState extends State<Setting> {

  static const eventChannel = MethodChannel("com.example.flutterDemo/test");
  static const streamChannel = EventChannel("com.example.flutterDemo/stream");
  String message = 'click me';

  StreamSubscription timerSubsc = null;

  void enableTimer() {
    if (timerSubsc == null) {
      timerSubsc = streamChannel.receiveBroadcastStream().listen(updateTimer);
    }
  }

  void updateTimer(timer) {
    setState(() {
      message = timer.toString();
    });
  }

  @override
  Widget build(BuildContext context) {
    enableTimer();
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        leading: Icon(
          Icons.arrow_back,
          color: Colors.black,
        ),
        backgroundColor: Colors.white,
        title: Text(
          "设置",
          style: TextStyle(fontSize: 16,color: Colors.black),
        ),
      ),
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