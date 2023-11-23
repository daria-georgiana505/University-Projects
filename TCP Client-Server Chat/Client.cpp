#include <iostream>
#include <string>
#include <winsock2.h>
#include <ws2tcpip.h>

#pragma comment(lib, "ws2_32.lib")

void ReceiveThread(void* lpParam) { //reads what the server sends to the client
    SOCKET clientSocket = *(SOCKET*)lpParam;
    char buffer[101];
    int bytesRead;
    while (true) {
        bytesRead = recv(clientSocket, buffer, sizeof(buffer) - 1, 0);
        if (bytesRead == SOCKET_ERROR || bytesRead == 0) {
            if (bytesRead == 0) {
                if (strcmp(buffer, "KICKED") == 0) { //The client was kicked
                    std::cerr << "You have been kicked from the chat." << std::endl;
                }
                else { //The client has quit the chat
                    std::cerr << "You have quit the conversation (Socket closed)." << std::endl;
                }
            }
            else {
                std::cerr << "Socket error." << std::endl;
            }
            closesocket(clientSocket);

            return; // Exit the thread
        }

        buffer[bytesRead] = '\0';

        // Check if the received message is a kick notification
        if (strcmp(buffer, "KICKED") != 0) {
            // If it's not a kick notification, it's a regular chat message
            std::cout << buffer << std::flush;
        }
    }
}


int main() {
    //used to prepare the application for using sockets api (for windows)
    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        std::cerr << "WSAStartup failed" << std::endl;
        return 1;
    }

    //declare a socket
    SOCKET clientSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (clientSocket == INVALID_SOCKET) {
        std::cerr << "Socket creation error" << std::endl;
        return 1;
    }

    //connects to the server
    sockaddr_in serverAddress = { 0 };
    serverAddress.sin_family = AF_INET;
    serverAddress.sin_port = htons(1111);
    inet_pton(AF_INET, "192.168.1.38", &(serverAddress.sin_addr));

    if (connect(clientSocket, (struct sockaddr*)&serverAddress, sizeof(serverAddress)) == SOCKET_ERROR) {
        std::cerr << "Connection error." << std::endl;
        closesocket(clientSocket);
        WSACleanup();
        return 1;
    }

    std::cout << "Username: ";

    // Start a thread for receiving data from the server
    CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)ReceiveThread, &clientSocket, 0, NULL);

    int bytesSent;
    while (true) { //send data to server
        std::string userInput;
        std::getline(std::cin, userInput);
        if (userInput.empty()) {
            continue;
        }

        userInput += '\n';

        //if an error occurs
        bytesSent = send(clientSocket, userInput.c_str(), userInput.length(), 0);
        if (bytesSent == SOCKET_ERROR) {
            std::cerr << "Send error." << std::endl;
            closesocket(clientSocket);
            break;
        }
        bytesSent = 0;
    }

    WSACleanup();

    return 0;
}