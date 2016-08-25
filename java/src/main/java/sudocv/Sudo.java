package sudocv;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.StringBuilder;
import java.lang.ArrayIndexOutOfBoundsException;

import sudocv.Cache;
import sudocv.areas.Area;
import sudocv.areas.Block;
import sudocv.areas.Column;
import sudocv.areas.Row;
import sudocv.areas.Position;

import sudocv.exceptions.NoPosibleMovesException;


public class Sudo {
	private int[][] puzzle;	

	public Sudo(){
		puzzle = new int[9][9];
	}

	public void load(String filename) throws FileNotFoundException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			try {
				for(int n=0; n < 9; ++n){
					String line = br.readLine();
					for(int i=0; i < 9; ++i){
						puzzle[n][i] = 
							Character.getNumericValue(line.charAt(i));
					}
				}
			} finally {
				br.close();
			}
		}catch (IOException ioe) {
			return;
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
	
	public void solve() throws NoPosibleMovesException{
		while (!solved()){
			Cache c = build_cache();
			if(only_choice(c) > 0){
				continue;
			}
			if(only_hope(c) > 0){
				continue;
			}
			claimed_values(c);
			if(only_hope(c) > 0){
				continue;
			}
			if(only_choice(c) > 0){
				continue;
			}
			if(!solved()){
				System.out.printf("All of our methods have failed us...\n");
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
		ArrayList<Integer> lst = new ArrayList<Integer>();
		if (puzzle[x][y] != 0) {
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
		ArrayList<Integer> list = new ArrayList<Integer>();
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


	private int only_hope(Cache c) throws NoPosibleMovesException{
		/*
		 * A Solving algorithm that finds all of the values that finds all of the
		 * values that have nowhere else to go. For example if a row has two open
		 * spots and is missing 1 and 5 if the first spot has a 5 in the same column
		 * then it would place the 5 in the other spot. This allows for another 
		 * algorithm to clean up the missing pieces that they wouldn't have been 
		 * able to solve otherwise.
		 *
		 * Returns 0 if no a change was made
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
					int choice = row_choice(c, x, y);
					if(choice > 0){
						put(x, y, choice);
						return 1;
					}
					choice = column_choice(c, x, y);
					if(choice > 0){
						put(x, y, choice);
						return 1;
					}
					choice = block_choice(c, x, y);
					if(choice > 0){
						put(x, y, choice);
						return 1;
					}
				}catch(ArrayIndexOutOfBoundsException e){
					throw new NoPosibleMovesException("There are no posible moves", x, y);
				}
			}
		}
		return 0;
	}

	private int row_choice(Cache c, int x, int y){
		char[] unique_pos = {1, 1, 1, 1, 1, 1, 1, 1, 1};
		for(int i=0; i < 9; i++){
			// the current item
			if(i==x){
				continue;
			}	
			ArrayList<Integer> res = c.get(i, y);
			// if res.size() == 0 then we have a set value and can eliminate that from unique_pos
			if(res.size() == 0){
				unique_pos[get(i, y)-1] = 0;
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
					//there are more than one posible values
					return 0;
				}
			}
		}
		return val;
	}
	
	private int column_choice(Cache c, int x, int y){
		char[] unique_pos = {1, 1, 1, 1, 1, 1, 1, 1, 1};
		for(int i=0; i < 9; i++){
			// the current item
			if(i==y){
				continue;
			}	
			ArrayList<Integer> res = c.get(x, i);
			// if res.size() == 0 then we have a set value and can eliminate that from unique_pos
			if(res.size() == 0){
				unique_pos[get(x, i)-1] = 0;
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
					//there are more than one posible values
					return 0;
				}
			}
		}
		return val;
	}

	private int block_choice(Cache c, int x, int y){
		char[] unique_pos = {1, 1, 1, 1, 1, 1, 1, 1, 1};
		//get the start of the block
		int base_y = (y/3) * 3;
		int base_x = (x/3) * 3;
		for(int i=0; i < 9; i++){
			// the current item
			int xi = base_x + i/3;
			int yi = base_y + i%3;
			if(xi == x && yi == y){
				continue;
			}	
			ArrayList<Integer> res = c.get(xi, yi);
			// if res.size() == 0 then we have a set value and can eliminate that from unique_pos
			if(res.size() == 0){
				unique_pos[get(xi, yi)-1] = 0;
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
					//there are more than one posible values
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
			claim_rows(cache, block);
			claim_columns(cache, block);
		}
	}

	private void claim_rows(Cache cache, int block){
		int base_x = block % 3;
		int base_y = block / 3;

		ArrayList< HashSet<Integer> > row = new ArrayList< HashSet<Integer> >(3);
		for(int i=0; i < 3; ++i){
			HashSet<Integer> union = new HashSet<Integer>();
			union.addAll(cache.get(base_x, base_y + i));
			union.addAll(cache.get(base_x + 1, base_y + i));
			union.addAll(cache.get(base_x + 2, base_y + i));
			row.add(union);
		}
		int cur = -1;
		int val = -1;
		for(int i=1; i < 10; ++i){	
			int icur = -1;
			for(int n=0; n < 3; ++n){
				HashSet<Integer> hash = row.get(n);
				if(hash.contains(i)){
					//we already have seen this
					if(icur > -1){
						icur = -1;
						break;
					}
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
			for(int z=0; z < 9; ++z){
				//skip this block
				if(z >= base_x && z < base_x+3){
					continue;
				}
				cache.del(z, base_y + cur, val);
			}
		}
	}

	private void claim_columns(Cache cache, int block){
		int base_x = block % 3;
		int base_y = block / 3;

		ArrayList< HashSet<Integer> > row = new ArrayList< HashSet<Integer> >(3);
		for(int i=0; i < 3; ++i){
			HashSet<Integer> union = new HashSet<Integer>();
			union.addAll(cache.get(base_x + i, base_y));
			union.addAll(cache.get(base_x + i, base_y + 1));
			union.addAll(cache.get(base_x + i, base_y + 2));
			row.add(union);
		}

		int cur = -1;
		int val = -1;
		for(int i=1; i < 10; ++i){
			int icur = -1;
			for(int n=0; n < 3; ++n){
				HashSet<Integer> hash = row.get(n);
				if(hash.contains(i)){
					//we already have seen this
					if(icur > -1){
						icur = -1;
						break;
					}
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
			for(int z=0; z < 9; ++z){
				//skip this block
				if(z >= base_y && z < base_y+3){
					continue;
				}
				cache.del(base_y + cur, z, val);
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
	
	public void debug(ArrayList<Integer> a){
		for(int val: a){
			System.out.printf("%d ", val);
		}
		System.out.println();
	}
}
