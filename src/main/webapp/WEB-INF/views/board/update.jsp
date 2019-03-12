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


        <form action="/board/update" method="post">

            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">

            <input type="hidden" name="currentPage" value="${criteria.currentPage}">
            <input type="hidden" name="displayRecords" value="${criteria.displayRecords}">

            <div class="form-group">
                <label>Number</label>
                <input class="form-control" name="bno" value='<c:out value="${boardVO.bno }" />' readonly >
            </div>

            <div class="form-group">
                <label>Title</label>
                <input class="form-control" name="title" value='<c:out value="${boardVO.title }" />' >
            </div>

            <div class="form-group">
                <label>Content</label>
                <textarea class="form-control" rows="3" name="content" ><c:out value="${boardVO.content }" /></textarea>
            </div>

            <div class="form-group">
                <label>Writer</label>
                <input class="form-control" name="writer" value='<c:out value="${boardVO.writer }" />' readonly>
            </div>

            <button data-oper='save' class='btn btn-primary'>저장</button>
            <button data-oper='delete' class="btn btn-danger">삭제</button>
            <button data-oper='list' class='btn btn-warning'>목록</button>

        </form>


    </div> <!-- card-body -->

</div>

</div>
<!-- /.container-fluid -->

<script type="text/javascript">

    $(document).ready(function(){


        $("button").click(function(){
            var dataOper = $(this).data("oper");
            console.log("var dataOper = $(this).data(\"oper\")" + dataOper);

            if(dataOper == 'delete'){
                $("form").attr("action", "/board/delete");
            } else if(dataOper == 'list') {
                //location = "/board/list";
                var currentPage = $("input[name='currentPage']").clone();
                var displayRecords = $("input[name='displayRecords']").clone();
                $("form").empty();
                $("form").append(currentPage);
                $("form").append(displayRecords);
                $("form").attr("action", "/board/list").attr("method", "get");

                $("form").submit();

                return;
            }

            $("form").submit();
        });

    });


</script>


<%@ include file="../includes/footer.jsp" %>