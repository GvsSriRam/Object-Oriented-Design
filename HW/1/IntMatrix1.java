package edu.syr.hw1;

public class IntMatrix {
    // initiating the rows, columns and matrix as final since we don't wish to update the values later.
    private final int nRows, nCols;
    private final int[] matrix;

    public IntMatrix(int noOfRows, int noOfCols) {
        if (noOfRows <= 0 || noOfCols <= 0) {
            throw new IllegalArgumentException("Number of rows and columns must be positive integers.");
        }

        this.nRows = noOfRows;
        this.nCols = noOfCols;
        this.matrix = new int[noOfRows * noOfCols];
    }

    public boolean validateRowIndex(int row) {
        return row >= 0 && row < nRows;
    }

    public boolean validateColIndex(int col) {
        return col >= 0 && col < nCols;
    }

    private String ErrorMessage(boolean rowValidation, boolean colValidation) {
        if (!rowValidation && !colValidation) {
            return "Invalid row and column indices. Row: 0-" + (nRows - 1) + ", Col: 0-" + (nCols - 1);
        } else if (!rowValidation) {
            return "Invalid row index. Allowed range: 0-" + (nRows - 1);
        } else if (!colValidation) {
            return "Invalid column index. Allowed range: 0-" + (nCols - 1);
        } else {
            return "";
        }
    }

    private String validate(int row, int col) {
        return ErrorMessage(validateRowIndex(row), validateColIndex(col));
    }

//    public int get(int row, int col) {
//        try {
//            return matrix[row*nCols + col];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            String error = validate(row, col);
//            System.out.println(error);
//            System.out.println(e.getMessage());
//            throw new IndexOutOfBoundsException(error);
//        }
//    }

    public int get(int row, int col) {
        try {
            if (!validateIndex(row, col)) {
                throw new IndexOutOfBoundsException("Invalid indices. Row: 0-" + (nRows - 1) +
                        ", Col: 0-" + (nCols - 1));
            }
            return matrix[row * nCols + col];
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error in get() method: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

//    public void set(int row, int col, int value) {
//
//        String error = validate(row, col);
//        if (error.length() > 0) {
//            throw new ArrayIndexOutOfBoundsException(error);
//        }
//        else {
//            matrix[row*nCols + col] = value;
//        }
//    }

//    public void setSafe(int row, int col, int value) {
//        try {
//            set(row, col, value);
//        }
//        catch (ArrayIndexOutOfBoundsException e) {
//            e.printStackTrace();
//            System.out.println();
//        }
//    }

    public void set(int row, int col, int value) {
        try {
            String error = validate(row, col);
            if (error.length() > 0) {
                throw new ArrayIndexOutOfBoundsException(error);
            }
            else {
                matrix[row*nCols + col] = value;
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println();
        }
    }

    public void getMatrix() {
        System.out.print("Matrix: \n");
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                System.out.print(matrix[row*nCols + col] + " ");
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