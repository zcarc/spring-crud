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


        <form action="/board/update" method="post">

            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">

            <input type="hidden" name="currentPage" value="${criteria.currentPage}">
            <input type="hidden" name="displayRecords" value="${criteria.displayRecords}">
            <input type="hidden" name="type" value="${criteria.type}">
            <input type="hidden" name="keyword" value="${criteria.keyword}">

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

            <sec:authentication property="principal" var="pinfo" />
            <sec:authorize access="isAuthenticated()">
                <c:if test="${pinfo.username eq boardVO.writer }">
                    <button data-oper='save' class='btn btn-primary'>저장</button>
                    <button data-oper='delete' class="btn btn-danger">삭제</button>
                </c:if>
            </sec:authorize>
            <button data-oper='list' class='btn btn-warning'>목록</button>

        </form>


    </div> <!-- card-body -->

</div>


<%-- Attach --%>
<div class="card shadow mb-4">
    <div class="card-header py-3">
        <span class="m-0 font-weight-bold text-primary">Files</span>
    </div>

    <div class="form-group uploadDiv">
        <input type="file" name="uploadFile" multiple>
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

</div>
<!-- /.container-fluid -->

<script type="text/javascript">

    $(document).ready(function(){

        (function(){

            $.getJSON("/board/getListAttach", {bno: "${boardVO.bno}"}, function(attachVOList){
                //console.log("attachVOList: " + attachVOList);

                var str = "";

                $(attachVOList).each(function(i, attach){

                    // If fileType is true, it's an image file.
                    if(attach.fileType){

                        //console.log("image type...");

                        var imageThumbnailPath = encodeURIComponent( attach.uploadFolder+ "/s_" + attach.uuid + "_" + attach.fileName);

                        str += "<li data-path='"+attach.uploadFolder+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"
                            +       "<div>"
                            +           "<span>" + attach.fileName + "</span>"
                            +           "<button id='closeBtn' type='button' data-file=\'"+imageThumbnailPath+"\' data-type='image' class='btn btn-warning btn-circle btn-sm'><i class='fa fa-times'></i></button>"
                            +           "<br>"
                            +           "<img src='/displayImage?fileName="+imageThumbnailPath+"'>"
                            +       "</div>"
                            +  "</li>";

                    } else {

                        //console.log("not image type...");

                        var filePath = encodeURIComponent( attach.uploadFolder+ "/" + attach.uuid + "_" + attach.fileName);

                        str += "<li data-path='"+attach.uploadFolder+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"
                            +       "<div>"
                            +           "<span>" + attach.fileName + "</span>"
                            +           "<button id='closeBtn' type='button' data-file=\'"+filePath+"\' data-type='file' class='btn btn-warning btn-circle btn-sm'><i class='fa fa-times'></i></button>"
                            +           "<br>"
                            +           "<img src='/resources/img/attach.png'>"
                            +       "</div>"
                            +  "</li>";

                    }


                }); // end each

                //console.log("Immediately executed function. str: " + str);

                $(".uploadedResult ul").html(str);

            });

        })(); // Immediately executed function.


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

                var str="";

                $(".uploadedResult ul li").each( function(i, e){

                    var obj = $(e);
                    console.dir("obj: " + obj);

                    str += "<input type='hidden' name='attachVOList["+i+"].uploadFolder' value='"+obj.data("path")+"'>"
                        +  "<input type='hidden' name='attachVOList["+i+"].uuid' value='"+obj.data("uuid")+"'>"
                        +  "<input type='hidden' name='attachVOList["+i+"].fileName' value='"+obj.data("filename")+"'>"
                        +  "<input type='hidden' name='attachVOList["+i+"].fileType' value='"+obj.data("type")+"'>";
                });

                //alert("str: " + str);

                $("form").append(str);
                $("form").submit();

            }

        });

    });


    $("input[type='file']").change(function(e){

        var formData = new FormData();
        //console.log("$(\"input[name='uploadFile']\")[0].files: " + $("input[name='uploadFile']")[0].files);


        for(var i = 0; i < $("input[name='uploadFile']")[0].files.length; i++) {

            if( !checkExtension($("input[name='uploadFile']")[0].files[i].name, $("input[name='uploadFile']")[0].files[i].size) ) {
                return false;
            }

            formData.append( "uploadFile", $("input[name='uploadFile']")[0].files[i] );

        }// end for

        $.ajax({

            url: '/uploadAjaxAction',
            type: 'post',
            processData: false,
            contentType: false,
            data: formData,
            dataType: 'json',
            beforeSend: function(xhr){
                xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
            },
            success : function(result){
                showUploadedResult(result);
            }

        }); // ajax

    }); // change


    function showUploadedResult(uploadedResultArr) {

        if(!uploadedResultArr || uploadedResultArr.length == 0)
            return;

        var str = "";

        $(uploadedResultArr).each(function(i,obj) {

            //image type
            if(obj.image) {

                //console.log("Image type...");

                var imageThumbnailPath = encodeURIComponent( obj.uploadFolder+ "/s_" + obj.uuid + "_" + obj.fileName);

                str += "<li data-path='"+obj.uploadFolder+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'>"
                    +       "<div>"
                    +           "<span>" + obj.fileName + "</span>"
                    +           "<button id='closeBtn' type='button' data-file=\'"+imageThumbnailPath+"\' data-type='image' class='btn btn-warning btn-circle btn-sm'><i class='fa fa-times'></i></button>"
                    +           "<br>"
                    +           "<img src='/displayImage?fileName="+imageThumbnailPath+"'>"
                    +       "</div>"
                    +  "</li>";

            } else {

                //console.log("Not image type...");

                var filePath = encodeURIComponent( obj.uploadFolder+ "/" + obj.uuid + "_" + obj.fileName);
                //console.log("var filePath = encodeURIComponent( obj.uploadFolder+ \"/\" + obj.uuid + \"_\" + obj.fileName): " + filePath);

                str += "<li data-path='"+obj.uploadFolder+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'>"
                    +       "<div>"
                    +           "<span>" + obj.fileName + "</span>"
                    +           "<button type='button' data-file=\'"+filePath+"\' data-type='file' class='btn btn-warning btn-circle btn-sm'><i class='fa fa-times'></i></button>"
                    +           "<br>"
                    +           "<img src='/resources/img/attach.png'>"
                    +       "</div>"
                    +  "</li>";

            }

        }); // end each

        //console.log("str: " + str);

        $(".uploadedResult ul").append(str);

    } // end function showUploadResult()


    function checkExtension(fileName, fileSize) {

        var regex = new RegExp("(.*?)\.(exe|sh|zip|alz|rar|bat)$");
        var maxSize = 5242880;

        if(fileSize >= maxSize) {
            alert("파일 사이즈 초과");
            return false;
        }

        if(regex.test(fileName)) {
            alert("해당 종류의 파일은 업로드할 수 없습니다.");
            return false;
        }

        return true;

    } // end checkExtension



    // 'X'
    $(".uploadedResult").on("click", "button", function(e){

        //console.log("delete file...");

        if(confirm("해당 파일을 삭제하시겠습니까?")){

            var targetLi = $(this).closest("li");
            targetLi.remove();

        }


    }); // uploadedResult


</script>


<%@ include file="../includes/footer.jsp" %>