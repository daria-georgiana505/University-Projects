#pragma once
#include "Repository.h"
#include <string>
#include <stdexcept>
#include <vector>
#include "RepositoryExceptions.h"
#include "ValidatorClassEntities.h"
#include "FileAdoptionList.h"

class Services
{
private:
	Repository array_in_services;
	Repository adoption_list;
	FileAdoptionList* type_of_file_to_display;
public:
	Services() {};
	Services(Repository& given_repository) : array_in_services{ given_repository } {};
	~Services() {};
	void resizeArrayInServices();
	void copyGivenServicesArrayIntoThisServicesArray(Services& services_array_to_copy);
	Entities& operator[](int position_in_array);
	int getLengthOfDynamicVectorInServices();
	//void add10ElementsInArrayInServices();
	void addElementIntoDynamicVectorInServices(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph);
	void deleteElementFromDynamicVectorInServices(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph);
	void updateElementFromDynamicVectorInServices(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph, std::string& new_given_name, std::string& new_given_breed, int new_given_age, std::string& new_given_photograph);
	std::vector<Entities> returnTheArrayOfElements();
	void addDogIntoAdoptionList(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph);
	std::vector<Entities> returnTheAdoptionList();
	void set_type_of_file_to_display(FileAdoptionList* given_type_of_file);
	void writeInformationAboutDogsIntoFile();
	void write_into_csv_or_html_file();
	void display_csv_or_html_file();
};