import 'package:flutter/material.dart';
import 'package:flutter_dada/model/RecommendModel.dart';

class RecommendView extends StatelessWidget {
  RecommendModel model;

  RecommendView(this.model);

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Column(
      children: <Widget>[
        new Image(
          image: NetworkImage(model.imageUrl),
          width: 80,
          height: 50,
          fit: BoxFit.cover,
        ),
        new Text(model.title),
        new Text(model.content),
      ],
    );
  }
}
