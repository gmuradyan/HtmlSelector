package com.company;

public class CommndHtmlLoader extends Commands {

    protected String htmlPath;
    protected String htmlFile;



    CommndHtmlLoader(){ }

    CommndHtmlLoader(String path){
        htmlPath=path.trim();//new String(path);
    }

    public void commandHandel(){
        System.out.println("CommndHtmlLoader");
        if(loadHtmlFile()){
            System.out.println("file-corrected-loaded="+htmlFile);
        }
    }

    private Boolean chackHtmlTags(){
       return HtmlValidator.validateFile(htmlFile);
    }

    protected Boolean loadHtmlFile(){
        htmlFile=new String(inOut.scanFile(htmlPath));
        return chackHtmlTags();
    }

}
