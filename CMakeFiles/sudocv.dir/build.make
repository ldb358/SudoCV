# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.5

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/brenemal/workspace/cpp/SudoCV

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/brenemal/workspace/cpp/SudoCV

# Include any dependencies generated for this target.
include CMakeFiles/sudocv.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/sudocv.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/sudocv.dir/flags.make

CMakeFiles/sudocv.dir/sudo.cpp.o: CMakeFiles/sudocv.dir/flags.make
CMakeFiles/sudocv.dir/sudo.cpp.o: sudo.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/brenemal/workspace/cpp/SudoCV/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/sudocv.dir/sudo.cpp.o"
	/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/sudocv.dir/sudo.cpp.o -c /home/brenemal/workspace/cpp/SudoCV/sudo.cpp

CMakeFiles/sudocv.dir/sudo.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/sudocv.dir/sudo.cpp.i"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/brenemal/workspace/cpp/SudoCV/sudo.cpp > CMakeFiles/sudocv.dir/sudo.cpp.i

CMakeFiles/sudocv.dir/sudo.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/sudocv.dir/sudo.cpp.s"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/brenemal/workspace/cpp/SudoCV/sudo.cpp -o CMakeFiles/sudocv.dir/sudo.cpp.s

CMakeFiles/sudocv.dir/sudo.cpp.o.requires:

.PHONY : CMakeFiles/sudocv.dir/sudo.cpp.o.requires

CMakeFiles/sudocv.dir/sudo.cpp.o.provides: CMakeFiles/sudocv.dir/sudo.cpp.o.requires
	$(MAKE) -f CMakeFiles/sudocv.dir/build.make CMakeFiles/sudocv.dir/sudo.cpp.o.provides.build
.PHONY : CMakeFiles/sudocv.dir/sudo.cpp.o.provides

CMakeFiles/sudocv.dir/sudo.cpp.o.provides.build: CMakeFiles/sudocv.dir/sudo.cpp.o


CMakeFiles/sudocv.dir/utils/cache.cpp.o: CMakeFiles/sudocv.dir/flags.make
CMakeFiles/sudocv.dir/utils/cache.cpp.o: utils/cache.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/brenemal/workspace/cpp/SudoCV/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/sudocv.dir/utils/cache.cpp.o"
	/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/sudocv.dir/utils/cache.cpp.o -c /home/brenemal/workspace/cpp/SudoCV/utils/cache.cpp

CMakeFiles/sudocv.dir/utils/cache.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/sudocv.dir/utils/cache.cpp.i"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/brenemal/workspace/cpp/SudoCV/utils/cache.cpp > CMakeFiles/sudocv.dir/utils/cache.cpp.i

CMakeFiles/sudocv.dir/utils/cache.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/sudocv.dir/utils/cache.cpp.s"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/brenemal/workspace/cpp/SudoCV/utils/cache.cpp -o CMakeFiles/sudocv.dir/utils/cache.cpp.s

CMakeFiles/sudocv.dir/utils/cache.cpp.o.requires:

.PHONY : CMakeFiles/sudocv.dir/utils/cache.cpp.o.requires

CMakeFiles/sudocv.dir/utils/cache.cpp.o.provides: CMakeFiles/sudocv.dir/utils/cache.cpp.o.requires
	$(MAKE) -f CMakeFiles/sudocv.dir/build.make CMakeFiles/sudocv.dir/utils/cache.cpp.o.provides.build
.PHONY : CMakeFiles/sudocv.dir/utils/cache.cpp.o.provides

CMakeFiles/sudocv.dir/utils/cache.cpp.o.provides.build: CMakeFiles/sudocv.dir/utils/cache.cpp.o


# Object files for target sudocv
sudocv_OBJECTS = \
"CMakeFiles/sudocv.dir/sudo.cpp.o" \
"CMakeFiles/sudocv.dir/utils/cache.cpp.o"

# External object files for target sudocv
sudocv_EXTERNAL_OBJECTS =

sudocv: CMakeFiles/sudocv.dir/sudo.cpp.o
sudocv: CMakeFiles/sudocv.dir/utils/cache.cpp.o
sudocv: CMakeFiles/sudocv.dir/build.make
sudocv: CMakeFiles/sudocv.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/brenemal/workspace/cpp/SudoCV/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Linking CXX executable sudocv"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/sudocv.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/sudocv.dir/build: sudocv

.PHONY : CMakeFiles/sudocv.dir/build

CMakeFiles/sudocv.dir/requires: CMakeFiles/sudocv.dir/sudo.cpp.o.requires
CMakeFiles/sudocv.dir/requires: CMakeFiles/sudocv.dir/utils/cache.cpp.o.requires

.PHONY : CMakeFiles/sudocv.dir/requires

CMakeFiles/sudocv.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/sudocv.dir/cmake_clean.cmake
.PHONY : CMakeFiles/sudocv.dir/clean

CMakeFiles/sudocv.dir/depend:
	cd /home/brenemal/workspace/cpp/SudoCV && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/brenemal/workspace/cpp/SudoCV /home/brenemal/workspace/cpp/SudoCV /home/brenemal/workspace/cpp/SudoCV /home/brenemal/workspace/cpp/SudoCV /home/brenemal/workspace/cpp/SudoCV/CMakeFiles/sudocv.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/sudocv.dir/depend

