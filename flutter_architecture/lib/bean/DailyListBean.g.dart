// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'DailyListBean.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

DailyListBean _$DailyListBeanFromJson(Map<String, dynamic> json) {
  return new DailyListBean(
      date: json['date'] as String,
      stories: (json['stories'] as List)
          ?.map((e) => e == null
              ? null
              : new StoriesBean.fromJson(e as Map<String, dynamic>))
          ?.toList(),
      top_stories: (json['top_stories'] as List)
          ?.map((e) => e == null
              ? null
              : new TopStoriesBean.fromJson(e as Map<String, dynamic>))
          ?.toList());
}

abstract class _$DailyListBeanSerializerMixin {
  String get date;
  List<StoriesBean> get stories;
  List<TopStoriesBean> get top_stories;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'date': date,
        'stories': stories,
        'top_stories': top_stories
      };
}

StoriesBean _$StoriesBeanFromJson(Map<String, dynamic> json) {
  return new StoriesBean(
      type: json['type'] as int,
      id: json['id'] as int,
      ga_prefix: json['ga_prefix'] as String,
      title: json['title'] as String,
      images: (json['images'] as List)?.map((e) => e as String)?.toList());
}

abstract class _$StoriesBeanSerializerMixin {
  int get type;
  int get id;
  String get ga_prefix;
  String get title;
  List<String> get images;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'type': type,
        'id': id,
        'ga_prefix': ga_prefix,
        'title': title,
        'images': images
      };
}

TopStoriesBean _$TopStoriesBeanFromJson(Map<String, dynamic> json) {
  return new TopStoriesBean(
      type: json['type'] as int,
      id: json['id'] as int,
      ga_prefix: json['ga_prefix'] as String,
      title: json['title'] as String,
      image: json['image'] as String,
      readState: json['readState'] as bool);
}

abstract class _$TopStoriesBeanSerializerMixin {
  int get type;
  int get id;
  String get ga_prefix;
  String get title;
  String get image;
  bool get readState;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'type': type,
        'id': id,
        'ga_prefix': ga_prefix,
        'title': title,
        'image': image,
        'readState': readState
      };
}
