
/**
 *
 *      kakao
 *
 */



//<![CDATA[
Kakao.init('your app id');

$("#kakaoBtn").click(function(e){

    e.preventDefault();

    Kakao.Auth.getStatus(function(statusObj){

        //console.log("statusObj: " + JSON.stringify(statusObj));

        if(statusObj.status == "connected") {
            //console.log("statusObj.status: " + statusObj.status);

            if(confirm("현재 로그인 상태입니다. 로그아웃 하시겠습니까?")){
                // 로그인 상태라면 기존 계정 로그아웃 후 새로 로그인
                Kakao.Auth.logout(function(){
                    //console.log("logout...");
                    location.reload(true);
                });
            }

        } // if

        if(statusObj.status == "not_connected"){
            //console.log("statusObj.status: " + statusObj.status);

            Kakao.Auth.loginForm({
                success : function(authObj){
                    //console.log("authObj: " + JSON.stringify(authObj));

                    Kakao.API.request({
                        url: '/v2/user/me',
                        success: function(res) {
                            //console.log("url: '/v2/user/me': " + JSON.stringify(res));

                            if(res.id){
                                //console.log("res.properties.nickname: " + JSON.stringify(res.properties.nickname));

                                $.ajax({
                                    url: "/getPlatformInfo",
                                    type: "post",
                                    data: JSON.stringify({
                                        platform: "kakao",
                                        id:res.id,
                                    }),
                                    dataType: 'JSON',
                                    contentType: "application/json; charset=utf-8",
                                    success: function(result){
                                        //alert("prefix result: " + result.prefix);

                                        var uid = $("#uid").val(res.id).clone().attr("type", "hidden");
                                        var upw = $("#upw").val(result.prefix + res.id).clone().attr("type", "hidden");
                                        $("form[role='form']").find($("#uid")).attr("disabled", "true").val("");
                                        $("form[role='form']").find($("#upw")).attr("disabled", "true").val("");

                                        $("form[role='form']").append(uid);
                                        $("form[role='form']").append(upw);


                                        //alert("$(\"#upw\").val(): " + $("#upw").val());
                                        $("form[role='form']").submit();

                                    },
                                    error: function(xhr,status,error){
                                        //console.log("error: " + error);
                                        alert("에러가 발생했습니다.");
                                    }
                                });


                            } else {
                                //console.log("res.for_partner: " + JSON.stringify(res.for_partner));
                                alert("사용자 정보가 존재하지 않습니다.");
                            }

                        },
                        fail: function(error) {
                            //console.log("Kakao.API.request failed: " + JSON.stringify(error));
                        }
                    }); // kakao.API.request
                }
            });

        } // if


    }); // Kakao.Auth.getStatus


}); // $("#newLoginForm").click



$("#logout").click(function(){

    Kakao.Auth.logout(function(){
        //console.log("logout");
        location.reload(true);
    });

});


$("#status").click(function(){

    Kakao.Auth.getStatus(function(statusObj){
        //console.log("statusObj: " + JSON.stringify(statusObj));
    });

});
