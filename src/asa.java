import Jama.Matrix;

import java.util.Random;

public class asa {

    //macierz eliminacji
    //macierz probeniusa
    public static void main(String[] args) {
        System.out.println("Hello World!");

        int n = 10;
        double[][] array = new double[n][n];
        double[][] b_array = new double[n][1];
        Random r = new Random();
        Matrix A = new Matrix(array);
        Matrix M = Matrix.identity(n,n);
        Matrix U = A;
        Matrix L = Matrix.identity(n,n);

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                array[i][j] = r.nextDouble();

        for (int i = 0; i < n; i++)
            b_array[i][0] = r.nextDouble();


        System.out.println("macierz A:");
        A.print(n, 3);

        Matrix b = new Matrix(b_array);
        System.out.println("wektor b:");
        b.print(n, 3);

//        M.print(n, 3);

        Matrix[] matrix_array = new Matrix[n-1];
        Matrix[] matrix_array_invert = new Matrix[n-1];
        Matrix[] matrix_array_A = new Matrix[n-1];

        for(int i = 0; i < n - 1; i++)
            matrix_array[i] = Matrix.identity(n,n);

        for(int i = 0; i < n - 1; i++)
            matrix_array_invert[i] = Matrix.identity(n,n);

        for(int i = 0; i < n - 1; i++)
            matrix_array_A[i] = A;

        //liczenie U i L
        for(int i = 0; i < n - 1; i++) {
            //Mi
            for(int j = i + 1; j < n; j++) {
                matrix_array[i].set(j, i, -U.get(j,i)/U.get(i,i));
            }
            //System.out.println("M" + i);
            //matrix_array[i].print(n,3);
            //Mi-1
            matrix_array_invert[i] = (Matrix.identity(n,n).minus(matrix_array[i])).plus(Matrix.identity(n,n));
            // U
            U = matrix_array[i].times(U);
        }
        //L
        L = matrix_array_invert[0];
        for(int i = 1; i < n - 1; i++)
            L = L.times(matrix_array_invert[i]);

//        for(int i = 0; i < n - 1; i++)
//            matrix_array[i].print(n,3);

        System.out.println("Macierz U:");
        U.print(n,3);
        System.out.println("Macierz L:");
        L.print(n, 3);

        System.out.println("Sprawdzenie norma Frobeniusa: " + (A.minus(L.times(U))).normF());

        System.out.println("Rozwiazanie rownania Ax=b");

        Matrix y = L.inverse().times(b);
        Matrix x = L.transpose().inverse().times(y);

        System.out.println("macierz x:");
        x.print(n, 3);

    }
}