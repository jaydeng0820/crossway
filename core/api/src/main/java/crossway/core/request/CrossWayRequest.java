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
package crossway.core.request;

import crossway.codec.node.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Based on RequestBase, add some extensional properties, such as requestProps
 * <p>
 * INFO: this object will create in every RPC request
 *
 * @author iamcyw
 */
public class CrossWayRequest extends RequestBase {

    private static final long serialVersionUID = 7329530374415722876L;

    /**
     * Extensional properties of request
     */
    private           Map<String, Object> requestProps;
    /**
     * 序列化类型
     */
    private transient String              serializeType;

    private Node data;

    /**
     * Gets request prop.
     *
     * @param key
     *     the key
     *
     * @return request prop
     */
    public Object getRequestProp(String key) {
        return requestProps != null ? requestProps.get(key) : null;
    }

    /**
     * Add request prop.
     *
     * @param key
     *     the key
     * @param value
     *     the value
     */
    public void addRequestProp(String key, Object value) {
        if (key == null || value == null) {
            return;
        }
        if (requestProps == null) {
            requestProps = new HashMap<String, Object>(16);
        }
        requestProps.put(key, value);
    }

    /**
     * Remove request prop.
     *
     * @param key
     *     the key
     */
    public void removeRequestProp(String key) {
        if (key == null) {
            return;
        }
        if (requestProps != null) {
            requestProps.remove(key);
        }
    }

    /**
     * Add request props.
     *
     * @param map
     *     the map
     */
    public void addRequestProps(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        if (requestProps == null) {
            requestProps = new HashMap<String, Object>(16);
        }
        requestProps.putAll(map);
    }

    /**
     * Gets request props.
     *
     * @return the request props
     */
    public Map<String, Object> getRequestProps() {
        return requestProps;
    }

    public Node getData() {
        return data;
    }

    public void setData(Node data) {
        this.data = data;
    }
}