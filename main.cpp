#include <iostream>
#include <windows.h>
#include <winuser.h>
#include <thread>
#include <chrono>
#include<cmath>

using namespace std;

string fillSpace(string input, string replacer) {
    string result = "";
    for (int i=0;i<input.length();i++) {
        if (input[i] == ' ') {
            result += replacer;
        } else result += input[i];
    }
    return result;
}

string dottify(string input, short type) {
    if (type == 0) {
        string result = "";
        for (int i=0;i<input.length();i++) {
            if (input[i] == ' ') {
                result += input[i];
            } else result += ".";
        }
        return result;
    } else if (type == 1) {
        string result = "";
        for (int i=0;i<input.length();i++) {
            if (input[i] == ' ') {
                result += input[i];
            } else result += ",";
        }
        return result;
    } else
    return input;
}

string bigNumText(int input, short row) { //row 0=top, 1=topmid, 2=mid
    string output = "";
    short intSize = (short)to_string(input).length();
    for (int i=0;i<intSize;i++) {
        switch ((int)((input % (int)(pow(10,intSize-i)))/(int)(pow(10,intSize-i-1)))) {
            case 0:
                switch (row) {
                    case 0: output += " ___  "; break;
                    case 1: output += "/   \\ "; break;
                    case 2: output += "|   | "; break;
                    case 3: output += "\\   / "; break;
                    case 4: output += " ```  "; break;
                }
                break;
            case 1:
                switch (row) {
                    case 0: output += " .  "; break;
                    case 1: output += "/|  "; break;
                    case 2: output += " |  "; break;
                    case 3: output += " |  "; break;
                    case 4: output += "``` "; break;
                }
                break;
            case 2:
                switch (row) {
                    case 0: output += " __  "; break;
                    case 1: output += "(  ) "; break;
                    case 2: output += "  /  "; break;
                    case 3: output += " /   "; break;
                    case 4: output += "```` "; break;
                }
                break;
            case 3:
                switch (row) {
                    case 0: output += " ___  "; break;
                    case 1: output += "    ) "; break;
                    case 2: output += " --<  "; break;
                    case 3: output += "    ) "; break;
                    case 4: output += " ```  "; break;
                }
                break;
            case 4:
                switch (row) {
                    case 0: output += "   . "; break;
                    case 1: output += "  /| "; break;
                    case 2: output += " / | "; break;
                    case 3: output += "```| "; break;
                    case 4: output += "   ` "; break;
                }
                break;
            case 5:
                switch (row) {
                    case 0: output += " ___  "; break;
                    case 1: output += "|     "; break;
                    case 2: output += " ---  "; break;
                    case 3: output += ".   ) "; break;
                    case 4: output += " ```  "; break;
                }
                break;
            case 6:
                switch (row) {
                    case 0: output += " ___  "; break;
                    case 1: output += "/     "; break;
                    case 2: output += "|---, "; break;
                    case 3: output += "\\   / "; break;
                    case 4: output += " ```  "; break;
                }
                break;
            case 7:
                switch (row) {
                    case 0: output += "_____ "; break;
                    case 1: output += "    / "; break;
                    case 2: output += "   /  "; break;
                    case 3: output += "  /   "; break;
                    case 4: output += " '    "; break;
                }
                break;
            case 8:
                switch (row) {
                    case 0: output += " ___  "; break;
                    case 1: output += "(   ) "; break;
                    case 2: output += " ---  "; break;
                    case 3: output += "(   ) "; break;
                    case 4: output += " ```  "; break;
                }
                break;
            case 9:
                switch (row) {
                    case 0: output += " ___. "; break;
                    case 1: output += "(   | "; break;
                    case 2: output += " ---| "; break;
                    case 3: output += "    | "; break;
                    case 4: output += "    ` "; break;
                }
                break;
        }
    }
    return output;
}// end bigNumText

int main()
{
    /**if (GetKeyState(VK_UP) & 0x8000 ||GetKeyState(VK_SPACE) & 0x8000 || GetKeyState('W') & 0x8000)
    {
        cout << "e";
        this_thread::sleep_for (chrono::milliseconds(50));
    } else {
        system("CLS");
    }*/



    //JAVA CODE
    //DEFINE DOMAIN OF GAME AND SURROUNDINGS
    int screensizex = 70;//MUST BE OVER 46 (RECOMMENDED OVER 48)
    short gameSpeed = 3;//MUST BE OVER 0
    //(speed 4 recommended when screensizex > 60)
    //(speed 3 recommended when screensizex <= 60)
    //idk u can slow it down/speed it up further if u want i guess

    string customBorder = "|";
    string customTop = "-";
    string customBottom = "-";

    string customEnemy = "H";
    string border[] = {customTop,customBottom, customBorder};
    string gaps = " ";
    string playerCharacter = "U";

    //floor and sky should be same size, clouds probably won't display in order because im lazy
    //in this case its 39 characters
    string customFloor = "`''\"`\"\"`*\"*\"\"`\"''\"'*`\"'*``^\"\"''''*\"\"``\"";
    string customSky =    "  ***                  ** ***  **      ";
    short skyR = 0;
    string floor = "";
    string sky = "";
    short floorspot = 0;

    //set the floor
    for (int i=0;floor.length() < screensizex-2;i++) {
        //double up if i have to
        floor += customFloor[i%customFloor.length()];
        sky += customSky[i%customFloor.length()];
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
    string endText[] = {"_   _  ___  _   _    _      ___   ____ _____",
                        "█   █ █   █ █   █    █     █   █ █     █    ",
                        " █ █  █   █ █   █    █     █   █  ---_ █--  ",
                        "  █   █   █ █   █    █     █   █     █ █    ",
                        "  -    ---   ---     -----  ---  ----  -----"};
    string endSpaces = "";
    string endFaders[] = {"|","X","x","."," "};

    //DEFINE GAMEPLAY VARIABLES
    int x = 0;
    int y = 0;
    short realY = 0;
    short jumprecharge = 0;
    string direction = "";
    x++;
    bool failed = false;
    int pts = 0;
    bool keypressed = false;

    int badthing = (int)(rand()%5)+100;
    bool badtype[] = {false, true, true};
                       //0 bot, 1 mid, 2 top

    string upframe = "     ";
    string midframe = "     ";
    string botframe = "     ";


    //egg

    string groundEgg[] = {"  ****         * **  *****  **amongus**",
                          "***  This game was made by Jus Liu  ***"};
    while (true) {
        //reset variables
        failed = false;
        x = 0;
        y = 0;
        realY = 0;
        badthing = (int)(rand()%5)+100;
        pts = 0;


        while (!failed) {
            //checkifkeypressed
            if (GetKeyState(VK_UP) & 0x8000 ||GetKeyState(VK_SPACE) & 0x8000 || GetKeyState('W') & 0x8000) {
                keypressed = true;
            } else keypressed = false;

            //CLEAR BUFFER
            system("CLS");

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
            for (int i=0;i<gameSpeed;i++) {
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
            }

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
            for (int i=0;i<gameSpeed;i++) {
                x++;
                if (badthing-5==x) {
                    if (badtype[y]) { //2 top, 1 mid, 0 bot
                        x--;
                        failed = true;
                    } else pts++;
                }
            }

            //if obstacle already past, make new one
            if (badthing < x) {
                //SET BADTYPE (use badthing variable to store separate info to be applied later)
                badthing = (int)((rand()%4)+0.7);
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
                badthing = (int)(rand()%20)+100;
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
            for (int i=0;i<gameSpeed;i++) {
                if (floorspot != 0 && floorspot % (10*customFloor.length()) == 0) {
                    floorspot = (short)(rand()%30);
                    if (floorspot == 17 && skyR != 32 && skyR != 12) skyR = 32;
                    else if (floorspot < 4 && skyR != 32 && skyR != 12) skyR = 12;
                    else skyR = 0;

                    floorspot = 0;
                } else floorspot++;
                floor = floor.substr(1,floor.length()) + customFloor[floorspot%customFloor.length()];
                if (floorspot % 10 == 2) {
                    if (skyR == 12)
                        sky = sky.substr(1,sky.length()) + groundEgg[0][((floorspot)/10)%(groundEgg[0].length())];
                    else if (skyR == 32)
                        sky = sky.substr(1,sky.length()) + groundEgg[1][((floorspot)/10)%(groundEgg[1].length())];
                    else
                        sky = sky.substr(1,sky.length()) + customSky[((floorspot)/10)%(customSky.length())];
                }
            }

            //DRAW EVERYTHING
            cout << border[1] << endl <<
                    customBorder + sky + customBorder << endl <<
                    customBorder + upframe << endl <<
                    customBorder + midframe << endl <<
                    customBorder + botframe << endl <<
                    customBorder + floor + customBorder << endl <<
                    border[0] << endl << " points: " << pts << "  ";

            //debug
            // System.out.println(badtype[2] + " " + badtype[1] + " " + badtype[0]);
            // System.out.println(keypressed);

            //THREAD SLEEP
            this_thread::sleep_for (chrono::milliseconds(10));
        }
        for (int i=0;i<6;i++) {
            system("CLS");

            cout << border[1] << endl;
            for (int e=0; e<=i && e<5; e++) {
                cout << border[2] << endl;
            }
            if (i == 0) {
                cout << customBorder + upframe << endl <<
                customBorder + midframe << endl <<
                customBorder + botframe << endl <<
                customBorder + floor + customBorder << endl;
            } else if (i == 1) {
                cout << customBorder + midframe << endl <<
                customBorder + botframe << endl <<
                customBorder + floor + customBorder << endl;
            } else if (i == 2) {
                cout << customBorder + botframe << endl <<
                customBorder + floor + customBorder << endl;
            } else if (i == 3) {
                cout << customBorder + floor + customBorder << endl;
            }
            //always print the bottom
            cout << border[0] << endl;

            this_thread::sleep_for (chrono::milliseconds(100));
        }

        ////SAY END PART
        for (int wordpart=0;wordpart<5;wordpart++) {
            switch (wordpart) {
                case 0:
                    endText[0]=".   .  ___  .   .    .      ___   ___  _____";
                    endText[1]="|   | /   \\ |   |    |     /   \\ (     |    ";
                    endText[2]=" \\ /  |   | |   |    |     |   |  ---  |--  ";
                    endText[3]="  |   \\   / \\   /    |     \\   /     ) |    ";
                    endText[4]="  '    ```   ```     `````  ```  ````  `````";
                    break;
                case 1:
                    endText[0]=" ___  .   . ____     .   .  ___  .   . ____ ";
                    endText[1]="/   \\ |\\  | |   \\    |   | /   \\ |   | |   )";
                    endText[2]="|---| | \\ | |   |     \\ /  |   | |   | |--- ";
                    endText[3]="|   | |  \\| |   /      |   \\   / \\   / |   \\";
                    endText[4]="'   ' '   ' ````       '    ```   ```  '   '";
                    break;
                case 2:
                    endText[0]=" ___   ___   ___  ____  _____    _____  ___ ";
                    endText[1]="(     /   \\ /   \\ |   ) |          |   (    ";
                    endText[2]=" ---  |     |   | |---  |--        |    --- ";
                    endText[3]="    ) \\   / \\   / |   \\ |          |       )";
                    endText[4]=" ```   ```   ```  '   ' `````    ````` ```` ";
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
                        endText[0]=" _____ _____ o  ___      ___  .  .  ";
                        endText[1]="   |     |   / (        /   \\ | /   ";
                        endText[2]="   |     |      ---     |   | |<    ";
                        endText[3]="   |     |         )    \\   / | \\  o";
                        endText[4]=" `````   '      ```      ```  '  '  ";
                    } else if (pts < 40) {
                        endText[0]=".   . _____  ___  _____    _____  ___  ____ ";
                        endText[1]="|\\  |   |   /   \\ |           |  /   \\ |   )";
                        endText[2]="| \\ |   |   |     |--         |  |   | |--< ";
                        endText[3]="|  \\|   |   \\   / |        \\  |  \\   / |   )";
                        endText[4]="'   ' `````  ```  `````     ``    ```  ```` ";
                    } else if (pts < 60) {
                        endText[0]=".     . .   _  ___  _____  ___  .";
                        endText[1]="|  |  | |   | /   \\   |   /   | |";
                        endText[2]="|  |  | |---| |---|   |      /  |";
                        endText[3]="\\  |  / |   | |   |   |     '    ";
                        endText[4]=" `` ``  '   ' '   '   '     '   '";
                    } else if (pts < 100) {
                        endText[0]=".   .  ___  .     .  ___  .  ___  .  ___  . ";
                        endText[1]="|   | /   \\ |  |  | /   | | /   | | /   | | ";
                        endText[2]="|---| |   | |  |  |    /  |    /  |    /  | ";
                        endText[3]="|   | \\   / \\  |  /   '   '   '   '   '   ' ";
                        endText[4]="'   '  ```   `` ``    '   '   '   '   '   ' ";
                    } else {
                        endText[0]=".   .  ___  .     .    _____ _____  ___  . .";
                        endText[1]="|   | /   \ |  |  |      |   |     /   | | |";
                        endText[2]="|---| |   | |  |  |      |   |--      /  | |";
                        endText[3]="|   | \   / \  |  /      |   |       '   ' '";
                        endText[4]="'   '  ```   `` ``       '   '       '   ' '";
                    }
                    break;
            }

            //CLEAR FRAME
            system("CLS");

            //fill screen with border
            if (wordpart == 0 || wordpart == 1 || wordpart == 4) {
                cout<<border[1]<<endl<<border[2]<<endl<<border[2]<<endl<<border[2]<<endl<<border[2]<<endl<<border[2]<<endl<<border[0]<<endl;
            } else
                cout<<border[1]<<endl<<customBorder+gaps<<endl<<customBorder+gaps<<endl<<customBorder+gaps<<endl<<customBorder+gaps<<endl<<customBorder+gaps<<endl<<border[0]<<endl;

            //pause on frame
            this_thread::sleep_for (chrono::milliseconds(200));

            //fade in loop
            if (wordpart == 0 || wordpart == 1 || wordpart == 4) for (int i=0;i<5;i++) {
                //CLEAR FRAME
                system("CLS");

                //CALCULATE SPACES FOR WORDS:
                endSpaces = "";
                for (int e=0; e < (screensizex - endText[0].length()) / 2 - 1; e++) {
                    endSpaces += endFaders[i];
                }

                //print top border
                cout<<border[1]<<endl;

                //print words (secondary thing in case the screensizex is odd)
                if (screensizex % 2 == 0) {
                    cout<<customBorder+endSpaces+fillSpace(endText[0],endFaders[i])+endSpaces+customBorder<<endl<<
                        customBorder+endSpaces+fillSpace(endText[1],endFaders[i])+endSpaces+customBorder<<endl<<
                        customBorder+endSpaces+fillSpace(endText[2],endFaders[i])+endSpaces+customBorder<<endl<<
                        customBorder+endSpaces+fillSpace(endText[3],endFaders[i])+endSpaces+customBorder<<endl<<
                        customBorder+endSpaces+fillSpace(endText[4],endFaders[i])+endSpaces+customBorder<<endl;
                } else {
                    cout<<customBorder+endSpaces+fillSpace(endText[0],endFaders[i])+endSpaces+endFaders[i]+customBorder<<endl<<
                        customBorder+endSpaces+fillSpace(endText[1],endFaders[i])+endSpaces+endFaders[i]+customBorder<<endl<<
                        customBorder+endSpaces+fillSpace(endText[2],endFaders[i])+endSpaces+endFaders[i]+customBorder<<endl<<
                        customBorder+endSpaces+fillSpace(endText[3],endFaders[i])+endSpaces+endFaders[i]+customBorder<<endl<<
                        customBorder+endSpaces+fillSpace(endText[4],endFaders[i])+endSpaces+endFaders[i]+customBorder<<endl;
                }


                //print bottom border
                cout<<border[0]<<endl;

                //pause on frame
                this_thread::sleep_for (chrono::milliseconds(100));
            } else {
                endSpaces = "";
                for (int e=0; e < (screensizex - endText[0].length()) / 2 - 1; e++) {
                    endSpaces += endFaders[4];
                }
                for (short i=0;i<3;i++) {
                    //CLEAR FRAME
                    system("CLS");

                    //print top border
                    cout<<border[1]<<endl;

                    //print words (secondary thing in case the screensizex is odd)
                    if (screensizex % 2 == 0) {
                        cout<<customBorder+endSpaces+dottify(endText[0],i)+endSpaces+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[1],i)+endSpaces+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[2],i)+endSpaces+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[3],i)+endSpaces+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[4],i)+endSpaces+customBorder<<endl;
                    } else {
                        cout<<customBorder+endSpaces+dottify(endText[0],i)+endSpaces+endFaders[4]+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[1],i)+endSpaces+endFaders[4]+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[2],i)+endSpaces+endFaders[4]+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[3],i)+endSpaces+endFaders[4]+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[4],i)+endSpaces+endFaders[4]+customBorder<<endl;
                    }


                    //print bottom border
                    cout<<border[0]<<endl;

                    //pause on frame
                    this_thread::sleep_for (chrono::milliseconds(50));
                }
            }

            if (wordpart == 4) {
                this_thread::sleep_for (chrono::seconds(2));
            } else {
                this_thread::sleep_for (chrono::seconds(1));
            }

            //fade out loop (only if wordpart = 0,3)
            if (wordpart == 0 || wordpart == 3) {
                for (int i=4;i>-1;i--) {
                    //CLEAR FRAME
                    system("CLS");

                    //CALCULATE SPACES FOR WORDS:
                    endSpaces = "";
                    for (int e=0; e < (screensizex - endText[0].length()) / 2 - 1; e++) {
                        endSpaces += endFaders[i];
                    }

                    //print top border
                    cout<<border[1]<<endl;

                    //print words (secondary thing in case the screensizex is odd)
                    switch (screensizex % 2) {
                        case 0:
                            cout<<customBorder+endSpaces+fillSpace(endText[0],endFaders[i])+endSpaces+customBorder<<endl<<
                                customBorder+endSpaces+fillSpace(endText[1],endFaders[i])+endSpaces+customBorder<<endl<<
                                customBorder+endSpaces+fillSpace(endText[2],endFaders[i])+endSpaces+customBorder<<endl<<
                                customBorder+endSpaces+fillSpace(endText[3],endFaders[i])+endSpaces+customBorder<<endl<<
                                customBorder+endSpaces+fillSpace(endText[4],endFaders[i])+endSpaces+customBorder<<endl;
                            break;
                        case 1:
                            cout<<customBorder+endSpaces+fillSpace(endText[0],endFaders[i])+endSpaces+endFaders[i]+customBorder<<endl<<
                                customBorder+endSpaces+fillSpace(endText[1],endFaders[i])+endSpaces+endFaders[i]+customBorder<<endl<<
                                customBorder+endSpaces+fillSpace(endText[2],endFaders[i])+endSpaces+endFaders[i]+customBorder<<endl<<
                                customBorder+endSpaces+fillSpace(endText[3],endFaders[i])+endSpaces+endFaders[i]+customBorder<<endl<<
                                customBorder+endSpaces+fillSpace(endText[4],endFaders[i])+endSpaces+endFaders[i]+customBorder<<endl;
                            break;
                    }

                    //print bottom border
                    cout<<border[0]<<endl;

                    //pause on frame
                    this_thread::sleep_for (chrono::milliseconds(50));
                }
            } else {
                endSpaces = "";
                for (int e=0; e < (screensizex - endText[0].length()) / 2 - 1; e++) {
                    endSpaces += endFaders[4];
                }
                for (short i=2;i>-1;i--) {
                    //CLEAR FRAME
                    system("CLS");

                    //print top border
                    cout<<border[1]<<endl;

                    //print words (secondary thing in case the screensizex is odd)
                    if (screensizex % 2 == 0) {
                        cout<<customBorder+endSpaces+dottify(endText[0],i)+endSpaces+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[1],i)+endSpaces+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[2],i)+endSpaces+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[3],i)+endSpaces+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[4],i)+endSpaces+customBorder<<endl;
                    } else {
                        cout<<customBorder+endSpaces+dottify(endText[0],i)+endSpaces+endFaders[4]+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[1],i)+endSpaces+endFaders[4]+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[2],i)+endSpaces+endFaders[4]+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[3],i)+endSpaces+endFaders[4]+customBorder<<endl<<
                        customBorder+endSpaces+dottify(endText[4],i)+endSpaces+endFaders[4]+customBorder<<endl;
                    }


                    //print bottom border
                    cout<<border[0]<<endl;

                    //pause on frame
                    this_thread::sleep_for (chrono::milliseconds(50));
                }
            }
        }

        //CLEAR FRAME
        system("CLS");

        //print empty box
        cout<<border[1]<<endl<<customBorder+gaps<<endl<<customBorder+gaps<<endl<<customBorder+gaps<<endl<<customBorder+gaps<<endl<<customBorder+gaps<<endl<<border[0]<<endl;

        //wait before restart
        this_thread::sleep_for (chrono::milliseconds(100));

        /*****RESTART BUTTON*****/
        //CLEAR FRAME
        system("CLS");

        //calculate end spaces
        endSpaces = "";
        for (int e=0; e < (screensizex - 25) / 2 - 1; e++) {
            endSpaces += " ";
        }

        //print thingy that tells what to do
        cout<<border[1]<<endl<<customBorder+gaps<<endl<<customBorder+gaps<<endl;
        switch (screensizex % 2) {
            case 0: cout<<customBorder+endSpaces+"Press jump key to restart."+endSpaces+customBorder<<endl; break;
            case 1: cout<<customBorder+endSpaces+"Press jump key to restart"+endSpaces+customBorder<<endl; break;
        }
        cout<<customBorder+gaps<<endl<<customBorder+gaps<<endl<<border[0]<<endl;

        this_thread::sleep_for (chrono::milliseconds(100));

        //wait for key to be pressed
        while (true) {
            if (GetKeyState(VK_UP) & 0x8000 ||GetKeyState(VK_SPACE) & 0x8000 || GetKeyState('W') & 0x8000) break;
            this_thread::sleep_for (chrono::milliseconds(10));
        }
    }
    return 0;
}
