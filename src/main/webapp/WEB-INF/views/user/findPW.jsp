<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp" %>

<link rel="stylesheet" href="/resources/css/upload.css" type="text/css">
<link rel="stylesheet" href="/resources/css/form.css" type="text/css">


<!-- Page Heading -->
<h1 class="h3 mb-2 text-gray-800">User</h1>
<p class="mb-4">  </p>

<!-- DataTales Example -->
<div class="card shadow mb-4 shadowForm">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">비밀번호 찾기</h6>
    </div>

    <div class="card-body">


            <%--<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">--%>
            <p>해당 이메일에 임시 비밀번호가 발송됩니다.</p>

            <div class="form-group row">

                <label for="useremail" class="col-form-label col-sm-1 col-md-1">이메일 :</label>

                <div class="input-group col-sm-4 col-md-4">

                    <input type="text" name="useremail" id="useremail" class="form-control" >

                    <div class="input-group-append">
                        <button class="btn btn-primary " id="checkemail" type="button">발송</button>
                    </div>

                </div>

            </div>

            <hr>

            <div class="form-group row">
                <div class="col-sm-1"></div>

                <a href="/customLogin"><button class="btn btn-primary">로그인 페이지</button></a> &nbsp;
                <a href="/board/list"><button class="btn btn-warning">목록</button></a>
            </div>



    </div> <!-- card-body -->

</div>



</div>
<!-- /.container-fluid -->




<%@ include file="../includes/footer.jsp" %>



<script>


    $("#checkemail").click(function(){


        var regex = /^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i;

        //console.log(regex.test($("#userEmail").val()));

        if (regex.test($("#useremail").val()) == false) {

            alert("이메일을 확인하세요.");
            return;

        }


        if(confirm($("#useremail").val() + "로 인증 메일을 보내시겠습니까?")) {

            var useremail = $("#useremail").val();


            $.ajax({
                url: '/checkEmail',
                type: 'post',
                data: useremail,
                contentType: 'text/html; charset=utf-8',
                success: function(result){

                    console.log("result: " + result);

                    if(result === "X"){
                        alert("이메일이 존재하지 않습니다.");
                    } else {
                        sendEmail();
                    }
                }
            }); // ajax

        } // if


    }); // click


    function sendEmail(){

        var useremail = $("#useremail").val();
        console.log("useremail: " + useremail);

            $.ajax({
                url : "/requestPW",
                type :"post",
                data :  useremail,
                dataType : "text",
                contentType : "text/html; charset=utf-8",
                success : function(result){
                    console.log("requestPW result: " + result);
                } // success

            }); // ajax

            alert("임시 비밀번호를 발송하였습니다.");


    }





</script>
