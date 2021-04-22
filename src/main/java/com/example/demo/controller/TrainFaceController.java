package com.example.demo.controller;


import com.example.demo.engine.QuickStart;
import com.example.demo.engine.contorl.EngineUtil;
import com.example.demo.engine.entity.*;
import com.example.demo.engine.util.CreateEngineFileUtil;
import com.example.demo.engine.util.FolderUtil;
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
import java.util.ArrayList;
import java.util.List;

import com.example.demo.engine.util.AttributeCheck;
import com.example.demo.util.WebResponse;
import com.example.demo.engine.contorl.GetResultUtil;

@RestController
public class TrainFaceController {
    private static Logger LOGGER = LoggerFactory.getLogger(TrainFaceController.class);
    final static FolderUtil folderUtil = new FolderUtil();
    final static EngineUtil engineUtil = new EngineUtil();
    final static CreateEngineFileUtil createEngineFileUtil = new CreateEngineFileUtil();

    final static boolean logDeleteFlag = false;

    final static String quickStartPath = "C:\\";
    final static StringBuilder enginePath = new StringBuilder(quickStartPath + "\\eGroupAI_FaceEngine_CPU_V4.2.0-beta.20");
    final static StringBuilder faceDBPath = new StringBuilder(enginePath + "\\eGroup\\");
    final static StringBuilder resourcesPath = new StringBuilder(enginePath + "\\resources\\");
    final static StringBuilder trainResultLogPath = new StringBuilder(enginePath + "\\Status.TrainResultCPU.eGroup");
    final static StringBuilder trainListPath = new StringBuilder(resourcesPath + "list.txt");
    final static StringBuilder modelAppendListPath = new StringBuilder(faceDBPath + "\\ModelAppend.egroupList");
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

//        modelAppend(name);
        modelInsert(name);

        return null;
    }

    public static List<String> getFaceImageFolder(String name) {
        List<String> imagePathList = new ArrayList<>();
        final StringBuilder FaceImageFolderPath = new StringBuilder(resourcesPath+name);
        final File FaceImageFolder = new File(FaceImageFolderPath.toString());
        imagePathList = folderUtil.listPath(FaceImageFolder);
        return imagePathList;
    }

    public static void modelInsert(String name){
        List<String> faceDBList = new ArrayList<>();
        faceDBList.add(faceDBPath + name + ".faceDB");
        // Set model insert variable
        ModelInsert modelInsert = new ModelInsert();
        modelInsert.setEnginePath(enginePath.toString());
        modelInsert.setFaceDBList(faceDBList);
        modelInsert.setListPath(modelInserFilePath.toString());
        ModelInsertResult modelInsertResult = engineUtil.modelInsert(modelInsert, false, 3000);
        LOGGER.info("modelInsertResult : " + new Gson().toJson(modelInsertResult));
    }

    public static void modelAppend(String name){
        List<String> faceDBList = new ArrayList<>();
        faceDBList.add(faceDBPath + name + ".faceDB");
        faceDBList.add(faceDBPath + "all.faceDB");

        // Model Append
        final ModelAppend modelAppend = new ModelAppend();
        modelAppend.setTrainedFaceDBPath(faceDBPath + "all.faceDB");
        modelAppend.setFaceDBList(faceDBList);
        modelAppend.setListPath(modelAppendListPath.toString());
        modelAppend.setEnginePath(enginePath.toString());
        final ModelAppendResult modelAppendResult = engineUtil.modelAppend(modelAppend, false, 25000);
        LOGGER.info("modelInsertResult : " + new Gson().toJson(modelAppendResult));

    }


}