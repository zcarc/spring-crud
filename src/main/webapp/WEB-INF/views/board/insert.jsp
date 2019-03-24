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
        <h6 class="m-0 font-weight-bold text-primary">Board List Page</h6>
    </div>

    <div class="card-body">

        <form role="form" action="/board/insert" method="post">

            <%--<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">--%>

            <div class="form-group">
                <label>Title</label>
                <input class="form-control" name="title" id="name">
            </div>

            <div class="form-group">
                <label>Text Area</label>
                <textarea class="form-control" rows="3" name="content"></textarea>
            </div>

            <div class="form-group">
                <label>Writer</label>
                <input class="form-control" name="writer" value="<sec:authentication property="principal.username" />" readonly>
            </div>

            <button type="submit" class="btn btn-primary">Submit Button</button>
            <button type="reset" class="btn btn-danger">Reset Button</button>
            <button data-type="list" class="btn btn-warning">List</button>
        </form>


    </div> <!-- card-body -->

</div>


<%-- Attach --%>
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <span class="m-0 font-weight-bold text-primary">Files</span>
    </div>
    <div class="card-body">
        <input type="file" name="uploadFile" multiple>
        <div class="uploadedResult">
            <ul>
            </ul>
        </div>
    </div>
</div>


</div>
<!-- /.container-fluid -->




<%@ include file="../includes/footer.jsp" %>

<script src="/resources/js/board/insert/upload.js"></script>