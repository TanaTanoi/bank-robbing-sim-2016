import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by tanatanoi on 17/09/16.
 */
public class InputTuple {
    public final int[] weights;
    public final int[] values;
    public final int maxWeight;
    public final int[] counts;


    // Init 0 1
    public InputTuple(int[] weights, int[] values, int maxWeight){
        this.weights = weights;
        this.values = values;
        this.maxWeight = maxWeight;
        this.counts = new int[weights.length];
        java.util.Arrays.fill(this.counts, 1);
    }

    // Init 0 N
    public InputTuple(int[] weights, int[] values, int maxWeight, int counts[]){
        this.weights = weights;
        this.values = values;
        this.maxWeight = maxWeight;
        this.counts = counts;
    }


    public static InputTuple readZeroOneFile(BufferedReader br) throws IOException {
        String line = br.readLine();
        int maxWeight = Integer.parseInt(line);
        line = br.readLine();
        int numberOfItems = Integer.parseInt(line);
        int[] weights = new int[numberOfItems];
        int[] values = new int[numberOfItems];

        for(int i = 0; i < numberOfItems; i++){
            line = br.readLine();
            String[] elements = line.split(" ");
            weights[i] = Integer.parseInt(elements[0]);
            values[i] = Integer.parseInt(elements[1]);
        }

        return new InputTuple(weights, values, maxWeight);
    }


    public static InputTuple readZeroNAsZeroOne(BufferedReader br) throws IOException{
        String line = br.readLine();
        int maxWeight = Integer.parseInt(line);
        line = br.readLine();
        int numberOfItems = Integer.parseInt(line.split(" ")[0]);
        int[] weights = new int[numberOfItems];
        int[] values = new int[numberOfItems];

        for(int i = 0; i < numberOfItems; i++){
            line = br.readLine();
            String[] elements = line.split(" ");

            int count = Integer.parseInt(elements[0]);
            for(int j = 0; j < count; j++) {
                weights[i + j] = Integer.parseInt(elements[1]);
                values[i + j] = Integer.parseInt(elements[2]);

            }
            i+= count;
        }

        return new InputTuple(weights, values, maxWeight);

    }

    public static InputTuple readZeroN(BufferedReader br) throws IOException{
        String line = br.readLine();
        int maxWeight = Integer.parseInt(line);
        line = br.readLine();
        int numberOfItems = Integer.parseInt(line.split(" ")[1]);
        int[] weights = new int[numberOfItems];
        int[] values = new int[numberOfItems];
        int[] counts = new int[numberOfItems];

        for(int i = 0; i < numberOfItems; i++){
            line = br.readLine();
            String[] elements = line.split(" ");

            counts[i] = Integer.parseInt(elements[0]);
            weights[i] = Integer.parseInt(elements[1]);
            values[i] = Integer.parseInt(elements[2]);
        }

        return new InputTuple(weights, values, maxWeight, counts);
    }
}
