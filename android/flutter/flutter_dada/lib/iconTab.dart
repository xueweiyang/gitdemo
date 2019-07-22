import 'package:flutter/material.dart';

class IconTab extends StatefulWidget {
  const IconTab({Key key, this.text, this.icon, this.color})
      : assert(text != null || icon != null || color != null),
        super(key: key);

  final String text;
  final String icon;
  final Color color;

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new IconTabState();
  }
}

class IconTabState extends State<IconTab> {

  Widget _labelText() {
    return new Text(widget.text,
      style: new TextStyle(color: widget.color),);
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    Widget label = new Column(
      children: <Widget>[
        new Container(
          child: new Image(image: new AssetImage(widget.icon),
            height: 18,
            width: 18,),
        ),
        _labelText()
      ],
    );
    return new SizedBox(
      height: 50,
      child: new Center(
        child: label,
      ),
    );
  }

}
