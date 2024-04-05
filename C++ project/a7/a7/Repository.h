#pragma once
#include <vector>
#include "Entities.h"
#include <string>
#include <algorithm>
#include <stdexcept>
#include <fstream>
#include "RepositoryExceptions.h"

using namespace std;

class Repository
{
private:
	std::vector<Entities> array_in_repository;
	std::string filename_with_stored_information_about_dogs;
public:
	Repository();
	Repository(const std::string& given_filename);
	~Repository() {};
	void resizeArrayInRepository();
	void copyGivenRepositoryIntoThisRepository(Repository& repository_to_copy);
	Entities& operator[](int position_in_array);
	int getLengthOfDynamicVectorInRepository();
	void addElementIntoDynamicVectorInRepository(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph);
	void deleteElementFromDynamicVectorInRepository(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph);
	void updateElementFromDynamicVectorInRepository(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph, std::string& new_given_name, std::string& new_given_breed, int new_given_age, std::string& new_given_photograph);
	std::vector<Entities> returnTheArrayInRepository();
	void readInformationAboutDogsFromFile();
	void writeInformationAboutDogsIntoFile();
};