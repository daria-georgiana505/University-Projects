#include <iostream>
#include "UI.h"
#include "Repository.h"
#include "Services.h"
#include <crtdbg.h>
#include "Tests.h"
#include <string>
//#include "csvAdoptionList.h"
//#include "htmlAdoptionList.h"

using namespace std;

int main()
{
	//testAllFunctions();
	
	Repository repository("Dogs.txt");
	Services services(repository);
	UI array_with_elements(services);
	array_with_elements.run_app();

	_CrtDumpMemoryLeaks();
	return 0;
}