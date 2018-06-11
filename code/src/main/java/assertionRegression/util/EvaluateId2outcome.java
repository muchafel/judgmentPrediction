package assertionRegression.util;

import java.io.File;

import org.dkpro.tc.ml.report.util.Tc2LtlabEvalConverter;

import de.unidue.ltl.evaluation.core.EvaluationData;
import de.unidue.ltl.evaluation.measure.EvaluationMeasure;
import de.unidue.ltl.evaluation.measures.correlation.PearsonCorrelation;



public class EvaluateId2outcome {
	public static void main(String[] args) throws Exception {
//		EvaluationData<Double> data = Tc2LtlabEvalConverter.convertRegressionModeId2Outcome(new File("/Users/michael/Desktop/toEvaluate/embeddings2.txt"));
//		PearsonCorrelation m  = new PearsonCorrelation(data);
//		System.out.println(m.getResult());
//	
//		
//		EvaluationData<Double> data2 = Tc2LtlabEvalConverter.convertRegressionModeId2Outcome(new File("/Users/michael/Desktop/toEvaluate/5grams.txt"));
//		PearsonCorrelation m2  = new PearsonCorrelation(data2);
//		System.out.println(m2.getResult());
//		
//		EvaluationData<Double> data3 = Tc2LtlabEvalConverter.convertRegressionModeId2Outcome(new File("/Users/michael/Desktop/toEvaluate/trigrams.txt"));
//		PearsonCorrelation m3  = new PearsonCorrelation(data3);
//		System.out.println(m3.getResult());
//		
//		EvaluationData<Double> data4 = Tc2LtlabEvalConverter.convertRegressionModeId2Outcome(new File("/Users/michael/Desktop/toEvaluate/full.txt"));
//		PearsonCorrelation m4  = new PearsonCorrelation(data4);
//		System.out.println(m4.getResult());
//		
//		EvaluationData<Double> data5 = Tc2LtlabEvalConverter.convertRegressionModeId2Outcome(new File("/Users/michael/Desktop/toEvaluate/length.txt"));
//		PearsonCorrelation m5  = new PearsonCorrelation(data5);
//		System.out.println(m5.getResult());
//		
//		EvaluationData<Double> data6 = Tc2LtlabEvalConverter.convertRegressionModeId2Outcome(new File("/Users/michael/Desktop/toEvaluate/sentiment.txt"));
//		PearsonCorrelation m6  = new PearsonCorrelation(data6);
//		System.out.println(m6.getResult());
//		
//		EvaluationData<Double> data7 = Tc2LtlabEvalConverter.convertRegressionModeId2Outcome(new File("/Users/michael/Desktop/toEvaluate/sentimentTrigrams.txt"));
//		PearsonCorrelation m7  = new PearsonCorrelation(data7);
//		System.out.println(m7.getResult());
//		
//		EvaluationData<Double> data8 = Tc2LtlabEvalConverter.convertRegressionModeId2Outcome(new File("/Users/michael/Desktop/toEvaluate/style.txt"));
//		PearsonCorrelation m8  = new PearsonCorrelation(data8);
//		System.out.println(m8.getResult());
//		
//		EvaluationData<Double> data9 = Tc2LtlabEvalConverter.convertRegressionModeId2Outcome(new File("/Users/michael/Desktop/toEvaluate/unigrams.txt"));
//		PearsonCorrelation m9  = new PearsonCorrelation(data9);
//		System.out.println(m9.getResult());
//		
//		EvaluationData<Double> data10 = Tc2LtlabEvalConverter.convertRegressionModeId2Outcome(new File("/Users/michael/Desktop/toEvaluate/US Electoral System2.txt"));
//		PearsonCorrelation m10  = new PearsonCorrelation(data10);
//		System.out.println(m10.getResult());
		
		
		for(File f: new File("src/main/resources/similarityPredictions_deep").listFiles()) {
			EvaluationData<Double> data_z = Tc2LtlabEvalConverter.convertRegressionModeId2Outcome(f);
			PearsonCorrelation m_z  = new PearsonCorrelation(data_z);
			System.out.println(m_z.getResult());
		}
	}
}
