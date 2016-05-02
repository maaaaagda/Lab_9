/**
 * Created by Magdalena Polak on 26.04.2016.
 */
public class Data {
    int height;
    int keys;
    int leaves;
    public Data(int height, int keys, int leaves, int cos)
    {
        this.height = height;
        this.keys = keys;
        this.leaves = leaves;
    }

    @Override
    public String toString() {
        return  "Wysokość:  " + height + "Węzły: " + keys + "Liście: " + leaves;
    }
/*
    rbt.insert(20);
    rbt.insert(10);
    rbt.insert(15);
    //    rbt.insert(18);
    //    rbt.insert(25);
    //    rbt.insert(16);
    //rbt.insert(12);   */
}
