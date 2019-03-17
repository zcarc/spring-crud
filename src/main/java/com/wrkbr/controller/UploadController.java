package com.wrkbr.controller;

import com.wrkbr.domain.AttachDTO;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@Log4j
public class UploadController {


//    @GetMapping("/uploadAjax")
//    public void uploadAjax(){
//        log.info("uploadAjax");
//    }

    // Create physical files.
    @PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<AttachDTO>> uploadAjaxAction(@RequestBody MultipartFile[] uploadFile){
        log.info("uploadAjaxAction()...");

        String todayDateFolder = getFolder();

        File uploadFolder = new File("C:\\upload", todayDateFolder);
        log.info("File uploadFolder = new File(\"C:\\\\upload\", getFolder()): " + uploadFolder);

        if(!uploadFolder.exists()) {
            uploadFolder.mkdir();
        }

        List<AttachDTO> attachDTOList = new ArrayList<>();

        for(MultipartFile multipartFile : uploadFile){

            log.info("multipartFile.getOriginalFilename() - : " + multipartFile.getOriginalFilename());
            log.info("multipartFile.getSize() - : " + multipartFile.getSize());

            String fileName = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("\\") + 1);
            log.info("substring fileName - : " + fileName);


            UUID uuid = UUID.randomUUID();
            String fileNameWithUUID = uuid.toString() + "_" + fileName;
            log.info("fileName = uuid.toString() + \"_\" + fileName - : " + fileName);


            try {
                File newFile = new File(uploadFolder, fileNameWithUUID);
                multipartFile.transferTo(newFile);

                AttachDTO attachDTO = new AttachDTO();
                attachDTO.setFileName(fileName);
                attachDTO.setUploadFolder(todayDateFolder);
                attachDTO.setUuid(uuid.toString());

                // check Image
                if(checkImageType(newFile)) {

                    attachDTO.setIsImage(true);

                    try(FileInputStream thumbnailInputStream = new FileInputStream(newFile);
                        FileOutputStream thumbnailOutputStream = new FileOutputStream(new File(uploadFolder, "s_" + fileNameWithUUID))){

                        Thumbnailator.createThumbnail(thumbnailInputStream, thumbnailOutputStream, 300, 300);
                    } // try

                } // if checkImageType

                // if or else
                attachDTOList.add(attachDTO);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } // for

        log.info("attachDTOList: " + attachDTOList);
        return new ResponseEntity<>(attachDTOList, HttpStatus.OK);

    } // uploadAjaxAction


    public String getFolder(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = simpleDateFormat.format(new Date());

        return dateFormat.replace("-", File.separator);
    }

    public boolean checkImageType(File file){
        try {
            String contentType = Files.probeContentType(file.toPath());
            log.info("String contentType = Files.probeContentType(file.toPath()) - : " + contentType);

            //return contentType.startsWith("image");
            return contentType.contains("image");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/displayImage")
    @ResponseBody
    public ResponseEntity<byte[]> displayImage(String fileName){
        log.info("displayImage... fileName: " + fileName);

        File file = new File("C:\\upload\\", fileName);
        log.info("File file = new File(\"C:\\\\upload\\\\\", fileName) - : " + file);

        ResponseEntity<byte[]> result = null;

        try{
            HttpHeaders header = new HttpHeaders();

            header.add("Content-Type", Files.probeContentType(file.toPath()));

            if(file.exists())
                result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }// displayImage()


	@GetMapping(value = "/downloadFile", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String fileName, @RequestHeader("User-Agent") String userAgent) {
		log.info("downloadFile....................");
        log.info("fileName: " + fileName);

		Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
        log.info("resource: " + resource);

        if(!resource.exists()) {
            log.info("File does not exist.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String resourceFileName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        // remove UUID
        String removedUUIDFileName = resourceFileName.substring(resourceFileName.indexOf("_") + 1);

        try {
            headers.add("Content-Disposition",
                        "attachment; filename=" + new String(removedUUIDFileName.getBytes("UTF-8"), "ISO-8859-1"));

            String encodedDownFileName = null;

            if(userAgent.contains("Trident")){
                log.info("IE browser");
                encodedDownFileName = URLEncoder.encode(removedUUIDFileName, "UTF-8").replaceAll("\\+", " ");


            } else if(userAgent.contains("Edge")){
                log.info("Edge browser");

                // URLEncoder: Korean encoding
                encodedDownFileName = URLEncoder.encode(removedUUIDFileName, "UTF-8");

            } else {
                log.info("Chrome browser");
                encodedDownFileName = new String(removedUUIDFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
            log.info("encodedDownFileName - : " + encodedDownFileName);
            log.info("removedUUIDFileName - : " + removedUUIDFileName);

            // Set FileName
            headers.add("Content-Disposition", "attachment; filename=" + encodedDownFileName);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}


    @PostMapping("/deleteFile")
    @ResponseBody
    public ResponseEntity<String> deleteFile(String fileName, String type) {

        log.info("deleteFile...");
        log.info("fileName: " + fileName);
        log.info("type: " + type);

        File file = null;

        try {

            file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
            log.info("decoded File: " + file );


            if(file.exists())
                log.info("deleted: " + file.delete());

            // if file is thumbnail, delete origin image
            if(type.equals("image")) {

                log.info("type.equals(\"image\")...");

                String originImage = file.getAbsolutePath().replace("s_", "");
                log.info("file.getPath(): " + file.getPath());
                log.info("file.getAbsolutePath(): " + file.getAbsolutePath());
                log.info("file.getCanonicalPath(): " + file.getCanonicalPath());
                log.info("String originImage = file.getAbsolutePath().replace(\"s_\", \"\"): " + originImage);

                file = new File(originImage);

                if(file.exists())
                    log.info("deleted: " + file.delete());

            }

        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>("deleted", HttpStatus.OK);

    }

}
