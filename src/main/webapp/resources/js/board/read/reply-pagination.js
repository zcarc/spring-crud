


// 댓글 페이징 영역 시작

    var bno = $("#bno").val();
    var replyUL = $(".chat");


    showList(1);

    // read.jsp 댓글 페이징 즉시 실행
    function showList(currentPage) {

        replyService.getListWithPagination({
                bno:bno,
                currentPage:currentPage || 1
            }, // callback: 댓글 count, 댓글 list
            function(replyCountInDTO,replyList) {
                //console.log("replyCountInDTO: " + replyCountInDTO);
                //console.log("replyList: " + replyList);

                // currentPage가 -1일 경우 댓글 작성
                // replyService.getListWithPagination에서 currentPage 0으로 설정
                if(currentPage === -1) {
                    //console.log("currentPage === -1");
                    pageNum = Math.ceil(replyCountInDTO/10.0);
                    showList(pageNum);
                    return;
                }


                var str = "";
                if(replyList == null || replyList.length == 0) {
                    replyUL.html("");
                    return;
                }

                for(var i=0, len = replyList.length || 0; i<len; i++) {
                    str += "<li class='clearfix' data-rno='"+ replyList[i].rno+ "'>";
                    str += "	<div><div class='header'><strong class='primary-font'>"+replyList[i].replyer+ " (" + replyList[i].replyerId + ")" +"</strong>";
                    str += "		<small class='float-right'>"+replyService.displayTime(replyList[i].regDate)+"</small></div>";
                    str += "		<p>"+replyList[i].reply+"</p><hr></div></li>";
                }

                replyUL.html(str);

                //
                showReplyPage(replyCountInDTO);

            }); // callback

    } // showList()


    // 댓글 수 받아서 페이징 처리
    var pageNum = 1;

    function showReplyPage(replyCount){

        var endNum = Math.ceil(pageNum / 10.0) * 10;
        var startNum = endNum - 9;

        var prev = startNum != 1;
        var next = false;

        if(endNum * 10 > replyCount) {
            endNum = Math.ceil(replyCount/10.0);
        }

        if(endNum * 10 < replyCount) {
            next = true;
        }

        var str = "<ul class='pagination float-right'>";

        if(prev){
            str += "<li class='page-item'><a class='page-link' href='"+(startNum -1)+"'>Previous</a></li>";
        }

        for(var i = startNum; i<= endNum; i++){
            var active = pageNum == i ? "active" : "";
            str += "<li class='page-item "+active+" '><a class='page-link' href='"+i+"'>"+i+"</a></li>";
        }

        if(next) {
            str += "<li class='page-item'><a class='page-link' href='"+(endNum + 1)+"'>Next</a></li>";
        }

        str += "</ul></div>";

        $(".card-footer").html(str);

    } // showReplyPage()


    // 댓글 페이지 버튼 클릭 시
    $(".card-footer").on("click", "li a", function(e){

        e.preventDefault();
        //console.log("pagination");

        var targetPageNum = $(this).attr("href");
        //console.log("targetPageNum: " + targetPageNum);

        pageNum = targetPageNum;

        showList(pageNum);

    });

// 댓글 페이징 영역 끝