import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Point {
	private double x, y;
	private Cluster cluster;
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public Cluster getCluster() {
		return cluster;
	}
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	public static double calcDistance(Point a, Point b) {
		return Math.sqrt(Math.pow((b.getX()-a.getX()), 2)+Math.pow((b.getY()-a.getY()), 2));
	}
	public static ArrayList<Point> getPointsFromFile(String fileName) {
		ArrayList<Point> points = new ArrayList<Point>();
		try {
			Scanner scanner = new Scanner(new File(fileName));
			scanner.nextLine();
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] args = line.split("\t");
				double x = Double.parseDouble(args[0].trim());
				double y = Double.parseDouble(args[1].trim());
				points.add(new Point(x,y));
			}
			scanner.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return points;
	}
	public static void writePointsToFile(String fileName,ArrayList<Point> points) {
		try {
			File outFile = new File (fileName); 
			FileWriter fWriter = new FileWriter (outFile); 
			PrintWriter pWriter = new PrintWriter (fWriter); 
			
			for(Point p : points) {
				pWriter.println(p.getX()+","+p.getY());
			}
			pWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public String toString() {
		return ("("+x+","+y+")");
	}
	public static void main(String[] args) {
		ArrayList<Point> points = Point.getPointsFromFile("xyz.txt");
		writePointsToFile("points.csv",points);
	}
}
