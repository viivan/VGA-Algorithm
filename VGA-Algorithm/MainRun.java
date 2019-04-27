package test;

/**
 * 主函数运行类
 */

public class MainRun {

	public static void main(String[] args) {
		//long startTime=System.currentTimeMillis();
		
		//创建遗传算法驱动对象
		GenericAlgorithm GA=new GenericAlgorithm();
		
		//创建初始种群
		SpeciesPopulation speciesPopulation = new SpeciesPopulation();

		//开始遗传算法（选择算子、交叉算子、变异算子）
		SpeciesIndividual bestRate=GA.run(speciesPopulation);

		//显示最优聚类成果
		bestRate.display();
		
		//long endTime=System.currentTimeMillis();
		//System.out.print("\n消耗时间"+(endTime-startTime));
	}

}