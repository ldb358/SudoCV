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
CMAKE_COMMAND = /usr/local/Cellar/cmake/3.5.2/bin/cmake

# The command to remove a file.
RM = /usr/local/Cellar/cmake/3.5.2/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/brenemal/personal/sudocv/cpp

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/brenemal/personal/sudocv/cpp

# Include any dependencies generated for this target.
include CMakeFiles/vision.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/vision.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/vision.dir/flags.make

CMakeFiles/vision.dir/vision.cpp.o: CMakeFiles/vision.dir/flags.make
CMakeFiles/vision.dir/vision.cpp.o: vision.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/brenemal/personal/sudocv/cpp/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/vision.dir/vision.cpp.o"
	/Library/Developer/CommandLineTools/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/vision.dir/vision.cpp.o -c /Users/brenemal/personal/sudocv/cpp/vision.cpp

CMakeFiles/vision.dir/vision.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/vision.dir/vision.cpp.i"
	/Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/brenemal/personal/sudocv/cpp/vision.cpp > CMakeFiles/vision.dir/vision.cpp.i

CMakeFiles/vision.dir/vision.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/vision.dir/vision.cpp.s"
	/Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/brenemal/personal/sudocv/cpp/vision.cpp -o CMakeFiles/vision.dir/vision.cpp.s

CMakeFiles/vision.dir/vision.cpp.o.requires:

.PHONY : CMakeFiles/vision.dir/vision.cpp.o.requires

CMakeFiles/vision.dir/vision.cpp.o.provides: CMakeFiles/vision.dir/vision.cpp.o.requires
	$(MAKE) -f CMakeFiles/vision.dir/build.make CMakeFiles/vision.dir/vision.cpp.o.provides.build
.PHONY : CMakeFiles/vision.dir/vision.cpp.o.provides

CMakeFiles/vision.dir/vision.cpp.o.provides.build: CMakeFiles/vision.dir/vision.cpp.o


# Object files for target vision
vision_OBJECTS = \
"CMakeFiles/vision.dir/vision.cpp.o"

# External object files for target vision
vision_EXTERNAL_OBJECTS =

vision: CMakeFiles/vision.dir/vision.cpp.o
vision: CMakeFiles/vision.dir/build.make
vision: /usr/local/lib/libopencv_xphoto.3.1.0.dylib
vision: /usr/local/lib/libopencv_xobjdetect.3.1.0.dylib
vision: /usr/local/lib/libopencv_tracking.3.1.0.dylib
vision: /usr/local/lib/libopencv_surface_matching.3.1.0.dylib
vision: /usr/local/lib/libopencv_structured_light.3.1.0.dylib
vision: /usr/local/lib/libopencv_stereo.3.1.0.dylib
vision: /usr/local/lib/libopencv_saliency.3.1.0.dylib
vision: /usr/local/lib/libopencv_rgbd.3.1.0.dylib
vision: /usr/local/lib/libopencv_reg.3.1.0.dylib
vision: /usr/local/lib/libopencv_plot.3.1.0.dylib
vision: /usr/local/lib/libopencv_optflow.3.1.0.dylib
vision: /usr/local/lib/libopencv_line_descriptor.3.1.0.dylib
vision: /usr/local/lib/libopencv_fuzzy.3.1.0.dylib
vision: /usr/local/lib/libopencv_dpm.3.1.0.dylib
vision: /usr/local/lib/libopencv_dnn.3.1.0.dylib
vision: /usr/local/lib/libopencv_datasets.3.1.0.dylib
vision: /usr/local/lib/libopencv_ccalib.3.1.0.dylib
vision: /usr/local/lib/libopencv_bioinspired.3.1.0.dylib
vision: /usr/local/lib/libopencv_bgsegm.3.1.0.dylib
vision: /usr/local/lib/libopencv_aruco.3.1.0.dylib
vision: /usr/local/lib/libopencv_videostab.3.1.0.dylib
vision: /usr/local/lib/libopencv_superres.3.1.0.dylib
vision: /usr/local/lib/libopencv_stitching.3.1.0.dylib
vision: /usr/local/lib/libopencv_photo.3.1.0.dylib
vision: /usr/local/lib/libopencv_text.3.1.0.dylib
vision: /usr/local/lib/libopencv_face.3.1.0.dylib
vision: /usr/local/lib/libopencv_ximgproc.3.1.0.dylib
vision: /usr/local/lib/libopencv_xfeatures2d.3.1.0.dylib
vision: /usr/local/lib/libopencv_shape.3.1.0.dylib
vision: /usr/local/lib/libopencv_video.3.1.0.dylib
vision: /usr/local/lib/libopencv_objdetect.3.1.0.dylib
vision: /usr/local/lib/libopencv_calib3d.3.1.0.dylib
vision: /usr/local/lib/libopencv_features2d.3.1.0.dylib
vision: /usr/local/lib/libopencv_ml.3.1.0.dylib
vision: /usr/local/lib/libopencv_highgui.3.1.0.dylib
vision: /usr/local/lib/libopencv_videoio.3.1.0.dylib
vision: /usr/local/lib/libopencv_imgcodecs.3.1.0.dylib
vision: /usr/local/lib/libopencv_imgproc.3.1.0.dylib
vision: /usr/local/lib/libopencv_flann.3.1.0.dylib
vision: /usr/local/lib/libopencv_core.3.1.0.dylib
vision: CMakeFiles/vision.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/brenemal/personal/sudocv/cpp/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable vision"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/vision.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/vision.dir/build: vision

.PHONY : CMakeFiles/vision.dir/build

CMakeFiles/vision.dir/requires: CMakeFiles/vision.dir/vision.cpp.o.requires

.PHONY : CMakeFiles/vision.dir/requires

CMakeFiles/vision.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/vision.dir/cmake_clean.cmake
.PHONY : CMakeFiles/vision.dir/clean

CMakeFiles/vision.dir/depend:
	cd /Users/brenemal/personal/sudocv/cpp && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/brenemal/personal/sudocv/cpp /Users/brenemal/personal/sudocv/cpp /Users/brenemal/personal/sudocv/cpp /Users/brenemal/personal/sudocv/cpp /Users/brenemal/personal/sudocv/cpp/CMakeFiles/vision.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/vision.dir/depend
