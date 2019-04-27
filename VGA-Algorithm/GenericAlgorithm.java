package test;

import java.util.Random;
import java.util.*;

/**
 * �Ŵ��㷨��
 * ������
 * 		1.run ��ʼ���㷨
 * 		2.createBeginningSpecies ������Ⱥ
 * 		3.calRate ����ÿһ�����ֱ�ѡ�еĸ���
 *      4.select  ���̲��� ѡ����Ӧ�ȸߵ�����
 *      5.crossover Ⱦɫ��ɫ����콻��
 *      6.mutate 
 */

public class GenericAlgorithm {
	
	    //��ʼ�Ŵ�
		SpeciesIndividual run(SpeciesPopulation list)
		{		
			//������ʼ��Ⱥ
			createBeginningSpecies(list);
			
			long startTime=System.currentTimeMillis();
			long endTime=System.currentTimeMillis();
			
			//int reproductionCount=0;
			
			while(endTime-startTime<1800000) 
			{	
				//����
				crossover(list);
				
				//reproductionCount++;
				list.display();
				endTime=System.currentTimeMillis();
			}	
			return list.bestIndividual;  //�������Ž�
		}
		
		//������ʼ��Ⱥ
		void createBeginningSpecies(SpeciesPopulation list)
		{
			for(int i=1;i<=Data.SPECIES_NUM;i++)
			{
				SpeciesIndividual species=new SpeciesIndividual(1);//��ģʽ1�����¸���
				
				//�����Ӧ��С��0�������´�������Ϊ��Ӧ����Ϊ����ʱ������Һ���ѡ������������ת
				while(species.fitness<=0) {
					species=new SpeciesIndividual(1);
				}
				
				list.population.add(species);//���¸�����뵽��Ⱥ��
			}
			list.sort(); //������Ⱥ�и�����Ӧ�ȴ�С����Ⱥ�и�������
			list.worstIndividual=list.population.get(0); //����Ⱥ��ʼ���������Ⱥ������������
			list.bestIndividual=list.population.get(Data.SPECIES_NUM-1);
		}

		//����ÿһ���ֱ�ѡ�еĸ���
		void calRate(SpeciesPopulation list)
		{
			//��������Ӧ��
			double totalFitness=0;
			ArrayList<SpeciesIndividual> population=list.population;
			for(int i=0;i<Data.SPECIES_NUM;i++){
				SpeciesIndividual temp=population.get(i); //��ǰ����
				totalFitness += temp.fitness;
			}

			//���㱻ѡ�и���
			for(int i=0;i<Data.SPECIES_NUM;i++){
				SpeciesIndividual temp=population.get(i); //��ǰ����
				temp.rate=temp.fitness/totalFitness;
			}
		}
		
		
		SpeciesIndividual select(SpeciesPopulation list) {
			System.out.println("ѡ��");
			
			//����ÿ�����屻ѡ�еĸ���
			calRate(list);
			
			
			ArrayList<SpeciesIndividual> population=list.population;
			double m=0;
			double r=Math.random();
			for(int i=0;i<Data.SERVICE_NUM;i++){
				m+=population.get(i).rate;
				if(r<=m) {
					return population.get(i);
				}
			}
			return null;
		}
		
		//�������
		void crossover(SpeciesPopulation list) {
			
			System.out.println("����");
			//�Ը���pc���н���
			double rate=Math.random();
			
			if(rate < Data.pc){			
			
				SpeciesIndividual father=select(list),mother=select(list);
				//ͨ��ģʽ0�����հ��Ӹ���
				SpeciesIndividual child=new SpeciesIndividual(0);
				
				boolean fatherChoose=true; //�Ƿ��ֵ��Ӹ�����ѡȡ����λ
				boolean[] isChosen=new boolean[Data.SERVICE_NUM]; //�ж�һ������λ�Ƿ��Ѿ���������������ظ�����
				int countChosen=0; //��¼��ѡ�й��Ļ�����
				int clusterNr=1; //��¼������ر��
				
				//����ֱ�����ӵĻ���λ������
				while(countChosen!=Data.SERVICE_NUM) {
					//����/ĸȾɫ����ֵ��ͬ�Ļ���λ����Ӧ��ID�ռ�����
					for(int i=0;i<Data.SERVICE_NUM;i++) {
						if(isChosen[i]==false) {
							ArrayList<Integer> indexBox=new ArrayList<>();
							indexBox.add(i);
							isChosen[i]=true;
							if(fatherChoose) {
								for(int j=i+1;j<Data.SERVICE_NUM;j++) {
									if(isChosen[j]==false&&father.genes[j]==father.genes[i]) {
										indexBox.add(j);
										isChosen[j]=true;
									}
								}
							}else{
								for(int j=i+1;j<Data.SERVICE_NUM;j++) {
									if(isChosen[j]==false&&mother.genes[j]==mother.genes[i]) {
										indexBox.add(j);
										isChosen[j]=true;
									}
								}
							}
							//���ռ����ID�����Ӧ�ĺ��ӻ���λ�ϣ�ֵΪǰ���clusterNr
							for(int y=0;y<indexBox.size();y++) {
								child.genes[indexBox.get(y)]=clusterNr;
							}
							//��һ�����ֵ��һ������һ�ε���غż�һ
							clusterNr++;
							//���±�������ĺ��ӻ���λ��
							countChosen+=indexBox.size();
						}
					}
				}
				//�¸��嵮�������һ�α���
				mutate(child);
				//���㸳���¸�����Ӧ��
				child.calFitness();
				//����̨�����ʾ�����ӻ����ٽ���
				System.out.println("������Ӧ��:"+child.fitness);
				//�����Ӽ�����Ⱥ��ȥ
				list.add(child);
			}
		}
		
		
		void mutate(SpeciesIndividual individual)
		{	
			double probability=Math.random();
			for(int i=0;i<Data.SERVICE_NUM;i++) {
				if(probability<Data.pm) {
					individual.genes[i]=(int)(Math.random()*Data.CLUSTER_NUM)+1;
				}
				probability=Math.random();
			}
		}
	
}