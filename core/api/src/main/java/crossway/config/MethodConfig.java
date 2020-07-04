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
package crossway.config;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 方法级配置
 *
 * @author iamcyw
 */
public class MethodConfig implements Serializable {

    /**
     * The Parameters. 自定义参数
     */
    protected Map<String, String> parameters;
    /**
     * The Timeout. 远程调用超时时间(毫秒)
     */
    protected Integer timeout;
    /**
     * The Retries. 失败后重试次数
     */
    protected Integer retries;
    private String name;

    /**
     * Gets parameters.
     *
     * @return the parameters
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Sets parameters.
     *
     * @param parameters
     *     the parameters
     */
    public MethodConfig setParameters(Map<String, String> parameters) {
        if (this.parameters == null) {
            this.parameters = new ConcurrentHashMap<String, String>();
            this.parameters.putAll(parameters);
        }
        return this;
    }

    /**
     * Sets parameter.
     *
     * @param key
     *     the key
     * @param value
     *     the value
     */
    public MethodConfig setParameter(String key, String value) {
        if (parameters == null) {
            parameters = new ConcurrentHashMap<String, String>();
        }
        parameters.put(key, value);
        return this;
    }

    /**
     * Gets parameter.
     *
     * @param key
     *     the key
     *
     * @return the value
     */
    public String getParameter(String key) {
        return parameters == null ? null : parameters.get(key);
    }
}
