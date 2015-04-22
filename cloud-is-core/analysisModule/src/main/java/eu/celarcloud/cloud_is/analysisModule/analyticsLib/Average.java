/*
 * --------------------------------------------------------------------------------------------------------------
 * Copyright 2014, Laboratory of Internet Computing (LInC), Department of Computer Science, University of Cyprus
 * 
 * For any information relevant to Cloud Information System,
 * please contact Athanasios Foudoulis,  afoudo01{at}cs.ucy.ac.cy
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * --------------------------------------------------------------------------------------------------------------
 */
package eu.celarcloud.cloud_is.analysisModule.analyticsLib;

/**
 * The Class Average.
 * 
 * Attribution:
 * - Moving average is inspired and uses code snippet from the 
 * "Incremental average calculation" blog (http://jvminside.blogspot.com/2010/01/incremental-average-calculation.html)
 * 
 */
public class Average {

    /**
	 * Average.
	 *
	 * @param values
	 *            the values
	 * @return the double
	 */
    public static double average(double... values)
    {
        if (values.length == 0)
        {
            throw new IllegalArgumentException("Expected a non-empty array of numbers.");
        }

        double result = 0.0;

        for (int i = 0; i < values.length; i++)
        {
            result += values[i];
        }

        result /= values.length;

        return result;
    }

    /**
	 * Incremental average.
	 *
	 * @param values
	 *            the values
	 * @return the double
	 */
    public static double incrementalAverage(double... values)
    {
        if (values.length == 0)
        {
            throw new IllegalArgumentException("Expected a non-empty array of numbers.");
        }

        double result = 0.0;

        for (int i = 0; i < values.length; i++)
        {
            result = incrementalAverageIteration(result, values[i], i);
        }

        return result;
    }

    /**
	 * Incremental average iteration.
	 *
	 * @param previousAverage
	 *            the previous average
	 * @param value
	 *            the value
	 * @param iterationIndex
	 *            the iteration index
	 * @return the double
	 */
    public static double incrementalAverageIteration(double previousAverage, double value, int iterationIndex)
    {
        return ((value - previousAverage) / (iterationIndex + 1)) + previousAverage;
    }

    /**
	 * Average from average.
	 *
	 * @param av1
	 *            the av1
	 * @param cnt1
	 *            the cnt1
	 * @param av2
	 *            the av2
	 * @param cnt2
	 *            the cnt2
	 * @return the double
	 */
    public static double averageFromAverage(double av1, int cnt1, double av2, int cnt2)
    {
    	return ((av1 * cnt1) + (av2 * cnt2)) / (cnt1 + cnt2);
    }
         
    /**
	 * Simple Moving Average.
	 *
	 * @param arg
	 *            point series
	 * @param count
	 *            number of points that was taken into consideration
	 * @return the double[]
	 */
    public static double[] sma_2(double[] arg, int count) {
	    double[] ret = new double[arg.length];
	    double sum = 0;
	    
	    int l = 0;
	    for(; l < arg.length && Double.isNaN(arg[l]); l++) {
	    	ret[l] = Double.NaN;
	    }
	    
	    int i = l;
	    for(; i < l + count - 1 && i < arg.length; i++) {
	    	ret[i] = Double.NaN;
	    	sum += arg[i];
	    }
	    
	    for(int j = l, k = i; k < arg.length; k++, j++) {
	    	sum += arg[k];
	    	ret[k] = sum/count;
	    	sum -= arg[j];
	    }
	    return ret;
    }  

}
