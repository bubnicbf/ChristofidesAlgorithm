# Christofides Algorithm
Eulerian Walk Approximation Method Within a Factor of 1.5 

A *Eulerian walk* on a graph is a walk that includes each edge of the graph exactly once. Our next algorithm depends on the following elementary theorem in graph theory: every vertex of a connected graph G has even degree iff G has a Eulerian walk.

As an aside, it’s easy to see that a Eulerian walk can only exist if all nodes of a graph have even
degree: Every time the walk passes through a node it must use two edges (one to enter the node and
one to exit). No edges are traversed twice in the walk, so if a node is visited c times it must have degree
2c, an even number.

## Using the Eulerian walk theorem, we can get a factor 1.5 approximation. This approach is called the
Christofides algorithm:

The Christofides algorithm is a heuristic algorithm to solve the Travelling Salesman Problem. The algorithm consists of the following steps:

1. Compute a minimum spanning tree (MST) of the graph.
2. Find the set of vertices with odd degree (called odd vertices).
3. Compute a minimum weight perfect matching on the odd vertices.
4. Combine the MST and the matching to form a multigraph.
5. Compute an Eulerian circuit of the multigraph.
6. Convert the Eulerian circuit to a Hamiltonian circuit by skipping visited nodes.
7. My ChristofidesMain class reads input from the user, passes it to the solve method of the Christofides class, which returns an array representing the shortest path. The solve method takes a two-dimensional weight matrix as input, which is built from the coordinates of the cities.


## Christofides

The Christofides class contains methods to implement each step of the algorithm. The prim method implements step 1 by computing a minimum spanning tree using Prim's algorithm. The greadyMatch method implements step 3 by computing a minimum weight perfect matching on the odd vertices using a greedy algorithm. The buildMultiGraph method combines the MST and the matching to form a multigraph as described in step 4. The getEulerCircuit method computes an Eulerian circuit of the multigraph as described in step 5. The buildRoute and findOddDegreeNodes methods are helper methods used in step 5. The solve method puts all the steps together and returns the shortest path as an array representing the order in which cities should be visited.

The ChristofidesManager class provides some utility methods for handling input/output operations related to the Christofides algorithm.

## ChristofidesManager 

The readDistanceMatrix method reads a distance matrix from a file specified by the input parameter INPUT_FILE, and returns a two-dimensional array of doubles representing the matrix. The method first determines the number of cities in the file by counting the lines, and then populates the matrix by parsing each line of the file.

The printMatrix method prints the contents of a given two-dimensional array of doubles to the console.

## Edge

The Edge class represents an edge in a graph. It contains three attributes: from, to, and cost.

- from and to represent the vertices that the edge connects.
- cost represents the weight or cost of the edge.
The class also implements the Comparable interface, which allows edges to be compared and sorted based on their costs.

The compareTo method compares the cost of two edges and returns 0 if they are equal, 1 if the cost of this edge is greater than the cost of the other edge, and -1 if the cost of this edge is less than the cost of the other edge.

Finally, the class provides getFrom() and getTo() methods to retrieve the vertices that the edge connects.

## Node

The Node class represents a node in a tree, where each node can have zero or more children nodes. The class has three instance variables: isRoot, which indicates if the node is the root of the tree; number, which is an identifier for the node; and children, which is an ArrayList containing the node's children nodes.

The class has two constructors, one that takes only the node's identifier and another that also takes a boolean value indicating whether the node is the root of the tree.

The class has three methods: addChild(), which adds a child node to the current node; visitBuildRoute(), which recursively visits each node in the tree and adds its identifier to an ArrayList in pre-order traversal; and visitFindOddDegreeNodes(), which recursively visits each node in the tree and adds the identifier of any node with an odd number of children to an ArrayList.

## GraphNode

The GraphNode class represents a node in a graph data structure. It has a name attribute that identifies the node and an ArrayList childList that stores its children nodes. It also has a visited attribute which indicates whether the node has been visited during a traversal of the graph.

The class provides methods for adding and removing children nodes, as well as checking if the node has any children. The getNextChild method is used to traverse the graph starting from this node and proceeding along the first best edge until the goal node is reached. The path parameter is a vector that stores the nodes visited during the traversal, and the firstTime parameter is a boolean value that indicates whether this is the first time the method is called.

## TSP Solution

To solve the traveling salesman problem using the code you provided, you can follow these steps:

1. Create a double[][] matrix representing the distances between all the cities you want to visit. You can use the ChristofidesManager class's readDistanceMatrix method to read the matrix from a file, or you can manually create the matrix in your code.

2. Create a Graph object to represent the cities and their connections. You can use the Graph class provided to you.

3. Populate the Graph object with the cities and their connections. To do this, you need to add nodes to the graph using the addNode method, and add edges between the nodes using the addEdge method.

4. Use the Christofides class's getApproximateRoute method to get an approximate solution to the TSP problem. This method takes in the Graph object and the distance matrix, and returns a list of node numbers representing the approximate route.

5. Use the BruteForce class's getBestRoute method to get the best possible route for the TSP problem. This method takes in the Graph object and the distance matrix, and returns a list of node numbers representing the best possible route.

6. Print the approximate route and the best route to the console using the printRoute method in the TSPManager class.

### So for example:

Suppose you have 4 cities labeled A, B, C, and D, and the distances between them are:

- A to B: 5
- A to C: 7
- A to D: 3
- B to C: 2
- B to D: 6
- C to D: 4

Then the distance matrix would be:

|   | A | B | C | D |
|---|---|---|---|---|
| A | 0 | 5 | 7 | 3 |
| B | 5 | 0 | 2 | 6 |
| C | 7 | 2 | 0 | 4 |
| D | 3 | 6 | 4 | 0 |

Note that the diagonal elements of the matrix (i.e., matrix[i][i]) should be 0.

