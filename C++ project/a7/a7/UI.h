#pragma once
#include <string>
#include <algorithm>
#include <iterator>
//#include "uiInputExceptions.h"
#include "ValidatorClassUI.h"
#include "ValidatorClassEntities.h"

//#include <cstring>
#include "Services.h"
#include <stdexcept>
#include <iostream>
#include <vector>
//#include <curl.h>
#include "RepositoryExceptions.h"

#include "csvAdoptionList.h"
#include "htmlAdoptionList.h"

class UI
{
private:
	Services array_in_ui_with_dogs;
	void print_information_about_dogs_in_shelter();
	void run_ui_administrator_mode();
	void print_information_about_dogs_in_adoption_list();
	void run_ui_user_mode();
	bool display_information_about_dogs_in_given_array();
	bool display_information_about_dogs_with_given_breed_and_age_less_than_given_integer_in_given_array(std::string& given_breed, int given_age);
public:
	UI() {};
	UI(Services& given_services) : array_in_ui_with_dogs{given_services} {};
	void run_app();
	//void add_10_elements_in_array();
};


