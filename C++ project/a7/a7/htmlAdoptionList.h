#pragma once
#include "FileAdoptionList.h"
#include "Repository.h"

class htmlAdoptionList :public FileAdoptionList
{
public:
    htmlAdoptionList() {};
    void writeInformationAboutDogsIntoFile(Repository& given_adoption_list) override;
    void displayAdoptionList() override;
};


