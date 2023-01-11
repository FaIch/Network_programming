#include <iostream>
#include <list>
#include <vector>
#include <thread>

using namespace std;

bool isPrime(int number){
    int i;
    bool is_prime = true;

    if (number == 0 || number == 1) {
        is_prime = false;
    }

    for (i = 2; i <= number/2; ++i) {
        if (number % i == 0) {
            is_prime = false;
            break;
        }
    }
    return is_prime;
}

void primesBetweenNumbers(int lower, int higher, list<int>&primes){
    for (int i = lower; i < higher; ++i) {
        if (isPrime(i)){
            primes.emplace_back(i);
        }
    }
}

vector<pair<int, int>> createSubRanges(const int lower, const int higher, const int numberOfThreads){
    vector<pair<int, int>> rangeBounds{};

    const int difference = higher + 1 - lower;
    const int range = difference / numberOfThreads;
    int rest = difference % numberOfThreads;

    int start = lower;
    int end = lower + range - 1;

    for (int i = 0; i < numberOfThreads; ++i) {
        if (i < rest){
            end++;
        }
        rangeBounds.emplace_back(start, end);
        start = end + 1;
        end = start + range - 1;
    }
    return rangeBounds;
}

int main() {
    list<int> primes;
    int higher = 10;
    int lower = 1000;
    int numberOfThreads = 3;
    vector<pair<int, int>> rangeBounds = createSubRanges(lower, higher, numberOfThreads);
    vector<thread> threads;
    threads.reserve(numberOfThreads);

    for (int i = 0; i < numberOfThreads; ++i) {
        threads.emplace_back([i, rangeBounds, &primes]{
            primesBetweenNumbers(rangeBounds[i].first, rangeBounds[i].second, primes);
        });
    }

    for (auto &thread : threads){
        thread.join();
    }

    primes.sort();

    for (auto &number : primes){
        cout << number << endl;
    }

    return 0;
}
