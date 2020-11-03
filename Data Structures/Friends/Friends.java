package friends;

import structures.Queue;
import structures.Stack;

import java.util.*;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {

		//checks to see if any of the parameteres is null
		if (g == null) {
			return null;
		}
		if (p1 == null) {
			return null;
		}
		if (p2 == null) {
			return null;
		}
		
		
		ArrayList<String> shortest = new ArrayList<String>();
		boolean[] visitedVertices = new boolean[g.members.length];
		Queue<Person> queue = new Queue<Person>();
		Person[] previousVisited = new Person[g.members.length];
		int i = g.map.get(p1);
		queue.enqueue(g.members[i]);
		
		visitedVertices[i] = true;
	
		while (queue.isEmpty() == false) {
			
			Person person = queue.dequeue();

			int Index = g.map.get(person.name);
			visitedVertices[Index] = true;
			
			Friend neighbor = person.first;
			
			if (neighbor == null) {
				return null;
			}
			
			while (neighbor != null) {
				
				if (visitedVertices[neighbor.fnum] == false) {
					visitedVertices[neighbor.fnum] = true;
					previousVisited[neighbor.fnum] = person; 
					queue.enqueue(g.members[neighbor.fnum]);
					
					//check if p2 matches the neighbor
					if (g.members[neighbor.fnum].name.equals(p2)) {
						person = g.members[neighbor.fnum];
						
						while (person.name.equals(p1) == false) {
							shortest.add(0, person.name);
							person = previousVisited[g.map.get(person.name)];
						}
						shortest.add(0, p1);
						return shortest;
					}
				}
				neighbor = neighbor.next;
			}
		}
		return null;
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		//check for bad input
		if (g == null) {
			return null;
		}
		if (school == null) {
			return null;
		}
		
		//This will keep track of the listOfCliques
		ArrayList<ArrayList<String>> cliques= new ArrayList<ArrayList<String>>();
		
		//This will create the array to see which vertices we have visited
		boolean[] visited = new boolean[g.members.length];
		
		//This will initiate the helper method
		return BFS(g, g.members[0], cliques, visited, school);
		
	}
	
	//This is the helper method
	private static ArrayList<ArrayList<String>> BFS(Graph a, Person start, ArrayList<ArrayList<String>> listOfCliques, boolean[] visited, String school){
		
		//This will hold the results of the cliques
		ArrayList<String> cliquesResults = new ArrayList<String>();
		
		//This will create a queue that is for use of BFS
		Queue<Person> queue = new Queue<Person>();
		
		//This will add start to the queue
		queue.enqueue(start);
		
		//set the current index in visited to true because we start off by visiting from the start
		visited[a.map.get(start.name)] = true;
		
		Person pivot = new Person();
		Friend neighbor;
		
		if (start.school == null || start.school.equals(school) == false) {
			
			queue.dequeue();
			
			for (int j = 0; j < visited.length; j++) {
				if (visited[j] == false) {
					return BFS(a, a.members[j], listOfCliques, visited, school);
				}
			}
		}
		
		while (queue.isEmpty() == false) {
			
			//We have to check each neighbor of pivot
			pivot = queue.dequeue();
			
			//This is the neighbor of pivot
			neighbor = pivot.first;
			cliquesResults.add(pivot.name);
			
			//checking all the parents neighbor
			while (neighbor != null) {
				
				//checking only the neighbor that hasn't been checked already
				if (visited[neighbor.fnum] == false) {
					
					//if the neighbor goes to school he gets checked in future
					if (a.members[neighbor.fnum].school == null) {
						
					}
					else {
						if (a.members[neighbor.fnum].school.equals(school)) {
							queue.enqueue(a.members[neighbor.fnum]);
						}
					}
					visited[neighbor.fnum] = true;
				}
				neighbor = neighbor.next;
			}
		}
		
		//This will take out the empty list at the end
		if (listOfCliques.isEmpty() == false && cliquesResults.isEmpty()) {
			
		} 
		else {
			listOfCliques.add(cliquesResults);
		}
		
		for (int i = 0; i < visited.length; i++) {
			if (visited[i] == false) {
				return BFS(a, a.members[i], listOfCliques, visited, school);
			}
		}
		
		//This will return answer
		return listOfCliques;
	}

	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		
		if (g == null) {
			return null;
		}
		
		ArrayList<String> connect = new ArrayList<String>();
		
		boolean[] visited = new boolean[g.members.length];
		
		ArrayList<String> previous = new ArrayList<String>();
		
		int[] numberDFS= new int[g.members.length];
		
		int[] beforeNumber = new int[g.members.length];
		
		
		for (int i = 0; i < g.members.length; i++){
			if (visited[i] == false) {
				connect = DFS(connect, g, g.members[i], visited, new int[] {0,0}, numberDFS, beforeNumber, previous, true);
			}
		}
		
		return connect;
	}
	
	private static ArrayList<String> DFS(ArrayList<String> connectors, Graph graph, Person start, boolean[] visited, int[] count, int[] numbersOfDFS, int[] back, ArrayList<String> backward, boolean started){
		
		visited[graph.map.get(start.name)] = true;
		
		Friend neighbor = start.first;
		
		numbersOfDFS[graph.map.get(start.name)] = count[0];
		back[graph.map.get(start.name)] = count[1];
		
		while (neighbor != null) {
			
			if (visited[neighbor.fnum] == false) {
				
				count[0]++;
				count[1]++;
				
				connectors = DFS(connectors, graph, graph.members[neighbor.fnum], visited, count, numbersOfDFS, back, backward, false);
				
				if (numbersOfDFS[graph.map.get(start.name)] <= back[neighbor.fnum]) {
					
					if ((connectors.contains(start.name) == false && backward.contains(start.name)) || (connectors.contains(start.name) == false && started == false)) {
						connectors.add(start.name);
					}
				}
				else {
					int first = back[graph.map.get(start.name)];

					int second = back[neighbor.fnum];
					
					if (first < second) {
						back[graph.map.get(start.name)] = first;
					}
					else {
						back[graph.map.get(start.name)] = second;
					} 
				}		
			backward.add(start.name);
			}
			else {
				int third = back[graph.map.get(start.name)];
				
				int fourth = numbersOfDFS[neighbor.fnum];
				
				if (third < fourth) {
					back[graph.map.get(start.name)] = third;
				}
				else {
					back[graph.map.get(start.name)] = fourth;
				}
			}
			neighbor = neighbor.next;
		}
		return connectors;
	}
}