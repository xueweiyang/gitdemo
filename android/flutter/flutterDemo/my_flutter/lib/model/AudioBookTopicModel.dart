import 'package:json_annotation/json_annotation.dart';

@JsonSerializable()
class AudioBookTopicModel extends Object {

  List<Advertisement> advertisement;

}

@JsonSerializable()
class Advertisement extends Object {

  int id;
  String name;
  String jump_url;
  String cover_url;

}