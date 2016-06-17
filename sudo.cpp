#include <iostream>
#include <fstream>
#include <vector>
#include <exception>
#include "utils/cache.h"

class BadGameDataException: public std::exception
{
	virtual const char *what() const throw()
	{
		return "Bad Game Board Loaded";
	}
} bad_game;


void print_vector(std::vector<int> &v){
	for(auto i = v.begin(); i < v.end(); ++i){
		std::cout << *i << " ";
	}
	std::cout << std::endl;
}



class Sudoku
{
	int puzzle[9][9];
	public:
		Sudoku();
		void load(std::string filename);
		int get(unsigned int x, unsigned int y);
		void put(unsigned int x, unsigned int y, int val);
		std::vector<int> check(unsigned int x, unsigned int y);
		void solve();
		void print();

	private:
		//solvers and helper
		int only_choice();
		int only_choice(Cache &c);
		std::vector<int> check_row(unsigned int y);
		std::vector<int> check_column(unsigned int x);
		std::vector<int> check_block(unsigned int x, unsigned int y);
		int only_hope();
		int row_choice(unsigned int x, unsigned int y);
		int column_choice(unsigned int x, unsigned int y);
		int block_choice(unsigned int x, unsigned int y);

		//filters
		void claimed_values(Cache &cache);
		void claim_rows(Cache &cache, int block);
		void claim_columns(Cache &cache, int block);


		void elim_pairs(Cache &cache);
		void elim_triple(Cache &cache);
		Cache build_cache();

		int is_solved();
};

Sudoku::Sudoku()
{
	for(int i=0; i < 9; ++i){
		for(int j=0; j < 9; ++j){
			puzzle[i][j] = 0;
		}
	}
}

int Sudoku::is_solved() {
	for(int i=0; i < 9; ++i){
		for(int n=0; n < 9; ++n){
			if(puzzle[i][n] == 0){
				return 0;
			}
		}
	}
	return 1;
}

void Sudoku::solve(){
	while(!is_solved()){
		only_choice();	
		if(is_solved()){
			return;
		}
		if(only_hope()){
			continue;
		}
		if(is_solved()){
			return;
		}
		Cache c = build_cache();
		claimed_values(c);
		c.print();
		only_choice(c);
		c.print();
		break;
	}
}

int Sudoku::only_choice(){
	/*
	 * A solving algorithm that find all the squares with only one possible
	 * value. That value will be inserted and the iteration will restart.
	 * Only works on simple puzzles.
	 *
	 * Returns 0 if no changes were made
	 */
	int changes = 0;
	for(int i=0; i < 9; ++i){
		for(int n=0; n < 9; ++n){
			std::vector<int> res = check(i, n);
			if(res.size() == 1){
				puzzle[i][n] = res[0];
				changes = 1;
				i=0;
				n=-1;
			}
		}
	}
	return changes;
}

int Sudoku::only_choice(Cache &c){
	/*
	 * A solving algorithm that find all the squares with only one possible
	 * value. That value will be inserted and the iteration will restart.
	 * Only works on simple puzzles.
	 *
	 * Returns:
	 * 	0 if no changes were made.
	 */
	int changes = 0;
	for(int i=0; i < 9; ++i){
		for(int n=0; n < 9; ++n){
			std::vector<int> res = c.get(i, n);
			if(res.size() == 1){	
				puzzle[i][n] = res[0];
				c.del(i, n, res[0]);
				changes = 1;
				i=0;
				n=-1;
			}
		}
	}
	return changes;
}


int Sudoku::only_hope(){
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
	// for each x
	for(unsigned int x=0; x < 9; ++x){
		// for each y
		for(unsigned int y=0; y < 9; ++y){	
			// get the set A of every possible value
			// std::vector<int> cur = check(x, y);
			// create an array Z of values 0-9 and mark them 1 if they are in A and 0 if they are not
			if(puzzle[x][y]){
				continue;
			}
			int choice = row_choice(x, y);
			if(choice){
				puzzle[x][y] = choice;
				return 1;
			}
			choice = column_choice(x, y);
			if(choice){
				puzzle[x][y] = choice;
				return 1;

			}
			choice = block_choice(x, y);
			if(choice){
				puzzle[x][y] = choice;
				solve();
				return 1;
			}
		}
	}
	return 0;
}

int Sudoku::only_hope(){
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
	// for each x
	for(unsigned int x=0; x < 9; ++x){
		// for each y
		for(unsigned int y=0; y < 9; ++y){	
			// get the set A of every possible value
			// std::vector<int> cur = check(x, y);
			// create an array Z of values 0-9 and mark them 1 if they are in A and 0 if they are not
			if(puzzle[x][y]){
				continue;
			}
			int choice = row_choice(x, y);
			if(choice){
				puzzle[x][y] = choice;
				return 1;
			}
			choice = column_choice(x, y);
			if(choice){
				puzzle[x][y] = choice;
				return 1;

			}
			choice = block_choice(x, y);
			if(choice){
				puzzle[x][y] = choice;
				solve();
				return 1;
			}
		}
	}
	return 0;
}

void Sudoku::claimed_values(Cache &cache)
{
	/*
	 * Claimed Values:
	 * if a number only exists in one row or column in a block, then that 
	 * number for that row/column must be in this block which means you
	 * can eliminate it from all other boxes in the row/column.
	 */
	for(int block=0; block < 9; ++block){
		std::cout << "Starting Block " << block << std::endl;
		claim_rows(cache, block);
		std::cout << "Starting Columns " << block << std::endl;
		claim_columns(cache, block);
		std::cout << "Done." << std::endl;
	}
}

void Sudoku::claim_rows(Cache &cache, int block)
{	
	//for each row
	int base_x = block % 3; 
	int base_y = block / 3;

	// build the set for each row by finding the union of each col in the row
	std::vector< std::vector<int> > row(3);
	for (int i=0; i < 3; ++i){
		std::vector<int> tmp(10);
		std::vector<int> row_s(10);
		std::vector<int> a = cache.get(base_x, base_y + i);
		std::vector<int> b = cache.get(base_x + 1, base_y + i);
		std::vector<int> c = cache.get(base_x + 2, base_y + i);
		std::set_union(a.begin(), a.end(), b.begin(), b.end(), tmp.begin());
		std::set_union(tmp.begin(), tmp.end(), c.begin(), c.end(), row_s.begin());
		row.push_back(row_s);
	}
	std::vector< std::vector<int>::iterator > row_i {
		row[0].begin(),
		row[1].begin(),
		row[2].begin()
	};

	// Check how many rows have each value if its one then "claim" the value
	for(int i=0; i < 9; ++i){
		int cur = -1;

		for(int r=0; r < 3; ++r){	
			if(row_i[r] == row[r].end()){
				continue;
			}
			if(*row_i[r] == i){
				++row_i[r];
				//we already have this
				if(cur > -1){
					cur = -1;
					break;
				}
				//we are unique so far
				cur = r;
			}
			if(*row_i[r] < i){
				++row_i[r];
			}
		}
		if(cur > -1){
			for(int r=0; r < 9; ++r){
				if(r >= base_x && r < base_x+3){
					continue;
				}
				cache.del(r, cur, i);
			}
		}
	}
}
void Sudoku::claim_columns(Cache &cache, int block)
{	
	//for each col
	int base_x = block % 3; 
	int base_y = block / 3;
	// build the set for each row by finding the union of each col in the row
	std::vector< std::vector<int> > cols(3);
	for (int i=0; i < 3; ++i){
		std::vector<int> tmp(10);
		std::vector<int> col(10);
		std::vector<int> a = cache.get(base_x + i, base_y);
		std::vector<int> b = cache.get(base_x + i, base_y + 1);
		std::vector<int> c = cache.get(base_x + i, base_y + 2);
		std::set_union(a.begin(), a.end(), b.begin(), b.end(), tmp.begin());
		std::set_union(tmp.begin(), tmp.end(), c.begin(), c.end(), col.begin());
		cols.push_back(col);
	}
	std::vector< std::vector<int>::iterator > col_i {
		cols[0].begin(),
		cols[1].begin(),
		cols[2].begin()
	};
	// check how many vectors have each value. if only 1 does then "claim" that
	// number from the rest of the row
	for(int i=0; i < 9; ++i){
		int cur = -1;
		for(int r=0; r < 3; ++r){	
			if(col_i[r] == cols[r].end()){
				continue;
			}
			if(*col_i[r] == i){
				++col_i[r];
				//we already have this
				if(cur > -1){
					cur = -1;
					break;
				}
				//we are unique so far
				cur = r;
			}
			if(*col_i[r] < i){
				++col_i[r];
			}
		}
		if(cur > -1){
			for(int r=0; r < 9; ++r){
				if(r >= base_x && r < base_x+3){
					continue;
				}
				std::cout << "Claimed Value Removed" << std::endl;
				cache.del(r, cur, i);
			}
		}
	}
}

int Sudoku::row_choice(unsigned int x, unsigned int y)
{
	/*
	 * Returns the value of the only option for this spot in the row. 
	 * Returns 0 if there are no only options.
	 */
	char unique_pos[9] = { 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	// loop over every item in the row
	for(int i=0; i < 9; ++i){
		// get the set B of every possible value for this row	
		if(i == x){
			continue;
		}
		std::vector<int> res = check(i, y);
		
		if(res.size() == 0){
			unique_pos[puzzle[i][y]-1] = 0;
			continue;
		}
		// mark all of these values to 0
		for(auto i = res.begin(); i < res.end(); ++i){
			if(*i != 0){
				unique_pos[*i-1] = 0;
			}
		}
	}
	int val = 0;
	for(unsigned int i=0; i < 9; ++i){
		if(unique_pos[i] != 0){
			if(!val){
				val = i+1;
			}else{
				return 0;
			}
		}
	}
	return val;

}

int Sudoku::column_choice(unsigned int x, unsigned int y)
{
	/*
	 * Returns the value of the only option for this spot in the column. 
	 * Returns 0 if there are no only options.
	 */
	char unique_pos[9] = { 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	// loop over every item in the row
	for(int i=0; i < 9; ++i){
		// get the set B of every possible value for this row	
		if(i == y){
			continue;
		}
		std::vector<int> res = check(x, i);
		
		if(res.size() == 0){
			unique_pos[puzzle[x][i]-1] = 0;
			continue;
		}
		// mark all of these values to 0
		for(auto i = res.begin(); i < res.end(); ++i){
			if(*i != 0){
				unique_pos[*i-1] = 0;
			}
		}
	}
	int val = 0;
	for(unsigned int i=0; i < 9; ++i){
		if(unique_pos[i] != 0){
			if(!val){
				val = i+1;
			}else{
				return 0;
			}
		}
	}
	return val;
}

int Sudoku::block_choice(unsigned int x, unsigned int y)
{
	/*
	 * Returns the value of the only option for this spot in the block. 
	 * Returns 0 if there are no only options.
	 */
	char unique_pos[9] = { 1, 1, 1, 1, 1, 1, 1, 1, 1 };
	// loop over every item in the row
	unsigned int base_y = (y/3) * 3;
	unsigned int base_x = (x/3) * 3;
	for(int i=0; i < 9; ++i){
		// get the set B of every possible value for this row	
		if(base_x + i/3 == x && base_y + i%3 == y){
			continue;
		}
		std::vector<int> res = check(base_x + i/3, base_y + i%3);
		
		if(res.size() == 0){
			unique_pos[puzzle[base_x + i/3][base_y + i%3]-1] = 0;
			continue;
		}
		// mark all of these values to 0
		for(auto i = res.begin(); i < res.end(); ++i){
			if(*i != 0){
				unique_pos[*i-1] = 0;
			}
		}
	}
	int val = 0;
	for(unsigned int i=0; i < 9; ++i){
		if(unique_pos[i] != 0){
			if(!val){
				val = i+1;
			}else{
				return 0;
			}
		}
	}
	return val;
}


void Sudoku::print()
{
	for(int i=0; i < 37; ++i){
		std::cout << "_";
	}
	std::cout << std::endl;
	for(int i=0; i < 9; ++i){
		//print the numbers
		std::cout << "| ";
		for(int n=0; n < 9; ++n){
			std::cout << puzzle[n][i] << " | ";
		}
		std::cout << std::endl;
		//print the separation lines
		for(int i=0; i < 37; ++i){
			std::cout << "-";
		}
		std::cout << std::endl;
	}
}

std::vector<int> Sudoku::check(unsigned int x, unsigned int y)
{
	std::vector<int> list;
	if(puzzle[x][y] != 0){
		return list;
	}
	std::vector<int> row = check_row(y);	
	std::vector<int> col = check_column(x);	
	std::vector<int> block = check_block(x, y);

	std::vector<int>::iterator row_i = row.begin();
	std::vector<int>::iterator col_i = col.begin();
	std::vector<int>::iterator block_i = block.begin();

	for(int i=1; i < 10; ++i){
		if(row_i == row.end() ||
		   col_i == col.end() ||
		   block_i == block.end()){
			break;
		}
		if(*row_i == i && *col_i == i && *block_i == i){
			list.push_back(i);
			row_i++;
			col_i++;
			block_i++;
		}else{
			if(*block_i <= i){
				++block_i;
			}
			if(*row_i <= i){
				++row_i;
			}
			if(*col_i <= i){
				++col_i;
			}
		}
	}
	return list;
}

std::vector<int> Sudoku::check_row(unsigned int y)
{
	std::vector<int> list;
	for(int i=1; i < 10; ++i){
		int exists = 0;
		for(unsigned int n=0; n < 9; n++){
			if(puzzle[n][y] == i){
				exists = 1;
				break;
			}
		}
		if (!exists) {
			list.push_back(i);
		}
	}
	return list;
}

std::vector<int> Sudoku::check_column(unsigned int x)
{
	std::vector<int> list;
	for(int i=1; i < 10; ++i){
		int exists = 0;
		for(unsigned int n=0; n < 9; n++){
			if(puzzle[x][n] == i){
				exists = 1;
				break;
			}
		}
		if (!exists) {
			list.push_back(i);
		}
	}
	return list;
}

std::vector<int> Sudoku::check_block(unsigned int x, unsigned int y)
{
	std::vector<int> list;
	unsigned int base_y = (y/3) * 3;
	unsigned int base_x = (x/3) * 3;
	for(int i=1; i < 10; ++i){
		int exists = 0;
		for(unsigned int n=0; n < 9; n++){
			if(puzzle[base_x + n/3][base_y + n%3] == i){
				exists = 1;
				break;
			}
		}
		if (!exists) {
			list.push_back(i);
		}
	}
	return list;
}


Cache Sudoku::build_cache()
{
	Cache c;
	for(unsigned int x=0; x < 9; ++x){
		for(unsigned int y=0; y < 9; ++y){
			c.add(x, y, check(x, y));
		}
	}
	return c;
}

void Sudoku::load(std::string filename)
{
	char *memblock;
	std::ifstream file(filename, std::ios::in | std::ios::binary | std::ios::ate);
	if (file.is_open()) {
		file.seekg (0, std::ios::beg);
		for(int i=0; i < 9; ++i){
			memblock = new char[10];
			file.read(memblock, 10);
			for(int n=0; n < 9; n++){
				if(memblock[n] >= 48 && memblock[n] <= 57){
					puzzle[n][i] = memblock[n] - 48;
				}else{
					throw bad_game;
				}
			}
		}
		file.close();
	}

}

int Sudoku::get(unsigned int x, unsigned int y)
{
	return puzzle[x][y];	
}

void Sudoku::put(unsigned int x, unsigned int y, int val)
{
	if (x >= 9 || y >= 9) {
		return;
	}
	puzzle[x][y] = val;
}

int main(int argc, char **argv)
{
	Sudoku s;
	try{
		s.load("game.txt");
	}catch (BadGameDataException &e){
		std::cout << "Failed to load game board. Bad format." << std::endl;
	}
	s.solve();
	s.print();
	return 0;
}
