<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp" %>



<!-- Page Heading -->
<h1 class="h3 mb-2 text-gray-800">Tables</h1>
<p class="mb-4">  </p>

<!-- DataTales Example -->
<div class="card shadow mb-4">
    <div class="card-header py-3 clearfix">
        <h6 class="m-0 font-weight-bold text-primary" style="display:inline;">Board List Page</h6>

        <button id="regBtn" class="btn btn-info btn-xs" style="float:right;">글쓰기</button>

    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                <tr>
                    <th>#번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>수정일</th>
                </tr>
                </thead>
                <tfoot>
                <tr>
                    <th>#번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>수정일</th>
                </tr>
                </tfoot>
                <tbody>
                <c:forEach items="${list }" var="board">
                    <tr>
                        <td><c:out value="${board.bno}" /></td>
                        <td><c:out value="${board.title}" /></td>
                        <td><c:out value="${board.writer}" /></td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regDate}"/></td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>



            <!-- Success Modal-->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="myModalLabel">Modal Title</h5>
                            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">처리가 완료되었습니다.</div>
                        <div class="modal-footer">
                            <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                            <button class="btn btn-primary" type="button">Save Changes</button>
                        </div>
                    </div>
                </div>
            </div>



        </div>
    </div>
</div>

</div>
<!-- /.container-fluid -->

<script type="text/javascript">

    $(document).ready(function(){

        var result = '<c:out value="${result}" />';

        checkModal(result);


        // post result 모달
        function checkModal(result){

            if(result === '') {
                return;
            }

            console.log("parseInt(result):" + parseInt(result));

            if(parseInt(result) > 0) {
                $(".modal-body").html("게시글 " + parseInt(result) + " 번이 등록 되었습니다." );
            }

            $("#myModal").modal("show");


        } //checkModal()


        $("#regBtn").click(function(){
            location = "/board/insert";
        });

    });

</script>


<%@ include file="../includes/footer.jsp" %>

