import 'dart:convert' show json;


class JournalismBean {

  String desc;
  String statusCode;
  Result result;


  JournalismBean.fromParams({this.desc, this.statusCode, this.result});

  factory JournalismBean(jsonStr) => jsonStr is String ? JournalismBean.fromJson(json.decode(jsonStr)) : JournalismBean.fromJson(jsonStr);

  JournalismBean.fromJson(jsonRes) {
    desc = jsonRes['desc'];
    statusCode = jsonRes['statusCode'];
    result = new Result.fromJson(jsonRes['result']);

  }

  @override
  String toString() {
    return '{"desc": ${desc != null?'${json.encode(desc)}':'null'},"statusCode": ${statusCode != null?'${json.encode(statusCode)}':'null'},"result": $result}';
  }
}



class Result {

  String channel;
  String num;
  List<JournalismItem> list;


  Result.fromParams({this.channel, this.num, this.list});

  Result.fromJson(jsonRes) {
    channel = jsonRes['channel'];
    num = jsonRes['num'];
    list = [];

    for (var listItem in jsonRes['list']){

      list.add(new JournalismItem.fromJson(listItem));
    }


  }

  @override
  String toString() {
    return '{"channel": ${channel != null?'${json.encode(channel)}':'null'},"num": ${num != null?'${json.encode(num)}':'null'},"list": $list}';
  }
}



class JournalismItem {

  String category;
  String content;
  String pic;
  String src;
  String time;
  String title;
  String url;
  String weburl;


  JournalismItem.fromParams({this.category, this.content, this.pic, this.src, this.time, this.title, this.url, this.weburl});

  JournalismItem.fromJson(jsonRes) {
    category = jsonRes['category'];
    content = jsonRes['content'];
    pic = jsonRes['pic'];
    src = jsonRes['src'];
    time = jsonRes['time'];
    title = jsonRes['title'];
    url = jsonRes['url'];
    weburl = jsonRes['weburl'];

  }

  @override
  String toString() {
    return '{"category": ${category != null?'${json.encode(category)}':'null'},"content": ${content != null?'${json.encode(content)}':'null'},"pic": ${pic != null?'${json.encode(pic)}':'null'},"src": ${src != null?'${json.encode(src)}':'null'},"time": ${time != null?'${json.encode(time)}':'null'},"title": ${title != null?'${json.encode(title)}':'null'},"url": ${url != null?'${json.encode(url)}':'null'},"weburl": ${weburl != null?'${json.encode(weburl)}':'null'}}';
  }
}

