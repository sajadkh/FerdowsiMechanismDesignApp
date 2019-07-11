package algorithms.assignment.envyFree;

import java.util.ArrayList;
import java.util.List;

public class Envy {

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

    private enum Status {
        Envy(0), Envy_free(2), Weakly_envy_free(1);
        private int Value;

        Status(int value) {
            this.Value = value;
        }

        public int getValue() {
            return Value;
        }

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

    public String Execute(double[][] assigmentMatrix, int[][] preferMatrix) {
        StringBuilder outPut = new StringBuilder();
        List<double[][]> sortedMatrixs = Sort(assigmentMatrix, preferMatrix);
        List<Result> resultForRows = Compare(sortedMatrixs);
        Status statusForMatrix = CompareMatrix(resultForRows);

        outPut.append(Print.PrepareForPrint(assigmentMatrix,preferMatrix,sortedMatrixs,resultForRows,statusForMatrix));


        return outPut.toString();
    }

    //region Sort
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
