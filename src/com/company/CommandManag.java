package com.company;

import java.util.List;
import java.util.ArrayList;

public class CommandManag {

    private static List<CommandArg> commandArgList = new ArrayList<CommandArg>();

    public static Boolean commandPars(String[] args) {

        try {
            String flag = null;
            String option = null;
            int i = 0;
            while (i < args.length) {
                if (args[i].charAt(0) == '-' && SupportedCommands.isFlag(args[i])) {

                    if (SupportedCommands.isHaveOpt(args[i])) {
                        if (++i < args.length && args[i].length() > 0) {
                            flag = new String(args[i - 1]);
                            option = new String();

                            while (i < args.length && args[i].charAt(0) != '-') {
                                option = option.concat(" ");
                                option = option.concat(args[i]);
                                ++i;
                            }
                            System.out.println(flag + " " + option);
                        } else {
                            System.out.println("---invalid command ----");
                            return false;
                        }
                    } else {
                        flag = new String(args[i]);
                    }
                } else {
                    System.out.println("---must start whit flag ----");
                    return false;
                }
                if (flag != null) {
                    commandArgList.add(new CommandArg(flag, option));
                } else {
                    System.out.println("---can't pars command ----");
                    return false;
                }
                System.out.println("-------");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static Commands commandLoader() {
        try {
            if (!commandArgList.isEmpty()) {
                int i = 0;
                switch (commandArgList.get(i).getFlag()) {
                    case "-f":
                        if (i + 1 < commandArgList.size()) {
                            ++i;
                            switch (commandArgList.get(i).getFlag()) {
                                case "-s":
                                    return new CommandHtmlSelector(commandArgList.get(i - 1).getOption(), commandArgList.get(i).getOption());
                            }
                        } else {
                            return new CommndHtmlLoader(commandArgList.get(i).getOption().trim());
                        }
                        break;
                    case "-h":
                        return new CommandHelp();
                }
            }

        } catch (IndexOutOfBoundsException e) {

        } catch (ClassCastException e) {

        }
        return new Commands();
    }

    public static void commandManag(String[] args) {
        if (commandPars(args)) {
            commandLoader().commandHandel();
        }
    }

    public static void commandResult() {

    }
}
