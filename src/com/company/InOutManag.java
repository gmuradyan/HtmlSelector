package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.Scanner;


public class InOutManag {

    public  String scanFile(String path){
        try {
            File file = new File(path);
            if (file.isFile()) {
                StringBuilder html=new StringBuilder();
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    html.append(sc.nextLine());
                }
                return html.toString();
            }else{
                System.out.println("can`t find file"+path);
            }
        }catch (FileNotFoundException e){
        }
        return null;
    }

    public  void printFile(String str){
        System.out.println("RESULT:");
        System.out.println(str);
    }

    public  Scanner createFileDescription(String path,String delimer){
        try {
            File file = new File(path);
            if (file.isFile()) {
                Scanner sc = new Scanner(file);
                sc.useDelimiter(delimer);
                return sc;
            }else{
                System.out.println("can`t find file"+path);
            }
        }catch (FileNotFoundException e){
        }
        return null;
    }

    public  String scanFileByLine(Scanner sc){
        try {
                return sc.nextLine();
        }catch (java.util.InputMismatchException e ){
        }
        return null;
    }

    public  String readFile(String path){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String currentLine = reader.readLine();
            reader.close();
            System.out.println("currentLine"+currentLine);
            return currentLine;
        }
        catch (IOException e){
            System.out.println("IOException"+e.getMessage());
        }
        return null;
    }


}
