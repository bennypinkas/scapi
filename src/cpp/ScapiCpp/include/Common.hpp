#ifndef SCAPI_COMMON_H
#define SCAPI_COMMON_H

#include <iostream>
#include <unordered_map>
#include <string>
#include <boost/multiprecision/random.hpp>
#include <random>
#include <boost/multiprecision/miller_rabin.hpp>
#include <math.h> /* pow */
#include <boost/multiprecision/cpp_int.hpp>

using namespace std;
// Using boost::multiprecision:mpz_int - Arbitrary precision integer type.
namespace mp = boost::multiprecision;     // Reduce the typing a bit later...
using biginteger = boost::multiprecision::cpp_int;

typedef unsigned char byte;		// put in global namespace to avoid ambiguity with other byte typedefs


int find_log2_floor(biginteger);
int NumberOfBits(biginteger bi);
size_t bytesCount(biginteger value);
void gen_random_bytes_vector(vector<byte> &v, const int len);
void copy_byte_vector_to_byte_array(const vector<byte> &source_vector, byte * dest, int beginIndex);
void encodeBigInteger(biginteger value, byte* output, size_t length);
biginteger decodeBigInteger(byte* input, size_t length);

#endif