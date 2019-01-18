import 'dart:io';
import 'dart:convert';
import 'package:flutter/material.dart';


class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new MaterialApp(
      home: new MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key}) : super(key : key);

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new _MyHomePageState();
  }
}

class _MyHomePageState extends State<MyHomePage> {

  var _ipAddress = 'Unknown';

  _getIpAddress() async {
    var url = 'https://httpbin.org/ip';
    var httpClient = new HttpClient();

    String result;
    try {
      var request = await httpClient.getUrl(Uri.parse(url));
      var response = await request.close();
      if (response.statusCode == HttpStatus.OK) {
        var json = await response.transform(Utf8Decoder()).join();
        var data = new JsonCodec().decode(json);
        result = data['origin'];
      }
    } catch (exception) {
      result = 'Failed get ip';
    }

    setState(() {
      _ipAddress = result;
    });
  }

  @override
  Widget build(BuildContext context) {
    var spacer = new SizedBox(height: 32.0,);
    // TODO: implement build
    return new Scaffold(
      body: new Center(
        child: new Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            new Text('Your current Ip is:'),
            new Text('$_ipAddress.'),
            spacer,
            new RaisedButton(onPressed: _getIpAddress,
            child: new Text('Get ip'),)
          ],
        ),
      ),
    );
  }
}

void main() {
  runApp(new MyApp());
}
