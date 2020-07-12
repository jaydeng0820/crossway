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
package crossway.core.response;

import crossway.codec.node.Node;
import crossway.exception.CrossWayException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Sofa RPC Response class
 *
 * @author iamcyw
 */
public final class CrossWayResponse implements Serializable {

    private static final long serialVersionUID = -4364536436151723421L;

    /**
     * 框架异常
     */
    private boolean isError = false;

    /**
     * 框架异常的消息
     */
    private String errorMsg;

    private CrossWayException error;

    /**
     * 业务返回
     */
    private Node data;

    /**
     * extensional properties
     */
    private Map<String, String> responseProps;

    //====================== 下面是非传递属性 ===============

    /**
     * 序列化类型
     */
    private transient byte serializeType;

    /**
     * Is error boolean.
     *
     * @return the boolean
     */
    public boolean isError() {
        return isError;
    }

    /**
     * Gets error msg.
     *
     * @return the error msg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Sets error msg.
     *
     * @param error
     *     the error
     */
    public void setErrorMsg(String error) {
        if (error == null) {
            return;
        }
        errorMsg = error;
        isError = true;
    }

    /**
     * Gets response prop.
     *
     * @param key
     *     the key
     *
     * @return the response prop
     */
    public Object getResponseProp(String key) {
        return responseProps == null ? null : responseProps.get(key);
    }

    /**
     * Add response prop.
     *
     * @param key
     *     the key
     * @param value
     *     the value
     */
    public void addResponseProp(String key, String value) {
        if (responseProps == null) {
            responseProps = new HashMap<String, String>(16);
        }
        if (key != null && value != null) {
            responseProps.put(key, value);
        }
    }

    /**
     * Remove response props.
     *
     * @param key
     *     the key
     */
    public void removeResponseProp(String key) {
        if (responseProps != null && key != null) {
            responseProps.remove(key);
        }
    }

    /**
     * Gets response props.
     *
     * @return the response props
     */
    public Map<String, String> getResponseProps() {
        return responseProps;
    }

    /**
     * Sets response props.
     *
     * @param responseProps
     *     the response props
     */
    public void setResponseProps(Map<String, String> responseProps) {
        this.responseProps = responseProps;
    }

    /**
     * Gets serialize type.
     *
     * @return the serialize type
     */
    public byte getSerializeType() {
        return serializeType;
    }

    /**
     * Sets serialize type.
     *
     * @param serializeType
     *     the serialize type
     *
     * @return the serialize type
     */
    public CrossWayResponse setSerializeType(byte serializeType) {
        this.serializeType = serializeType;
        return this;
    }

    public Exception getError() {
        return error;
    }

    public void setError(CrossWayException error) {
        this.isError = true;
        this.error = error;
    }

    public Node getData() {
        return data;
    }

    public void setData(Node data) {
        this.data = data;
    }
}