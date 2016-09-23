/**
 * Created by tanatanoi on 17/09/16.
 */
public class TestCaseGenerator {

    public static final int MAX_VALUE = 10;

    public static String generateZeroOneCase(int numberOfCases, int maxWeight){
        StringBuilder sb = new StringBuilder();
        sb.append("01\n");
        sb.append(numberOfCases);sb.append("\n");
        for(int i = 0; i < numberOfCases; i++){
            int weight = (int)(Math.random() * maxWeight) + 1;
            int value = (int)(Math.random() * MAX_VALUE);
            sb.append(weight + " " + value + "\n");
        }
        return sb.toString();
    }

    public static String generateZeroNCase(int numberOfCases, int maxWeight){
        StringBuilder sb = new StringBuilder();
        sb.append("01\n");
        sb.append(numberOfCases);sb.append("\n");
        while(numberOfCases > 0){
            int count = (int)(Math.random() * numberOfCases + 1);
            numberOfCases-= count;
            int weight = (int)(Math.random() * maxWeight) + 1;
            int value = (int)(Math.random() * MAX_VALUE);
            sb.append(count + " " + weight + " " + value + "\n");
        }
        return sb.toString();
    }

    public static InputTuple generateZeroOneInputTuple(int numberOfCases, int maxWeight){
        int[] weights = new int[numberOfCases];
        int[] values = new int[numberOfCases];
        for(int i = 0; i < numberOfCases; i++){
            int weight = (int)(Math.random() * maxWeight) + 1;
            int value = (int)(Math.random() * MAX_VALUE);
            weights[i] = weight;
            values[i] = value;
        }
        return new InputTuple(weights, values, maxWeight);
    }


    /**
     * Returns an string representing an array of times in Ruby syntax for Nyaplot.
     * @param numberOfCases
     * @return
     */
    public static String runTestCases(int numberOfCases){
        long[] times = new long[numberOfCases];
        for(int i = 1; i < numberOfCases; i++){
            InputTuple input = generateZeroOneInputTuple(i, 10);
            long time = System.currentTimeMillis();
            // CHANGE ALGORITHM FOR TESTING HERE
            boolean[] result = Main.zeroOneKnapsackBruteForce(input.weights, input.values, input.maxWeight);
            time = System.currentTimeMillis() - time;
            times[i] = time;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[" + times[0]);

        for(int i = 1; i < times.length; i ++){
            sb.append(", " + times[i]);
        }
        sb.append("]");
        System.out.println(sb.toString());
        return sb.toString();
    }


    public static void main(String[] args){
        runTestCases(1);
    }
}
