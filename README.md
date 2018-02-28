# my-seed
这是一个web后端的demo，其中包括：
* 基于mybatis-mapper实现的通用的单表操作接口的mapper。
* 基于mybatis-mapper抽象的通用的Example对单表操作的条件构建。
* 基于shiro实现的权限管理，支持多组织权限。
* 基于shiro的sessionDao实现的将session保存到redis。
* 基于spring-redis的可插拔的缓存。
* 基于spring-stomp（stomp是一个协议）的websocket消息推送功能。
* 基于spring的HandlerMethodArgumentResolver实现的登录用户自动注入controller参数功能。
* 基于spring的HandlerMethodArgumentResolver实现的自动注入controller的list类型参数（只支持元素为基本类型+String）。
* 基于mybatis-mapper提供的mybatis通用分页功能。
* mybatis的mapper.xml配置启用驼峰命名转换可以将sql查询出来的字段自动赋值对象相应的属性。
* mybatis的一对一、一对多关联查询的最佳实践。
* 基于url的token参数实现的无cookie的跨浏览器的session功能。
* 基于maven的profile实现的多环境部署配置。
* 基于spring和前端FormData（复合表单方式）或者FileReader（流方式）实现的文件上传功能。
* 基于restful风格的url设计实践。
* 静态文件组织策略（所有的静态文件放在static目录，这样在web.xml和shiro权限过滤配置中就非常方便）。
* 基于spring的PropertyPlaceholderConfigurer和Value注解实现的自动注入properties的配置到java对象。
* spring的component-scan和exclude-filter、include-filter正确配置bean的注入，防止bean重复创建。
* 基于spring的HandlerExceptionResolver实现的统一异常处理。
* 基于自定义日志注解、aspectj、ConcurrentLinkedQueue实现的生产者消费者模式的高效省资源实时性好的日志记录到数据库。
* 基于druid配置的service方法性能监控、sql性能监控。
* 自动扫描Controller生成url和权限字符串映射关系的工具类。
* spring多数据源配置。
* 拓展http响应头以及jquery的ajax实现的ajax重定向功能。
* 自定义的js函数实现js对象转成http请求参数支持spring注入controller参数。
* js其他工具函数，如日期、字符串、提取url中的sessionId。
* java工具类，如数组拼接、md5消息摘要、反射工具类、csv/excel2003/excel2007文件内容抽取器等。
  
  
  
  
