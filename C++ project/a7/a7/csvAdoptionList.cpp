#include "csvAdoptionList.h"
#include <fstream>
#include <exception>
#include <Windows.h>
#include <shellapi.h>

void csvAdoptionList::writeInformationAboutDogsIntoFile(Repository& given_adoption_list)
{
	std::ofstream fout("adoption_list.csv");
	if (!fout.is_open())
		throw std::runtime_error("File is not opened!");
	for (auto& dog : given_adoption_list.returnTheArrayInRepository())
	{
		fout << dog;
	}
	fout.close();
}

void csvAdoptionList::displayAdoptionList()
{
	//ShellExecuteA(NULL, "open", "C:\\Program Files\\Microsoft Office\\Office16\\EXCEL.EXE", filename.c_str(), NULL, SW_MAXIMIZE);

	//ShellExecuteA(NULL, "open", "C:\\Windows\\system32\\notepad.exe", NULL, NULL, SW_SHOWNORMAL);

	/*(void)system("C:\\Windows\\system32\\notepad.exe");*/

	(void)system("C:\\Users\\DARIA\\source\\repos\\a7-913-Fenesan-Daria-1\\a7\\a7\\adoption_list.csv");
}
