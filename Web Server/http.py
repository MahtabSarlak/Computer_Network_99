import socket
#################################################################
PORT = 8090
HOST = 'localhost'

#################################################################

# Create a TCP server socket
#(AF_INET is used for IPv4 protocols)
#(SOCK_STREAM is used for TCP)
listener = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

listener.bind((HOST,PORT))
# Listen to at most 1 connection at a time
listener.listen(1)

print ("Server is running on host: {}".format(HOST))
print ("Server is running on port: {}".format(PORT))

while True:
    print ('Server is listening...')
    # Set up a new connection from the client
    connection, addr = listener.accept()
    # If an exception occurs during the execution of try clause
    # the rest of the clause is skipped
    # If the exception type matches the word after except
    # the except clause is executed
    try:
        # Receives the request message from the client
        request = connection.recv(1024)
        # Extract the path of the requested object from the message
        # The path is the second part of HTTP header, identified by [1]
        print("HTTP Request:\n %s \n" % request)
        req = request.split()
        if len(req) > 0:
            path = req[1]
        # Because the extracted path of the HTTP request includes
        # a character '\', we read the path from the second character
        path = path[1:]
        print ("Url Path: %s" % path)
        # Store the entire content of the requested file in a temporary buffer
        f = open(path, "r")
        c = f.read();
        # Send the HTTP response header line to the connection socket
        connection.send('HTTP/1.0 200 OK\r\n')
        property("Send HTTP 200 OK")
        connection.send("Content-type: text/html; charset=utf-8\r\n\r\n")
        # Send the content of the requested file to the connection socket
        connection.send(c)

    except IOError:
        # Send HTTP response message for file not found
        connection.send('HTTP/1.0 404 Not Found\r\n')
        print("Send HTTP 404 Not Found")
        connection.send("Content-type: text/html\r\n\r\n")
        connection.send("<h1>404 Not Found</h1><h3/>File not found: %s </h3>" % path)
    finally:
        # Close the client connection socket
        connection.close()
        print("Connection close\n")

# Close the Server connection socket
listener.close()