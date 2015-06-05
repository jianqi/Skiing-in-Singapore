package redmart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author JianQi
 */
public class RedMart {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try{
            File file = new File("map.txt");
            Scanner sc = new Scanner(file);
            int x = sc.nextInt();
            int y = sc.nextInt();
            
            int[][] map = new int[x][y];
            int[][] longestPathCount = new int[x][y];
            int[][] steepestCount = new int[x][y];
            
            //init matrix to -1 as there are cases with path count  = 0
            for(int i = 0 ; i < longestPathCount.length ; i++){
                for(int j = 0 ; j < longestPathCount[i].length; j++){
                    longestPathCount[i][j] = -1;
                }
            }
            
            for(int i = 0 ; i < x ; i++){
                for(int j = 0 ; j < y ; j++){
                    map[i][j] = sc.nextInt();
                }
            }
            
            traverseMap(map, longestPathCount, steepestCount);
            
        }catch(FileNotFoundException fnf){
            fnf.printStackTrace();
        }
    }

    private static void traverseMap(int[][] map, int[][] longestPathCount, int[][] steepestCount) {
        //  ans[0]  -   longest answer
        //  ans[1]  -   steepest answer
        int[] ans = new int[2];
        for(int i = 0 ; i < map.length ; i ++){
            for(int j = 0 ; j < map[i].length ; j++){
                //only visit node that has not been visited
                if(longestPathCount[i][j] == -1){
                    int longest = 0;              
                    int stepCount = 0;

                    //up                
                    if( i - 1 >= 0){
                        if(map[i][j] > map[i-1][j]){
                           
                            stepCount = traverseMap2(map, longestPathCount, steepestCount, i-1, j);
                            int steep = map[i][j] - map[i-1][j] + steepestCount[i-1][j];
                            stepCount += 1;
                            calculateAndGetFindBiggest(stepCount, steep, longestPathCount, steepestCount, i, j, ans);
                        }
                    }
                    //left
                    if( j - 1 >= 0){
                        if(map[i][j] > map[i][j-1]){
                            
                            stepCount = traverseMap2(map, longestPathCount, steepestCount, i, j-1);
                            int steep = map[i][j] - map[i][j-1] + steepestCount[i][j-1];
                            stepCount += 1;
                            calculateAndGetFindBiggest(stepCount, steep, longestPathCount, steepestCount, i, j, ans);
                        }
                    }
                    //down
                    if( i + 1 <= map.length-1){
                        if(map[i][j] > map[i+1][j]){
                            
                            stepCount = traverseMap2(map, longestPathCount, steepestCount, i+1, j);
                            int steep = map[i][j] - map[i+1][j] + steepestCount[i+1][j];
                            stepCount += 1;
                            calculateAndGetFindBiggest(stepCount, steep, longestPathCount, steepestCount, i, j, ans);
                        }
                    }
                    //right
                    if( j + 1 <= map.length-1){
                        if(map[i][j] > map[i][j+1]){
                            
                            stepCount = traverseMap2(map, longestPathCount, steepestCount, i, j+1);
                            int steep = map[i][j] - map[i][j+1] + steepestCount[i][j+1];
                            stepCount += 1;
                            calculateAndGetFindBiggest(stepCount, steep, longestPathCount, steepestCount, i, j, ans);
                        }
                    }

                    
                }
                
            }
        }
        System.out.println(ans[0]+1);
        System.out.println(ans[1]);
                
    }
    
    private static void calculateAndGetFindBiggest(int stepCount, int steep, int[][] longestPathCount, int[][] steepestCount, int i, int j, int[] ans){
        if(stepCount > longestPathCount[i][j]){
            longestPathCount[i][j] = stepCount;
            steepestCount[i][j] = steep;

            if(stepCount > ans[0]){
                ans[0] = stepCount;
                ans[1] = steep;
            }else if(stepCount == ans[0]){
                ans[1] = steep > ans[1] ? steep: ans[1];
            }                                        
        }
        else if(stepCount == longestPathCount[i][j]){
            steepestCount[i][j] = steep >  steepestCount[i][j] ? steep :  steepestCount[i][j];

            if(stepCount > ans[0]){
                ans[0] = stepCount;
                ans[1] = steep;
            }else if(stepCount == ans[0]){
                ans[1] = steep > ans[1] ? steep: ans[1];
            } 
        }
        
    }
    private static int traverseMap2(int[][] map, int[][] longestPathCount, int[][] steepestCount, int x, int y) {
        
        int stepCount = 0;
        
        //return the longest path count stored.
        if(longestPathCount[x][y] != -1){
            return longestPathCount[x][y];
        }
        if( x - 1 >= 0){
            if(map[x][y] > map[x-1][y]){
                //retreive step count
                
                stepCount = traverseMap2(map, longestPathCount, steepestCount, x-1, y);
                int steep = map[x][y] - map[x-1][y] + steepestCount[x-1][y];
                //increase step taken
                stepCount += 1;
                //store longest step count
                calculate(longestPathCount, steepestCount, x, y, stepCount, steep);
                
            }
        }
        //up
        if( y - 1 >= 0){
            if(map[x][y] > map[x][y-1]){
                
                stepCount =traverseMap2(map, longestPathCount, steepestCount, x, y-1);
                int steep = map[x][y] - map[x][y-1] + steepestCount[x][y-1];
                stepCount += 1;
                calculate(longestPathCount, steepestCount, x, y, stepCount, steep);
            }
        }
        //down
        if( x + 1 <= map.length-1){
            if(map[x][y] > map[x+1][y]){
                
                stepCount = traverseMap2(map, longestPathCount, steepestCount, x+1, y);
                int steep = map[x][y] - map[x+1][y] + steepestCount[x+1][y];
                stepCount += 1;
                calculate(longestPathCount, steepestCount, x, y, stepCount, steep);
            }
        }
        //right
        if( y + 1 <= map.length-1){
            if(map[x][y] > map[x][y+1]){
                
                stepCount = traverseMap2(map, longestPathCount, steepestCount, x, y+1);
                int steep = map[x][y] - map[x][y+1] + steepestCount[x][y+1];
                stepCount += 1;
                calculate(longestPathCount, steepestCount, x, y, stepCount, steep);
            }
        }
        
        // didnt traverse to any other node
        if(stepCount == 0)
            longestPathCount[x][y] = stepCount;
        return longestPathCount[x][y];
    }
    
    public static void calculate(int[][] longestPathCount, int[][] steepestCount, int x, int y, int stepCount, int steep){
        if(stepCount > longestPathCount[x][y]){
            longestPathCount[x][y] = stepCount;
            steepestCount[x][y] = steep;
        }
        else if(stepCount == longestPathCount[x][y]){
            steepestCount[x][y] = steep > steepestCount[x][y] ? steep : steepestCount[x][y];
        }
    }
    
}
