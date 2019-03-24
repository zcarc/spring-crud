<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp" %>

<link rel="stylesheet" href="/resources/css/upload.css" type="text/css">
<link rel="stylesheet" href="/resources/css/form.css" type="text/css">


<!-- Page Heading -->
<h1 class="h3 mb-2 text-gray-800">Profile</h1>
<p class="mb-4">  </p>

<!-- DataTales Example -->
<div class="card shadow mb-4 shadowForm">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">회원 정보 수정</h6>
    </div>

    <div class="card-body">

        <form role="form" action="/user/modify" method="post">

            <%--<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">--%>

            <div class="form-group row">

                <label for="userid" class="col-form-label col-sm-1 col-md-1">아이디 :</label>
                <div class="col-sm-4 col-md-4">
                    <input type="text" name="userid" id="userid" class="form-control" value="<sec:authentication property="principal.username"/>" disabled>
                </div>

            </div>

            <div class="form-group row">

                <label for="usernickname" class="col-form-label col-sm-1 col-md-1">닉네임 :</label>

                <div class="input-group col-sm-4 col-md-4">

                    <input type="text" name="usernickname" id="usernickname" class="form-control" value="${usernickname}">

                    <div class="input-group-append">
                        <button class="btn btn-primary btn-sm" id="checknickname" type="button">중복확인</button>
                    </div>

                </div>

            </div>


            <div class="form-group row">

                <label for="userpw" class="col-sm-1 col-md-1">비밀번호 :</label>

                <c:if test="${social eq 'true'}">
                    <div class="col-sm-4 col-md-4">
                        <input type="password" id="userpw" class="form-control" disabled>
                        <span> *소셜 로그인 사용자</span>
                    </div>
                </c:if>

                <c:if test="${social eq null}">
                    <div class="col-sm-4 col-md-4">
                        <input type="password" name="userpw" id="userpw" class="form-control">
                    </div>
                </c:if>

            </div>


                <hr>
            <div class="form-group row">
                <div class="col-sm-1"></div>

                <button type="submit" class="btn btn-primary">수정완료</button> &nbsp;
                <button data-type="list" class="btn btn-warning">목록</button>
            </div>

        </form>


    </div> <!-- card-body -->

</div>



</div>
<!-- /.container-fluid -->




<%@ include file="../includes/footer.jsp" %>



<script>

    var nicknameVerify = false;
    var usernickname = "${usernickname}";
    var social = "${social}";

    $("button").click(function(e){

        e.preventDefault();
        //console.log("submit clicked.");


        if($(this).data("type") == 'list'){
            $("form").empty();
            $("form").attr("action", "/board/list").attr("method", "get").submit();


        } else if($(this).attr("type") == "submit"){


            // usernickname : 현재 로그인 한 닉네임
            // $("#usernickname").val(): 입력한 닉네임
            // 아이디를 수정하고 submit
            if(usernickname != $("#usernickname").val()){

                console.log("usernickname != $(\"#usernickname\").val()");

                // 닉네임 체크가 되지 않았을 경우
                if(nicknameVerify === false){
                    console.log("nicknameVerify: " + nicknameVerify);
                    alert("닉네임을 확인해주세요.");

                  // 닉네임 체크가 됐을 경우
                } else if(nicknameVerify === true) {

                    console.log("nicknameVerify: " + nicknameVerify);

                    // 닉네임을 수정하고 비밀번호가 공란인 경우 (닉네임만 수정)
                    if(!$("#userpw").val()){
                        $("#userpw").val(null);
                        alert("회원 정보가 수정되었습니다.");
                        $("form").attr("action", "/profileSave").attr("method", "post").submit();
                        return;

                      //  닉네임 수정 후 비밀번호를 입력하지 않았거나 6자 이하인 경우
                    } else if(!$("#userpw").val() || $("#userpw").val().length < 6) {

                        alert("비밀번호는 6자리 이상입니다.");
                        return;
                    }


                }

              // 닉네임을 수정하지 않고 submit 한 경우
            } else if(usernickname === $("#usernickname").val()){

                // 비밀번호 체크
                if(!$("#userpw").val() || $("#userpw").val().length < 6){
                    alert("비밀번호는 6자리 이상입니다.");
                    return;
                }

                alert("회원 정보가 수정되었습니다.");
                $("form").attr("action", "/profileSave").attr("method", "post").submit();
            }


        } // else if submit


    });

    $("#checknickname").click(function(){

        var checkNickname = $("#usernickname").val();


        if(usernickname == checkNickname){
            alert("자신의 닉네임입니다.");
            return;
        }


        $.ajax({
            url: '/checkNickname',
           type: 'post',
           data: checkNickname,
           contentType: 'text/html; charset=utf-8',
           success: function(result){
                console.log("result: " + result);
                if(result === "O"){
                    alert("이미 존재하는 닉네임입니다.");
                    nicknameVerify = false;
                } else {
                    alert("사용할 수 있는 닉네임입니다.");
                    nicknameVerify = true;
                }
           }
        });

    });



</script>
