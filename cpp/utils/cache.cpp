#include "cache.h"
#include <iostream>


void Cache::add(unsigned int x, unsigned int y, std::vector<int> vals)
{
	int pos = y * 9 +  x;
	for(unsigned int c=cache.size(); c < pos + 1; ++c){
		cache.push_back(std::vector<int>());
	}
	cache[pos] = vals;
}


void Cache::add(unsigned int x, unsigned int y, int val)
{
	int pos = y * 9 +  x;
	for(auto i = cache[pos].begin(); i < cache[pos].end(); ++i){
		if (*i > val) {
			cache[pos].insert(i, val);
			return;
		}else if(*i == val) {
			return;
		}
	}
}


void Cache::del(unsigned int x, unsigned int y, int val)
{
	int pos = y * 9 +  x;
	for(auto i = cache[pos].begin(); i < cache[pos].end(); ++i){
		if(*i == val) {
			cache[pos].erase(i);
			return;
		}
	}
}


std::vector<int> Cache::get(unsigned int x, unsigned int y)
{
	int pos = y * 9 +  x;
	return cache[pos];
}


void Cache::print()
{
	for(int i=0; i < 37; ++i){
		std::cout << "_";
	}
	std::cout << std::endl;
	for(int i=0; i < 9; ++i){
		//print the numbers
		std::cout << "| ";
		for(int n=0; n < 9; ++n){
			std::cout << get(n, i).size() << " | ";
		}
		std::cout << std::endl;
		//print the separation lines
		for(int z=0; z < 37; ++z){
			std::cout << "-";
		}
		std::cout << std::endl;
	}
}

