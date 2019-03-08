<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>/sample/admin page</h1>

<p>principal : <sec:authentication property="principal" /> </p>
<p>MemberVO : <sec:authentication property="principal.vo"/> </p>
<p>사용자이름 : <sec:authentication property="principal.vo.username"/> </p>
<p>사용자아이디 : <sec:authentication property="principal.username"/> </p>
<p>사용자 권한 리스트 : <sec:authentication property="principal.vo.authList"/> </p>

<a href="/customLogout">Logout</a>

</body>
</html>
