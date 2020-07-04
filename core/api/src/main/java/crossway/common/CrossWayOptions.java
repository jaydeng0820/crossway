/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package crossway.common;

/**
 * 配置项关键字
 *
 * @author iamcyw
 */
public class CrossWayOptions {
    /**
     * 决定本配置文件的加载顺序，越大越往后加载
     */
    public static final String RPC_CFG_ORDER       = "rpc.config.order";
    /**
     * 日志默认实现
     */
    public static final String LOGGER_IMPL         = "logger.impl";
    /**
     * 扩展点加载的路径
     */
    public static final String EXTENSION_LOAD_PATH = "extension.load.path";
    /**
     * 需要被加载的模块列表，多个用逗号隔开
     *
     * @since 5.3.0
     */
    public static final String MODULE_LOAD_LIST    = "module.load.list";

    /**
     * 系统cpu核数
     */
    public static final String SYSTEM_CPU_CORES          = "system.cpu.cores";
    /**
     * 是否允许线程上下文携带自定义参数，关闭后，可能tracer等会失效，但是可以提高性能
     */
    public static final String CONTEXT_ATTACHMENT_ENABLE = "context.attachment.enable";
    /**
     * 是否主动监听JVM关闭事件，默认true
     */
    public static final String JVM_SHUTDOWN_HOOK         = "jvm.shutdown.hook";

    /**
     * 默认服务uniqueId
     */
    public static final String DEFAULT_UNIQUEID      = "default.uniqueId";
    /**
     * 默认序列化
     */
    public static final String DEFAULT_SERIALIZATION = "default.serialization";
    /**
     * 默认字符集 utf-8
     */
    public static final String DEFAULT_CHARSET       = "default.charset";

    public static final String DEFAULT_LISTENER = "default.listener";

    public static final String DEFAULT_SEND = "default.send";

    public static final String DEFAULT_BOOTSTRAP_SEND = "default.bootstrap_send";

    public static final String DEFAULT_BOOTSTRAP_LISTENER = "default.bootstrap_listener";

    /**
     * 默认启动端口，包括不配置或者随机，都从此端口开始计算
     */
    public static final String SERVER_PORT_START     = "server.port.start";
    /**
     * 默认启动端口，包括不配置或者随机，都从此端口开始计算
     */
    public static final String SERVER_PORT_END       = "server.port.end";
    /**
     * 默认发布路径
     */
    public static final String SERVER_CONTEXT_PATH   = "server.context.path";
    /**
     * 默认io线程大小，推荐自动设置
     */
    public static final String SERVER_IOTHREADS      = "server.ioThreads";
    /**
     * 默认服务端业务线程池类型
     */
    public static final String SERVER_POOL_TYPE      = "server.pool.type";
    /**
     * 默认服务端业务线程池最小
     */
    public static final String SERVER_POOL_CORE      = "server.pool.core";
    /**
     * 默认服务端业务线程池最大
     */
    public static final String SERVER_POOL_MAX       = "server.pool.max";
    /**
     * 默认服务端业务线程池是否初始化核心线程池
     */
    public static final String SERVER_POOL_PRE_START = "server.pool.pre.start";
    /**
     * 是否hold住端口，true的话随主线程退出而退出，false的话则要主动退出
     */
    public static final String SERVER_DAEMON         = "server.daemon";
    /**
     * 端口是否自适应，如果当前端口被占用，自动+1启动
     */
    public static final String SEVER_ADAPTIVE_PORT   = "server.adaptive.port";
    /**
     * 服务端是否自动启动
     */
    public static final String SEVER_AUTO_START      = "server.auto.start";
    /**
     * 服务端关闭超时时间
     */
    public static final String SERVER_STOP_TIMEOUT   = "server.stop.timeout";

    /**
     * 默认权重
     */
    public static final String PROVIDER_WEIGHT = "provider.weight";

    /**
     * 默认负载均衡算法
     */
    public static final String CONSUMER_LOAD_BALANCER = "consumer.loadBalancer";
    /**
     * 默认失败重试次数
     */
    public static final String CONSUMER_RETRIES       = "consumer.retries";
    /**
     * 默认不延迟加载
     */
    public static final String CONSUMER_LAZY          = "consumer.lazy";

    /**
     * 自定义设置：序列化是否检测循环引用类型
     */
    public static final String SERIALIZE_CHECK_REFERENCE = "serialize.check.reference";
    /**
     * 自定义设置：检查系统时间（针对linux）
     */
    public static final String CHECK_SYSTEM_TIME         = "check.system.time";
}
