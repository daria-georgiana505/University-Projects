#include "RepositoryExceptions.h"

FileException::FileException(const std::string& exception_message) : message(exception_message)
{
}

const char* FileException::what()
{
	return message.c_str();
}

RepositoryException::RepositoryException() : exception{}, message{ "" }
{
}

RepositoryException::RepositoryException(const std::string& exception_message) : message{ exception_message }
{
}

const char* RepositoryException::what()
{
	return this->message.c_str();
}

const char* DuplicateDogException::what()
{
	return "There is another dog with the same name, breed, age and photo url";
}

const char* DogNotFoundException::what()
{
	return "The information about the dog was not found";
}
