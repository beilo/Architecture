import 'dart:convert' show json;


class LatestJokeBean {

  int error_code;
  String reason;
  Result result;


  LatestJokeBean.fromParams({this.error_code, this.reason, this.result});

  factory LatestJokeBean(jsonStr) => jsonStr is String ? LatestJokeBean.fromJson(json.decode(jsonStr)) : LatestJokeBean.fromJson(jsonStr);

  LatestJokeBean.fromJson(jsonRes) {
    error_code = jsonRes['error_code'];
    reason = jsonRes['reason'];
    result = new Result.fromJson(jsonRes['result']);

  }

  @override
  String toString() {
    return '{"error_code": $error_code,"reason": ${reason != null?'${json.encode(reason)}':'null'},"result": $result}';
  }
}



class Result {

  List<LatestJokeItem> data;


  Result.fromParams({this.data});

  Result.fromJson(jsonRes) {
    data = [];

    for (var dataItem in jsonRes['data']){

      data.add(new LatestJokeItem.fromJson(dataItem));
    }


  }

  @override
  String toString() {
    return '{"data": $data}';
  }
}



class LatestJokeItem {

  int unixtime;
  String content;
  String hashId;
  String updatetime;


  LatestJokeItem.fromParams({this.unixtime, this.content, this.hashId, this.updatetime});

  LatestJokeItem.fromJson(jsonRes) {
    unixtime = jsonRes['unixtime'];
    content = jsonRes['content'];
    hashId = jsonRes['hashId'];
    updatetime = jsonRes['updatetime'];

  }

  @override
  String toString() {
    return '{"unixtime": $unixtime,"content": ${content != null?'${json.encode(content)}':'null'},"hashId": ${hashId != null?'${json.encode(hashId)}':'null'},"updatetime": ${updatetime != null?'${json.encode(updatetime)}':'null'}}';
  }
}

