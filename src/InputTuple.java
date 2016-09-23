import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by tanatanoi on 17/09/16.
 */
public class InputTuple {
    public final int[] weights;
    public final int[] values;
    public final int maxWeight;

    public InputTuple(int[] weights, int[] values, int maxWeight){
        this.weights = weights;
        this.values = values;
        this.maxWeight = maxWeight;
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
        int numberOfItems = Integer.parseInt(line);
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
}
