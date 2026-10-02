cmake_minimum_required(VERSION 3.22.1)

project("rider_noise_suppression")

add_library(
    rider_noise_suppression
    SHARED
    native-lib.cpp
)

target_include_directories(
    rider_noise_suppression
    PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}/webrtc
)

find_library(
    log-lib
    log
)

target_link_libraries(
    rider_noise_suppression
    ${log-lib}
)
