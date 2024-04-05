#include "Services.h"

void Services::resizeArrayInServices()
{
	this->array_in_services.resizeArrayInRepository();
}

//Services::Services()
//{
//	
//}

void Services::copyGivenServicesArrayIntoThisServicesArray(Services& services_array_to_copy)
{
	this->array_in_services.copyGivenRepositoryIntoThisRepository(services_array_to_copy.array_in_services);
}

Entities& Services::operator[](int position_in_array)
{
	// TODO: insert return statement here
	return this->array_in_services[position_in_array];
}

int Services::getLengthOfDynamicVectorInServices()
{
	return this->array_in_services.getLengthOfDynamicVectorInRepository();
}

//void Services::add10ElementsInArrayInServices()
//{
//	std::string name1 = "Max";
//	std::string breed1 = "Golden Retriever";
//	//std::string photo1 = "https://www.google.com/search?q=golden+retriever";
//	std::string photo1 = "https://marshallspetzone.com/blog/wp-content/uploads/2017/01/6.jpg";
//	this->array_in_services.addElementIntoDynamicVectorInRepository(name1, breed1, 2, photo1);
//	//std::string name2 = "Nero";
//	//std::string breed2 = "Greyhound";
//	////std::string photo2 = "https://www.google.com/search?q=greyhound";
//	////std::string photo2 = "https://www.petfinder.com/sites/default/files/images/content/greyhound-detail-scaled.jpg";
//	//std::string photo2 = "https://www.thesprucepets.com/thmb/UhKKaZM-C689jYgDDm0u8_GEaYA=/1500x0/filters:no_upscale():strip_icc()/breed-profile-greyhound-1117972-hero-0941975164554e09a56ee3accb0fcec9.jpeg";
//	//this->array_in_services.addElementIntoDynamicVectorInRepository(name2, breed2, 4, photo2);
//	std::string name3 = "Apollo";
//	std::string breed3 = "Beagle";
//	//std::string photo3 = "https://www.google.com/search?q=beagle";
//	std::string photo3 = "https://s3.amazonaws.com/cdn-origin-etr.akc.org/wp-content/uploads/2021/01/13180313/Beagle-puppy-standing-in-the-grass-1.jpeg";
//	this->array_in_services.addElementIntoDynamicVectorInRepository(name3, breed3, 1, photo3);
//	std::string name4 = "Rex";
//	std::string breed4 = "German Shepherd";
//	//std::string photo4 = "https://www.google.com/search?q=german+shepherd";
//	std::string photo4 = "https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg";
//	this->array_in_services.addElementIntoDynamicVectorInRepository(name4, breed4, 4, photo4);
//	std::string name5 = "Luna";
//	std::string breed5 = "Labrador Retriever";
//	//std::string photo5 = "https://www.google.com/search?q=labrador+retriever";
//	std::string photo5 = "https://d3o47n6kn1r59u.cloudfront.net/images/dogbreeds/large/Labrador-Retriever.jpg";
//	this->array_in_services.addElementIntoDynamicVectorInRepository(name5, breed5, 5, photo5);
//	std::string name6 = "Bruno";
//	std::string breed6 = "Cocker Spaniel";
//	//std::string photo6 = "https://www.google.com/search?q=cocker+spaniel";
//	std::string photo6 = "https://www.perfect-pooches.com/wp-content/uploads/2022/04/1.jpg";
//	this->array_in_services.addElementIntoDynamicVectorInRepository(name6, breed6, 1, photo6);
//	std::string name7 = "Nala";
//	std::string breed7 = "Border Collie";
//	//std::string photo7 = "https://www.google.com/search?q=border+collie";
//	std::string photo7 = "https://cf.ltkcdn.net/dogs/dog-breeds/images/orig/323839-1600x1066-border-collie-breed.jpg";
//	this->array_in_services.addElementIntoDynamicVectorInRepository(name7, breed7, 4, photo7);
//	std::string name8 = "Coco";
//	std::string breed8 = "Alaskan Malamute";
//	//std::string photo8 = "https://www.google.com/search?q=alaskan+malamute";
//	std::string photo8 = "https://2.bp.blogspot.com/-pbdNn_1TOyI/V14_6BIJXGI/AAAAAAAAF2o/ZLRrUxl6G4AgMnWsattnhTdaoHGPwq9FwCKgB/s1600/Alaskan_Malamute_Lead_Shot.jpg";
//	this->array_in_services.addElementIntoDynamicVectorInRepository(name8, breed8, 3, photo8);
//	std::string name9 = "Mia";
//	std::string breed9 = "Samoyed";
//	//std::string photo9 = "https://www.google.com/search?q=samoyed";
//	std::string photo9 = "https://rasedecaini.ro/wp-content/uploads/2020/07/rasa-caini-samoyed.jpg";
//	this->array_in_services.addElementIntoDynamicVectorInRepository(name9, breed9, 3, photo9);
//	std::string name10 = "Leo";
//	std::string breed10 = "Chow Chow";
//	//std::string photo10 = "https://www.google.com/search?q=chow+chow";
//	std::string photo10 = "https://i.pinimg.com/originals/ff/6e/a2/ff6ea261410a85c4fec775eb00b4e9b0.jpg";
//	this->array_in_services.addElementIntoDynamicVectorInRepository(name10, breed10, 1, photo10);
//	std::string name11 = "Theo";
//	std::string breed11 = "Beagle";
//	std::string photo11 = "https://www.zooplus.ro/ghid/wp-content/uploads/2021/07/despre-beagle-1024x682.jpg";
//	this->array_in_services.addElementIntoDynamicVectorInRepository(name11, breed11, 4, photo11);
//}

void Services::addElementIntoDynamicVectorInServices(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph)
{
	try
	{
		Entities dog(given_name, given_breed, given_age, given_photograph);
		DogsValidator::validate(dog);
		this->array_in_services.addElementIntoDynamicVectorInRepository(given_name, given_breed, given_age, given_photograph);
		//this->array_in_services.writeInformationAboutDogsIntoFile();
	}
	catch (ValidatorClassEntities& exception_for_dog)
	{
		throw exception_for_dog;
	}
	catch (DuplicateDogException& exception)
	{
		throw exception;
	}
}

void Services::deleteElementFromDynamicVectorInServices(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph)
{
	try
	{
		Entities dog(given_name, given_breed, given_age, given_photograph);
		DogsValidator::validate(dog);
		this->array_in_services.deleteElementFromDynamicVectorInRepository(given_name, given_breed, given_age, given_photograph);
		//this->array_in_services.writeInformationAboutDogsIntoFile();
	}
	catch (ValidatorClassEntities& exception_for_dog)
	{
		throw exception_for_dog;
	}
	catch (DogNotFoundException& exception)
	{
		throw exception;
	}
}

void Services::updateElementFromDynamicVectorInServices(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph, std::string& new_given_name, std::string& new_given_breed, int new_given_age, std::string& new_given_photograph)
{
	try
	{
		Entities dog(given_name, given_breed, given_age, given_photograph);
		DogsValidator::validate(dog);
		this->array_in_services.updateElementFromDynamicVectorInRepository(given_name, given_breed, given_age, given_photograph, new_given_name, new_given_breed, new_given_age, new_given_photograph);
		//this->array_in_services.writeInformationAboutDogsIntoFile();
	}
	catch (ValidatorClassEntities& exception_for_dog)
	{
		throw exception_for_dog;
	}
	catch (DogNotFoundException& exception)
	{
		throw exception;
	}
}

std::vector<Entities> Services::returnTheArrayOfElements()
{
	return this->array_in_services.returnTheArrayInRepository();
}

void Services::addDogIntoAdoptionList(std::string& given_name, std::string& given_breed, int given_age, std::string& given_photograph)
{
	this->adoption_list.addElementIntoDynamicVectorInRepository(given_name, given_breed, given_age, given_photograph);
}

std::vector<Entities> Services::returnTheAdoptionList()
{
	return this->adoption_list.returnTheArrayInRepository();
}

void Services::set_type_of_file_to_display(FileAdoptionList* given_type_of_file)
{
	this->type_of_file_to_display = given_type_of_file;
}

void Services::writeInformationAboutDogsIntoFile()
{
	try
	{
		this->array_in_services.writeInformationAboutDogsIntoFile();
	}
	catch (FileException& file_exception)
	{
		throw file_exception;
	}
}

void Services::write_into_csv_or_html_file()
{
	this->type_of_file_to_display->writeInformationAboutDogsIntoFile(this->adoption_list);
}

void Services::display_csv_or_html_file()
{
	this->type_of_file_to_display->displayAdoptionList();
}
