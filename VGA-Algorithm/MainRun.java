package test;

/**
 * ������������
 */

public class MainRun {

	public static void main(String[] args) {
		//long startTime=System.currentTimeMillis();
		
		//�����Ŵ��㷨��������
		GenericAlgorithm GA=new GenericAlgorithm();
		
		//������ʼ��Ⱥ
		SpeciesPopulation speciesPopulation = new SpeciesPopulation();

		//��ʼ�Ŵ��㷨��ѡ�����ӡ��������ӡ��������ӣ�
		SpeciesIndividual bestRate=GA.run(speciesPopulation);

		//��ʾ���ž���ɹ�
		bestRate.display();
		
		//long endTime=System.currentTimeMillis();
		//System.out.print("\n����ʱ��"+(endTime-startTime));
	}

}