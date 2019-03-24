


//  댓글 모달 영역 시작



// 댓글 서브밋 클릭 시
$("#modalRegisterBtn").click(function(e){
    replyService.insert(
        {reply: $("input[name=reply]").val(), replyer: $("input[name=replyer]").val(), replyerId: $("input[name=replyerId]").val(), bno: $("#bno").val()},
        function(result){
            //console.log("result: " + result);
            modal.find("input").val("");
            modal.modal("hide");

            // -1은 댓글 작성 시 보내는 값
            showList(-1);
        });
});

// 댓글 영역 클릭 시
// 댓글 수정 모달 출력

// .chat = replyUL
$(".chat").on("click", "li", function(){
    var rno = $(this).data("rno");

    replyService.read(rno, function(result){

        $("input[name=reply]").val(result.reply);
        $("input[name=replyer]").val(result.replyer);
        $("input[name=replyerId]").val(result.replyerId);
        $("input[name=regDate]").val(replyService.displayTime(result.regDate));
        $("#myModal").data("rno", result.rno);

        modal.find("button[id != 'modalCloseBtn']").hide();
        modal.find("button[id = 'modalModBtn']").show();
        modal.find("button[id = 'modalRemoveBtn']").show();

        $("#myModal").modal("show");
    });
});

// 댓글 수정완료 버튼 클릭 시
$("#modalModBtn").click(function(){

    if(!replyerId) {
        alert("로그인이 필요합니다.");
        modal.modal("hide");
        return;

    } else if(modal.find("input[name='replyerId']").val() != replyerId){
        alert("본인의 댓글만 수정이 가능합니다.");
        modal.modal("hide");
        return;
    }

    var reply = {
        rno: modal.data("rno"),
        reply: $("input[name=reply]").val(),
        replyer: modal.find("input[name='replyer']").val(),
        replyerId: modal.find("input[name='replyerId']").val()
    };

    replyService.update(reply,
        function(result){
            //console.log(result);
            modal.find("input").val("");
            modal.modal("hide");
            showList(pageNum);
        });
});

// 댓글 삭제 버튼 클릭 시
$("#modalRemoveBtn").click(function(){

    if(!replyerId) {
        alert("로그인이 필요합니다.");
        modal.modal("hide");
        return;

    } else if(modal.find("input[name='replyerId']").val() != replyerId){
        alert("본인의 댓글만 삭제가 가능합니다.");
        modal.modal("hide");
        return;
    }

    replyService.remove(modal.data("rno"), modal.find("input[name='replyer']").val(), modal.find("input[name='replyerId']").val(),
        function(result){
            //console.log(result);
            modal.find("input").val("");
            modal.modal("hide");
            showList(pageNum);
        });
});

// 댓글 창 닫기 클릭 시
$("#modalCloseBtn").click(function(){
    modal.find("input").val("");
    modal.modal("hide");
});

// 댓글 모달 영역 끝