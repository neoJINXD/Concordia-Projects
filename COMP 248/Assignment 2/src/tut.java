import java.util.Scanner;
public class tut {
    public static void main(String[] args)
    {
//        QUESTION 2
//        int i,j,k;
//        for( i = 1; i<=3; i++)
//        {
//            for( j=0; j<(3-i);j++)
//                System.out.print(' ');
//
//            for( k=1; k<=(5-2*j);k++)
//                System.out.print('*');
//
//            System.out.println();
//        }
//        for(i=1; i<=2;i++)
//        {
//            for (j=1;j<=i;j++)
//                System.out.print(' ');
//            for (k=1; k<=5-2*i;k++)
//                System.out.print("*");
//            System.out.println();
//        }

//        QUESTION 3
//        int i, j, k;
//        Scanner input = new Scanner(System.in);
//        while (true) {
//            System.out.println("Enter an integer");
//            int user = input.nextInt();
//
//            if (user <= 2)
//            {
//                System.out.println("ERROR");
//                break;
//            }
//
//
//            for (i = 0; i < user/2+user%2; i++) {
//                for (j = 0; j < i; j++)
//                    System.out.print(' ');
//
//                for (j = 0; j < user-2*i; j++)
//                    System.out.print('*');
//
//                System.out.println();
//            }
//            int m = user/2+user%2;
//            for (i = 1; i <m; i++) {
//                for (j = 0; j <m-1-i; j++)
//                    System.out.print(' ');
//                for (k = j; k <user-j; k++)
//                    System.out.print('*');
//                System.out.println();
//            }
//        }
//        input.close();
//
//        QUESTION 5
//        int i = 0;
//        for (; ; i++)
//        {
//            if (((i%3) ==1)&&((i%5) == 2)&&((i%7) == 3))
//                break;
//        }
//        System.out.println(i);

        int i,j;
        for (i=0; i<5;i++)
        {
            for(j=i; j<5 ; j++)
            {
                System.out.print((char)(i+j));
            }
            System.out.println();
        }





    }
}
