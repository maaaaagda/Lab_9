/**
 * Created by Magdalena Polak on 26.04.2016.
 */

import java.util.*;

public class RedBlackBST<Key extends Comparable<Key>, Value> {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;
    private Node root;
    private class Node {
        private Key key;
        private Value val;         // associated data
        private Node left, right;  // links to left and right subtrees
        private boolean color;     // color of parent link
        private int N;
        private int wys;
        private int wezly;
        private int lisc;
        private int pot1;
        private int pot2;

        public Node(Key key, Value val, boolean color, int N) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.N = N;
            this.wys = wys;
        }
    }
    public RedBlackBST() {    }
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }
    private int size(Node x) {
        if (x == null) return 0;
        return x.N;
    }
    private Node search(Key value) {
        Node node = root;
        int cmp=0;
        while (node != null &&(cmp = value.compareTo(node.key))!=0)
            node = cmp < 0 ? node.left : node.right;
        return node;
    }
    public void put(Key key, Value val) {
        if (key == null) throw new NullPointerException("first argument to put() is null");


        root = put(root, key, val);
        root.color = BLACK;

    }
    private Node put(Node h, Key key, Value val) {
        if (h == null) return new Node(key, val, RED, 1);

        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left  = put(h.left,  key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else              h.val   = val;


        Node n = search(key);
        Node uncle = findUncle(key);
        Node parent = findParent(key);
        Node grandPa = findParent(findParent(key).key);
        Node father = findParent(key);
        // czerwony wujek
        mainloop:
        if(uncle != null && uncle.color == true && father.color == RED)
        {

            father.color = false;
            uncle.color = false;
            grandPa.color = true;

          return h;

        }
        // czarny wujek
        else if((uncle == null || uncle.color == false) && father.color == RED)
        {
            //prawy syn
            if(parent.right != null && parent.right.key == key && grandPa !=null)
            {
                if (isRed(h.right) && !isRed(h.left))
                     h = rotateLeft(h);

                if (isRed(h.left)  &&  isRed(h.left.left) )
                    h = rotateRight(h);
                return h;

            }
            //lewy syn
            else if (parent.left != null && parent.left.key == key && grandPa !=null)
            {
                father.color = false;
                grandPa.color = true;
                h = rotateRight(h);
                return h;
            }
        }

        return h;
    }
    protected Node findParent(Key val) {
        if(root == null) {
            return null;
        }
        Node current = root;
        Node parent = current;
        if(current.key == val) { //found
            return null;
        }
        while(true) {
            if(current == null) { //not found
                return null;
            }
            if(current.key == val) { //found
                return parent;
            }

            parent = current;
            if((current.key).compareTo(val) > 0) { //go left
                current = current.left;
            }
            else { //go right
                current = current.right;
            }
        }
    }
    protected Node findUncle(Key val)    {
        Node grandParent = findParent(findParent(val).key);
        if(grandParent == null)
            return null;
        if((grandParent.key).compareTo(val) > 0)
        {
            return grandParent.right;
        }
        else
            return grandParent.left;
    }
    private Node rotateRight(Node h) {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }
    private Node rotateLeft(Node h) {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }
    void printLevelOrder()    {
        int h = heightR(root);
        int i;
        for (i=1; i<=h; i++)
            printGivenLevel(root, i);
    }
    void printGivenLevel (Node root ,int level)    {
        if (root == null)
            return;
        if (level == 1)
            System.out.print(root.key + "("+root.color + ")");
        else if (level > 1)
        {
            printGivenLevel(root.left, level-1);
            printGivenLevel(root.right, level-1);
        }
    }
    int heightR(Node root)    {
        if (root == null)
            return 0;
        else
        {
            /* compute  height of each subtree */
            int lheight = heightR(root.left);
            int rheight = heightR(root.right);

            /* use the larger one */
            if (lheight > rheight)
                return(lheight+1);
            else return(rheight+1);
        }
    }
    int heightRoot(Node root)    {
        if (root == null)
            return 0;
        else
        {
            /* compute  height of each subtree */
            int lheight = height(root.left);
            int rheight = height(root.right);

            /* use the larger one */
            if (lheight > rheight)
                return(lheight);
            else return(rheight);
        }
    }
    int nodes(Node node)    {
        if (node == null)
            return 0;
        else   {

            int lnodes= nodes(node.left);
            int rnodes = nodes(node.right);

            return rnodes + lnodes + 1;
        }

    }int sum =0;
    int pot1 (Node node)    {
        if( node == null ) {
            return 0;
        }

       if(node.right != null && node.left == null)
        {
            return pot1(node.right) + pot1(node.left)+1;
            }
       else if(node.left != null && node.right == null)

       {  return pot1(node.right) + pot1(node.left)+1;
                     }
        else {
           return pot1(node.right) + pot1(node.left);
       }

    }
    int pot2 (Node node)    {
        if( node == null )
            return 0;

        else if( node.left != null && node.right != null ) {
           return pot2(node.left) +  pot2(node.right) +1;

        }

        else
        {
            return pot2(node.left) +  pot2(node.right);
        }
    }
    int countLeaves(Node node){
        if( node == null )
            return 0;
        if( node.left == null && node.right == null ) {
            return 1;
        } else {
            return countLeaves(node.left) + countLeaves(node.right);
        }
    }
    public void displayTree()     {
        Stack globalStack = new Stack();
        globalStack.push(root);
        int emptyLeaf = 50;
        boolean isRowEmpty = false;
        System.out.println("****..............................................................................................****");
        while(isRowEmpty==false)
        {

            Stack localStack = new Stack();
            isRowEmpty = true;

            for(int j=0; j<emptyLeaf; j++)
                System.out.print(' ');
            while(globalStack.isEmpty()==false)
            {
                Node temp = (Node) globalStack.pop();
                if(temp != null)
                {
                    temp.wys = heightRoot(temp)+1;
                    temp.wezly = nodes(temp);
                    temp.pot1 = pot1(temp);
                    temp.lisc = countLeaves(temp);
                    //System.out.print(temp.key);
                    System.out.print(temp.key+"(" + temp.color+")" + temp.wys + " " +temp.wezly + " " + temp.pot1 + " " + temp.lisc );
                    localStack.push(temp.left
                    );
                    localStack.push(temp.right);
                    if(temp.left != null ||temp.right != null)
                        isRowEmpty = false;
                }
                else
                {
                    System.out.print("    --");
                    localStack.push(null);
                    localStack.push(null);
                }
                for(int j=0; j<emptyLeaf*2-2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            emptyLeaf /= 2;
            while(localStack.isEmpty()==false)
                globalStack.push( localStack.pop() );
        }
        System.out.println("****...............................................................................................****");
    }
    public static void main(String[] args) {
        RedBlackBST<Integer, String> st = new RedBlackBST<Integer, String>();

        st.put(20, "");
        st.put(10, "");
        st.put(15, "");
        st.put(18, "");
        st.put(25, "");
        st.put(16, "");
        st.put(12, "");
       // st.put(19, "");
        st.put(9, "");


        st.printLevelOrder();
        System.out.println();
        st.displayTree();
        System.out.println(st.search(18).key);
    }

}

