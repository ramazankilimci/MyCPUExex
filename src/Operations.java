import java.util.Scanner;

public class Operations {

    /**
     * Get Operation Name. "LOAD", "PRINT", "PUSH" etc.
     * @param operation
     * @return
     */
    public static String getOperationName(String operation)
    {
        String output;
        switch (operation) 
        {
            case "000001" :
                output = "HALT";
                break;
            case "000010" :
                output = "LOAD";
                break;
            case "000011" :
                output = "STORE";
                break;
            case "000100":
                output = "ADD";
                break;
            case "000101":
                output = "SUB";
                break;
            case "000110":
                output= "INC";
                break;
            case "000111":
                output = "DEC";
                break;
            case "001000":
                output = "MUL";
                break;
            case "001001":
                output = "DIV";
                break;
            case "001010":
                output = "XOR";
                break;
            case "001011":
                output = "AND";
                break;
            case "001100":
                output = "OR";
                break;
            case "001101":
                output = "NOT";
                break;
            case "001110":
                output = "SHL";
                break;
            case "001111":
                output = "SHR";
                break;
            case "010000":
                output = "NOP";
                break;
            case "010001":
                output = "PUSH";
                break;
            case "010010":
                output = "POP";
                break;
            case "010011":
                output = "CMP";
                break;
            case "010100":
                output = "JMP";
                break;
            case "010101":
                output = "JZorJE";
                break;
            case "010110":
                output = "JNZorJNE";
                break;
            case "010111":
                output = "JC";
                break;
            case "011000":
                output = "JNC";
                break;
            case "011001":
                output = "JA";
                break;
            case "100000":
                output = "JAE";
                break;
            case "100001":
                output = "JB";
                break;
            case "100010":
                output = "JBE";
                break;
            case "100011":
                output = "READ";
                break;
            case "100100":
                output = "PRINT";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation);
        }
        return output;
    }

    /**
     * LOAD Instruction
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @return
     */
    public static String load(String addressMode, String[][] memory, String operand, String[][] register)
    {
        /** TODO load 01 yazılacak */
        System.out.println("LOAD Method Addr_Mode : " +operand);
        String tempVal = null;
        int length = memory.length;
        if(addressMode.equals("00"))
        {
            register[1][1] = operand;
            System.out.println("LOAD--00 : " + tempVal);
        }
        else if(addressMode.equals("01"))
        {
            for (int i=0; i<register.length; i++)
                if(register[i][0].equals(operand))
                    register[1][1] = register[i][1];
        }
        else if(addressMode.equals("10"))
        {
            for (int i=0; i<length; i++) {
                if (memory[i][0].equals(operand)) {
                    register[1][1] = memory[i][1];
                    System.out.println("LOAD--10 : " + tempVal);
                }
            }
        }
        return tempVal;

    }

    /**
     * STORE Instruction
     * @param addressMode
     * @param register
     * @param memory
     * @param operand
     */
    public static void store(String addressMode, String[][] register, String[][] memory, String operand)
    {
        String oprTempVal = null;
        /* Address_Mode = 01 ---> STORE "mainTempVal" at the register A || B || C || D || E */
        if(addressMode.equals("01"))
            for (int i = 0; i < register.length; i++) {
                if (register[i][0].equals(operand))
                    register[i][1] = register[1][1];
            }
        /* Address_Mode = 10 ---> STORE "mainTempVal" at the Memory Address pointed in the register.
           GET Memory Address in the register and STORE value in that address */
        else if(addressMode.equals("10"))
        {
            String memAddress = null;
            for (int i = 0; i < register.length; i++) {
                if (register[i][0].equals(operand))
                    memAddress = register[i][1];
            }
            System.out.println(memAddress + " : " + register[1][1]);
            //Convert "memAddress" to Integer. So that we can 2 memAddress to store Operand as 8-bit value.
            int memInt = Integer.parseInt(memAddress,2);

            for (int j=0; j<memory.length; j++){
                if(memory[j][0] == null) {
                    memory[j][0] = String.format("%16s", Integer.toBinaryString(memInt)).replace(' ','0');
                    memory[j][1] = register[1][1].substring(0,8);
                    memory[j+1][0] = String.format("%16s", Integer.toBinaryString(memInt+1)).replace(' ','0');
                    memory[j+1][1] = register[1][1].substring(8,16);
                    break;
                }
            }
        }
        /* Address_Mode = 11 ---> STORE "mainTempVal" at the Memory Address stated directly */
        else if(addressMode.equals("11"))
        {
            for (int j=0; j<memory.length; j++){
                if(memory[j][0] != null) {
                    memory[j][0] = operand;
                    memory[j][1] = register[1][1];
                }
            }

        }
        System.out.println("STORE in function executed");
    }

    /**
     * ADD Instruction
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @param flagArr
     */
    public static void add(String addressMode, String[][] memory, String operand, String[][] register,String[][] flagArr)
    {
        String tempVal = null;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
        }
        else if(addressMode.equals("01"))
        {
            for (int i=0; i<register.length; i++)
                if(register[i][0].equals(operand))
                    tempVal = register[i][1];
        }
        else if(addressMode.equals("10"))
        {
            for (int i=0; i<length; i++) {
                if (memory[i][0].equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];

                }
            }
        }

        int numOne = Integer.parseInt(register[1][1],2);
        int numTwo = Integer.parseInt(tempVal,2);
        /** ADD two numbers using INT. Convert into BINARY and STORE in REGISTER A */
        register[1][1] = String.format("%16s", Integer.toBinaryString( numOne + numTwo)).replace(' ','0');
        if (numOne+numTwo>Math.pow(2,16))
            /** Set CF=1 if TOTAL NUMBER is greater than 2**16 */
            flagArr[1][1] = "1";
            else
                {
                flagArr[0][1] = "0";
                flagArr[1][1] = "0";
                flagArr[2][1] = "0";
                }
    }

    /**
     * SUB Instruction
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @param flagArr
     */
    public static void sub(String addressMode, String[][] memory, String operand, String[][] register,String[][] flagArr)
    {
        String tempVal = null;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
        }
        else if(addressMode.equals("01"))
        {
            for (int i=0; i<register.length; i++)
                if(register[i][0].equals(operand))
                    tempVal = register[i][1];
        }
        else if(addressMode.equals("10"))
        {
            for (int i=0; i<length; i++) {
                if (memory[i][0].equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];
                }
            }
        }
        int numOne = Integer.parseInt(register[1][1],2);
        int numTwo = Integer.parseInt(tempVal,2);
        /** SUBs two numbers using INT. Convert into BINARY and STORE in REGISTER A */
        register[1][1] = String.format("%16s", Integer.toBinaryString( numOne - numTwo)).replace(' ','0');
        if (numOne < numTwo)
        /** Set CF=1 if NumTWO > NumONE*/
            flagArr[1][1] = "1";
        else
        {
            flagArr[0][1] = "0";
            flagArr[1][1] = "0";
            flagArr[2][1] = "0";
        }
    }

    /** INCREASE Instruction
     * TODO Check Whether Memory Addresses right or not? After Integer.toBinaryString CONVERSION
     * TODO DECIDE use of SUBSTRING
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @param flagArr
     */
    public static void inc(String addressMode, String[][] memory, String operand, String[][] register,String[][] flagArr)
    {
        String tempVal = null;
        int printInt = 0;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
            printInt = Integer.parseInt(tempVal, 2);
            printInt = printInt + 1;
            tempVal = String.format("%16s", Integer.toBinaryString(printInt)).replace(' ','0');
        }
        else if(addressMode.equals("01"))
        {
            /** GET Operand in REGISTER. INC OPERAND and STORE VALUE in same REGISTER */
            for (int i=0; i<register.length; i++) {
                if (register[i][0].equals(operand)) {
                    tempVal = register[i][1];
                    printInt = Integer.parseInt(tempVal, 2);
                    printInt = printInt + 1;
                    register[i][1] = String.format("%16s", Integer.toBinaryString(printInt)).replace(' ', '0');
                }
            }
        }
        else if(addressMode.equals("10"))
        {
            /** Find MEMORY Address. MERGE 2 MEMORY Address. INC OPERAND in MEMORY Address. SAVE to same MEMORY Address */
            for (int i=0; i<length; i++) {
                if ((memory[i][0]+memory[i+1][0]).equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];
                    printInt = Integer.parseInt(tempVal,2);
                    printInt = printInt + 1;
                    tempVal = String.format("%16s", Integer.toBinaryString(printInt)).replace(' ','0');
                    memory[i][1] = tempVal.substring(0,8);
                    memory[i+1][1] = tempVal.substring(8,16);
                }
            }
        }

        /** SET Zero-Flag flagArr[0][1]=1 if Operand(printInt) equals ZERO! */
        if(printInt == 0)
            flagArr[0][1] = "1";
        /** SET Carry-Flag flagArr[1][1]=1 if Operand(printInt) greater than 2^16! */
        else if(printInt > Math.pow(2,16))
            flagArr[1][1] = "1";
        /** SET Signed-Flag flagArr[2][1]=1 if Operand(printInt) lower than ZERO! */
        else if(printInt < 0)
            flagArr[2][1] = "1";
        else
        {
            flagArr[0][1] = "0";
            flagArr[1][1] = "0";
            flagArr[2][1] = "0";
        }
    }

    /**
     * DECREASE Instruction
     * TODO Check Whether Memory Addresses right or not? After Integer.toBinaryString CONVERSION
     * TODO DECIDE use of SUBSTRING
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @param flagArr
     */
    public static void dec(String addressMode, String[][] memory, String operand, String[][] register,String[][] flagArr)
    {
        String tempVal = null;
        int printInt = 0;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
            printInt = Integer.parseInt(tempVal, 2);
            printInt = printInt - 1;
            tempVal = String.format("%16s", Integer.toBinaryString(printInt)).replace(' ','0');
        }
        else if(addressMode.equals("01"))
        {
            /** Get Operand in REGISTER. DECREASE Operand and STORE Operand in same REGISTER */
            for (int i=0; i<register.length; i++) {
                if (register[i][0].equals(operand)) {
                    tempVal = register[i][1];
                    printInt = Integer.parseInt(tempVal, 2);
                    printInt = printInt - 1;
                    register[i][1] = String.format("%16s", Integer.toBinaryString(printInt)).replace(' ', '0');
                }
            }
        }
        else if(addressMode.equals("10"))
        {
            /** Find MEMORY Address. MERGE 2 MEMORY Address. DECREASE Operand in MEMORY Address. SAVE Operand to same MEMORY Address */
            for (int i=0; i<length; i++) {
                if ((memory[i][0]+memory[i+1][0]).equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];
                    printInt = Integer.parseInt(tempVal,2);
                    printInt = printInt - 1;
                    tempVal = String.format("%16s", Integer.toBinaryString(printInt)).replace(' ','0');
                    memory[i][1] = tempVal.substring(0,8);
                    memory[i+1][1] = tempVal.substring(8,16);
                }
            }
        }

        /** SET Zero-Flag flagArr[0][1]=1 if Operand(printInt) equals ZERO! */
        if(printInt == 0)
            flagArr[0][1] = "1";
        /** SET Signed-Flag flagArr[2][1]=1 if Operand(printInt) lower than ZERO! */
        else if(printInt < 0)
            flagArr[2][1] = "1";
        else
        {
            flagArr[0][1] = "0";
            flagArr[1][1] = "0";
            flagArr[2][1] = "0";
        }
    }

    /**
     * MUL Instruction
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @param flagArr
     */
    public static void mul(String addressMode, String[][] memory, String operand, String[][] register,String[][] flagArr)
    {
        String tempVal = null;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
        }
        else if(addressMode.equals("01"))
        {
            for (int i=0; i<register.length; i++)
                if(register[i][0].equals(operand))
                    tempVal = register[i][1];
        }
        else if(addressMode.equals("10"))
        {
            for (int i=0; i<length; i++) {
                if (memory[i][0].equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];

                }
            }
        }

        int numOne = Integer.parseInt(register[1][1],2);
        int numTwo = Integer.parseInt(tempVal,2);
        /** MULTIPLY two numbers using INT. Convert into BINARY and STORE in REGISTER A */
        register[1][1] = String.format("%16s", Integer.toBinaryString( numOne * numTwo)).replace(' ','0');

        /** SET Zero-Flag flagArr[0][1]=1 if Operand(printInt) equals ZERO! */
        if(numOne * numTwo == 0)
            flagArr[0][1] = "1";
        /** SET Signed-Flag flagArr[2][1]=1 if Operand(printInt) lower than ZERO! */
        else if(numOne * numTwo < 0)
            flagArr[2][1] = "1";
        else
        {
            flagArr[0][1] = "0";
            flagArr[2][1] = "0";
        }
    }

    /**
     * DIV Instruction
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @param flagArr
     */
    public static void div(String addressMode, String[][] memory, String operand, String[][] register,String[][] flagArr)
    {
        String tempVal = null;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
        }
        else if(addressMode.equals("01"))
        {
            for (int i=0; i<register.length; i++)
                if(register[i][0].equals(operand))
                    tempVal = register[i][1];
        }
        else if(addressMode.equals("10"))
        {
            for (int i=0; i<length; i++) {
                if (memory[i][0].equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];

                }
            }
        }

        int numOne = Integer.parseInt(register[1][1],2);
        int numTwo = Integer.parseInt(tempVal,2);
        if(numTwo != 0) {
            /** DIVIDE two numbers using INT. Convert into BINARY and STORE in REGISTER A */
            register[1][1] = String.format("%16s", Integer.toBinaryString(numOne / numTwo)).replace(' ', '0');
            register[4][1] = String.format("%16s", Integer.toBinaryString(numOne % numTwo)).replace(' ', '0');

            /** SET Zero-Flag flagArr[0][1]=1 if Operand(printInt) equals ZERO! */
            if (numOne * numTwo == 0)
                flagArr[0][1] = "1";
            /** SET Signed-Flag flagArr[2][1]=1 if Operand(printInt) lower than ZERO! */
            else if (numOne * numTwo < 0)
                flagArr[2][1] = "1";
            else
            {
                flagArr[0][1] = "0";
                flagArr[2][1] = "0";
            }
        } else {
            System.out.println("You cannot divide number by Zero(0)!");
        }
    }

    /**
     * XOR Instruction
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @param flagArr
     */
    public static void xor(String addressMode, String[][] memory, String operand, String[][] register,String[][] flagArr)
    {
        String tempVal = null;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
        }
        else if(addressMode.equals("01"))
        {
            for (int i=0; i<register.length; i++)
                if(register[i][0].equals(operand))
                    tempVal = register[i][1];
        }
        else if(addressMode.equals("10"))
        {
            for (int i=0; i<length; i++) {
                if (memory[i][0].equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];

                }
            }
        }

        int numOne = Integer.parseInt(register[1][1],2);
        int numTwo = Integer.parseInt(tempVal,2);
        /** XOR two numbers using INT. Convert into BINARY and STORE in REGISTER A */
        register[1][1] = String.format("%16s", Integer.toBinaryString( numOne ^ numTwo)).replace(' ','0');

        /** SET Zero-Flag flagArr[0][1]=1 if Operand(printInt) equals ZERO! */
        if((numOne ^ numTwo) == 0)
            flagArr[0][1] = "1";
        /** SET Signed-Flag flagArr[2][1]=1 if Operand(printInt) lower than ZERO! */
        else if((numOne ^ numTwo) < 0)
            flagArr[2][1] = "1";
        else
        {
            flagArr[0][1] = "0";
            flagArr[2][1] = "0";
        }
    }

    /**
     * AND Instruction
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @param flagArr
     */
    public static void and(String addressMode, String[][] memory, String operand, String[][] register,String[][] flagArr)
    {
        String tempVal = null;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
        }
        else if(addressMode.equals("01"))
        {
            for (int i=0; i<register.length; i++)
                if(register[i][0].equals(operand))
                    tempVal = register[i][1];
        }
        else if(addressMode.equals("10"))
        {
            for (int i=0; i<length; i++) {
                if (memory[i][0].equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];

                }
            }
        }

        int numOne = Integer.parseInt(register[1][1],2);
        int numTwo = Integer.parseInt(tempVal,2);
        /** AND two numbers using INT. Convert into BINARY and STORE in REGISTER A */
        register[1][1] = String.format("%16s", Integer.toBinaryString( numOne & numTwo)).replace(' ','0');

        /** SET Zero-Flag flagArr[0][1]=1 if Operand(printInt) equals ZERO! */
        if((numOne & numTwo) == 0)
            flagArr[0][1] = "1";
        /** SET Signed-Flag flagArr[2][1]=1 if Operand(printInt) lower than ZERO! */
        else if((numOne & numTwo) < 0)
            flagArr[2][1] = "1";
        else
        {
            flagArr[0][1] = "0";
            flagArr[2][1] = "0";
        }
    }

    /**
     * OR Instruction
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @param flagArr
     */
    public static void or(String addressMode, String[][] memory, String operand, String[][] register,String[][] flagArr)
    {
        String tempVal = null;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
        }
        else if(addressMode.equals("01"))
        {
            for (int i=0; i<register.length; i++)
                if(register[i][0].equals(operand))
                    tempVal = register[i][1];
        }
        else if(addressMode.equals("10"))
        {
            for (int i=0; i<length; i++) {
                if (memory[i][0].equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];

                }
            }
        }

        int numOne = Integer.parseInt(register[1][1],2);
        int numTwo = Integer.parseInt(tempVal,2);
        /** OR two numbers using INT. Convert into BINARY and STORE in REGISTER A */
        register[1][1] = String.format("%16s", Integer.toBinaryString( numOne | numTwo)).replace(' ','0');

        /** SET Zero-Flag flagArr[0][1]=1 if Operand(printInt) equals ZERO! */
        if((numOne | numTwo) == 0)
            flagArr[0][1] = "1";
        /** SET Signed-Flag flagArr[2][1]=1 if Operand(printInt) lower than ZERO! */
        else if((numOne | numTwo) < 0)
            flagArr[2][1] = "1";
        else
        {
            flagArr[0][1] = "0";
            flagArr[2][1] = "0";
        }
    }

    /**
     * NOT Instruction
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @param flagArr
     */
    public static void not(String addressMode, String[][] memory, String operand, String[][] register,String[][] flagArr)
    {
        String tempVal = null;
        long printInt = 0;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
            printInt = Integer.parseInt(tempVal, 2);
            printInt = printInt - 1;
            tempVal = String.format("%16s", Long.toBinaryString(~printInt)).substring(48,64);
        }
        else if(addressMode.equals("01"))
        {
            /** Get Operand in REGISTER. DECREASE Operand and STORE Operand in same REGISTER */
            for (int i=0; i<register.length; i++) {
                if (register[i][0].equals(operand)) {
                    tempVal = register[i][1];
                    printInt = Integer.parseInt(tempVal, 2);
                    printInt = printInt - 1;
                    register[i][1] = String.format("%16s", Long.toBinaryString(~printInt)).substring(48,64);
                }
            }
        }
        else if(addressMode.equals("10"))
        {
            /** Find MEMORY Address. MERGE 2 MEMORY Address. DECREASE Operand in MEMORY Address. SAVE Operand to same MEMORY Address
             * TODO CHECK Whether Memory Addresses right or not (DONE!)*/
            for (int i=0; i<length; i++) {
                if ((memory[i][0]+memory[i+1][0]).equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];
                    printInt = Integer.parseInt(tempVal,2);
                    printInt = printInt - 1;
                    tempVal = String.format("%16s", Long.toBinaryString(~printInt)).substring(48,64);
                    memory[i][1] = tempVal.substring(0,8);
                    memory[i+1][1] = tempVal.substring(8,16);
                }
            }
        }

        /** SET Zero-Flag flagArr[0][1]=1 if Operand(printInt) equals ZERO! */
        if(printInt == 0)
            flagArr[0][1] = "1";
        /** SET Signed-Flag flagArr[2][1]=1 if Operand(printInt) lower than ZERO! */
        else if(printInt < 0)
            flagArr[2][1] = "1";
        else
        {
            flagArr[0][1] = "0";
            flagArr[2][1] = "0";
        }
    }

    /**
     * SHL Instruction
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @param flagArr
     */
    public static void shl(String addressMode, String[][] memory, String operand, String[][] register,String[][] flagArr)
    {
        String tempVal = null;
        int printInt = 0;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
            printInt = Integer.parseInt(tempVal, 2);
            printInt = printInt << 1;
            tempVal = String.format("%16s", Integer.toBinaryString(printInt)).replace(' ','0');
        }
        else if(addressMode.equals("01"))
        {
            /** Get Operand in REGISTER. DECREASE Operand and STORE Operand in same REGISTER */
            for (int i=0; i<register.length; i++) {
                if (register[i][0].equals(operand)) {
                    tempVal = register[i][1];
                    printInt = Integer.parseInt(tempVal, 2);
                    printInt = printInt << 1;
                    register[i][1] = String.format("%16s", Integer.toBinaryString(printInt)).replace(' ','0');
                }
            }
        }
        else if(addressMode.equals("10"))
        {
            /** Find MEMORY Address. MERGE 2 MEMORY Address. DECREASE Operand in MEMORY Address. SAVE Operand to same MEMORY Address
             * TODO CHECK Whether Memory Addresses right or not (DONE!)*/
            for (int i=0; i<length; i++) {
                if ((memory[i][0]+memory[i+1][0]).equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];
                    printInt = Integer.parseInt(tempVal,2);
                    printInt = printInt << 1;
                    tempVal = String.format("%16s", Integer.toBinaryString(printInt)).replace(' ','0');
                    memory[i][1] = tempVal.substring(0,8);
                    memory[i+1][1] = tempVal.substring(8,16);
                }
            }
        }

        /** SET Zero-Flag flagArr[0][1]=1 if Operand(printInt) equals ZERO! */
        if(printInt == 0)
            flagArr[0][1] = "1";
        /** SET Signed-Flag flagArr[2][1]=1 if Operand(printInt) lower than ZERO! */
        else if(printInt < 0)
            flagArr[2][1] = "1";
        else
        {
            flagArr[0][1] = "0";
            flagArr[1][1] = "0";
            flagArr[2][1] = "0";
        }
    }

    /**
     * SHR Instruction
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @param flagArr
     */
    public static void shr(String addressMode, String[][] memory, String operand, String[][] register,String[][] flagArr)
    {
        String tempVal = null;
        int printInt = 0;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
            printInt = Integer.parseInt(tempVal, 2);
            printInt = printInt >>> 1;
            tempVal = String.format("%16s", Integer.toBinaryString(printInt)).replace(' ','0');
        }
        else if(addressMode.equals("01"))
        {
            /** Get Operand in REGISTER. DECREASE Operand and STORE Operand in same REGISTER */
            for (int i=0; i<register.length; i++) {
                if (register[i][0].equals(operand)) {
                    tempVal = register[i][1];
                    printInt = Integer.parseInt(tempVal, 2);
                    printInt = printInt >>> 1;
                    register[i][1] = String.format("%16s", Integer.toBinaryString(printInt)).replace(' ','0');
                }
            }
        }
        else if(addressMode.equals("10"))
        {
            /** Find MEMORY Address. MERGE 2 MEMORY Address. DECREASE Operand in MEMORY Address. SAVE Operand to same MEMORY Address
             * TODO CHECK Whether Memory Addresses right or not (DONE!)*/
            for (int i=0; i<length; i++) {
                if ((memory[i][0]+memory[i+1][0]).equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];
                    printInt = Integer.parseInt(tempVal,2);
                    printInt = printInt >>> 1;
                    tempVal = String.format("%16s", Integer.toBinaryString(printInt)).replace(' ','0');
                    memory[i][1] = tempVal.substring(0,8);
                    memory[i+1][1] = tempVal.substring(8,16);
                }
            }
        }

        /** SET Zero-Flag flagArr[0][1]=1 if Operand(printInt) equals ZERO! */
        if(printInt == 0)
            flagArr[0][1] = "1";
        /** SET Signed-Flag flagArr[2][1]=1 if Operand(printInt) lower than ZERO! */
        else if(printInt < 0)
            flagArr[2][1] = "1";
        else
        {
            flagArr[0][1] = "0";
            flagArr[2][1] = "0";
        }
    }

    /**
     * NOP Instruction
     * @param addressMode
     * @param operand
     */
    public static void nop(String addressMode, String operand)
    {
        //System.out.println("Thanks to my Piko :)");
    }

    /**
     * PUSH Instruction
     * @param addressMode
     * @param register
     * @param memory
     * @param operand
     * @param flagArr
     */
    public static void push(String addressMode, String[][] register, String[][] memory, String operand, String[][] flagArr)
    {
        String oprTempVal = null;
        /* Address_Mode = 01 ---> GET value from the register A || B || C || D || E */
        if(addressMode.equals("01"))
        {
            for (int i = 0; i < register.length; i++)
            {
                if (register[i][0].equals(operand))
                {
                    String memAddress = flagArr[5][1];
                    int memInt = Integer.parseInt(memAddress,2);
                    for (int j=memory.length; j>0; j--)
                    {
                        if(memory[j][0] == null)
                        {
                            // Store register data at the memoryAddress-1(First 8-bit)
                            memory[j-1][0] = String.format("%16s", Integer.toBinaryString(memInt-1)).replace(' ','0');
                            memory[j-1][1] = register[i][1].substring(0,8);
                            // Store register data at the memoryAddress-1(Last 8-bit)
                            memory[j][0] = String.format("%16s", Integer.toBinaryString(memInt)).replace(' ','0');
                            memory[j][1] = register[i][1].substring(8,16);
                            /** Decrease 2 from Stack Pointer (SP) */
                            flagArr[5][1] = String.format("%16s", Integer.toBinaryString(memInt-2)).replace(' ','0');
                            break;
                        }
                    }
                }
            }
        }

        System.out.println("PUSH in function executed");
    }

    /**
     * POP Instruction
     * @param addressMode
     * @param register
     * @param memory
     * @param operand
     * @param flagArr
     */
    public static void pop(String addressMode, String[][] register, String[][] memory, String operand, String[][] flagArr)
    {
        String tempVal = null;
        /* Address_Mode = 01 ---> Save data into the register A || B || C || D || E */
        if(addressMode.equals("01"))
        {
            for (int i = 0; i < register.length; i++)
            {
                if (register[i][0].equals(operand))
                {
                    String memAddress = flagArr[5][1];
                    int memInt = Integer.parseInt(memAddress,2);
                    for (int j=memory.length; j>0; j--)
                    {
                        if(memory[j][1] == null)
                        {
                            tempVal = memory[j+1][1] + memory[j+2][1];
                            int poppedNumber = Integer.parseInt(tempVal,2);
                            /** Store Operand in the memory to specified Register Address. */
                            register[i][1] = String.format("%16s", Integer.toBinaryString( poppedNumber)).replace(' ','0');
                            memory[j+1][1] = memory[j+2][1] = null;
                            /** Increase Stack Pointer (SP) by 2. Assign Memory Address to SP. */
                            flagArr[5][1] = memory[j+2][0];
                            break;
                        }
                    }
                }
            }
        }

        System.out.println("PUSH in function executed");
    }

    /**
     * CMP Instruction --> Perform comparison between AC(Register A) & Operand
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     * @param flagArr
     */
    public static void cmp(String addressMode, String[][] memory, String operand, String[][] register,String[][] flagArr)
    {
        String tempVal = null;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
        }
        else if(addressMode.equals("01"))
        {
            for (int i=0; i<register.length; i++)
                if(register[i][0].equals(operand))
                    tempVal = register[i][1];
        }
        else if(addressMode.equals("10"))
        {
            for (int i=0; i<length; i++) {
                if (memory[i][0].equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];

                }
            }
        }
        /** Compare Accumulator(Register A) with Operand! */
        int numAcc = Integer.parseInt(register[1][1],2);
        int numCmp = Integer.parseInt(tempVal,2);
        /** Set Flags accordingly */
        if (numAcc < numCmp) {
            flagArr[0][1] = "0"; //ZF
            flagArr[1][1] = "1"; //CF
            flagArr[2][1] = "1"; //SF
        } else if (numAcc == numCmp){
            flagArr[0][1] = "1"; //ZF
            flagArr[1][1] = "0"; //CF
            flagArr[2][1] = "0"; //SF
        }
        else {
            flagArr[0][1] = "0";
            flagArr[1][1] = "0";
            flagArr[2][1] = "0";
        }
    }

    /**
     * JMP Instruction --> Unconditional jump
     * @param addressMode
     * @param operand
     * @param register
     * @return
     */
    public static int jmp(String addressMode, String operand, String[][] register) {
        String tempVal = null;
        if (addressMode.equals("00")) {
            tempVal = operand;
        }
        /** Set PC(Program Counter) to jumped instruction */
        register[0][1] = tempVal;
        /** Convert String value to Integer. Divide by 3. Assign quotient to jmpIndex. i = jmpIndex */
        int jmpIndex = Integer.parseInt(tempVal, 2);
        jmpIndex = jmpIndex / 3;
        return jmpIndex;
    }

    /**
     * JZ & JE Instruction --> Jump if ZF=1
     * @param addressMode
     * @param operand
     * @param register
     * @param flagArr
     * @return
     */
    public static int jzOrje(String addressMode, String operand, String[][] register, String[][] flagArr) {
        String tempVal = null;
        if (addressMode.equals("00")) {
            tempVal = operand;
        }
        if(flagArr[0][1].equals("1")) {
            /** Set PC(Program Counter) to jumped instruction */
            register[0][1] = tempVal;
            /** Convert String value to Integer. Divide by 3. Assign quotient to jmpIndex. i = jmpIndex */
            int jmpIndex = Integer.parseInt(tempVal, 2);
            jmpIndex = jmpIndex / 3;
            return jmpIndex;
        }
        else {
            return 66000;
        }
    }

    /**
     * JNZ & JNE Instruction --> Jump if ZF=0
     * @param addressMode
     * @param operand
     * @param register
     * @param flagArr
     * @return
     */
    public static int jnzOrjne(String addressMode, String operand, String[][] register, String[][] flagArr) {
        String tempVal = null;
        if (addressMode.equals("00")) {
            tempVal = operand;
        }
        if(flagArr[0][1].equals("0")) {
            /** Set PC(Program Counter) to jumped instruction */
            register[0][1] = tempVal;
            /** Convert String value to Integer. Divide by 3. Assign quotient to jmpIndex. i = jmpIndex */
            int jmpIndex = Integer.parseInt(tempVal, 2);
            jmpIndex = jmpIndex / 3;
            return jmpIndex;
        }
        else {
            return 66000;
        }
    }

    /**
     * JC Instruction --> Jump if CF=1
     * @param addressMode
     * @param operand
     * @param register
     * @param flagArr
     * @return
     */
    public static int jc(String addressMode, String operand, String[][] register, String[][] flagArr) {
        String tempVal = null;
        if (addressMode.equals("00")) {
            tempVal = operand;
        }
        if(flagArr[1][1].equals("1")) {
            /** Set PC(Program Counter) to jumped instruction */
            register[0][1] = tempVal;
            /** Convert String value to Integer. Divide by 3. Assign quotient to jmpIndex. i = jmpIndex */
            int jmpIndex = Integer.parseInt(tempVal, 2);
            jmpIndex = jmpIndex / 3;
            return jmpIndex;
        }
        else {
            return 66000;
        }
    }

    /**
     * JNC Instruction --> Jump if CF=0
     * @param addressMode
     * @param operand
     * @param register
     * @param flagArr
     * @return
     */
    public static int jnc(String addressMode, String operand, String[][] register, String[][] flagArr) {
        String tempVal = null;
        if (addressMode.equals("00")) {
            tempVal = operand;
        }
        if(flagArr[1][1].equals("0")) {
            /** Set PC(Program Counter) to jumped instruction */
            register[0][1] = tempVal;
            /** Convert String value to Integer. Divide by 3. Assign quotient to jmpIndex. i = jmpIndex */
            int jmpIndex = Integer.parseInt(tempVal, 2);
            jmpIndex = jmpIndex / 3;
            return jmpIndex;
        }
        else {
            return 66000;
        }
    }

    /**
     * JA Instruction --> Jump if CF=0
     * @param addressMode
     * @param operand
     * @param register
     * @param flagArr
     * @return
     */
    public static int ja(String addressMode, String operand, String[][] register, String[][] flagArr) {
        String tempVal = null;
        if (addressMode.equals("00")) {
            tempVal = operand;
        }
        if(flagArr[1][1].equals("0")) {
            /** Set PC(Program Counter) to jumped instruction */
            register[0][1] = tempVal;
            /** Convert String value to Integer. Divide by 3. Assign quotient to jmpIndex. i = jmpIndex */
            int jmpIndex = Integer.parseInt(tempVal, 2);
            jmpIndex = jmpIndex / 3;
            return jmpIndex;
        }
        else {
            return 66000;
        }
    }

    /**
     * JAE Instruction --> Jump if CF=0
     * @param addressMode
     * @param operand
     * @param register
     * @param flagArr
     * @return
     */
    public static int jae(String addressMode, String operand, String[][] register, String[][] flagArr) {
        String tempVal = null;
        if (addressMode.equals("00")) {
            tempVal = operand;
        }
        if(flagArr[1][1].equals("0")) {
            /** Set PC(Program Counter) to jumped instruction */
            register[0][1] = tempVal;
            /** Convert String value to Integer. Divide by 3. Assign quotient to jmpIndex. i = jmpIndex */
            int jmpIndex = Integer.parseInt(tempVal, 2);
            jmpIndex = jmpIndex / 3;
            return jmpIndex;
        }
        else {
            return 66000;
        }
    }

    /**
     * JB Instruction
     * @param addressMode
     * @param operand
     * @param register
     * @param flagArr
     * @return
     */
    public static int jb(String addressMode, String operand, String[][] register, String[][] flagArr) {
        String tempVal = null;
        if (addressMode.equals("00")) {
            tempVal = operand;
        }
        if(flagArr[1][1].equals("1")) {
            /** Set PC(Program Counter) to jumped instruction */
            register[0][1] = tempVal;
            /** Convert String value to Integer. Divide by 3. Assign quotient to jmpIndex. i = jmpIndex */
            int jmpIndex = Integer.parseInt(tempVal, 2);
            jmpIndex = jmpIndex / 3;
            return jmpIndex;
        }
        else {
            return 66000;
        }
    }

    /**
     * JBE Instruction --> Jump if ZF=1 or CF=1
     * @param addressMode
     * @param operand
     * @param register
     * @param flagArr
     * @return
     */
    public static int jbe(String addressMode, String operand, String[][] register, String[][] flagArr) {
        String tempVal = null;
        if (addressMode.equals("00")) {
            tempVal = operand;
        }
        if(flagArr[0][1].equals("1") || flagArr[1][1].equals("1")) {
            /** Set PC(Program Counter) to jumped instruction */
            register[0][1] = tempVal;
            /** Convert String value to Integer. Divide by 3. Assign quotient to jmpIndex. i = jmpIndex */
            int jmpIndex = Integer.parseInt(tempVal, 2);
            jmpIndex = jmpIndex / 3;
            return jmpIndex;
        }
        else {
            return 66000;
        }
    }

    /**
     * READ Instruction --> Reads a character from console.
     * @param addressMode
     * @param operand
     * @param register
     * @param memory
     */
    public static void read(String addressMode, String operand, String [][] register, String [][] memory)
    {
        /** Try until correct value entered! */
        String myString = null;
        for (int i=0; i<2; i++) {
            Scanner readMe = new Scanner(System.in);
            System.out.println("Enter your character. For example A, B, Z etc.\n");
            myString = readMe.nextLine();
            if (myString.length() > 1) {
                System.out.println("Please enter only one character!");
                i = 0;
            } else if ((int) myString.charAt(0) <= 32 || (int) myString.charAt(0) >= 255)
            {
                System.out.println("Please enter valid ASCII character!");
                i = 0;
            }
            else
                i = 1;
        }

        /** Below 3 lines of code convert character into Binary String */
        char myChar = myString.charAt(0);
        int charInt = (int) myChar;
        String myOperand = String.format("%16s", Integer.toBinaryString(charInt)).replace(' ', '0');
        //System.out.println("Your character : " + myString + " ASCII Decimal : " + charInt);
        //System.out.println("Binary value of your character : " + myOperand);

        /* Address_Mode = 01 ---> STORE "myOperand" at the register A || B || C || D || E */
        if(addressMode.equals("01"))
            for (int i = 0; i < register.length; i++) {
                if (register[i][0].equals(operand))
                    register[i][1] = myOperand;
            }
        /* Address_Mode = 10 ---> STORE "myOperand" at the Memory Address pointed in the register.
           GET Memory Address in the register and STORE value in that address */
        else if(addressMode.equals("10"))
        {
            String memAddress = null;
            for (int i = 0; i < register.length; i++) {
                if (register[i][0].equals(operand))
                    memAddress = register[i][1];
            }
            System.out.println(memAddress + " : " + register[1][1]);
            //Convert "memAddress" to Integer. So that we can 2 memAddress to store Operand as 8-bit value.
            int memInt = Integer.parseInt(memAddress,2);

            for (int j=0; j<memory.length; j++){
                if(memory[j][0] == null) {
                    memory[j][0] = String.format("%16s", Integer.toBinaryString(memInt)).replace(' ','0');
                    memory[j][1] = myOperand.substring(0,8);
                    memory[j+1][0] = String.format("%16s", Integer.toBinaryString(memInt+1)).replace(' ','0');
                    memory[j+1][1] = myOperand.substring(8,16);
                    break;
                }
            }
        }
        /* Address_Mode = 11 ---> STORE "mainTempVal" at the Memory Address stated directly */
        else if(addressMode.equals("11"))
        {
            for (int j=0; j<memory.length; j++){
                if(memory[j][0] != null) {
                    memory[j][0] = operand;
                    memory[j][1] = register[1][1];
                }
            }

        }
    }

    /**
     * PRINT values if they are in printable ASCII value range
     * @param addressMode
     * @param memory
     * @param operand
     * @param register
     */
    public static void print(String addressMode, String[][] memory, String operand, String[][] register)
    {
        String tempVal = null;
        int length = memory.length;
        if(addressMode.equals("00")) {
            tempVal = operand;
        }
        else if(addressMode.equals("01"))
        {
            for (int i=0; i<register.length; i++)
                if(register[i][0].equals(operand))
                    tempVal = register[i][1];
        }
        else if(addressMode.equals("10"))
        {
            for (int i=0; i<length; i++) {
                if (memory[i][0].equals(operand)) {
                    tempVal = memory[i][1]+memory[i+1][1];
                }
            }
        }

        int printInt = Integer.parseInt(tempVal,2);
        if(printInt>=32 || printInt<=255)
        {
            char printChar = (char) printInt;
            System.out.println(printChar);
        }
        else
            {
            System.out.println("Out of ASCII Character values!");
        }

    }
}
