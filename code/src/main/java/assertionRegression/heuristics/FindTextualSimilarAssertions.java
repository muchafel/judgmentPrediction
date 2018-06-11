package assertionRegression.heuristics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.bytedeco.javacpp.RealSense.context;
import org.dkpro.tc.api.type.TextClassificationOutcome;

import assertionRegression.annotationTypes.Issue;
import assertionRegression.io.AssertionReader;
import de.tudarmstadt.ukp.dkpro.core.api.resources.DkproContext;
import dkpro.similarity.algorithms.api.SimilarityException;
import dkpro.similarity.algorithms.api.TextSimilarityMeasure;
import dkpro.similarity.algorithms.lexical.ngrams.WordNGramJaccardMeasure;

public class FindTextualSimilarAssertions {


	
	
	public static void main(String[] args) throws ResourceInitializationException, IOException, SimilarityException {
		
		String baseDir = DkproContext.getContext().getWorkspace().getAbsolutePath();
		System.out.println("DKPRO_HOME: " + baseDir);
		
		ArrayList<String> issues = new ArrayList<String>(Arrays.asList(
				"Climate Change"
				, 
				"Vegetarian & Vegan Lifestyle"
				,
				"Black Lives Matter"
				, 
				"Creationism in school curricula"
				, 
				"Foreign Aid"
				, 
				"Gender Equality"
				, 
				"Gun Rights"
				,
				"Legalization of Marijuana"
				, 
				"Legalization of Same-sex Marriage"
				, 
				"Mandatory Vaccination"
				, 
				"Media Bias"
				,
				"Obama Care -- Affordable Health Care Act"
				,
				"US Engagement in the Middle East"
				,
				"US Electoral System"
				,
				"US Immigration"
				, 
				"War on Terrorism"
				));
		
		TextSimilarityMeasure measure= new WordNGramJaccardMeasure(1);
				CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(AssertionReader.class,
						AssertionReader.PARAM_SOURCE_LOCATION, baseDir+"/UCI/data/data.tsv", AssertionReader.PARAM_LANGUAGE, "en",
						AssertionReader.PARAM_TARGETCLASS, "Agreement");
				Map<String,Double> pair2Sim= new HashMap<String,Double>();
				for(String issue: issues) {
					for (JCas jcas_1 : new JCasIterable(reader)) {
						if(!JCasUtil.selectSingle(jcas_1, Issue.class).getIssue().equals(issue)) continue;
						for (JCas jcas_2 : new JCasIterable(reader)) {
							
							String text1= jcas_1.getDocumentText();
							String text2= jcas_2.getDocumentText();
							if(text1.equals(text2)) continue;
							double sim = measure.getSimilarity(text1.split(" "), text2.split(" "));
							
							
//							System.out.println(jcas_1.getDocumentText()+ "_"+jcas_2.getDocumentText()+ " "+sim);
							pair2Sim.put(text1+" ("+JCasUtil.selectSingle(jcas_1, TextClassificationOutcome.class).getOutcome()+") _"+text2+" ("+JCasUtil.selectSingle(jcas_2, TextClassificationOutcome.class).getOutcome()+")", sim);
						}
						
					}
				}
				System.out.println(pair2Sim.size());
				pair2Sim=sortByValue(pair2Sim);
				System.out.println(pair2Sim.size());
				
				int i=0;
				for(String key: pair2Sim.keySet()) {
					if(i==100)break;
					System.out.println(key+ " "+pair2Sim.get(key));
					i++;
				}

	}
	
	
	private static Map<String, Double> sortByValue(Map<String, Double> unsortMap) {

        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }


        return sortedMap;
    }
	

}
