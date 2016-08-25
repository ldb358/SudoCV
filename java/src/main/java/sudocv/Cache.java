package sudocv;

import java.util.ArrayList;
import java.lang.IndexOutOfBoundsException;

public class Cache {
	ArrayList< ArrayList<Integer> > cache;

	public Cache(){
		cache = new ArrayList< ArrayList<Integer> >();
	}

	public void add(int x, int y, ArrayList<Integer> vals) {
		int pos = y * 9 + x;
		for(int c=cache.size(); c < pos + 1; ++c) {
			cache.add(new ArrayList<Integer>());
		}
		cache.set(pos, vals);
	}
	
	public void add(int x, int y, int val) {
		int pos = y * 9 + x;
		ArrayList<Integer> loc = cache.get(pos);	
		for(int i=0; i < loc.size(); ++i){
			int item = loc.get(i);
			if (item > val) {
				loc.add(i, val);
				break;
			} else if (val == item) {
				break;
			}
		}
	}

	public void del(int x, int y, int val){
		int pos = y * 9 + x;
		ArrayList<Integer> loc = cache.get(pos);
		for(int i=0; i < loc.size(); ++i){
			int item = loc.get(i);
			if(item == val){
				loc.remove(i);
				return;
			}
		}
	}

	public ArrayList<Integer> get(int x, int y)
			throws IndexOutOfBoundsException {
		int pos = y * 9 + x;
		if(pos >= cache.size()){
			throw new IndexOutOfBoundsException(String.format("Invald x %d, y %d", x, y));
		}
		return cache.get(pos);
	}

	public void print() {
		for(int i=0; i < 37; ++i){
			System.out.print("_");
		}
		System.out.println();
		for(int i=0; i < 9; ++i){
			//print the numbers
			System.out.print("| ");
			for(int n=0; n < 9; ++n){
				System.out.printf("%s | ", get(n, i));
			}
			System.out.println();
			//print the separation lines
			for(int z=0; z < 37; ++z){
				System.out.print("-");
			}
			System.out.println();
		}
	}

}
