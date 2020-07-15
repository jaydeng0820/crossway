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
package crossway.exception;

/**
 * SOFA RPC Exception, all rpc exception will extends it
 *
 * @author
 */
public class CrossWayException extends RuntimeException {

    private static final long serialVersionUID = -6354359417814605070L;
    /**
     * 异常类型
     */
    protected            int  errorType        = ErrorType.UNKNOWN;

    protected CrossWayException() {

    }

    public CrossWayException(int errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public CrossWayException(int errorType, Throwable cause) {
        super(cause);
        this.errorType = errorType;
    }

    public CrossWayException(int errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    public int getErrorType() {
        return errorType;
    }
}
