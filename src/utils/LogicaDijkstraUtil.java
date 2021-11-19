package utils;

import java.util.ArrayList;
import java.util.List;

public class LogicaDijkstraUtil {

	private static List<Vertice> vertices = new ArrayList<>();

	public static List<Vertice> getVertices() {
		return vertices;
	}

	public static void setVertices(List<Vertice> vertices) {
		LogicaDijkstraUtil.vertices = vertices;
	}

}