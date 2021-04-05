import java.util.ArrayList;
import java.util.List;

public class CalendarProblem {

    static class Prog{
        public float start;
        public float end;

        public Prog(float start, float end){
            this.start = start;
            this.end = end;
        }
    }

    static void solve(Prog[] c1, Prog[] c2, float min1, float max1, float min2, float max2){
        List<Prog> result = new ArrayList<Prog>();


        System.out.println(c1.length + " " + c2.length);

        //TODO add lower and higher bound parameters and also minimum time gap

        // check free time with lower bound
        if((c1[0].start - min1 >= 0.5) && (c2[0].start - min2 >= 0.5)){
            if(c2[0].start - min1 >= 0.5f){
                if(c1[0]. start < c2[0].start){
                    result.add(new Prog(min1, c1[0].start));
                } else {
                    result.add(new Prog(min1, c2[0].start));
                }
            }

            if(c1[0].start - min2 >= 0.5f){
                if(c2[0].start < c1[0].start){
                    result.add(new Prog(min2, c2[0].start));
                } else {
                    result.add(new Prog(min2, c1[0].start));
                }
            }
        }

        int i = 0, j = 0;
        while(i < c1.length && j < c2.length){

            int ok = 1;
            System.out.println("[i, j] = [" + i + ", " + j + "]");
            if(c1[i].end < c2[j].start){
//                System.out.println("i = " + i);
                if(c2[j].start - c1[i].end >= 0.5f){
                    if(i + 1 < c1.length){
                        if(c1[i+1].start - c1[i].end >= 0.5f && c1[i+1].start > c2[j].start){
                            result.add(new Prog(c1[i].end, c2[j].start));
//                            System.out.print("[" + c1[i].end + ", " + c2[j].start + "] ");
                        } else {
                            result.add(new Prog(c1[i].end, c1[i + 1].start));
                        }
                    } else {
                        result.add(new Prog(c1[i].end, c2[j].start));
//                        System.out.print("[" + c1[i].end + ", " + c2[j].start + "] ");
                    }
                }
                ok = 0;
                i++;
                System.out.println("case c1.end < c2.start");
            }

            if(c2[j].end < c1[i].start){
//                System.out.println("j = " + j);
                if(c1[i].start - c2[j].end >= 0.5f){
                    if(j + 1 < c2.length){
                        if(c2[j+1].start - c2[j].end >= 0.5f && c2[j+1].start > c1[i].start){
                            result.add(new Prog(c2[j].end, c1[i].start));
//                            System.out.print("[" + c2[j].end + ", " + c1[i].start + "] ");
                        } else {
                            result.add(new Prog(c2[j].end, c2[j + 1].start));
                        }
                    } else {
                        result.add(new Prog(c2[j].end, c1[i].start));
//                        System.out.print("[" + c2[j].end + ", " + c1[i].start + "] ");
                    }
                }
                ok = 0;
                j++;
                System.out.println("case c2.end < c1.start");
            }

            if(ok == 1){
                System.out.println("case contained");
                if(c1[i].end < c2[j].end){
                    i++;
                } else {
                    j++;
                }
            }

        }

        if(i == c1.length){
            i--;
        }

        if(j == c2.length){
            j--;
        }
        System.out.println(i + " " + j);

        int check = 0;

        //TODO ceva susta aicea ce tre rezolvata

        while(i < c1.length){

            if(i + 1 < c1.length){
                if(check == 0){
                    if((c1[i+1].start - c1[i].end >= 0.5f) && (c1[i].start - c2[j].end >= 0.5f)){
                        result.add(new Prog(c2[j].end, c1[i].start));
//                            System.out.print("[" + c1[i].end + ", " + c2[j].start + "] ");
                        check = 1;
                    }
                } else {
                    if(c1[i+1].start - c1[i].end >= 0.5f && (max2 - c1[i + 1].start >= 0.5f)){
                        result.add(new Prog(c1[i].end, c1[i+1]. start));
                    }
                }

            } else {
                if(c1[i].start - c2[j].end >= 0.5f && (c1[i].start - c1[i - 1].end >= 0.5f))
                result.add(new Prog(c2[j].end, c1[i].start));
//                        System.out.print("[" + c1[i].end + ", " + c2[j].start + "] ");
                check = 1;
            }
            i++;
        }

        if(i == c1.length){
            i--;
        }

        if(j == c2.length){
            j--;
        }

        check = 0;
        while(j < c2.length){
            if(j + 1 < c2.length){
                if(check == 0){
                    if((c2[j+1].start - c2[j].end >= 0.5f) && (c2[j].start - c1[i].end >= 0.5f)){
                        result.add(new Prog(c1[i].end, c2[j].start));
//                            System.out.print("[" + c1[i].end + ", " + c2[j].start + "] ");
                        check = 1;
                    }
                } else {
                    if(c2[j+1].start - c2[j].end >= 0.5f && (max1 - c2[j + 1].start >= 0.5f)){
                        result.add(new Prog(c2[j].end, c2[j+1]. start));
                    }
                }

            } else {
                if(c2[j].start - c1[i].end >= 0.5f && (c2[j].start - c2[j-1].end >= 0.5f))
                    result.add(new Prog(c1[i].end, c2[j].start));
//                        System.out.print("[" + c1[i].end + ", " + c2[j].start + "] ");
                check = 1;
            }
            j++;

        }

        if(i == c1.length){
            i--;
        }

        if(j == c2.length){
            j--;
        }

        // check for higher bound free time
        if((20.0f - c1[i].start >= 0.5) && (max2 - c2[j].end > 0.5f)){
            if(c1[i].end > c2[j].end && (max2 - c1[i].end >= 0.5f)){
                result.add(new Prog(c1[i].end, max2));
            }
            if(c2[j].end > c1[i].end && (max1 - c2[j].end >= 0.5f)){
                result.add(new Prog(c2[j].end, max1));
            }
        }

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

//        solve(c1, c2, 9.0f, 20.0f, 10.0f, 18.5f);
        solve(c3, c4, 8.0f, 22.0f, 9.0f, 20.0f);

    }
}

