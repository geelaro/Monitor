# Monitor
一个Android性能监控工具

已完成内存监测，剩余正在完善中...
## 使用[freeline](https://github.com/alibaba/freeline/)构建
配置 project-level 的 build.gradle，加入 freeline-gradle 的依赖：

````Gradle
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.antfortune.freeline:gradle:0.8.5'
    }
}
````
然后，在你的主 module 的 build.gradle 中，应用 freeline 插件的依赖：

````Gradle
apply plugin: 'com.antfortune.freeline'

android {
    ...
}
````

最后，在命令行执行以下命令来下载 freeline 的 python 和二进制依赖。

- Windows[CMD]: gradlew initFreeline
- Linux/Mac: ./gradlew initFreeline
