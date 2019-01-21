import 'package:flutter/material.dart';
import 'package:flutter_boss/job.dart';

class JobListItem extends StatelessWidget {
  final Job job;

  const JobListItem(this.job);

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Padding(
      padding:
          const EdgeInsets.only(top: 3.0, left: 5.0, right: 5.0, bottom: 3.0),
      child: new SizedBox(
        child: new Card(
          elevation: 0.0,
          child: new Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              new Expanded(
                  child: new Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisSize: MainAxisSize.min,
                children: <Widget>[
                  new Row(
                    children: <Widget>[
                      new Padding(
                        padding: const EdgeInsets.only(
                            top: 10.0, left: 10.0, bottom: 5.0),
                        child: new Text(job.name),
                      ),
                      new Expanded(
                          child: new Column(
                        crossAxisAlignment: CrossAxisAlignment.end,
                        children: <Widget>[
                          new Padding(
                            padding: const EdgeInsets.only(right: 10.0),
                            child: new Text(job.salary),
                          )
                        ],
                      ))
                    ],
                  ),
                  new Container(
                    child: new Text(job.cname + ' ' + job.size),
                  ),
                  new Divider(),
                  new Row(
                    children: <Widget>[
                      new Padding(
                        padding: const EdgeInsets.only(
                            top: 5.0, left: 10.0, right: 5.0, bottom: 15.0),
                        child: new Text(job.username + ' ' + job.title),
                      )
                    ],
                  )
                ],
              ))
            ],
          ),
        ),
      ),
    );
  }
}
