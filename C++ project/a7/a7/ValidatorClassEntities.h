#pragma once
#include <string>
#include <exception>
#include "Entities.h"

class ValidatorClassEntities : public std::exception
{
private:
	std::string message;

public:
	ValidatorClassEntities(std::string _message);
	const char* what() const noexcept override;
};

class DogsValidator
{
public:
	static void validate(Entities& dog);
};

