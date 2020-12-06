package com.project.fileservice.util;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;

import java.io.*;

public class WordByPDF {
    public static void wordByPDF(String strWordPath,String strPDFpath){
        //开始时间
        long start = System.currentTimeMillis();
        File inputWord = new File(strWordPath);
        File outputFile = new File(strPDFpath);
        try  {
            InputStream docxInputStream = new FileInputStream(inputWord);
            OutputStream outputStream = new FileOutputStream(outputFile);
            IConverter converter = LocalConverter.builder().build();
            converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
            outputStream.close();
            //结束时间
            long end = System.currentTimeMillis();
            System.out.println("转化时间："+(end-start)/1000 + "s");
            System.out.println("转化成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
