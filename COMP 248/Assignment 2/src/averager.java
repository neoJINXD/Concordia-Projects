import java.util.Scanner;
public class averager {

    public static void main (String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        double input = 1, sum = 0, count = 0;
        System.out.println("Keep entering marks, negative number to stop");
        while (true)
        {
            input = keyboard.nextDouble();
            if (input < 0)
                break;

            sum += input;
            ++count;
        }

        if (sum ==0)
            System.out.println("No average calculable");

        else
            System.out.println("Average: "+ (sum/count));
    }
}
