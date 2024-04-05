#include "Tests.h"
#include <assert.h>
#include <cstring>

void testEntities()
{
	Entities empty_entity;
	assert(empty_entity.getName() == "");
	assert(empty_entity.getBreed() == "");
	assert(empty_entity.getAge() == 0);
	assert(empty_entity.getPhotograph() == "");

	std::string name = "Max";
	std::string breed = "Golden Retriever";
	std::string photo_url = "https://www.google.com/search?q=golden+retriever";
	Entities information_about_a_dog(name, breed, 2, photo_url);
	assert(information_about_a_dog.getName() == name);
	assert(information_about_a_dog.getBreed() == breed);
	assert(information_about_a_dog.getAge() == 2);
	assert(information_about_a_dog.getPhotograph() == photo_url);

	std::string new_name = "Nero";
	std::string new_breed = "Greyhound";
	std::string new_photo_url = "https://www.google.com/search?q=greyhound";
	information_about_a_dog.setName(new_name);
	information_about_a_dog.setBreed(new_breed);
	information_about_a_dog.setAge(4);
	information_about_a_dog.setPhotograph(new_photo_url);
	assert(information_about_a_dog.getName() == new_name);
	assert(information_about_a_dog.getBreed() == new_breed);
	assert(information_about_a_dog.getAge() == 4);
	assert(information_about_a_dog.getPhotograph() == new_photo_url);

	Entities information_about_a_dog2(new_name, breed, 4, new_photo_url);
	Entities information_about_a_dog3(new_name, new_breed, 4, new_photo_url);
	assert(information_about_a_dog == information_about_a_dog3);
	assert(information_about_a_dog != information_about_a_dog2);

	std::string string_of_element;
	string_of_element = new_name;
	string_of_element += ",";
	string_of_element += new_breed;
	string_of_element += ",";
	string_of_element += std::to_string(4);
	string_of_element += ",";
	string_of_element += new_photo_url;
	string_of_element += "\0";
	assert(information_about_a_dog.returnStringWithAllInformationAboutElement() == string_of_element);
}

void testRepository()
{
	Repository array_with_elements;
	Repository copy_of_array_with_elements;
	array_with_elements.resizeArrayInRepository();

	std::string first_dog_name = "Max";
	std::string first_dog_breed = "Golden Retriever";
	std::string first_dog_photo_url = "https://www.google.com/search?q=golden+retriever";
	Entities information_about_a_dog(first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	array_with_elements.addElementIntoDynamicVectorInRepository(first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	std::string second_dog_name = "Nero";
	std::string second_dog_breed = "Greyhound";
	std::string second_dog_photo_url = "https://www.google.com/search?q=greyhound";
	Entities information_about_a_dog2(second_dog_name, second_dog_breed, 4, second_dog_photo_url);
	array_with_elements.addElementIntoDynamicVectorInRepository(second_dog_name, second_dog_breed, 4, second_dog_photo_url);
	assert(array_with_elements.getLengthOfDynamicVectorInRepository() == 2);
	assert(array_with_elements[0] == information_about_a_dog);
	assert(array_with_elements[1] == information_about_a_dog2);

	bool check_if_an_exception_was_thrown = false;
	try
	{
		array_with_elements.addElementIntoDynamicVectorInRepository(first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	}
	catch (std::exception& exception)
	{
		check_if_an_exception_was_thrown = true;
	}
	assert(check_if_an_exception_was_thrown == true);

	copy_of_array_with_elements.copyGivenRepositoryIntoThisRepository(array_with_elements);
	assert(copy_of_array_with_elements.getLengthOfDynamicVectorInRepository() == 2);
	assert(copy_of_array_with_elements[0] == information_about_a_dog);
	assert(copy_of_array_with_elements[1] == information_about_a_dog2);

	array_with_elements.deleteElementFromDynamicVectorInRepository(first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	assert(array_with_elements.getLengthOfDynamicVectorInRepository() == 1);
	assert(array_with_elements[0] == information_about_a_dog2);

	check_if_an_exception_was_thrown = false;
	try
	{
		array_with_elements.deleteElementFromDynamicVectorInRepository(first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	}
	catch (std::exception& exception)
	{
		check_if_an_exception_was_thrown = true;
	}
	assert(check_if_an_exception_was_thrown == true);

	array_with_elements.updateElementFromDynamicVectorInRepository(second_dog_name, second_dog_breed, 4, second_dog_photo_url, first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	assert(array_with_elements.getLengthOfDynamicVectorInRepository() == 1);
	assert(array_with_elements[0] == information_about_a_dog);

	check_if_an_exception_was_thrown = false;
	try
	{
		array_with_elements.updateElementFromDynamicVectorInRepository(second_dog_name, second_dog_breed, 4, second_dog_photo_url, first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	}
	catch (std::exception& exception)
	{
		check_if_an_exception_was_thrown = true;
	}
	assert(check_if_an_exception_was_thrown == true);

	std::vector<Entities> array_to_store_the_elements_returned_by_a_function_called_returnTheArrayInRepository = array_with_elements.returnTheArrayInRepository();
	assert(array_to_store_the_elements_returned_by_a_function_called_returnTheArrayInRepository.size() == 1);
}

void testServices()
{
	Services array_with_elements;
	Services copy_of_array_with_elements;
	array_with_elements.resizeArrayInServices();

	std::string first_dog_name = "Max";
	std::string first_dog_breed = "Golden Retriever";
	std::string first_dog_photo_url = "https://www.google.com/search?q=golden+retriever";
	Entities information_about_a_dog(first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	array_with_elements.addElementIntoDynamicVectorInServices(first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	std::string second_dog_name = "Nero";
	std::string second_dog_breed = "Greyhound";
	std::string second_dog_photo_url = "https://www.google.com/search?q=greyhound";
	Entities information_about_a_dog2(second_dog_name, second_dog_breed, 4, second_dog_photo_url);
	array_with_elements.addElementIntoDynamicVectorInServices(second_dog_name, second_dog_breed, 4, second_dog_photo_url);
	assert(array_with_elements.getLengthOfDynamicVectorInServices() == 2);
	assert(array_with_elements[0] == information_about_a_dog);
	assert(array_with_elements[1] == information_about_a_dog2);

	bool check_if_an_exception_was_thrown = false;
	try
	{
		array_with_elements.addElementIntoDynamicVectorInServices(first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	}
	catch (std::exception& exception)
	{
		check_if_an_exception_was_thrown = true;
	}
	assert(check_if_an_exception_was_thrown == true);

	copy_of_array_with_elements.copyGivenServicesArrayIntoThisServicesArray(array_with_elements);
	assert(copy_of_array_with_elements.getLengthOfDynamicVectorInServices() == 2);
	assert(copy_of_array_with_elements[0] == information_about_a_dog);
	assert(copy_of_array_with_elements[1] == information_about_a_dog2);

	array_with_elements.deleteElementFromDynamicVectorInServices(first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	assert(array_with_elements.getLengthOfDynamicVectorInServices() == 1);
	assert(array_with_elements[0] == information_about_a_dog2);

	check_if_an_exception_was_thrown = false;
	try
	{
		array_with_elements.deleteElementFromDynamicVectorInServices(first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	}
	catch (std::exception& exception)
	{
		check_if_an_exception_was_thrown = true;
	}
	assert(check_if_an_exception_was_thrown == true);

	array_with_elements.updateElementFromDynamicVectorInServices(second_dog_name, second_dog_breed, 4, second_dog_photo_url, first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	assert(array_with_elements.getLengthOfDynamicVectorInServices() == 1);
	assert(array_with_elements[0] == information_about_a_dog);

	check_if_an_exception_was_thrown = false;
	try
	{
		array_with_elements.updateElementFromDynamicVectorInServices(second_dog_name, second_dog_breed, 4, second_dog_photo_url, first_dog_name, first_dog_breed, 2, first_dog_photo_url);
	}
	catch (std::exception& exception)
	{
		check_if_an_exception_was_thrown = true;
	}
	assert(check_if_an_exception_was_thrown == true);

	/*Services array_to_add_10_elements;
	array_to_add_10_elements.add10ElementsInArrayInServices();
	assert(array_to_add_10_elements.getLengthOfDynamicVectorInServices() == 10);*/

	std::vector<Entities> array_to_store_the_elements_returned_by_a_function_called_returnTheArrayInRepository = array_with_elements.returnTheArrayOfElements();
	assert(array_to_store_the_elements_returned_by_a_function_called_returnTheArrayInRepository.size() == 1);
}

void testAllFunctions()
{
	testEntities();
	testRepository();
	testServices();
}
