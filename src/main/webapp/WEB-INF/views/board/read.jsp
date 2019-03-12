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

</div>
<!-- /.container-fluid -->

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