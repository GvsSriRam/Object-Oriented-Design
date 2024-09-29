package edu.syr.hw4;

/* Adapted from Programming in Scala, Odersky */

public class Rational {
    private int numer;
    private int denom;
    private int g;

    public Rational(int n, int d) { /*implement this*/
        if(d==0) {
            throw new ArithmeticException("Denominator cannot be zero");
        }
        this.g = computeGCD(Math.abs(n), Math.abs(d));
        n = (d < 0) ? -n : n; // Handles negative denominator cases
        d = Math.abs(d);
        // Normalize the rational number
        this.numer = n / this.g;
        this.denom = d / this.g;
    }

    public Rational(int n) {/*implement this*/
        this.numer = n;
        this.denom = 1; // Setting denominator = 1
        this.g = 1; // GCD will be 1, no further computation necessary for normalization
    }

    private static int computeGCD (int n, int d) {
        if(n == 0) return d;
        if(d == 0) return n;
        return computeGCD(d, n % d);
    }

    @Override
    public String toString() {/*implement this*/
        StringBuilder sb = new StringBuilder();
        sb.append(this.numer);
        if (this.denom != 1) { // If denominator is 1, display it as integer value
            sb.append("/").append(this.denom);
        };
        return sb.toString();
    }

    public Rational add(Rational that) {/*implement this*/
        if (this.numer == 0) {
            return that;
        } else if (that.numer == 0) {
            return this;
        }
        return new Rational(this.numer * that.denom + this.denom * that.numer, this.denom * that.denom);
    }

    public Rational add(int that) {/*implement this*/
        if (this.numer == 0) {
            return new Rational(that);
        } else if (that == 0) {
            return this;
        }
        return new Rational(this.numer + that*this.denom, this.denom);
    }

    public boolean lessThan(Rational that) {/*implement this*/
        return this.numer * that.denom < this.denom * that.numer;
    }

    public boolean lessThan(int that) {/*implement this*/
        return this.numer < that * this.denom;
    }

    // Equals method to verify any rational or integer value is same as this rational
    // We ignore GCD value for this
    // Overriding this method is out of scope of this assignment
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o==null) {
            return false;
        }

        if (o instanceof Integer) {
            int that = (Integer) o;
            return this.numer == that && this.denom == 1;
        }

        if (o instanceof Rational) {
            Rational that = (Rational) o;
            return this.numer == that.numer && this.denom == that.denom;
        }

        return false;
    }

    // Overriding this method is out of scope of this assignment
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.numer;
        result = 31 * result + this.denom;
        return result;
    }

//    public Rational max(Rational that) {/*implement this*/
//        if (this.equals(that)) {
//            return this;
//        }
//        else if (this.lessThan(that)) {
//            return that;
//        }
//        else {
//            return this;
//        }
//    }
//
//    public Rational max(int that) {/*implement this*/
//        if (this.equals(that)) {
//            return this;
//        }
//        else if (this.lessThan(that)) {
//            return new Rational(that, 1);
//        }
//        else {
//            return this;
//        }
//    }

    public Rational max(Rational that) {
        return this.lessThan(that) ? that : this;
    }

    public Rational max(int that) {
        return this.lessThan(that) ? new Rational(that) : this;
    }

//    public static void main(String[] args) {
//        Rational half = new Rational(1, 2);
//        Rational third = new Rational(1, 3);
//        System.out.println(half.max(third)); // returns half; prints "1/2"
//        System.out.println(third.max(half)); // returns half; prints "1/2"
//        Rational result = half.add(third);
//        Rational fiveSixths = new Rational(5, 6);
//        System.out.println("are they the same object? " + (result == fiveSixths));
//        System.out.println(fiveSixths.lessThan(result)); // false
//        System.out.println(result.lessThan(fiveSixths)); // false
//        Rational one = new Rational(1);
//        Rational oneAndHalf = one.add(half);
//        System.out.println(oneAndHalf); // prints "3/2";
//        Rational fifteenOverTen = new Rational(15, 10);
//        System.out.println(fifteenOverTen); // prints "3/2"
//    }

    public static void main(String[] args) {
        // Test case 1: Zero numerator
        Rational zeroNumerator = new Rational(0, 5);
        System.out.println("test case 1");
        System.out.println(zeroNumerator); // prints "0"
        assert zeroNumerator.equals(new Rational(0)) : "Zero numerator should simplify to 0";

        // Test case 2: Zero denominator should throw exception
        System.out.println("test case 2");
        try {
            Rational invalidRational = new Rational(5, 0);
            assert false : "Should have thrown ArithmeticException";
        } catch (ArithmeticException e) {
            System.out.println("Caught expected ArithmeticException for zero denominator");
        }

        // Test case 3: Negative numerator
        System.out.println("test case 3");
        Rational negativeNumerator = new Rational(-3, 5);
        System.out.println(negativeNumerator); // prints "-3/5"
//        System.out.println(negativeNumerator.toString());
//        System.out.println(negativeNumerator.numer);
//        System.out.println(negativeNumerator.denom);
//        System.out.println(negativeNumerator.g);
        assert negativeNumerator.equals(new Rational(-3, 5));

        // Test case 4: Negative denominator
        System.out.println("test case 4");
        Rational negativeDenominator = new Rational(3, -5);
        System.out.println(negativeDenominator); // prints "-3/5"
        assert negativeDenominator.equals(new Rational(-3, 5));

        // Test case 5: Both numerator and denominator negative
        System.out.println("test case 5");
        Rational bothNegative = new Rational(-3, -5);
        System.out.println(bothNegative); // prints "3/5"
        assert bothNegative.equals(new Rational(3, 5));

        // Test case 6: Zero numerator and denominator
        System.out.println("test case 6");
        Rational zero = new Rational(0);
        System.out.println(zero); // prints "0/1"
        assert zero.equals(new Rational(0, 1));

        // Test case 7: Rational number simplification
        System.out.println("test case 7");
        Rational simplified = new Rational(15, 10);
        System.out.println(simplified); // prints "3/2"
        assert simplified.equals(new Rational(3, 2)) : "Should be simplified to 3/2";

        // Test case 8: Equality for simplified values
        System.out.println("test case 8");
        Rational sameAsSimplified = new Rational(30, 20);
        assert sameAsSimplified.equals(simplified) : "30/20 and 15/10 should both simplify to 3/2";

        // Test case 9: Addition with zero
        System.out.println("test case 9");
        Rational result = zero.add(new Rational(3, 5));
        System.out.println(result); // prints "3/5"
        assert result.equals(new Rational(3, 5));

        // Test case 10: Adding negative and positive numbers
        System.out.println("test case 10");
        Rational negativeAddition = new Rational(-1, 3).add(new Rational(2, 3));
        System.out.println(negativeAddition); // prints "1/3"
        assert negativeAddition.equals(new Rational(1, 3));

        // Test case 11: Comparison lessThan with negative numbers
        System.out.println("test case 11");
        Rational negOneHalf = new Rational(-1, 2);
        Rational oneThird = new Rational(1, 3);
        System.out.println(negOneHalf.lessThan(oneThird)); // prints "true"
        assert negOneHalf.lessThan(oneThird) : "-1/2 should be less than 1/3";

        // Test case 12: Comparison with zero
        System.out.println("test case 12");
        Rational half = new Rational(1, 2);
        System.out.println(half.lessThan(zero)); // prints "false"
        assert !half.lessThan(zero) : "1/2 should not be less than 0";

        // Test case 13: Comparison equals with positive and negative values
        System.out.println("test case 13");
        Rational negThreeFourths = new Rational(-3, 4);
        Rational negThreeFourthsAgain = new Rational(6, -8); // should be equal to -3/4
        System.out.println(negThreeFourths.equals(negThreeFourthsAgain)); // prints "true"
        assert negThreeFourths.equals(negThreeFourthsAgain) : "-3/4 should be equal to 6/-8";

        // Test case 14: Add fractions that simplify to the same value
        System.out.println("test case 14");
        Rational addSimplified = new Rational(1, 4).add(new Rational(1, 4));
        System.out.println(addSimplified); // prints "1/2"
        assert addSimplified.equals(new Rational(1, 2)) : "1/4 + 1/4 should be 1/2";

        // Test case 15: Max of two numbers
        System.out.println("test case 15");
        Rational maxRational = new Rational(2, 5).max(new Rational(3, 5));
        System.out.println(maxRational); // prints "3/5"
        assert maxRational.equals(new Rational(3, 5)) : "Max of 2/5 and 3/5 should be 3/5";

        // Test case 16: Max with negative values
        System.out.println("test case 16");
        Rational maxNegative = new Rational(-3, 5).max(new Rational(2, 5));
        System.out.println(maxNegative); // prints "2/5"
        assert maxNegative.equals(new Rational(2, 5)) : "Max of -3/5 and 2/5 should be 2/5";

        // Test case 17: zero numerator equals another zero numerator
        System.out.println("test case 17");
        Rational zero1 = new Rational(0, 5);
        Rational zero2 = new Rational(0, 7);
        Rational zero3 = new Rational(0);
        System.out.println(zero1.equals(zero2));
        System.out.println(zero1.equals(zero3));
        System.out.println(zero2.equals(0));
    }
}

