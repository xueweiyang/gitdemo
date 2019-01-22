import 'package:flutter/material.dart';

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
//    return new Image(image: new AssetImage("assets/images/img1.png"),);
    return Image.network(
      'https://data.dadaabc.com/parent_ads/20181130/41/d1/60/70/41d1607005c64d818f524efa0510e893.jpg'
    );
  }
}

void main() {
  runApp(new MyApp());
}
