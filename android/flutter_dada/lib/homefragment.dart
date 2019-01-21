import 'package:flutter/material.dart';

class HomeFragment extends StatefulWidget {
  
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new Home();
  }
  
}

class Home extends State<HomeFragment> {
  
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      body: new Text(data),
    );
  }
  
}
