#pragma once
#include <string>
#include <vector>
#include <iostream>

/*
	Trims leading and trailing spaces from a string.
	Input: s - string
	Output: a string, with no leading and trailing spaces.
*/
std::string trim(const std::string& s);

/*
	Tokenizes a string.
	Input:	str - the string to be tokenized.
	delimiter - char - the delimiter used for tokenization
	Output: a vector of strings, containing the tokens
*/
std::vector<std::string> tokenize(const std::string& str, char delimiter);

class Entities
{
private:
	std::string name;
	std::string breed;
	int age;
	std::string photograph;
public:
	Entities();
	Entities(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph);
	std::string getName();
	std::string getBreed();
	std::string getPhotograph();
	int getAge();
	void setName(std::string& given_name);
	void setBreed(std::string& given_breed);
	void setPhotograph(std::string& given_photograph);
	void setAge(int given_age);
	bool operator==(Entities& informationToBeComparedWith);
	bool operator!=(Entities& informationToBeComparedWith);

	std::string returnStringWithAllInformationAboutElement();
	friend std::istream& operator>>(std::istream& istream_file, Entities& dog_to_read_from_file);
	friend std::ostream& operator<<(std::ostream& ostream_file, Entities& dog_to_write_into_file);
};

