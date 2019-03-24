


var ckeditor_config = {
    resize_enaleb : false,
    enterMode : CKEDITOR.ENTER_BR,
    shiftEnterMode : CKEDITOR.ENTER_P,
    filebrowserUploadUrl : "/ckUploding",

    resize_enabled : false,
    extraAllowedContent : 'iframe[*]',
    width: "100%",
    height: "585px"
};

CKEDITOR.replace("ckeditor", ckeditor_config);