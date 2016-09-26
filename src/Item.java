/**
 * Created by tanatanoi on 26/09/16.
 */
public class Item implements Comparable{
    public final int value;
    public final int weight;
    public final int count;
    public final float fraction;

    public Item(int weight, int value, int count){
        this.weight = weight;
        this.value = value;
        this.count = count;
        this.fraction = (float) value / (float) weight;
    }

    public int compareTo(Object o){
        if(o instanceof Item){
            Item other = (Item)o;
            float diff = fraction - other.fraction;
            if(diff > 0){
                return -1;
            }else if(diff < 0){
                return 1;
            }else{
                return 0;
            }
        }
        return -1;
    }
}
