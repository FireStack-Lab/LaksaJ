package com.firestack.laksaj.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Zilliqa unit conversion functions.
 */
public final class Convert {
  private Convert() { }

  public static BigDecimal fromQa(String number, Unit unit) {
    return fromQa(new BigDecimal(number), unit);
  }

  public static BigDecimal fromQa(BigDecimal number, Unit unit) {
    return number.divide(unit.getQaFactor());
  }

  public static BigInteger toQa(String number, Unit unit) {
    return toQa(new BigDecimal(number), unit);
  }

  public static BigInteger toQa(BigDecimal number, Unit unit) {
    return number.multiply(unit.getQaFactor()).toBigInteger();
  }

  public enum Unit {
    Qa("qa", 0),
    Li("li", 6),
    ZIL("zil", 12);

    private String name;
    private BigDecimal qaFactor;

    Unit(String name, int factor) {
      this.name = name;
      this.qaFactor = BigDecimal.TEN.pow(factor);
    }

    public BigDecimal getQaFactor() {
      return qaFactor;
    }

    @Override
    public String toString() {
      return name;
    }

    public static Unit fromString(String name) {
      if (name != null) {
        for (Unit unit : Unit.values()) {
          if (name.equalsIgnoreCase(unit.name)) {
            return unit;
          }
        }
      }
      return Unit.valueOf(name);
    }
  }
}
