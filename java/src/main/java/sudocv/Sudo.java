package sudocv;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ArrayIndexOutOfBoundsException;

import sudocv.areas.Area;
import sudocv.areas.Block;
import sudocv.areas.Column;
import sudocv.areas.Row;
import sudocv.areas.Position;

import sudocv.exceptions.NoPossibleMovesException;


public class Sudo {
	private int[][] puzzle;	

	public Sudo(){
		puzzle = new int[9][9];
	}

	public void load(String filename) throws FileNotFoundException{
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                for (int n = 0; n < 9; ++n) {
                    String line = br.readLine();
                    for (int i = 0; i < 9; ++i) {
                        puzzle[n][i] =
                                Character.getNumericValue(line.charAt(i));
                    }
                }
            }
		}catch (IOException ignored) {
		}
	}

	public int get(int x, int y) {
		return puzzle[x][y];
	}
	public int get(Position p) { 
		return puzzle[p.get_x()][p.get_y()];
	}

	public void put(int x, int y, int val) {
		if (x >= 9 || y >= 9) {
			return;
		}
		puzzle[x][y] = val;
	}	
	
	public void solve() throws NoPossibleMovesException {
		while (!solved()){
			Cache c = build_cache();
			if(only_choice(c) > 0){
				continue;
			}
			if(only_hope(c) > 0){
				continue;
			}
			claimed_values(c);
            disjoint_sets(c);
			if(only_hope(c) > 0){
				continue;
			}
			if(only_choice(c) > 0) {
                continue;
            }
			if(!solved()){
				System.out.printf("All of our methods have failed us...\n");
                c.print();
			}
			break;
		}
	}

	private int only_choice(Cache c){
		/*
		 * A solving algorithm that find all the squares with only one possible
		 * value. That value will be inserted and the iteration will restart.
		 * Only works on simple puzzles.
		 *
		 * Returns:
		 * 	0 if no changes were made.
		 */
		int changes = 0;
		for(int i=0; i < 9; i++){
			for(int n=0; n < 9; n++){
				ArrayList<Integer> res = c.get(i, n);
				if(res.size() == 1){
					puzzle[i][n] = res.get(0);
					c = build_cache();
					changes += 1;
					i=0;
					n=-1;
				}
			}
		}
		return 0;
	}
	
	public ArrayList<Integer> check(int x, int y) {
		ArrayList<Integer> lst = new ArrayList<>();
		if (get(x, y) != 0) {
			return lst;
		}
		ArrayList<Integer> row = check_area(new Row(y));
		ArrayList<Integer> col = check_area(new Column(x));
		ArrayList<Integer> block = check_area(new Block(x, y));
		
		Iterator<Integer> row_i = row.iterator();
		Iterator<Integer> col_i = col.iterator();
		Iterator<Integer> block_i = block.iterator();
		
		int row_v = row_i.hasNext()? row_i.next() : 10;
		int col_v = col_i.hasNext()? col_i.next() : 10;
		int block_v = block_i.hasNext()? block_i.next() : 10;

		for(int i=1; i < 10; ++i){
			if(row_v == i && col_v == i && block_v == i){
				lst.add(i);
				if(!(row_i.hasNext() &&
				   col_i.hasNext() &&
				   block_i.hasNext())){
					break;
				}
				row_v = row_i.next();
				col_v = col_i.next();
				block_v = block_i.next();
			}else{
				if(block_v <= i){
					if(!block_i.hasNext()){
						break;
					}
					block_v  = block_i.next();
				}
				if(row_v <= i){
					if(!row_i.hasNext()){
						break;
					}
					row_v = row_i.next();
				}
				if(col_v <= i){
					if(!col_i.hasNext()){
						break;
					}
					col_v = col_i.next();
				}
			}	
		}		
		return lst;
	}

	private ArrayList<Integer> check_area(Area a){
		ArrayList<Integer> list = new ArrayList<>();
		for(int i=1; i < 10; ++i){
			boolean exists = false;
			for(int n=0; n < 9; n++){
				if(get(a.get(n)) == i){
					exists = true;
					break;
				}
			}
			if (!exists) {
				list.add(i);
			}
		}
		return list;
	}

	private int only_hope(Cache c) throws NoPossibleMovesException {
		/*
		 * A Solving algorithm that finds all of the values that finds all of the
		 * values that have nowhere else to go. For example if a row has two open
		 * spots and is missing 1 and 5 if the first spot has a 5 in the same column
		 * then it would place the 5 in the other spot. This allows for another 
		 * algorithm to clean up the missing pieces that they wouldn't have been 
		 * able to solve otherwise.
		 *
		 * Returns 0 if no a move was made
		 */
		for(int x=0; x < 9; ++x){
			// for each y
			for(int y=0; y < 9; ++y){	
				// get the set A of every possible value
				// std::vector<int> cur = check(x, y);
				// create an array Z of values 0-9 and mark them 1 if they are in A and 0 if they are not
				if(get(x, y) > 0){
					continue;
				}
				try{
				    Position pos =  new Position(x, y);
					int choice = choices(c, new Row(y), pos);
					if(choice > 0){
						put(x, y, choice);
						return 1;
					}
					choice = choices(c, new Column(x), pos);
					if(choice > 0){
						put(x, y, choice);
						return 1;
					}
					choice = choices(c, new Block(x, y), pos);
					if(choice > 0){
						put(x, y, choice);
						return 1;
					}
				}catch(ArrayIndexOutOfBoundsException e){
					throw new NoPossibleMovesException("There are no posible moves", x, y);
				}
			}
		}
		return 0;
	}

	private int choices(Cache c, Area region, Position cur){
		char[] unique_pos = {1, 1, 1, 1, 1, 1, 1, 1, 1};
		for(int i=0; i < 9; i++){
			Position pos = region.get(i);
			// the current item
			if(pos.equals(cur)){
				continue;
			}	
			ArrayList<Integer> res = c.get(pos);
			// if res.size() == 0 then we have a set value and can eliminate that from unique_pos
			if(res.size() == 0){
				unique_pos[get(pos)-1] = 0;
				continue;
			}

			// make all values in res to 0
			for(int val: res){
				if(val != 0){
					unique_pos[val-1] = 0; 
				}
			}
		}
		//check to see what is left
		int val = 0;
		for(int i=0; i < 9; i++){
			if(unique_pos[i] != 0){
				if(val == 0){
					val = i+1;
				}else{
					//there are more than one possible values
					return 0;
				}
			}
		}
		return val;
	}
	
	private void claimed_values(Cache cache){
		/*
		 * Claimed Values:
		 * if a number only exists in one row or column in a block, then that 
		 * number for that row/column must be in this block which means you
		 * can eliminate it from all other boxes in the row/column.
		 */
		for(int block=0; block < 9; ++block){
			claim(cache, new Row(0), block);
			claim(cache, new Column(0), block);
		}
	}

	private void claim(Cache cache, Area region, int block){
		int base_x = block % 3;
		int base_y = block / 3;

		ArrayList< HashSet<Integer> > items = new ArrayList<>(3);
        region.move(base_x, base_y);
        // loop through the three rows or columns in a block
		for(int i=0; i < 3; ++i){
		    // we can increase both by since they only pay attention to what they use.

			HashSet<Integer> union = new HashSet<>();
            // find union of all boxes in one region(row or col) in a block
			union.addAll(cache.get(region.get_by_offset(0)));
			union.addAll(cache.get(region.get_by_offset(1)));
			union.addAll(cache.get(region.get_by_offset(2)));
			items.add(union);
            //move over 1 region
            region.move(1);
		}
		int cur = -1;
		int val = -1;
		for(int i=1; i < 10; ++i){	
			int icur = -1;
			for(int n=0; n < 3; ++n){
				HashSet<Integer> hash = items.get(n);
				if(hash.contains(i)){
					//we already have seen this
					icur = n;
					val = i;
				}
				if(icur > -1 && cur == -1){
					cur = n;
				}else if(cur > -1 && icur > -1){
					cur = -1;
					break;
				}
			}
		}
		if(cur > -1){
		    // Reset to start of block then shift to row that will be 'claiming'
		    region.move(base_x, base_y);
            region.move(cur);
            // for each square in region
			for(int z=0; z < 9; ++z){
				//skip z if its in this block
				if(region.get(z).between(
				        region.get_by_offset(0), region.get_by_offset(2))){
					continue;
				}
				cache.del(region.get(z), val);
			}
		}
	}

	private void disjoint_sets(Cache cache) {
        // loop through every square in a region that has a candidate list of 2
        // compare it to every other square that has a length of 2 and if they
        // match delete those values from every candidate list in that area
        for(int i=0; i < 9; ++i){
            filter_doubles(cache, new Row(i));
            filter_doubles(cache, new Column(i));
            filter_doubles(cache, new Block(i));
            filter_triples(cache, new Row(i));
            filter_triples(cache, new Column(i));
            filter_triples(cache, new Block(i));
        }
    }

    private void filter_doubles(Cache cache, Area region){
        for(int i=0; i < 9; ++i){
            ArrayList<Integer> first = cache.get(region.get(i));
            if(first.size() != 2){
                continue;
            }
            for(int n=0; n < 9; ++n){
                ArrayList<Integer> second = cache.get(region.get(n));
                if(i == n || second.size() != 2) {
                    continue;
                }
                if(arrays_match(first, second)){
                    for(int z=0; z < 9; z++){
                        if(z == i || z == n) {
                            continue;
                        }
                        for(int val: first) {
                            cache.del(region.get(z), val);
                        }
                    }
                }
            }

        }
    }

    private void filter_triples(Cache cache, Area region){
        for(int i=0; i < 9; ++i){
            ArrayList<Integer> first = cache.get(region.get(i));
            if(first.size() > 3 || first.size() == 0) {
                continue;
            }
            for(int n=i; n < 9; ++n){
                ArrayList<Integer> second = cache.get(region.get(n));
                if(i == n || second.size() > 3 || second.size() == 0) {
                    continue;
                }
                for(int k=n; k < 9; ++k) {
                    ArrayList<Integer> third = cache.get(region.get(k));
                    if(k == i || k== n || third.size() > 3 || third.size() == 0) {
                        continue;
                    }
                    HashSet<Integer> set = new HashSet<>(first);
                    set.addAll(second);
                    set.addAll(third);

                    if(set.size() != 3) continue;
                    for(int z = 0; z < 9; z++) {
                        if (z == i || z == n || z==k) {
                            continue;
                        }
                        for (int val : set) {
                            cache.del(region.get(z), val);
                        }
                    }
                }
            }

        }
    }

	private Cache build_cache() {
		Cache c = new Cache();	
		for(int y=0; y < 9; y++){
			for(int x=0; x < 9; x++){
				c.add(x, y, check(x, y));
			}
		}
		return c;
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
				System.out.printf("%s | ", puzzle[n][i]);
			}
			System.out.println();
			//print the separation lines
			for(int z=0; z < 37; ++z){
				System.out.print("-");
			}
			System.out.println();
		}

	}

	public boolean solved(){
		for(int x=0; x < 9; x++){
			for(int y=0; y < 9; y++){
				if(get(x, y) == 0){
					return false;
				}
			}
		}
		return true;
	}

	public boolean arrays_match(ArrayList<Integer> a, ArrayList<Integer> b) {
	    if(a == null || b == null) {
	        return (a == null && b == null);
        }
        if(a.size() != b.size()){
            return false;
        }
        for(int val: a){
            if(!b.contains(a)){
                return false;
            }
        }
        return true;
    }

	public void debug(ArrayList<Integer> a){
		for(int val: a){
			System.out.printf("%d ", val);
		}
		System.out.println();
	}
}
