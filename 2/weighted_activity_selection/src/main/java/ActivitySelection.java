import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

class ActivitySelection {
    public static int maxWeight(Activity[] activities) {
        int n = activities.length;
        Arrays.sort(activities, Comparator.comparingInt(a -> a.end));

        int[] dp = new int[n];
        dp[0] = activities[0].weight;

        for (int i = 1; i < n; i++) {
            int maxWeight = activities[i].weight;
            int compatibleActivity = findCompatibleActivity(activities, i);

            if (compatibleActivity != -1) {
                maxWeight += dp[compatibleActivity];
            }

            dp[i] = Math.max(maxWeight, dp[i - 1]);
        }

        return dp[n - 1];
    }

    private static int findCompatibleActivity(Activity[] activities, int current) {
        for (int i = current - 1; i >= 0; i--) {
            if (activities[i].end <= activities[current].start) {
                return i;
            }
        }
        return -1;
    }

    private static String generateOutputFilePath(String inputFilePath) {
        Path path = Paths.get(inputFilePath);
        String fileNameWithExtension = path.getFileName().toString();

        String outputFileName;
        String fileName = path.getFileName().toString();
        String fileExtension = "";
        int dotIndex = fileNameWithExtension.lastIndexOf(".");
        if (dotIndex >= 0) {
            fileName = fileNameWithExtension.substring(0, dotIndex);
            fileExtension = fileNameWithExtension.substring(dotIndex + 1);
        }

        outputFileName = String.format("%s_19016713.%s", fileName, fileExtension);

        return path.getParent().resolve(outputFileName).toString();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide input file path as command-line argument.");
            return;
        }
        String inputFilePath = args[0];
        String outputFilePath = generateOutputFilePath(inputFilePath);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

            int n = Integer.parseInt(reader.readLine());
            Activity[] activities = new Activity[n];

            for (int i = 0; i < n; i++) {
                String[] activityData = reader.readLine().split(" ");
                int start = Integer.parseInt(activityData[0]);
                int end = Integer.parseInt(activityData[1]);
                int weight = Integer.parseInt(activityData[2]);
                activities[i] = new Activity(start, end, weight);
            }

            int maxWeight = maxWeight(activities);
            writer.write(Integer.toString(maxWeight));

            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

