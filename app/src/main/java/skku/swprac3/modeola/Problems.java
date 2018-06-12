package skku.swprac3.modeola;

import java.util.List;

public class Problems {
    Problem[] problems;

    Problems(List<String> names, List<String> contents, List<Integer> answers, List<Integer> types) {
        problems = new Problem[5];

        int contentCur = 0;
        for(int i = 0; i < 5; i++) {
            String[] contentsT = null;
            if(types.get(i) == 1) { // OX
                contentsT = null;
                contentCur+=2;
            } else if(types.get(i) == 2) { // Multiple
                contentsT = new String[5];
                contentsT[0] = contents.get(contentCur++);
                contentsT[1] = contents.get(contentCur++);
                contentsT[2] = contents.get(contentCur++);
                contentsT[3] = contents.get(contentCur++);
                contentsT[4] = contents.get(contentCur++);
            }
            problems[i] = new Problem(names.get(i), contentsT, answers.get(i), types.get(i));
        }
    }
    public Problem getProblem(int index) {
        return problems[index];
    }



    public class Problem {
        String name;
        String[] content;
        int answer;
        int type;

        Problem(String name, String[] content, int answer, int type) {
            this.name = name;
            if(type == 1) { // OX
                this.content = null;
            } else if(type == 2) { // Multiple
                this.content = new String[5];
                this.content[0] = content[0];
                this.content[1] = content[1];
                this.content[2] = content[2];
                this.content[3] = content[3];
                this.content[4] = content[4];
            }
            this.answer = answer;
        }
        public String getName() {
            return name;
        }
        public String getContent(int index) {
            return content[index];
        }
        public String[] getContents() {
            return content;
        }
        public int getAnswer() {
            return answer;
        }
        public int getType() {
            return type;
        }
    }
}




/* ---- Usage Example ----
    myDBHelper myDB = new myDBHelper(getApplicationContext());
    Problems probs = myDB.getProblems(3, 2); // OX 3sets, Multiple 2sets

    String problem1Title = probs.getProblem(0).getTitle();

    // Content get example1
    String problem1Content1 = probs.getProblem(0).getContent(0);
    String problem1Content2 = probs.getProblem(0).getContent(1);
    String problem1Content3 = probs.getProblem(0).getContent(2);
    String problem1Content4 = probs.getProblem(0).getContent(3);
    String problem1Content5 = probs.getProblem(0).getContent(4);

    // Content get example2
    String[] problem1Contents = probs.getProblem(0).getContents();

    int problem1Answer = probs.getProblem(0).getAnswer();
    // if necessary, int problem1Type = probs.getProblem(0).getType();
 */
