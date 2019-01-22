import 'package:flutter/material.dart';
import 'package:flutter_dada/homefragment.dart';
import 'package:flutter_dada/iconTab.dart';
import 'package:flutter_dada/reversefragment.dart';

void main() => runApp(new MaterialApp(
      title: 'dada',
      home: new MyApp(),
    ));

class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new MainState();
  }
}

class MainState extends State<MyApp> with SingleTickerProviderStateMixin {
  TabController _controller;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _controller = new TabController(length: 2, vsync: this);
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      body: new TabBarView(
          controller: _controller,
          children: <Widget>[new HomeFragment(), new ReverseFragment()]),
      bottomNavigationBar: new Material(
        child: new TabBar(controller: _controller, tabs: <IconTab>[
          new IconTab(
            icon: "assets/images/ic_main_tab_company_pre.png",
            text: '首页',
            color: Colors.grey[900],
          ),
          new IconTab(
            icon: "assets/images/ic_main_tab_find_pre.png",
            text: '约课',
            color: Colors.grey[900],
          ),
        ]),
      ),
    );
  }
}
