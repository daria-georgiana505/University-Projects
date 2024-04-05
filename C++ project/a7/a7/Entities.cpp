#include "Entities.h"
//using namespace std;

#include <sstream>
#include <string>
#include <vector>

using namespace std;

std::string trim(const std::string& s)
{
	std::string result(s);
	size_t pos = result.find_first_not_of(" ");
	if (pos != -1)
		result.erase(0, pos);
	pos = result.find_last_not_of(" ");
	if (pos != std::string::npos)
		result.erase(pos + 1);

	return result;
}

vector<string> tokenize(const string& str, char delimiter)
{
	vector <string> result;
	stringstream ss(str);
	string token;
	while (getline(ss, token, delimiter))
		result.push_back(token);

	return result;
}

Entities::Entities() : name(""), breed(""), age(0), photograph("") {}

Entities::Entities(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph) : name(given_name), breed(given_breed), age(given_age), photograph(given_photograph) {}

std::string Entities::getName()
{
	return this->name;
}

std::string Entities::getBreed()
{
	return this->breed;
}

std::string Entities::getPhotograph()
{
	return this->photograph;
}

int Entities::getAge()
{
	return this->age;
}

void Entities::setName(std::string& given_name)
{
	this->name = given_name;
}

void Entities::setBreed(std::string& given_breed)
{
	this->breed = given_breed;
}

void Entities::setPhotograph(std::string& given_photograph)
{
	this->photograph = given_photograph;
}

void Entities::setAge(int given_age)
{
	this->age = given_age;
}

bool Entities::operator==(Entities& informationToBeComparedWith)
{
	if (this->name == informationToBeComparedWith.name && this->breed == informationToBeComparedWith.breed && this->age == informationToBeComparedWith.age && this->photograph == informationToBeComparedWith.photograph)
		return true;
	return false;
}

bool Entities::operator!=(Entities& informationToBeComparedWith)
{
	if (this->name != informationToBeComparedWith.name || this->breed != informationToBeComparedWith.breed || this->age != informationToBeComparedWith.age || this->photograph != informationToBeComparedWith.photograph)
		return true;
	return false;
}

std::string Entities::returnStringWithAllInformationAboutElement()
{
	std::string string_of_element;
	string_of_element = this->name;
	string_of_element += ",";
	string_of_element += this->breed;
	string_of_element += ",";
	string_of_element += std::to_string(this->age);
	string_of_element += ",";
	string_of_element += this->photograph;
	string_of_element += "\0";
	return string_of_element;
}

std::istream& operator>>(std::istream& istream_file, Entities& dog_to_read_from_file)
{
	std::string line;
	getline(istream_file, line);

	std::vector<std::string> tokens = tokenize(line, ',');
	if (tokens.size() != 4)
		return istream_file;

	dog_to_read_from_file.name = tokens[0];
	dog_to_read_from_file.breed = tokens[1];
	dog_to_read_from_file.age = stoi(tokens[2]);
	dog_to_read_from_file.photograph = tokens[3];

	return istream_file;
}

std::ostream& operator<<(std::ostream& ostream_file, Entities& dog_to_write_into_file)
{
	ostream_file << dog_to_write_into_file.returnStringWithAllInformationAboutElement()<<"\n";
	return ostream_file;
}
