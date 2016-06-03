#include <iostream>
#include <fstream>
#include <vector>
#include <exception>

class BadGameDataException: public std::exception
{
	virtual const char *what() const throw()
	{
		return "Bad Game Board Loaded";
	}
} bad_game;

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
		std::vector<int> check_row(unsigned int y);
		std::vector<int> check_column(unsigned int x);
		std::vector<int> check_block(unsigned int x, unsigned int y);
};

Sudoku::Sudoku()
{
	for(int i=0; i < 9; ++i){
		for(int j=0; j < 9; ++j){
			puzzle[i][j] = 0;
		}
	}
}

void Sudoku::solve(){
	for(int i=0; i < 9; ++i){
		for(int n=0; n < 9; ++n){
			std::vector<int> res = check(i, n);
			if(res.size() == 1){
				puzzle[i][n] = res[0];
				i=0;
				n=-1;
			}
		}
	}
}

void Sudoku::print(){
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
		//print the seperation lines
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
