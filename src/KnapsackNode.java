/**
 * Created by tanatanoi on 25/09/16.
 */

import java.util.ArrayList;

public class KnapsackNode {
    public final int[] counts;
    public final int weight;
    public int value = -1;
    public ArrayList<KnapsackNode> children = new ArrayList<KnapsackNode>();

    public KnapsackNode(int[] counts, int[] weights){
        this.counts = counts;
        this.weight = Main.sumOfCountedElements(this.counts, weights);
    }

    public void generateChildren(int[] weights, int maxWeight, int[] maxCounts){
        for(int i = 0; i < counts.length; i++){
            // PRUNING: Don't add children not worth checking (i.e. larger weight or at max counts)
            if(weight + weights[i] <= maxWeight && counts[i] < maxCounts[i]){
                int[] newCounts = new int[counts.length];
                System.arraycopy( counts, 0, newCounts, 0, counts.length );
                newCounts[i]++;
                children.add(new KnapsackNode(newCounts, weights));
            }
        }
    }

    public int totalValue(int[] values){
        if(value == -1) {
            value = Main.sumOfCountedElements(counts, values);
        }
        return value;
    }

    public double calculateBestPotentialValue(Item[] items, int[] maxCounts, int maxWeight){
        int[] currentCounts = new int[maxCounts.length];
        double bestFrac = 0;
        for(int i = 0; i < maxCounts.length; i++){
            if(maxCounts[i] - counts[i] > 0){
                bestFrac = items[i].fraction;
                break;
            }
        }
        return bestFrac * (maxWeight - weight);
    }
}
