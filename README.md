# Multithreaded-WebServer
This project demonstrates a basic implementation of a multithreaded web server using Java Socket Programming. The web server is designed to handle client requests by retrieving files from a document root folder.

## Instructions to run the server
To run the web server, follow the instructions below:
1. Open the command line and navigate to the project directory.
2. Run the following command to build the project:
    > mvn clean install
3. After the project is built, navigate to the target\WebServer directory where you will find the executable jar Multithreaded-WebServer.jar.
4. Execute the following command to start the web server:
    >java -jar Multithreaded-WebServer.jar -document_root "path\of\document\root" -port 8088

### Note: 
Replace "path/of/document/root" with the actual path of the document root folder in your system. The -port argument can be replaced with any available port number on your system.

I built this project as an assignment for my Distributed Systems course. I hope you find this project useful and I am open to any feedback or suggestions that you may have.