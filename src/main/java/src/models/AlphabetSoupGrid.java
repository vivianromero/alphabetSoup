package src.models;

import java.util.ArrayList;
import java.util.List;

public class AlphabetSoupGrid {
        private int w, h;
        private boolean ltr, rtl, ttb, btt, d, dUpLtoR,dDownLtoR,dUpRtoL,dDownRtoL;
        private char[][] alphabetSoup; //sopa de palabras
        private List<String> words; //todas las posibles palabras a introd en la sopa
        private List<String> solutions; //las palabras que se pusiero en la sopa con su posic
        private List<String> wordsSolutions = new ArrayList<>(); // las palabras que se van encontrando
        private int numAttempts;

        public int getW() {
            return w;
        }

        public void setW(int w) {
            this.w = w;
        }

        public int getH() {
            return h;
        }

        public void setH(int h) {
            this.h = h;
        }

        public boolean isLtr() {
            return ltr;
        }

        public void setLtr(boolean ltr) {
            this.ltr = ltr;
        }

        public boolean isRtl() {
            return rtl;
        }

        public void setRtl(boolean rtl) {
            this.rtl = rtl;
        }

        public boolean isTtb() {
            return ttb;
        }

        public void setTtb(boolean ttb) {
            this.ttb = ttb;
        }

        public boolean isBtt() {
            return btt;
        }

        public void setBtt(boolean btt) {
            this.btt = btt;
        }

        public boolean isD() {
            return d;
        }

        public void setD(boolean d) {
            this.d = d;
        }

        public boolean isdUpLtoR() {
            return dUpLtoR;
        }

        public void setdUpLtoR(boolean dUpLtoR) {
            this.dUpLtoR = dUpLtoR;
        }

        public boolean isdDownLtoR() {
            return dDownLtoR;
        }

        public void setdDownLtoR(boolean dDownLtoR) {
            this.dDownLtoR = dDownLtoR;
        }

        public boolean isdUpRtoL() {
            return dUpRtoL;
        }

        public void setdUpRtoL(boolean dUpRtoL) {
            this.dUpRtoL = dUpRtoL;
        }

        public boolean isdDownRtoL() {
            return dDownRtoL;
        }

        public void setdDownRtoL(boolean dDownRtoL) {
            this.dDownRtoL = dDownRtoL;
        }

        public char[][] getAlphabetSoup() {
            return alphabetSoup;
        }

        public void setAlphabetSoup(char[][] alphabetSoup) {
            this.alphabetSoup = alphabetSoup;
        }

        public List<String> getWords() {
            return words;
        }

        public void setWords(List<String> words) {
            this.words = words;
        }

        public List<String> getSolutions() {
            return solutions;
        }

        public void setSolutions(List<String> solutions) {
            this.solutions = solutions;
        }

        public List<String> getWordsSolutions() {
                return wordsSolutions;
            }

        public void setWordsSolutions(List<String> wordsSolutions) {
                this.wordsSolutions = wordsSolutions;
            }



        public int getNumAttempts() {
                return numAttempts;
            }

        public void setNumAttempts(int numAttempts) {
                this.numAttempts = numAttempts;
            }
            
}
