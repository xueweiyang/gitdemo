import 'package:flutter/material.dart';

class TabbarSample extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new MaterialApp(
      home: new DefaultTabController(
        length: choices.length,
        child: new Scaffold(
          appBar: new AppBar(
            title: const Text('Tab appbar'),
            bottom: new TabBar(
                tabs: choices.map((Choice choice) {
              return new Tab(
                text: choice.title,
                icon: new Icon(choice.icon),
              );
            }).toList()),
          ),
          body: new TabBarView(
              children: choices.map((Choice choice) {
            return new Padding(
              padding: const EdgeInsets.all(16.0),
              child: new ChoiceCard(choice: choice),
            );
          }).toList()),
        ),
      ),
    );
  }
}

class Choice {
  const Choice({this.title, this.icon});

  final String title;
  final IconData icon;
}

const List<Choice> choices = const <Choice>[
  const Choice(title: 'car', icon: Icons.directions_car),
  const Choice(title: 'bicycle', icon: Icons.directions_bike),
  const Choice(title: 'boat', icon: Icons.directions_boat),
];

class ChoiceCard extends StatelessWidget {

  const ChoiceCard({Key key, this.choice}) : super(key:key);

  final Choice choice;

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    final TextStyle textStyle = Theme.of(context).textTheme.display1;
    return new Card(
      color: Colors.white,
      child: new Center(
        child: new Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            new Icon(choice.icon,size: 128.9,color: textStyle.color,),
            new Text(choice.title, style: textStyle,)
          ],
        ),
      ),
    );
  }

}

void main() {
  runApp(new TabbarSample());
}
