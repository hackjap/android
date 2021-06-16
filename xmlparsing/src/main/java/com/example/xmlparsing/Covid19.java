package com.example.xmlparsing;

public class Covid19 {
   String decideCNT; // 총 확진자
   String seq;    // 확진 증가 수
   String stateDT;   // 날짜

   public Covid19(String decideCNT, String seq, String stateDT) {
      this.decideCNT = decideCNT;
      this.seq = seq;
      this.stateDT = stateDT;
   }

   public String getDecideCNT() {
      return decideCNT;
   }

   public void setDecideCNT(String decideCNT) {
      this.decideCNT = decideCNT;
   }

   public String getSeq() {
      return seq;
   }

   public void setSeq(String seq) {
      this.seq = seq;
   }

   public String getStateDT() {
      return stateDT;
   }

   public void setStateDT(String stateDT) {
      this.stateDT = stateDT;
   }
}

