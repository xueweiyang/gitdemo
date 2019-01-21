import 'package:flutter/material.dart';
import 'package:flutter_dada/iconTab.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      body: new TabBarView(children: <Widget>[

      ]),
      bottomNavigationBar: new Material(
        child: new TabBar(tabs: <IconTab>[
          new IconTab(icon: "assets/images/ic_main_tab_company_pre.png",
          text: '职位',
          color: Colors.grey[900],)
        ]),
      ),
    );
  }
}
