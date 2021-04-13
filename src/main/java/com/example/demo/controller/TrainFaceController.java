package com.example.demo.controller;


import com.example.demo.engine.QuickStart;
import com.example.demo.engine.contorl.EngineUtil;
import com.example.demo.engine.entity.Face;
import com.example.demo.engine.entity.RecognizeFace;
import com.example.demo.engine.entity.TrainFace;
import com.example.demo.engine.entity.TrainResult;
import com.example.demo.engine.util.CreateEngineFileUtil;
import com.example.demo.engine.util.FolderUtil;
import com.example.demo.engine.contorl.GetResultUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.demo.engine.util.AttributeCheck;
import com.example.demo.util.WebResponse;
import com.example.demo.engine.contorl.GetResultUtil;


@RestController
public class TrainFaceController {
    private static Logger LOGGER = LoggerFactory.getLogger(TrainFaceController.class);
    final static FolderUtil folderUtil = new FolderUtil();
    final static EngineUtil engineUtil = new EngineUtil();
    final static CreateEngineFileUtil createEngineFileUtil = new CreateEngineFileUtil();
    final static GetResultUtil getResultUtil = new GetResultUtil();

    final static boolean logDeleteFlag = false;

    final static String quickStartPath = "C:\\QuickStart";
    final static StringBuilder enginePath = new StringBuilder(quickStartPath + "\\Engine");
    final static StringBuilder faceDBPath = new StringBuilder(enginePath + "\\eGroup\\");
    final static StringBuilder resourcesPath = new StringBuilder(enginePath + "\\resources\\");
    final static StringBuilder trainResultLogPath = new StringBuilder(enginePath + "\\Status.TrainResultCPU.eGroup");
    final static StringBuilder trainListPath = new StringBuilder(resourcesPath + "list.txt");
    final static StringBuilder modelAppendListPath = new StringBuilder(faceDBPath + "\\modelList.egroup.List");
    final static StringBuilder outputfacePath = new StringBuilder(enginePath + "\\outputFace");
    final static StringBuilder outputframepath = new StringBuilder(enginePath + "\\outputFrame");
    final static StringBuilder jsonFolderPath = new StringBuilder(enginePath + "\\json");
    final static StringBuilder catchJsonName = new StringBuilder("output.cache.egroup");
    // final static StringBuilder allJsonName = new StringBuilder("output." + LocalDate.now() + ".egroup");
    final static StringBuilder modelInserFilePath = new StringBuilder(enginePath + "\\Singal_For_Model_Insert.txt");

    final static File outputfaceFile = new File(outputfacePath.toString());
    final static File faceDBFile = new File(faceDBPath.toString());
    final static File outputframeFile = new File(outputframepath.toString());
    final static File jsonFolderFile = new File(jsonFolderPath.toString());

    final static StringBuilder jeffFaceDBPath = new StringBuilder(faceDBPath + "\\jeff");
//    final static StringBuilder FaceImageFolderPath = new StringBuilder(resourcesPath + "jeff");
//    final static File FaceImageFolder = new File(FaceImageFolderPath.toString());

    @PostMapping("/Train/{name}")
    public Response TrainPhoto(@PathVariable String name) {
        final List<TrainFace> trainFaceList = new ArrayList<>();
        // Set training variable
        final TrainFace trainFace = new TrainFace();
        trainFace.setTrainListPath(trainListPath.toString());
        trainFace.setModelPath((faceDBPath+name));
        trainFace.setEnginePath(enginePath.toString());
        trainFace.setPersonId(name);
        // Get image in folder and set training image
        trainFace.setImagePathList(getFaceImageFolder(name));
        // Add to trainFace list
        trainFaceList.add(trainFace);
        // Create train face list
        createEngineFileUtil.createTrainFaceTxt(trainListPath.toString(), trainFaceList);
        // Start training and get result
        final TrainResult trainResult = engineUtil.trainFace(trainFace, logDeleteFlag);
        LOGGER.info("trainResult=" + new Gson().toJson(trainResult));

        return null;
    }


        public static List<String> getFaceImageFolder (String name){
            List<String> imagePathList = new ArrayList<>();
            final StringBuilder FaceImageFolderPath = new StringBuilder(resourcesPath + name);
            final File FaceImageFolder = new File(FaceImageFolderPath.toString());
            imagePathList = folderUtil.listPath(FaceImageFolder);
            return imagePathList;
        }
        
    }
