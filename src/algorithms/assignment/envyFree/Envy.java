package algorithms.assignment.envyFree;


import java.util.ArrayList;
import java.util.List;

public class Envy {

    /**
     * کلاسی برای ذخیریه نتیجه مقایسه هر دو سطر یک ماتریس یا هر دو عامل می باشد
     * این کلاس دارای متغیرهای agent  و virvalAgent‌ می باشد که به ترتیب نشان دهنده عامل اصلی و عامل مقایسه شده می باشد
     * نتیجه مقایسه در متغیر Ressult ذخیره می شود که متغیر Result‌ از نوع Status‌ می باشد که در کلاس Status ‌بیشتر مورد بررسی قرار می گیرد
     */
    private class Result {
        int Agent;
        int virvalAgent;
        Status Ressult;

        Result(int agent, int virvalAgent, Status ressult) {
            Agent = agent;
            this.virvalAgent = virvalAgent;
            Ressult = ressult;
        }
    }

    /**
     * نوع داده ای برای نگه داری ۳ وضعیت Envy ، Weakly_envy_free ، Envy_free می باشد.
     * وضعیت Envy که با کد صفر مشخص می شود برای زمانی است که عامل نسبت به عامل‌ های دیگر مغلوب شده باشد.
     * وضعیت Weakly_envy_free که با کد ۱ مشخص می شود برای زمانی است که عامل حداقل با یک عامل دیگر برابر باشد و نبست به دیگر عامل ها غالب باشد.
     * وضعیت Envy_free که با کد ۲ مشخص می شود برای زمانی است که عامل نسبت به دیگر عامل ها غالب باشد.
     */
    private enum Status {
        Envy(0), Envy_free(2), Weakly_envy_free(1);
        private int Value;

        Status(int value) {
            this.Value = value;
        }

        public int getValue() {
            return Value;
        }

        // به منظور برگرداندن وضعیت با استفاده از کد وضعیت
        public static Status getStatus(int code) {
            Status status = null;
            for (Status item : values()) {
                if (item.Value == code) {
                    status = item;
                }
            }

            return status;
        }
    }

    /**
     * تنها تابع کلاس Envy می باشد که از خارج کلاس در دسترس می باشد و در واقع تابعی است که مدیریت روال برنامه را بر عهدا دارد.
     * @param assigmentMatrix ماتریس تخصیص
     * @param preferMatrix ماتریس ترجیحات
     *   در مرحله اول تعدادی ماتریس تخصیص مرتب شده براساس ماتریس ترجیحات ایجاد می کند و در sortedMatrixs نگه می دارد.
     *                     در مرحله دوم سطر های هر ماتریس را با سطر عامل مقایسه کرده و نتایج را در لیستی به نام resultForRows ذخیره می کند.
     * در مرحله سوم بررسی می کنیم ماتریس تخصیص ارایه شده کدام یک از ۳ وضعیت یاد شده در بالا را داراست. و در متغیر statusForMatrix ذخیره می کند.
     *
     *   در نهایت آماده رشته ای با ترتیب خاص برای  بر گرداندن ایجاد می کند.
     * @return * برگرداندن رشته ای با فرمت خاص
     */
    public String Execute(double[][] assigmentMatrix, int[][] preferMatrix) {
        StringBuilder outPut = new StringBuilder();
        //مرحله اول
        List<double[][]> sortedMatrixs = Sort(assigmentMatrix, preferMatrix);
        //مرحله دوم
        List<Result> resultForRows = Compare(sortedMatrixs);
        // مرحله سوم
        Status statusForMatrix = CompareMatrix(resultForRows);

        // ایجاد رشته برای خروجی
        outPut.append(Print.PrepareForPrint(assigmentMatrix,preferMatrix,sortedMatrixs,resultForRows,statusForMatrix));


        return outPut.toString();
    }

    //region Sort

    /**
     *
     * @param assigmentMatrix ماتریس تخصیص
     * @param preferMatrix ماتریس ترجیحات
     *
     * @return *
     * به تعداد سطر های ماتریس ترجیحات تعدادی ماتریس تخصیص مرتب شده براساس ماتریس ترجیحات را بر می گرداند
     *
     */
    private List<double[][]> Sort(double[][] assigmentMatrix, int[][] preferMatrix) {
        List<double[][]> sortedMatrixs = new ArrayList<>();
        for (int[] row : preferMatrix) {

            int j = 0;
            double[][] newNatrix = CreateMatrix(assigmentMatrix.length,assigmentMatrix[0].length);
            for (int index : row) {

                for (int i = 0; i < assigmentMatrix.length; i++) {
                    newNatrix[i][j] = assigmentMatrix[i][index];
                }
                j++;
            }
            sortedMatrixs.add(newNatrix);
        }

        return sortedMatrixs;
    }

    /**
     *
     * @param rowNumber  تعداد سطر ها ماتریس مورد نظر
     * @param columnNumber تعداد ستون های ماتریس مورد نظر
     * @return * ماتریسی با ابعاد مورد نظر و مقدار اولیه صفر ایجاد می کند
     */
    private double[][] CreateMatrix(int rowNumber , int columnNumber)
    {
        double[][] tempMatrix = new double[rowNumber][];
        for (int i=0;i<rowNumber;i++)
        {
            tempMatrix[i] =new double[columnNumber];
        }

        return tempMatrix;
    }
    //endregion

    //region Compare

    /**
     * در این تابع سطرهای هر ماتریس را جدا نموده و به همراه سطر عامل مورد نظر ( که همان سطر با شماره ماتریس می باشد) را برای مشخص شدن وضعیت سطر عامل نسبت به دیگر سطرها را به تابع ()CompareAgents می فرستد و نتیجه را در لیستی از Result به نام result اضافه می کند.
     * @param sortedMatrixs لیستی از ماتریس مرتب شده
     * @return *لیت result را برمی گرداند
     */
    private List<Result> Compare(List<double[][]> sortedMatrixs) {
        List<Result> results = new ArrayList<>();
        for (int agent = 0; agent < sortedMatrixs.size(); agent++) {
            double[][] matrix = sortedMatrixs.get(agent);

            for (int virval = 0; virval < matrix.length; virval++) {
                double[] ComparativeRow = matrix[virval];

                if (virval != agent) {
                    double[] MainRow = matrix[agent];
                    Status status = CompareAgents(MainRow, ComparativeRow);
                    results.add(new Result(agent, virval, status));
                }
            }

        }
        return results;
    }

    /**
     * ابتدا مورد اول هر دو تخصیص را مقایسه می کند :
     * اگر تخصیص عامل مورد نظر ار تخصیص دیگر بزرگتر بود آنگاه عامل مورد نظر نسبت به عامل دیگر Envy_free‌ می باشد و نتیجه را بر می گردانیم.
     * اگر تخصیص عامل مورد نظر از تخصیص دیگر کوچکتر بود آنگاه عامل مورد نظر نسبت به عامل دیگر Envy می باشد و نتیجه را بر می گردانیم.
     * اگر دو تخصیص برابر بود مجموع دو مورد اول را طبق الگوریتم بالا مقایسه می کنیم تا زمانی که در یکی از حالات بالا متوقف شویم یا تا انتهای تخصیص را بررسی کنیم که در حات دوم نتیجه می گیریم دو تخصیص نسبت به هم Weakly_envy_free می باشند و نتیجه را بر کی گردانیم.
     * @param MainRow تخصیص عامل مورد نظر
     * @param ComparativeRow عاملی که قرار است با عامل مورد نظر مقایسه شود
     *
     * @return * برگرداندن وضعیت دو عامل
     */
    private Status CompareAgents(double[] MainRow, double[] ComparativeRow) {
        double mainAgent = 0;
        double comparativeAgent = 0;
        Status status = null;
        for (int i = 0; i < MainRow.length - 1; i++) {
            mainAgent += MainRow[i];
            comparativeAgent += ComparativeRow[i];
            int compareResult = Double.compare(mainAgent, comparativeAgent);
            if (compareResult > 0) {
                status = Status.Envy_free;
                break;
            } else if (compareResult < 0) {
                status = Status.Envy;
                break;
            } else // if == 0
            {
                continue;
            }
        }
        if (status == null) {
            status = Status.Weakly_envy_free;
        }
        return status;
    }
    //endregion

    //region CompareMatrix

    /**
     * در این تابع وضعیت عامل ها را بررسی کرده و وضیعیت با کمترین کد را به عنوان وضعیت ماتریس انتخاب می کنیم که در نوع داده ای Status‌ کد ها را بررسی کردیم
     * @param resultForRows نتیجه مقایسه سطر ها
     * @return * وضعیت ماتریس
     */
    private Status CompareMatrix(List<Result> resultForRows) {
        int minimumState = 10;
        for (Result row : resultForRows) {

            if (row.Ressult.getValue() < minimumState) {
                minimumState = row.Ressult.Value;
            }
        }

        return Status.getStatus(minimumState);
    }
    //endregion

    /**
     * در این کلاس صرفا نتیجه نهایی را برای چا آماده می کنیم که به ترتیتب زیر موارد را چاپ می کند
     * ابتدا ماتریس تخصیص را چا می کند
     * سپس ماتریس ترجیحات چاپ می شود
     * سپس به ازای هر ماتریس مرتب شده
     * ابتدا ماتریس مرتب شده آن  چاپ می شود
     * سپس وضعیت عامل آن ماتریس نسبت به دیگر عامل ها
     * در ادامه وضعیت کلی آن عامل چاپ می شود
     * در انتها وضعیت کلی ماتریس چاپ می شود
     */
    private static class Print {
        static StringBuilder PrepareForPrint(double[][] assigmentMatrix, int[][] preferMatrix,
                                             List<double[][]> sortedMatrixs, List<Result> resultForRows,
                                             Status statusForMatrix) {
            StringBuilder outPut = new StringBuilder();
            outPut.append("assigmentMatrix : \n");
            outPut.append(printMatrix(assigmentMatrix));
            outPut.append("\n");
            outPut.append("preferMatrix : \n");
            outPut.append(printMatrix(preferMatrix));
            outPut.append("********************************************************\n");

            for (int matrixNumber=0;matrixNumber<sortedMatrixs.size();matrixNumber++)
            {
                double[][] matrix = sortedMatrixs.get(matrixNumber);
                outPut.append("P(").append(matrixNumber).append(") = \n");
                outPut.append(printMatrix(matrix));
                outPut.append(printResults(resultForRows,matrixNumber));
                outPut.append("----------------------------------------------------\n");

            }
            outPut.append("********************************************************\n");

            outPut.append("The Assignment of P is : ").append(statusForMatrix).append("\n");



            return outPut;
        }

        private static StringBuilder printResults(List<Result> resultForRows , int agent )
        {
            StringBuilder print = new StringBuilder();
            print.append("\n");

            int minimumStatus = 10;
            for (Result result : resultForRows) {
                if (result.Agent == agent)
                {
                    print.append("Agent").append(agent).append(" to ").append("Agent").append(result.virvalAgent)
                            .append(" = ").append(result.Ressult).append("\n");
                    if (minimumStatus > result.Ressult.getValue())
                    {
                        minimumStatus = result.Ressult.getValue();
                    }
                }

            }
            print.append("\n");

            print.append("General State of Agent").append(agent).append(" ").append(Status.getStatus(minimumStatus)).append("\n");


            return print;
        }

        private static StringBuilder printMatrix(double[][] matrix) {
            StringBuilder print = new StringBuilder();

            for (double[] row : matrix) {
                for (double index : row) {
                    print.append(index).append(" ");
                }
                print.append("\n");
            }

            return print;
        }
        private static StringBuilder printMatrix(int[][] matrix) {
            StringBuilder print = new StringBuilder();

            for (int[] row : matrix) {
                for (int index : row) {
                    print.append(index).append(" ");
                }
                print.append("\n");
            }

            return print;
        }
    }

}
