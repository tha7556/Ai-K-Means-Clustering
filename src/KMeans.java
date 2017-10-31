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
			for(Cluster cluster : clusters) {
				double change = cluster.updateCentroid();
				if(change > biggestChange)
					biggestChange = change;
			}
			System.out.println(biggestChange);
			if(biggestChange <= target) {
				System.out.println("all done!");
				break;
			}
		}
	}
	public static void main(String[] args) {
		ArrayList<Point> points = new ArrayList<Point>(10);
		Random r = new Random();
		for(int i = 0; i < 10; i++)
			points.add(new Point(r.nextDouble(),r.nextDouble()));
		KMeans kmeans = new KMeans(3,.0001,points);
		kmeans.run();
	}
}
