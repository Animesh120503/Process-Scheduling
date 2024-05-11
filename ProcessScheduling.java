import java.util.Scanner;

public class ProcessScheduling {
    public static void fcfs() {
        System.out.println("\n------ Implementing FCFS algorithm ------\n");
        int[] AT = new int[5];
        int[] BT = new int[5];
        int[] CT = new int[5];
        int[] TAT = new int[5];
        int[] WT = new int[5];

        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            System.out.print("Enter Arrival Time of Process " + (i + 1) + ": ");
            AT[i] = scanner.nextInt();
            System.out.print("Enter Burst Time of Process " + (i + 1) + ": ");
            BT[i] = scanner.nextInt();
        }

        int totalTAT = 0, totalWT = 0;
        float avgTAT, avgWT;
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                CT[i] = BT[i];
            } else {
                CT[i] = CT[i - 1] + BT[i];
            }
            TAT[i] = CT[i] - AT[i];
            WT[i] = TAT[i] - BT[i];
            totalTAT += TAT[i];
            totalWT += WT[i];
        }

        System.out.println("\nGantt chart:");
        for (int i = 0; i < 5; i++) {
            System.out.print("-------");
        }
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.print("|  P" + (i + 1) + "  ");
        }
        System.out.println("|");
        for (int i = 0; i < 5; i++) {
            System.out.print("-------");
        }
        System.out.println();
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                System.out.printf("%d     %2d", 0, CT[i]);
            } else {
                System.out.printf("     %2d", CT[i]);
            }
        }
        System.out.println();

        System.out.println("\nProcess Chart:");
        System.out.println("  Process  |  AT  |  BT  |  CT  |  TAT  |  WT  ");
        System.out.println("----------------------------------------------");
        for (int i = 0; i < 5; i++) {
            System.out.printf("     P%d    |  %2d  |  %2d  |  %2d  |  %2d   |  %2d  \n", i + 1, AT[i], BT[i], CT[i], TAT[i], WT[i]);
        }

        avgTAT = (float) totalTAT / 5;
        avgWT = (float) totalWT / 5;
        System.out.printf("\nAverage turnaround time: %.2f\nAverage waiting time: %.2f\n", avgTAT, avgWT);
    }

    public static void rr() {
        System.out.println("\n------ Implementing RR algorithm ------\n");
        int[] AT = new int[5];
        int[] BT = new int[5];
        int[] CT = new int[5];
        int[] TAT = new int[5];
        int[] WT = new int[5];
        int[] RT = new int[5];
        int[] tempBT = new int[5];

        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            System.out.print("Enter Arrival Time of Process " + (i + 1) + ": ");
            AT[i] = scanner.nextInt();
            System.out.print("Enter Burst Time of Process " + (i + 1) + ": ");
            BT[i] = scanner.nextInt();
            tempBT[i] = BT[i];
            RT[i] = -1;
        }

        System.out.print("\nEnter time quantum of algorithm: ");
        int t = scanner.nextInt();
        int totalTAT = 0, totalWT = 0, totalRT = 0;
        float avgTAT, avgWT, avgRT;

        int currentTime = 0;
        int doneProcesses = 0;

        int[] currentTimeArray = new int[5];
        System.out.println("\nGantt chart:");
        System.out.println("-------------------------------------------------------");
        int index = 0;
        while (doneProcesses < 5) {
            int done = 1;

            for (int i = 0; i < 5; i++) {
                if (AT[i] <= currentTime && tempBT[i] > 0) {
                    done = 0;
                    if (RT[i] == -1) {
                        RT[i] = currentTime - AT[i];
                        if (RT[i] < 0) {
                            RT[i] = 0;
                        }
                    }
                    int waitTimeTemp = currentTime - AT[i] - RT[i];
                    if (waitTimeTemp < 0) {
                        waitTimeTemp = 0;
                    }
                    if (tempBT[i] > t) {
                        System.out.print("|  P" + (i + 1) + "  |");
                        currentTimeArray[index++] = currentTime;
                        currentTime += t;
                        tempBT[i] -= t;
                        WT[i] += waitTimeTemp;
                    } else {
                        System.out.print("|  P" + (i + 1) + "  |");
                        currentTimeArray[index++] = currentTime;
                        currentTime += tempBT[i];
                        CT[i] = currentTime;
                        TAT[i] = CT[i] - AT[i];
                        totalTAT += TAT[i];
                        totalRT += RT[i];
                        doneProcesses++;
                        tempBT[i] = 0;
                    }
                }
            }
        }
        currentTimeArray[index++] = currentTime;
        System.out.println("\n-------------------------------------------------------");
        for (int i = 0; i < index; i++) {
            if (i == 0)
                System.out.printf("%2d     ", currentTimeArray[i]);
            else
                System.out.printf("%2d      ", currentTimeArray[i]);
        }
        System.out.println();

        for (int i = 0; i < 5; i++) {
            WT[i] = TAT[i] - BT[i];
            totalWT += WT[i];
        }

        System.out.println("\nProcess Chart:");
        System.out.println("  Process  |  AT  |  BT  |  CT  |  TAT  |  WT  |  RT  ");
        System.out.println("-------------------------------------------------------");
        for (int i = 0; i < 5; i++) {
            System.out.printf("     P%d    |  %2d  |  %2d  |  %2d  |  %2d   |  %2d  |  %2d\n", i + 1, AT[i], BT[i], CT[i], TAT[i], WT[i], RT[i]);
        }

        avgTAT = (float) totalTAT / 5;
        avgWT = (float) totalWT / 5;
        avgRT = (float) totalRT / 5;

        System.out.printf("\nAverage turnaround time: %.2f\nAverage waiting time: %.2f\nAverage response time: %.2f\n", avgTAT, avgWT, avgRT);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("-------------CHOICES-------------");
        System.out.println("1. First Come First Served (FCFS)");
        System.out.println("2. Round Robin (RR)");
        System.out.println("3. Exit from program.");
        System.out.print("Enter your choice: ");
        choice = scanner.nextInt();
        while (choice != 3) {
            switch (choice) {
                case 1:
                    fcfs();
                    break;
                case 2:
                    rr();
                    break;
                default:
                    System.out.println("Please enter a valid choice.");
                    break;
            }
            System.out.println("\n-------------CHOICES-------------");
            System.out.println("1. First Come First Served (FCFS)");
            System.out.println("2. Round Robin (RR)");
            System.out.println("3. Exit from program.");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
        }
        System.out.println("Program exited.");
    }
}
