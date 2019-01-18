
import 'package:flutter/material.dart';
import 'package:flutter_app/button.dart';
import 'package:flutter_app/counterdisplay.dart';
import 'package:flutter_app/product.dart';

class MyAppBar extends StatelessWidget {
  MyAppBar({this.title});

  final Widget title;

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Container(
      height: 56.0,
      padding: const EdgeInsets.symmetric(horizontal: 8.0),
      child: new Row(
        children: <Widget>[
          new IconButton(
            icon: new Icon(Icons.menu),
            onPressed: null,
            tooltip: 'Navigation menu',
          ),
          new Expanded(child: title),
          new IconButton(
            icon: new Icon(Icons.security),
            onPressed: null,
            tooltip: 'Security',
          ),
          new IconButton(
            icon: new Icon(Icons.search),
            onPressed: null,
            tooltip: 'Search',
          )
        ],
      ),
    );
  }
}

class MyScaffold extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Material(
      child: new Column(
        children: <Widget>[
          new MyAppBar(
            title: new Text(
              'Example title',
              style: Theme.of(context).primaryTextTheme.title,
            ),
          ),
          new Expanded(
              child: new Center(
            child: new Text('Hello world'),
          ))
        ],
      ),
    );
  }
}

class TutorialHome extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      appBar: new AppBar(
        leading: new IconButton(
          icon: new Icon(Icons.menu),
          tooltip: 'Navigation menu',
          onPressed: null,
        ),
        title: new Text('Example title'),
        actions: <Widget>[
          new IconButton(
              icon: new Icon(Icons.search), tooltip: 'Search', onPressed: null)
        ],
      ),
      body: new Center(
        child: new ShoppingListItem(inCart: false,
        product: new Product(name: '瓜子'),),
      ),
      floatingActionButton: new FloatingActionButton(
        onPressed: null,
        tooltip: 'Add',
        child: new Icon(Icons.add),
      ),

    );
  }
}

void main() {
  runApp(new MaterialApp(
    title: 'my app',
    home: new TutorialHome(),
  ));
}
