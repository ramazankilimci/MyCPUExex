public class General {

    public static void print2DArray(Object[][] myObjectArr) {
        for (int i = 0; i < myObjectArr.length; i++) {
            for (int j = 0; j < myObjectArr[i].length; j++)
                System.out.print("[" + i + "][" + j + "] : " + myObjectArr[i][j] + " ");
            System.out.println();
        }
    }
}
