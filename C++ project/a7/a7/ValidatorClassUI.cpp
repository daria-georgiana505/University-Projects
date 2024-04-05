#include "ValidatorClassUI.h"

ValidatorClassUI::ValidatorClassUI(std::string _message) : message{ _message }
{
}

const char* ValidatorClassUI::what() const noexcept
{
    return message.c_str();
}

void StringContainsAlphabeticLetters::validate(std::string& given_string)
{
	std::string errors_displayed;
	for (int i = 0; i < given_string.length(); i++)
	{
		if (!isalpha(given_string[i]) && given_string[i] != ' ') //if (!isalpha(given_string[i]))
		{
			errors_displayed += "The given string does not contain only alphabetical letters";
			break;
		}
	}
	if (errors_displayed.size() > 0)
		throw ValidatorClassUI(errors_displayed);
}

void StringIsNumber::validate(std::string& given_string)
{
	std::string errors_displayed;
	for (int i = 0; i < given_string.length(); i++)
	{
		if (i == 0 && given_string[0] == '-')
		{
		}
		else
		{
			if (!isdigit(given_string[i]))
			{
				errors_displayed += "The given string is not a number";
				break;
			}
		}
	}
	if (errors_displayed.size() > 0)
		throw ValidatorClassUI(errors_displayed);
}
