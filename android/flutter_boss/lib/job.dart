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

  static List fromJson(String json) {
//    return new JsonCodec()
//        .decode(json)['list']
//        .map((obj) => Job.fromMap(obj))
//        .toList();

    return new List<Job>.from(new JsonCodec()
        .decode(json)['list'].map((obj) => Job.fromMap(obj)));
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

class JobListC {

  final List<Job> list;

  JobListC({this.list});

  static JobListC fromJson(String json) {
    return new JsonCodec()
        .decode(json)
        .map((obj) => JobListC.fromMap(obj));
  }

  static JobListC fromMap(Map map) {
    return new JobListC(
      list: map['list']
    );
  }
}
