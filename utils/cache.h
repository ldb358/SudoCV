#include <vector>

#ifndef SU_CACHE
#define SU_CACHE
class Cache
{
	std::vector< std::vector<int> > cache;
	public:
		void add(unsigned int x, unsigned int y, std::vector<int> vals);
		void add(unsigned int x, unsigned int y, int val);
		void del(unsigned int x, unsigned int y, int val);
		std::vector<int> get(unsigned int x, unsigned int y);
		void print();
};
#endif
