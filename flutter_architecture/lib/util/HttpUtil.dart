import 'package:dio/dio.dart';

final String ZHIHU_HOST = 'http://news-at.zhihu.com/api/4/';
final String GANK_HOST = 'http://gank.io/api/';
const String Joke_HOST = 'http://v.juhe.cn/';
const String JOURNALISM_HOST = 'http://api.apishop.net/';

Dio getZhiHuDio() {
  Dio dio = Dio();
  Options options = dio.options;
  options.baseUrl = ZHIHU_HOST;
  options.connectTimeout = 5000;
  options.receiveTimeout = 5000;
  return dio;
}

Dio getGankDio() {
  Dio dio = Dio();
  Options options = dio.options;
  options.baseUrl = GANK_HOST;
  options.connectTimeout = 5000;
  options.receiveTimeout = 5000;
  return dio;
}

Dio getJokeDio(){
  Dio dio = Dio();
  Options options = dio.options;
  options.baseUrl = Joke_HOST;
  options.connectTimeout = 5000;
  options.receiveTimeout = 5000;
  return dio;
}

Dio getJournalismDio(){
  Dio dio = Dio();
  Options options = dio.options;
  options.baseUrl = JOURNALISM_HOST;
  options.connectTimeout = 5000;
  options.receiveTimeout = 5000;
  return dio;
}


