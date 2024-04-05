#pragma once
#include "FileAdoptionList.h"
#include "Repository.h"

class csvAdoptionList :public FileAdoptionList
{
public:
    csvAdoptionList() {};
    void writeInformationAboutDogsIntoFile(Repository& given_adoption_list) override;
    void displayAdoptionList() override;
};
