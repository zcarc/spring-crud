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
        <h6 class="m-0 font-weight-bold text-primary">Board List Page</h6>
    </div>

    <div class="card-body">


        <form action="/board/update" method="post">


            <input type="hidden" name="currentPage" value="${criteria.currentPage}">
            <input type="hidden" name="displayRecords" value="${criteria.displayRecords}">
            <input type="hidden" name="type" value="${criteria.type}">
            <input type="hidden" name="keyword" value="${criteria.keyword}">


            <div class="form-group">
                <small class="font-weight-bold text-danger">Number</small>
                <p class="font-weight-light"><c:out value="${boardVO.bno }" /></p>
                <hr>
                <input type="hidden" class="form-control" name="bno" value='<c:out value="${boardVO.bno }" />' readonly >
            </div>

            <div class="form-group">
                <small class="font-weight-bold text-danger">Title</small>
                <input class="form-control" name="title" id="name" value='<c:out value="${boardVO.title }" />' >
            </div>

            <div class="form-group">
                <textarea class="form-control" name="content" id="ckeditor"><c:out value="${boardVO.content }" /></textarea>
                <hr>
            </div>

            <div class="form-group">
                <small class="font-weight-bold text-danger">Writer</small>
                <p class="font-weight-light" style="font-size: 1.3rem"><c:out value="${boardVO.writer }" /> (${boardVO.writerId})</p>
                <hr>
                <input type="hidden" class="form-control" name="writer" value='<c:out value="${boardVO.writer }" />' readonly>
            </div>

            <input type="hidden" name="writerId" value="${boardVO.writerId}">

            <sec:authentication property="principal" var="pinfo" />
            <sec:authorize access="isAuthenticated()">
                <c:if test="${pinfo.username eq boardVO.writerId }">
                    <button data-oper='save' class='btn btn-primary'>저장</button>
                    <button data-oper='delete' class="btn btn-danger">삭제</button>
                </c:if>
            </sec:authorize>
            <button data-oper='list' class='btn btn-warning'>목록</button>

        </form>


    </div> <!-- card-body -->

</div>



</div>
<!-- /.container-fluid -->

<%-- CK Editor --%>
<script src="/resources/ckeditor/ckeditor.js" type="text/javascript"></script>
<script src="/resources/js/board/common/ck-editor.js" type="text/javascript"></script>


<script type="text/javascript">

    $(document).ready(function(){


        $("button").click(function(){
            var dataOper = $(this).data("oper");
            //console.log("var dataOper = $(this).data(\"oper\")" + dataOper);

            if(dataOper == 'delete'){

                $("form").attr("action", "/board/delete");
                $("form").submit();

            } else if(dataOper == 'list') {
                //location = "/board/list";
                var currentPage = $("input[name='currentPage']").clone();
                var displayRecords = $("input[name='displayRecords']").clone();
                var type = $("input[name='type']").clone();
                var keyword = $("input[name='keyword']").clone();
                $("form").empty();
                $("form").append(currentPage);
                $("form").append(displayRecords);
                $("form").append(type);
                $("form").append(keyword);
                $("form").attr("action", "/board/list").attr("method", "get");

                $("form").submit();


            } else if(dataOper === 'save'){

                //console.log("save clicked.");

                if($("#name").val().length <= 0) {
                    alert("제목을 입력하세요.");
                    return;
                }

                $("form").submit();

            }

        });

    });


</script>


<%@ include file="../includes/footer.jsp" %>