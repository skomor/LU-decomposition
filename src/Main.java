import java.util.Arrays;
import java.util.Random;

import Jama.*;

public class Main {
    static int n = 10;

    public static void main(String[] args) {
        ///A nalezy do R^(n,m) n>=m a_(i,j) nalezy do rnd(<0,1>) nalezy do R
        // b nalezy do R^m          b_i nalezy do rnd (<0,1>) nalezy do R
        // G^(m,m)=A^(T)*A  => Gx=b dla najmniejszej możliwej wartości elelmentów: g_(00) i g_(m-1,m-1) przyjmując jakaś dokładność np epsilon =0,01


        Matrix tmp = Matrix.random(n, n);
        tmp.print(6, 3);
        Matrix in = Matrix.identity(n, n);
        in.print(6, 3);
        Matrix tempG = tmp.copy();
        Matrix tempD = tmp.copy();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (j < i) tempG.set(i, j, 0);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (i < j) tempD.set(i, j, 0);
            }
        }
        System.out.println("macież górna ");
        tempG.print(6, 3);
      /*  System.out.println("macież dolna ");
     tempD.print(6, 3);
        System.out.println("Składnik uwarunkowania obliczony");
        System.out.println(skladnikUwar(tmp));
        */

        Matrix wyk = invGornej(tempG);
        System.out.println("tak powinno byc");
        tempG.inverse().print(6, 3);

        Matrix wyk1 = invDolnej(tempD);
        System.out.println("tak powinno byc");
        tempD.inverse().print(6, 3);

        Matrix x = wyk.times(tempG);
       // System.out.println("po teście");
       // x.print(6, 3);
        Matrix y = wyk.inverse().times(wyk);
        System.out.println("po teście2");
        y.print(6, 3);

    }

    public static double skladnikUwar(Matrix tab) {

        double sum1 = 0;
        double sum2 = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                sum1 = tab.get(i, j) * tab.get(i, j);
            }
        }
        return Math.sqrt(sum1);
    }

    public static Matrix invGornej(Matrix m) {
        double sum = 0;
        Matrix ind = Matrix.identity(n, n);

        Matrix wynikowa = new Matrix(n, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < j; k++) {
                    sum+=wynikowa.get(i,k)*m.get(k,j);
                }
                wynikowa.set(i,j,(ind.get(i,j) - sum)/m.get(j,j));
                sum=0;
            }

        }

        System.out.println("Macież po odwróceniu");
        wynikowa.print(6, 3);
        return wynikowa;
    }


    public static Matrix invDolnej(Matrix m) {
        double sum = 0;
        Matrix ind = Matrix.identity(n, n);

        Matrix wynikowa = new Matrix(n, n);

        for (int i = n-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                for (int k = n-1; k >= j; k--) {
                    sum+=wynikowa.get(i,k)*m.get(k,j);
                }
                wynikowa.set(i,j,(ind.get(i,j) - sum)/m.get(j,j));
                sum=0;
            }

        }

        System.out.println("Dolna Macież po odwróceniu");
        wynikowa.print(6, 3);
        return wynikowa;
    }
}

/* wynikowa.set(0, 0, ind.get(0, 0) / m.get(0, 0));                                        //0,0


        wynikowa.set(0,1,(ind.get(0,1) - m.get(0,1)*wynikowa.get(0,0)
                                                        )/m.get(1,1));                                           //0,1   x(0,0)*a(0,1)/1,1


        wynikowa.set(1,1,(ind.get(2,2) - (m.get(1,0)*wynikowa.get(0,1))                   //1,1  x(0,1)*a(1,0)/1,1
                                                            )/m.get(1,1));


        wynikowa.set(2,2,(ind.get(2,2) - (wynikowa.get(0,1)*0 )                                  //2,2
                                                            )/m.get(2,2));

        wynikowa.set(3,3,(ind.get(3,3) - (wynikowa.get(0,2)*0 )                  //3,3
        )/m.get(3,3));

        wynikowa.set(0,2,(ind.get(0,2)
                - m.get(0,2)*wynikowa.get(0,0)            -   m.get(1,2) *  wynikowa.get(0,1)                                                                           //0,2!!!!!!!!!!!!!!!!!!!!!!!
        )/m.get(2,2));


        wynikowa.set(1,2,(ind.get(1,2) - m.get(1,2)*(wynikowa.get(1,1) +                  //1,2
                0
        )/m.get(2,2)));


        wynikowa.set(0, 0, ind.get(0, 0) / m.get(0, 0));
*/

