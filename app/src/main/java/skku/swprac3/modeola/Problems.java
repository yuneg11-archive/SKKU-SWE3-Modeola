package skku.swprac3.modeola;

import java.util.List;

public class Problems {
    private Problem[] problems;

    Problems(List<String> contents, List<String> contentsOriginal, List<Integer> answers, List<Integer> types) {
        problems = new Problem[5];

        int contentCur = 0;
        for(int i = 0; i < 5; i++) {
            String[] contentsT = null;
            String[] contentsO = null;
            if(types.get(i) == 1) { // OX
                contentsT = new String[2];
                contentsO = new String[2];
                contentsT[0] = contents.get(contentCur);
                contentsO[0] = contentsOriginal.get(contentCur++);
                contentsT[1] = contents.get(contentCur);
                contentsO[1] = contentsOriginal.get(contentCur++);
            } else if(types.get(i) == 2) { // Multiple
                contentsT = new String[5];
                contentsO = new String[5];
                contentsT[0] = contents.get(contentCur);
                contentsO[0] = contentsOriginal.get(contentCur++);
                contentsT[1] = contents.get(contentCur);
                contentsO[1] = contentsOriginal.get(contentCur++);
                contentsT[2] = contents.get(contentCur);
                contentsO[2] = contentsOriginal.get(contentCur++);
                contentsT[3] = contents.get(contentCur);
                contentsO[3] = contentsOriginal.get(contentCur++);
                contentsT[4] = contents.get(contentCur);
                contentsO[4] = contentsOriginal.get(contentCur++);
            }
            problems[i] = new Problem(contentsT, contentsO, answers.get(i), types.get(i));
        }
    }
    public Problem getProblem(int index) {
        return problems[index];
    }


    public class Problem {
        private String[] content;
        private String[] contentOriginal;
        private int answer;
        private int type;

        Problem(String[] content, String[] contentOriginal, int answer, int type) {
            if(type == 1) { // OX
                this.content = new String[2];
                this.content[0] = content[0];
                this.content[1] = content[1];
                this.contentOriginal = new String[2];
                this.contentOriginal[0] = contentOriginal[0];
                this.contentOriginal[1] = contentOriginal[1];
            } else if(type == 2) { // Multiple
                this.content = new String[5];
                this.content[0] = content[0];
                this.content[1] = content[1];
                this.content[2] = content[2];
                this.content[3] = content[3];
                this.content[4] = content[4];
                this.contentOriginal = new String[5];
                this.contentOriginal[0] = contentOriginal[0];
                this.contentOriginal[1] = contentOriginal[1];
                this.contentOriginal[2] = contentOriginal[2];
                this.contentOriginal[3] = contentOriginal[3];
                this.contentOriginal[4] = contentOriginal[4];
            }
            this.answer = answer;
        }
        public String getContent(int index) {
            return content[index];
        }
        public String[] getContents() {
            return content;
        }
        public String[] getContentsOriginal() {
            return contentOriginal;
        }
        public int getAnswer() {
            return answer;
        }
        public int getType() {
            return type;
        }
    }
}