<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>

</body>

<script>



    if("${result}" == 'O') {

        alert('회원가입 되었습니다.');
        location='/customLogin';

    } else {
        alert('회원 가입에 실패 했습니다.');
        location="/register";
    }


</script>

</html>