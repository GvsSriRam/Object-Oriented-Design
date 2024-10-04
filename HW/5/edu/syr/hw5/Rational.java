package edu.syr.hw5;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.LinkedHashMap;

class RationalCache<K, V> extends LinkedHashMap<K, V> {
    private final int maxSize; // Maximum Cache Size

    // Constructor to initialize the cache with a maximum size and access order
    public RationalCache(int maxSize) {
        super(maxSize, 0.75f, true);  // 'true' for access-order (for LRU) - Removes least accessed entity from cache
        this.maxSize = maxSize;
    }

    // Override the removeEldestEntry method to control when to remove the eldest entry
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;  // Remove the eldest entry / least accessed if cache size exceeds maxSize
    }
}

public class Rational {
    private int numer;
    private int denom;
    private int g;
    private static final int CACHE_SIZE = 1000; // can be customized
    // Same key and Value pair i.e Same Rational as key and value
    private static final Map<Rational, Rational> cache = new RationalCache<Rational, Rational>(CACHE_SIZE);

    // constructor
    private Rational(int n, int d) {
        if (d == 0) {
            throw new IllegalArgumentException("Denominator can't be 0!");
        }
        g = gcd(n, d);
        numer = n/g;
        denom = d/g;
    }

    public static Rational getInstance(int n, int d) {
        if (d == 0) {
            throw new IllegalArgumentException("Denominator can't be 0!");
        }

        int g2 = gcd(Math.abs(n), Math.abs(d));
        n = (d < 0) ? -n : n; // Handles negative denominator cases
        d = Math.abs(d);
        n = n/g2;
        d = d/g2;

        Rational key = new Rational(n, d);
        // Check if it is already in cache
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        // If not already present, put it in the cache
        cache.put(key, key);
        return key;
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
        System.out.println("Test 1: Basic Cache Functionality");
        Rational r1 = Rational.getInstance(1, 2);
        Rational r2 = Rational.getInstance(1, 2);
        assert r1 == r2 : "Test 1 Failed: Expected the same Rational object for (1/2)";
        System.out.println("Test 1 Passed: Cache reused the same instance for (1/2)\n");

        // Test 2: Cache size limit
        System.out.println("Test 2: Cache Size Limit");
        int cacheSize = Rational.CACHE_SIZE;
        for (int i = 0; i < cacheSize + 10; i++) {
            Rational.getInstance(i, 1);
        }
        assert Rational.cache.size() == cacheSize :
                "Test 2 Failed: Cache size should be exactly " + cacheSize;
        System.out.println("Test 2 Passed: Cache maintained the maximum size of " + cacheSize + "\n");

        // Test 3: LRU Eviction Policy
        System.out.println("Test 3: LRU Eviction Policy");
        Rational a = Rational.getInstance(1, 2);
        Rational b = Rational.getInstance(3, 4);
        Rational c = Rational.getInstance(5, 6);
        Rational d = Rational.getInstance(7, 8);

        // Fill cache to force eviction of a, b, and c
        for (int i = 0; i < Rational.CACHE_SIZE - 4; i++) {
            Rational tempR = Rational.getInstance(i, 1);
        }

        Rational e = Rational.getInstance(7, 8); // e should be the same as d if d is not evicted
        assert d == e : "Test 3 Failed: Rational (7/8) should still be in the cache";

        assert !Rational.cache.containsKey(a) : "Test 3 Failed: (1/2) should have been evicted";
        assert !Rational.cache.containsKey(b) : "Test 3 Failed: (3/4) should have been evicted";
        assert !Rational.cache.containsKey(c) : "Test 3 Failed: (5/6) should have been evicted";
        System.out.println("Test 3 Passed: LRU eviction policy worked as expected\n");

        // Test 4: Negative denominator handling
        System.out.println("Test 4: Negative Denominator Handling");
        Rational r3 = Rational.getInstance(1, -2);
        Rational r4 = Rational.getInstance(-1, 2);
        assert r3 == r4 : "Test 4 Failed: Expected the same Rational object for (1/-2) and (-1/2)";
        System.out.println("Test 4 Passed: Negative denominators handled correctly\n");

        // Test 5: Zero numerator handling
        System.out.println("Test 5: Zero Numerator Handling");
        Rational r5 = Rational.getInstance(0, 2);
        Rational r6 = Rational.getInstance(0, 5);
        assert r5 == r6 : "Test 5 Failed: Expected the same Rational object for (0/2) and (0/5)";
        System.out.println("Test 5 Passed: Zero numerator handled correctly\n");

        // Test 6: Cache key equivalence for similar rational values
        System.out.println("Test 6: Cache Key Equivalence for Similar Values");
        Rational r7 = Rational.getInstance(2000, 1);
        Rational r8 = Rational.getInstance(4000, 2);
        assert r7 == r8 : "Test 6 Failed: Expected the same Rational object for (2000/1) and (4000/2)";
        System.out.println("Test 6 Passed: Similar rational values reduced to a common form\n");

        // Summary of Tests
        System.out.println("All tests passed successfully!");
    }
}

