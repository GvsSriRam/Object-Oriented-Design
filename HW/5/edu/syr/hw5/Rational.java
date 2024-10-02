package edu.syr.hw5;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Rational {
    private int numer;
    private int denom;
    private int g;
    private static final int CACHE_SIZE = 1000;
    private static final TreeMap<String, Rational> cache = new TreeMap<String, Rational>();

    private Rational(int n, int d) {
        if (d == 0) {
            throw new IllegalArgumentException("Denominator can't be 0!");
        }
        g = gcd(n, d);
        numer = n/g;
        denom = d/g;
    }

    private static Rational getInstance(int n, int d) {
        if (d == 0) {
            throw new IllegalArgumentException("Denominator can't be 0!");
        }

        int g2 = gcd(Math.abs(n), Math.abs(d));
        n = (d < 0) ? -n : n; // Handles negative denominator cases
        d = Math.abs(d);
        n = n/g2;
        d = d/g2;

        String key = getStringKey(n, d);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        Rational r = new Rational(n, d);
        if (cache.size() < CACHE_SIZE){
            cache.put(key, r);
        } else {
            cache.pollFirstEntry();
            cache.put(key, r);
        }
        return r;
    }

    private static String getStringKey(int n, int d) {
        return d==1 ? n +"" : n + "/" + d;
    }

    private Rational(int n) {
        this(n, 1);
    }

    public static Rational getInstance(int n) {
        return getInstance(n, 1);
    }

    private Rational() {}

    private static int gcd(int n, int d) {
        return d==0 ? n : gcd(d, n%d);
    }

    @Override
    public String toString() {
        return getStringKey(numer, denom);
    }

    public Rational add(Rational that) {
        return getInstance(numer * that.denom + denom * that.numer, denom * that.denom);
    }

    public Rational add(int that) {
        return add(getInstance(that));
    }

    public boolean lessThan(Rational that) {
        return this.numer * that.denom < that.numer * this.denom;
    }

    public boolean lessThan(int that) {
        return lessThan(getInstance(that));
    }

    public Rational max(Rational that) {
        return this.lessThan(that) ? that : this;
    }

    public Rational max(int that) {
        return max(getInstance(that));
    }

//    public static void main(String[] args) {
//        Rational half = getInstance(1, 2);
//        Rational third = getInstance(1, 3);
//        System.out.println(half.max(third)); // returns half; prints "1/2"
//        System.out.println(third.max(half)); // returns half; prints "1/2"
//        Rational result = half.add(third);
//        Rational fiveSixths = getInstance(5, 6);
//        System.out.println("are they the same object? " + (result == fiveSixths));
//        System.out.println(fiveSixths.lessThan(result)); // false
//        System.out.println(result.lessThan(fiveSixths)); // false
//        Rational one = getInstance(1);
//        Rational oneAndHalf = one.add(half);
//        System.out.println(oneAndHalf); // prints "3/2";
//        Rational fifteenOverTen = getInstance(15, 10);
//        System.out.println(fifteenOverTen); // prints "3/2"
//    }

    public static void main(String[] args) {
        // Test 1: Basic cache functionality
        Rational r1 = Rational.getInstance(1, 2);
        Rational r2 = Rational.getInstance(1, 2);
        System.out.println("Test 1: Same object? " + (r1 == r2)); // Should be true

        // Test 2: Cache size limit
        int cacheSize = Rational.CACHE_SIZE;
        for (int i = 0; i < cacheSize + 10; i++) {
            Rational r = Rational.getInstance(i, 1);
        }
        System.out.println("Test 2: Cache size after exceeding limit: " + Rational.cache.size()); //Should be cacheSize

        // Test 3: LRU eviction (simplified)
        Rational a = Rational.getInstance(1, 2);
        Rational b = Rational.getInstance(3, 4);
        Rational c = Rational.getInstance(5, 6);
        Rational d = Rational.getInstance(7,8);
        for (int i = 0; i < Rational.CACHE_SIZE - 3; i++){
            Rational tempR = Rational.getInstance(i,1);
        }
        Rational e = Rational.getInstance(7,8); //Check if d is still there
        System.out.println("Test 3: Is d the same as e? " + (d == e)); //Should be true

        System.out.println("Test 3: Is a still there? " + Rational.cache.containsKey("1/2")); //Should be false
        System.out.println("Test 3: Is a still there? " + Rational.cache.containsKey("3/4")); //Should be false
        System.out.println("Test 3: Is a still there? " + Rational.cache.containsKey("5/6")); //Should be false

        // Test 4: Negative denominator
        Rational r3 = Rational.getInstance(1, -2);
        Rational r4 = Rational.getInstance(-1, 2);
        System.out.println("Test 4: Same object (-1/2)? " + (r3 == r4)); // Should be true

        // Test 5: Zero numerator
        Rational r5 = Rational.getInstance(0, 2);
        Rational r6 = Rational.getInstance(0, 5);
        System.out.println("Test 5: Same object (0)? " + (r5 == r6)); // Should be true
        System.out.println(r5.toString());
        System.out.println("Test 5: Same object (0)? " + Rational.cache.containsKey("0"));

        Rational r7 = Rational.getInstance(2000, 1);
        Rational r8 = Rational.getInstance(4000, 2);
        System.out.println("Test 5: Same object (0)? " + (r7 == r8)); // Should be true
        System.out.println(r7.toString());
        System.out.println(r8.toString());
        System.out.println("Test 6: Same object (2000)? " + Rational.cache.containsKey("2000"));
    }
}

