# ChristofidesAlgorithm
Eulerian Walk Approximation Method Within a Factor of 1.5 

A *Eulerian walk* on a graph is a walk that includes each edge of the graph exactly once. Our next algorithm depends on the following elementary theorem in graph theory: every vertex of a connected graph G has even degree iff G has a Eulerian walk.

As an aside, it’s easy to see that a Eulerian walk can only exist if all nodes of a graph have even
degree: Every time the walk passes through a node it must use two edges (one to enter the node and
one to exit). No edges are traversed twice in the walk, so if a node is visited c times it must have degree
2c, an even number.

##Using the Eulerian walk theorem, we can get a factor 1.5 approximation. This approach is called the
Christofides algorithm:

1. Find the MST of the given graph G.
2. Identify all odd-degree nodes in the MST
Another elementary theorem in graph theory says that the number of odd-degree nodes in a graph
is even. It’s easy to see why this is the case: The sum of the degrees of all nodes in a graph is
twice the number of edges in the graph, because each edge increases the degree of both its attached
nodes by one. Thus, the sum of degrees of all nodes is even. For a sum of integers to be even it
must have an even number of odd terms, so we have an even number of odd-degree nodes.
3. Do minimum cost perfect matching on the odd-degree nodes in the MST
A matching is a subset of a graph’s edges that do not share any nodes as endpoints. A perfect
matching is a matching containing all the nodes in a graph (a graph may have many perfect
matchings). A minimum cost perfect matching is a perfect matching for which the sum of edge
weights is minimum. A minimum cost perfect matching of a graph can be found in polynomial
time.
4. Add the matching edges to the MST.
This may produce “doubled” edges, which are pairs of edges joining the same pair of nodes. We
will allow doubled edges for now. Observe that in the graph produced at this step, all nodes are
of even degree.
5. Do a Eulerian walk on the graph from the previous step.
By the Eulerian walk theorem stated earlier, a Eulerian walk exists on this graph. Moreover, we
claim without proof that it can be found in polynomial time.
Shortcut the Eulerian walk.
6. Since the Eulerian walk traverses all nodes in the graph, we can shortcut this walk to produce a
tour of the graph of total weight less than or equal to that of the Eularian walk. The proof of this
is the same as the one used for shortcutting in the previous triangle inequality TSP approximation
algorithm.
