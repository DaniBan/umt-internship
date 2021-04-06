import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class CalendarProblem {

    static class Prog{
        public float start;
        public float end;

        public Prog(float start, float end){
            this.start = start;
            this.end = end;
        }
    }

    static void printCalendar(Prog[] calendar, String label){
        System.out.print(label + ": ");
        for(Prog p: calendar){
            System.out.print("[" + p.start + ", " + p.end + "] ");
        }
        System.out.println();
    }

    static void solve(Prog[] c1, Prog[] c2, float min1, float max1, float min2, float max2, float threshold){
        // min1 and max1 represent the min and max ranges of the first calendar
        // min2 and max2 represent the min and max ranges of the second calendar

        List<Prog> result = new ArrayList<Prog>();


        // check free time with lower bound
        // if both have time before the first appointment
        if((c1[0].start - min1 >= threshold) && (c2[0].start - min2 >= threshold)){
            // if there is enough time between when the first person is available and
            // the starting time of the first appointment of the second person or vice versa
            if(c2[0].start - min1 >= threshold || c1[0].start - min2 >= threshold){
                // add the appropriate result based on which first appointment starts first
                // and on which lower bound (limit) is bigger
                if(c1[0]. start < c2[0].start){
                    if(c1[0].start - max(min1, min2) >= threshold){
                        result.add(new Prog(max(min1, min2), c1[0].start));
                    }
                } else {
                    if(c2[0].start - max(min1, min2) >= threshold){
                        result.add(new Prog(max(min1, min2), c2[0].start));
                    }
                }
            }
        }

        int i = 0, j = 0;
        while(i < c1.length && j < c2.length){

            int ok = 1;
            // if the appointment nb i form c1 ends before the appointment nb j from c2 starts
            if(c1[i].end < c2[j].start){
                // if there is enough time
                if(c2[j].start - c1[i].end >= threshold){
                    // if the appointment from c1 is not the last one
                    if(i + 1 < c1.length){
                        // if the appointment nb i+1 from c1 starts after the appointment nb j from c2
                        // we add to the results a free time interval starting at the end of c1[i] and ending at the start of c2[j]
                        // otherwise the added free time will have the ending time equal to c1[i+1].start
                        if(c1[i+1].start - c1[i].end >= threshold && c1[i+1].start > c2[j].start){
                            result.add(new Prog(c1[i].end, c2[j].start));
                        } else {
                            if(c1[i+1].start - c1[i].end >= threshold){
                                result.add(new Prog(c1[i].end, c1[i + 1].start));
                            }
                        }
                    } else {
                        // if the appointment from c1 is the last one simply add the free time interval to the result.
                        result.add(new Prog(c1[i].end, c2[j].start));
                    }
                }
                ok = 0;
                i++;
            }

            // if the appointment nb j form c2 ends before the appointment nb i from c1 starts
            if(c2[j].end < c1[i].start){
                // if there is enough time
                if(c1[i].start - c2[j].end >= threshold){
                    // if the appointment from c2 is not the last one
                    if(j + 1 < c2.length){
                        // if the appointment nb j+1 from c2 starts after the appointment nb i from c1
                        // we add to the results a free time interval starting at the end of c2[j] and ending at the start of c1[i]
                        // otherwise the added free time will have the ending time equal to c2[j+1].start
                        if(c2[j+1].start - c2[j].end >= threshold && c2[j+1].start > c1[i].start){
                            result.add(new Prog(c2[j].end, c1[i].start));
                        } else {
                            if(c2[j+1].start - c2[j].end >= threshold){
                                result.add(new Prog(c2[j].end, c2[j + 1].start));
                            }
                        }
                    } else {
                        // if the appointment from c2 is the last one simply add the free time interval to the result.
                        result.add(new Prog(c2[j].end, c1[i].start));
                    }
                }
                ok = 0;
                j++;
            }

            // if none of the current appointments starts one before the other
            // increase i or j based on which appointment ends first
            if(ok == 1){
                if(c1[i].end < c2[j].end){
                    i++;
                } else {
                    j++;
                }
            }

        }

        // if i or j got out of bounds we bring it inbounds
        if(i == c1.length){
            i--;
        }

        if(j == c2.length){
            j--;
        }


        int check = 0; // used to verify whether the last element from c2 was taken into consideration
        int ok = 0;

        // as log as there are remaining appointments in c1
        while(i < c1.length){

            if(i - 1 < 0){
                i++;
                continue;
            }

            if(check == 0){ // if the last element from c2 was not taken into consideration
                if(c1[i].start - c1[i-1].end >= threshold && c1[i].start - c2[j].end >= threshold){
                    result.add(new Prog(max(c2[j].end, c1[i-1].end), c1[i].start));
                    check = 1; // mark that the last element from c2 was taken into consideration
                }
            } else {
                // add the free time intervals between the left appointments from c1
                if(c1[i].start - c1[i-1].end >= threshold){
                    result.add(new Prog(c1[i-1].end, c1[i].start));
                }
            }
            i++;
        }

        // if i or j got out of bounds we bring it inbounds
        if(i == c1.length){
            i--;
        }

        if(j == c2.length){
            j--;
        }

        check = 0; // used to verify whether the last element from c1 was taken into consideration
        ok = 0;

        // as log as there are remaining appointments in c2
        while(j < c2.length){

            if(j - 1 < 0){
                j++;
                continue;
            }

            if(check == 0){ // if the last element from c1 was not taken into consideration
                if(c2[j].start - c2[j-1].end >= threshold && c2[j].start - c1[i].end >= threshold){
                    result.add(new Prog(max(c1[i].end, c2[j-1].end), c2[j].start));
                    check = 1; // mark that the last element from c1 was taken into consideration
                }
            } else {
                // add the free time intervals between the left appointments from c2
                if(c2[j].start - c2[j-1].end >= threshold){
                    result.add(new Prog(c2[j-1].end, c2[j].start));
                }
            }
            
            j++;

        }

        // if i or j got out of bounds we bring it inbounds
        if(i == c1.length){
            i--;
        }

        if(j == c2.length){
            j--;
        }

        // check for higher bound free time
        if((max1 - c1[i].end >= 0.5) && (max2 - c2[j].end > threshold)){
            if(c1[i].end > c2[j].end && (max2 - c1[i].end >= threshold)){
                result.add(new Prog(c1[i].end, min(max1, max2)));
            }
            if(c2[j].end > c1[i].end && (max1 - c2[j].end >= threshold)){
                result.add(new Prog(c2[j].end, min(max1, max2)));
            }
        }

        System.out.print("Output: ");
        for(Prog p: result){
            System.out.print("[" + p.start + ", " + p.end + "] ");
        }
        System.out.println();
    }

    public static void main(String Args[]){
        Prog[] c1 = {new Prog(9,10.5f), new Prog(12,13), new Prog(16,18)};
        Prog[] c2 = {new Prog(10,11.5f), new Prog(12.5f,14.5f), new Prog(14.5f,15), new Prog(16,17)};

        Prog[] c3 = {new Prog(9,11), new Prog(12,12.5f), new Prog(13,16), new Prog(19,19.5f)};
        Prog[] c4 = {new Prog(12,12.5f), new Prog(14,15), new Prog(16,19)};

        Prog[] c5 = {new Prog(10,13), new Prog(13.5f,14), new Prog(15,17), new Prog(18,18.5f)};
        Prog[] c6 = {new Prog(11,12)};

        // min1 and max1 represent the min and max ranges of the first calendar
        // min2 and max2 represent the min and max ranges of the second calendar

        // Test Case 1
        printCalendar(c1, "Calendar 1");
        printCalendar(c2, "Calendar 2");
        solve(c1, c2, 9.0f, 20.0f, 10.0f, 18.5f, 0.5f);
        System.out.println();

        // Test Case 2
        printCalendar(c3, "Calendar 3");
        printCalendar(c4, "Calendar 4");
        solve(c3, c4, 8.0f, 22.0f, 9.0f, 20.0f, 0.5f);
        System.out.println();


        // Test Case 3
        printCalendar(c1, "Calendar 5");
        printCalendar(c2, "Calendar 6");
        solve(c5, c6, 8.0f, 18.5f, 9.0f, 20.0f, 0.5f);

    }
}

