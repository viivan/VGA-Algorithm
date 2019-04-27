package test;

import java.util.Random;
import java.util.*;

/**
 * 遗传算法类
 * 包含：
 * 		1.run 开始跑算法
 * 		2.createBeginningSpecies 创建种群
 * 		3.calRate 计算每一种物种被选中的概率
 *      4.select  轮盘策略 选择适应度高的物种
 *      5.crossover 染色体色体变异交叉
 *      6.mutate 
 */

public class GenericAlgorithm {
	
	    //开始遗传
		SpeciesIndividual run(SpeciesPopulation list)
		{		
			//创建初始种群
			createBeginningSpecies(list);
			
			long startTime=System.currentTimeMillis();
			long endTime=System.currentTimeMillis();
			
			//int reproductionCount=0;
			
			while(endTime-startTime<1800000) 
			{	
				//交叉
				crossover(list);
				
				//reproductionCount++;
				list.display();
				endTime=System.currentTimeMillis();
			}	
			return list.bestIndividual;  //返回最优解
		}
		
		//创建初始种群
		void createBeginningSpecies(SpeciesPopulation list)
		{
			for(int i=1;i<=Data.SPECIES_NUM;i++)
			{
				SpeciesIndividual species=new SpeciesIndividual(1);//以模式1创建新个体
				
				//如果适应度小于0，则重新创建，因为适应度若为负数时则会扰乱后续选择算子正常运转
				while(species.fitness<=0) {
					species=new SpeciesIndividual(1);
				}
				
				list.population.add(species);//将新个体加入到种群中
			}
			list.sort(); //按照种群中个体适应度大小给种群中个体排序
			list.worstIndividual=list.population.get(0); //在种群初始化后更新种群的最差个体引用
			list.bestIndividual=list.population.get(Data.SPECIES_NUM-1);
		}

		//计算每一物种被选中的概率
		void calRate(SpeciesPopulation list)
		{
			//计算总适应度
			double totalFitness=0;
			ArrayList<SpeciesIndividual> population=list.population;
			for(int i=0;i<Data.SPECIES_NUM;i++){
				SpeciesIndividual temp=population.get(i); //当前个体
				totalFitness += temp.fitness;
			}

			//计算被选中概率
			for(int i=0;i<Data.SPECIES_NUM;i++){
				SpeciesIndividual temp=population.get(i); //当前个体
				temp.rate=temp.fitness/totalFitness;
			}
		}
		
		
		SpeciesIndividual select(SpeciesPopulation list) {
			System.out.println("选择");
			
			//更新每个个体被选中的概率
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
		
		//交叉操作
		void crossover(SpeciesPopulation list) {
			
			System.out.println("交叉");
			//以概率pc进行交叉
			double rate=Math.random();
			
			if(rate < Data.pc){			
			
				SpeciesIndividual father=select(list),mother=select(list);
				//通过模式0创建空白子个体
				SpeciesIndividual child=new SpeciesIndividual(0);
				
				boolean fatherChoose=true; //是否轮到从父亲中选取基因位
				boolean[] isChosen=new boolean[Data.SERVICE_NUM]; //判断一个基因位是否已经被处理过，避免重复处理
				int countChosen=0; //记录被选中过的基因数
				int clusterNr=1; //记录最新类簇编号
				
				//运行直到孩子的基因位被填满
				while(countChosen!=Data.SERVICE_NUM) {
					//将父/母染色体中值相同的基因位所对应的ID收集起来
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
							//将收集后的ID们填到对应的孩子基因位上，值为前面的clusterNr
							for(int y=0;y<indexBox.size();y++) {
								child.genes[indexBox.get(y)]=clusterNr;
							}
							//下一次填的值加一，即下一次的类簇号加一
							clusterNr++;
							//更新被处理过的孩子基因位数
							countChosen+=indexBox.size();
						}
					}
				}
				//新个体诞生后进行一次变异
				mutate(child);
				//计算赋予新个体适应度
				child.calFitness();
				//控制台输出显示，可视化跟踪进度
				System.out.println("孩子适应度:"+child.fitness);
				//将孩子加入种群中去
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