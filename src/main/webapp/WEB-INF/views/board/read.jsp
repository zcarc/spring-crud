<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp" %>

<link rel="stylesheet" href="/resources/css/upload.css" type="text/css">



<!-- Page Heading -->
<h1 class="h3 mb-2 text-gray-800">Tables</h1>
<p class="mb-4">  </p>

<!-- DataTales Example -->
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Read Page</h6>
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

        <sec:authentication property="principal" var="pinfo" />
        <sec:authorize access="isAuthenticated()">
            <c:if test="${pinfo.username eq boardVO.writer}">
                <button data-oper='update' class='btn btn-primary'>수정</button>
            </c:if>
        </sec:authorize>


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


<%-- Attach --%>
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <span class="m-0 font-weight-bold text-primary">Files</span>
    </div>
    <div class="card-body">
        <div class="uploadedResult">
            <ul>
            </ul>
        </div>
    </div>
</div>

<div class="bigPictureWrapper">
    <div class="bigPicture">
    </div>
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
                    <label>Reply</label>
                    <input class="form-control" name='reply' value='New Reply!!!'>
                </div>

                <div class="form-group">
                    <label>Replyer</label>
                    <input class="form-control" name='replyer' value='replyer' readonly>
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

    $(document).ready(function(){

        // read.jsp로 이동 시, 즉시 실행 함수로 타입에 맞는 이미지 출력
        (function(){

            $.getJSON("/board/getListAttach", {bno: "${boardVO.bno}"}, function(attachVOList){
                //console.log("attachVOList: " + attachVOList);

                var str = "";

                $(attachVOList).each(function(i, attach){

                    //image type
                    if(attach.fileType){

                        //console.log("image type...");

                        var imageThumbnailPath = encodeURIComponent( attach.uploadFolder+ "/s_" + attach.uuid + "_" + attach.fileName);

                        str += "<li data-path='"+attach.uploadFolder+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"
                            +       "<div>"
                            +           "<span>" + attach.fileName + "</span>"
                            +           "<br>"
                            +           "<img src='/displayImage?fileName="+imageThumbnailPath+"'>"
                            +       "</div>"
                            +  "</li>";

                    } else {

                        //console.log("not image type...");

                        str += "<li data-path='"+attach.uploadFolder+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"
                            +       "<div>"
                            +           "<span>" + attach.fileName + "</span>"
                            +           "<br>"
                            +           "<img src='/resources/img/attach.png'>"
                            +       "</div>"
                            +  "</li>";

                    }


                }); // end each

                //console.log("str: " + str);

                $(".uploadedResult ul").html(str);

            });

        })(); // Immediate function


        // 이미지: 섬네일 출력
        // 파일: 다운로드
        $(".uploadedResult").on("click", "li", function(e){

            //console.log("li clicked...");
            var liObj = $(this);

            var path = encodeURIComponent(liObj.data("path") + "/" + liObj.data("uuid") + "_" + liObj.data("filename"));

            if(liObj.data("type")){
                //console.log("image type...");
                showImage(path);

            } else{
                //console.log("not image type...");
                self.location = "/downloadFile?fileName=" + path;

            }

        }); // uploadedResult


        // 이미지 타입일 경우 섬네일 출력
        function showImage(filePath) {

            //console.log("filePath: " + filePath);

            $(".bigPictureWrapper").css("display", "flex");
            $(".bigPicture").html("<img src='/displayImage?fileName="+filePath+"'>")
                            .animate({width:'100%', height:'100%'}, 100);

        } // showImage


        // 원본 이미지 파일 클릭 시 닫기
        $(".bigPictureWrapper").on("click", function(e){

            $(".bigPicture").animate({width:'0%', height: '0%'}, 0);

            var myTimeout = setTimeout(function(){

                $(".bigPictureWrapper").hide();
                clearTimeout(myTimeout);

            }, 0);


        });


        // ajax 공통으로 호출
        $(document).ajaxSend(function(e,xhr,options) {
            xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
        });

        var bno = "${boardVO.bno}";
        var replyUL = $(".chat");

        showList(1);

        // read.jsp 댓글 페이징 즉시 실행
        function showList(currentPage) {

            replyService.getListWithPagination({
                    bno:bno,
                    currentPage:currentPage || 1
                },
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
            //console.log("pagination");

            var targetPageNum = $(this).attr("href");
            //console.log("targetPageNum: " + targetPageNum);

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

        var replyer = null;
        <sec:authorize access="isAuthenticated()">
            replyer = "<sec:authentication property="principal.username" />";
        </sec:authorize>

        var modal = $("#myModal");


        // 댓글 작성 버튼 클릭 시
        $("#addReplyBtn").on("click",function(e){

            <sec:authorize access="isAnonymous()">
                if(confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")){
                    location = "/customLogin";
                }
                return;
            </sec:authorize>

            modal.find("input").val("");
            modal.find("input[name = 'regDate']").closest("div").hide();
            modal.find("button[id != 'modalCloseBtn']").hide();
            modal.find("button[id = 'modalRegisterBtn']").show();

            modal.find("input[name='replyer']").val(replyer);

            $("#myModal").modal("show");
        });

        // 댓글 작성 클릭 시
        $("#modalRegisterBtn").click(function(e){
            replyService.insert(
                {reply: $("input[name=reply]").val(), replyer: $("input[name=replyer]").val(), bno: "${boardVO.bno}"},
                function(result){
                    //console.log("result: " + result);
                    modal.find("input").val("");
                    modal.modal("hide");

                    // -1은 댓글 작성 시 보내는 값
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

            if(!replyer) {
                alert("로그인이 필요합니다.");
                modal.modal("hide");
                return;
            } else if(modal.find("input[name='replyer']").val() != replyer){
                alert("본인의 댓글만 수정이 가능합니다.");
                modal.modal("hide");
                return;
            }

            var reply = {
                rno: modal.data("rno"),
                reply: $("input[name=reply]").val(),
                replyer: modal.find("input[name='replyer']").val()
            };



            replyService.update(reply,
                function(result){
                    //console.log(result);
                    modal.find("input").val("");
                    modal.modal("hide");
                    showList(pageNum);
                });
        });

        $("#modalRemoveBtn").click(function(){

            if(!replyer) {
                alert("로그인이 필요합니다.");
                modal.modal("hide");
                return;
            } else if(modal.find("input[name='replyer']").val() != replyer){
                alert("본인의 댓글만 삭제가 가능합니다.");
                modal.modal("hide");
                return;
            }

            replyService.remove(modal.data("rno"), modal.find("input[name='replyer']").val(),
                function(result){
                    //console.log(result);
                    modal.find("input").val("");
                    modal.modal("hide");
                    showList(pageNum);
                });
        });

        $("#modalCloseBtn").click(function(){
            modal.find("input").val("");
            modal.modal("hide");
        });


    }); // ready

</script>


<script type="text/javascript">

    $(document).ready(function(){

        var operForm = $("#operForm");
        $("button[data-oper='update']").click(function(e){
            operForm.submit();
        });

        $("button[data-oper='list']").click(function(e){
            operForm.find("#bno").remove();
            operForm.attr("action", "/board/list");
            operForm.submit();
        });

    });

</script>


<%@ include file="../includes/footer.jsp" %>