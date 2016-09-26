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

    public static InputTuple generateZeroNInputTuple(int numberOfCases, int maxWeight){
        int[] counts = new int[numberOfCases];
        int[] weights = new int[numberOfCases];
        int[] values = new int[numberOfCases];
        for(int i = 0; i < numberOfCases; i++){
            int weight = (int)(Math.random() * maxWeight) + 1;
            int value = (int)(Math.random() * MAX_VALUE) + 1;
//            int count = (int)(Math.random() * MAX_VALUE) + 1;
            int count = 10;
            weights[i] = weight;
            values[i] = value;
            counts[i] = count;
        }
        return new InputTuple(weights, values, maxWeight, counts);
    }


    /**
     * Returns an string representing an array of times in Ruby syntax for Nyaplot.
     * @param numberOfCases
     * @return
     */
    public static String runTestCasesNumberOfElements(int numberOfCases){
        int step = 10;
        long[] times = new long[numberOfCases / step];
        for(int i = 1; i < numberOfCases; i+=step){
            InputTuple input = generateZeroOneInputTuple(i, (int)(numberOfCases * 1.5));
            System.out.println("Testing case with " + i + " elements");
            long time = System.currentTimeMillis();
            // CHANGE ALGORITHM FOR TESTING HERE
//            boolean[] result = Main.zeroOneKnapsackDynamic(input.weights, input.values, input.maxWeight);
            int[] result = Main.zeroNKnapsackGraph(input.weights, input.values, input.counts, input.maxWeight);
//            int[] result = Main.zeroNKnapsackDynamic(input.weights, input.values, input.counts, input.maxWeight);
            time = System.currentTimeMillis() - time;
            times[i / step] = time;
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
        runTestCasesNumberOfElements(100);
    }
}
