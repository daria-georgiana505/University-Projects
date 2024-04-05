#define _CRT_SECURE_NO_WARNINGS
#define CURL_STATICLIB

#include <stdio.h>
#include </Users/DARIA/source/repos/curl/curl_2/x64 Debug/include/curl/curl.h>
#include </Users/DARIA/source/repos/curl/curl_2/x64 Release/include/curl/curl.h>

#include <windows.h>

#include <opencv2/core.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/highgui.hpp>
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/opencv.hpp"
using namespace cv;

/* For older cURL versions you will also need
#include <curl/types.h>
#include <curl/easy.h>
*/

size_t write_data(void* ptr, size_t size, size_t nmemb, FILE* stream) {
	size_t written = fwrite(ptr, size, nmemb, stream);
	return written;
}

using namespace std;

//ex 1
#include "UI.h"
#include <iostream>

using namespace std;

void printAllOptionsAdministratorMode()
{
	std::cout << endl << "1- Add information about a dog" << endl;
	std::cout << "2- Delete information about a dog" << endl;
	std::cout << "3- Update information about a dog" << endl;
	std::cout << "4- Display information about all dogs" << endl;
	std::cout << "5- Exit administrator mode" << endl;
}

void UI::print_information_about_dogs_in_shelter()
{
	std::vector<Entities> array_used_for_printing_the_dogs = this->array_in_ui_with_dogs.returnTheArrayOfElements();

	if (array_used_for_printing_the_dogs.size() == 0)
	{
		cout << "Currently there are no dogs to adopt. Try again another day" << endl;
	}
	else
	{
		for (auto dog : array_used_for_printing_the_dogs)
		{
			std::cout << dog.returnStringWithAllInformationAboutElement() << "\n";
		}
	}
}

void UI::run_ui_administrator_mode()
{
	int print_the_array_with_all_the_elements = 4;
	int add_a_given_element_to_the_array = 1;
	int remove_a_given_element_from_the_array = 2;
	int update_the_information_of_a_given_element_in_the_array_with_new_given_information = 3;
	int exit_the_program = 5;

	while (true)
	{
		printAllOptionsAdministratorMode();
		int user_input_as_choice_for_functionality;
		std::cout << "> ";
		std::cin >> user_input_as_choice_for_functionality;
		std::cout << endl;
		//if (user_input_as_choice_for_functionality == 5)
		if (user_input_as_choice_for_functionality == exit_the_program)
		{
			break;
		}
		//if (user_input_as_choice_for_functionality == 4) //prints the array with all the elements
		if (user_input_as_choice_for_functionality == print_the_array_with_all_the_elements)
		{
			this->print_information_about_dogs_in_shelter();
		}
		//if (user_input_as_choice_for_functionality == 1) //adds a given element to the array
		if (user_input_as_choice_for_functionality == add_a_given_element_to_the_array)
		{
			try
			{
				std::string given_name;
				std::string given_breed;
				std::string given_age; //int given_age;
				std::string given_photograph;

				std::cout << "Given name: ";
				getline(cin >> ws, given_name);

				StringContainsAlphabeticLetters::validate(given_name);

				std::cout << "Given breed: ";
				getline(cin >> ws, given_breed);

				StringContainsAlphabeticLetters::validate(given_breed);

				std::cout << "Given age: ";
				getline(cin >> ws, given_age);

				StringIsNumber::validate(given_age);

				std::cout << "Given photograph url: ";
				getline(cin >> ws, given_photograph);

				this->array_in_ui_with_dogs.addElementIntoDynamicVectorInServices(given_name, given_breed, stoi(given_age), given_photograph);
				this->array_in_ui_with_dogs.writeInformationAboutDogsIntoFile();
			}
			catch (ValidatorClassUI& exception_for_input)
			{
				cout << endl << exception_for_input.what() << endl;
			}
			catch (RepositoryException& exception_for_repository)
			{
				cout << endl << exception_for_repository.what() << endl;
			}
			catch (ValidatorClassEntities& exception_for_dogs)
			{
				cout << endl << exception_for_dogs.what() << endl;
			}
			catch (FileException& file_exception)
			{
				cout << endl << file_exception.what() << endl;
			}
		}
		//if (user_input_as_choice_for_functionality == 2) //removes a given element from the array
		if (user_input_as_choice_for_functionality == remove_a_given_element_from_the_array)
		{
			try
			{
				std::string given_name;
				std::string given_breed;
				std::string given_age; //int given_age;
				std::string given_photograph;

				std::cout << "Given name: ";
				getline(cin >> ws, given_name);

				StringContainsAlphabeticLetters::validate(given_name);

				std::cout << "Given breed: ";
				getline(cin >> ws, given_breed);

				StringContainsAlphabeticLetters::validate(given_breed);

				std::cout << "Given age: ";
				getline(cin >> ws, given_age);

				StringIsNumber::validate(given_age);

				std::cout << "Given photograph url: ";
				getline(cin >> ws, given_photograph);
			
				this->array_in_ui_with_dogs.deleteElementFromDynamicVectorInServices(given_name, given_breed, stoi(given_age), given_photograph);
				this->array_in_ui_with_dogs.writeInformationAboutDogsIntoFile();
			}
			catch (ValidatorClassUI& exception_for_input)
			{
				cout << endl << exception_for_input.what() << endl;
			}
			catch (RepositoryException& exception_for_repository)
			{
				cout << endl << exception_for_repository.what() << endl;
			}
			catch (ValidatorClassEntities& exception_for_dogs)
			{
				cout << endl << exception_for_dogs.what() << endl;
			}
			catch (FileException& file_exception)
			{
				cout << endl << file_exception.what() << endl;
			}
		}
		//if (user_input_as_choice_for_functionality == 3) //updates the information of a given element in the array with new given information
		if (user_input_as_choice_for_functionality == update_the_information_of_a_given_element_in_the_array_with_new_given_information)
		{
			try
			{
				std::string given_name;
				std::string given_breed;
				std::string given_age; //int given_age;
				std::string given_photograph;

				std::cout << "Given name: ";
				getline(cin >> ws, given_name);

				StringContainsAlphabeticLetters::validate(given_name);

				std::cout << "Given breed: ";
				getline(cin >> ws, given_breed);

				StringContainsAlphabeticLetters::validate(given_breed);

				std::cout << "Given age: ";
				//std::cin >> given_age;
				//std::cin.ignore(1000, '\n'); //***
				getline(cin >> ws, given_age);

				StringIsNumber::validate(given_age);

				std::cout << "Given photograph url: ";
				getline(cin >> ws, given_photograph);

				std::string new_given_name;
				std::string new_given_breed;
				std::string new_given_age; //int new_given_age;
				std::string new_given_photograph;

				std::cout << "New given name: ";
				getline(cin >> ws, new_given_name);

				StringContainsAlphabeticLetters::validate(new_given_name);

				std::cout << "New given breed: ";
				getline(cin >> ws, new_given_breed);

				StringContainsAlphabeticLetters::validate(new_given_breed);

				std::cout << "New given age: ";
				getline(cin >> ws, new_given_age);

				StringIsNumber::validate(new_given_age);

				std::cout << "New given photograph url: ";
				getline(cin >> ws, new_given_photograph);

				this->array_in_ui_with_dogs.updateElementFromDynamicVectorInServices(given_name, given_breed, stoi(given_age), given_photograph, new_given_name, new_given_breed, stoi(new_given_age), new_given_photograph);
				this->array_in_ui_with_dogs.writeInformationAboutDogsIntoFile();
			}
			catch (ValidatorClassUI& exception_for_input)
			{
				cout << endl << exception_for_input.what() << endl;
			}
			catch (RepositoryException& exception_for_repository)
			{
				cout << endl << exception_for_repository.what() << endl;
			}
			catch (ValidatorClassEntities& exception_for_dogs)
			{
				cout << endl << exception_for_dogs.what() << endl;
			}
			catch (FileException& file_exception)
			{
				cout << endl << file_exception.what() << endl;
			}
		}
	}
}

void UI::print_information_about_dogs_in_adoption_list()
{
	std::vector<Entities> array_used_for_printing_the_elements = this->array_in_ui_with_dogs.returnTheAdoptionList();

	if (array_used_for_printing_the_elements.size() == 0)
	{
		cout << "Your adoption list is empty" << endl;
	}
	else
	{
		for (int i = 0; i < array_used_for_printing_the_elements.size(); i++)
		{
			std::cout << array_used_for_printing_the_elements[i].returnStringWithAllInformationAboutElement() << "\n";
		}
	}
}

void printAllOptionsUserMode()
{
	std::cout << endl << "1- See the dogs in the database" << endl;
	std::cout << "2- See all the dogs of a given breed, having an age less than a given number" << endl;
	std::cout << "3- See the adoption list" << endl;
	std::cout << "4- Display the adoption list as a CSV or HTML file" << endl;
	std::cout << "5- Exit user mode" << endl;
}

void UI::run_ui_user_mode()
{
	int see_the_dogs_in_the_database = 1;
	int see_all_the_dogs_of_a_given_breed_having_an_age_less_than_given_number = 2;
	int see_the_adoption_list = 3;
	int display_the_adoption_list_as_csv_or_html_file = 4;
	int exit_the_program = 5;

	std::string input_csv_or_html;
	std::cout << endl << "Write 'CSV' to save in a csv file or 'HTML' to save in a html file" << endl;
	std::cin >> input_csv_or_html;
	std::cout << endl;
	if (input_csv_or_html == "CSV")
	{
		this->array_in_ui_with_dogs.set_type_of_file_to_display(new csvAdoptionList());
	}
	else
	{
		this->array_in_ui_with_dogs.set_type_of_file_to_display(new htmlAdoptionList());
	}

	while (true)
	{
		printAllOptionsUserMode();
		int user_input_as_choice_for_functionality;
		std::cout << "> ";
		std::cin >> user_input_as_choice_for_functionality;
		std::cout << endl;
		if (user_input_as_choice_for_functionality == exit_the_program)
			break;
		if (user_input_as_choice_for_functionality == display_the_adoption_list_as_csv_or_html_file)
		{
			this->array_in_ui_with_dogs.write_into_csv_or_html_file();
			this->array_in_ui_with_dogs.display_csv_or_html_file();
		}
		if (user_input_as_choice_for_functionality == see_the_dogs_in_the_database)
		{
			bool returned_value_of_function = this->display_information_about_dogs_in_given_array();
			bool stop_displaying_information_about_dogs = true;
			while (returned_value_of_function != stop_displaying_information_about_dogs)
				returned_value_of_function = this->display_information_about_dogs_in_given_array();
		}
		if (user_input_as_choice_for_functionality == see_the_adoption_list)
		{
			this->print_information_about_dogs_in_adoption_list();
		}
		if (user_input_as_choice_for_functionality == see_all_the_dogs_of_a_given_breed_having_an_age_less_than_given_number)
		{
			try
			{
				std::string given_breed;
				std::string given_age;
				std::cout << "Given breed: ";
				std::cin.ignore(1000, '\n'); //***
				getline(cin, given_breed);

				StringContainsAlphabeticLetters::validate(given_breed);

				std::cout << "Given age: ";
				getline(cin >> ws, given_age);

				StringIsNumber::validate(given_age);

				if (stoi(given_age) <= 0)
					throw std::exception("The age of a dog cannot be negative or equal to zero");
			
				bool returned_value_of_function = this->display_information_about_dogs_with_given_breed_and_age_less_than_given_integer_in_given_array(given_breed, stoi(given_age));
				bool stop_displaying_information_about_dogs = true;
				while (returned_value_of_function != stop_displaying_information_about_dogs)
					returned_value_of_function = this->display_information_about_dogs_with_given_breed_and_age_less_than_given_integer_in_given_array(given_breed, stoi(given_age));
			}
			catch (ValidatorClassUI& exception_for_input)
			{
				cout << endl << exception_for_input.what() << endl;
			}
			catch (std::exception& exception_catched)
			{
				cout << endl << exception_catched.what() << endl;
			}
		}
	}
}

bool UI::display_information_about_dogs_in_given_array()
{
	std::vector<Entities> array_used_for_displaying_the_elements = this->array_in_ui_with_dogs.returnTheArrayOfElements();

	bool stop_displaying_the_dogs_in_shelter = false;

	if (array_used_for_displaying_the_elements.size() == 0)
	{
		cout << "There are no dogs available for adoption" << endl;
		stop_displaying_the_dogs_in_shelter = true;
		return stop_displaying_the_dogs_in_shelter;
	}

	for (int i = 0; i < array_used_for_displaying_the_elements.size(); i++)
	{
		std::string current_url_of_photo = array_used_for_displaying_the_elements[i].getPhotograph();
		std::string current_name_of_saved_photo_from_url = current_url_of_photo.substr(current_url_of_photo.find_last_of("/") + 1);

		CURL* url_of_photograph;
		FILE* file_to_save_image;
		CURLcode result; 
		char file_path_of_saved_image[FILENAME_MAX] = "C:/Users/DARIA/source/repos/a45-photos/";
		strcat(file_path_of_saved_image, current_name_of_saved_photo_from_url.c_str());

		url_of_photograph = curl_easy_init();
		if (url_of_photograph) {
			file_to_save_image = fopen(file_path_of_saved_image, "wb");
			curl_easy_setopt(url_of_photograph, CURLOPT_URL, current_url_of_photo.c_str()); //curl_easy_setopt(curl, CURLOPT_URL, current_url_of_photo);
			curl_easy_setopt(url_of_photograph, CURLOPT_WRITEFUNCTION, write_data);
			curl_easy_setopt(url_of_photograph, CURLOPT_WRITEDATA, file_to_save_image);
			result = curl_easy_perform(url_of_photograph);

			/* always cleanup */
			curl_easy_cleanup(url_of_photograph);
			fclose(file_to_save_image);

			if (result == CURLE_OK)
			{

				Mat current_image_displayed = cv::imread(file_path_of_saved_image);
				namedWindow("Keep calm and adopt a pet", WINDOW_AUTOSIZE);

				if (current_image_displayed.cols > 1080 || current_image_displayed.rows > 1920)
					resize(current_image_displayed, current_image_displayed, Size(current_image_displayed.cols - 500, current_image_displayed.rows - 500), INTER_LINEAR);

				putText(current_image_displayed, array_used_for_displaying_the_elements[i].getName(), Point(10, current_image_displayed.rows - 70 - 15), FONT_HERSHEY_SIMPLEX, 1, Scalar(0, 0, 0), 8, LINE_AA);
				putText(current_image_displayed, array_used_for_displaying_the_elements[i].getName(), Point(10, current_image_displayed.rows - 70 - 15), FONT_HERSHEY_SIMPLEX, 1, Scalar(255, 255, 255), 2, LINE_AA);

				putText(current_image_displayed, array_used_for_displaying_the_elements[i].getBreed(), Point(10, current_image_displayed.rows - 35 - 15), FONT_HERSHEY_SIMPLEX, 1, Scalar(0, 0, 0), 8, LINE_AA);
				putText(current_image_displayed, array_used_for_displaying_the_elements[i].getBreed(), Point(10, current_image_displayed.rows - 35 - 15), FONT_HERSHEY_SIMPLEX, 1, Scalar(255, 255, 255), 2, LINE_AA);

				putText(current_image_displayed, to_string(array_used_for_displaying_the_elements[i].getAge()), Point(10, current_image_displayed.rows - 15), FONT_HERSHEY_SIMPLEX, 1, Scalar(0, 0, 0), 8, LINE_AA);
				putText(current_image_displayed, to_string(array_used_for_displaying_the_elements[i].getAge()), Point(10, current_image_displayed.rows - 15), FONT_HERSHEY_SIMPLEX, 1, Scalar(255, 255, 255), 2, LINE_AA);


				imshow("Keep calm and adopt a pet", current_image_displayed);
				int position_of_window_on_x_axis = 1920 / 2 - current_image_displayed.cols / 2;
				int position_of_window_on_y_axis = 1080 / 2 - current_image_displayed.rows / 2 - 30;
				moveWindow("Keep calm and adopt a pet", position_of_window_on_x_axis, position_of_window_on_y_axis);
				waitKey(0);
				destroyAllWindows();

			LABEL:
				cout << "Do you want to adopt this dog?" << endl << "Write 'yes' to add it to the adoption list" << endl << "Write 'no' to not adopt it" << endl << "Write 'exit' to exit from this option" << endl << "> ";
				std::string user_input_for_adopting_a_dog;
				cin >> user_input_for_adopting_a_dog;
				cout << endl;

				if (user_input_for_adopting_a_dog == "yes")
				{
					std::string name_of_current_dog = array_used_for_displaying_the_elements[i].getName();
					std::string breed_of_current_dog = array_used_for_displaying_the_elements[i].getBreed();
					int age_of_current_dog = array_used_for_displaying_the_elements[i].getAge();
					std::string photo_of_current_dog = array_used_for_displaying_the_elements[i].getPhotograph();
					this->array_in_ui_with_dogs.addDogIntoAdoptionList(name_of_current_dog, breed_of_current_dog, age_of_current_dog, photo_of_current_dog);
					this->array_in_ui_with_dogs.deleteElementFromDynamicVectorInServices(name_of_current_dog, breed_of_current_dog, age_of_current_dog, photo_of_current_dog);
					this->array_in_ui_with_dogs.writeInformationAboutDogsIntoFile();
				}
				else
				{
					if (user_input_for_adopting_a_dog == "no")
					{
						;
					}
					else
					{
						if (user_input_for_adopting_a_dog == "exit")
						{
							stop_displaying_the_dogs_in_shelter = true;
							return stop_displaying_the_dogs_in_shelter;
						}
						else
						{
							cout << "This option does not exist. Try again" << endl << endl;
							goto LABEL;
						}
					}
				}
			}
			else
			{
				cout << "Some photograph links are not ok" << endl << endl;
			}
		}
		else
		{
			cout << "Some photograph links are not ok" << endl << endl;
		}
	}
	return stop_displaying_the_dogs_in_shelter;
}

bool UI::display_information_about_dogs_with_given_breed_and_age_less_than_given_integer_in_given_array(std::string& given_breed, int given_age)
{
	std::vector<Entities> array_with_all_the_elements = this->array_in_ui_with_dogs.returnTheArrayOfElements();
	std::vector<Entities> array_used_for_displaying_the_elements;

	std::copy_if(array_with_all_the_elements.begin(), array_with_all_the_elements.end(), std::back_inserter(array_used_for_displaying_the_elements), [&](Entities& currentElement) {
		return (currentElement.getBreed() == given_breed && currentElement.getAge() < given_age); });

	bool stop_displaying_the_dogs_in_shelter = false;

	if (array_used_for_displaying_the_elements.size() == 0)
	{
		cout << "There are no dogs available for adoption" << endl;
		stop_displaying_the_dogs_in_shelter = true;
		return stop_displaying_the_dogs_in_shelter;
	}

	for (int i = 0; i < array_used_for_displaying_the_elements.size(); i++)
	{
		std::string current_url_of_photo = array_used_for_displaying_the_elements[i].getPhotograph();
		std::string current_name_of_saved_photo_from_url = current_url_of_photo.substr(current_url_of_photo.find_last_of("/") + 1);

		CURL* url_of_photograph;
		FILE* file_to_save_image;
		CURLcode result;
		char file_path_of_saved_image[FILENAME_MAX] = "C:/Users/DARIA/source/repos/a45-photos/";
		strcat(file_path_of_saved_image, current_name_of_saved_photo_from_url.c_str());

		url_of_photograph = curl_easy_init();
		if (url_of_photograph) {
			file_to_save_image = fopen(file_path_of_saved_image, "wb");
			curl_easy_setopt(url_of_photograph, CURLOPT_URL, current_url_of_photo.c_str()); //curl_easy_setopt(curl, CURLOPT_URL, current_url_of_photo);
			curl_easy_setopt(url_of_photograph, CURLOPT_WRITEFUNCTION, write_data);
			curl_easy_setopt(url_of_photograph, CURLOPT_WRITEDATA, file_to_save_image);
			result = curl_easy_perform(url_of_photograph);

			/* always cleanup */
			curl_easy_cleanup(url_of_photograph);
			fclose(file_to_save_image);

			if (result == CURLE_OK)
			{

				Mat current_image_displayed = cv::imread(file_path_of_saved_image);
				namedWindow("Keep calm and adopt a pet", WINDOW_AUTOSIZE);

				if (current_image_displayed.cols > 1080 || current_image_displayed.rows > 1920)
					resize(current_image_displayed, current_image_displayed, Size(current_image_displayed.cols - 500, current_image_displayed.rows - 500), INTER_LINEAR);

				putText(current_image_displayed, array_used_for_displaying_the_elements[i].getName(), Point(10, current_image_displayed.rows - 70 - 15), FONT_HERSHEY_SIMPLEX, 1, Scalar(0, 0, 0), 8, LINE_AA);
				putText(current_image_displayed, array_used_for_displaying_the_elements[i].getName(), Point(10, current_image_displayed.rows - 70 - 15), FONT_HERSHEY_SIMPLEX, 1, Scalar(255, 255, 255), 2, LINE_AA);

				putText(current_image_displayed, array_used_for_displaying_the_elements[i].getBreed(), Point(10, current_image_displayed.rows - 35 - 15), FONT_HERSHEY_SIMPLEX, 1, Scalar(0, 0, 0), 8, LINE_AA);
				putText(current_image_displayed, array_used_for_displaying_the_elements[i].getBreed(), Point(10, current_image_displayed.rows - 35 - 15), FONT_HERSHEY_SIMPLEX, 1, Scalar(255, 255, 255), 2, LINE_AA);

				putText(current_image_displayed, to_string(array_used_for_displaying_the_elements[i].getAge()), Point(10, current_image_displayed.rows - 15), FONT_HERSHEY_SIMPLEX, 1, Scalar(0, 0, 0), 8, LINE_AA);
				putText(current_image_displayed, to_string(array_used_for_displaying_the_elements[i].getAge()), Point(10, current_image_displayed.rows - 15), FONT_HERSHEY_SIMPLEX, 1, Scalar(255, 255, 255), 2, LINE_AA);


				imshow("Keep calm and adopt a pet", current_image_displayed);
				int position_of_window_on_x_axis = 1920 / 2 - current_image_displayed.cols / 2;
				int position_of_window_on_y_axis = 1080 / 2 - current_image_displayed.rows / 2 - 30;
				moveWindow("Keep calm and adopt a pet", position_of_window_on_x_axis, position_of_window_on_y_axis);
				waitKey(0);
				destroyAllWindows();

			LABEL:
				cout << "Do you want to adopt this dog?" << endl << "Write 'yes' to add it to the adoption list" << endl << "Write 'no' to not adopt it" << endl << "Write 'exit' to exit from this option" << endl << "> ";
				std::string user_input_for_adopting_a_dog;
				cin >> user_input_for_adopting_a_dog;
				cout << endl;

				if (user_input_for_adopting_a_dog == "yes")
				{
					std::string name_of_current_dog = array_used_for_displaying_the_elements[i].getName();
					std::string breed_of_current_dog = array_used_for_displaying_the_elements[i].getBreed();
					int age_of_current_dog = array_used_for_displaying_the_elements[i].getAge();
					std::string photo_of_current_dog = array_used_for_displaying_the_elements[i].getPhotograph();
					this->array_in_ui_with_dogs.addDogIntoAdoptionList(name_of_current_dog, breed_of_current_dog, age_of_current_dog, photo_of_current_dog);
					this->array_in_ui_with_dogs.deleteElementFromDynamicVectorInServices(name_of_current_dog, breed_of_current_dog, age_of_current_dog, photo_of_current_dog);
					this->array_in_ui_with_dogs.writeInformationAboutDogsIntoFile();
				}
				else
				{
					if (user_input_for_adopting_a_dog == "no")
					{
						;
					}
					else
					{
						if (user_input_for_adopting_a_dog == "exit")
						{
							stop_displaying_the_dogs_in_shelter = true;
							return stop_displaying_the_dogs_in_shelter;
						}
						else
						{
							cout << "This option does not exist. Try again" << endl << endl;
							goto LABEL;
						}
					}
				}
			}
			else
			{
				cout << "Some photograph links are not ok" << endl << endl;
			}
		}
		else
		{
			cout << "Some photograph links are not ok" << endl << endl;
		}
	}
	return stop_displaying_the_dogs_in_shelter;
}

void UI::run_app()
{
	//this->add_10_elements_in_array();

	while (true)
	{
		cout << endl << "Write 'user' for user options" << endl << "Write 'admin' for administrator options" << endl << "Write 'exit' for exiting the program" << endl << "> ";
		string input_for_admin_or_user;
		cin >> input_for_admin_or_user;
		cout << endl;
		if (input_for_admin_or_user == "user")
		{
			this->run_ui_user_mode();
			//break;
		}
		else
		{
			if (input_for_admin_or_user == "admin")
			{
				this->run_ui_administrator_mode();
				//break;
			}
			else
			{
				if (input_for_admin_or_user == "exit")
					break;
				else
					cout << "Choose between the above options!" << endl;
			}
		}
	}
}

//void UI::add_10_elements_in_array()
//{
//	this->array_in_ui_with_dogs.add10ElementsInArrayInServices();
//}
