import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Fibonacci extends Thread {
    private int length;
    private long[] series;

    public Fibonacci(int length) {
        this.length = length;
    }

    public long[] getSeries() {
        if (isAlive() || getState() == State.NEW) {
            throw new RuntimeException("fibonacci has not run yet or is not finished");
        } else {
            return series.clone();
        }
    }

    @Override
    public void run() {
        // http://www.java2s.com/Book/Java/0020__Language-Basics/Create_Fibonacci_Series.htm
        series = new long[length];
        series[0] = 0;
        series[1] = 1;
        for (int i = 2; i < length; i++) {
            series[i] = series[i - 1] + series[i - 2];
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String error = "a number is required as argument";
        if (args.length == 1) {
            try {
                int length = Integer.parseInt(args[0]);
                Fibonacci fibonacci = new Fibonacci(length);
                fibonacci.start();
                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");
                System.out.println("fibonacci started at " + LocalDateTime.now().format(dateTimeFormat));
                try {
                    fibonacci.join();
                    System.out.println("fibonacci stopped at " + LocalDateTime.now().format(dateTimeFormat));
                    long[] fibonacciSeries = fibonacci.getSeries();
                    for (long number : fibonacciSeries) {
                        System.out.println("" + number);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (NumberFormatException e) {
                System.out.printf(error);
            }
        } else {
            System.out.printf(error);
        }

    }
}