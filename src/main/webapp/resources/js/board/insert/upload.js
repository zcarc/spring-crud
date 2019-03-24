

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

        var str = "";

        $(".uploadedResult ul li").each(function(i, e){

            //console.dir("var obj = $(e): " + obj);
            var obj = $(e);

            str += "<input type='hidden' name='attachVOList["+i+"].uploadFolder' value='"+obj.data("path")+"'>"
                +  "<input type='hidden' name='attachVOList["+i+"].uuid' value='"+obj.data("uuid")+"'>"
                +  "<input type='hidden' name='attachVOList["+i+"].fileName' value='"+obj.data("filename")+"'>"
                +  "<input type='hidden' name='attachVOList["+i+"].fileType' value='"+obj.data("type")+"'>";

        });

        //console.log("str: " + str);

        $("form[role='form']").append(str).submit();

    } else if($(this).attr("type") == "reset"){
        $("form").trigger('reset');
    }


});


// 확장자 확인
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


// 파일 업로드 시
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
        // beforeSend: function(xhr){
        //     xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
        // },
        success : function(result){
            //console.log(result);
            showUploadedResult(result);
        }

    }); // ajax

}); // change


// 파일 타입에 맞는 이미지 출력
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

    // 이미지 정보가 포함된 <li></li> 태그 추가
    $(".uploadedResult ul").append(str);

} // end function showUploadResult()


// 파일 이미지에서 'X' 버튼 클릭 시 삭제
$(".uploadedResult").on("click", "button", function(e){

    //console.log("delete file");

    var deleteFile = $(this).data("file");
    var type = $(this).data("type");
    //console.log("deleteFile: " + deleteFile);
    //console.log("type: " + type);

    var currentFileLi = $(this).closest("li").remove();

    $.ajax({

        url: '/deleteFile',
        type: 'post',
        data: {fileName:deleteFile, type:type},
        dataType: 'text',
        // beforeSend: function(xhr){
        //     xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
        // },
        success: function(result) {
            //console.log("deleteFile result: " + result);
            currentFileLi.remove();

        }

    }); // ajax


}); // uploadedResult
