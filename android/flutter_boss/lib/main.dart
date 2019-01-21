import 'package:flutter/material.dart';
import 'package:flutter_boss/JobTab.dart';
import 'package:flutter_boss/component/iconTab.dart';

const double _kTabTextSize = 8.0;
const int INDEX_JOB = 0;
const int INDEX_COMPANY = 1;
const int INDEX_MESSAGE = 2;
const int INDEX_MINE = 3;
Color _kPrimaryColor = new Color.fromARGB(255, 0, 215, 198);

void main() => runApp(new MaterialApp(
      title: 'boss',
      home: new MyApp(),
    ));

class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new HomeState();
  }
}

class HomeState extends State<MyApp> with SingleTickerProviderStateMixin {
  int _currentIndex = 0;
  TabController _controller;
  VoidCallback onChanged;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _controller =
        new TabController(initialIndex: _currentIndex, length: 3, vsync: this);
    onChanged = () {
      setState(() {
        _currentIndex = this._controller.index;
      });
    };
    _controller.addListener(onChanged);
  }

  @override
  void dispose() {
    // TODO: implement dispose
    _controller.removeListener(onChanged);
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build

    return new MaterialApp(
        home: new Scaffold(
      body: new TabBarView(controller: _controller, children: <Widget>[
        new JobsTab(),
        new Center(
          child: new Text('自行车啛啛喳喳错2'),
        ),
        new Center(
          child: new Text('自行车啛啛喳喳错3'),
        ),
      ]),
      bottomNavigationBar: new Material(
        child: new TabBar(controller: _controller, tabs: <IconTab>[
          new IconTab(
            icon: _currentIndex == INDEX_JOB
                ? "assets/images/ic_main_tab_company_pre.png"
                : "assets/images/ic_main_tab_company_nor.png",
            text: '职位',
            color:
                _currentIndex == INDEX_JOB ? _kPrimaryColor : Colors.grey[900],
          ),
          new IconTab(
              icon: _currentIndex == INDEX_COMPANY
                  ? "assets/images/ic_main_tab_contacts_pre.png"
                  : "assets/images/ic_main_tab_contacts_nor.png",
              text: "公司",
              color: _currentIndex == INDEX_COMPANY
                  ? _kPrimaryColor
                  : Colors.grey[900]),
          new IconTab(
              icon: _currentIndex == INDEX_MESSAGE
                  ? "assets/images/ic_main_tab_find_pre.png"
                  : "assets/images/ic_main_tab_find_nor.png",
              text: "消息",
              color: _currentIndex == INDEX_MESSAGE
                  ? _kPrimaryColor
                  : Colors.grey[900]),
        ]),
      ),
    ));

//    return
//      new Scaffold(
//      body: new TabBarView(
//        children: <Widget>[
//          new Center(child: new Text('自行车自行车自行车自行车自行车自行车')),
//          new JobsTab(),
//          new JobsTab(),
//          new JobsTab(),
//        ],
//        controller: _controller,
//      ),
//      bottomNavigationBar: new Material(
//        child: new TabBar(controller: _controller, tabs: <IconTab>[
//          new IconTab(
//            icon: _currentIndex == INDEX_JOB
//                ? "assets/images/ic_main_tab_company_pre.png"
//                : "assets/images/ic_main_tab_company_nor.png",
//            text: '职位',
//            color:
//                _currentIndex == INDEX_JOB ? _kPrimaryColor : Colors.grey[900],
//          ),
//          new IconTab(
//              icon: _currentIndex == INDEX_COMPANY
//                  ? "assets/images/ic_main_tab_contacts_pre.png"
//                  : "assets/images/ic_main_tab_contacts_nor.png",
//              text: "公司",
//              color: _currentIndex == INDEX_COMPANY
//                  ? _kPrimaryColor
//                  : Colors.grey[900]),
//          new IconTab(
//              icon: _currentIndex == INDEX_MESSAGE
//                  ? "assets/images/ic_main_tab_find_pre.png"
//                  : "assets/images/ic_main_tab_find_nor.png",
//              text: "消息",
//              color: _currentIndex == INDEX_MESSAGE
//                  ? _kPrimaryColor
//                  : Colors.grey[900]),
//          new IconTab(
//              icon: _currentIndex == INDEX_MINE
//                  ? "assets/images/ic_main_tab_my_pre.png"
//                  : "assets/images/ic_main_tab_my_nor.png",
//              text: "我的",
//              color: (_currentIndex == INDEX_MINE)
//                  ? _kPrimaryColor
//                  : Colors.grey[900])
//        ]),
//      ),
//    );
  }
}
