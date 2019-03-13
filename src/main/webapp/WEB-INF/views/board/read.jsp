<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp" %>



<!-- Page Heading -->
<h1 class="h3 mb-2 text-gray-800">Tables</h1>
<p class="mb-4">  </p>

<!-- DataTales Example -->
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Board List Page</h6>
    </div>

    <div class="card-body">



        <div class="form-group">
            <label>Number</label>
            <input class="form-control" name="bno" value='<c:out value="${boardVO.bno }" />' readonly >
        </div>

        <div class="form-group">
            <label>Title</label>
            <input class="form-control" name="title" value='<c:out value="${boardVO.title }" />' readonly>
        </div>

        <div class="form-group">
            <label>Content</label>
            <textarea class="form-control" rows="3" name="content" readonly><c:out value="${boardVO.content }" /></textarea>
        </div>

        <div class="form-group">
            <label>Writer</label>
            <input class="form-control" name="writer" value='<c:out value="${boardVO.writer }" />' readonly>
        </div>


        <button data-oper='update' class='btn btn-primary'>수정</button>
        <button data-oper='list' class='btn btn-warning'>목록</button>


        <form id="operForm" action="/board/update" method="get">
            <input type="hidden" id="bno" name="bno" value="${boardVO.bno}">
            <input type="hidden" name="currentPage" value="${criteria.currentPage}">
            <input type="hidden" name="displayRecords" value="${criteria.displayRecords}">
            <input type="hidden" name="type" value="${criteria.type}">
            <input type="hidden" name="keyword" value="${criteria.keyword}">

        </form>



    </div> <!-- card-body -->

</div>


<%-- Reply section --%>
<div class="card shadow mb-4">
    <div class="card-header py-3 clearfix">
        <i class="fa fa-comments fa-fw" ></i>
        <span class="m-0 font-weight-bold text-primary">Reply List</span>

        <button id='addReplyBtn' class="btn btn-primary btn-xs float-right">댓글 작성</button>
    </div>

    <div class="card-body">

        <ul class="list-unstyled chat">
            <!-- start reply -->
        </ul>
        <!-- ./ end ul -->
    </div>

</div>

<%-- Reply pagination --%>
<div class="card-footer clearfix">
</div>


</div>
<!-- /.container-fluid -->

<%-- Reply modal --%>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="myModalLabel">Reply Modal</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">


                <div class="form-group">
                    <label>Replyer</label>
                    <input class="form-control" name='replyer' value='replyer' readonly>
                </div>

                <div class="form-group">
                    <label>Reply</label>
                    <input class="form-control" name='reply' value='New Reply!!!'>
                </div>

                <div class="form-group">
                    <label>Reply Date</label>
                    <input class="form-control" name='regDate' value='' readonly>
                </div>


            </div>

            <div class="modal-footer">
                <button id="modalModBtn" class="btn btn-warning" type="button">Modify</button>
                <button id="modalRemoveBtn" class="btn btn-danger" type="button">Remove</button>
                <button id="modalRegisterBtn" class="btn btn-primary" type="button">Register</button>
                <button id="modalCloseBtn" class="btn btn-secondary" type="button">Close</button>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="/resources/js/reply.js"></script>

<script type="text/javascript">
    // reply.js

    var csrfHeaderName = "${_csrf.headerName}";
    var csrfTokenValue = "${_csrf.token}";

    $(document).ajaxSend(function(e,xhr,options) {
        xhr.setRequestHeader(csrfHeaderName, csrfTokenValue)
    });

    var bno = "${boardVO.bno}";
    var replyUL = $(".chat");

    showList(1);

    function showList(currentPage) {

        replyService.getListWithPagination({
                bno:bno,
                currentPage:currentPage || 1
            },
            function(replyCountInDTO,replyList) {
                console.log("replyCountInDTO: " + replyCountInDTO);
                console.log("replyList: " + replyList);

                if(currentPage === -1) {
                    console.log("currentPage === -1");
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
                    str += "	<div><div class='header'><strong class='primary-font'>"+replyList[i].replyer+"</strong>";
                    str += "		<small class='float-right'>"+replyService.displayTime(replyList[i].regDate)+"</small></div>";
                    str += "		<p>"+replyList[i].reply+"</p><hr></div></li>";
                }

                replyUL.html(str);

                showReplyPage(replyCountInDTO);

            }); // callback

    } // showList()


    // Reply pagination
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


    // Click pagination
    $(".card-footer").on("click", "li a", function(e){

        e.preventDefault();
        console.log("pagination");

        var targetPageNum = $(this).attr("href");
        console.log("targetPageNum: " + targetPageNum);

        pageNum = targetPageNum;

        showList(pageNum);

    });



    // replyService.insert(
    //     {reply:"댓글 테스트" + bno, replyer:"작성자" + bno, bno:bno},
    //     function(result){
    //         alert("result:" + result);
    // });
    //
    //
    // replyService.getListWithPagination(
    //     {bno:bno, currentPage:1},
    //     function(result){
    //         for(var i = 0; i < result.length; i++){
    //             console.log(result[i]);
    //         }
    // });
    //
    // replyService.remove(9, function(result){
    //     console.log(result);
    // }, function(error){
    //     console.log(error);
    // });
    //
    // replyService.update(
    //     {rno:5, bno:bno, reply:"댓글5수정"},
    //     function(result){
    //         console.log(result);
    //     });
    //
    // replyService.read(8, function(result){
    //     console.log(result);
    // });


<%--Reply Modal--%>

    var modal = $("#myModal");

    $("#addReplyBtn").on("click",function(e){

        modal.find("input").val("");
        modal.find("input[name = 'regDate']").closest("div").hide();
        modal.find("button[id != 'modalCloseBtn']").hide();
        modal.find("button[id = 'modalRegisterBtn']").show();

        $("#myModal").modal("show");
    });

    $("#modalRegisterBtn").click(function(e){
        replyService.insert(
            {reply: $("input[name=reply]").val(), replyer: $("input[name=replyer]").val(), bno: "${boardVO.bno}"},
            function(result){
               console.log("result: " + result);
               modal.find("input").val("");
               modal.modal("hide");

               showList(-1);
            });
    });

    // .chat = replyUL
    $(".chat").on("click", "li", function(){
        var rno = $(this).data("rno");

        replyService.read(rno, function(result){

            $("input[name=reply]").val(result.reply);
            $("input[name=replyer]").val(result.replyer);
            $("input[name=regDate]").val(replyService.displayTime(result.regDate));
            $("#myModal").data("rno", result.rno);

            modal.find("button[id != 'modalCloseBtn']").hide();
            modal.find("button[id = 'modalModBtn']").show();
            modal.find("button[id = 'modalRemoveBtn']").show();

            $("#myModal").modal("show");
        });
    });

    $("#modalModBtn").click(function(){
        replyService.update({rno: modal.data("rno"), reply: $("input[name=reply]").val()},
            function(result){
                console.log(result);
                modal.find("input").val("");
                modal.modal("hide");
                showList(pageNum);
            });
    });

    $("#modalRemoveBtn").click(function(){
        replyService.remove(modal.data("rno"),
            function(result){
                console.log(result);
                modal.find("input").val("");
                modal.modal("hide");
                showList(pageNum);
            });
    });

    $("#modalCloseBtn").click(function(){
        modal.find("input").val("");
        modal.modal("hide");
    });
</script>


<script type="text/javascript">

    var operForm = $("#operForm");
    $("button[data-oper='update']").click(function(e){
        operForm.submit();
    });

    $("button[data-oper='list']").click(function(e){
       operForm.find("#bno").remove();
       operForm.attr("action", "/board/list");
       operForm.submit();
    });

</script>


<%@ include file="../includes/footer.jsp" %>