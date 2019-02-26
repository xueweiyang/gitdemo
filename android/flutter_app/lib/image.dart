import 'package:flutter/material.dart';

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
//    return new Image(image: new AssetImage("assets/images/img1.png"),);
    return new Material(child:new Image(
      image: new NetworkImage("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3882265467,3924971696&fm=27&gp=0.jpg"),
      width: 20.0,
      height: 20.0,
      fit: BoxFit.cover,
    ),
    borderRadius: BorderRadius.circular(80),);
  }
}

void main() {
  runApp(new MyApp());
}
