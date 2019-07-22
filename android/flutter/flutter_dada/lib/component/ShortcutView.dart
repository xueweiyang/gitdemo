import 'package:flutter/material.dart';
import 'package:flutter_dada/util/toast.dart';
import 'package:flutter_dada/model/ShortcutModel.dart';

class ShortcutView extends StatelessWidget {
  String url3 = "https://data.dadaabc"
      ".com/parent_ads/20181130/41/d1/60/70/41d1607005c64d818f524efa0510e893.jpg";

  ShortcutModel model;

  ShortcutView(this.model);

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new GestureDetector(
      onTap: () {
        Toast.show(context, "跳转口语练习");
      },
      child: new Column(
        children: <Widget>[
          new Material(
            child: new Image(
              image: NetworkImage(url3),
              width: 40,
              height: 40,
              fit: BoxFit.cover,
            ),
            borderRadius: BorderRadius.circular(20),
          ),
          new Text(model.content)
        ],
      ),
    );
  }
}
