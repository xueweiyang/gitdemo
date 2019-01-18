import 'dart:convert';

class Job {

  final String name;
  final String cname;
  final String size;
  final String salary;
  final String username;
  final String title;

  Job({this.name, this.cname, this.size, this.salary, this.username, this
      .title});

  static List<Job> fromJson(String json) {
    return new JsonCodec()
        .decode(json)['list']
        .map((obj) => Job.fromJson(obj))
        .toList();
  }

  static Job fromMap(Map map) {
    return new Job(
        name: map['name'],
        cname: map['cname'],
        size: map['size'],
        salary: map['salary'],
        username: map['username'],
        title: map['title']
    );
  }
}
