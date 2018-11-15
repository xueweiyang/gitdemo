# fastdex

[![license](https://img.shields.io/hexpm/l/plug.svg)](https://raw.githubusercontent.com/typ0520/fastdex/master/LICENSE) [ ![Download](https://api.bintray.com/packages/typ0520/maven/com.github.typ0520%3Afastdex-gradle/images/download.svg) ](https://bintray.com/typ0520/maven/com.github.typ0520%3Agradle-plugin/_latestVersion)

如果你忍受不了apk龟速的编译(尤其是项目中有多个dex)，fastdex可以帮助你加快apk生成过程

![fastdex.png](fastdex-idea-plugin/src/main/resources/icons/bg_update.png)

Android API 9(2.3)+  ; android-gradle-build 2.0.0+

[版本记录](https://github.com/typ0520/fastdex/blob/master/CHANGELOG.md)

## 使用方式

> idea 插件

在Android Studio中，打开搜索插件页面：

- MacOS

    Android Studio → Preferences → Plugins → Browse repositories

- Windows 和 Linux

    File → Settings → Plugins → Browse repositories
    
搜索fastdex安装重启Android Studio就可以了。

> 手动配置

- 1、关闭Instant Run功能
     点击左上角Android studio -> Preferences -> Build,Execution,Deployment -> Instant Run -> Enable Instant Run......(把对勾去掉)

- 2、在root project下的build.gradle中添加依赖

  	````
  	buildscript {
        repositories {
            jcenter()
        }
        
        dependencies {
            classpath 'com.github.typ0520:fastdex-gradle:0.8.6'
        }
    }
   	````
    
- 3、在app的项目中的build.gradle添加插件

    ````
    apply plugin: 'fastdex.app'
    ````
    
- 4、直接点击studio的run或者执行打包命令，就会在任务执行的过程中做hook

    ````
    第一次全量打包成功后，fastdex支持把增量的dex和资源推送到正在运行的app里重启并加载，这样会省去安装app的时间开销
    拿debug为例如果没有配置flavor就执行(gradlew fastdex${Variant})
    
    Mac/Linux:
    ./gradlew fastdexDebug
    
    Windows:
    gradlew.bat fastdexDebug
    ````

## 注意事项

- 1、不要把fastdex打出来的包用在生产环境，因为fastdex打出来的包项目所有的代码都在第二个dex后面，会造成5.0以
    下机器首次运行比较慢(如果是本地调试就无所谓了)；当打包生产环境apk时注释掉加入插件的代码
    //apply plugin: 'fastdex.app'
    
- 2、fastdex会忽略开启混淆的buildType

- 3、强烈建议你的application不要直接依赖library工程，打成aar包让application工程远程依赖

## 实现原理
  - gradle在执行transformClassesWithDexFor${variant}任务生成dex文件时会很慢(尤其是开启了multidex)，我们在开发中，修改的几乎全是项目代码，第三方库改动比较小。fastdex的原理就是预先把所有代码生成dex,
  当下次执行assemble任务时只会把项目目录下变化的代码生成dex，然后和缓存的dex合并生成apk，这样即不影响调试，又能在生成dex的过程中省下了大量的时间。
  [详情](http://www.jianshu.com/p/53923d8f241c)
  
  - 应用安装的速度比较慢，尤其是5.0以后的版本，fastdex会把补丁dex和资源通过adb推到正在运行的app里直接重启app

## 打包流程
##### 全量打包时的流程:
  - 1、合并所有的class文件生成一个jar包
  - 2、扫描所有的项目代码并且在构造方法里添加对fastdex.runtime.antilazyload.AntilazyLoad类的依赖
     这样做的目的是为了解决class verify的问题，
     详情请看 [安卓App热补丁动态修复技术介绍](https://mp.weixin.qq.com/s?__biz=MzI1MTA1MzM2Nw==&mid=400118620&idx=1&sn=b4fdd5055731290eef12ad0d17f39d4a)
  - 3、对项目代码做快照，为了以后补丁打包时对比那些java文件发生了变化
  - 4、对当前项目的所以依赖做快照，为了以后补丁打包时对比依赖是否发生了变化，如果变化需要清除缓存
  - 5、调用真正的transform生成dex
  - 6、缓存生成的dex，并且把fastdex-runtime.dex插入到dex列表中，假如生成了两个dex，classes.dex classes2.dex 需要做一下操作
     fastdex-runtime.dex => classes.dex
     classes.dex         => classes2.dex
     classes2.dex        => classes3.dex
     然后运行期在入口Application(fastdex.runtime.FastdexApplication)使用MultiDex把所有的dex加载进来
  - @see [fastdex.build.transform.FastdexDexTransform](https://github.com/typ0520/fastdex/blob/master/fastdex-gradle/src/main/groovy/fastdex/build/transform/FastdexDexTransform.groovy)
  - 7、保存资源映射表，为了保持id的值一致，详情看
  - @see [fastdex.build.task.FastdexResourceIdTask](https://github.com/typ0520/fastdex/blob/master/fastdex-gradle/src/main/groovy/fastdex/build/task/FastdexResourceIdTask.groovy)


##### 补丁打包时的流程
  - 1、检查缓存的有效性
  - @see [fastdex.build.variant.FastdexVariant](https://github.com/typ0520/fastdex/blob/master/fastdex-gradle/src/main/groovy/fastdex/build/variant/FastdexVariant.groovy) 的prepareEnv方法说明
  - 2、扫描所有变化的java文件并编译成class
  - @see [fastdex.build.task.FastdexCustomJavacTask](https://github.com/typ0520/fastdex/blob/master/fastdex-gradle/src/main/groovy/fastdex/build/task/FastdexCustomJavacTask.groovy)
  - 3、合并所有变化的class并生成jar包
  - 4、生成补丁dex
  - 5、把所有的dex按照一定规律放在transformClassesWithMultidexlistFor${variantName}任务的输出目录
     fastdex-runtime.dex    => classes.dex
     patch.dex              => classes2.dex
     dex_cache.classes.dex  => classes3.dex
     dex_cache.classes2.dex => classes4.dex
     dex_cache.classesN.dex => classes(N + 2).dex

## Thanks
[Instant Run](https://developer.android.com/studio/run/index.html#instant-run)

[Tinker](https://github.com/Tencent/tinker)

[Freeline](https://github.com/alibaba/freeline)

[安卓App热补丁动态修复技术介绍](https://mp.weixin.qq.com/s?__biz=MzI1MTA1MzM2Nw==&mid=400118620&idx=1&sn=b4fdd5055731290eef12ad0d17f39d4a)

[Android应用程序资源的编译和打包过程分析](http://blog.csdn.net/luoshengyang/article/details/8744683)
  
  
  
