package crossway.codec.json;

import crossway.codec.Serializer;
import crossway.codec.node.Node;
import crossway.exception.CrossWayRuntimeException;
import crossway.ext.api.Extension;
import crossway.impl.codec.node.ArrayNode;
import crossway.impl.codec.node.NodeFactory;
import crossway.impl.codec.node.ObjectNode;
import crossway.log.LogCodes;

import java.util.Map;

@Extension("json")
public class JsonSerializer implements Serializer {
    @Override
    public Node encode(Object object, Map<String, Object> context) {
        if (!(object instanceof String)) {
            throw new CrossWayRuntimeException(LogCodes.getLog(LogCodes.ERROR_SERIALIZER_TYPE));
        }

        return new Encode((String) object).nextValue();
    }

    @Override
    public Object decode(Node data, Map<String, Object> context) {
        return null;
    }

    private class Encode {
        private final char[] buffer;
        private       int    position;

        public Encode(String string) {
            this.buffer = string.toCharArray();
            this.position = -1;
        }

        public Node nextValue() {
            try {
                char c = this.nextToken();
                switch (c) {
                    case '{':
                        try {
                            ObjectNode objectNode = NodeFactory.instance.objectNode();
                            if (nextToken() != '}') {
                                --position;
                                while (true) {
                                    Node key = nextValue();
                                    if (nextToken() != ':') {
                                        throw new ParseException(new String(this.buffer), this.position,
                                                                 "Expected a ':' after a key");
                                    }
                                    objectNode.put(key.textValue(), nextValue());
                                    switch (nextToken()) {
                                        case ';':
                                        case ',':
                                            if (nextToken() == '}') {
                                                return objectNode;
                                            }
                                            --position;
                                            break;
                                        case '}':
                                            return objectNode;
                                        default:
                                            throw new ParseException(new String(this.buffer), this.position,
                                                                     "Expected a ',' or '}'");
                                    }
                                }

                            } else {
                                return NodeFactory.instance.missingNode();
                            }
                        } catch (ArrayIndexOutOfBoundsException ignore) {
                            throw new ParseException(new String(this.buffer), this.position, "Expected a ',' or '}'");
                        }

                    case '[':
                        try {
                            ArrayNode arrayNode = NodeFactory.instance.arrayNode();
                            if (nextToken() != ']') {
                                --position;
                                while (true) {
                                    if (nextToken() == ',') {
                                        --position;
                                        arrayNode.add(NodeFactory.instance.missingNode());
                                    } else {
                                        --position;
                                        arrayNode.add(nextValue());
                                    }
                                    switch (nextToken()) {
                                        case ',':
                                            if (nextToken() == ']') {
                                                return arrayNode;
                                            }
                                            --position;
                                            break;
                                        case ']':
                                            return arrayNode;
                                        default:
                                            throw new ParseException(new String(this.buffer), this.position,
                                                                     "Expected a ',' or ']'");
                                    }
                                }
                            } else {
                                return NodeFactory.instance.missingNode();
                            }

                        } catch (Exception ignore) {
                            throw new ParseException(new String(this.buffer), this.position, "Expected a ',' or '}'");
                        }

                    case '"': //双引号和单引号
                    case '\'':
                        StringBuffer sb = new StringBuffer();
                        while (true) {
                            char ch = this.buffer[++position];
                            switch (ch) {
                                case '\n':
                                case '\r':
                                    throw new ParseException(new String(this.buffer), this.position,
                                                             "Unterminated string");
                                case '\\':
                                    ch = this.buffer[++position];
                                    switch (ch) {
                                        case 'b':
                                            sb.append('\b');
                                            break;
                                        case 't':
                                            sb.append('\t');
                                        case 'n':
                                            sb.append('\n');
                                            break;
                                        case 'f':
                                            sb.append('\f');
                                            break;
                                        case 'r':
                                            sb.append('\r');
                                            break;
                                        case 'u': // unicode
                                            int num = 0;
                                            for (int i = 3; i >= 0; --i) {
                                                int tmp = buffer[++position];
                                                if (tmp <= '9' && tmp >= '0') {
                                                    tmp = tmp - '0';
                                                } else if (tmp <= 'F' && tmp >= 'A') {
                                                    tmp = tmp - ('A' - 10);
                                                } else if (tmp <= 'f' && tmp >= 'a') {
                                                    tmp = tmp - ('a' - 10);
                                                } else {
                                                    throw new ParseException(new String(this.buffer), this.position,
                                                                             "Illegal hex code");
                                                }
                                                num += tmp << (i * 4);
                                            }
                                            sb.append((char) num);
                                            break;
                                        case '"':
                                        case '\'':
                                        case '\\':
                                        case '/':
                                            sb.append(ch);
                                            break;
                                        default:
                                            throw new ParseException(new String(this.buffer), this.position,
                                                                     "Illegal escape.");
                                    }
                                    break;
                                default:
                                    if (ch == c) {
                                        return NodeFactory.instance.textNode(sb.toString());
                                    }
                                    sb.append(ch);
                            }
                        }
                }

                int startPosition = this.position;
                while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
                    c = this.buffer[++position];
                }
                String substr = new String(buffer, startPosition, position-- - startPosition);
                if ("true".equalsIgnoreCase(substr)) {
                    return NodeFactory.instance.booleanNode(Boolean.TRUE);
                }
                if ("false".equalsIgnoreCase(substr)) {
                    return NodeFactory.instance.booleanNode(Boolean.FALSE);
                }
                if ("null".equalsIgnoreCase(substr)) {
                    return NodeFactory.instance.nullNode();
                }

                char b = "-+".indexOf(substr.charAt(0)) < 0 ? substr.charAt(0) : substr.charAt(1);
                if (b >= '0' && b <= '9') {
                    try {
                        Long l = Long.valueOf(substr.trim());
                        if (l.intValue() == l) {
                            return NodeFactory.instance.numberNode(l.intValue());
                        }
                        return NodeFactory.instance.numberNode(l);
                    } catch (NumberFormatException exInt) {
                        try {
                            return NodeFactory.instance.numberNode(new Double(substr.trim()));
                        } catch (NumberFormatException ignore) { // NOPMD
                        }
                    }
                }
                return NodeFactory.instance.textNode(substr);
            } catch (ArrayIndexOutOfBoundsException ignore) {
                throw new ParseException(new String(this.buffer), this.position, "Unexpected end");
            }


        }

        private char nextToken() throws ArrayIndexOutOfBoundsException {
            char char1;
            while ((char1 = this.buffer[++position]) <= ' ' || char1 == '/') {
                switch (char1) {
                    case '/': //注释开始
                        char ch = this.buffer[++position];
                        switch (ch) {
                            case '/': // 单行注释
                                while (true) {
                                    ch = this.buffer[++position];
                                    if (ch == '\n') {
                                        break;
                                    }
                                }
                                break;
                            case '*': //多行注释
                                while (true) {
                                    ch = this.buffer[++position];
                                    if (ch == '*') {
                                        ch = this.buffer[++position];
                                        if (ch == '/') {
                                            break;
                                        }
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    default:
                        break;
                }
            }
            return this.buffer[position];
        }
    }
}
