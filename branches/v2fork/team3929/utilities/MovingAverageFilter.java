/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package team3929.utilities;

/**
 *
 * @author bhgray
 */
public class MovingAverageFilter {

    private double [] data;
    private int currentDataPointIndex;
    private int windowSize;
    private boolean fullSample;
    private double average;

    public MovingAverageFilter(int windowSize)
    {
        this.windowSize = windowSize;
        data = new double[windowSize];
        fullSample = false;
        reset();
    }

    public synchronized void addPoint(double point)
    {
        if (currentDataPointIndex + 1 >= data.length)
        {
            currentDataPointIndex = 0;
            fullSample = true;
        } else {
            currentDataPointIndex++;
        }
        data[currentDataPointIndex] = point;
    }

    public  synchronized void reset()
    {
        for (int i = 0; i < windowSize; i++)
        {
            data[i] = 0;
        }
        fullSample = false;
    }

//    public int getAverage()
//    {
//        double total = 0.0;
//        int average = 0;
//
//        if (fullSample)
//        {
//           for (int i = 0; i < data.length; i++)
//            {
//                total += data[i];
//            }
//           average = (int) (total / data.length);
//        } else
//        {
//         for (int i = 0; i <= currentDataPointIndex; i++)
//            {
//                total += data[i];
//            }
//            average = (int) (total / (double)(currentDataPointIndex));
//        }
//        return average;
//    }

    public synchronized double getAverage()
    {
        return average;
    }

    public synchronized double run()
    {
        average = 0.0;
        for( int i = 0; i < data.length; i++ )
        {
            average += data[i];
        }
        average /= (double)data.length;
        return average;
    }
}
