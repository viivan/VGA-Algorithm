package test;

import java.util.*;
import java.lang.*;

/**
 * ������
 * ������
 * 		1.createByRandomGenes ��ʼ���ֻ���(���)
 * 		2.calFitness ����������Ӧ��
 */

public class SpeciesIndividual {
	
	int[] genes;//��������
	double fitness;//��Ӧ��
	double rate;  //��ѡ������ѡ�еĸ���
	int maxClusterMark;    //���ٸ����
	final int NUM=Data.SERVICE_NUM;
	final int K=Data.CLUSTER_NUM;
	
	SpeciesIndividual(int flag)
	{
		//��ʼ��
		this.genes=new int[NUM];
		this.fitness=0;
		this.maxClusterMark=0;
		rate=0;
		
		//���ݲ�ͬ����ģʽѡ��ͬ�Ĵ�������
		if(flag==0) {
			createByRandomGenes();
		}
		else {
			createGenes();
		}
		
		calFitness();
		System.out.println("������Ӧ�ȣ�"+fitness);
	}
	
	//��ʼ���ֻ���(���)
	void createByRandomGenes()
	{
		//genes=Arrays.copyOf(Data.model,2048);
		
		/*
		for(int i=0;i<500;i++) {
			int index=(int)(Math.random()*2048);
			int temp=genes[index];
			genes[index]=(int)(Math.random()*5)+1;
			while(genes[index]==temp) {
				genes[index]=(int)(Math.random()*5)+1;
			}
		}
		*/
		
		
		for(int i = 0;i < NUM;i++)
		{
			genes[i]=(int)(Math.random()*K)+1;
		}
		
		
		System.out.println("��������");
	}
	
	
	void createGenes() {
		//genes=Arrays.copyOf(Data.model,2048);
		
		ArrayList<Integer> centerBox=new ArrayList<>();
		for(int i=0;i<Data.CLUSTER_NUM;i++) {
			int center=(int)(Math.random()*Data.SERVICE_NUM);
			while(centerBox.contains(center)) {
				center=(int)(Math.random()*Data.SERVICE_NUM);
			}
			centerBox.add(center);
		}
		for(int i=0;i<centerBox.size();i++) {
			genes[centerBox.get(i)]=i+1;
		}
		for(int i=0;i<Data.SERVICE_NUM;i++) {
			if(centerBox.contains(i)) {
				continue;
			}
			double max=-1;
			int maxCenter=0;
			for(int j=0;j<centerBox.size();j++) {
				if(Data.sMatrix[centerBox.get(j)][i]>max) {
					max=Data.sMatrix[centerBox.get(j)][i];
					maxCenter=centerBox.get(j);
				}
			}
			genes[i]=genes[maxCenter];
		}
		
	}
	
	//����������Ӧ��
	void calFitness()
	{
		double Q=0;
		boolean[] isVisited=new boolean[NUM];
		for(int i=0;i<NUM;i++) {
			if(isVisited[i]==false) {
				int temp=genes[i];
				isVisited[i]=true;
				ArrayList<Integer> indexBox=new ArrayList<>(Data.SERVICE_NUM);
				indexBox.add(i);
				for(int j=i+1;j<NUM;j++) {
					if(genes[j]==temp&&isVisited[j]==false) {
						isVisited[j]=true;
						indexBox.add(j);
					}
				}
				double eii=0;
				double aii=0;
				int count=1,count2=1;
				for(int z=0;z<indexBox.size();z++) {
					int zIndex=indexBox.get(z);
					for(int x=z+1;x<indexBox.size();x++) {
						int xIndex=indexBox.get(x);
						eii+=Data.sMatrix[zIndex][xIndex];
						count2++;
					}
					for(int x=0;x<Data.SERVICE_NUM;x++) {
						if(Collections.binarySearch(indexBox, x)>0) {
							continue;
						}
						aii+=Data.sMatrix[zIndex][x];
						count++;
					}
				}
				
				//��ƽ��ֵ
				eii/=count2;
				aii/=count;
				Q+=(indexBox.size()*1.0/Data.SERVICE_NUM)*(eii-aii);   //
			}
		}
		
		this.fitness=Q*10000;
		
	}
	

	//���¸�ÿ������λ����
	public void remark() {
		boolean[] isRemarked=new boolean[NUM];
		int index=1;
		
		for(int i=0;i<NUM;i++) {
			if(isRemarked[i]==false) {
				int temp=genes[i];
				genes[i]=index;
				isRemarked[i]=true;
				for(int j=i+1;j<NUM;j++) {
					if(isRemarked[j]==false&&genes[j]==temp) {
						genes[j]=index;
						isRemarked[j]=true;
					}
				}
				index++;
			}
		}
		this.maxClusterMark=index-1;
	}
	
	public void display() {
		for(int i=0;i<genes.length;i++) {
			System.out.println(genes[i]+" ");
		}
	}
}