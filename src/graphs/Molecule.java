package graphs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import utils.Interval;
import utils.RelativeMatrix;

import static java.lang.ProcessBuilder.Redirect.appendTo;

public class Molecule {
	
	private RelativeMatrix nodesMem; //DEBUG
	
	private int nbNodes, nbEdges, nbHexagons, nbStraightEdges, maxIndex;
	private ArrayList<ArrayList<Integer>> edgeMatrix;
	private int [][] adjacencyMatrix;
	private ArrayList<String> edgesString;
	private ArrayList<String> hexagonsString;
	private Node [] nodesRefs;
	private RelativeMatrix coords;
	private int [][] hexagons;
	private int [][] dualGraph;
	
	private ArrayList<ArrayList<Integer>> hexagonsVertices;

	/**
	 * Constructors
	 */
	
	public Molecule(int nbNodes, int nbEdges, int nbHexagons, ArrayList<ArrayList<Integer>> edgeMatrix,
			int[][] adjacencyMatrix, ArrayList<String> edgesString, ArrayList<String> hexagonsString,
			Node[] nodesRefs, RelativeMatrix coords) {

		this.nbNodes = nbNodes;
		this.nbEdges = nbEdges;
		this.nbHexagons = nbHexagons;
		this.edgeMatrix = edgeMatrix;
		this.adjacencyMatrix = adjacencyMatrix;
		this.edgesString = edgesString;
		this.hexagonsString = hexagonsString;
		this.nodesRefs = nodesRefs;
		this.coords = coords;
		
		hexagons = new int[nbHexagons][6];
		initHexagons();
		
		computeDualGraph();
	}
	
	public Molecule(int nbNodes, int nbEdges, int nbHexagons, ArrayList<ArrayList<Integer>> edgeMatrix,
			int[][] adjacencyMatrix, ArrayList<String> edgesString, ArrayList<String> hexagonsString,
			Node[] nodesRefs, RelativeMatrix coords, RelativeMatrix nodesMem, int maxIndex) {

		this.nbNodes = nbNodes;
		this.nbEdges = nbEdges;
		this.nbHexagons = nbHexagons;
		this.edgeMatrix = edgeMatrix;
		this.adjacencyMatrix = adjacencyMatrix;
		this.edgesString = edgesString;
		this.hexagonsString = hexagonsString;
		this.nodesRefs = nodesRefs;
		this.coords = coords;
		this.nodesMem = nodesMem;
		this.maxIndex = maxIndex;
		
		hexagons = new int[nbHexagons][6];
		initHexagons();

		nbStraightEdges = 0;

		for (int i = 0 ; i < adjacencyMatrix.length ; i++){
			for (int j = (i+1) ; j < adjacencyMatrix[i].length ; j++){
				if (adjacencyMatrix[i][j] == 1){
					Node u1 = nodesRefs[i];
					Node u2 = nodesRefs[j];

					if (u1.getX() == u2.getX())
						nbStraightEdges ++;
				}
			}
		}
		
		computeDualGraph();
	}
	
	/**
	 * Getters and setters
	 */
	
	public int getNbNodes() {
		return nbNodes;
	}

	public int getNbEdges() {
		return nbEdges;
	}


	public int getNbHexagons() {
		return nbHexagons;
	}

	public int getMaxIndex() {
		return maxIndex;
	}

	public ArrayList<ArrayList<Integer>> getEdgeMatrix() {
		return edgeMatrix;
	}

	public int[][] getAdjacencyMatrix(){
		return adjacencyMatrix;
	}
	
	public ArrayList<String> getEdgesString() {
		return edgesString;
	}

	public ArrayList<String> getHexagonsString() {
		return hexagonsString;
	}
	
	public Node getNodeRef(int index) {
		return nodesRefs[index];
	}
	
	public RelativeMatrix getCoords() {
		return coords;
	}
	
	public Node[] getNodesRefs() {
		return nodesRefs;
	}

	public ArrayList<ArrayList<Integer>> getHexagonsVertices() {
		return hexagonsVertices;
	}

	public int getNbStraightEdges(){
		return nbStraightEdges;
	}

	public int [][] getHexagons(){
		return hexagons;
	}
	
	/**
	 * Class's methods
	 */
	
private void computeDualGraph() {
		
		dualGraph = new int [nbHexagons][6];
	
		for (int i = 0 ; i < nbHexagons ; i++)
			for (int j = 0 ; j < 6 ; j++)
				dualGraph[i][j] = -1;
		
		ArrayList<Integer> candidats = new ArrayList<Integer>();
		candidats.add(0);
		
		int index = 0;
		
		while (index < nbHexagons) {
		
			int candidat = candidats.get(index);
			int [] candidatHexagon = hexagons[candidat];
			
			for (int i = 0 ; i < candidatHexagon.length ; i++) {
				
				int u = candidatHexagon[i];
				int v = candidatHexagon[(i+1) % 6];
				
				System.out.print("");
				
				for (int j = 0 ; j < nbHexagons ; j++) {
					if (j != candidat) { //j != i avant
						
						int contains = 0;
						for (int k = 0 ; k < 6 ; k++) {
							if (hexagons[j][k] == u || hexagons[j][k] == v)
								contains ++;
						}
						
						if (contains == 2) {
							
							dualGraph[candidat][i] = j;
							
							if (!candidats.contains(j))
								candidats.add(j);
							
							break;
						}
					}
				}
				
			}
			index ++;
		}
	}
	
	public void exportToGraphviz(String outputFileName) {
		//COMPIL: dot -Kfdp -n -Tpng -o test.png test
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(new File(outputFileName)));
			
			w.write("graph{" + "\n");
			
			for (int i = 1 ; i <= nodesRefs.length ; i++) {
				w.write("\t" + i + " [pos=\"" + nodesRefs[(i-1)].getX() + "," + nodesRefs[(i-1)].getY() + "!\"]" + "\n" );
			}
			
			w.write("\n");
			
			for (int i = 0 ; i < adjacencyMatrix.length ; i++) {
				for (int j = i + 1 ; j < adjacencyMatrix[i].length ; j++) {
					
					if (adjacencyMatrix[i][j] != 0)
						w.write("\t" + (i) + " -- " + (j) + "\n");
					
				}
			}
			
			w.write("}");
			
			w.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public RelativeMatrix getNodesMem() {
		return nodesMem;
	}
	
	private int findHexagon(int u, int v) {

		for (int i = 0 ; i < nbHexagons ; i++) {
			int [] hexagon = hexagons[i];
			
			if (hexagon[4] == u && hexagon[5] == v)
				return i;
		}
		
		return -1;
		
	}
	
	private ArrayList<Integer> findHexagons(int hexagon, Interval interval) {
		
		ArrayList<Integer> hexagons = new ArrayList<Integer>();
		int size = interval.size() / 2;
		
		hexagons.add(hexagon);
		
		int newHexagon = hexagon;
		
		for (int i = 0 ; i < size ; i++) {
			
			newHexagon = dualGraph[newHexagon][1];
			hexagons.add(newHexagon);
		}
		
		return hexagons;
		
	}
	
	public ArrayList<Integer> getAllHexagonsOfIntervals(ArrayList<Interval> intervals) {
		
		
		ArrayList<Integer> hexagons = new ArrayList<Integer>();
		
		for (Interval interval : intervals) {
			
			int hexagon = findHexagon(interval.x1(), interval.y1());
			hexagons.addAll(findHexagons(hexagon, interval));
		}
		
		return hexagons;
	}
	
	public void initHexagons() {

		hexagonsVertices = new ArrayList<ArrayList<Integer>>();

		for (int i = 0 ; i < nbNodes ; i++)
			hexagonsVertices.add(new ArrayList<Integer>());

		for (int i = 0 ; i < nbHexagons ; i++) {
			String hexagon = hexagonsString.get(i);
			String [] sHexagon = hexagon.split(" ");
			
			for (int j = 1 ; j < sHexagon.length ; j++) {
				String [] sVertex = sHexagon[j].split(Pattern.quote("_"));
				int x = Integer.parseInt(sVertex[0]);
				int y = Integer.parseInt(sVertex[1]);
				hexagons[i][j-1] = coords.get(x, y);
				hexagonsVertices.get(coords.get(x, y)).add(i);
			}
		}
	}
	
	public ArrayList<ArrayList<Integer>> getOrbits(String nautyDirectory) throws IOException {
		
		String tmpFilename = nautyDirectory + "/" + "tmp_nauty";
		String outputFilename = nautyDirectory + "/" + "output";
		
		System.out.println("tmpFilename : " + tmpFilename);
		System.out.println("outputFilename : " + outputFilename);
		System.out.println("");
		
		exportToNautyScript(tmpFilename);
		
		System.out.println("running : " + nautyDirectory + "/nauty26r12/dreadnaut");
		
		ProcessBuilder pb = new ProcessBuilder(nautyDirectory + "/nauty26r12/dreadnaut");
		pb.redirectInput(new File(tmpFilename));
		pb.redirectOutput(appendTo(new File(outputFilename)));
		Process p = pb.start();
		
		while(p.isAlive()) {}	
		
		BufferedReader r = new BufferedReader(new FileReader(new File(outputFilename)));
		StringBuilder output = new StringBuilder();
		String line = null;
		boolean add = false;
		
		while ((line = r.readLine()) != null) {
			String [] splittedLine = line.split(" ");
			if (add) 
				output.append(line);
			else if (splittedLine.length > 0 && splittedLine[0].equals("cpu"))
				add = true;
		}
		
		r.close();
		
		String orbitsStr = output.toString().trim().replaceAll(" +", " ");
		
		System.out.println("orbitsStr : " + orbitsStr);
		
		
		ProcessBuilder rm = new ProcessBuilder("rm", tmpFilename, outputFilename);
		Process prm = rm.start();
		
		while(prm.isAlive()) {}
		
		ArrayList<ArrayList<Integer>> orbits = new ArrayList<ArrayList<Integer>>();
		String [] splittedOrbits = orbitsStr.split(Pattern.quote("; "));
		
		for (int i = 0 ; i < splittedOrbits.length ; i++) {
			
			String orbitStr;
			if (i < splittedOrbits.length - 1)
				orbitStr = splittedOrbits[i];
			else
				orbitStr = splittedOrbits[i].substring(0, splittedOrbits[i].length() - 1);
			
			String [] splittedOrbit = orbitStr.split(" ");
			
			ArrayList<Integer> orbit = new ArrayList<Integer>();
			
			if (splittedOrbit.length == 1)
				orbit.add(Integer.parseInt(splittedOrbit[0]));
			else {
				for (int j = 0 ; j < splittedOrbit.length - 1 ; j ++) {
					orbit.add(Integer.parseInt(splittedOrbit[j]));
				}
			}
			
			orbits.add(orbit);
				
		}
		
		return orbits;
	}
	
	public void exportToNautyScript(String outputFilename) throws IOException{
		
		BufferedWriter w = new BufferedWriter(new FileWriter(new File(outputFilename)));
		
		w.write("n = " + nbHexagons + "\n");
		w.write("g" + "\n");
		
		ArrayList<ArrayList<Integer>> neighbors = new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0 ; i < dualGraph.length ; i++)
			neighbors.add(new ArrayList<Integer>());
		
		for (int i = 0 ; i < dualGraph.length ; i++) {
			for (int j = 0 ; j < dualGraph[i].length ; j++) {
				int v = dualGraph[i][j];
				if (v != -1) {
					if (!neighbors.get(v).contains(i))
						neighbors.get(i).add(v);
				}
			}
		}
		
		for (int i = 0 ; i < dualGraph.length ; i++) {
			w.write(i + " : ");
			for (Integer u : neighbors.get(i)) {
				w.write(u + " ");
			}
			w.write(";\n");
		}
		
		w.write("x\n");
		w.write("o\n");
		
		w.close();
	}
}
