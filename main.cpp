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

list<int> primesBetweenNumbers(int lower, int higher, int threads){
    list<int> primes;
    for (int i = lower + 1; i < higher; ++i) {
        if (isPrime(i)){
            primes.push_back(i);
        }
    }

    return primes;
}

int main() {
    list<int> allPrimes;
    int higher = 10;
    int lower = 1000;
    int numberOfThreads = 3;
    vector<thread> threads(numberOfThreads);

    int range = (higher - lower) / numberOfThreads;
    int newHigher = higher;
    int newLower = lower;
    for (int i = 0; i < numberOfThreads; ++i) {
        threads.emplace_back([i, &allPrimes, newHigher, newLower]{
            
        });
    }

    return 0;
}
