import 'package:json_annotation/json_annotation.dart';

part 'HotBean.g.dart';

@JsonSerializable()
class HotBean extends Object with _$HotBeanSerializerMixin {
  List<RecentBean> recent;

  HotBean({this.recent});

  factory HotBean.fromJson(Map<String, dynamic> json) =>
      _$HotBeanFromJson(json);
}

@JsonSerializable()
class RecentBean extends Object with _$RecentBeanSerializerMixin {
  int news_id;
  String url;
  String thumbnail;
  String title;
  bool readState;

  RecentBean(
      {this.news_id, this.url, this.thumbnail, this.title, this.readState});

  factory RecentBean.fromJson(Map<String, dynamic> json) =>
      _$RecentBeanFromJson(json);
}
