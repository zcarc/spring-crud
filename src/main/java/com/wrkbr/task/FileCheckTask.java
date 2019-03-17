package com.wrkbr.task;

import com.wrkbr.domain.BoardAttachVO;
import com.wrkbr.mapper.BoardAttachMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.java2d.pipe.SpanShapeRenderer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j
public class FileCheckTask {

    @Setter(onMethod_ = @Autowired)
    private BoardAttachMapper boardAttachMapper;

    private String getFolderYesterday(){

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = simpleDateFormat.format(cal.getTime());
        return formatDate.replace("-", File.separator);
    }

    @Scheduled(cron = "0 0 2 * * * ")
    public void checkFiles(){

        log.info("checkFiles...");
        log.info("================================");


        List<BoardAttachVO> oldFiles =  boardAttachMapper.getOldFiles();

        List<Path> oldFilesPathList = oldFiles.stream()
                .map(boardAttachVO -> Paths.get("C:\\upload", boardAttachVO.getUploadFolder(), boardAttachVO.getUuid() + "_" + boardAttachVO.getFileName())
        ).collect(Collectors.toList());

        oldFiles.stream().filter(boardAttachVO -> boardAttachVO.isFileType() == true)
                        .map(boardAttachVO -> Paths.get("C:\\upload", boardAttachVO.getUploadFolder(), "_s" + boardAttachVO.getUuid() + "_" + boardAttachVO.getFileName()))
                        .forEach(boardAttachVO -> oldFilesPathList.add(boardAttachVO));

        File yesterDayFolder = Paths.get("C:\\upload", getFolderYesterday()).toFile();
        File[] notExistFiles = yesterDayFolder.listFiles(yesterdayFile -> oldFilesPathList.contains(yesterdayFile.toPath()) == false);

        for(File file : notExistFiles){
            log.warn("notExistFiles: " + file);

            if(file.exists())
                file.delete();
        }




    }

}
