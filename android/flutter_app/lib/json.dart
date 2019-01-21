import 'package:flutter/material.dart';

var stuJson = """
{
  "id":"487349",
  "name":"Pooja Bhaumik",
  "score" : 1000
}
""";

class Stu {
  String id;
  String name;
  int score;

  Stu({this.id, this.name, this.score});

  static fromJson(Map map) {
    return Stu(
      id: map['id'],
      name: map['name'],
      score: map['score']
    );
  }
}

