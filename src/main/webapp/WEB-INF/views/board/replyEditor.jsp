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
        <h6 class="m-0 font-weight-bold text-primary">Reply Page</h6>
    </div>

    <div class="card-body">

        <form role="form" action="/board/insert" method="post">

            <input class="form-control" type="hidden" name="bgroup" value="${boardVO.bgroup}">
            <input class="form-control" type="hidden" name="bstep" value="${boardVO.bstep}">
            <input class="form-control" type="hidden" name="bindent" value="${boardVO.bindent}">


            <div class="form-group">
                <label>Title</label>
                <input class="form-control" name="title" id="name" value="[RE] ">
            </div>

            <div class="form-group">
                <textarea class="form-control" name="content" id="ckeditor"></textarea>
            </div>


            <div class="form-group">
                <input type="hidden" class="form-control" name="writer" value="${usernickname}" readonly>
            </div>


            <input type="hidden" name="writerId" value="<sec:authentication property="principal.username" />">


            <button type="submit" class="btn btn-primary">작성완료</button>
            <button type="reset" class="btn btn-danger">초기화</button>
            <button data-type="list" class="btn btn-warning">목록</button>
        </form>


    </div> <!-- card-body -->

</div>



</div>
<!-- /.container-fluid -->




<%@ include file="../includes/footer.jsp" %>

<%-- CK Editor --%>
<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>
<script src="/resources/js/board/common/ck-editor.js"></script>


<script>

    $("button").click(function(e){

        e.preventDefault();
        //console.log("submit clicked.");


        if($(this).data("type") == 'list'){
            $("form").empty();
            $("form").attr("action", "/board/list").attr("method", "get").submit();


        } else if($(this).attr("type") == "submit"){

            if($("#name").val().length <= 0) {
                alert("제목을 입력하세요.");
                return;
            }

            $("form[role='form']").attr("action", "/board/replyEditor").attr("method", "post");
            $("form[role='form']").submit();


        } else if($(this).attr("type") == "reset"){
            $("form").trigger('reset');
        }


    });


</script>
