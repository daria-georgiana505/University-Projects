#pragma once
#include "Repository.h"

class FileAdoptionList
{
public:
	FileAdoptionList() {};
	virtual void writeInformationAboutDogsIntoFile(Repository& given_adoption_list) = 0;
	virtual void displayAdoptionList() = 0;

	virtual ~FileAdoptionList() {};
};