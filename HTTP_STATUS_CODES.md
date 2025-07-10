## HTTP Status Codes

### 1. Informational
- **100 Continue**: The client should continue with the request.
- **101 Switching Protocols**: The server accepts the client's request to change protocols.

### 2. Success
- **200 OK**: The request was successful, and the server is returning the requested resource.
- **201 Created**: The request was successful, and the resource was created (usually in response to a POST).
- **202 Accepted**: The request was accepted but has not yet been processed.
- **204 No Content**: The request was successful, but there is no content to return.

### 3. Redirect
- **300 Multiple Choices**: Multiple choices for the resource; the client must choose one.
- **301 Moved Permanently**: The requested resource has been moved to a new URL (permanently).
- **302 Found**: The requested resource is temporarily available at another URL.
- **303 See Other**: The client should contact another URL to get the response.
- **304 Not Modified**: The resource has not changed since the last request (used for caching).

### 4. Client Errors
- **400 Bad Request**: The server cannot process the request due to invalid syntax.
- **401 Unauthorized**: Authentication is required to access the resource.
- **403 Forbidden**: The server understands the request but refuses to fulfill it (no access rights).
- **404 Not Found**: The requested resource was not found on the server.
- **405 Method Not Allowed**: The method specified in the request is not supported for this resource.
- **408 Request Timeout**: The request timed out.

### 5. Server Errors
- **500 Internal Server Error**: General server error; something went wrong.
- **501 Not Implemented**: The server does not support the functionality required to fulfill the request.
- **502 Bad Gateway**: The server acting as a gateway or proxy received an invalid response from the upstream server.
- **503 Service Unavailable**: The server is temporarily unavailable, usually due to overload or maintenance.
- **504 Gateway Timeout**: The server acting as a gateway or proxy did not receive a timely response from the upstream server.
