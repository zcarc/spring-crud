/**
 *
 *      google
 *
 */



function init() {
    console.log("google init...");

    gapi.load('auth2', function() { // ready
        console.log("google auth2...");

        // return: gapi.auth2.GoogleAuth
        window.gauth = gapi.auth2.init({
            client_id: 'your app id'

        });

        // GoogleAuth.then(onInit, onError)
        gauth.then(function(){
            console.log("GoogleAuth success.");
            console.log("GoogleAuth.isSignedIn.get(): " + gauth.isSignedIn.get());

        }, function(){
            console.log("GoogleAuth fail.");

        }); // then()

    });
}

document.getElementById('googleLoginBtn').onclick = function(e) {

    e.preventDefault();

    console.log("googleLoginBtn clicked...");
    checkLoginStatus();

};

function checkLoginStatus(){

    // 로그인 상태 확인
    if(gauth.isSignedIn.get()){
        console.log("You have been logged in.");

        if(confirm("현재 로그인 상태입니다. 로그아웃 하시겠습니까?")){
            signOut();
        }

    } else {
        console.log("You have been logged out.");
        signIn();
    }

}

function signIn() {

    gauth.signIn().then(function(){

        console.log("gauth.signIn().then()");

        var googleUser = gauth.currentUser.get();
        var profile = googleUser.getBasicProfile();
        //alert("profile: " + JSON.stringify(profile));
        //alert("profile.getId(): " + profile.getId());

        transferProfile(profile);

    });

}

function transferProfile(profile){

    $.ajax({
        url: "/getPlatformInfo",
        type: "post",
        data: JSON.stringify({
            platform: "google",
            id: profile.getId()
        }),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        beforeSend: function(xhr){
            xhr.setRequestHeader(getCsrf().csrfHeaderName, getCsrf().csrfToken);
        },
        success: function(result){
            //alert("google ajax result: " + JSON.stringify(result));

            var uid = $("#uid").val(profile.getId()).clone().attr("type", "hidden");
            var upw = $("#upw").val(result.prefix + profile.getId()).clone().attr("type", "hidden");
            $("form[role='form']").find($("#uid")).attr("disabled", "true").val("");
            $("form[role='form']").find($("#upw")).attr("disabled", "true").val("");

            $("form[role='form']").append(uid);
            $("form[role='form']").append(upw);

            $("form[role='form']").submit();

        },
        error: function(){
            alert("에러가 발생했습니다.");
        }

    });

}

function signOut() {

    gauth.signOut().then(function(){

        console.log("gauth.signOut().then()");
        location.reload(true);
    });

}
