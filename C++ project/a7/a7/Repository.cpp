#include "Repository.h"

Repository::Repository()
{
}

Repository::Repository(const std::string& given_filename)
{
	this->filename_with_stored_information_about_dogs = given_filename;
	this->readInformationAboutDogsFromFile();
}

void Repository::resizeArrayInRepository()
{
	//this->array_in_repository.resize(this->array_in_repository.capacity()*2);
	this->array_in_repository.reserve(this->array_in_repository.capacity() * 2);
}

void Repository::copyGivenRepositoryIntoThisRepository(Repository& repository_to_copy)
{
	this->array_in_repository = repository_to_copy.array_in_repository;
}

Entities& Repository::operator[](int position_in_array)
{
	// TODO: insert return statement here
	return this->array_in_repository[position_in_array];
	//return this->array_in_repository.at(position_in_array);
}

int Repository::getLengthOfDynamicVectorInRepository()
{
	return this->array_in_repository.size();
}

void Repository::addElementIntoDynamicVectorInRepository(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph)
{
	auto position_of_element_we_want_to_add_in_array = std::find_if(this->array_in_repository.begin(), this->array_in_repository.end(), [&](Entities& currentElement) {
		return currentElement.getName() == given_name && currentElement.getBreed() == given_breed && currentElement.getAge() == given_age && currentElement.getPhotograph() == given_photograph; });

	if (position_of_element_we_want_to_add_in_array != this->array_in_repository.end())
	{
		//throw std::exception("The given element is already in the list");
		throw DuplicateDogException();
	}
	Entities element_created_to_add_in_array(given_name, given_breed, given_age, given_photograph);
	if (this->array_in_repository.size() == this->array_in_repository.capacity())
		this->resizeArrayInRepository();
	this->array_in_repository.push_back(element_created_to_add_in_array);
	/*this->writeInformationAboutDogsIntoFile();*/
}

void Repository::deleteElementFromDynamicVectorInRepository(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph)
{
	auto position_of_element_we_want_to_remove_in_array = std::find_if(this->array_in_repository.begin(), this->array_in_repository.end(), [&](Entities& currentElement) {
		return currentElement.getName() == given_name && currentElement.getBreed() == given_breed && currentElement.getAge() == given_age && currentElement.getPhotograph() == given_photograph; });
	if (position_of_element_we_want_to_remove_in_array == this->array_in_repository.end())
	{
		//throw std::exception("The given element was not found in the list");
		throw DogNotFoundException();
	}
	this->array_in_repository.erase(position_of_element_we_want_to_remove_in_array);
	//this->writeInformationAboutDogsIntoFile();
}

void Repository::updateElementFromDynamicVectorInRepository(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph, std::string& new_given_name, std::string& new_given_breed, int new_given_age, std::string& new_given_photograph)
{
	auto position_of_element_we_want_to_update_in_array = std::find_if(this->array_in_repository.begin(), this->array_in_repository.end(), [&given_name, &given_breed, given_age, &given_photograph](Entities& currentElement) {
		return currentElement.getName() == given_name && currentElement.getBreed() == given_breed && currentElement.getAge() == given_age && currentElement.getPhotograph() == given_photograph; });
	if (position_of_element_we_want_to_update_in_array == this->array_in_repository.end())
	{
		//throw std::exception("The given element was not found in the list");
		throw DogNotFoundException();
	}
	Entities element_created_to_update_in_array(new_given_name, new_given_breed, new_given_age, new_given_photograph);
	this->array_in_repository[position_of_element_we_want_to_update_in_array - this->array_in_repository.begin()] = element_created_to_update_in_array;
	//this->writeInformationAboutDogsIntoFile();
}

std::vector<Entities> Repository::returnTheArrayInRepository()
{
	std::vector<Entities> copy_of_array_we_want_to_return;
	copy_of_array_we_want_to_return = this->array_in_repository;
	return copy_of_array_we_want_to_return;
	//return this->array_in_repository;
}

void Repository::readInformationAboutDogsFromFile()
{
	ifstream file(this->filename_with_stored_information_about_dogs);

	if (!file.is_open())
		throw FileException("The file could not be opened!");

	Entities dog;
	while (file >> dog)
		this->array_in_repository.push_back(dog);

	file.close();
}

void Repository::writeInformationAboutDogsIntoFile()
{
	ofstream file(this->filename_with_stored_information_about_dogs);
	if (!file.is_open())
		throw FileException("The file could not be opened!");

	for (auto dog : this->array_in_repository)
	{
		file << dog;
	}

	file.close();
}


