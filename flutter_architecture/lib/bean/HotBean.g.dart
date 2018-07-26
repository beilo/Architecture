// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'HotBean.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

HotBean _$HotBeanFromJson(Map<String, dynamic> json) {
  return new HotBean(
      recent: (json['recent'] as List)
          ?.map((e) => e == null
              ? null
              : new RecentBean.fromJson(e as Map<String, dynamic>))
          ?.toList());
}

abstract class _$HotBeanSerializerMixin {
  List<RecentBean> get recent;
  Map<String, dynamic> toJson() => <String, dynamic>{'recent': recent};
}

RecentBean _$RecentBeanFromJson(Map<String, dynamic> json) {
  return new RecentBean(
      news_id: json['news_id'] as int,
      url: json['url'] as String,
      thumbnail: json['thumbnail'] as String,
      title: json['title'] as String,
      readState: json['readState'] as bool);
}

abstract class _$RecentBeanSerializerMixin {
  int get news_id;
  String get url;
  String get thumbnail;
  String get title;
  bool get readState;
  Map<String, dynamic> toJson() => <String, dynamic>{
        'news_id': news_id,
        'url': url,
        'thumbnail': thumbnail,
        'title': title,
        'readState': readState
      };
}
