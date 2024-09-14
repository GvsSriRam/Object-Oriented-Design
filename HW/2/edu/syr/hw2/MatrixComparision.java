package edu.syr.hw2;

import java.util.Random;

class Matrix1D {
    private final int nDim1;
    private final int nDim2;
    private final int nDim3;
    private final int[] matrix;

    public Matrix1D(int x, int y, int z) {
        if (x <= 0 || y <= 0 || z <= 0) {
            throw new IllegalArgumentException("Values must be greater than 0");
        }
        this.nDim1 = x;
        this.nDim2 = y;
        this.nDim3 = z;
        long totalSize = (long) x * y * z; // Use long to avoid overflow
        if (totalSize > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Invalid dimensions: Array size exceeds maximum integer value");
        }
        this.matrix = new int[(int) totalSize];
    }

    public void validateIndices(int xIndex, int yIndex, int zIndex) {
        if (xIndex < 0 || xIndex >= nDim1) {
            throw new IllegalArgumentException("Invalid x index. Allowed range: 0-" + (nDim1 - 1));
        }
        if (yIndex < 0 || yIndex >= nDim2) {
            throw new IllegalArgumentException("Invalid y index. Allowed range: 0-" + (nDim2 - 1));
        }
        if (zIndex < 0 || zIndex >= nDim3) {
            throw new IllegalArgumentException("Invalid z index. Allowed range: 0-" + (nDim3 - 1));
        }
    }

    public int get(int xIndex, int yIndex, int zIndex) {
        try {
            validateIndices(xIndex, yIndex, zIndex);
            return matrix[xIndex * nDim2 * nDim3 + yIndex * nDim3 + zIndex];
        } catch (IllegalArgumentException e) {
            System.err.println("Error in get() method: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public void set(int xIndex, int yIndex, int zIndex, int value) {
        try {
            validateIndices(xIndex, yIndex, zIndex);
            matrix[xIndex * nDim2 * nDim3 + yIndex * nDim3 + zIndex] = value;
        } catch (IllegalArgumentException e) {
            System.err.println("Error in set() method: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void getMatrix() {
        for (int i = 0; i < nDim1; i++) {
            for (int j = 0; j < nDim2; j++) {
                for (int k = 0; k < nDim3; k++) {
                    System.out.print(matrix[i * nDim2 * nDim3 + j * nDim3 + k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
    }
}

class Matrix3D {
    private final int nDim1;
    private final int nDim2;
    private final int nDim3;
    private int[][][] matrix;

    public Matrix3D(int nDim1, int nDim2, int nDim3) {
        if (nDim1 <= 0 || nDim2 <= 0 || nDim3 <= 0) {
            throw new IllegalArgumentException("Values must be greater than 0");
        }
        this.nDim1 = nDim1;
        this.nDim2 = nDim2;
        this.nDim3 = nDim3;

        if (nDim1 > Integer.MAX_VALUE || nDim2 > Integer.MAX_VALUE || nDim3 > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Invalid dimensions: Array size exceeds maximum integer value");
        }

        this.matrix = new int[nDim1][nDim2][nDim3];
    }

    public void validateIndices(int xIndex, int yIndex, int zIndex) {
        if (xIndex < 0 || xIndex >= nDim1) {
            throw new IllegalArgumentException("Invalid x index. Allowed range: 0-" + (nDim1 - 1));
        }
        if (yIndex < 0 || yIndex >= nDim2) {
            throw new IllegalArgumentException("Invalid y index. Allowed range: 0-" + (nDim2 - 1));
        }
        if (zIndex < 0 || zIndex >= nDim3) {
            throw new IllegalArgumentException("Invalid z index. Allowed range: 0-" + (nDim3 - 1));
        }
    }

    public int get(int xIndex, int yIndex, int zIndex) {
        try {
            validateIndices(xIndex, yIndex, zIndex);
            return matrix[xIndex][yIndex][zIndex];
        } catch (IllegalArgumentException e) {
            System.err.println("Error in get() method: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public void set(int xIndex, int yIndex, int zIndex, int value) {
        try {
            validateIndices(xIndex, yIndex, zIndex);
            matrix[xIndex][yIndex][zIndex] = value;
        } catch (IllegalArgumentException e) {
            System.err.println("Error in set() method: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void getMatrix() {
        for (int i = 0; i < nDim1; i++) {
            for (int j = 0; j < nDim2; j++) {
                for (int k = 0; k < nDim3; k++) {
                    System.out.print(matrix[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
    }
}

public class MatrixComparision {
    public static void main(String[] args) {
        int nTrials = 3; // Number of trials for each dimension

        int[][] dimensions = {
                {3, 4, 5},           // Small size, for initial comparison
                {30, 40, 50},        // Moderate size
                {300, 400, 500},      // Larger size
                {500, 500, 500},      // Increasing the size
                {100, 100, 1000},     //  Varying the proportions of dimensions
                {300, 300, 300},     //  Testing with more equal dimensions
                {100, 500, 500},     //  Another combination with varying dimensions
                {1000, 1000, 1000},
                {1023, 1023, 1023},
                {1024, 1024, 1024},
        };

        Random random = new Random(); // For generating random values

        for (int[] dim : dimensions) {
            int nDim1 = dim[0];
            int nDim2 = dim[1];
            int nDim3 = dim[2];

            long totalSetTime1D = 0;
            long totalSetTime3D = 0;
            long totalGetTime1D = 0;
            long totalGetTime3D = 0;

            System.out.println("\nDimensions: " + nDim1 + " x " + nDim2 + " x " + nDim3);

            boolean oneDFailed = false;
            boolean threeDFailed = false;

            for (int trial = 0; trial < nTrials; trial++) {
                // Create new matrices for each trial to avoid cached data
                try {
                    Matrix1D m1 = new Matrix1D(nDim1, nDim2, nDim3);

                    // Measure the time to set in 1D matrix
                    long startTime = System.nanoTime();
                    for (int i = 0; i < nDim1; i++) {
                        for (int j = 0; j < nDim2; j++) {
                            for (int k = 0; k < nDim3; k++) {
                                m1.set(i, j, k, random.nextInt(100)); // Set random values
                            }
                        }
                    }
                    long endTime = System.nanoTime();
                    totalSetTime1D += endTime - startTime;

                    // Measure the time to get in 1D matrix
                    startTime = System.nanoTime();
                    for (int i = 0; i < nDim1; i++) {
                        for (int j = 0; j < nDim2; j++) {
                            for (int k = 0; k < nDim3; k++) {
                                m1.get(i, j, k);
                            }
                        }
                    }
                    endTime = System.nanoTime();
                    totalGetTime1D += endTime - startTime;

                } catch (IllegalArgumentException e) {
                    System.err.println("Error creating 1D matrix: " + e.getMessage());
                    oneDFailed = true; // Mark 1D creation as failed
                } catch (OutOfMemoryError e) {
                    System.err.println("Error creating 1D matrix: Out of Memory for dimensions " + nDim1 + " x " + nDim2 + " x " + nDim3);
                    oneDFailed = true;
                }

                // Create and test 3D matrix
                try {
                    Matrix3D m2 = new Matrix3D(nDim1, nDim2, nDim3);

                    // Measure the time to set in 3D matrix
                    long startTime = System.nanoTime();
                    for (int i = 0; i < nDim1; i++) {
                        for (int j = 0; j < nDim2; j++) {
                            for (int k = 0; k < nDim3; k++) {
                                m2.set(i, j, k, random.nextInt(100)); // Set random values
                            }
                        }
                    }
                    long endTime = System.nanoTime();
                    totalSetTime3D += endTime - startTime;

                    // Measure the time to get in 3D matrix
                    startTime = System.nanoTime();
                    for (int i = 0; i < nDim1; i++) {
                        for (int j = 0; j < nDim2; j++) {
                            for (int k = 0; k < nDim3; k++) {
                                m2.get(i, j, k);
                            }
                        }
                    }
                    endTime = System.nanoTime();
                    totalGetTime3D += endTime - startTime;

                } catch (IllegalArgumentException e) {
                    System.err.println("Error creating 3D matrix: " + e.getMessage());
                    threeDFailed = true;
                    // If 3D creation fails, continue to the next trial
                } catch (OutOfMemoryError e) {
                    System.err.println("Error creating 3D matrix: Out of Memory for dimensions " + nDim1 + " x " + nDim2 + " x " + nDim3);
                    threeDFailed = true;
                }
            }


            // Calculate average times
            double avgSetTime1D = (double) totalSetTime1D / nTrials;
            double avgSetTime3D = (double) totalSetTime3D / nTrials;
            double avgGetTime1D = (double) totalGetTime1D / nTrials;
            double avgGetTime3D = (double) totalGetTime3D / nTrials;

            if (oneDFailed && threeDFailed) {
                System.out.println("Matrix of these dimensions failed for 1D & 3D");
            }
            else if (oneDFailed) {
                System.out.println("Matrix of these dimensions failed for 1D");
                System.out.println("Average Time taken to set in 3D matrix: " + avgSetTime3D + " ns");
                System.out.println("Average Time taken to get in 3D matrix: " + avgGetTime3D + " ns");
            } else if (threeDFailed) {
                System.out.println("Matrix of these dimensions failed for 3D");
                System.out.println("Average Time taken to set in 1D matrix: " + avgSetTime1D + " ns");
                System.out.println("Average Time taken to get in 1D matrix: " + avgGetTime1D + " ns");
            } else {
                System.out.println("Average Time taken to set in 1D matrix: " + avgSetTime1D + " ns");
                System.out.println("Average Time taken to get in 1D matrix: " + avgGetTime1D + " ns");
                System.out.println("Average Time taken to set in 3D matrix: " + avgSetTime3D + " ns");
                System.out.println("Average Time taken to get in 3D matrix: " + avgGetTime3D + " ns");

                // Compare the average time taken to set and get in 1D and 3D matrix
                if (avgSetTime1D < avgSetTime3D) {
                    System.out.println("Setting in 1D matrix is faster on average");
                } else {
                    System.out.println("Setting in 3D matrix is faster on average");
                }

                if (avgGetTime1D < avgGetTime3D) {
                    System.out.println("Getting in 1D matrix is faster on average");
                } else {
                    System.out.println("Getting in 3D matrix is faster on average");
                }
            }
        }
    }
}