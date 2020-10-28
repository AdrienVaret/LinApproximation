package solver;

import java.io.IOException;
import java.util.ArrayList;

import graphs.Molecule;
import parser.GraphParser;

public class TestNauty {

	public static void main(String [] args) throws IOException {
		String path = "/Users/adrien/molecules/molecules_CP/molecule_26.graph_coord";
		Molecule molecule = GraphParser.parseUndirectedGraph(path, null, true);
		ArrayList<ArrayList<Integer>> orbits = molecule.getOrbits();
		for (ArrayList<Integer> orbit : orbits)
			System.out.println(orbit);
	}
}
