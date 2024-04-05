#include "htmlAdoptionList.h"
#include <fstream>

void htmlAdoptionList::writeInformationAboutDogsIntoFile(Repository& given_adoption_list)
{
	std::ofstream fout("adoption_list.html");
	if (!fout.is_open())
		throw std::runtime_error("File is not opened!");
	fout << "<!DOCTYPE html>\n";
	fout << "<html>\n";
	fout << "<head>\n";
	fout << "	<title>Adoption list</title>\n";
	fout << "</head>\n";
	fout << "<body>\n";
	fout << "<table border=\"1\">\n";
	fout << "	<tr>\n";
	fout << "		<td>Name</td>\n";
	fout << "		<td>Breed</td>\n";
	fout << "		<td>Age</td>\n";
	fout << "		<td>Link of photograph</td>\n";
	fout << "\t</tr>\n";
	for (auto& dog : given_adoption_list.returnTheArrayInRepository())
	{
		fout << "\t<tr>\n";
		fout << "\t\t<td>" << dog.getName() << "</td>\n";
		fout << "\t\t<td>" << dog.getBreed() << "</td>\n";
		fout << "\t\t<td>" << dog.getAge() << "</td>\n";
		fout << "\t\t<td><a href=\"" << dog.getPhotograph() << "\">Link</a></td>\n";
		fout << "\t</tr>\n";
	}
	fout << "</table>\n</body>\n</html>\n";
	fout.close();
}

void htmlAdoptionList::displayAdoptionList()
{
	(void)system("C:\\Users\\DARIA\\source\\repos\\a7-913-Fenesan-Daria-1\\a7\\a7\\adoption_list.html");
}
