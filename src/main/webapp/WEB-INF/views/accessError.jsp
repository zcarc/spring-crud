<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Access Denied Page</title>
</head>
<body>

<h2>${SPRING_SECURITY_403_EXCEPTION.getMessage()}</h2>
<h2>${msg}</h2>


</body>
</html>
