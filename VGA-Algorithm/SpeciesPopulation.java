package test;
import java.util.*;

public class SpeciesPopulation {
	
	ArrayList<SpeciesIndividual> population; //存放个体
	SpeciesIndividual worstIndividual; 
	SpeciesIndividual bestIndividual;
	
	SpeciesPopulation()
	{
		population=new ArrayList<>(Data.SERVICE_NUM);
		worstIndividual=bestIndividual=null;
	}
	
	//添加生物
	void add(SpeciesIndividual species)
	{
		//加入新个体就要扔掉最差的个体，保证整个种群朝着好的方向进化
		if(species.fitness>worstIndividual.fitness) {
			population.add(species);
			remove();
			
			//如果新加入的个体比种群中最好的个体还要好，则标记它
			if(species.fitness>bestIndividual.fitness) {
				bestIndividual=species;
			}
		}
	}
	
	//淘汰最差个体
	void remove() {
		population.remove(worstIndividual);
		sort();
		//排序过后最差的个体就会出现在种群第一个
		worstIndividual=population.get(0);
	}
	
	//打印种群信息，便于参考
	void display() {
		System.out.println(worstIndividual.fitness+"   "+bestIndividual.fitness);
	}
	
	void sort() {
		Collections.sort(population,new Comparator<SpeciesIndividual>() {
			@Override
			public int compare(SpeciesIndividual lhs, SpeciesIndividual rhs) {
				if ( lhs.fitness > rhs.fitness ) {
					return 1;
				} else {
					return -1;
				}
			}
		});
	}
}

