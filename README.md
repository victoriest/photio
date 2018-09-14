# photio

## about bootstrap.yml

The ```bootstrap.yml``` file contains some sensitive information (e.g. server ip, database user and password)

The complete file template is seem like below:

```bootstrap.yml```
```yml
eureka:
  instance:
    hostname: localhost
    # 默认跳转链接
    status-page-url: http://${spring.cloud.client.ip-address}:${server.port}/swagger-ui.html
  client:
    serviceUrl:
      defaultZone: http://your_erueka_server_host:port/eureka/
#      defaultZone: http://${photio.security.user.name}:${photio.security.user.password}@${eureka.instance.hostname}:${server.port}/eureka/

# 需要指明spring.application.name
# 这个很重要，这在以后的服务与服务之间相互调用一般都是根据这个name
spring:
  cloud:
    consul:
      discovery:
        serviceName: ${spring.application.name}
        healthCheckPath: /health
      host: localhost
      port: 8500
  zipkin:
    base-url: http://your_zipin_server＿host:port
  sleuth:
    sampler:
      percentage: 1
  rabbitmq:
    host: your_rabbitmq_server_host_ip
    port: your_rabbitmq_server_host_port
    username: your_rabbitmq_server_user_name
    password: your_rabbitmq_server_password
  datasource:
    url: your_database_url
    username: your_database_user
    password: your_database_password
    driver-class-name: com.mysql.jdbc.Driver
  redis:
    database: 0
    host: 111.231.89.138
    port: 7000
    password: estest
# 连接超时时间（毫秒）
    timeout: 0
    timeLimit: 10
    pool:
      # 连接池最大连接数（使用负值表示没有限制）
      maxTotal: 256
      # 连接池中的最大空闲连接
      maxIdle: 64
      # 连接池中的最小空闲连接
      minIdle: 16
      # 连接池最大阻塞等待时间（使用负值表示没有限制）
      maxWaitMillis: 150000
      minEvictableIdleTimeMillis: 120000
      timeBetweenEvictionRunsMillis: 60000
    cluster:
      nodes:
        - 111.231.89.138:7000

mybatis:
  gererator:
    datasource:
      url: your_database_url
      username: your_database_user
      password: your_database_password
      driver-class-name: com.mysql.jdbc.Driver
#指定bean所在包
  type-aliases-package: me.victoriest.photio.model.entity
#指定映射文件
  mapper-locations: mapper/**/*.xml

token:
  rsa-expire-time-seconds: 900
  #移动端token失效时间，单位为秒
  expire-time-seconds: 43200
  #是否开启模拟token值
  enabled-simulate: false
  unit-test-token-value: 1
  rsa-encrypt-enabled: true

photio:

  security:
    user:
      name: name
      password: pwd

  snow-flake:
    original-timestamp: 1480166465631L
    data-center-id: 1
    machine-id: 1

```