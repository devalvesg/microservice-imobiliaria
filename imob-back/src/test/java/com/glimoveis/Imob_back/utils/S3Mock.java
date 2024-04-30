package com.glimoveis.Imob_back.utils;

import java.util.ArrayList;
import java.util.List;

public class S3Mock {

    public static List<String> presignedUrls = new ArrayList<>();


    public static List<String> mockImages(){
        presignedUrls.add("https://url-para-a-primeira-imagem");
        presignedUrls.add("https://url-para-a-segunda-imagem");

        return  presignedUrls;
    }
}
