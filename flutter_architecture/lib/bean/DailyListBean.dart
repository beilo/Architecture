import 'package:json_annotation/json_annotation.dart';
part 'DailyListBean.g.dart';

@JsonSerializable()
class DailyListBean extends Object with _$DailyListBeanSerializerMixin {
  String date;
  List<StoriesBean> stories;
  List<TopStoriesBean> top_stories;

  DailyListBean({this.date, this.stories, this.top_stories});

  factory DailyListBean.fromJson(Map<String,dynamic> json) =>
      _$DailyListBeanFromJson(json);


}

@JsonSerializable()
class StoriesBean extends Object with _$StoriesBeanSerializerMixin{
  int type;
  int id;
  String ga_prefix;
  String title;
  List<String> images;

  StoriesBean({this.type, this.id, this.ga_prefix, this.title, this.images});

  factory StoriesBean.fromJson(Map<String, dynamic> json) =>
      _$StoriesBeanFromJson(json);
}

@JsonSerializable()
class TopStoriesBean extends Object with _$TopStoriesBeanSerializerMixin{
  int type;
  int id;
  String ga_prefix;
  String title;
  String image;
  bool readState;

  TopStoriesBean(
      {this.type,
        this.id,
        this.ga_prefix,
        this.title,
        this.image,
        this.readState});

  factory TopStoriesBean.fromJson(Map<String, dynamic> json) =>
      _$TopStoriesBeanFromJson(json);
}