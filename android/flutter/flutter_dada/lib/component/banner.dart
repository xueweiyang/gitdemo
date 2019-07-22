import 'package:flutter/material.dart';
import 'dart:ui' as ui;
import 'dart:async';

import 'package:flutter_dada/util/toast.dart';

typedef Widget BuildShowView<D>(int index, D itemData);

const IntegerMax = 0x7fffffff;

class BannerView<T> extends StatefulWidget {
  int scrollTime;
  int delayTime;
  List<T> data;
  double height;
  BuildShowView<T> buildShowView;

  BannerView(
      {Key key,
      @required this.data,
      @required this.buildShowView,
      this.scrollTime = 200,
      this.delayTime = 3000,
      this.height = 200})
      : super(key: key);

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new BannerViewState();
  }
}

class BannerViewState extends State<BannerView> {
  final pageController = new PageController(initialPage: IntegerMax ~/ 2);
  Timer timer;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    resetTimer();
  }

  resetTimer() {
    clearTimer();
    timer = new Timer.periodic(new Duration(milliseconds: widget.delayTime),
        (Timer timer) {
//      print(timer.tick);
      if (pageController.positions.isNotEmpty) {
        var i = pageController.page.toInt() + 1;
        pageController.animateToPage(i % widget.data.length,
            duration: new Duration(milliseconds: widget.scrollTime),
            curve: Curves.linear);
      }
    });
  }

  clearTimer() {
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    double screenWidth = MediaQueryData.fromWindow(ui.window).size.width;
    return new SizedBox(
      height: widget.height,
      child: widget.data.length == 0
          ? null
          : new GestureDetector(
              onTap: () {
                Toast.show(context, "哈哈哈");
              },
              child: new PageView.builder(
                controller: pageController,
                itemBuilder: (BuildContext context, int index) {
//                  return widget.buildShowView(
//                      index, widget.data[index % widget.data.length]);
                  var data = widget.data[index % widget.data.length];
                  return new Material(child: new Image(image: new
                      NetworkImage(data), width: screenWidth,height: 200,fit:
                  BoxFit.cover,),
                  borderRadius: BorderRadius.circular(8),);
                },
                itemCount: IntegerMax,
              ),
            ),
    );
  }
}
