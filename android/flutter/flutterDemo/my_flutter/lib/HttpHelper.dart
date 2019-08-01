import 'package:dio/dio.dart';
import 'model/audio_book_topic_entity.dart';
import 'dart:convert';
class HttpHelper {

  Dio dio = Dio();

  HttpHelper() {
    dio.options.baseUrl = "https://api.dadaabc.com/";
  }


  getAudioBookTopic() async {
    Response response = await dio.post("v1/student.pictureBook.topic");
    Map map = JsonDecoder().convert(response.data.toString());
    var audioBookTopic = new AudioBookTopicEntity.fromJson(map);
    print('name::${audioBookTopic.advertisement[0].name}');
    return audioBookTopic;
  }
}