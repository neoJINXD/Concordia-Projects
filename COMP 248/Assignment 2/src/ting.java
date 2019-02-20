import java.util.Scanner;
public class ting {
    public static void main (String[] args)
    {

        Scanner input = new Scanner(System.in);
        int count=0, length, number =0;
        double average, sum=0, mark;

        System.out.println("How many students? ");
        length = input.nextInt();

        double[] marks = new double[length];


        for (;count<length;count++)
        {
            System.out.print("Please enter mark nb " + (count+1) + ": ");
            mark = input.nextDouble();
            sum += mark;
            marks[count] = mark;
            System.out.println();
        }
        average = sum/length;
        System.out.println("The average is: " + average);

        for (int i =0 ; i < marks.length ; i++)
        {
            if (marks[i]>average)
                number +=1;
        }

        System.out.println(number+ " students have a mark higher than everage.");
    }
}
