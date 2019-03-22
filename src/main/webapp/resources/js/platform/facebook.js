
/**
 *
 *      facebook
 *
 */


window.fbAsyncInit = function() {
    FB.init({
        appId : 'your app id',
        cookie : true,
        xfbml : true,
        version : 'v3.2'
    });

    console.log("FB.init...");

};

(function(d, s, id){
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement(s); js.id = id;
    js.src = "https://connect.facebook.net/ko_KR/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);

    console.log("(function(d, s, id)..");

}(document, 'script', 'facebook-jssdk'));


$("#fbLogin").click(function(e){

    e.preventDefault();

    FB.getLoginStatus(function(response) {

        console.log('statusChangeCallback');
        if (response.status === 'connected') {
            console.log("connected status...");

            if(confirm("현재 로그인 상태입니다. 로그아웃 하시겠습니까?")){
                FB.logout(function(response) {
                    location.reload(true);
                });

            } // if

            // 로그인 상태가 아닌 경우
        } else {

            FB.login(function(response) {
                if (response.authResponse) {
                    FB.api('/me', function(response) {
                        //console.log('JSON.stringify(response), ' + JSON.stringify(response) + '.');
                        //console.log("response.id: " + response.id);

                        $.ajax({
                            url: "/getPlatformInfo",
                            type: "post",
                            data: JSON.stringify({
                                platform: "facebook",
                                id: response.id
                            }),
                            dataType: 'JSON',
                            contentType: "application/json; charset=utf-8",
                            beforeSend: function(xhr){
                                xhr.setRequestHeader(getCsrf().csrfHeaderName, getCsrf().csrfToken);
                            },
                            success: function(result){

                                //alert("result: " + JSON.stringify(result));
                                //alert("result.fb_generate + response.id: " + result.prefix + response.id);

                                var uid = $("#uid").val(response.id).clone().attr("type", "hidden");
                                var upw = $("#upw").val(result.prefix + response.id).clone().attr("type", "hidden");
                                $("form[role='form']").find($("#uid")).attr("disabled", "true").val("");
                                $("form[role='form']").find($("#upw")).attr("disabled", "true").val("");

                                $("form[role='form']").append(uid);
                                $("form[role='form']").append(upw);

                                $("form[role='form']").submit();

                            },
                            error: function(xhr,status,error){
                                alert("에러가 발생했습니다.");
                            }

                        }); // ajax

                    }); // FB.api
                } else {
                    console.log('사용자가 로그인을 취소했거나 앱을 완전히 승인하지 않았습니다.');
                }
            }, {scope: 'public_profile,email'}); // FB.login

        }// else

    });// FB.getLoginStatus


});// $("#fbLogin").click


$("#fbLogout").click(function(){
    fbLogout();
});


function fbLogout(){

    var result = "";

    if(confirm("현재 로그인 상태입니다. 로그아웃 하시겠습니까?")){
        FB.logout(function(response) {
            //alert("fblogout response: " + JSON.stringify(response));
        });
        result = "1";
    }

    return result;


}



function checkStatusAndLogin(){

    var result = "";

    FB.getLoginStatus(function(response) {

        console.log('statusChangeCallback');

        if (response.status === 'connected') {

            console.log("connected status...");

            result = fbLogout();


        } else if (response.status === 'not_authorized') {

            console.log('Please log ' + 'into this app.');
            //document.getElementById('status').innerHTML = 'Please log ' + 'into this app.';

        } else {

            console.log('Please log ' + 'into Facebook.');

            //document.getElementById('status').innerHTML = 'Please log ' + 'into Facebook.';
        }

    });

    return result;

}
