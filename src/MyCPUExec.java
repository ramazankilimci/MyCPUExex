
import jdk.dynalink.Operation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyCPUExec {

    public static void StartTomorrowMorning()
    {

    }

    public static void main(String[] args) throws IOException {

        /* Flag[0][0]=ZF - Flag_Name ; Flags[0][1]=1 - Flag_Value */
        String[][] flagArr = {
                {"ZF", "0"},
                {"CF", "0"},
                {"SF", "0"},
        };
        /* Register[0][0]=0001(HEX) - Register A ; Register[0][1]=0000111100001111 - Register Data */
        String[][] registerArr = {
                {"0000000000000000", null},
                {"0000000000000001", null},
                {"0000000000000010", null},
                {"0000000000000011", null},
                {"0000000000000100", null},
                {"0000000000000101", null},
                {"0000000000000110", "1111111111111111"},
        };

        /* Memory[0][0]=002A(HEX); Memory[0][1]=0001000100010001; */
        String[][] memoryArr = new String[64][2];

        /**
         * This part is reading TEST.TXT or PROGRAM.BIN file from my folder. Put into LIST "myList".
         * ARRAY "test" is created using toArray() method.
         * TODO Read from command line!
         */
        File file = new File("//Users//ramazankilimci//Documents//SWE514//CPU-Project//program.txt");
        BufferedReader brStr = new BufferedReader(new FileReader(file));

        String str = null;
        List<String> myList = new ArrayList<String>();
        while ((str = brStr.readLine()) != null)
        {
            myList.add(str);
        }

        /**PUT ALL ROWS INTO "text2Arr" for parsing
         * INITIALIZE "instructionArr" ROW INDEX LENGTH as ROW COUNT of TXT FILE
         *  COLUMN INDEXES are assigned using SubString() Method. First 6 OpCode, 7-8 Address Mode, Last 16 Operand.
         *  Instruction[0][0]=LOAD ; Instruction[0][1]=00 - Addr_Mode ; Instruction[0][2]=0000111100001111 - Operand
         * */
        String[] text2Arr;
        text2Arr = myList.toArray(new String[0]);
        String[][] instructionArr = new String[text2Arr.length][3];
        System.out.println(text2Arr.length);
        for (int i=0; i<text2Arr.length; i++)
        {
            instructionArr[i][0] = text2Arr[i].substring(0,6);
            instructionArr[i][1] = text2Arr[i].substring(6,8);
            instructionArr[i][2] = text2Arr[i].substring(8);
            //System.out.print(instructionArr[i][0] + " " + instructionArr[i][1] + " " + instructionArr[i][2]);
            //System.out.println();
        }


        String opName, addressMode, operand =null;
        String mainTempVal = null;
        String memAddress = null;
        int i;
        for(i=0; i<text2Arr.length; i++)
        {
            System.out.println("Print i : " + i);
           //Operations myOpr = new Operations();
            opName = Operations.getOperationName(instructionArr[i][0]);
           addressMode = instructionArr[i][1];
           operand = instructionArr[i][2];



            if(opName.equals("LOAD")) {
               System.out.print(instructionArr[i][0] + " " + instructionArr[i][1] + " " + instructionArr[i][2]);
                mainTempVal = Operations.load(addressMode,memoryArr,operand, registerArr);
                System.out.println("LOAD executed " + operand);

           }
           else if(opName.equals("STORE"))
           {
               Operations.store(addressMode,registerArr,memoryArr,operand);
               /* Address_Mode = 01 ---> STORE "mainTempVal" at the register A || B || C || D || E */
               System.out.println("STORE executed");
           }
            else if(opName.equals("ADD"))
            {
                Operations.add(addressMode,memoryArr,operand,registerArr,flagArr);
                System.out.println("ADD executed");
            }
            else if(opName.equals("SUB"))
            {
                Operations.sub(addressMode,memoryArr,operand,registerArr,flagArr);
                System.out.println("SUB executed");
            }
            else if(opName.equals("INC"))
            {
                Operations.inc(addressMode,memoryArr,operand,registerArr,flagArr);
                System.out.println("INC executed");
            }

            else if(opName.equals("DEC"))
            {
                Operations.dec(addressMode,memoryArr,operand,registerArr,flagArr);
                System.out.println("DEC executed");
            }
            else if(opName.equals("MUL"))
            {
                Operations.mul(addressMode,memoryArr,operand,registerArr,flagArr);
                System.out.println("MUL executed");
            }
            else if(opName.equals("DIV"))
            {
                Operations.div(addressMode,memoryArr,operand,registerArr,flagArr);
                System.out.println("DIV executed");
            }
            else if(opName.equals("XOR"))
            {
                Operations.xor(addressMode,memoryArr,operand,registerArr,flagArr);
                System.out.println("XOR executed");
            }
            else if(opName.equals("AND"))
            {
                Operations.and(addressMode,memoryArr,operand,registerArr,flagArr);
                System.out.println("AND executed");
            }
            else if(opName.equals("OR"))
            {
                Operations.or(addressMode,memoryArr,operand,registerArr,flagArr);
                System.out.println("OR executed");
            }
            else if(opName.equals("NOT"))
            {
                Operations.not(addressMode,memoryArr,operand,registerArr,flagArr);
                System.out.println("NOT executed");
            }
            else if(opName.equals("SHL"))
            {
                Operations.shl(addressMode,memoryArr,operand,registerArr,flagArr);
                System.out.println("SHL executed");
            }
            else if(opName.equals("SHR"))
            {
                Operations.shr(addressMode,memoryArr,operand,registerArr,flagArr);
                System.out.println("SHR executed");
            }
            else if(opName.equals("NOP"))
            {
                Operations.nop(addressMode,operand);
                System.out.println("NOP executed");
            }
            else if(opName.equals("PUSH"))
            {
                Operations.push(addressMode,registerArr,memoryArr,operand, flagArr);
                System.out.println("PUSH executed");
            }
            else if(opName.equals("POP"))
            {
                Operations.pop(addressMode,registerArr,memoryArr,operand, flagArr);
                System.out.println("POP executed");
            }
            else if(opName.equals("CMP"))
            {
                Operations.cmp(addressMode,memoryArr,operand,registerArr,flagArr);
                System.out.println("CMP executed");
            }
           else if(opName.equals("JMP"))
            {
                /** Return jmpIndex and  */
                i -= Operations.jmp(addressMode,operand,registerArr);
                System.out.println("JMP executed");
                continue;

            }
           else if(opName.equals("JZorJE"))
            {
                if(Operations.jzOrje(addressMode,operand,registerArr,flagArr) < 65000) {
                    i -= Operations.jzOrje(addressMode, operand, registerArr, flagArr);
                    System.out.println("JZorJE executed");
                    continue;
                }
            }
           else if(opName.equals("JNZorJNE"))
            {
                if(Operations.jnzOrjne(addressMode,operand,registerArr,flagArr) < 65000) {
                    i = Operations.jnzOrjne(addressMode, operand, registerArr, flagArr)-1;
                    System.out.println("JNZorJNE executed");
                    continue;
                }
            }
            else if(opName.equals("JC"))
            {
                if(Operations.jc(addressMode,operand,registerArr,flagArr) < 65000) {
                    i -= Operations.jc(addressMode, operand, registerArr, flagArr);
                    System.out.println("JC executed");
                    continue;
                }
            }
            else if(opName.equals("JNC"))
            {
                if(Operations.jnc(addressMode,operand,registerArr,flagArr) < 65000) {
                    i -= Operations.jnc(addressMode, operand, registerArr, flagArr);
                    System.out.println("JNC executed");
                    continue;
                }
            }
            else if(opName.equals("JA"))
            {
                if(Operations.ja(addressMode,operand,registerArr,flagArr) < 65000) {
                    i -= Operations.ja(addressMode, operand, registerArr, flagArr);
                    System.out.println("JA executed");
                    continue;
                }
            }
            else if(opName.equals("JAE"))
            {
                if(Operations.jae(addressMode,operand,registerArr,flagArr) < 65000) {
                    i -= Operations.jae(addressMode, operand, registerArr, flagArr);
                    System.out.println("JAE executed");
                    continue;
                }
            }
            else if(opName.equals("JB"))
            {
                if(Operations.jb(addressMode,operand,registerArr,flagArr) < 65000) {
                    i -= Operations.jb(addressMode, operand, registerArr, flagArr);
                    System.out.println("JB executed");
                    continue;
                }
            }
            else if(opName.equals("JBE"))
            {
                if(Operations.jbe(addressMode,operand,registerArr,flagArr) < 65000) {
                    i -= Operations.jbe(addressMode, operand, registerArr, flagArr);
                    System.out.println("JBE executed");
                    continue;
                }
            }
            else if(opName.equals("READ"))
            {
                Operations.read(addressMode, operand, registerArr, memoryArr);
                System.out.println("READ executed");
            }

           else if(opName.equals("PRINT"))
            {
               Operations.print(addressMode,memoryArr,operand,registerArr);
               System.out.println("PRINT executed");
            }

        }

        //General.print2DArray(registerArr);
        //General.print2DArray(memoryArr);

    }
}
