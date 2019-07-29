import 'package:flutter/material.dart';

class Setting extends StatefulWidget {

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return SettingState();
  }

}

class SettingState extends State<Setting> {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Container(
      child: Text('设置页面哦'),
      color: Colors.yellow,
    );
  }
}