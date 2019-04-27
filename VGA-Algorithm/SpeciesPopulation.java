package test;
import java.util.*;

public class SpeciesPopulation {
	
	ArrayList<SpeciesIndividual> population; //��Ÿ���
	SpeciesIndividual worstIndividual; 
	SpeciesIndividual bestIndividual;
	
	SpeciesPopulation()
	{
		population=new ArrayList<>(Data.SERVICE_NUM);
		worstIndividual=bestIndividual=null;
	}
	
	//�������
	void add(SpeciesIndividual species)
	{
		//�����¸����Ҫ�ӵ����ĸ��壬��֤������Ⱥ���źõķ������
		if(species.fitness>worstIndividual.fitness) {
			population.add(species);
			remove();
			
			//����¼���ĸ������Ⱥ����õĸ��廹Ҫ�ã�������
			if(species.fitness>bestIndividual.fitness) {
				bestIndividual=species;
			}
		}
	}
	
	//��̭������
	void remove() {
		population.remove(worstIndividual);
		sort();
		//����������ĸ���ͻ��������Ⱥ��һ��
		worstIndividual=population.get(0);
	}
	
	//��ӡ��Ⱥ��Ϣ�����ڲο�
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

