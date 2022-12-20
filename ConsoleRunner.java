import java.awt.Color;
import java.util.Scanner;
import java.awt.event.*;
import javax.swing.*;



public class ConsoleRunner extends JFrame implements KeyListener {
	JLabel label;
	public static boolean keypressed;
    public static boolean upPress;
    public static boolean dwnPress;

    public static void main(String[] args) {

        // >:( i dont like this part
        new ConsoleRunner();

        //DEFINE DOMAIN OF GAME AND SURROUNDINGS
        int screensizex = 70;//MUST BE OVER 46 (RECOMMENDED OVER 48)
        short gameSpeed = 4;//MUST BE OVER 0
        //(speed 4 recommended when screensizex > 60)
        //(speed 3 recommended when screensizex <= 60)
        //idk u can slow it down/speed it up further if u want i guess

        String customBorder = "█";
        String customTop = "▀";
        String customBottom = "▄";

        String customEnemy = "█";
        String[] border = {customTop,customBottom, customBorder};
        String gaps = " ";
        String playerCharacter = "U";

        //floor and sky should be same size, clouds probably won't display in order because im lazy
        //in this case its 39 characters
        String customFloor = "¯''\"¯\"\"¯°\"°\"\"¯\"''\"'°¯\"'°¯¯¹\"\"''''°\"\"¯¯\"";
        String customSky =    "  °°°                  °° °°°  °°      ";
        short skyR = 0;
        String floor = "";
        String sky = "";
        short floorspot = 0;

        //set the floor
        while (floor.length() < screensizex-2) for (int i=0;floor.length() < screensizex-2;i++) {
            //double up if i have to
            floor += customFloor.substring(i%customFloor.length(),i%customFloor.length()+1);
            sky += customSky.substring(i%customFloor.length(),i%customFloor.length()+1);
            floorspot++;
        }


        //USE INITIAL VARIABLES TO CALCULATE SIZE
        while (border[0].length() < screensizex) {
            border[0] += customTop;
            border[1] += customBottom;
            border[2] += customBorder;
        }
        while (gaps.length() < screensizex - 2) {
            gaps += " ";
        }
        gaps += customBorder;

        //DEFINE ENDING CUTSCENE VARIABLES
        String[] endText = {"▄   ▄  ▄▄▄  ▄   ▄    ▄      ▄▄▄   ▄▄▄▄ ▄▄▄▄▄",
                            "█   █ █   █ █   █    █     █   █ █     █    ",
                            " █ █  █   █ █   █    █     █   █  ▀▀▀▄ █▀▀  ",
                            "  █   █   █ █   █    █     █   █     █ █    ",
                            "  ▀    ▀▀▀   ▀▀▀     ▀▀▀▀▀  ▀▀▀  ▀▀▀▀  ▀▀▀▀▀"};
        String endSpaces = "";
        String[] endFaders = {"✖","X","×","·"," "};

        //DEFINE GAMEPLAY VARIABLES
        Scanner scan = new Scanner (System.in);
        int x = 0;
        int y = 0;
        short realY = 0;
        short jumprecharge = 0;
        String direction = "";
        x++;
        boolean failed = false;
        int pts = 0;

        int badthing = (int)(Math.random()*5)+100;
        boolean[] badtype = {false, true, true};
                           //0 bot, 1 mid, 2 top

        String upframe = "     ";
        String midframe = "     ";
        String botframe = "     ";


        //egg

        String[] groundEgg = {"  °°°°         ° °°ඞ°°°ඞ°°  °←ඞ°ඞ°ඞඞ°° ",
                              "°°°  This game was made by Jus Liu  °°°"};
        while (true) {
            //reset variables
            failed = false;
            x = 0;
            y = 0;
            realY = 0;
            badthing = (int)(Math.random()*5)+100;
            pts = 0;


            while (!failed) {
                //CLEAR BUFFER
                System.out.print("\033[H\033[2J");
                System.out.flush();

                //DRAW UPFRAME MIDFRAME AND BOTFRAME
                upframe = "";
                midframe = "";
                botframe = "";
                if (badthing-x <= 5 && badthing > x) {
                    if (badtype[2]) {
                        while (upframe.length() < badthing-x) {
                            upframe += " ";
                        }
                        upframe += customEnemy;
                    }
                    if (badtype[1]) {
                        while (midframe.length() < badthing-x) {
                            midframe += " ";
                        }
                        midframe += customEnemy;

                        while (midframe.length()<4) {
                            midframe += " ";
                        }
                    }
                    if (badtype[0]) {
                        while (botframe.length() < badthing-x) {
                            botframe += " ";
                        }
                        botframe += customEnemy;
                    }
                }

                while (midframe.length()<5) {
                    midframe += " ";
                }
                while (upframe.length()<5) {
                    upframe += " ";
                }
                while (botframe.length() < 5) {
                    botframe += " ";
                }

                //CONTROLS
                if (realY == 0 && jumprecharge > 0) jumprecharge--;

                if (keypressed && realY <= 0) {
                    if (realY == 0 && jumprecharge <= 0) {
                        realY--;
                        jumprecharge = 10;
                    } else if (realY > -30 && realY < 0)
                        realY --;
                    else if (realY < 0)
                        realY=30;
                } else if (realY > 0)
                    realY--;
                else if (realY < 0)
                    realY=(short)((-realY)-1);
                else realY = 0;

                if (realY > 0) y = (int)((realY+9)/10);
                else if (realY < 0) y = (int)((9-realY)/10);
                else y=0;

                //failsafe
                if (y>2) y=2;


                //DRAW PLAYER
                if (y == 0) {
                    botframe += playerCharacter;
                } else if (y == 1) {
                    midframe += playerCharacter;
                } else {
                    upframe += playerCharacter;
                }


                /**Fill in UPFRAME, MIDFRAME, and BOTFRAME**/

                //COLLISION!!!
                x++;
                if (badthing-5==x) {
                    if (badtype[y]) { //2 top, 1 mid, 0 bot
                        x--;
                        failed = true;
                    } else pts++;
                }

                //if obstacle already past, make new one
                if (badthing < x) {
                    //SET BADTYPE (use badthing variable to store separate info to be applied later)
                    badthing = (int)((Math.random()*3.6)+0.7);
                    switch (badthing) {
                        case 0:
                            badtype[2] = true;
                            badtype[1] = false;
                            badtype[0] = false;
                                        //0 bot, 1 mid, 2 top
                            break;
                        case 1:
                            badtype[2] = true;
                            badtype[1] = true;
                            badtype[0] = false;
                            break;
                        case 2:
                            badtype[2] = false;
                            badtype[1] = true;
                            badtype[0] = true;
                            break;
                        case 3:
                            badtype[2] = false;
                            badtype[1] = false;
                            badtype[0] = true;
                            break;
                        case 4:
                            badtype[2] = true;
                            badtype[1] = false;
                            badtype[0] = true;
                            break;
                    }
                    //SET LOCATION
                    badthing = (int)(Math.random()*20)+100;
                    x = 0;
                }

                //PLACE OBSTACLE (badthing)
                if (badthing-x < screensizex-2 && badthing > x+5) {
                    if (badtype[2]) {
                        while (upframe.length() < badthing-x) {
                            upframe += " ";
                        }
                        upframe += customEnemy;
                    }
                    if (badtype[1]) {
                        while (midframe.length() < badthing-x) {
                            midframe += " ";
                        }
                        midframe += customEnemy;
                    }
                    if (badtype[0]) {
                        while (botframe.length() < badthing-x) {
                            botframe += " ";
                        }
                        botframe += customEnemy;
                    }
                }


                while (upframe.length() < screensizex-2) {
                    upframe += " ";
                }
                while (midframe.length() < screensizex-2) {
                    midframe += " ";
                }
                while (botframe.length() < screensizex-2) {
                    botframe += " ";
                }

                //END EACH ROW WITH THE CUSTOM BORDER
                upframe += customBorder;
                midframe += customBorder;
                botframe += customBorder;

                //update the floor and sky
                if (floorspot != 0 && floorspot % (10*customFloor.length()) == 0) {
                    floorspot = (short)(Math.random()*30);
                    if (floorspot == 17 && skyR != 32 && skyR != 12) skyR = 32;
                    else if (floorspot < 4 && skyR != 32 && skyR != 12) skyR = 12;
                    else skyR = 0;

                    floorspot = 0;
                } else floorspot++;
                floor = floor.substring(1,floor.length()) + customFloor.substring((floorspot%customFloor.length()),(floorspot%(customFloor.length())+1));
                if (floorspot % 10 == 2) {
                    if (skyR == 12)
                        sky = sky.substring(1,sky.length()) + groundEgg[0].substring(((floorspot)/10)%(groundEgg[0].length()),((floorspot)/10)%(groundEgg[0].length())+1);
                    else if (skyR == 32)
                        sky = sky.substring(1,sky.length()) + groundEgg[1].substring(((floorspot)/10)%(groundEgg[1].length()),((floorspot)/10)%(groundEgg[1].length())+1);
                    else
                        sky = sky.substring(1,sky.length()) + customSky.substring(((floorspot)/10)%(customSky.length()),((floorspot)/10)%(customSky.length())+1);
                }

                //DRAW EVERYTHING
                System.out.println(border[1]);
                System.out.println(customBorder + sky + customBorder);
                System.out.println(customBorder + upframe);
                System.out.println(customBorder + midframe);
                System.out.println(customBorder + botframe);
                System.out.println(customBorder + floor + customBorder);
                System.out.println(border[0]);
                System.out.print(" points: "+pts+"  ");

                //debug
                // System.out.println(badtype[2] + " " + badtype[1] + " " + badtype[0]);
                // System.out.println(keypressed);

                //THREAD SLEEP
                try {
                    Thread.sleep(60/gameSpeed);
                } catch(InterruptedException e) {
                    // this part is executed when an exception (in this example InterruptedException) occurs
                }
            }

            /**     ENDING CUTSCENE     **/

            for (int i=0;i<6;i++) {
                System.out.print("\033[H\033[2J");
                System.out.flush();

                System.out.println(border[1]);
                for (int e=0; e<=i && e<5; e++) {
                    System.out.println(border[2]);
                }
                if (i == 0) {
                    System.out.println(customBorder + upframe);
                    System.out.println(customBorder + midframe);
                    System.out.println(customBorder + botframe);
                    System.out.println(customBorder + floor + customBorder);
                } else if (i == 1) {
                    System.out.println(customBorder + midframe);
                    System.out.println(customBorder + botframe);
                    System.out.println(customBorder + floor + customBorder);
                } else if (i == 2) {
                    System.out.println(customBorder + botframe);
                    System.out.println(customBorder + floor + customBorder);
                } else if (i == 3) {
                    System.out.println(customBorder + floor + customBorder);
                }
                //always print the bottom
                System.out.println(border[0]);

                try {
                    Thread.sleep(100);
                } catch(InterruptedException e) {
                    // this part is executed when an exception (in this example InterruptedException) occurs
                }
            }

            ////SAY END PART
            for (int wordpart=0;wordpart<5;wordpart++) {
                switch (wordpart) {
                    case 0:
                        endText[0]="▄   ▄  ▄▄▄  ▄   ▄    ▄      ▄▄▄   ▄▄▄▄ ▄▄▄▄▄";
                        endText[1]="█   █ █   █ █   █    █     █   █ █     █    ";
                        endText[2]=" █ █  █   █ █   █    █     █   █  ▀▀▀▄ █▀▀  ";
                        endText[3]="  █   █   █ █   █    █     █   █     █ █    ";
                        endText[4]="  ▀    ▀▀▀   ▀▀▀     ▀▀▀▀▀  ▀▀▀  ▀▀▀▀  ▀▀▀▀▀";
                        break;
                    case 1:
                        endText[0]=" ▄▄▄  ▄▄  ▄ ▄▄▄▄     ▄   ▄  ▄▄▄  ▄   ▄ ▄▄▄▄ ";
                        endText[1]="█   █ █▀▄ █ █   █    █   █ █   █ █   █ █   █";
                        endText[2]="█▀▀▀█ █ █ █ █   █     █ █  █   █ █   █ █▀▀▀▄";
                        endText[3]="█   █ █ ▀▄█ █   █      █   █   █ █   █ █   █";
                        endText[4]="▀   ▀ ▀  ▀▀ ▀▀▀▀       ▀    ▀▀▀   ▀▀▀  ▀   ▀";
                        break;
                    case 2:
                        endText[0]=" ▄▄▄▄  ▄▄▄   ▄▄▄  ▄▄▄▄  ▄▄▄▄▄    ▄▄▄▄▄  ▄▄▄▄";
                        endText[1]="█     █   █ █   █ █   █ █          █   █    ";
                        endText[2]=" ▀▀▀▄ █     █   █ █▀▀▀▄ █▀▀        █    ▀▀▀▄";
                        endText[3]="    █ █   █ █   █ █   █ █          █       █";
                        endText[4]="▀▀▀▀   ▀▀▀   ▀▀▀  ▀   ▀ ▀▀▀▀▀    ▀▀▀▀▀ ▀▀▀▀ ";
                        break;
                    case 3:
                        endText[0]=bigNumText(pts,(short)0);
                        endText[1]=bigNumText(pts,(short)1);
                        endText[2]=bigNumText(pts,(short)2);
                        endText[3]=bigNumText(pts,(short)3);
                        endText[4]=bigNumText(pts,(short)4);
                        break;
                    case 4:
                        if (pts < 10) {
                            endText[0]="▄▄▄▄▄ ▄▄▄▄▄ ▄  ▄▄▄▄     ▄▄▄  ▄   ▄  ";
                            endText[1]="  █     █  ▄▀ █        █   █ █ ▄▀   ";
                            endText[2]="  █     █      ▀▀▀▄    █   █ ██     ";
                            endText[3]="  █     █         █    █   █ █ ▀▄   ";
                            endText[4]="▀▀▀▀▀   ▀     ▀▀▀▀      ▀▀▀  ▀   ▀ ▀";
                        } else if (pts < 40) {
                            endText[0]="▄▄  ▄ ▄▄▄▄▄  ▄▄▄  ▄▄▄▄▄    ▄▄▄▄▄  ▄▄▄  ▄▄▄▄ ";
                            endText[1]="█▀▄ █   █   █   █ █           █  █   █ █   █";
                            endText[2]="█ █ █   █   █     █▀▀         █  █   █ █▀▀▀▄";
                            endText[3]="█ ▀▄█   █   █   █ █        █  █  █   █ █   █";
                            endText[4]="▀  ▀▀ ▀▀▀▀▀  ▀▀▀  ▀▀▀▀▀     ▀▀    ▀▀▀  ▀▀▀▀ ";
                        } else if (pts < 60) {
                            endText[0]="▄     ▄ ▄   ▄  ▄▄▄  ▄▄▄▄▄  ▄▄▄  ▄";
                            endText[1]="█  █  █ █   █ █   █   █   ▀   █ █";
                            endText[2]="█  █  █ █▀▀▀█ █▀▀▀█   █     ▄▀  █";
                            endText[3]="█  █  █ █   █ █   █   █     ▀   ▀";
                            endText[4]=" ▀▀ ▀▀  ▀   ▀ ▀   ▀   ▀     ▀   ▀";
                        } else if (pts < 100) {
                            endText[0]="▄   ▄  ▄▄▄  ▄     ▄  ▄▄▄  ▄  ▄▄▄  ▄  ▄▄▄  ▄ ";
                            endText[1]="█   █ █   █ █  █  █ ▀   █ █ ▀   █ █ ▀   █ █ ";
                            endText[2]="█▀▀▀█ █   █ █  █  █   ▄▀  █   ▄▀  █   ▄▀  █ ";
                            endText[3]="█   █ █   █ █  █  █   ▀   ▀   ▀   ▀   ▀   ▀ ";
                            endText[4]="▀   ▀  ▀▀▀   ▀▀ ▀▀    ▀   ▀   ▀   ▀   ▀   ▀ ";
                        } else {
                            endText[0]="▄   ▄  ▄▄▄  ▄     ▄    ▄▄▄▄▄ ▄▄▄▄▄  ▄▄▄  ▄ ▄";
                            endText[1]="█   █ █   █ █  █  █      █   █     ▀   █ █ █";
                            endText[2]="█▀▀▀█ █   █ █  █  █      █   █▀▀     ▄▀  █ █";
                            endText[3]="█   █ █   █ █  █  █      █   █       ▀   ▀ ▀";
                            endText[4]="▀   ▀  ▀▀▀   ▀▀ ▀▀       ▀   ▀       ▀   ▀ ▀";
                        }
                        break;
                }

                //CLEAR FRAME
                System.out.print("\033[H\033[2J");
                System.out.flush();

                //fill screen with border
                if (wordpart == 0 || wordpart == 1 || wordpart == 4) {
                    System.out.println(border[1]+"\n"+border[2]+"\n"+border[2]+"\n"+border[2]+"\n"+border[2]+"\n"+border[2]+"\n"+border[0]);
                } else
                    System.out.println(border[1]+"\n"+customBorder+gaps+"\n"+customBorder+gaps+"\n"+customBorder+gaps+"\n"+customBorder+gaps+"\n"+customBorder+gaps+"\n"+border[0]);

                //pause on frame
                try {
                    Thread.sleep(200);
                } catch(InterruptedException e) {}

                //fade in loop
                if (wordpart == 0 || wordpart == 1 || wordpart == 4) for (int i=0;i<5;i++) {
                    //CLEAR FRAME
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    //CALCULATE SPACES FOR WORDS:
                    endSpaces = "";
                    for (int e=0; e < (screensizex - endText[0].length()) / 2 - 1; e++) {
                        endSpaces += endFaders[i];
                    }

                    //print top border
                    System.out.println(border[1]);

                    //print words (secondary thing in case the screensizex is odd)
                    if (screensizex % 2 == 0) {
                        System.out.println(customBorder+endSpaces+fillSpace(endText[0],endFaders[i])+endSpaces+customBorder);
                        System.out.println(customBorder+endSpaces+fillSpace(endText[1],endFaders[i])+endSpaces+customBorder);
                        System.out.println(customBorder+endSpaces+fillSpace(endText[2],endFaders[i])+endSpaces+customBorder);
                        System.out.println(customBorder+endSpaces+fillSpace(endText[3],endFaders[i])+endSpaces+customBorder);
                        System.out.println(customBorder+endSpaces+fillSpace(endText[4],endFaders[i])+endSpaces+customBorder);
                    } else {
                        System.out.println(customBorder+endSpaces+fillSpace(endText[0],endFaders[i])+endSpaces+endFaders[i]+customBorder);
                        System.out.println(customBorder+endSpaces+fillSpace(endText[1],endFaders[i])+endSpaces+endFaders[i]+customBorder);
                        System.out.println(customBorder+endSpaces+fillSpace(endText[2],endFaders[i])+endSpaces+endFaders[i]+customBorder);
                        System.out.println(customBorder+endSpaces+fillSpace(endText[3],endFaders[i])+endSpaces+endFaders[i]+customBorder);
                        System.out.println(customBorder+endSpaces+fillSpace(endText[4],endFaders[i])+endSpaces+endFaders[i]+customBorder);
                    }


                    //print bottom border
                    System.out.println(border[0]);

                    //pause on frame
                    try {
                        Thread.sleep(50);
                    } catch(InterruptedException e) {}
                } else {
                    endSpaces = "";
                    for (int e=0; e < (screensizex - endText[0].length()) / 2 - 1; e++) {
                        endSpaces += endFaders[4];
                    }
                    for (short i=0;i<3;i++) {
                        //CLEAR FRAME
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        //print top border
                        System.out.println(border[1]);

                        //print words (secondary thing in case the screensizex is odd)
                        if (screensizex % 2 == 0) {
                            System.out.println(customBorder+endSpaces+dottify(endText[0],i)+endSpaces+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[1],i)+endSpaces+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[2],i)+endSpaces+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[3],i)+endSpaces+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[4],i)+endSpaces+customBorder);
                        } else {
                            System.out.println(customBorder+endSpaces+dottify(endText[0],i)+endSpaces+endFaders[4]+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[1],i)+endSpaces+endFaders[4]+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[2],i)+endSpaces+endFaders[4]+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[3],i)+endSpaces+endFaders[4]+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[4],i)+endSpaces+endFaders[4]+customBorder);
                        }


                        //print bottom border
                        System.out.println(border[0]);

                        //pause on frame
                        try {
                            Thread.sleep(50);
                        } catch(InterruptedException e) {}
                    }
                }

                if (wordpart == 4) {
                    try {
                        Thread.sleep(2000);
                    } catch(InterruptedException e) {}
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException e) {}
                }

                //fade out loop (only if wordpart = 0,3)
                if (wordpart == 0 || wordpart == 3) {
                    for (int i=4;i>-1;i--) {
                        //CLEAR FRAME
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        //CALCULATE SPACES FOR WORDS:
                        endSpaces = "";
                        for (int e=0; e < (screensizex - endText[0].length()) / 2 - 1; e++) {
                            endSpaces += endFaders[i];
                        }

                        //print top border
                        System.out.println(border[1]);

                        //print words (secondary thing in case the screensizex is odd)
                        switch (screensizex % 2) {
                            case 0:
                                System.out.println(customBorder+endSpaces+fillSpace(endText[0],endFaders[i])+endSpaces+customBorder);
                                System.out.println(customBorder+endSpaces+fillSpace(endText[1],endFaders[i])+endSpaces+customBorder);
                                System.out.println(customBorder+endSpaces+fillSpace(endText[2],endFaders[i])+endSpaces+customBorder);
                                System.out.println(customBorder+endSpaces+fillSpace(endText[3],endFaders[i])+endSpaces+customBorder);
                                System.out.println(customBorder+endSpaces+fillSpace(endText[4],endFaders[i])+endSpaces+customBorder);
                                break;
                            case 1:
                                System.out.println(customBorder+endSpaces+fillSpace(endText[0],endFaders[i])+endSpaces+endFaders[i]+customBorder);
                                System.out.println(customBorder+endSpaces+fillSpace(endText[1],endFaders[i])+endSpaces+endFaders[i]+customBorder);
                                System.out.println(customBorder+endSpaces+fillSpace(endText[2],endFaders[i])+endSpaces+endFaders[i]+customBorder);
                                System.out.println(customBorder+endSpaces+fillSpace(endText[3],endFaders[i])+endSpaces+endFaders[i]+customBorder);
                                System.out.println(customBorder+endSpaces+fillSpace(endText[4],endFaders[i])+endSpaces+endFaders[i]+customBorder);
                                break;
                        }

                        //print bottom border
                        System.out.println(border[0]);

                        //pause on frame
                        try {
                            Thread.sleep(50);
                        } catch(InterruptedException e) {}
                    }
                } else {
                    endSpaces = "";
                    for (int e=0; e < (screensizex - endText[0].length()) / 2 - 1; e++) {
                        endSpaces += endFaders[4];
                    }
                    for (short i=2;i>-1;i--) {
                        //CLEAR FRAME
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        //print top border
                        System.out.println(border[1]);

                        //print words (secondary thing in case the screensizex is odd)
                        if (screensizex % 2 == 0) {
                            System.out.println(customBorder+endSpaces+dottify(endText[0],i)+endSpaces+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[1],i)+endSpaces+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[2],i)+endSpaces+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[3],i)+endSpaces+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[4],i)+endSpaces+customBorder);
                        } else {
                            System.out.println(customBorder+endSpaces+dottify(endText[0],i)+endSpaces+endFaders[4]+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[1],i)+endSpaces+endFaders[4]+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[2],i)+endSpaces+endFaders[4]+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[3],i)+endSpaces+endFaders[4]+customBorder);
                            System.out.println(customBorder+endSpaces+dottify(endText[4],i)+endSpaces+endFaders[4]+customBorder);
                        }


                        //print bottom border
                        System.out.println(border[0]);

                        //pause on frame
                        try {
                            Thread.sleep(50);
                        } catch(InterruptedException e) {}
                    }
                }
            }

            //CLEAR FRAME
            System.out.print("\033[H\033[2J");
            System.out.flush();

            //print empty box
            System.out.println(border[1]+"\n"+customBorder+gaps+"\n"+customBorder+gaps+"\n"+customBorder+gaps+"\n"+customBorder+gaps+"\n"+customBorder+gaps+"\n"+border[0]);

            //wait before restart
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {}

            /*****RESTART BUTTON*****/
            //CLEAR FRAME
            System.out.print("\033[H\033[2J");
            System.out.flush();

            //calculate end spaces
            endSpaces = "";
            for (int e=0; e < (screensizex - 25) / 2 - 1; e++) {
                endSpaces += " ";
            }

            //print thingy that tells what to do
            System.out.println(border[1]);
            System.out.println(customBorder+gaps);
            System.out.println(customBorder+gaps);
            switch (screensizex % 2) {
                case 0: System.out.println(customBorder+endSpaces+"Press any key to restart. "+endSpaces+customBorder); break;
                case 1: System.out.println(customBorder+endSpaces+"Press any key to restart."+endSpaces+customBorder); break;
            }
            System.out.println(customBorder+gaps);
            System.out.println(customBorder+gaps);
            System.out.println(border[0]);

            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {}

            //wait for key to be pressed
            while (!keypressed) try { Thread.sleep(50); } catch(InterruptedException e) {}
        }
    } // end main

    private static String fillSpace(String input, String replacer) {
        String result = "";
        for (int i=0;i<input.length();i++) {
            if (input.substring(i,i+1).equals(" ")) {
                result += replacer;
            } else result += input.substring(i,i+1);
        }
        return result;
    }

    private static String dottify(String input, short type) {
        if (type == 0) {
            String result = "";
            for (int i=0;i<input.length();i++) {
                if (input.substring(i,i+1).equals(" ")) {
                    result += input.substring(i,i+1);
                } else result += "·";
            }
            return result;
        } else if (type == 1) {
            String result = "";
            for (int i=0;i<input.length();i++) {
                if (input.substring(i,i+1).equals(" ")) {
                    result += input.substring(i,i+1);
                } else result += "•";
            }
            return result;
        } else
        return input;
    }

    private static String bigNumText(int input, short row) { //row 0=top, 1=topmid, 2=mid
        String output = "";
        short intSize = (short)Integer.toString(input).length();
        for (int i=0;i<intSize;i++) {
            switch ((int)((input % Math.pow(10,intSize-i))/(Math.pow(10,intSize-i-1)))) {
                case 0:
                    switch (row) {
                        case 0: output += " ▄▄▄  "; break;
                        case 1: output += "█   █ "; break;
                        case 2: output += "█   █ "; break;
                        case 3: output += "█   █ "; break;
                        case 4: output += " ▀▀▀  "; break;
                    }
                    break;
                case 1:
                    switch (row) {
                        case 0: output += "  ▄   "; break;
                        case 1: output += "▄▀█   "; break;
                        case 2: output += "  █   "; break;
                        case 3: output += "  █   "; break;
                        case 4: output += "▀▀▀▀▀ "; break;
                    }
                    break;
                case 2:
                    switch (row) {
                        case 0: output += " ▄▄▄  "; break;
                        case 1: output += "▀   █ "; break;
                        case 2: output += "  ▄▀  "; break;
                        case 3: output += "▄▀    "; break;
                        case 4: output += "▀▀▀▀▀ "; break;
                    }
                    break;
                case 3:
                    switch (row) {
                        case 0: output += " ▄▄▄  "; break;
                        case 1: output += "▀   █ "; break;
                        case 2: output += "  ▀▀▄ "; break;
                        case 3: output += "▄   █ "; break;
                        case 4: output += " ▀▀▀  "; break;
                    }
                    break;
                case 4:
                    switch (row) {
                        case 0: output += "    ▄ "; break;
                        case 1: output += "  ▄▀█ "; break;
                        case 2: output += "▄▀  █ "; break;
                        case 3: output += "▀▀▀▀█ "; break;
                        case 4: output += "    ▀ "; break;
                    }
                    break;
                case 5:
                    switch (row) {
                        case 0: output += "▄▄▄▄▄ "; break;
                        case 1: output += "█     "; break;
                        case 2: output += "▀▀▀▀▄ "; break;
                        case 3: output += "▄   █ "; break;
                        case 4: output += " ▀▀▀  "; break;
                    }
                    break;
                case 6:
                    switch (row) {
                        case 0: output += " ▄▄▄  "; break;
                        case 1: output += "█   ▀ "; break;
                        case 2: output += "█▀▀▀▄ "; break;
                        case 3: output += "█   █ "; break;
                        case 4: output += " ▀▀▀  "; break;
                    }
                    break;
                case 7:
                    switch (row) {
                        case 0: output += "▄▄▄▄▄ "; break;
                        case 1: output += "   ▄▀ "; break;
                        case 2: output += "  ▄▀  "; break;
                        case 3: output += "  █   "; break;
                        case 4: output += "  ▀   "; break;
                    }
                    break;
                case 8:
                    switch (row) {
                        case 0: output += " ▄▄▄  "; break;
                        case 1: output += "█   █ "; break;
                        case 2: output += "▄▀▀▀▄ "; break;
                        case 3: output += "█   █ "; break;
                        case 4: output += " ▀▀▀  "; break;
                    }
                    break;
                case 9:
                    switch (row) {
                        case 0: output += " ▄▄▄  "; break;
                        case 1: output += "█   █ "; break;
                        case 2: output += " ▀▀▀█ "; break;
                        case 3: output += "    █ "; break;
                        case 4: output += "    ▀ "; break;
                    }
                    break;
            }
        }
        return output;
    }// end bigNumText



	ConsoleRunner() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(1366,768);

		this.setLayout(null);

		this.addKeyListener(this);




		label = new JLabel();

		label.setBounds(0, 0, 100, 100);

		//label.setIcon(icon);

		//label.setBackground(Color.red);

		//label.setOpaque(true);

		this.getContentPane().setBackground(Color.black);

		this.add(label);

		this.setVisible(true);

	} //end object



	@Override

	public void keyTyped(KeyEvent e) {}



	@Override

	public void keyPressed(KeyEvent e) {

		//keyPressed = Invoked when a physical key is pressed down. Uses KeyCode, int output
        keypressed = true;

		switch(e.getKeyCode()) {

            case 32:
                upPress = true;
                break;

            case 38:
                upPress = true;
                break;

            case 87:
                upPress = true;
                break;

            case 40:
                dwnPress = true;
                break;

            case 83:
                dwnPress = true;
                break;
	    }

	}



	@Override

	public void keyReleased(KeyEvent e) {
        keypressed = false;

		switch(e.getKeyCode()) {

            case 32:
                upPress = false;
                break;

            case 38:
                upPress = false;
                break;

            case 87:
                upPress = false;
                break;

            case 40:
                dwnPress = false;
                break;

            case 83:
                dwnPress = false;
                break;
	    }

	}// end keyReleased
}
