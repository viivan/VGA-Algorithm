package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Data {
	
	static final int SERVICE_NUM=2048;
	static final int CLUSTER_NUM=5; 
	static final int SPECIES_NUM=35; 
	static final int DEVELOP_NUM=200000; 
	static final double pc=0.9;
	static final double pm=0.03;
	static double[][] sMatrix; 
	static int[] model;
	
	static {
		
		//构造标准聚类答案
		model=new int[SERVICE_NUM];
		for(int i=0;i<700;i++) {
			model[i]=1;
		}
		for(int i=700;i<1200;i++) {
			model[i]=2;
		}
		for(int i=1200;i<1700;i++) {
			model[i]=3;
		}
		for(int i=1700;i<2000;i++) {
			model[i]=4;
		}
		for(int i=2000;i<2048;i++) {
			model[i]=5;
		}
		
		//读入相似度矩阵文本文件
		sMatrix=new double[2048][2048];
		String filename="F:/javaWorkplace/test/bin/test/matrix.txt";
		File file = new File(filename);  
        BufferedReader reader = null;  
        try {
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            int line = 0;   
            while ((tempString = reader.readLine()) != null) {  
                  
            	String[] temp=tempString.split(" ");
                for(int i=0;i<2048;i++) {
                	sMatrix[line][i]=Double.parseDouble(temp[i]);
                }
                line++;  
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
	}
	
}