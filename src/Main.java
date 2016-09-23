import com.sun.glass.ui.SystemClipboard;
import com.sun.tools.doclets.internal.toolkit.util.DocFinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static final String ZERO_ONE_FILETYPE = "01";
    private static final String ZERO_N_FILETYPE = "0n";

    public static void main(String[] args) {
        String filename = args[0];
        InputTuple input = readFile(filename);
        boolean[] result;
        result = zeroOneKnapsackBruteForce(input.weights, input.values, input.maxWeight);


        System.out.println("Optimal Items to take: ");
        System.out.println("Weight : Value");
        int sumValue = 0;
        for(int i = 0; i < result.length; i++){
            if(result[i]) {
                int value = input.values[i];
                System.out.println(input.weights[i] + " : " + value);
                sumValue += value;
            }
        }
        System.out.println("Total Value: " + sumValue);
    }


    public static InputTuple readFile(String filepath){
        try{
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);
            String type = br.readLine();
            if(type.equals(ZERO_ONE_FILETYPE)){
                return InputTuple.readZeroOneFile(br);
            }else if(type.equals(ZERO_N_FILETYPE)){
                return InputTuple.readZeroNAsZeroOne(br);
            }
        }catch(IOException e){
            System.out.println(e);
        }
        return null;
    }



    public boolean[] zeroOneKnapsackDynamic(int[] weights, int[] values, int maxWeight){
        int n = weights.length;
        boolean[] results = new boolean[n];
        return results;
    }

    /**
     * Solves the Knapsack problem by iterating over every possible combination.
     * This means its O(2^n) in terms of run time and exact time.
     * @param weights
     * @param values
     * @param maxWeight
     * @return
     */
    public static boolean[] zeroOneKnapsackBruteForce(int[] weights, int[] values, int maxWeight){
        int n = weights.length;
        int combinations = (int)Math.pow(2, n);
        int maxIndex = 0;
        int maxSum = 0;
        for(int i = 0; i < combinations; i++){
            boolean[] flags = numberToFlagArray(i, n);
            int sum = sumOfTrueElements(flags, values);
            int weight = sumOfTrueElements(flags, weights);
            if(weight > maxWeight){
                sum = 0;
            }
            if(sum > maxSum){
                maxSum = sum;
                maxIndex = i;
            }
        }
        return numberToFlagArray(maxIndex, n);
    }

    /**
     * Returns the sum of the elements in Values which correspond to True in Flags.
     * @param flags - Which elements to sum
     * @param values - Array of values containing to be summed elements
     * @return
     */
    public static int sumOfTrueElements(boolean[] flags, int[] values){
        int sum = 0;
        if(flags.length < values.length){
            throw new IllegalArgumentException("There must be more values than possible flags");
        }
        for(int i = 0; i < flags.length; i++){
            if(flags[i]){
                sum += values[i];
            }
        }
        return sum;
    }

    /**
     * Creates an array of booleans based on the binary represented by number.
     * The total size of the array depends on the second paramter, maxSize.
     * @param num - number to convert to bitflag boolean array
     * @param maxSize - the size of the array containing the number's bit flags
     * @return
     */
    public static boolean[] numberToFlagArray(int num, int maxSize){
        boolean[] bitflags = new boolean[maxSize];
        for(int i = 0; i < maxSize; ++i) {
            bitflags[i] = (num & (1 << i)) != 0;
        }
        return bitflags;
    }
}
