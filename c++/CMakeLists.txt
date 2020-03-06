cmake_minimum_required(VERSION 3.14)

project(leetcode)

set(CMAKE_CXX_STANDARD 14)

include_directories(src/)

add_executable(AbsentMini src/_408/_2018_AbsentMini.cpp)
add_executable(LinkNodeReorder src/_408/_2019_LinkNodeReorder.cpp)
add_executable(MidExp src/_408/_2017_MidExp.cpp)
add_executable(RomoveAbsDup src/_408/_2015_RemoveAbsDup.cpp)

add_executable(_3_M src/leetcode/_3_M.cpp)
