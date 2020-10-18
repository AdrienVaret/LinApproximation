package solver;

import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.chocosolver.graphsolver.GraphModel;
import org.chocosolver.graphsolver.variables.UndirectedGraphVar;
import org.chocosolver.util.objects.graphs.UndirectedGraph;
import org.chocosolver.util.objects.setDataStructures.SetType;
import graphs.Node;
import graphs.NodeSameLine;
import graphs.UndirGraph;
import parser.GraphParser;
import perfect_matching.PerfectMatchingSolver;
import utils.EdgeSet;
import utils.Interval;
import utils.SubMolecule;
import utils.Utils;
import org.chocosolver.solver.search.strategy.strategy.*;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMin;
import org.chocosolver.solver.search.strategy.selectors.variables.FirstFail;

public class Approximation {

	private static BufferedWriter log;
	
	private static final int MAX_CYCLE_SIZE = 4;
	private static long computeCyclesTime;
	
	public static int [][][] energies = new int[127][11][4];
	public static int [][] circuits;		
	public static int [] circuitCount;
	
	public static ArrayList<ArrayList<Integer>> computeAllCycles(UndirGraph molecule) {
		
		ArrayList<ArrayList<Integer>> cycles = new ArrayList<>();

		int [] firstVertices = new int [molecule.getNbEdges()];
		int [] secondVertices = new int [molecule.getNbEdges()];
		
		GraphModel model = new GraphModel("Cycles");

		UndirectedGraph GLB = new UndirectedGraph(model, molecule.getNbNodes(), SetType.BITSET, false);
		UndirectedGraph GUB = new UndirectedGraph(model, molecule.getNbNodes(), SetType.BITSET, false);

		for (int i = 0; i < molecule.getNbNodes(); i++) {
			GUB.addNode(i);

			for (int j = (i + 1); j < molecule.getNbNodes(); j++) {
				if (molecule.getAdjacencyMatrix()[i][j] == 1) {
					GUB.addEdge(i, j);
				}
			}
		}

		UndirectedGraphVar g = model.graphVar("g", GLB, GUB);

		BoolVar[] boolEdges = new BoolVar[molecule.getNbEdges()];
			
		int index = 0;
		for (int i = 0 ; i < molecule.getNbNodes() ; i++) {
			for (int j = (i+1) ; j < molecule.getNbNodes() ; j++) {

				if (molecule.getAdjacencyMatrix()[i][j] == 1) {
					boolEdges[index] = model.boolVar("(" + i + "--" + j + ")");
					model.edgeChanneling(g, boolEdges[index], i, j).post();
					firstVertices[index] = i;
					secondVertices[index] = j;
					index ++;
				}
			}
		}
			
		model.minDegrees(g, 2).post();
		model.maxDegrees(g, 2).post();
		model.connected(g).post();
			
		
		model.or(
			model.and(model.arithm(model.nbNodes(g), "=", 6), model.sum(boolEdges, "=", 6)),
			model.and(model.arithm(model.nbNodes(g), "=", 10), model.sum(boolEdges, "=", 10)),
			model.and(model.arithm(model.nbNodes(g), "=", 14), model.sum(boolEdges, "=", 14)),
			model.and(model.arithm(model.nbNodes(g), "=", 18), model.sum(boolEdges, "=", 18)),
			model.and(model.arithm(model.nbNodes(g), "=", 22), model.sum(boolEdges, "=", 22)),
			model.and(model.arithm(model.nbNodes(g), "=", 26), model.sum(boolEdges, "=", 26))
		).post();
			
		model.getSolver().setSearch(new IntStrategy(boolEdges, new FirstFail(model), new IntDomainMin()));
		Solver solver = model.getSolver();

		Solution solution;
			
		while(solver.solve()){
			solution = new Solution(model);
			solution.record();
					
			ArrayList<Integer> cycle = new ArrayList<Integer>();

			for (int i = 0 ; i < boolEdges.length ; i++) {
				if (solution.getIntVal(boolEdges[i]) == 1) {
					cycle.add(firstVertices[i]);
					cycle.add(secondVertices[i]);
				}
			}

			cycles.add(cycle);
					
		}
		
		return cycles;	
	}
		
	
	public static void computeEnergy(UndirGraph molecule) {

		energies = Utils.initEnergies();
		circuits = new int[molecule.getNbHexagons()][MAX_CYCLE_SIZE];		
		circuitCount = new int[energies.length];
		
		int [] firstVertices = new int [molecule.getNbEdges()];
		int [] secondVertices = new int [molecule.getNbEdges()];
		
		GraphModel model = new GraphModel("Cycles");

		UndirectedGraph GLB = new UndirectedGraph(model, molecule.getNbNodes(), SetType.BITSET, false);
		UndirectedGraph GUB = new UndirectedGraph(model, molecule.getNbNodes(), SetType.BITSET, false);

		for (int i = 0; i < molecule.getNbNodes(); i++) {
			GUB.addNode(i);

			for (int j = (i + 1); j < molecule.getNbNodes(); j++) {
				if (molecule.getAdjacencyMatrix()[i][j] == 1) {
					GUB.addEdge(i, j);
				}
			}
		}

		UndirectedGraphVar g = model.graphVar("g", GLB, GUB);

		BoolVar[] boolEdges = new BoolVar[molecule.getNbEdges()];
			
		int index = 0;
		for (int i = 0 ; i < molecule.getNbNodes() ; i++) {
			for (int j = (i+1) ; j < molecule.getNbNodes() ; j++) {

				if (molecule.getAdjacencyMatrix()[i][j] == 1) {
					boolEdges[index] = model.boolVar("(" + i + "--" + j + ")");
					model.edgeChanneling(g, boolEdges[index], i, j).post();
					firstVertices[index] = i;
					secondVertices[index] = j;
					index ++;
				}
			}
		}
			
		model.minDegrees(g, 2).post();
		model.maxDegrees(g, 2).post();
		model.connected(g).post();
			
		
		model.or(
			model.and(model.arithm(model.nbNodes(g), "=", 6), model.sum(boolEdges, "=", 6)),
			model.and(model.arithm(model.nbNodes(g), "=", 10), model.sum(boolEdges, "=", 10)),
			model.and(model.arithm(model.nbNodes(g), "=", 14), model.sum(boolEdges, "=", 14)),
			model.and(model.arithm(model.nbNodes(g), "=", 18), model.sum(boolEdges, "=", 18)),
			model.and(model.arithm(model.nbNodes(g), "=", 22), model.sum(boolEdges, "=", 22)),
			model.and(model.arithm(model.nbNodes(g), "=", 26), model.sum(boolEdges, "=", 26))
		).post();
			
		model.getSolver().setSearch(new IntStrategy(boolEdges, new FirstFail(model), new IntDomainMin()));
		Solver solver = model.getSolver();

		Solution solution;
			
		while(solver.solve()){
			solution = new Solution(model);
			solution.record();
					
			ArrayList<Integer> cycle = new ArrayList<Integer>();

			for (int i = 0 ; i < boolEdges.length ; i++) {
				if (solution.getIntVal(boolEdges[i]) == 1) {
					cycle.add(firstVertices[i]);
					cycle.add(secondVertices[i]);
				}
			}
			
			treatCycle(molecule, cycle);
		}
		
		displayResult();
	}
	
	public static void displayResult() {
		
		System.out.println("");
		System.out.println("LOCAL ENERGY");
		
		int [] globalEnergy = new int[MAX_CYCLE_SIZE];
		
		for (int i = 0 ; i < circuits.length ; i++) {
			System.out.print("H" + i + " : ");
			
			for (int j = 0 ; j < MAX_CYCLE_SIZE ; j++) {
				
				System.out.print(circuits[i][j] + " ");
				globalEnergy[j] += circuits[i][j];
			}
			
			System.out.println("");
		}
		
		System.out.println("");
		System.out.print("GLOBAL ENERGY : ");
		
		for (int i = 0 ; i < globalEnergy.length ; i++)
			System.out.print(globalEnergy[i] + " ");
		System.out.println("");
		
	}
	
	public static EdgeSet computeStraightEdges(UndirGraph molecule, ArrayList<Integer> cycle) {
		
		List<Node> firstVertices = new ArrayList<Node>();
		List<Node> secondVertices = new ArrayList<Node>();
		
		for (int i = 0 ; i < cycle.size() - 1 ; i += 2) {
			int uIndex = cycle.get(i);
			int vIndex = cycle.get(i + 1);
			
			Node u = molecule.getNodesRefs()[uIndex];
			Node v = molecule.getNodesRefs()[vIndex];
			
			if (u.getX() == v.getX()) {
				firstVertices.add(u);
				secondVertices.add(v);
			}
		}
		
		return new EdgeSet(firstVertices, secondVertices);
	}
	
	public static List<Interval> computeIntervals(UndirGraph molecule, ArrayList<Integer> cycle, EdgeSet edges){
		
		System.out.println(cycle);
		
		if (cycle.toString().equals("[5, 18, 5, 19, 8, 9, 8, 11, 9, 10, 10, 14, 11, 13, 12, 14, 12, 15, 13, 17, 15, 25, 16, 18, 16, 21, 17, 26, 19, 22, 20, 23, 20, 24, 21, 26, 22, 23, 24, 29, 25, 27, 27, 37, 29, 32, 30, 35, 30, 37, 32, 35]"))
			System.out.print("");
		
		List<Interval> intervals = new ArrayList<Interval>();
	
		int [] edgesOK = new int [edges.size()];
		
		for (int i = 0 ; i < edges.size() ; i ++) {
			if (edgesOK[i] == 0) {
				edgesOK[i] = 1;
				Node u1 = edges.getFirstVertices().get(i);
				Node v1 = edges.getSecondVertices().get(i);
				
				int y1 = Math.min(u1.getY(), v1.getY());
				int y2 = Math.max(u1.getY(), v1.getY());
				
				//List<Integer> sameLineNodes = new ArrayList<Integer>();
				List<NodeSameLine> sameLineNodes = new ArrayList<NodeSameLine>();
				
				for (int j = (i+1) ; j < edges.size() ; j++) {
					if (edgesOK[j] == 0) {
						Node u2 = edges.getFirstVertices().get(j);
						Node v2 = edges.getSecondVertices().get(j);
						
						int y3 = Math.min(u2.getY(), v2.getY());
						int y4 = Math.max(u2.getY(), v2.getY());
						
						if (y1 == y3 && y2 == y4) {
							edgesOK[j] = 1;
							//sameLineNodes.add(j);
							sameLineNodes.add(new NodeSameLine(j, u2.getX()));
						}
					}
				}
				
				/**/
				sameLineNodes.add(new NodeSameLine(i, u1.getX()));
				Collections.sort(sameLineNodes);
				
				//if (sameLineNodes.size() == 1) {
				if (sameLineNodes.size() == 2) {
					//intervals.add(new Interval(edges.getFirstVertices().get(i), edges.getSecondVertices().get(i), 
					//			edges.getFirstVertices().get(sameLineNodes.get(0)), edges.getSecondVertices().get(sameLineNodes.get(0))));
				
					Node n1 = edges.getFirstVertices().get(sameLineNodes.get(0).getIndex());
					Node n2 = edges.getSecondVertices().get(sameLineNodes.get(0).getIndex());
					Node n3 = edges.getFirstVertices().get(sameLineNodes.get(1).getIndex());
					Node n4 = edges.getSecondVertices().get(sameLineNodes.get(1).getIndex());
					
					intervals.add(new Interval(n1, n2, n3, n4));
				}
				
				else {
					
					for (int j = 0 ; j < sameLineNodes.size() ; j += 2) {
						
						NodeSameLine nsl1 = sameLineNodes.get(j);
						NodeSameLine nsl2 = sameLineNodes.get(j+1);
						
						Node n1 = edges.getFirstVertices().get(nsl1.getIndex());
						Node n2 = edges.getSecondVertices().get(nsl1.getIndex());
						Node n3 = edges.getFirstVertices().get(nsl2.getIndex());
						Node n4 = edges.getSecondVertices().get(nsl2.getIndex());
						
						intervals.add(new Interval(n1, n2, n3, n4));
					}
					
/*
					int minIndex1 = i;
					int minIndex2 = -1;
					
					int minX1 = edges.getFirstVertices().get(i).getX();
					int minX2 = Integer.MAX_VALUE;
					
					for (Integer j : sameLineNodes) {
						int x = edges.getFirstVertices().get(j).getX();
						
						if (x < minX1) {
							minX2 = minX1;
							minX1 = x;
							minIndex2 = minIndex1;
							minIndex1 = j;
						}
						
						else if (x < minX2 && x > minX1) {
							minX2 = x;
							minIndex2 = j;
						}
					}
					
					Interval interval1 = new Interval(edges.getFirstVertices().get(minIndex1), edges.getSecondVertices().get(minIndex1), 
							                          edges.getFirstVertices().get(minIndex2), edges.getSecondVertices().get(minIndex2));
					
					intervals.add(interval1);
					
					int index1 = -1, index2 = -1;
					
					for (Integer j : sameLineNodes) {
						if (j != minIndex1 && j != minIndex2) {
							if (index1 == -1)
								index1 = j;
							else
								index2 = j;
						}
					}
					
					Interval interval2 = new Interval(edges.getFirstVertices().get(index1), edges.getSecondVertices().get(index1), 
							                          edges.getFirstVertices().get(index2), edges.getSecondVertices().get(index2));
					
					intervals.add(interval2);
					
*/
					
				}
			}
		}
		
		return intervals;
	}
	
	public static boolean hasEdge(UndirGraph molecule, int [] vertices, int vertex) {
		
		for (int u = 0 ; u < molecule.getNbNodes() ; u++) {
			if (molecule.getAdjacencyMatrix()[vertex][u] == 1 && vertices[u] == 0)
				return true;
		}
		
		return false;
	}
	
	public static SubMolecule substractCycleAndInterior(UndirGraph molecule, ArrayList<Integer> cycle, ArrayList<Interval> intervals) {
		
		int [][] newGraph = new int [molecule.getNbNodes()][molecule.getNbNodes()];
		int [] vertices = new int [molecule.getNbNodes()];
		int [] subGraphVertices = new int[molecule.getNbNodes()];
		
		List<Integer> hexagons = getHexagons(molecule, cycle, intervals);
		
		for (Integer hexagon : hexagons) {
			int [] nodes = molecule.getHexagons()[hexagon];
			
			for (int i = 0 ; i < nodes.length ; i++)
				vertices[nodes[i]] = 1;
		}
		
		int subGraphNbNodes = 0;
		
		int nbEdges = 0;
		
		for (int u = 0 ; u < molecule.getNbNodes() ; u++) {
			if (vertices[u] == 0) {
				for (int v = (u+1) ; v < molecule.getNbNodes() ; v++) {
					if (vertices[v] == 0) {
						newGraph[u][v] = molecule.getAdjacencyMatrix()[u][v];
						newGraph[v][u] = molecule.getAdjacencyMatrix()[v][u];
						
						if (molecule.getAdjacencyMatrix()[u][v] == 1)
							nbEdges ++;
						
						if (subGraphVertices[u] == 0) {
								subGraphVertices[u] = 1;
								subGraphNbNodes ++;
						}
						
						if (subGraphVertices[v] == 0) {
								subGraphVertices[v] = 1;
								subGraphNbNodes ++;
						}
					}
				}
			}
		}
		
		return new SubMolecule(subGraphNbNodes, nbEdges, molecule.getNbNodes(), subGraphVertices, newGraph);
	}
	
	public static SubMolecule substractCycle(UndirGraph molecule, ArrayList<Integer> cycle){
		
		int [][] newGraph = new int [molecule.getNbNodes()][molecule.getNbNodes()];
		int [] vertices = new int [molecule.getNbNodes()];
				
		int [] subGraphVertices = new int[molecule.getNbNodes()];
		
		for (Integer u : cycle)
			vertices[u] = 1;
		
		int subGraphNbNodes = 0;
		
		int nbEdges = 0;
		
		for (int u = 0 ; u < molecule.getNbNodes() ; u++) {
			if (vertices[u] == 0) {
				for (int v = (u+1) ; v < molecule.getNbNodes() ; v++) {
					if (vertices[v] == 0) {
						newGraph[u][v] = molecule.getAdjacencyMatrix()[u][v];
						newGraph[v][u] = molecule.getAdjacencyMatrix()[v][u];
						
						if (molecule.getAdjacencyMatrix()[u][v] == 1)
							nbEdges ++;
						
						if (subGraphVertices[u] == 0) {
								subGraphVertices[u] = 1;
								subGraphNbNodes ++;
						}
						
						if (subGraphVertices[v] == 0) {
								subGraphVertices[v] = 1;
								subGraphNbNodes ++;
						}
					}
				}
			}
		}
		
		return new SubMolecule(subGraphNbNodes, nbEdges, molecule.getNbNodes(), subGraphVertices, newGraph);
	}
	
	public static boolean intervalsOnSameLine(Interval i1, Interval i2) {
		return (i1.y1() == i2.y1() && i1.y2() == i2.y2());
	}

	public static List<Integer> getHexagons(UndirGraph molecule, ArrayList<Integer> cycle, ArrayList<Interval> intervals){
		List<Integer> hexagons = new ArrayList<Integer>();

		for (Interval interval : intervals){

			int [] hexagonsCount = new int [molecule.getNbHexagons()];

			for (int x = interval.x1() ; x <= interval.x2() ; x += 2){
				int u1 = molecule.getCoords().get(x, interval.y1());
				int u2 = molecule.getCoords().get(x, interval.y2());

				for (Integer hexagon : molecule.getHexagonsVertices().get(u1)) {
					hexagonsCount[hexagon] ++;
					if (hexagonsCount[hexagon] == 4)
						hexagons.add(hexagon);
				}

				for (Integer hexagon : molecule.getHexagonsVertices().get(u2)){
					hexagonsCount[hexagon] ++;
					if (hexagonsCount[hexagon] == 4)
						hexagons.add(hexagon);
				}
			}
		}

		return hexagons;
	}

	
	public static String displayCycle(ArrayList<Integer> cycle) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (Integer i : cycle) {
			if (!list.contains(i)) list.add(i);
		}
		Collections.sort(list);
		return list.toString();
	}

	public static void treatCycle(UndirGraph molecule, ArrayList<Integer> cycle) {
		
		EdgeSet verticalEdges = computeStraightEdges(molecule, cycle);
		ArrayList<Interval> intervals = (ArrayList<Interval>) computeIntervals(molecule, cycle, verticalEdges);
		Collections.sort(intervals);
		int cycleConfiguration = Utils.identifyCycle(molecule, cycle, intervals);
		
		if (cycleConfiguration != -1) {
			
			circuitCount[cycleConfiguration] ++;
			
			ArrayList<Integer> hexagons = (ArrayList<Integer>) getHexagons(molecule, cycle, intervals);
			SubMolecule subMolecule = substractCycleAndInterior(molecule, cycle, intervals);
			int nbPerfectMatchings = PerfectMatchingSolver.computeNbPerfectMatching(subMolecule);
			
			int [][] energiesCycle = energies[cycleConfiguration];
			
			for (int idHexagon = 0 ; idHexagon < hexagons.size() ; idHexagon++) {
				
				int hexagon = hexagons.get(idHexagon);
				for (int size = 0 ; size < 4 ; size ++) {
					
					if (energiesCycle[idHexagon][size] != 0)
						circuits[hexagon][size] += energiesCycle[idHexagon][size] * nbPerfectMatchings;			
				}
			}
			
		}
	}
	
	public static void computeLocalEnergy(UndirGraph molecule) throws IOException {
		
		energies = Utils.initEnergies();
		int [][] circuits = new int[molecule.getNbHexagons()][MAX_CYCLE_SIZE];		
		int [] circuitCount = new int[energies.length];
		
		List<ArrayList<Integer>> cycles = computeAllCycles(molecule);
		
		for (ArrayList<Integer> cycle : cycles) {
			
			EdgeSet verticalEdges = computeStraightEdges(molecule, cycle);
			ArrayList<Interval> intervals = (ArrayList<Interval>) computeIntervals(molecule, cycle, verticalEdges);
			Collections.sort(intervals);
			int cycleConfiguration = Utils.identifyCycle(molecule, cycle, intervals);
			
			if (cycleConfiguration != -1) {
				
				circuitCount[cycleConfiguration] ++;
				
				ArrayList<Integer> hexagons = (ArrayList<Integer>) getHexagons(molecule, cycle, intervals);
				SubMolecule subMolecule = substractCycleAndInterior(molecule, cycle, intervals);
				int nbPerfectMatchings = PerfectMatchingSolver.computeNbPerfectMatching(subMolecule);
				
				int [][] energiesCycle = energies[cycleConfiguration];
				
				for (int idHexagon = 0 ; idHexagon < hexagons.size() ; idHexagon++) {
					
					int hexagon = hexagons.get(idHexagon);
					for (int size = 0 ; size < 4 ; size ++) {
						
						if (energiesCycle[idHexagon][size] != 0)
							circuits[hexagon][size] += energiesCycle[idHexagon][size] * nbPerfectMatchings;
						
							
					}
				}
				
			} 
		}
		
		System.out.println("");
		System.out.println("LOCAL ENERGY");
		
		int [] globalEnergy = new int[MAX_CYCLE_SIZE];
		
		for (int i = 0 ; i < circuits.length ; i++) {
			System.out.print("H" + i + " : ");
			
			for (int j = 0 ; j < MAX_CYCLE_SIZE ; j++) {
				
				System.out.print(circuits[i][j] + " ");
				globalEnergy[j] += circuits[i][j];
			}
			
			System.out.println("");
		}
		
		System.out.println("");
		System.out.print("GLOBAL ENERGY : ");
		
		for (int i = 0 ; i < globalEnergy.length ; i++)
			System.out.print(globalEnergy[i] + " ");
		System.out.println("");

	}
	
	public static void displayTime() {
		System.out.println("computeCycles() : " + computeCyclesTime + " ms.");
	}
	 
	public static void main(String[] args) throws IOException {
		
		log = new BufferedWriter(new FileWriter(new File("logs.txt")));
		
		String path = "/Users/adrien/molecules/molecules_CP/molecule_22.graph_coord";
		
		//String path = "/Users/adrien/CLionProjects/ConjugatedCycles/molecules/coronnoids/2_crowns.graph_coord";
		//String pathNoCoords = "/Users/adrien/CLionProjects/ConjugatedCycles/molecules/coronnoids/3_crowns.graph";
	/*	
		if (args.length < 1) {
			System.err.println("ERROR: invalid argument(s)");
			System.err.println("USAGE: java -jar Approximation.jar ${input_file_name}");
			System.exit(1);
		}
		
		String path = args[0];
	*/	
		System.out.println("computing " + path + "\n");
		
		UndirGraph molecule = GraphParser.parseUndirectedGraph(path, null, true);
		
		long begin = System.currentTimeMillis();
		computeEnergy(molecule);
		long end = System.currentTimeMillis();
		long time = end - begin;
		System.out.println("time : " + time + " ms.");
		
		log.close();
	}
}
