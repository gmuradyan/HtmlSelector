package com.company;

public  class CommandArg {
   private String flag;
   private String option;

    CommandArg(String flag, String option){
            this.flag=flag;
            this.option=option;
    }

    CommandArg(String flag){
            this.flag = flag;
            this.option=null;
    }

    public String getFlag() {
        return flag;
    }

    public String getOption() {
        return option;
    }
}
