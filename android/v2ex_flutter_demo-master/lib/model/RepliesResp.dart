import 'dart:convert' show json;

import 'package:flutter_app/model/BaseResp.dart';
import 'package:flutter_app/model/MemberResp.dart';

class RepliesResp extends BaseResp {
  List<Reply> list;

  RepliesResp.fromParams(
      {this.list, String message, String status, RateLimit rateLimit})
      : super.fromParams(
            message: message, status: status, rate_limit: rateLimit);

  factory RepliesResp(jsonStr) => jsonStr is String
      ? RepliesResp.fromJson(json.decode(jsonStr))
      : RepliesResp.fromJson(jsonStr);

  RepliesResp.fromJson(jsonRes) : super.fromJson(jsonRes) {
    list = [];

    for (var listItem in jsonRes) {
      list.add(new Reply.fromJson(listItem));
    }
  }

  @override
  String toString() {
    return '{"json_list": $list}';
  }
}

class Reply {
  int created;
  int id;
  int last_modified;
  int last_touched;
  int replies;
  String content;
  String content_rendered;
  String last_reply_by;
  String title;
  String url;
  Member member;

  Reply.fromParams(
      {this.created,
      this.id,
      this.last_modified,
      this.last_touched,
      this.replies,
      this.content,
      this.content_rendered,
      this.last_reply_by,
      this.title,
      this.url,
      this.member});

  Reply.fromJson(jsonRes) {
    created = jsonRes['created'];
    id = jsonRes['id'];
    last_modified = jsonRes['last_modified'];
    last_touched = jsonRes['last_touched'];
    replies = jsonRes['replies'];
    content = jsonRes['content'];
    content_rendered = jsonRes['content_rendered'];
    last_reply_by = jsonRes['last_reply_by'];
    title = jsonRes['title'];
    url = jsonRes['url'];
    member = new Member.fromJson(jsonRes['member']);
  }

  @override
  String toString() {
    return '{"created": $created,"id": $id,"last_modified": $last_modified,"last_touched": $last_touched,"replies": $replies,"content": ${content !=
        null
        ? '${json.encode(content)}'
        : 'null'},"content_rendered": ${content_rendered != null
        ? '${json.encode(content_rendered)}'
        : 'null'},"last_reply_by": ${last_reply_by != null ? '${json.encode(
        last_reply_by)}' : 'null'},"title": ${title != null ? '${json.encode(
        title)}' : 'null'},"url": ${url != null
        ? '${json.encode(url)}'
        : 'null'},"member": $member}';
  }
}
