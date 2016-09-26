import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static int COUNT = 0;

    private static final String ZERO_ONE_FILE_TYPE = "01";
    private static final String ZERO_N_FILE_TYPE = "0n";

    public enum alg {
        BRUTE,
        DYNAMIC,
        GRAPH
    }
    public static final alg type = alg.DYNAMIC;

    public static void main(String[] args) {

        String filename = args[0];
        InputTuple input = readFile(filename);
        boolean [] result = new boolean[1];
        int[] resultCount = new int[1];

        System.out.println("Running " + type);
        if(type == alg.DYNAMIC) {
            resultCount = zeroNKnapsackDynamic(input.weights, input.values, input.counts, input.maxWeight);
        }else if(type == alg.BRUTE){
            result = zeroOneKnapsackBruteForce(input.weights, input.values, input.maxWeight);
        }else if(type == alg.GRAPH){
            resultCount = zeroNKnapsackGraph(input.weights, input.values, input.counts, input.maxWeight);
        }

        System.out.println("Total Steps taken: " + Main.COUNT);
        System.out.println("Optimal Items to take: ");
        System.out.println("Weight : Value");
        int sumValue = 0;
        if( result.length > 1) {
            for (int i = 0; i < result.length; i++) {
                if (result[i]) {
                    int value = input.values[i];
                    System.out.println(input.weights[i] + " : " + value);
                    sumValue += value;
                }
            }

        }else if(resultCount.length > 1) {
            for (int i = 0; i < resultCount.length; i++) {
                sumValue += resultCount[i] * input.values[i];
                System.out.print(resultCount[i] + " ");
            }
        }
        System.out.println("Total Value: " + sumValue);
    }


    public static int[] zeroNKnapsackGraph(int[] weights, int[] values, int[] counts, int maxWeight){
        int n = counts.length;
        Item[] items = new Item[n];

        Stack<KnapsackNode> fringe = new Stack<KnapsackNode>();
        fringe.push(new KnapsackNode(new int[n], weights));

        for(int i = 0; i < n; i++){
            items[i] = new Item(weights[i], values[i], counts[i]);
        }

        Arrays.sort(items);

        KnapsackNode current = fringe.peek(), max = current;
        int maxValue = -1;

        while(!fringe.isEmpty()){
            current = fringe.pop();
            current.generateChildren(weights, maxWeight, counts);

            int value = current.totalValue(values);
            if(value > maxValue){
                maxValue = value;
                max = current;
            }

            for(KnapsackNode child : current.children){
                COUNT++;
                int v_b = child.totalValue(values);
                double v_g = child.calculateBestPotentialValue(items, counts, maxWeight);
                if ( v_b + v_g > maxValue ) {
                    fringe.push(child);
                }
            }
        }

        return max.counts;
    }

    private static InputTuple readFile(String filepath){
        try{
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);
            String type = br.readLine();
            if(type.equals(ZERO_ONE_FILE_TYPE)){
                return InputTuple.readZeroOneFile(br);
            }else if(type.equals(ZERO_N_FILE_TYPE)){
//                if(Main.type == alg.GRAPH) {
                return InputTuple.readZeroN(br);
//                }else{
//                    return InputTuple.readZeroNAsZeroOne(br);
//                }
            }
        }catch(IOException e){
            System.out.println(e);
        }
        return null;
    }


    public static int[] zeroNKnapsackDynamic(int[] weights, int[] values, int[] counts, int maxWeight){

        // Convert to Zero - One format
        int totalElements = 0;
        for(int i : counts){
            totalElements  += i;
        }

        int[] zoWeights = new int[totalElements];
        int[] zoValues = new int[totalElements];

        int znIndex = 0;
        int elementsSoFar = 0;
        for(int i = 0; i < totalElements; i++){
            COUNT++;
            zoWeights[i] = weights[znIndex];
            zoValues[i] = values[znIndex];
            if(i - elementsSoFar == counts[znIndex]){
                elementsSoFar += counts[znIndex];
                znIndex ++;
            }
        }

        // Do Zero - One Format
        boolean[] results = Main.zeroOneKnapsackDynamic(zoWeights, zoValues, maxWeight);


        // Convert back to Zero - N Format
        int[] resultsCount = new int[counts.length];
        int progress = 0;
        for(int i = 0; i < counts.length; i++){
            int itemCount = 0;
            for(int j = 0; j < counts[i]; j++){
                COUNT++;
                if(results[progress + j]) {
                    itemCount++;
                }
            }
            progress += counts[i];
            resultsCount[i] = itemCount;
        }

        return resultsCount;

    }


    public static boolean[] zeroOneKnapsackDynamic(int[] weights, int[] values, int maxWeight){
        int n = weights.length;
        // Objects versus Max Weight
        ValueDirection<Integer>[][] table = new ValueDirection[n][maxWeight];
        // Construct table
        for(int current_max_weight = 0; current_max_weight < table[0].length; current_max_weight++){
            for(int obj_index = 0; obj_index < table.length; obj_index++){
                COUNT++;
                //if first row or column
                if(current_max_weight == 0){
                    table[obj_index][current_max_weight] = new ValueDirection<Integer>(0, -1, -1, false);
                    continue;
                }
                if(obj_index == 0){
                    if(weights[obj_index] < maxWeight) {
                        table[obj_index][current_max_weight] = new ValueDirection<Integer>(values[obj_index], -1, -1, true);
                    }else{
                        table[obj_index][current_max_weight] = new ValueDirection<Integer>(0, -1, -1, false);
                    }
                    continue;
                }
                // if other rows
                int value_1, value_2;
                value_1 = table[obj_index - 1][current_max_weight].value;
                try {
                    // God this is awful, but I don't know if I care that much
                    value_2 = table[obj_index - 1][current_max_weight - weights[obj_index]].value + values[obj_index];
                }catch(ArrayIndexOutOfBoundsException e){
                    value_2 = -1;
                }
                if( value_1 > value_2){
                    table[obj_index][current_max_weight] = new ValueDirection<Integer>(value_1, obj_index - 1, current_max_weight, false);
                }else{
                    table[obj_index][current_max_weight] = new ValueDirection<Integer>(value_2, obj_index - 1, current_max_weight - weights[obj_index], true);
                }
            }
        }


        // Backtrack to find select elements
        boolean[] results = new boolean[n];
        ValueDirection<Integer> current_node = new ValueDirection<Integer>(0, values.length - 1, maxWeight - 1, false);

        while(current_node.origin_x != -1 && current_node.origin_y != -1){
            COUNT++;
            ValueDirection<Integer> next = table[current_node.origin_x][current_node.origin_y];
            if(next.in_solution){
                results[current_node.origin_x] = true;
            }
            current_node = next;
        }
        System.out.println("");
        return results;
    }

    /**
     * Solves the Knapsack problem by iterating over every possible combination.
     * This means its O(2^n) in terms of run time and exact time.
     * @param weights Array of weights
     * @param values Array of values, with the weights
     * @param maxWeight Max weight of the knapsack
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

    public static int sumOfCountedElements(int[] counts, int[] values){
        int sum = 0;
        for(int i = 0; i < counts.length; i++){
            sum+= counts[i] * values[i];
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
