# Computer_Network
This repository is for fall 2020 Computer Network course where i put my assignments assigned throughout the semester.

## Assignment Descriptions

## Web-Server
We were asked to develop a simple Web server in Python that is
capable of processing only one request. It should have following features.

- create a connection socket when contacted by a client (browser)
- receive the HTTP request from this connection
- parse the request to determine the specific file being requested
- get the requested file from the server’s file system
- create an HTTP response message consisting of the requested file preceded
by header lines
- send the response over the TCP connection to the requesting browser.
   - If a browser requests a file that is not present in our server, our
server should return a “404 Not Found” error message.

## SMTP Mail Client
We were asked to develop a simple mail client that sends email to any recipient. Our client will
need to connect to a mail server, dialogue with the mail server using the SMTP
protocol, and send an email message to the mail server. 
