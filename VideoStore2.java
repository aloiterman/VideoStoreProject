import java.util.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom; 
/**
 *
 * @author Aharon's PC
 */
public class VideoStore2 {

    private static boolean Search(avlNode root, String k) {
       // Traverse untill root reaches to dead end 
        while (root != null) { 
            // pass right subtree as new tree 
            if (k.compareTo(root.key)>0) 
                root = root.right; 
  
            // pass left subtree as new tree 
            else if (k.compareTo(root.key)<0) 
                root = root.left; 
            else
                return true; // if the key is found return 1 
        } 
        return false;
    }
     
   static class avlNode { 
    String key;
    int height; 
    avlNode left, right; 
  
    avlNode(String d) { 
        key = d; 
        height = 1; 
    } 
} 
  
static class AVLTree { 
  
    avlNode root; 
  
    // A utility function to get the height of the tree 
    int height(avlNode N) { 
        if (N == null) 
            return 0; 
  
        return N.height; 
    } 
  
    // A utility function to get maximum of two integers 
    int max(int a, int b) { 
        return (a > b) ? a : b; 
    } 
  
    // A utility function to right rotate subtree rooted with y 
    // See the diagram given above. 
    avlNode rightRotate(avlNode y) { 
        avlNode x = y.left; 
        avlNode T2 = x.right; 
  
        // Perform rotation 
        x.right = y; 
        y.left = T2; 
  
        // Update heights 
        y.height = max(height(y.left), height(y.right)) + 1; 
        x.height = max(height(x.left), height(x.right)) + 1; 
  
        // Return new root 
        return x; 
    } 
  
    // A utility function to left rotate subtree rooted with x 
    // See the diagram given above. 
    avlNode leftRotate(avlNode x) { 
        avlNode y = x.right; 
        avlNode T2 = y.left; 
  
        // Perform rotation 
        y.left = x; 
        x.right = T2; 
  
        //  Update heights 
        x.height = max(height(x.left), height(x.right)) + 1; 
        y.height = max(height(y.left), height(y.right)) + 1; 
  
        // Return new root 
        return y; 
    } 
  
    // Get Balance factor of node N 
    int getBalance(avlNode N) { 
        if (N == null) 
            return 0; 
  
        return height(N.left) - height(N.right); 
    } 
  
    avlNode avlInsert(avlNode node, String key) { 
  
        /* 1.  Perform the normal BST insertion */
        if (node == null) 
            return (new avlNode(key)); 
  
        if (key.compareTo(node.key)<0) 
            node.left = avlInsert(node.left, key); 
        else if (key.compareTo(node.key)>0) 
            node.right = avlInsert(node.right, key); 
        else // Duplicate keys not allowed 
            return node; 
  
        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left), 
                              height(node.right)); 
  
        /* 3. Get the balance factor of this ancestor 
              node to check whether this node became 
              unbalanced */
        int balance = getBalance(node); 
  
        // If this node becomes unbalanced, then there 
        // are 4 cases Left Left Case 
        if (balance > 1 && key.compareTo(node.left.key)<0) 
            return rightRotate(node); 
  
        // Right Right Case 
        if (balance < -1 && key.compareTo(node.right.key)>0) 
            return leftRotate(node); 
  
        // Left Right Case 
        if (balance > 1 && key.compareTo(node.left.key)>0) { 
            node.left = leftRotate(node.left); 
            return rightRotate(node); 
            
        } 
  
        // Right Left Case 
        if (balance < -1 && key.compareTo(node.right.key)<0) { 
            node.right = rightRotate(node.right); 
            return leftRotate(node); 
        } 
  
        /* return the (unchanged) node pointer */
        return node; 
    } 
    
    /* Given a non-empty binary search tree, return the  
    node with minimum key value found in that tree.  
    Note that the entire tree does not need to be  
    searched. */
    avlNode minValueNode(avlNode node)  
    {  
        avlNode current = node;  
  
        /* loop down to find the leftmost leaf */
        while (current.left != null)  
        current = current.left;  
  
        return current;  
    }  
    
   avlNode deleteNode(avlNode root, String key)  
    {  
        // STEP 1: PERFORM STANDARD BST DELETE  
        if (root == null)  
            return root;  
  
        // If the key to be deleted is smaller than  
        // the root's key, then it lies in left subtree  
        if (key.compareTo(root.key)<0)  
            root.left = deleteNode(root.left, key);  
  
        // If the key to be deleted is greater than the  
        // root's key, then it lies in right subtree  
        else if (key.compareTo(root.key)>0)  
            root.right = deleteNode(root.right, key);  
  
        // if key is same as root's key, then this is the node  
        // to be deleted  
        else
        {  
  
            // node with only one child or no child  
            if ((root.left == null) || (root.right == null))  
            {  
                avlNode temp = null;  
                if (temp == root.left)  
                    temp = root.right;  
                else
                    temp = root.left;  
  
                // No child case  
                if (temp == null)  
                {  
                    temp = root;  
                    root = null;  
                }  
                else // One child case  
                    root = temp; // Copy the contents of  
                                // the non-empty child  
            }  
            else
            {  
  
                // node with two children: Get the inorder  
                // successor (smallest in the right subtree)  
                avlNode temp = minValueNode(root.right);  
  
                // Copy the inorder successor's data to this node  
                root.key = temp.key;  
  
                // Delete the inorder successor  
                root.right = deleteNode(root.right, temp.key);  
            }  
        }  
  
        // If the tree had only one node then return  
        if (root == null)  
            return root;  
  
        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE  
        root.height = max(height(root.left), height(root.right)) + 1;  
  
        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether  
        // this node became unbalanced)  
        int balance = getBalance(root);  
  
        // If this node becomes unbalanced, then there are 4 cases  
        // Left Left Case  
        if (balance > 1 && getBalance(root.left) >= 0)  
            return rightRotate(root);  
  
        // Left Right Case  
        if (balance > 1 && getBalance(root.left) < 0)  
        {  
            root.left = leftRotate(root.left);  
            return rightRotate(root);  
        }  
  
        // Right Right Case  
        if (balance < -1 && getBalance(root.right) <= 0)  
            return leftRotate(root);  
  
        // Right Left Case  
        if (balance < -1 && getBalance(root.right) > 0)  
        {  
            root.right = rightRotate(root.right);  
            return leftRotate(root);  
        }  
  
        return root;  
    }  
    
    // A utility function to print preorder traversal 
    // of the tree. 
    // The function also prints height of every node 
    void preOrder(avlNode node) { 
        if (node != null) { 
            System.out.print(node.key + " "); 
            preOrder(node.left); 
            preOrder(node.right); 
        } 
    } 
}


static class bstNode 
    { 
        String key; 
        bstNode left, right; 
  
        public bstNode(String item) 
        { 
            key = item; 
            left = right = null; 
        } 
    } 
  // Root of BST 
    bstNode root; 
  
    // Constructor 
   VideoStore2() 
    { 
        root = null; 
    }
         
    public static boolean newSearch(bstNode root, String k) 
{ 
    // Traverse untill root reaches to dead end 
        while (root != null) { 
            // pass right subtree as new tree 
            if (k.compareTo(root.key)>0) 
                root = root.right; 
  
            // pass left subtree as new tree 
            else if (k.compareTo(root.key)<0) 
                root = root.left; 
            else
                return true; // if the key is found return 1 
        } 
        return false;
}
   
   // This method mainly calls deleteRec() 
    void deleteKey(String key) 
    { 
        root = deleteRec(root, key); 
    } 
  
    /* A recursive function to insert a new key in BST */
    bstNode deleteRec(bstNode root, String key) 
    { 
        /* Base Case: If the tree is empty */
        if (root == null)  return root; 
  
        /* Otherwise, recur down the tree */
        if (key.compareTo(root.key)<0) 
            root.left = deleteRec(root.left, key); 
        else if (key.compareTo(root.key)>0) 
            root.right = deleteRec(root.right, key); 
  
        // if key is same as root's key, then This is the node 
        // to be deleted 
        else
        { 
            // node with only one child or no child 
            if (root.left == null) 
                return root.right; 
            else if (root.right == null) 
                return root.left; 
  
            // node with two children: Get the inorder successor (smallest 
            // in the right subtree) 
            root.key = minValue(root.right); 
  
            // Delete the inorder successor 
            root.right = deleteRec(root.right, root.key); 
        } 
  
        return root; 
    } 
  
    String minValue(bstNode root) 
    { 
        String minv = root.key; 
        while (root.left != null) 
        { 
            minv = root.left.key; 
            root = root.left; 
        } 
        return minv; 
    } 
  
    // This method mainly calls insertRec() 
    void insert(String key) 
    { 
        root = insertRec(root, key); 
    } 
  
    /* A recursive function to insert a new key in BST */
    bstNode insertRec(bstNode root, String key) 
    { 
  
        /* If the tree is empty, return a new node */
        if (root == null) 
        { 
            root = new bstNode(key); 
            return root; 
        } 
  
        /* Otherwise, recur down the tree */
        if (key.compareTo(root.key)<0) 
            root.left = insertRec(root.left, key); 
        else if (key.compareTo(root.key)>0) 
            root.right = insertRec(root.right, key); 
  
        /* return the (unchanged) node pointer */
        return root; 
    } 
  
    // This method mainly calls InorderRec() 
    void inorder() 
    { 
        inorderRec(root); 
    } 
  
    // A utility function to do inorder traversal of BST 
    void inorderRec(bstNode root) 
    { 
        if (root != null) 
        { 
            inorderRec(root.left); 
            System.out.print(root.key + " "); 
            inorderRec(root.right); 
        } 
    } 
  static class DllNode {
   private Object element;
   private DllNode prev, next;
   public DllNode(Object element, DllNode prev, DllNode next){
       this.element = element;               
   this.prev = prev;
   this.next = next;
}
   public Object getElement(){
       return element;
   }
public DllNode getPrev(){
return prev;
}
public DllNode getNext(){
return next;
}
public void setElement(Object element){
    this.element = element;
}
public void setPrev(DllNode prev){
    this.prev = prev;
}
public void setNext(DllNode next){
this.next = next;
}
}
static class Node{
private Object element;
Node next;
    int data;
    
   

public Node(Object element, Node next){
this.element = element;
this.next = next;
}  
public Object getElement(){
return element;
}
public Node getNext(){
return next;
}

public void setElement(Object element){
this.element = element;
}

public void setNext(Node next){
this.next = next;
}
}
static class SLL {
       
private Node head;
   
public SLL(){
head = null;
}

public Node getHead(){
return head;
}
public void setHead(Node head){
this.head = head;
}
public void print(){ // print out all elements in the SLL, for test purpose
Node current = head;
while(current != null){
System.out.print(current.getElement()+ " ");
current = current.getNext();
}
System.out.println();
}
public void add(Node n){ // add n after the old tail node so that n becomes the tail node
if(head == null){
head = n;
}else{
Node current = head;
while(current.getNext () != null){
current = current.getNext();
}
current.setNext(n);
}
n.setNext(null);
}

public void remove(){ // remove the tail node
if(head == null){
}
else if(
head.getNext()== null)
{
head = null; 
}else{
Node current = head;
while(current.getNext().getNext()!= null){
current = current.getNext();
}
current.setNext(null);
}
}
 private Node nodeAt(int index){
        Node current = head;
        for(int i = 0; i< index; i++){
            current = current.next;
        }
        return current;
    }
    public void delete( int index){
        if (index == 0){
            head = head.next;
        }else{
            Node current = nodeAt(index -1);
            current.next = current.next.next;
        }
    }
  void deleteNode(int position) 
    { 
        // If linked list is empty 
        if (head == null) 
            return; 
  
        // Store head node 
        Node temp = head; 
  
        // If head needs to be removed 
        if (position == 0) 
        { 
            head = temp.next;   // Change head 
            return; 
        } 
  
        // Find previous node of the node to be deleted 
        for (int i=0; temp!=null && i<position-1; i++) 
            temp = temp.next; 
  
        // If position is more than number of nodes 
        if (temp == null || temp.next == null) 
            return; 
  
        // Node temp->next is the node to be deleted 
        // Store pointer to the next of node to be deleted 
        Node next = temp.next.next; 
  
        temp.next = next;  // Unlink the deleted node from list 
    } 
public boolean SLLdelete(String data) {
        Node nodeBeforeDelete = this.head;
        if (nodeBeforeDelete == null) { // List in empty
            return false;
        } else if (nodeBeforeDelete.getElement() == data) {
            this.head = this.head.getNext();
            return true;
        }
        while (true) {
            Node next = (Node) nodeBeforeDelete.getElement();
            if (next == null) { // No data found in list
                return false;
            } else if (next.getElement() == data) {
                break;
            }
            nodeBeforeDelete = next;
        }
        Node next = nodeBeforeDelete.getNext();
        nodeBeforeDelete.setNext(next.getNext());
        next.setNext(null);
        return true;
    }
public void reverse(){ // reverse the nodes in the Singly LinkedList 
if(head == null || head.getNext
() == null){
}else{
Node before = null;
Node current = head;               
while(
current.getNext () != null)
{
    Node temp = current.getNext();
current.setNext(before);
before = current;
current = temp; 
}
current.setNext(before);
head = current;
}
}
}
 public static int getRandomValue(int Min, int Max) 
    { 
  
        // Get and return the random integer 
        // within Min and Max 
        return ThreadLocalRandom 
            .current() 
            .nextInt(Min, Max + 1); 
    } 

    public static void main(String []args) throws NullPointerException{
	try{
	 String input = args[0];
            
	    if(input.contentEquals("dll") && args.length>3){
                 long start = System.currentTimeMillis(); 
        DllList rentedVideos = new DllList(); 
        DllList videos = new DllList();
        DllList inStore = new DllList();
        DllList customers = new DllList();
          LinkedList <Object> inStoreVid = new LinkedList <Object> ();
        Queue<Integer> pq = new PriorityQueue<>(); 
        for(int i =0;i<Integer.parseInt(args[1]);i++){
            String a = "Movie #" + i;
            String b = "Movie #" + i;
            DllNode n = new DllNode(a,null,null);
            DllNode na = new DllNode(b,null,null);
            videos.addFirst(n);
            inStore.addFirst(na);
            inStoreVid.addFirst(a);
        }
                 
                  for(int i =0;i<Integer.parseInt(args[2]);i++){
            String a = "Customer #" + i;
            DllNode n = new DllNode(a,null,null);
            customers.addFirst(n);
                          }
                   for(int i =0;i<Integer.parseInt(args[3]);i++){      
            pq.add(getRandomValue(5,7));      
                          }
                   while(pq.size()!=0){
                       int v = pq.poll();
                   if(v==5){
                       boolean check;
                       String ee = "Movie #" +getRandomValue(0,1000);
                        DllNode look = new DllNode(ee,null,null);
		if(inStoreVid.indexOf(ee)!= -1){
		check = true;
                }else{
		check = false;
                }
                   }
                   if(v==6){
                        boolean check2 = true;
          int num = getRandomValue(0,100);
          String ee1 = "Movie #" +num;    
          int number = num;
          DllNode checkOut = new DllNode(ee1,null,null);   
           if(num<pq.size()){
	    inStore.delete(number);
            inStoreVid.remove(ee1);
        rentedVideos.addFirst(checkOut);
          }
          else{
              check2 = false;
          }
                   }
                   if(v==7){
                       boolean check3 = true;
                         
          int numbera = getRandomValue(0,100);
          String ff = "Movie #" +numbera;
          int num = numbera;
          DllNode checkIn = new DllNode(ff,null,null);  
          if(numbera<pq.size()){
          inStore.addFirst(checkIn);
          inStoreVid.addFirst(ff);
          rentedVideos.delete(num);
                   }
          else{
              check3 = false;
          }
                   }
        }
                    long end = System.currentTimeMillis(); 
        System.out.println("Total Service Time: " + 
                                    (end - start) + "ms"); 
            }
        if(input.contentEquals("sll") && args.length>3){
            long start = System.currentTimeMillis(); 
       SLL rentedVideos = new SLL(); 
        SLL videos = new SLL();
        SLL inStore = new SLL();
        SLL customers = new SLL(); 
        LinkedList <Object> inStoreVid = new LinkedList <Object> ();
        Queue<Integer> pq = new PriorityQueue<>(); 
        for(int i =0;i<Integer.parseInt(args[1]);i++){
            String a = "Movie #" + i;
            String b = "Movie #" + i;
            Node n = new Node(a,null);
            Node na = new Node(b,null);
            videos.add(n);
            inStore.add(na);
            inStoreVid.addFirst(a);
        }
                 
                  for(int i =0;i<Integer.parseInt(args[2]);i++){
            String a = "Customer #" + i;
            Node n = new Node(a,null);
            customers.add(n);
                          }
                   for(int i =0;i<Integer.parseInt(args[3]);i++){      
            pq.add(getRandomValue(5,7));      
                          }
                   while(pq.size()!=0){
                       int v = pq.poll();
                   if(v==5){
                       boolean check;
                       String ee = "Movie #" +getRandomValue(0,1000);
                        DllNode look = new DllNode(ee,null,null);
		if(inStoreVid.indexOf(ee)!= -1){
		check = true;
                }else{
		check = false;
                }
                   }
                   if(v==6){
                        boolean check2 = true;
          int num = getRandomValue(0,100);
          String ee1 = "Movie #" +num;    
          int number = num;
          Node checkOut = new Node(ee1,null);   
           if(num<pq.size()){
	    inStore.delete(number);
            inStoreVid.remove(ee1);
        rentedVideos.add(checkOut);
          }
          else{
              check2 = false;
          }
                   }
                   if(v==7){
                       boolean check3 = true;
                         
          int numbera = getRandomValue(0,100);
          String ff = "Movie #" +numbera;
          int num = numbera;
          Node checkIn = new Node(ff,null);  
          if(numbera<pq.size()){
          inStore.add(checkIn);
          inStoreVid.addFirst(ff);
          rentedVideos.delete(num);
                   }
          else{
              check3 = false;
          }
                   }
        }
                    long end = System.currentTimeMillis(); 
        System.out.println("Total Service Time: " + 
                                    (end - start) + "ms"); 
        }
		if(input.contentEquals("avl") && args.length>3){
            long start = System.currentTimeMillis(); 
        AVLTree rentedVideos = new AVLTree(); 
        AVLTree videos = new AVLTree();
        AVLTree inStoreVideos = new AVLTree();
        AVLTree customers = new AVLTree(); 
        Queue<Integer> pq = new PriorityQueue<>(); 
        for(int i =0;i<Integer.parseInt(args[1]);i++){
            String b = "Movie #" + i;
            inStoreVideos.root = inStoreVideos.avlInsert(inStoreVideos.root,b);
          videos.root = videos.avlInsert(videos.root, b);    
        }
                 
                  for(int i =0;i<Integer.parseInt(args[2]);i++){
            String a = "Customer #" + i;
            customers.root = customers.avlInsert(customers.root,a);
                          }
                   for(int i =0;i<Integer.parseInt(args[3]);i++){      
            pq.add(getRandomValue(5,7));      
                          }
                   while(pq.size()!=0){
                       int v = pq.poll();
                   if(v==5){
                       boolean check;
                       String ee = "Movie #" +getRandomValue(0,1000);
                       avlNode root = inStoreVideos.root;
         if (Search(root, ee)) {
		check = true;
                }else{
		check = false;
                }
                   }
                   if(v==6){
                        boolean check2 = true;
          int num = getRandomValue(0,100);
          String ee1 = "Movie #" +num;    
          int number = num; 
           if(num<pq.size()){
  inStoreVideos.root = inStoreVideos.deleteNode(inStoreVideos.root,ee1);
           rentedVideos.root = rentedVideos.avlInsert(rentedVideos.root,ee1);
          }
          else{
              check2 = false;
          }
                   }
                   if(v==7){
                       boolean check3 = true;
                         
          int numbera = getRandomValue(0,100);
          String ff = "Movie #" +numbera;
          int num = numbera;
          if(numbera<pq.size()){
  rentedVideos.root = rentedVideos.deleteNode(rentedVideos.root,ff);
           inStoreVideos.root = inStoreVideos.avlInsert(inStoreVideos.root,ff);
                   }
          else{
              check3 = false;
          }
                   }
        }
                    long end = System.currentTimeMillis(); 
        System.out.println("Total Service Time: " + 
                                    (end - start) + "ms"); 
            }
		if(input.contentEquals("bst") && args.length>3){
             long start = System.currentTimeMillis(); 
        VideoStore2 rentedVideos = new VideoStore2(); 
        VideoStore2 videos = new VideoStore2();
        VideoStore2 inStoreVideos = new VideoStore2();
        VideoStore2 customers = new VideoStore2();
        Queue<Integer> pq = new PriorityQueue<>(); 
        for(int i =0;i<Integer.parseInt(args[1]);i++){
            String b = "Movie #" + i;
            inStoreVideos.insert(b);
          videos.insert(b);    
        }
                  for(int i =0;i<Integer.parseInt(args[2]);i++){
            String a = "Customer #" + i;
            customers.insert(a);
                          }
                   for(int i =0;i<Integer.parseInt(args[3]);i++){      
            pq.add(getRandomValue(5,7));      
                          }
                   while(pq.size()!=0){
                       int v = pq.poll();
                   if(v==5){
                       boolean check;
                       String ee = "Movie #" +getRandomValue(0,1000);
                       bstNode root = inStoreVideos.root;
         if (newSearch(root, ee)) {
		check = true;
                }else{
		check = false;
                }
                   }
                   if(v==6){
                        boolean check2 = true;
          int num = getRandomValue(0,100);
          String ee1 = "Movie #" +num;    
          int number = num; 
           if(num<pq.size()){
  inStoreVideos.deleteKey(ee1);
           rentedVideos.insert(ee1);
          }
          else{
              check2 = false;
          }
                   }
                   if(v==7){
                       boolean check3 = true;
                         
          int numbera = getRandomValue(0,100);
          String ff = "Movie #" +numbera;
          int num = numbera;
          if(numbera<pq.size()){
  rentedVideos.deleteKey(ff);
           inStoreVideos.insert(ff);
                   }
          else{
              check3 = false;
          }
                   }
        }
                    long end = System.currentTimeMillis(); 
        System.out.println("Total Service Time: " + 
                                    (end - start) + "ms"); 
        }
       if(input.contentEquals("dll") && args.length<3){
        DllList rentedVideos = new DllList(); 
        DllList videos = new DllList();
        DllList inStore = new DllList();
        DllList customers = new DllList(); 
        LinkedList <Object> inStoreVid = new LinkedList <Object> ();
                int request;
        System.out.println("Welcome to the video store!");
        menu();
        Scanner query1 = new Scanner(System.in);
        request=query1.nextInt();
   do{
        while(request>0 && request <13){
       switch(request)
        {
           case 1: 
               
          System.out.println("What is the name of the video you would like to add?");
            Scanner a =new Scanner(System.in);
          String b = a.nextLine();
          DllNode insert = new DllNode(b,null,null);
		  DllNode inserta = new DllNode(b,null,null);
           inStore.addFirst(insert);
           videos.addFirst(inserta);
           inStoreVid.addFirst(b);
            System.out.println();
            
           
        menu();
        Scanner query2 = new Scanner(System.in);
        request=query2.nextInt();
           break;
           
           case 2: 
               videos.print();
          System.out.println("Which number video would you like to remove?(1=First Video in list,2= Second Video in list, etc.");  
           Scanner aa =new Scanner(System.in);
          int bb = aa.nextInt();
          videos.delete(bb);
         inStore.delete(bb); 
          inStoreVid.remove(bb);
          System.out.println();
        menu();
        Scanner query3 = new Scanner(System.in);
        request=query3.nextInt();
           break;
           
           case 3:
               System.out.println("What is the name of the customer you would like to add?");
               Scanner c =new Scanner(System.in);
               String cc = c.nextLine();
               DllNode customer = new DllNode(cc,null,null);
               customers.addFirst(customer);
           
            System.out.println();
            
        menu();
        Scanner query4 = new Scanner(System.in);
        request=query4.nextInt();
           break;
           
           case 4:
               customers.print();
          System.out.println("Which number customer would you like to remove (1=First customer in list, 2=Second customer in list, etc.?");
          Scanner d =new Scanner(System.in);
          int dd = d.nextInt();
          customers.delete(dd);
         System.out.println();
        menu();
        Scanner query5 = new Scanner(System.in);
        request=query5.nextInt();
          break;
          
          case 5:
               System.out.println("What is the name of the video you would like to look up?");
                Scanner e =new Scanner(System.in);
          String ee = e.nextLine();
          DllNode look = new DllNode(ee,null,null);
		if(inStoreVid.indexOf(ee)!= -1)
		System.out.println("True");
		else
		System.out.println("False");
         System.out.println();       
        menu();
        Scanner query6 = new Scanner(System.in);
        request=query6.nextInt();
          break;
          
           case 6:
           System.out.println("What is the name of the customer that is checking out?");
               Scanner potato = new Scanner(System.in);
               String pt = potato.nextLine();
           System.out.println("What is the name of the video that "+pt+" would like to check out?");
          Scanner e1 =new Scanner(System.in);
          String ee1 = e1.nextLine();
          inStore.display();
          System.out.println("Which number video is "+ee1+"?");
          Scanner num = new Scanner(System.in);
          int number = num.nextInt();
          DllNode checkOut = new DllNode(ee1,null,null);
	    inStore.delete(number);
            inStoreVid.remove(ee1);
        rentedVideos.addFirst(checkOut);
           System.out.println();	 
        menu();
        Scanner query7 = new Scanner(System.in);
        request=query7.nextInt();
           break;
           
           case 7:
              System.out.println("What is the name of the video you would like to check in?");
              Scanner f =new Scanner(System.in);
          String ff = f.nextLine();
          rentedVideos.print();
          System.out.println("Which number video is "+ff+"?");
          Scanner numa = new Scanner(System.in);
          int numbera = numa.nextInt();
          DllNode checkIn = new DllNode(ff,null,null);  
          inStore.addFirst(checkIn);
          inStoreVid.addFirst(ff);
          rentedVideos.delete(numbera);
          System.out.println();
        menu();
        Scanner query8 = new Scanner(System.in);
        request=query8.nextInt();
        break;
           case 8:
               System.out.println("Here is a list of the customers");
             customers.print();
             
          System.out.println();    
        menu();
        Scanner query9 = new Scanner(System.in);
        request=query9.nextInt();
        break;
        case 9:
          System.out.println("Here is a list of all of the videos");
               videos.print();
            System.out.println();   
                    
        menu();
        Scanner query10 = new Scanner(System.in);
        request=query10.nextInt();
        break;
        case 10:
             System.out.println("Here are the in-store videos:");
             inStore.display();
             inStoreVid.toString();
             System.out.println();  
        menu();
        Scanner query11 = new Scanner(System.in);
        request=query11.nextInt();
        break;
        case 11:
              System.out.println("Here is a list of all of the videos which are rented");
              rentedVideos.print();
               System.out.println();
              
        menu();
        Scanner query12 = new Scanner(System.in);
        request=query12.nextInt();
        break;
        case 12:
              System.out.println("Which customer do you want to know about?");
               Scanner customerName = new Scanner(System.in);
               String cName = customerName.nextLine();
        
            System.out.println("Here are the videos rented by "+cName+":");
            rentedVideos.print();
              
        menu();
        Scanner query13 = new Scanner(System.in);
        request=query13.nextInt();
      
              break;
           
          
         
        } 
   }
    }while(request>0 && request<13);
   System.out.println("Goodbye");   
	}
	
        if(input.contentEquals("sll")&& args.length<3){
		
                 SLL rentedVideos = new SLL(); 
        SLL videos = new SLL();
        SLL inStore = new SLL();
        SLL customers = new SLL(); 
        LinkedList <Object> inStoreVid = new LinkedList <Object> ();
                int request;
        System.out.println("Welcome to the video store!");
        menu();
        Scanner query1 = new Scanner(System.in);
        request=query1.nextInt();
   do{
        while(request>0 && request <13){
       switch(request)
        {
           case 1: 
               
          System.out.println("What is the name of the video you would like to add?");
            Scanner a =new Scanner(System.in);
          String b = a.nextLine();
          Node insert = new Node(b,null);
		  Node inserta = new Node(b,null);
           inStore.add(insert);
           videos.add(inserta);
           inStoreVid.addLast(b);
           
            System.out.println();
           
        menu();
        Scanner query2 = new Scanner(System.in);
        request=query2.nextInt();
           break;
           
          case 2: 
               videos.print();
          System.out.println("Which number video would you like to remove?(0= First video in list, 1= Second Video in list,etc.)");  
           Scanner aa =new Scanner(System.in);
          int bb = aa.nextInt();
          videos.delete(bb);      
         inStore.delete(bb); 
          inStoreVid.remove(bb);
          System.out.println();
        menu();
        Scanner query3 = new Scanner(System.in);
        request=query3.nextInt();
           break;
           
           case 3:
               System.out.println("What is the name of the customer you would like to add?");
               Scanner c =new Scanner(System.in);
               String cc = c.nextLine();
               Node customer = new Node(cc,null);
               customers.add(customer);
           
            System.out.println();
            
        menu();
        Scanner query4 = new Scanner(System.in);
        request=query4.nextInt();
           break;
           
           case 4:
               customers.print();
          System.out.println("Which number customer would you like to remove (0=First customer in list, 1=Second customer in list, etc.?)");
          Scanner d =new Scanner(System.in);
          int dd = d.nextInt();
          customers.delete(dd);
         System.out.println();
        menu();
        Scanner query5 = new Scanner(System.in);
        request=query5.nextInt();
          break;
          
          case 5:
               System.out.println("What is the name of the video you would like to look up?");
                Scanner e =new Scanner(System.in);
          String ee = e.nextLine();
          DllNode look = new DllNode(ee,null,null);
		if(inStoreVid.indexOf(ee)!= -1)
		System.out.println("True");
		else
		System.out.println("False");
         System.out.println();       
        menu();
        Scanner query6 = new Scanner(System.in);
        request=query6.nextInt();
          break;
          
           case 6:
           System.out.println("What is the name of the customer that is checking out?");
               Scanner potato = new Scanner(System.in);
               String pt = potato.nextLine();
           System.out.println("What is the name of the video that "+pt+" would like to check out?");
          Scanner e1 =new Scanner(System.in);
          String ee1 = e1.nextLine();
          inStore.print();
          System.out.println("Which number video is "+ee1+"?(0=First video, 1= Second video, etc.)");
          Scanner num = new Scanner(System.in);
          int number = num.nextInt();
          Node checkOut = new Node(ee1,null);
	    inStore.delete(number);
            inStoreVid.remove(ee1);
        rentedVideos.add(checkOut);
           System.out.println();	 
        menu();
        Scanner query7 = new Scanner(System.in);
        request=query7.nextInt();
           break;
           
           case 7:
              System.out.println("What is the name of the video you would like to check in?");
              Scanner f =new Scanner(System.in);
          String ff = f.nextLine();
          rentedVideos.print();
          System.out.println("Which number video is "+ff+"?(0=First video, 1= Second video, etc.)");
          Scanner numa = new Scanner(System.in);
          int numbera = numa.nextInt();
          Node checkIn = new Node(ff,null);  
          inStore.add(checkIn);
          inStoreVid.addLast(ff);
          rentedVideos.delete(numbera);
          System.out.println();
        menu();
        Scanner query8 = new Scanner(System.in);
        request=query8.nextInt();
        break;
           case 8:
               System.out.println("Here is a list of the customers");
             customers.print();
             
          System.out.println();    
        menu();
        Scanner query9 = new Scanner(System.in);
        request=query9.nextInt();
        break;
        case 9:
          System.out.println("Here is a list of all of the videos");
               videos.print();
            System.out.println();   
                    
        menu();
        Scanner query10 = new Scanner(System.in);
        request=query10.nextInt();
        break;
        case 10:
             System.out.println("Here are the in-store videos:");
             inStore.print();
          
             System.out.println();  
        menu();
        Scanner query11 = new Scanner(System.in);
        request=query11.nextInt();
        break;
        case 11:
              System.out.println("Here is a list of all of the videos which are rented");
              rentedVideos.print();
               System.out.println();
              
        menu();
        Scanner query12 = new Scanner(System.in);
        request=query12.nextInt();
        break;
        case 12:
              System.out.println("Which customer do you want to know about?");
               Scanner customerName = new Scanner(System.in);
               String cName = customerName.nextLine();
        
            System.out.println("Here are the videos rented by "+cName+":");
            rentedVideos.print();
              
        menu();
        Scanner query13 = new Scanner(System.in);
        request=query13.nextInt();
      
              break;
           
          
         
        } 
   }
    }while(request>0 && request<13);
   System.out.println("Goodbye");   
	
	}
		if(input.contentEquals("avl")&& args.length<3){
        AVLTree rentedVideos = new AVLTree(); 
        AVLTree videos = new AVLTree();
        AVLTree inStoreVideos = new AVLTree();
        AVLTree customers = new AVLTree(); 
				int request;
        System.out.println("Welcome to the video store!");
        menu();
        Scanner query1 = new Scanner(System.in);
        request=query1.nextInt();
   do{
        while(request>0 && request <13){
       switch(request)
        {
           case 1: 
               
          System.out.println("What is the name of the video you would like to add?");
            Scanner a =new Scanner(System.in);
          String b = a.nextLine();
           inStoreVideos.root = inStoreVideos.avlInsert(inStoreVideos.root,b);
          videos.root = videos.avlInsert(videos.root, b);           
            System.out.println();
            
           
        menu();
        Scanner query2 = new Scanner(System.in);
        request=query2.nextInt();
           break;
           
           case 2: 
          System.out.println("What is the name of the video you would like to remove?");  
           Scanner aa =new Scanner(System.in);
          String bb = aa.nextLine();
      videos.root = videos.deleteNode(videos.root,bb);
      inStoreVideos.root = inStoreVideos.deleteNode(inStoreVideos.root,bb);  
          
          System.out.println();
        menu();
        Scanner query3 = new Scanner(System.in);
        request=query3.nextInt();
           break;
           
           case 3:
               System.out.println("What is the name of the customer you would like to add?");
              Scanner c =new Scanner(System.in);
          String cc = c.nextLine();
            
           customers.root = customers.avlInsert(customers.root,cc);
           
            System.out.println();
            
        menu();
        Scanner query4 = new Scanner(System.in);
        request=query4.nextInt();
           break;
           
           case 4:
              // customers.inorder();
               System.out.println("What is the name of the customer you would like to remove?");
              Scanner d =new Scanner(System.in);
          String dd = d.nextLine();
        
         customers.root = customers.deleteNode(customers.root,dd);
         System.out.println();
        menu();
        Scanner query5 = new Scanner(System.in);
        request=query5.nextInt();
          break;
          
           case 5:
               System.out.println("What is the name of the video you would like to look up?");
                Scanner e =new Scanner(System.in);
          String ee = e.nextLine();
           avlNode root = inStoreVideos.root;
         if (Search(root, ee)) 
            System.out.println("True"); 
        else
            System.out.println("False"); 
               
            
          System.out.println();   
               
        menu();
        Scanner query6 = new Scanner(System.in);
        request=query6.nextInt();
          break;
          
           case 6:
           System.out.println("What is the name of the customer that is checking out?");
               Scanner potato = new Scanner(System.in);
               String pt = potato.nextLine();
           System.out.println("What is the name of the video that "+pt+" would like to check out?");
          Scanner e1 =new Scanner(System.in);
          String ee1 = e1.nextLine();
		  inStoreVideos.root = inStoreVideos.deleteNode(inStoreVideos.root,ee1);
           rentedVideos.root = rentedVideos.avlInsert(rentedVideos.root,ee1);
           System.out.println();
		 
        menu();
        Scanner query7 = new Scanner(System.in);
        request=query7.nextInt();
           break;
           
           case 7:
                            System.out.println("What is the name of the video you would like to check in?");
              Scanner f =new Scanner(System.in);
          String ff = f.nextLine();
            
           inStoreVideos.root = inStoreVideos.avlInsert(inStoreVideos.root,ff);
           rentedVideos.root = rentedVideos.deleteNode(rentedVideos.root,ff);
			  System.out.println();
        menu();
        Scanner query8 = new Scanner(System.in);
        request=query8.nextInt();
        break;
           case 8:
               System.out.println("Here is a list of the customers");
                   customers.preOrder(customers.root);
          System.out.println();    
        menu();
        Scanner query9 = new Scanner(System.in);
        request=query9.nextInt();
        break;
        case 9:
          System.out.println("Here is a list of all of the videos");
               videos.preOrder(videos.root);
            System.out.println();   
                    
        menu();
        Scanner query10 = new Scanner(System.in);
        request=query10.nextInt();
        break;
        case 10:
             System.out.println("Here are the in-store videos:");
             inStoreVideos.preOrder(inStoreVideos.root);
             System.out.println();  
        menu();
        Scanner query11 = new Scanner(System.in);
        request=query11.nextInt();
        break;
        case 11:
              System.out.println("Here is a list of all of the videos which are rented");
               rentedVideos.preOrder(rentedVideos.root);
               System.out.println();
              
        menu();
        Scanner query12 = new Scanner(System.in);
        request=query12.nextInt();
        break;
        case 12:
              System.out.println("Which customer do you want to know about?");
               Scanner customerName = new Scanner(System.in);
               String cName = customerName.nextLine();
               avlNode theName=customers.root;
               if (Search(theName, cName)) {
            System.out.println("Here are the videos rented by "+cName+":");
            rentedVideos.preOrder(rentedVideos.root); }  
               else{
            System.out.println("No such customer exists"); 
               }
               System.out.println();
              
        menu();
        Scanner query13 = new Scanner(System.in);
        request=query13.nextInt();
      
              break;
           
          
         
        } 
   }
    }while(request>0 && request<13);
   System.out.println("Goodbye");   
	}
        if(input.contentEquals("bst")&& args.length<3){ 
        VideoStore2 rentedVideos = new VideoStore2(); 
        VideoStore2 videos = new VideoStore2();
        VideoStore2 inStoreVideos = new VideoStore2();
        VideoStore2 customers = new VideoStore2();
        
        int request;
        System.out.println("Welcome to the video store!");
        menu();
        Scanner query1 = new Scanner(System.in);
        request=query1.nextInt();
   do{
        while(request>0 && request <13){
       switch(request)
        {
           case 1: 
               
          System.out.println("What is the name of the video you would like to add?");
            Scanner a =new Scanner(System.in);
          String b = a.nextLine();
            videos.insert(b);
           inStoreVideos.insert(b);
        
            
            System.out.println();
            
           
        menu();
        Scanner query2 = new Scanner(System.in);
        request=query2.nextInt();
           break;
           
           case 2: 
               //videos.inorder();
          System.out.println("What is the name of the video you would like to remove?");  
           Scanner aa =new Scanner(System.in);
          String bb = aa.nextLine();
       videos.deleteKey(bb);
        inStoreVideos.deleteKey(bb);  
          
          System.out.println();
        menu();
        Scanner query3 = new Scanner(System.in);
        request=query3.nextInt();
           break;
           
           case 3:
               System.out.println("What is the name of the customer you would like to add?");
              Scanner c =new Scanner(System.in);
          String cc = c.nextLine();
            
            customers.insert(cc);
           // customers.inorder();
        
            
            System.out.println();
            
        menu();
        Scanner query4 = new Scanner(System.in);
        request=query4.nextInt();
           break;
           
           case 4:
              // customers.inorder();
               System.out.println("What is the name of the customer you would like to remove?");
              Scanner d =new Scanner(System.in);
          String dd = d.nextLine();
        
         customers.deleteKey(dd);
         System.out.println();
        menu();
        Scanner query5 = new Scanner(System.in);
        request=query5.nextInt();
          break;
          
           case 5:
               System.out.println("What is the name of the video you would like to look up?");
                Scanner e =new Scanner(System.in);
          String ee = e.nextLine();
           bstNode insert3 = new bstNode("ee");
           bstNode pizza = new bstNode("yum");
           bstNode root = inStoreVideos.root;
         if (newSearch(root, ee)) 
            System.out.println("True"); 
        else
            System.out.println("False"); 
               
              
             System.out.println();
               
        menu();
        Scanner query6 = new Scanner(System.in);
        request=query6.nextInt();
          break;
          
           case 6:
           System.out.println("What is the name of the customer that is checking out?");
               Scanner potato = new Scanner(System.in);
               String pt = potato.nextLine();
           System.out.println("What is the name of the video that "+pt+" would like to check out?");
          Scanner e1 =new Scanner(System.in);
          String ee1 = e1.nextLine();
		   inStoreVideos.deleteKey(ee1);
                   rentedVideos.insert(ee1);
           System.out.println();
		 // inStoreVideos.inorder();
          
        menu();
        Scanner query7 = new Scanner(System.in);
        request=query7.nextInt();
           break;
           
           case 7:
                            System.out.println("What is the name of the video you would like to check in?");
              Scanner f =new Scanner(System.in);
          String ff = f.nextLine();
            
            inStoreVideos.insert(ff);
            rentedVideos.deleteKey(ff);
              //inStoreVideos.inorder();
			  System.out.println();
        menu();
        Scanner query8 = new Scanner(System.in);
        request=query8.nextInt();
        break;
           case 8:
               System.out.println("Here is a list of the customers");
              // if(customers.getHead()!=null){
                   customers.inorder();
              // }
            //System.out.println("No customers in the list.");
              System.out.println();
        menu();
        Scanner query9 = new Scanner(System.in);
        request=query9.nextInt();
        break;
        case 9:
          System.out.println("Here is a list of all of the videos");
               //if(videos.getHead()!= null){
               videos.inorder();
               //}
              
               //System.out.println("No videos in the list.");
            System.out.println();   
                    
        menu();
        Scanner query10 = new Scanner(System.in);
        request=query10.nextInt();
        break;
        case 10:
             System.out.println("Here are the in-store videos:");
             inStoreVideos.inorder();
             System.out.println();  
        menu();
        Scanner query11 = new Scanner(System.in);
        request=query11.nextInt();
        break;
        case 11:
              System.out.println("Here is a list of all of the videos which are rented");
               rentedVideos.inorder();
               System.out.println();
              
        menu();
        Scanner query12 = new Scanner(System.in);
        request=query12.nextInt();
        break;
        case 12:
              System.out.println("Which customer do you want to know about?");
               Scanner customerName = new Scanner(System.in);
               String cName = customerName.nextLine();
               bstNode theName=customers.root;
               if (newSearch(theName, cName)) {
            System.out.println("Here are the videos rented by "+cName+":");
            rentedVideos.inorder(); }  
               else{
            System.out.println("No such customer exists"); 
               }
               System.out.println();
               
        menu();
        Scanner query13 = new Scanner(System.in);
        request=query13.nextInt();
      
              break;
           
          
         
        } 
   }
    }while(request>0 && request<13);
   System.out.println("Goodbye");    
}
        }catch(NullPointerException e){
                System.out.println("Something went wrong, try an reenter your input again");
                }
        }

 public static void menu()
    {
      System.out.println("Please choose one of the following options:");
      System.out.println("1: Add a video");
      System.out.println("2: Delete a video");
      System.out.println("3: Add a customer");
      System.out.println("4: Delete a customer");
      System.out.println("5: Check if a particular video is in the store");
      System.out.println("6: Check out a video");
      System.out.println("7: Check in a video");
      System.out.println("8: Print all customers");
      System.out.println("9: Print all videos");
      System.out.println("10: Print in store videos");
      System.out.println("11: Print all rented videos");
      System.out.println("12: Print the videos rented by a customer");
      System.out.println("13: Exit");
       }


    private static class DllList {

       private DllNode header, trailer;
 public DllList(){
        header = new DllNode(null, null, null);
    trailer = new DllNode(null, header, null);
    header.setNext(trailer);
    }
    public DllNode getHeader(){
        return header;
    }
    public DllNode getTrailer(){
        return trailer;
    }
    public void setHeader(DllNode header){
        this.header = header;
    }
    public void setTrailer(DllNode trailer){
        this.trailer = trailer;
    }
    public void print(){ // output all elements in DList, for testing purpose
        DllNode current = header.getNext();
        while(current != trailer){
            System.out.print(current.getElement() + " ");
        current = current.getNext();
    }             
        System.out.println();
}
    public int size(){
        int count = 0;
        DllNode current = header;
        while (current !=null){
            current = current.next;
            count++;
        }
        return count;       
    }
	 //display() will print out the nodes of the list  
    public void display() {  
        //Node current will point to head  
        DllNode current = header;  
        if(header == null) {  
            System.out.println("List is empty");  
            return;  
        }  
        while(current != null) {  
            //Prints each node by incrementing the pointer.  
  
            System.out.print(current.getElement() + ", ");  
            current = current.next;  
        }  
    }
    private DllNode nodeAt(int index){
        DllNode current = header;
        for(int i = 0; i< index; i++){
            current = current.next;
        }
        return current;
    }
    public void delete( int index){
        if (index == 0){
            header = header.next;
        }else{
            DllNode current = nodeAt(index -1);
            current.next = current.next.next;
        }
    }
    public void addFirst(DllNode n){ // add node n after header
    DllNode temp = header.getNext();
    header.setNext(n);
    n.setPrev(header);
    n.setNext(temp); 
    temp.setPrev(n);
}public void addLast(DllNode n){ // add node n before trailer
    DllNode temp = trailer.getPrev();
    temp.setNext(n); 
    n.setPrev(temp);
    n.setNext(trailer); 
    trailer.setPrev(n);
}public void remove(DllNode n){ // remove node n
    DllNode prev= n.getPrev(); 
    DllNode next = n.getNext();
    if(prev!= null && next != null){
        prev.setNext(next);
     next.setPrev(prev);
    n.setPrev(null); 
    n.setNext(null);
}
}  
public void reverse(){ // reverse the Doubly LinkedList
    DllNode before = null; 
    DllNode current = header; 
    DllNode after = header.getNext();
    while(after.getNext() != null){
        DllNode temp = after.getNext();
        current.setPrev(after); 
        current.setNext(before);
        before = current; 
        current = after; 
        after = temp;
    }
    current.setPrev(after); 
    current.setNext(before);
    after.setPrev(null); 
    after.setNext(current);
    trailer = header; 
    header = after;
}

    }
      
  }
