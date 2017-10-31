import java.util.ArrayList;
import java.util.Random;

public class KMeans {
	private int k;
	private double target;
	private ArrayList<Point> points;
	private Cluster[] clusters;
	public KMeans(int k, double target, ArrayList<Point> points) {
		this.k = k;
		this.target = target;
		this.points = points;
		this.clusters = new Cluster[k];
		Point[] rands = selectRandomPoints(points,k);
		for(int i = 0; i < rands.length; i++) {
			ArrayList<Point> p = new ArrayList<Point>();
			p.add(rands[i]);
			clusters[i] = new Cluster(p);
		}
	}
	public Point[] selectRandomPoints(ArrayList<Point> points, int n) {
		Random r = new Random();
		ArrayList<Integer> rands = new ArrayList<Integer>(n);
		for(int i = 0; i < n; i++) {
			rands.add(-1);
			int num = -1;
			while(rands.contains(num)) {
				num = r.nextInt(points.size());
			}
			rands.set(i, num);
		}
		Point[] result = new Point[n];
		for(int i = 0; i < result.length; i++)
			result[i] = points.get(rands.get(i));
		return result;
	}
	public void run() {
		int loops = 0;
		ArrayList<ArrayList<Point>> centroids = new ArrayList<ArrayList<Point>>(k);
		for(int i = 0; i < k; i++) {
			centroids.add(new ArrayList<Point>());
		}
		while(true) {
			loops ++;
			for(Point point : points) {
				double smallest = Double.MAX_VALUE;
				int index = -1;
				for(int i = 0; i < clusters.length; i++) {
					double distance = Point.calcDistance(point, clusters[i].getCentroid());
					if(distance < smallest) {
						index = i;
						smallest = distance;
					}
				}
				clusters[index].addPoint(point);
			}
			double biggestChange = 0.0;
			for(int i = 0; i < clusters.length; i++) {
				centroids.get(i).add(clusters[i].getCentroid());
				double change = clusters[i].updateCentroid();
				if(change > biggestChange)
					biggestChange = change;
			}
			System.out.println(loops +": "+ biggestChange);
			if(biggestChange <= target) {
				System.out.println("all done!");
				break;
			}
		}
		for(int i = 0; i < centroids.size(); i++) {
			Point.writePointsToFile("Centroid"+(i+1)+".csv", centroids.get(i));
		}
		for(int i = 0; i < clusters.length; i++) {
			Point.writePointsToFile("Cluster"+(i+1)+".csv", clusters[i].getPoints());
		}
	}
	public static void main(String[] args) {
		ArrayList<Point> points = Point.getPointsFromFile("xyz.txt");
		KMeans kmeans = new KMeans(3,.000001,points);
		kmeans.run();
	}
}
