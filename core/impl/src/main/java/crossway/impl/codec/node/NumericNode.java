package crossway.impl.codec.node;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class NumericNode extends ValueNode {
    private static final long serialVersionUID = -2520994235104800755L;

    @Override
    public abstract Number numberValue();

    @Override
    public abstract int intValue();

    @Override
    public abstract long longValue();

    @Override
    public abstract double doubleValue();

    @Override
    public abstract BigDecimal decimalValue();

    @Override
    public abstract BigInteger bigIntegerValue();

    @Override
    public abstract boolean canConvertToInt();

    @Override
    public abstract boolean canConvertToLong();
}
