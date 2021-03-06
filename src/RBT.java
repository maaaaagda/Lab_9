import java.util.Stack;

/**
 * Created by Magdalena Polak on 26.04.2016.
 */

public class RBT<Elem extends Comparable<Elem>> {
    static final boolean RED=true;
    static final boolean BLACK =false;
    int allNodes = 1;
    private Node _root;

    public RBT()
    { _root=null;}

    private boolean isRed( Node x)    {return x!= null && x.color==RED;}
    public Elem find(Elem x)    {Node t = search(x);
        return t !=null ? t.value : null;
    }
    private Node search(Elem value) {
        Node node = _root;
        int cmp=0;
        while (node != null &&(cmp = value.compareTo(node.value))!=0)
            node = cmp < 0 ? node.left : node.right;
        return node;
    }
    private Node rotateL(Node t)    {
        Node x=t.right;
        t.right = x.left;
        x.left=t;
        x.color=t.color;
        t.color=RED;
        return x; }
    private Node rotateR(Node t)    { Node x=t.left;
        t.left=x.right;
        x.right=t;
        x.color=t.color;
        t.color=RED;
        return x; }


    private void colorFlip(Node t)
    {t.color=!t.color; t.left.color=!t.left.color; t.right.color=!t.right.color; }
  /*  public void insert(Elem x)
    { _root= insert(x,_root);
        _root.color=BLACK;
    allNodes++;}
    protected Node insert(Elem x, Node t) {
        if(t== null) t=new Node(x);
        else { int cmp=x.compareTo(t.value);
            if(isRed(t.left) && isRed(t.right))
                colorFlip(t);
            if(cmp<0) t.left=insert(x,t.left);
            else if(cmp>0) t.right=insert(x,t.right);
            else throw new RuntimeException("Duplicate  "+x.toString());
            t=fixUp(t);
        }
        return t;
    }   */
    protected Node findParent(Elem val) {
        if(_root == null) {
            return null;
        }
        Node current = _root;
        Node parent = current;
        if(current.value == val) { //found
            return null;
        }
        while(true) {
            if(current == null) { //not found
                return null;
            }
            if(current.value == val) { //found
                return parent;
            }

            parent = current;
            if((current.value).compareTo(val) > 0) { //go left
                current = current.left;
            }
            else { //go right
                current = current.right;
            }
        }
    }
    protected Node findUncle(Elem val)
    {
        Node grandParent = findParent(findParent(val).value);
        if(grandParent == null)
            return null;
        if((grandParent.value).compareTo(val) > 0)
        {
            return grandParent.right;
        }
        else
            return grandParent.left;
    }
    public void repair(Elem val)
    {
        Node n = search(val);
        Node uncle = findUncle(val);
        Node parent = findParent(val);
        Node grandPa = findParent(findParent(val).value);
        Node father = findParent(val);
        // czerwony wujek
        if(uncle != null && uncle.color == true)
        {
            father.color = false;
            uncle.color = false;
            grandPa.color = true;

        }
        // czarny wujek
       else if(uncle == null || uncle.color == false)
        {
            //prawy syn
            if(parent.right != null && parent.right.value == val && grandPa !=null)
            {
                n = father;
              //  grandPa.left = rotateL(n);
                father = findParent(val);
                grandPa.left= rotateL(n);
                father.color = false;
                Elem x = _root.value;
                _root.value = _root.left.value;
               // _root.right.value = x;

           //  n =  rotateR(n);
             /*   father.color = false;
                grandPa.color = true;
                rotateR(n); */
            }
            //lewy syn
           else if (parent.left != null && parent.left.value == val && grandPa !=null)
            {
                father.color = false;
                grandPa.color = true;
                rotateR(grandPa);
            }
        }
        _root.color = false;
    }
    public void insert(Elem id){
        Node newNode = new Node(id);
        if(_root==null){
            _root = newNode;
            _root.color = false;
            allNodes++;
            return;
        }
        Node current = _root;
        Node parent = null;
        while(true){
            parent = current;
            if(id.compareTo(current.value) <0){
                current = current.left;
                if(current==null){
                    parent.left = newNode;
                    allNodes++;
                    repair(id);
                    return;
                }
            }else{
                current = current.right;
                if(current==null){
                    parent.right = newNode;
                    allNodes++;
                    repair(id);
                    return;
                }
            }
        }

    }


    void printLevelOrder()    {
        int h = height(_root);
        int i;
        for (i=1; i<=h; i++)
            printGivenLevel(_root, i);
    }
    void printGivenLevel (Node root ,int level)    {
        if (root == null)
            return;
        if (level == 1)
            System.out.print(root.value + "("+root.color + ")");
        else if (level > 1)
        {
            printGivenLevel(root.left, level-1);
            printGivenLevel(root.right, level-1);
        }
    }
    int height(Node root)    {
        if (root == null)
            return 0;
        else
        {
            /* compute  height of each subtree */
            int lheight = height(root.left);
            int rheight = height(root.right);

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
        globalStack.push(_root);
        int emptyLeaf = 32;
        boolean isRowEmpty = false;
        System.out.println("****......................................................****");
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
                    System.out.print(temp.value+"(" + temp.color+")");
                    localStack.push(temp.left
                    );
                    localStack.push(temp.right);
                    if(temp.left != null ||temp.right != null)
                        isRowEmpty = false;
                }
                else
                {
                    System.out.print("--");
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
        System.out.println("****......................................................****");
    }
    private Node fixUp(Node t)    {   if(isRed(t.right))
        t=rotateL(t);
        if(isRed(t.left) && isRed(t.left.left))
            t=rotateR(t);
        if(isRed(t.left) && isRed(t.right))
            colorFlip(t);
        return t; }
    private Node moveRedR( Node t)    { colorFlip(t);
        if(isRed(t.left.left))
        { t=rotateR(t); colorFlip(t); }
        return t; }

    private Node moveRedL( Node t)    { colorFlip(t);
        if(isRed(t.right.left))
        { t.right=rotateR(t.right); t=rotateL(t); colorFlip(t);}
        return t;}
    public void delete(Elem x)    {_root=delete(x,_root);
        if(_root!=null) _root.color=BLACK;}

    protected Node delete(Elem x, Node t) {
        if(t==null) throw new RuntimeException("Not found  "+x.toString());
        else { int cmp=x.compareTo(t.value);
            if(cmp<0)
            { if(!isRed(t.left) && !isRed(t.left.left))
                t=moveRedL(t);
                t.left=delete(x,t.left);
            }
            else{if( isRed(t.left)) t=rotateR(t);
                if(x.compareTo(t.value)==0&&t.right == null) return null;
                else { if(!isRed(t.right) && !isRed(t.right.left))
                    t=moveRedR(t);
                    if(x.compareTo(t.value)>0)
                        t.right=delete(x,t.right);
                    else t.right=detachMin(t.right, t);
                }
            }
        }
        return fixUp(t);
    }

    protected Node detachMin(Node t, Node del) {
        if(t.left==null) {del.value=t.value; t=null;}
        else { if(!isRed(t.left) && !isRed(t.left.left))
            t=moveRedL(t);
            t.left=detachMin(t.left,del);
            t=fixUp(t); }
        return t;
    }
    int sum = 0;
    int nodes(Node node)
    {
        if( node == null )
            return 0;
        else
        {
            sum++;
            return nodes(node.left) +  outsideNodes(node.right);
        }

    }
    int outsideNodes(Node node)    {

        if( node == null )
            return 0;


        if( node.left == null && node.right == null ) {
            return sum + 2;
        }
        else if(node.left == null )
        {
            sum++;
            return outsideNodes(node.right);
        }
        else if(node.right == null )
        {
            sum++;
            return outsideNodes( node.left);
        }
        else
        {
            return outsideNodes(node.left) +  outsideNodes(node.right);
        }

    }
    public String toString()    {return toString(_root,0);}
    private String toString(Node t,int pos) {
        String result="";
        String spaces="                                                                                                                                                                                                     ";
        if(t!=null) result=result+toString(t.right,pos+4)+spaces.substring(0,pos)
                +String.format("%s%s",t.value,(t.color ? "/R" :"/B"))+toString(t.left,pos+4);
        else result=result+String.format("%n");
        return result;
    }
    class Node{
        Elem value;
        Node left;
        Node right;
        boolean color;
        Node(Elem x)
        { value=x; left = right = null; color = RED;}
    }
    public static void main(String[] args) {

        RBT rbt = new RBT();
        rbt.insert(20);
        rbt.insert(10);
        rbt.insert(15);
    //    rbt.insert(18);
      // rbt.insert(15);
    //    rbt.insert(16);
        //rbt.insert(12);
       // rbt.insert(15);
      //  rbt.printLevelOrder();
      System.out.println();
        rbt.displayTree();
   /*     System.out.println("Czy istnieje elment o kluczu "+ rbt.find(16));
        System.out.println("Wysokosc aktualnego drzewa: " + rbt.heightRoot(rbt._root));
        System.out.println("Liście: " + rbt.countLeaves(rbt._root));
        System.out.println("Wszystkie wezły: " + rbt.allNodes);
//        System.out.println("parent: " + rbt.findParent(18).value);
     //   System.out.println("parent: " + rbt.countLeaves(rbt.findParent(16))); */


      //  Data a = new Data(rbt.heightRoot(rbt._root),89, rbt.countLeaves(rbt._root));

    }


}

