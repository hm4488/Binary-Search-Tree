import java.io.*;
import java.util.*;

public class IRSystem{
  
   public static HashMap <String, Integer> listOfTermsHash = new HashMap<String, Integer>();
   public static BinarySearchTree bst;
   public static TermEntry[] termQueue;
   public static String[] termsArray;
   public static List<TermEntry> listOfEntries = new LinkedList<TermEntry>();
   public static void main(String[] args){
      termsArray = parse("quotes.txt");
      countTerms(termsArray);
      bst = new BinarySearchTree(listOfEntries.toArray(new TermEntry[0]));
      bst.inorderTraversal(bst.root);
     
            
      //Case 2
      singleTermQuery("all");
      
      //Case 3
      singleTermQuery("carrying");
      
      //Case 4
      singleTermQuery("robot");
      
      //Case 5
      andQuery("seattle", "marines");
      
      //Case 6
      andQuery("seattle", "pilots");
      
      //Case 7
      orQuery("four", "score");
      
      //Case 8
      orQuery("five", "score");
      
      //Case 9
      orQuery("five", "robots");
 
      
     
           
   }
  
  
   //Parse Method
   public static String[] parse(String filename){
      String[] listOfTerms = {};
      String fileTerms = new String();
      try{
      
         Scanner fileReaderScanner = new Scanner(new File(filename));
         fileTerms += fileReaderScanner.nextLine();
         while(fileReaderScanner.hasNextLine())
         {
            String data = fileReaderScanner.nextLine().toLowerCase();
            //System.out.println(fileReaderScanner.nextLine());
            fileTerms += data + "\n";
         
         }
         listOfTerms = fileTerms.split(" ");
         fileReaderScanner.close();
      
      
      
      }
      catch(FileNotFoundException fe)
      {
      
      }
      
      for(String s: listOfTerms)
      {
         System.out.println(s);
      }
      //System.out.println(listOfTerms.toString());
      return listOfTerms;   
   }
  
   //countTerms
   public static void countTerms(String[] terms){
      int i = 0;
   
     // termQueue = new TermEntry[terms.length];
      for(String s: terms)
      {
         listOfTermsHash.put(terms[i], i);
         //Object obj = terms;
        // termQueue.(terms[i],i);
         listOfEntries.add(new TermEntry(terms[i], i)); 
         i++;
      }
      
       
   
           
      for(String s: terms)
      {
         System.out.println(s);
      }
      
          
   }
   
   //singleTermQuery
   public static TermEntry singleTermQuery(String term)
   {
   
   
      Node node = bst.search(bst.root, term);
      if(node != null)
      {
         System.out.println("The total number for " + term + " is " + Collections.frequency(Arrays.asList(termsArray), term));
         TermEntry termEntry = null;
         return termEntry;
      }
      else
      {
         System.out.println("The specified term " + term + " is not found");
         return null;
      }    
   }
   
   //andQuery
   public static boolean andQuery(String term1, String term2)
   {
    boolean flag;
      if(listOfTermsHash.containsKey(term1) && listOfTermsHash.containsKey(term2))
      {
         System.out.println("True");
        flag = true;
      } 
      else
      {
      System.out.println("False");
       flag = false;
      }
      
      return flag;
   }
   
   //orQuery
   public static boolean orQuery(String term1, String term2)
   {
    boolean flag;
      if(listOfTermsHash.containsKey(term1) || listOfTermsHash.containsKey(term2))
      {
         System.out.println("True");
        flag = true;
      } 
      else
      {
      System.out.println("False");
       flag = false;
      }
      
      return flag;
   }

  
}

//Term entry class
class TermEntry{
   public String term;
   public int count;
  
   public TermEntry(String _term, int _count){
      term = _term;
      count = _count;
   }
   
     
   //accessor for term
   public String getTerm(){
      return term;
   }
  
   //accessor for count
   public int getCount(){
      return count;
   }
   
   public String toString()
   {
      return "Term: " + getTerm() + " Count: " + getCount();
   }
}


//Node class
class Node {
   Node left;
   Node right;
   TermEntry termsEntries;
  
   public Node(TermEntry _termsEntries) {
      termsEntries = _termsEntries;
   }
}

//BinarySearchTree class
class BinarySearchTree{
   Node root;
   int start;
   int end;
   int mid;
    
   
   public BinarySearchTree(TermEntry[] keys) {
   
      Collections.sort(Arrays.asList(keys), Comparator.comparing(TermEntry::getTerm));
      start = 0;
      end = keys.length - 1;
      mid = (start + end) / 2;
      root = new Node(keys[mid]);
     
      // left side of array passed to left subtree
      insert(root, keys, start, mid - 1);
      // right side of array passed to right subtree
      insert(root, keys, mid + 1, end);
   }  
  
  
   public void insert(Node node, TermEntry[] keys, int start, int end) {
      if(start <= end) {
         int mid = (start + end) / 2;
         int sub = mid - 1;
         int add = mid + 1;
         int compare_getTerm = keys[mid].getTerm().compareTo(node.termsEntries.getTerm());
         if(compare_getTerm < 0) {
            // This for left side only
            node.left = new Node(keys[mid]);
            insert(node.left, keys, start, sub);
            insert(node.left, keys, add, end);
         }
         else {
            // This for right subtree only
            node.right = new Node(keys[mid]);
            insert(node.right, keys, start, sub);
            insert(node.right, keys, add, end);
         }
      }
   }
  
   public void inorderTraversal(Node node) {
      if(node != null) {
         inorderTraversal(node.left);
         System.out.println("Visited " + node.termsEntries.toString()); // print node's key value
         inorderTraversal(node.right);
      }
   }
   

  
   public Node search(Node node, String key) {
      if(node == null)
         // hitting an empty node means search has failed
         return null;
      if(node.termsEntries.getTerm().equals(key))
         // found a match, return the Node's allTerms
         return node;
      else if(node.termsEntries.getTerm().compareTo(key) > 0)
         // need to search the left subtree since key is less than node value
         return search(node.left, key);
      else
         // key value is larger than current node, search right subtree
         return search(node.right, key);
   }  
}