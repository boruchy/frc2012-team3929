/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package team3929.testing;

import java.util.Random;
import team3929.utilities.MovingAverageFilter;

/**
 *
 * @author bhgray
 */
public class MovingAverageFilterTest {

    private MovingAverageFilter filter;

    public boolean testMAF(int windowSize, int numTestPoints)
    {
        filter = new MovingAverageFilter(windowSize);
        int maxPoint = 10000;
        Random gen = new Random();
        int total = 0;
        int currentPoint = 0;
        int currentAverage = 0;
        for (int i = 0; i < numTestPoints; i++)
        {
            currentPoint = gen.nextInt(maxPoint) + 1;
            total += currentPoint;
            filter.addPoint(currentPoint);
            currentAverage = (int)(total / (double)(i + 1));
            System.out.println("Data point " + i + ": " + currentPoint);
            System.out.println("    Filter Average:" + filter.getAverage());
            System.out.println("          Expected:" + currentAverage);
        }
        if (currentAverage == filter.getAverage())
        {
            return true;
        }
        return false;
    }


    public static void main(String[] args)
    {
        MovingAverageFilterTest tester = new MovingAverageFilterTest();
        tester.testMAF(10, 100);
    }
}
