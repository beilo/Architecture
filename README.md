# RxArchitecture 

##### google在2017年出了一个新的框架 [android-architecture-components](https://github.com/googlesamples/android-architecture-components) 模式为MVVM.主要用到了

1. V - LifecycleActivity,LifecycleFragment为View层,所有的view操作都在这边处理,数据方面只负责接收
2. VM - ViewModel用于控制数据逻辑层代码,所有的数据代码都在这里处理
3. M - 俗称的Model层
4. repository - 数据仓库,一般来说是用于定义service的,主要是当数据源需要在SQLite和Http切换的时候用到,如果没有这种业务可以不需要这一层
5. Data Binding - 用于实现双向绑定
6. LiveData 一个具有生命周期的数据管理器,当V和VM绑定后,在VM的LiveData会跟随的V的生命周期消失

##### 我的取舍
1. Data Binding - 我第一个就舍弃掉了,原因是我觉得它侵入了我的xml文件,维护代码会很麻烦
2. LiveData - 刚刚开始我觉得这个东西应该很棒,毕竟跟随着生命周期嘛.然后我写着写着就觉得不对劲了.首先,LiveData的observe方法会调用多次给了我不可控的感觉.其次,LiveData作为一个数据管理器,数据操作太难受了,用着很别扭.然后我就弃用LiveData,投入了RxJava的怀抱了.哇~贼舒服...

##### 于是乎我自己参照官方的Demo,自己写了一个demo,demo的功能参考的是GeekNews这个开源项目,下面简单介绍下我的demo
1. base 封装了LifecycleActivity,LifecycleFragment,主要是实现了LifecycleOwner接口
2. di dogger2的一些处理,封装了Activity,fragment,ViewModel,Http相关.
3. repository 数据提供工厂
4. ui 项目的V层和VM层全部在这个里面
5. viewmodel 提供统一的VM生成器,用于dogger2生成VM
6. 本项目采用了单AC+多FG模式,数据操作用的是RxJava2


## Thanks
```
感谢项目中用到的开源项目的作者，本项目中有些功能受你们项目灵感的启发，有些功能也用到你们的代码完成。
对此如果有什么意见请与我联系！再次感谢！
```


## TODO
- [x] toolbar代码的编写
- [x] 图片瀑布流滑动造成混乱
- [x] RxHelper.rxSchedulerHelper 从VM层迁移到V层.这不应该属于VM管理
- [x] 去除dogger2
- [x] 组件化第一步完成
- [x] flutter初步了解  
- [x] 新增了新闻模块 
- [ ] repository的完善,增加SQLite本地缓存
- [ ] 更多的功能
