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

    static void solve(Prog[] c1, Prog[] c2){
        List<Prog> result = new ArrayList<Prog>();


        System.out.println(c1.length + " " + c2.length);
        int i = 0, j = 0;
        while(i < c1.length && j < c2.length){

            int ok = 1;
            System.out.println("[i, j] = [" + i + ", " + j + "]");
            if(c1[i].end < c2[j].start){
//                System.out.println("i = " + i);
                if(c2[j].start - c1[i].end >= 0.5f){
                    if(i + 1 < c1.length){
                        if(c1[i+1].start - c1[i].end >= 0.5f){
                            result.add(new Prog(c1[i].end, c2[j].start));
//                            System.out.print("[" + c1[i].end + ", " + c2[j].start + "] ");
                        }
                    } else {
                        result.add(new Prog(c1[i].end, c2[j].start));
//                        System.out.print("[" + c1[i].end + ", " + c2[j].start + "] ");
                    }
                }
                ok = 0;
                i++;
            }

            if(c2[j].end < c1[i].start){
//                System.out.println("j = " + j);
                if(c1[i].start - c2[j].end >= 0.5f){
                    if(j + 1 < c2.length){
                        if(c2[j+1].start - c2[j].end >= 0.5f){
                            result.add(new Prog(c2[j].end, c1[i].start));
//                            System.out.print("[" + c2[j].end + ", " + c1[i].start + "] ");
                        }
                    } else {
                        result.add(new Prog(c2[j].end, c1[i].start));
//                        System.out.print("[" + c2[j].end + ", " + c1[i].start + "] ");
                    }
                }
                ok = 0;
                j++;
            }

            if(ok == 1){
                if(c1[i].end < c2[j].end){
                    i++;
                } else {
                    j++;
                }
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

        solve(c1, c2);

    }
}

