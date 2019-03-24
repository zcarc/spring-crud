<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<!DOCTYPE html>
<html>
<head>

    <style>
        #kakaoBtn {
            color: #fff;
            background-color: gold;
            border-color: #fff;
        }

        #kakaoBtn:hover {
            color: #fff;
            background-color: #FFC000;
            border-color: #eeeeee;
        }

        #kakaoBtn > span > img {
            height:18px;
            margin-right:3px;
        }


    </style>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Login</title>

    <!-- Custom fonts for this template-->
    <link href="resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
          rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="resources/css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body class="bg-gradient-primary">


<div class="container">


    <!-- Outer Row -->
    <div class="row justify-content-center">

        <div class="col-xl-10 col-lg-12 col-md-9">

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <!-- Nested Row within Card Body -->
                    <div class="row">
                        <div class="col-lg-6 d-none d-lg-block bg-login-image">
                        </div>
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
                                    <span style="color:red;">
                                        <p>${logout}</p>
                                        <p>${error}</p>
                                    </span>
                                </div>


                                <form role="form" method="post" action="/login" class="user">

                                    <%--<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">--%>

                                    <div class="form-group">
                                        <input type="text" class="form-control form-control-user" id="uid" name="username"
                                               placeholder="아이디" autofocus>
                                    </div>

                                    <div class="form-group">
                                        <input type="password" class="form-control form-control-user" id="upw" name="password"
                                               placeholder="패스워드" value="">
                                    </div>

                                    <div class="form-group">
                                        <div class="custom-control custom-checkbox small">
                                            <input type="checkbox" class="custom-control-input" id="customCheck"
                                                   name="remember-me">
                                            <label class="custom-control-label" for="customCheck">Remember Me</label>
                                        </div>
                                    </div>

                                    <a href="#" id="loginBtn" class="btn btn-primary btn-user btn-block">
                                        Login
                                    </a>

                                    <hr>
                                    <a href="#" id="googleLoginBtn" class="btn btn-google btn-user btn-block">
                                        <i class="fab fa-google fa-fw"></i> Login with Google
                                    </a>

                                    <a href="#" id="fbLogin" class="btn btn-facebook btn-user btn-block">
                                        <i class="fab fa-facebook-f fa-fw"></i> Login with Facebook
                                    </a>

                                    <a href="#" id="kakaoBtn" class="btn btn-facebook btn-user btn-block">
                                        <span><img src="/resources/css/kakaoIcon_small.png" /></span><i class="fab"></i> Login with Kakao
                                    </a>

                                </form>

                                <hr>

                                <div class="text-center">
                                    <a class="small" href="/board/list">홈으로</a>
                                </div>

                                <div class="text-center">
                                    <a class="small" href="/findPW">비밀번호 찾기</a>
                                </div>

                                <div class="text-center">
                                    <a class="small" href="/register">회원가입</a>
                                </div>


                                <%--<div>--%>
                                    <%--<button id="status">Kakao login status check</button>--%>
                                    <%--<button id="logout">Kakao logout</button>--%>
                                <%--</div>--%>

                                <%--<div>--%>
                                    <%--<button id="fbLogout">Facebook logout</button>--%>
                                <%--</div>--%>


                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>

</div>

<!-- Bootstrap core JavaScript-->
<script src="resources/vendor/jquery/jquery.min.js"></script>
<script src="resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="resources/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="resources/js/sb-admin-2.min.js"></script>

<script type="text/javascript">

    <%--function getCsrf(){--%>
        <%--var csrfHeaderName = "${_csrf.headerName}";--%>
        <%--var csrfToken = "${_csrf.token}";--%>

        <%--return {--%>
            <%--csrfHeaderName: csrfHeaderName,--%>
            <%--csrfToken: csrfToken--%>
        <%--}--%>
    <%--}--%>

    function getParamLogout() {

        var paramLogout = "${param.logout}";
        return paramLogout;

    }
</script>

<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="/resources/js/platform/kakao.js"></script>

<script src="/resources/js/platform/facebook.js"></script>

<script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
<script src="/resources/js/platform/google.js"></script>



<script type="text/javascript">
    $("#loginBtn").click(function (e) {

        e.preventDefault();

        if ( !$("#uid").val() | !$("#upw").val() ) {
            alert("아이디 또는 패스워드를 입력하지 않았습니다.");
            return;
        } else if($("#uid").val().length < 5) {
            alert("아이디는 5자 이상이어야 합니다.");
            return;
        } else if($("#upw").val().length < 6) {
            alert("비밀번호는 6자 이상이어야 합니다.");
            return;
        }

        $("form").submit();
    });
</script>


<c:if test="${param.logout != null }">
    <script src="/resources/js/platform/auto_logout.js"></script>
</c:if>

</body>
</html>