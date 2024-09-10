package edu.syr.hw2;

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
        this.matrix = new int[x * y * z];
    }

    public void validateIndices(int xIndiex, int yIndex, int zIndex) {
        if (xIndiex < 0 || xIndiex >= nDim1) {
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
        this.nDim1 = nDim1;
        this.nDim2 = nDim2;
        this.nDim3 = nDim3;
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
        try{
            validateIndices(xIndex, yIndex, zIndex);
            return matrix[xIndex][yIndex][zIndex];
        }
        catch (IllegalArgumentException e) {
            System.err.println("Error in get() method: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public void set(int xIndex, int yIndex, int zIndex, int value) {
        try{
            validateIndices(xIndex, yIndex, zIndex);
            matrix[xIndex][yIndex][zIndex] = value;
        }
        catch (IllegalArgumentException e) {
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
        Matrix1D m1 = new Matrix1D(3, 3, 3);
        Matrix3D m2 = new Matrix3D(3, 3, 3);

        // Measure the time to set in 1D matrix
        long startTime = System.nanoTime();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    m1.set(i, j, k, i + j + k);
                }
            }
        }
        long endTime = System.nanoTime();

        System.out.println("Time taken to set in 1D matrix: " + (endTime - startTime) + " ns");

        long setTime1D = endTime - startTime;

        // Measure the time to set in 3D matrix
        startTime = System.nanoTime();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    m2.set(i, j, k, i + j + k);
                }
            }
        }
        endTime = System.nanoTime();

        System.out.println("Time taken to set in 3D matrix: " + (endTime - startTime) + " ns");

        long setTime3D = endTime - startTime;

        // Measure the time to get in 1D matrix
        startTime = System.nanoTime();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    m1.get(i, j, k);
                }
            }
        }
        endTime = System.nanoTime();

        System.out.println("Time taken to get in 1D matrix: " + (endTime - startTime) + " ns");

        long getTime1D = endTime - startTime;

        // Measure the time to get in 3D matrix
        startTime = System.nanoTime();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    m2.get(i, j, k);
                }
            }
        }
        endTime = System.nanoTime();

        System.out.println("Time taken to get in 3D matrix: " + (endTime - startTime) + " ns");

        long getTime3D = endTime - startTime;

        // Compare the time taken to set and get in 1D and 3D matrix and say which is faster
        if (setTime1D < setTime3D) {
            System.out.println("Setting in 1D matrix is faster");
        } else {
            System.out.println("Setting in 3D matrix is faster");
        }

        if (getTime1D < getTime3D) {
            System.out.println("Getting in 1D matrix is faster");
        } else {
            System.out.println("Getting in 3D matrix is faster");
        }

        // Result for conclusion - comparision & explanation & avg of 3 trials
    }
}