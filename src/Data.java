/**
 * Created by Magdalena Polak on 26.04.2016.
 */
public class Data {
    int height;
    int keys;
    int leaves;
    public Data(int height, int keys, int leaves)
    {
        this.height = height;
        this.keys = keys;
        this.leaves = leaves;
    }

    @Override
    public String toString() {
        return  "Wysokość:  " + height + "Węzły: " + keys + "Liście: " + leaves;
    }
}
