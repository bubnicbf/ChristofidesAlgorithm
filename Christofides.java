import java.util.*;

public class Christofides{

    private boolean verbose;

    /**
     * Constructor that sets verbose to the given value.
     *
     * @param verbose   True or false depending on the users wish of seeing values that are processed.
     * @since           1.0
     */

    public Christofides(boolean verbose){
				this.verbose=verbose;
    }

    /**
     * This is the method that starts the algorithm, and gives back the answer.
     *
     * @param x			The x coordinates of the cities.
     * @param y			The y coordinates of the cities.
     * @return          The path of the travelling salesman.
     * @since           1.0
     */

//    public int[] solve(double[] x, double[] y){
	  public int[] solve(double[][] weightMatrix){

//				double[][] weightMatrix=buildWeightMatrix(x,y);
				int mst[]=prim(weightMatrix, weightMatrix[0].length);

				int match[][] = greadyMatch(mst,weightMatrix,weightMatrix[0].length);
				
				GraphNode nodes[] = buildMultiGraph(match, mst);
				
				int route[] = getEulerCircuit(nodes);
				
				double sum=0;

				for(int i=1;i<route.length;i++){
						sum+=weightMatrix[route[i-1]][route[i]];
				}
				sum+=weightMatrix[route[0]][route[route.length-1]];
				System.out.println("Summan: "+sum);

				return route;
		}

		/**
     * Builds the union of MST and MATCH, which is a multi graph
     *
     * @param nodes	    Multigraph with only even degree nodes.
     * @return          Euler circuit with shortcuts
     * @since           1.0
     */

		private int[] getEulerCircuit(GraphNode nodes[]) {
				LinkedList path=new LinkedList();
				Vector tmpPath = new Vector();
				int j=0;
				
				//Add the first cycle in the path, getNextChild goes depth first
				nodes[0].getNextChild( nodes[0].getName(), tmpPath, true );
				path.addAll(0, tmpPath);
				
				//go through all the nodes in our path, if the node has more outgoing edges so check cycles after this. stop the bike in the right place
				while(j < path.size()) {
						if(nodes[((Integer)path.get(j)).intValue()].hasMoreChilds()) {
								nodes[((Integer)path.get(j)).intValue()].getNextChild( nodes[((Integer)path.get(j)).intValue()].getName(),tmpPath,true );
								if(tmpPath.size()>0) {
										//reassemble the path and tmpPath
										for(int i = 0; i < path.size(); i++) {
												if( ((Integer)path.get(i)).intValue() == ((Integer)tmpPath.elementAt(0)).intValue() ) {
														path.addAll(i, tmpPath);
														break;
												}
										}
										tmpPath.clear();
								}
								j = 0;
						}
						else j++;
				}
					
				//find shortcuts Eulerian walk
				boolean inPath[]=new boolean[nodes.length];
				int[] route=new int[nodes.length];
				j=0;
				for(int i=0;i<path.size();i++){
						if(!inPath[((Integer)path.get(i)).intValue()]){
								route[j]=((Integer)path.get(i)).intValue();
								j++;
								inPath[((Integer)path.get(i)).intValue()]=true;
						}
				}
				//if(j!=nodes.length) System.out.println("Warning! constructed route does not contain all nodes");

				return route;				
		}

		/**
     * Builds the union of MST and MATCH, which is a multi graph
     *
     * @param match	   The "minimum" perfect match on the set of odd nodes.
     * @param mst		   The minimal spanning tree
     * @return          One dimensional nodes matrix representing the multi graph
     * @since           1.0
     */

		private GraphNode[] buildMultiGraph(int[][] match, int[] mst) {
				GraphNode nodes[]=new GraphNode[mst.length];
				//create empty nodes
				for(int i=0;i<mst.length;i++){
						nodes[i]=new GraphNode(i);
				}
				
				//add nodes and edges of the MST, symmetrical edges
				for(int i=1;i<mst.length;i++){
						nodes[i].addChild(nodes[mst[i]]);
						nodes[mst[i]].addChild(nodes[i]);				}
				
				//add nodes and edges from MATCHING, symmetrical edges
				for(int i=0;i<match.length;i++){
						nodes[match[i][0]].addChild(nodes[match[i][1]]);
						nodes[match[i][1]].addChild(nodes[match[i][0]]);
						if(verbose) System.out.println(match[i][0]+"-"+match[i][1]);
				}
				
				return nodes;
		}

    /**
     * Builds up the weightmatrix from the coordinates. Calculates distance between all pairs.
     *
     * @param x		   X-coordinates
     * @param y		   Y-coordinates
     * @return          Two-dimensional weightmatrix.
     * @since           1.0
     */

    private double[][] buildWeightMatrix(double[] x, double[] y){
				int dim=x.length;
				double[][] wt=new double[dim][dim];
				double dist;

				for(int u1=0;u1<dim;u1++){
						for(int u2=0;u2<dim;u2++){
								if(u1==u2){
										wt[u1][u2]=0.0;
										wt[u2][u1]=0.0;
										continue;
								}
								dist=Math.sqrt(
															 Math.pow( (x[u1] - x[u2]) ,2.0) +
															 Math.pow( (y[u1] - y[u2]) ,2.0)
															 );
								wt[u1][u2]=dist;
								wt[u2][u1]=dist;
						}
				}

				return wt;
    }

    /**
     * Using Prim's algorithm to find the Minimal Spanning Tree.
     *
     * @param wt        Weightmatrix.
     * @param dim       Number of dimensions in the problem.
     * @return          The parentvector. p[i] gives the parent of node i.
     * @since           1.0
     */

    public int[] prim(double[][] wt,int dim){

				Vector queue=new Vector();
				for(int i=0;i<dim;i++)
						queue.add(new Integer(i));

				// Prim's algorithm
				boolean isInTree[] = new boolean[dim];
				double key[]=new double[dim]; //distance from node to node and parent [i]
				int p[]=new int[dim]; //parent

				for(int i=0;i<dim;i++){
						key[i]=Integer.MAX_VALUE;
				}

				key[0]=0; // root-node
				int u=0;

				double temp;
				Integer elem;
				do{
						isInTree[u] = true; //add the node in the tree
						queue.removeElement(new Integer(u));
						for(int v=0;v<dim;v++){ //can be simplified if it is not a complete graph
								if( !isInTree[v] && wt[u][v]<key[v] ){
										p[v]=u;
										key[v]=wt[u][v];
								}
						}

						// ExtractMin goes through all the remaining nodes and removing it with the shortest distance to the tree
						double mint=Double.MAX_VALUE;
						for(int i=0;i<queue.size();i++){
								elem=(Integer)queue.elementAt(i); //ineffective
								temp=key[elem.intValue()];
								if(temp<mint){
										u=elem.intValue();
										mint=temp;
								}
						}
				} while(!queue.isEmpty());

				if(verbose){
						System.out.print("Key-vektor: ");
						for(int i=0;i<dim;i++){
								System.out.print(key[i]+" ");
						}
						System.out.print("\n\n");
						System.out.print("Parent:     ");
						for(int i=0;i<dim;i++){
								System.out.print(p[i]+" ");
						}
						System.out.print("\n");
						double sum=0;
						for(int g=0;g<dim;g++){
								sum+=key[g];
						}

						System.out.println("\n\n"+sum);
				}
				
				return p;
    }

    /**
     * Finds a match between the nodes that hava odd number of edges. Not perfect that gready, that is take the
     * shortest distance found first. Then the next shortest of the remaining i chosen.
     *
     * @param p	       Parentvector. p[i] gives the parent of node i.
     * @param wt        Weightmatrix of the complete graph.
     * @param dim	   Number of dimensions in the problem.
     * @return 		   Twodimensional matrix containing the pairs. Two columns where each row represent a pair.
     * @since           1.0
     */

    public int[][] greadyMatch(int[] p,double[][] wt,int dim){

				Node nodes[] = new Node[p.length];

				//create the forest
				nodes[0] = new Node(0, true); //roten
				for(int i =1; i<p.length;i++) {
						nodes[i] = new Node(i,false);
				}

				//building a tree in the forest
				for(int i = 0; i<p.length;i++) {
						if(p[i]!=i)
								nodes[p[i]].addChild(nodes[i]);
				}

				//find the odd nodes
				ArrayList oddDegreeNodes = findOddDegreeNodes(nodes[0]);
				int nOdd = oddDegreeNodes.size();

				if(verbose) {
						System.out.println("Udda noder:");
						for(int i = 0; i < nOdd; i++)
								System.out.print(oddDegreeNodes.get(i)+", ");
						System.out.println();
				}

				//try to find a match so minimal as possible with a greedy algorithm to sort all the edges between the odd corners
				Edge edges[][] = new Edge[nOdd][nOdd];
				for(int i = 0; i < nOdd; i++) {
						for(int j = 0; j < nOdd; j++) {
								if( ((Integer)oddDegreeNodes.get(i)).intValue() != ((Integer)oddDegreeNodes.get(j)).intValue() )
										edges[i][j] = new Edge( ((Integer)oddDegreeNodes.get(i)).intValue(),
																						((Integer)oddDegreeNodes.get(j)).intValue(),
																						wt[((Integer)oddDegreeNodes.get(i)).intValue()][((Integer)oddDegreeNodes.get(j)).intValue()] );
								else
										edges[i][j] = new Edge( ((Integer)oddDegreeNodes.get(i)).intValue(),
																						((Integer)oddDegreeNodes.get(j)).intValue(),Double.MAX_VALUE );
						}
						Arrays.sort(edges[i]); //browse all edges from node i
				}

				boolean matched[] = new boolean[dim];
				int match[][] = new int[(nOdd/2)][2];

				// for each corner, pick the shortest edge in crash select the shortest of the second shortest number of nodes with odd degrees always divisible by 2
				int k = 0;
				for(int i = 0; i < nOdd; i++) {
						for(int j = 0; j < nOdd; j++) {
								if( matched[edges[i][j].getFrom()] || matched[edges[i][j].getTo()] )
										continue;
								else {
										matched[edges[i][j].getFrom()] = true;
										matched[edges[i][j].getTo()] = true;
										match[k][0] = edges[i][j].getFrom();
										match[k][1] = edges[i][j].getTo();
										k++;
								}
						}
				}

				if(verbose) {
						System.out.println("Matchning");
						for(int i = 0; i < nOdd/2; i++) {
								System.out.println(match[i][0] + "-" + match[i][1]);
						}
				}

				return match;
		}

    /**
     * Activates the treetraversing-routine that builds the path given by DFS.
     *
     * @param _root	   The root which is the start node of the route.
     * @return          The route which is the order of nodes after the traversing.
     * @since           1.0
     */

    private ArrayList buildRoute(Node _root) {
				ArrayList route = new ArrayList();
				_root.visitBuildRoute(route);
				return route;
    }


    /**
     * Activates the routine that finds vertexes which have odd number of edges.
     *
     * @param _root     Startnode.
     * @return          List of nodes with odd number of edges.
     * @since           1.0
     */

    private ArrayList findOddDegreeNodes(Node _root) {
				ArrayList oddNodes = new ArrayList();
				_root.visitFindOddDegreeNodes(oddNodes);
				return oddNodes;
    }
}
