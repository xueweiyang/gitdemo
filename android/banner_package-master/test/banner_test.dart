import 'package:flutter/material.dart';
import 'package:banner/banner_widget.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:banner/banner.dart';
import 'package:banner/banner_evalutor.dart';

void main() {
  final List<Model> data = [
    new Model(imgUrl: 'https://img01.sogoucdn.com/app/a/100520093/60d2f4fe0275d790-007c9f9485c5acfd-bdc6566f9acf5ba2a7e7190734c38920.jpg'),
    new Model(imgUrl: 'http://img4.duitang.com/uploads/item/201502/27/20150227083741_w5YjR.jpeg'),
    new Model(imgUrl: 'http://img4.duitang.com/uploads/item/201501/06/20150106081248_ae4Rk.jpeg'),
    new Model(imgUrl: 'http://pic1.win4000.com/wallpaper/a/59322eda4daf0.jpg'),
    new Model(imgUrl: 'http://uploads.5068.com/allimg/1711/151-1G130093R1.jpg'),
  ];

  testWidgets('Default Banner', (WidgetTester tester) async {
    await tester.pumpWidget(
        MaterialApp(home: BannerWidget(
          data: data,
          curve: Curves.linear,
          onClick: (position,bannerWithEval){
            print(position);
          },
        )));
    expect(find.text("0", skipOffstage: false), findsOneWidget);
  });
}

class Model extends Object with BannerWithEval {
  final String imgUrl;

  Model({this.imgUrl});

  @override
  get bannerUrl => imgUrl;
}
