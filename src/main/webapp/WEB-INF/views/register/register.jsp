<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<!DOCTYPE html>
<html>
<head>

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

    <style>
        .danger {
            color: red;
        }
    </style>

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
                        <div class="col-lg-6 d-none d-lg-block bg-register-image"></div>
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">회원가입</h1>
                                    <span style="color:red;">
                                    </span>
                                </div>

                                <form:form modelAttribute="userVO" id="frm"
                                           onsubmit="return checkSpaces()">


                                    <div class="form-group row">
                                        <form:input path="username" class="form-control" id="username" placeholder="이름"/>
                                        <form:errors path="username" class="danger"/>
                                    </div>


                                    <div class="form-group row">
                                        <div class="input-group">
                                            <form:input path="userid" id="userid" class="form-control" placeholder="아이디" />
                                            <div class="input-group-append">
                                                <button class="btn btn-primary btn-sm" id="checkid" type="button">중복확인</button>
                                            </div>
                                        </div>

                                        <form:errors path="userid" class="danger"/>
                                    </div>



                                    <div class="form-group row">
                                        <form:password path="userpw" id="userpw" class="form-control" autocomplete="off" placeholder="비밀번호" showPassword="false" />
                                        <form:errors path="userpw" class="danger"/>
                                    </div>


                                    <div class="form-group row">
                                        <div class="input-group">
                                        <form:input path="userEmail" id="userEmail" class="form-control" autocomplete="true" placeholder="이메일"/>
                                        <div class="input-group-append">
                                            <button class="btn btn-primary btn-sm" id="verityBtn" type="button">이메일 인증</button>
                                        </div>
                                    </div>

                                        <form:errors path="userEmail" class="danger"/>
                                    </div>


                                    <div class="form-group row">

                                        <form:input path="userPhone" id="userPhone" class="form-control" placeholder="휴대폰 번호"/>
                                        <form:errors path="userPhone" class="danger"/>

                                    </div>


                                    <div class="form-group row">
                                        <form:label path="userGender" class="control-label col-sm-2" for="userGender1">성별:</form:label>

                                        <div class="col-sm-10">
                                            <div id="ugenderTD" class="text-left">

                                                <label class="radio-inline">
                                                    <form:radiobutton id="userGender1" path="userGender" name="userGender" value="m"/>
                                                    남</label>

                                                <label class="radio-inline">
                                                    <form:radiobutton id="userGender12" path="userGender" name="userGender" value="w"/>
                                                    여</label>
                                            </div>
                                        </div>

                                        <div class="text-left">
                                            <form:errors path="userGender" class="danger"/>
                                        </div>

                                    </div>


                                    <div class="text-center">
                                        <div class="form-group">
                                            <input type="submit" id="submit" value="회원가입" class="btn btn-primary btn-user btn-block" >
                                        </div>
                                    </div>


                                </form:form>

                                <hr>

                                <div class="text-center">
                                    <a class="small" href="<%=request.getContextPath()%>/customLogin">로그인 페이지</a>
                                </div>



                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>

</div>

<!-- Bootstrap core JavaScript-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="resources/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="resources/js/sb-admin-2.min.js"></script>




</body>

<script type="text/javascript">


    //DOM 로드 후 자동 실행
    // if($("#userpw").val()){
    //     $("#userpw").attr('type', 'password').val('');
    // }

    $("input[id^='u']").each(function(e,i){

        $(this).attr("maxlength", "30");
    });


    /**************************************************************************************/
    //이메일 인증 버튼
    $("#verityBtn").click(function(){

        <%--var csrfHeaderName = "${_csrf.headerName}"; // X-CSRF-TOKEN--%>
        <%--var csrfTokenValue = "${_csrf.token}";--%>

        <%--console.log("csrfHeaderName: " + csrfHeaderName);--%>
        <%--console.log("csrfTokenValue: " + csrfTokenValue);--%>

        var regex = /^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i;

        //console.log(regex.test($("#userEmail").val()));

        if (regex.test($("#userEmail").val()) == false) {

            alert("이메일을 확인하세요.");

        } else {
            if(confirm($("#userEmail").val() + "로 인증 메일을 보내시겠습니까?")) {

                $.ajax({
                    url : "/requestEmailAuth",
                    type :"post",
                    // beforeSend: function(xhr) {
                    //     xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
                    // },
                    contentType : "application/json; charset=utf-8",
                    data :  JSON.stringify({
                        userEmail : $("#userEmail").val()
                    }),
                    dataType : "text",
                    success : function(result){
                        //console.log("result: " + result);
                        if(result == "success") {
                            //alert("인증 메일을 발송하였습니다.");
                        } else {
                            //alert("인증 메일 발송에 실패했습니다.");
                        }
                    } // success

                }); // ajax

                alert("인증 메일이 발송되었습니다.")

            } // if

        }


    });//이메일 인증 버튼
    /**************************************************************************************/




    /********************************************************************************************************/
    //onsubmit() 호출 함수
    function checkSpaces(){

        var regex = /\s/;
        var spacesRemoved = "";


        $("input[id^='u']").each(function(i,e){

            if( $(this).val().search(/\s/) != -1 | $(this).val() != ""  ) {

                spacesRemoved = $(this).val().replace(regex, "");
                $(this).val(spacesRemoved);

                return false;
            }//if

        });//each


        if( $("#username").val().search(/^[^가-힣]+$/) != -1 ) {
            //alert("이름은 한글로 입력해주세요.");
            var KorRemoved = $("#username").val().replace(/^[^가-힣]+$/, "");
            $("#username").val(KorRemoved);

            return false;


        }//if

        if(idVerify === false){
            alert("아이디를 확인하세요.");
            return false;
        }



    }//checkSpaces
    /********************************************************************************************************/

    $("#checkid").click(function(){

        var userid = $("#userid").val();


        if( userid.length < 6 ) {
            alert("아이디는 6자 이상입니다.");
            return;
        }

        $.ajax({
            url: "/checkId",
            type: "post",
            data: userid,
            dataType: 'text',
            contentType: 'text/html; charset=utf-8',
            success: function(result){
                window.idVerify = false;

                if(result === 'O'){
                    alert("이미 존재하는 아이디입니다.");

                } else {
                    alert("사용할 수 있는 아이디입니다.");
                    idVerify = true;
                }
            }

        });


    });


</script>

</html>