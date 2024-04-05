#include "ValidatorClassEntities.h"

#define _CRT_SECURE_NO_WARNINGS
#define CURL_STATICLIB

#include <stdio.h>
#include </Users/DARIA/source/repos/curl/curl_2/x64 Debug/include/curl/curl.h>
#include </Users/DARIA/source/repos/curl/curl_2/x64 Release/include/curl/curl.h>

#include <windows.h>

#include <opencv2/core.hpp>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/highgui.hpp>
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/opencv.hpp"
using namespace cv;


ValidatorClassEntities::ValidatorClassEntities(std::string _message) : message{ _message }
{
}

const char* ValidatorClassEntities::what() const noexcept
{
    return message.c_str();
}

void DogsValidator::validate(Entities& dog)
{
    std::string errors_displayed;
    if (dog.getAge() <= 0)
        errors_displayed += std::string("The dog cannot have a negative age or an age equal to zero\n");

	std::string url_of_photo = dog.getPhotograph();
	CURL* curl_of_photograph;
	CURLcode result;

	curl_of_photograph = curl_easy_init();
	if (curl_of_photograph) {
		curl_easy_setopt(curl_of_photograph, CURLOPT_URL, url_of_photo.c_str()); //curl_easy_setopt(curl, CURLOPT_URL, current_url_of_photo);
		curl_easy_setopt(curl_of_photograph, CURLOPT_NOBODY, 1);
		result = curl_easy_perform(curl_of_photograph);

		/* always cleanup */
		curl_easy_cleanup(curl_of_photograph);

		if (result != CURLE_OK)
		{
			errors_displayed += std::string("The photo url is not a link\n");
		}
	}

	if (errors_displayed.size() > 0)
		throw ValidatorClassEntities(errors_displayed);
}
