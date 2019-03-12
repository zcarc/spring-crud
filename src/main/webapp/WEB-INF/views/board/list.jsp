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
        <div class="table-responsive clearfix">
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
                        <td><a href="${board.bno}" class="move"><c:out value="${board.title}" /></a></td>
                        <td><c:out value="${board.writer}" /></td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regDate}"/></td>
                        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>



            <form id='searchForm' action='/board/list' method='get'>

                <select name='type'>
                    <option value='' <c:out value="${pageDTO.criteria.type == null ? 'selected' : ''}" /> >--</option>
                    <option value='T' <c:out value="${pageDTO.criteria.type eq 'T' ? 'selected' : '' }" /> >제목
                    <option value='C' <c:out value="${pageDTO.criteria.type eq 'C' ? 'selected' : '' }" /> >내용</option>
                    <option value='W' <c:out value="${pageDTO.criteria.type eq 'W' ? 'selected' : '' }" /> >작성자</option>
                    <option value='TC' <c:out value="${pageDTO.criteria.type eq 'TC' ? 'selected' : '' }" /> >제목 or 내용</option>
                    <option value='TW' <c:out value="${pageDTO.criteria.type eq 'TW' ? 'selected' : '' }" /> >제목 or 작성자</option>
                    <option value='TWC' <c:out value="${pageDTO.criteria.type eq 'TWC' ? 'selected' : '' }" /> >제목 or 내용 or 작성자</option>
                </select>

                <input type='text' name='keyword' value="${pageDTO.criteria.keyword }">
                <input type='hidden' name="currentPage" value="${pageDTO.criteria.currentPage }">
                <input type='hidden' name="displayRecords" value="${pageDTO.criteria.displayRecords }">
                <button class='btn btn-primary'>Search</button>
            </form>



            <ul class="pagination" style="float:right;">
                <c:if test="${pageDTO.prev}">
                    <li class="paginate_button page-item previous">
                        <a href="${pageDTO.startPageNum - 1}" class="page-link">Previous</a>
                    </li>
                </c:if>

                <c:forEach var="num" begin="${pageDTO.startPageNum }" end="${pageDTO.endPageNum }">
                    <li class="paginate_button page-item ${pageDTO.criteria.currentPage == num ? 'active' : ''}">
                        <a href="${num}" class="page-link">${num }</a>
                    </li>
                </c:forEach>

                <c:if test="${pageDTO.next }">
                    <li class="paginate_button next">
                        <a href="${pageDTO.endPageNum + 1}" class="page-link">Next</a>
                    </li>
                </c:if>

            </ul>

            <form id="actionForm" action="/board/list" method="get">
                <input type="hidden" name="currentPage" value="${pageDTO.criteria.currentPage}">
                <input type="hidden" name="displayRecords" value="${pageDTO.criteria.displayRecords}">
            </form>



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

        history.replaceState({}, null, null);

        // post result 모달
        function checkModal(result){

            if(result === '' || history.state) {
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

        // pagination
        $(".paginate_button a").click(function(e){
            e.preventDefault();
            $("#actionForm").find("input[name='currentPage']").val($(this).attr("href"));
            $("#actionForm").submit();
        });

        // read
        $(".move").click(function(e){
           e.preventDefault();
            $("#actionForm").append("<input type='hidden' name='bno' value='"+$(this).attr('href')+"'>");
            $("#actionForm").append($("#searchForm").find("select[name='type']"));
            $("#actionForm").append($("#searchForm").find("input[name='keyword']"));
            $("#actionForm").attr("action", "/board/read");

            $("#actionForm").submit();
        });

        // search
        var searchForm = $("#searchForm");
        $("#searchForm button").click(function(e){

           if(!searchForm.find("option:selected").val()){
               alert("검색 종류를 선택하세요.");
               return false;
           }

           if(!searchForm.find("input[name='keyword']").val()){
               alert("키워르를 입력하세요.");
               return false;
           }

           searchForm.find("input[name='currentPage']").val('1');
           searchForm.submit();
        });


    });

</script>


<%@ include file="../includes/footer.jsp" %>

