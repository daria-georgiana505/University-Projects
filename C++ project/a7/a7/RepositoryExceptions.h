#pragma once
#include <exception>
#include <string>

class FileException : public std::exception
{
protected:
	std::string message;

public:
	FileException(const std::string& exception_message);
	virtual const char* what();
};

class RepositoryException : public std::exception
{
protected:
	std::string message;

public:
	RepositoryException();
	RepositoryException(const std::string& exception_message);
	virtual ~RepositoryException() {}
	virtual const char* what();
};

class DuplicateDogException : public RepositoryException
{
	const char* what();
};

class DogNotFoundException : public RepositoryException
{
	const char* what();
};