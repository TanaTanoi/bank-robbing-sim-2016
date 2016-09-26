/**
 * Created by tanatanoi on 24/09/16.
 */
public class ValueDirection<T> {
    public enum Direction {
        NORTH,
        NORTH_WEST,
        END
    }

    public final T value;
    public final int origin_x, origin_y;
    public final boolean in_solution;

    public ValueDirection(T value, int origin_x, int origin_y, boolean in_solution){
        this.value = value;
        this.origin_x = origin_x;
        this.origin_y = origin_y;
        this.in_solution = in_solution;
    }

}
