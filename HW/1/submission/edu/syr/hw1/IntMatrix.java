package edu.syr.hw1;

public class IntMatrix {
    // Initiating the rows, columns and matrix as final since we don't wish to update the values later.
    // Initiated them as private to restrict access from outside the class.
    private final int nRows;
    private final int nCols;
    private final int[] data;

    // Constructor
    public IntMatrix(int numRows, int numColumns) {
        // Validate the input for number of rows and columns
        if (numRows <= 0 || numColumns <= 0) {
            throw new IllegalArgumentException("Number of rows and columns must be greater than 0");
        }
        this.nRows = numRows;
        this.nCols = numColumns;
        this.data = new int[numRows * numColumns];
    }

    // Validates the row and column indices to ensure they are within valid bounds.
    // Non-static as it checks indices against the specific instance's matrix size.
    // Made private since we don't want to use it outside this class
    private void validateIndices(int rowIndex, int colIndex) {
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

    // Method to get data from matrix
    // Made non-static as the matrix data is specific to an instance
    public int get(int rowIndex, int colIndex) {
        try {
            validateIndices(rowIndex, colIndex);
            return data[rowIndex * nCols + colIndex];
        } catch (IllegalArgumentException e) { // catch the exception thrown by validateIndices method
            System.err.println("Error in get() method: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    // Method to set data in matrix
    // Made non-static as the matrix data we are setting is unique to an instance
    public void set(int rowIndex, int colIndex, int value) {
        try {
            validateIndices(rowIndex, colIndex);
            data[rowIndex * nCols + colIndex] = value;
        } catch (IllegalArgumentException e) { // catch the exception thrown by validateIndices method
            System.err.println("Error in set() method: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Prints the matrix in row-column format.
    // Non-static because the matrix data is unique to the instance.
    public void getMatrix() {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                System.out.print(data[i * nCols + j] + " ");
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