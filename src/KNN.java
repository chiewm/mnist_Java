import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class KNN {

    private final static int K = 3;//KNN 中 K 的值

    private static int[] data2Vec(String fileName){
        int arr[] = new int[32 * 32];

        try{
            FileReader reader = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(reader);

            for(int index = 0; index < 32; index++){
                String str = buffer.readLine();
                int length = str.length();
                for(int i = 0; i < length; i++){
                    String c = str.substring(i, i + 1);
                    arr[32*index+i] = Integer.parseInt(c);
                }
            }


        }
        catch (IOException e){
            e.printStackTrace();
        }


        return arr;

    }


    private static double calDistance(int[] a, int[] b){
        double result = 0.0;
        int temp = 0;
        for(int i = 0; i < a.length; i++){
            temp += (a[i] - b[i])*(a[i] - b[i]);
        }
        result = Math.sqrt(temp);

        return result;
    }


    public static int[] classify(String fileName){
        int result[] = new int[2];

        int arr[] = data2Vec("samples/test/"+fileName);

        result[0] = Integer.parseInt(fileName.split("_")[0]);

        double dis[] = new double[K];
        int num[] = new int[K];

        for(int index = 0; index < K; index++){
            dis[index] = 32;
            num[index] = -1;
        }

        for(int i = 0; i <= 9; i++){
            for(int j = 0; j < 100; j++){
                int temp_arr[] = data2Vec("samples/trainingDigits/"+i+"_"+j+".txt");
                double temp_dis = calDistance(arr, temp_arr);

                for(int k = 0; k < K; k++){
                    if(temp_dis < dis[k]){
                        dis[k] = temp_dis;
                        num[k] = i;
                        break;
                    }
                }
            }
        }

        int count[] = new int[10];

        for(int i = 0; i < 10; i++)
            count[i] = 0;

        for(int i = 0; i < K; i++){
            if(num[i]!=-1)
                count[num[i]]++;
        }

        int max = 0;
        for(int i = 0; i < 10; i++){
            if(count[i]>max){
                max = count[i];
                result[1] = i;
            }
        }

        return result;
    }


}