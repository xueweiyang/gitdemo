import 'package:flutter/material.dart';
import 'package:flutter_dada/component/banner.dart';

class HomeFragment extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new Home();
  }
}

class Home extends State<HomeFragment> {
  String url5 = "https://data.dadaabc"
      ".com/parent_ads/2018124/40/00/77/72/40007772a4dc34e8caf83e5917e025ca.png";
  String url4 = "https://data.dadaabc"
      ".com/parent_ads/2018124/84/a4/d3/b1/84a4d3b1ce6d9da4a94401b5e7178a1f.png";
  String url2 = "https://data.dadaabc"
      ".com/parent_ads/2018123/18/46/bd/3d/1846bd3d1a4856952d5792ccd0af748e.png";
  String url3 = "https://data.dadaabc"
      ".com/parent_ads/20181130/41/d1/60/70/41d1607005c64d818f524efa0510e893.jpg";

  @override
  Widget build(BuildContext context) {


    Widget build(int index, String data) {
      return Text('参数');
    }

    // TODO: implement build
    return new Padding(
      padding: new EdgeInsets.only(left: 20, right: 20, top: 44),
      child: new Container(
        child: new Column(
          children: <Widget>[
            new Row(
              children: <Widget>[
                Expanded(
                    child: Text(
                  'DaDa',
                  textAlign: TextAlign.left,
                  style: TextStyle(fontSize: 28, color: Color(0xff262626)),
                )),
                new Text(
                  'tip',
                  textAlign: TextAlign.right,
                ),
              ],
            ),
            new BannerView(data: <String>[url2, url3, url4, url5],
            buildShowView: build,),
          ],
        ),
      ),
    );
  }
}
