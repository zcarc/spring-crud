


//console.log("로그아웃 되었습니다.");


// 카카오 로그아웃
Kakao.Auth.getStatus(function(statusObj) {
    //console.log("Kakao status: " + JSON.stringify(statusObj));

    if (statusObj.status == "connected") {
        //console.log("statusObj.status == \"connected\": " + statusObj.status);

        Kakao.Auth.logout(function () {
            //console.log("Kakao logout...");
        });
    } // if

}); // Kakao


// 페이스북 로그아웃
var fSetTimeout = setTimeout(function(){

    FB.getLoginStatus(function(response) {

        if (response.status === 'connected') {

            FB.logout(function(response) {
                //console.log("FB logout...");
            });
        }
    });

    clearTimeout(fSetTimeout);
}, 400);


// 구글 로그아웃
var gSetTimeout = setTimeout(function(){

    if(gauth.isSignedIn.get()){

        gauth.signOut().then(function(){
            //console.log("구글 자동 로그아웃");
        });
    }

    clearTimeout(gSetTimeout);
}, 700);