#pragma once
#include <string>
#include <exception>

class ValidatorClassUI : public std::exception
{
private:
	std::string message;

public:
	ValidatorClassUI(std::string _message);
	const char* what() const noexcept override;
};

class StringContainsAlphabeticLetters
{
public:
	static void validate(std::string& given_string);
};

class StringIsNumber
{
public:
	static void validate(std::string& given_string);
};