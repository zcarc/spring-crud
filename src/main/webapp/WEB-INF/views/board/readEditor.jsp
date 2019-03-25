<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp" %>

<link rel="stylesheet" href="/resources/css/upload.css" type="text/css">
<link rel="stylesheet" href="/resources/css/form.css" type="text/css">



<!-- Page Heading -->
<h1 class="h3 mb-2 text-gray-800">Tables</h1>
<p class="mb-4">  </p>

<!-- DataTales Example -->
<div class="card shadow mb-4 shadowForm">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Read Page</h6>
    </div>

    <div class="card-body">



        <div class="form-group">
            <small class="font-weight-bold text-danger">Number</small>
            <p class="font-weight-light"><c:out value="${boardVO.bno }" /></p>
            <hr>
            <input type="hidden" class="form-control" name="bno" value='<c:out value="${boardVO.bno }" />' readonly >
        </div>


        <div class="form-group">
            <small class="font-weight-bold text-danger">Title</small>
            <p class="font-weight-light" style="font-size:30px; font-weight: lighter; margin-top: 10px;" style=""><c:out value="${boardVO.title }" /></p>
            <hr>
            <input type="hidden" class="form-control" name="title" value='<c:out value="${boardVO.title }" />' readonly>
        </div>


        <div class="form-group">
            <%--<textarea class="form-control" rows="3" name="content" id="ckeditor" readonly><c:out value="${boardVO.content }" /></textarea>--%>
            <div id="temp"></div>
            <input type="hidden" name="content" id="content001" value='<c:out value="${boardVO.content }" />'>
            <hr>
        </div>




        <div class="form-group">
            <small class="font-weight-bold text-danger">Writer</small>
            <p class="font-weight-light" style="font-size: 1.3rem"><c:out value="${boardVO.writer }" /> (${boardVO.writerId})</p>
            <hr>
            <input type="hidden" class="form-control" name="writer" value='<c:out value="${boardVO.writer }" />' readonly>
        </div>


        <sec:authentication property="principal" var="pinfo"  />
        <sec:authorize access="isAuthenticated()">
            <input type="hidden" name="writerId" value="${pinfo}">
            <c:if test="${pinfo.username eq boardVO.writerId}">
                <button data-oper='update' class='btn btn-primary'>수정</button>
            </c:if>

        </sec:authorize>


        <button data-oper='list' class='btn btn-warning'>목록</button>


        <form id="operForm" action="/board/updateEditor" method="get">
            <input type="hidden" id="bno" name="bno" value="${boardVO.bno}">
            <input type="hidden" name="currentPage" value="${criteria.currentPage}">
            <input type="hidden" name="displayRecords" value="${criteria.displayRecords}">
            <input type="hidden" name="type" value="${criteria.type}">
            <input type="hidden" name="keyword" value="${criteria.keyword}">

        </form>



    </div> <!-- card-body -->

</div>



<%-- Reply section --%>
<div class="card shadow mb-4 shadowForm">
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
                    <input class="form-control" name='replyer' value='' readonly>
                </div>

                <sec:authorize access="isAuthenticated()">
                    <input type="hidden" name='replyerId' value='' readonly>
                </sec:authorize>



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

<%-- CK Editor --%>
<%--<script src="/resources/ckeditor/ckeditor.js" type="text/javascript"></script>--%>
<%--<script src="/resources/js/board/common/ck-editor.js" type="text/javascript"></script>--%>

<%-- 댓글 CURD --%>
<script src="/resources/js/reply.js" type="text/javascript"></script>

<%--댓글 페이징--%>
<script src="/resources/js/board/read/reply-pagination.js" type="text/javascript"></script>

<%--댓글 모달 --%>
<script type="text/javascript">

    window.replyerId = null;

    <sec:authorize access="isAuthenticated()">
    replyerId = "<sec:authentication property="principal.username" />";
    </sec:authorize>

    var modal = $("#myModal");


    // 댓글 작성하기 버튼 클릭 시
    $("#addReplyBtn").on("click",function(e){

        var usernickname = "${usernickname}";

        <sec:authorize access="isAnonymous()">
        if(confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")){
            location = "/customLogin";
            return;

        } else {
            return;
        }
        </sec:authorize>

        modal.find("input").val("");
        modal.find("input[name = 'regDate']").closest("div").hide();
        modal.find("button[id != 'modalCloseBtn']").hide();
        modal.find("button[id = 'modalRegisterBtn']").show();

        modal.find("input[name='replyer']").val(usernickname);
        modal.find("input[name='replyerId']").val(replyerId);


        $("#myModal").modal("show");
    });

</script>
<script src="/resources/js/board/read/reply-modal.js"></script>


<%-- 입력 폼 클릭 이벤트 --%>
<script type="text/javascript">

    $(document).ready(function(){

        console.log("$(\"#content001\").val(): "+ $("#content001").val());

        // 기존 이미지 불러와서 div에 저장
        $("#temp").html($("#content001").val());


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