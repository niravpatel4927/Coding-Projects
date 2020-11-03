package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/
		TrieNode rootTrie = new TrieNode (null,null,null);
		if (allWords.length == 0){
			return rootTrie;
		}
		
		rootTrie.firstChild = new TrieNode (new Indexes(0,(short)(0),(short)(allWords[0].length() -1)), null, null);
		
		TrieNode pointer = rootTrie.firstChild;
		TrieNode last = rootTrie.firstChild;
		int similar = -1;
		int startInd = -1;
		int endInd = -1;
		int wordInd = -1;
		
		for (int i = 1; i < allWords.length; i++) {
			String aWord = allWords[i];
			while (pointer != null) {
				startInd = pointer.substr.startIndex;
				endInd = pointer.substr.endIndex;
				wordInd = pointer.substr.wordIndex;
				if (startInd > aWord.length()){
					last = pointer;
					pointer = pointer.sibling;
					continue;
				}
				similar = similarUpTo(allWords[wordInd].substring(startInd, endInd+1), aWord.substring(startInd));
				if(similar != -1)
					similar += startInd;
				
				if(similar == -1) {
					last = pointer;
					pointer = pointer.sibling;
				}
				else {
					if(similar == endInd) {
						last = pointer;
						 pointer = pointer.firstChild;
					}
					else if (similar < endInd){ 
						last = pointer;
						break;
					}
				}
			}
			if(pointer == null) {
				Indexes indices = new Indexes(i, (short)startInd, (short)(aWord.length()-1));
				last.sibling = new TrieNode(indices, null, null);
			} else {
				
				Indexes currentIndices = last.substr; 
				TrieNode currentFirstChild = last.firstChild; 
				
				
				Indexes currentWordNewIndices = new Indexes(currentIndices.wordIndex, (short)(similar+1), currentIndices.endIndex);
				currentIndices.endIndex = (short)similar; 
			
				last.firstChild = new TrieNode(currentWordNewIndices, null, null);
				last.firstChild.firstChild = currentFirstChild;
				last.firstChild.sibling = new TrieNode(new Indexes((short)i, (short)(similar+1), (short)(aWord.length()-1)), null, null);
			}
			pointer = last = rootTrie.firstChild;
			similar = startInd = endInd = wordInd = -1;
		}
	
		
		
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		return rootTrie;
}
	
	private static int similarUpTo(String inTrie, String insert) {
		int counter = 0;
		while(counter < inTrie.length() && counter < insert.length() && inTrie.charAt(counter) == insert.charAt(counter))
			counter++;
		
		return (counter-1);
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/
		if(root == null) return null;
				
				ArrayList<TrieNode> match = new ArrayList<>();
				TrieNode pointer = root;
				
				while(pointer != null) {
					if(pointer.substr == null)
						pointer = pointer.firstChild;
					
					String string = allWords[pointer.substr.wordIndex];
					String string2 = string.substring(0, pointer.substr.endIndex+1);
					if(string.startsWith(prefix) || prefix.startsWith(string2)) {
						if(pointer.firstChild != null) { //this is not a full word, go to children
							match.addAll(completionList(pointer.firstChild, allWords, prefix));
							pointer = pointer.sibling;
						} else { //Otherwise this is a full string node
							match.add(pointer);
							pointer = pointer.sibling;
						}
					} else {
						pointer = pointer.sibling;
					}
				}
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		return match;
	}
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
