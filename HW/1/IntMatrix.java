package edu.syr.hw1;

public class IntMatrix {
    // initiating the rows, columns and matrix as final since we don't wish to update the values later.
    // Initiated them as private to restrict access from outside the class.
    private final int nRows;
    private final int nCols;
    private final int[] matrix;

    public IntMatrix(int numRows, int numColumns) {
        if (numRows <= 0 || numColumns <= 0) {
            throw new IllegalArgumentException("Number of rows and columns must be greater than 0");
        }
        this.nRows = numRows;
        this.nCols = numColumns;
        this.matrix = new int[numRows * numColumns];
    }

    public void validateIndices(int rowIndex, int colIndex) {
        if ((rowIndex < 0 || rowIndex >= nRows) && (colIndex < 0 || colIndex >= nCols)) {
            throw new IllegalArgumentException("Invalid row and column indices. Row: 0-" + (nRows - 1) + ", Col: 0-" + (nCols - 1));
        }
        if (rowIndex < 0 || rowIndex >= nRows) {
            throw new IllegalArgumentException("Invalid row index. Allowed range: 0-" + (nRows - 1));
        }
        if (colIndex < 0 || colIndex >= nCols) {
            throw new IllegalArgumentException("Invalid column index. Allowed range: 0-" + (nCols - 1));
        }
    }

    public int get(int rowIndex, int colIndex) {
        try {
            validateIndices(rowIndex, colIndex);
            return matrix[rowIndex * nCols + colIndex];
        } catch (IllegalArgumentException e) {
            System.err.println("Error in get() method: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public void set(int rowIndex, int colIndex, int value) {
        try {
            validateIndices(rowIndex, colIndex);
            matrix[rowIndex * nCols + colIndex] = value;
        } catch (IllegalArgumentException e) {
            System.err.println("Error in set() method: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void getMatrix() {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                System.out.print(matrix[i * nCols + j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        IntMatrix m1 = new IntMatrix(2, 3);
        m1.set(0, 0, 1);
        m1.set(0, 1, 2);
        m1.set(0, 2, 3);
        m1.getMatrix();
        m1.set(0, 3, 4);
        m1.set(1, 0, 4);
        m1.set(1, 1, 5);
        m1.set(1, 2, 6);
        m1.getMatrix();
    }
}