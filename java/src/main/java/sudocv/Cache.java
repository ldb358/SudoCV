package sudocv;

import sudocv.areas.Position;

import java.util.ArrayList;
import java.lang.IndexOutOfBoundsException;
import java.util.HashSet;
import java.util.TreeSet;

public class Cache {
    // Treeset is used because it 1) maintains compare order and 2) maintain unique values
	private ArrayList<TreeSet<Integer>> cache;

	public Cache(){
		cache = new ArrayList<>();
	}

	public void add(int x, int y, ArrayList<Integer> vals) {
		int pos = y * 9 + x;
		for(int i=cache.size(); i <= pos; ++i) {
			cache.add(new TreeSet<>());
		}
		cache.set(pos, new TreeSet<>(vals));
	}
	
	public void add(int x, int y, int val) {
		int pos = y * 9 + x;
        for(int i=cache.size(); i <= pos; ++i) {
			cache.add(new TreeSet<>());
		}
		TreeSet<Integer> loc = cache.get(pos);
        // add the value to the tree
        loc.add(val);
	}

	public void del(int x, int y, int val){
		int pos = y * 9 + x;
		TreeSet<Integer> loc = cache.get(pos);
        loc.remove(val);
	}

	public void del(Position p, int val){
        int pos = p.get_y() * 9 + p.get_x();
        TreeSet<Integer> loc = cache.get(pos);
        loc.remove(val);
    }

	public ArrayList<Integer> get(int x, int y)
			throws IndexOutOfBoundsException {
		int pos = y * 9 + x;
		if(pos >= cache.size()){
			throw new IndexOutOfBoundsException(String.format("Invalid x %d, y %d", x, y));
		}
        return new ArrayList<>(cache.get(pos));
	}

	public ArrayList<Integer> get(Position p)
			throws IndexOutOfBoundsException {
		int pos = p.get_y() * 9 + p.get_x();
		if(pos >= cache.size()){
			throw new IndexOutOfBoundsException(String.format("Invalid x %d, y %d", p.get_x(), p.get_y()));
		}
		return new ArrayList<>(cache.get(pos));
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
