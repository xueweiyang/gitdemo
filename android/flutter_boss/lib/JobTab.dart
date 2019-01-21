import 'package:flutter/material.dart';
import 'package:flutter_boss/job.dart';
import 'package:flutter_boss/joblist_item.dart';

class JobsTab extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new JobList();
  }
}

class JobList extends State<JobsTab> {

  List<Job> _jobs = [];

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    getJobList();
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
//      backgroundColor: new Color.fromARGB(255, 242, 242, 245),
//      appBar: new AppBar(
//        elevation: 0.0,
//        title: new Text(
//          'Android',
//          style: new TextStyle(fontSize: 20.0, color: Colors.red),
//        ),
//      ),
      body: new ListView.builder(
          itemCount: _jobs.length,
          itemBuilder: buildJobItem),
//      body: new Center(child :new Text("hhhh")),
    );
  }

  Widget buildJobItem(BuildContext context, int index) {
    Job job = _jobs[index];
    var jobItem = new InkWell(
      onTap: () {
        showDialog(context: context, child: new AlertDialog(
          content: new Text(
            "尽请期待",
            style: new TextStyle(fontSize: 20.0),
          ),
        ));
      },
      child: new JobListItem(job),
    );
    return jobItem;
  }

  void getJobList() {
    setState(() {
      _jobs = Job.fromJson("""
          {
            "list": [
              {
                "name": "架构师（Android）",
                "cname": "蚂蚁金服",
                "size": "B轮",
                "salary": "25k-45k",
                "username": "Claire",
                "title": "HR"
              },
              {
                "name": "资深Android架构师",
                "cname": "今日头条",
                "size": "D轮",
                "salary": "40k-60k",
                "username": "Kimi",
                "title": "HRBP"
              }
            ]
          }
      """);
    });
  }
}

void main() {
  runApp(new MaterialApp(
    home: new JobsTab(),
  ));
}
