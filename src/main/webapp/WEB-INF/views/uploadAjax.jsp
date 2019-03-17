<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/upload.css" type="text/css">
</head>
<body>

<div class="uploadDiv">
    <input type="file" name="uploadFile" multiple>
</div>

<div class="uploadedResult">
    <ul></ul>
</div>

<button id="uploadBtn">Upload</button>

<div class="bigPictureWrapper">
    <div class="bigPicture">
    </div>
</div>

</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>

    $(document).ajaxSend(function(e,xhr,options) {
        xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}")
    });

    // called <a> tag
    function showImage(imageOriginPath){
        console.log("imageOriginPath: " + imageOriginPath);

        // default: none
        $(".bigPictureWrapper").css("display", "flex").show();

        $(".bigPicture").html("<img src='/displayImage?fileName="+imageOriginPath+"'>");
        $(".bigPicture").animate({width:'100%', height: '100%'}, 500);
    }


    $(document).ready(function(){

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
        }

        var uploadDivClone = $(".uploadDiv").clone();
        $("#uploadBtn").click(function(){

           var formData = new FormData();
           console.log( $("input[name='uploadFile']")[0].files );

           for(var i = 0; i < $("input[name='uploadFile']")[0].files.length; i++){

               if(!checkExtension($("input[name='uploadFile']")[0].files[i].name, $("input[name='uploadFile']")[0].files[i].size))
                   return false;

               formData.append("uploadFile", $("input[name='uploadFile']")[0].files[i]);
           }

           $.ajax({
               url: "/uploadAjaxAction",
               type: 'post',
               processData: false,
               contentType: false,
               data: formData,
               dataType: 'json',
               beforeSend: function(xhr){
                   xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
               },
               success: function(result){
                   console.log("uploaded");

                   showUploadedFiles(result);

                   $(".uploadDiv").html( uploadDivClone.html() );
               }
           }); // ajax

        });


        function showUploadedFiles(attachDTOList){

            var str = "";
            $(attachDTOList).each(function(i,e){

                if(e.image){
                    var imageThumbnailPath = encodeURIComponent(e.uploadFolder + "\\s_" + e.uuid + "_" + e.fileName);
                    var imageOriginPath = e.uploadFolder + "\\" + e.uuid + "_" + e.fileName;
                    imageOriginPath = imageOriginPath.replace( new RegExp(/\\/g), "/" );
                    console.log("imageOriginPath: " + imageOriginPath);
                    str += "<li>"
                        +      "<a href=\"javascript:showImage('"+imageOriginPath+"')\">"
                        +           "<img src='/displayImage?fileName="+imageThumbnailPath+"'>"+e.fileName+""
                        +      "</a>"
                        +      "<span data-file=\'"+imageThumbnailPath+"\' data-type='image'> x </span></li>"
                        +  "</li>";

                } else {
                    var filePath = encodeURIComponent(e.uploadFolder + "\\" + e.uuid + "_" + e.fileName);
                    str += "<li>"
                         +      "<a href='/downloadFile?fileName="+filePath+"'>"
                         +          "<img src='/resources/img/attach.png'>"+e.fileName+""
                         +      "</a>"
                         +      "<span data-file=\'"+filePath+"\' data-type='file'> x </span></li>"
                         + "</li>";
                }


            });
            $(".uploadedResult ul").append(str);
        }

        $(".bigPictureWrapper").on("click", function(e) {

            $(".bigPicture").animate( {width:'0%', height:'0%'}, 1000 );

            var myTimeout = setTimeout( function () {
                $('.bigPictureWrapper').hide();
                clearTimeout(myTimeout);
            }, 1000 );

        });

        // 'X'
        $(".uploadedResult").on("click", "span", function(e){

            var deleteFile = $(this).data("file");
            var type = $(this).data("type");
            console.log("deleteFile: " + deleteFile);
            console.log("type: " + type);

            $.ajax({

                url: '/deleteFile',
                data: {fileName:deleteFile, type:type},
                dataType: 'text',
                type: 'post',
                success: function(result) {
                    console.log("deleteFile result: " + result);
                }

            }); // ajax

        });


    }); // ready()


</script>

</html>
