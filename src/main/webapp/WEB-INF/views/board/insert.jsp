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

            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">

            <div class="form-group">
                <label>Title</label>
                <input class="form-control" name="title">
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

<script>

    $(document).ready(function(){

        $("button").click(function(e){
            e.preventDefault();
            console.log("submit clicked.");

            if($(this).data("type") == 'list'){
                $("form").empty();
                $("form").attr("action", "/board/list").attr("method", "get").submit();

            } else if($(this).attr("type") == "submit"){

                var str = "";

                $(".uploadedResult ul li").each(function(i, e){

                    console.dir("var obj = $(e): " + obj);
                    var obj = $(e);

                    str += "<input type='hidden' name='attachVOList["+i+"].uploadFolder' value='"+obj.data("path")+"'>"
                        +  "<input type='hidden' name='attachVOList["+i+"].uuid' value='"+obj.data("uuid")+"'>"
                        +  "<input type='hidden' name='attachVOList["+i+"].fileName' value='"+obj.data("filename")+"'>"
                        +  "<input type='hidden' name='attachVOList["+i+"].fileType' value='"+obj.data("type")+"'>";

                });

                console.log("str: " + str);

                $("form[role='form']").append(str).submit();

            } else if($(this).attr("type") == "reset"){
                $("form").trigger('reset');
            }


        });


        //
        var regex = new RegExp("(.*?)\.(exe|sh|zip|alz|rar|bat)$");
        var maxSize = 5242880;

        function checkExtension(fileName, fileSize) {

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


        $("input[type='file']").change(function(e){

            var formData = new FormData();
            console.log("$(\"input[name='uploadFile']\")[0].files: " + $("input[name='uploadFile']")[0].files);


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
                    console.log(result);
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

                    console.log("Image type...");

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

                    console.log("Not image type...");

                    var filePath = encodeURIComponent( obj.uploadFolder+ "/" + obj.uuid + "_" + obj.fileName);
                    console.log("var filePath = encodeURIComponent( obj.uploadFolder+ \"/\" + obj.uuid + \"_\" + obj.fileName): " + filePath);

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

            console.log("str: " + str);

            $(".uploadedResult ul").append(str);

        } // end function showUploadResult()


        // 'X'
        $(".uploadedResult").on("click", "button", function(e){

            console.log("delete file");

            var deleteFile = $(this).data("file");
            var type = $(this).data("type");
            console.log("deleteFile: " + deleteFile);
            console.log("type: " + type);

            var currentFileLi = $(this).closest("li").remove();

            $.ajax({

                url: '/deleteFile',
                type: 'post',
                data: {fileName:deleteFile, type:type},
                dataType: 'text',
                beforeSend: function(xhr){
                    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
                },
                success: function(result) {
                    console.log("deleteFile result: " + result);
                    currentFileLi.remove();

                }

            }); // ajax


        }); // uploadedResult

    }); // ready


</script>