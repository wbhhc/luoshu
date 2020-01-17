package wbhhc.luoshu;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.abs;

public class LuoShu {
    private int sum=15;

    @Getter
    @Setter
    private int row1col1;
    @Getter
    @Setter
    private int row1col2;
    @Getter
    @Setter
    private int row1col3;
    @Getter
    @Setter
    private int row2col1;
    @Getter
    @Setter
    private int row2col2=5;
    @Getter
    @Setter
    private int row2col3;
    @Getter
    @Setter
    private int row3col1;
    @Getter
    @Setter
    private int row3col2;
    @Getter
    @Setter
    private int row3col3;

    private Integer[][] arr=new Integer[3][3];

    public LuoShu() {
//        genArray();
    }

    public void setDefault() {
        this.row1col1 = 4;
        this.row1col2 = 9;
        this.row1col3 = 2;
        this.row2col1 = 3;
        this.row2col2 = 5;
        this.row2col3 = 7;
        this.row3col1 = 8;
        this.row3col2 = 1;
        this.row3col3 = 6;
//        genArray();
    }

    public LuoShu(int row1col1, int row1col2, int row1col3,
                  int row2col1, int row2col2, int row2col3,
                  int row3col1, int row3col2, int row3col3) {

        this.row1col1 = row1col1;
        this.row1col2 = row1col2;
        this.row1col3 = row1col3;
        this.row2col1 = row2col1;
//        this.row2col2 = row2col2;//中间数5不允许输入
        this.row2col3 = row2col3;
        this.row3col1 = row3col1;
        this.row3col2 = row3col2;
        this.row3col3 = row3col3;

//        genArray();
    }

    public boolean doValied() {
        genArray();

        //横竖斜加起来都等于15
        if (sumRow() && sumCol() && sumSla() && noRepeat()) return true;

        return false;
    }

    private boolean noRepeat() {
        for(int ri=0;ri<arr.length;ri++){
            for(int ci=0;ci<arr[ri].length;ci++){
                if(findRepeat(arr[ri][ci])) return false;
            }
        }
        return true;
    }

    private boolean findRepeat(int v){
        int count=0;
        for(int ri=0;ri<arr.length;ri++){
            for(int ci=0;ci<arr[ri].length;ci++){
                if(arr[ri][ci]==v) count++;
            }
        }
        if(count>1) return true;
        return false;
    }

    private boolean sumSla() {
        if(sumSlaLeft() && sumSlaRight()) return true;
        return false;
    }

    private boolean sumSlaRight() {
        int slSum=0;
        for(int ri=0;ri<arr.length;ri++){
            for(int ci=arr[ri].length-1;ci>=0;ci--){
                if(ri==(arr[ri].length-1-ci))
                    slSum=arr[ri][ci]+slSum;
            }
        }
        if(slSum!=sum) return false;
        return true;
    }

    private boolean sumSlaLeft() {
        int slSum=0;
        for(int ri=0;ri<arr.length;ri++){
            for(int ci=0;ci<arr[ri].length;ci++){
                if(ri==ci)
                    slSum=arr[ri][ci]+slSum;
            }
        }
        if(slSum!=sum) return false;
        return true;
    }

    private boolean sumCol() {
        for(int ci=0;ci<3;ci++){
            int colSum=0;
            for(int ri=0;ri<3;ri++){
                colSum=arr[ri][ci]+colSum;
            }
            if(colSum!=sum) return false;
        }
        return true;
    }

    private boolean sumRow() {
        for(Integer[] row : arr){
            int rowSum=0;
            for (Integer c:row){
                rowSum+=c;
            }
            if(rowSum!=sum) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  " - - - \n"
                +"|" + arr[0][0] + "|" + arr[0][1] + "|" + arr[0][2] + "|\n"
                +" - - - \n"
                +"|" + arr[1][0] + "|" + arr[1][1] + "|" + arr[1][2] + "|\n"
                +" - - - \n"
                +"|" + arr[2][0] + "|" + arr[2][1] + "|" + arr[2][2] + "|\n"
                +" - - - \n"
                ;
    }

    public boolean autoFill() {

        genArray();

        int dog=64;
        int times=0;

        //如果输入的数字少于2个，则无法自动填充。（5以外输入少于2个）
        int inputCount=getInputCount();
        if(inputCount<3) throw new RuntimeException("请至少输入两个数字！");
        if(inputCount==3 && isSymmetry())//5以外只输入两个数字的，还需要验证是否对称
            throw new RuntimeException("仅有的两个数字不能对称！");

        //开始自动填充，循环，直到8个数字填满。
        while(getInputCount()<=8){


            //检查横
            fillRow();
            //检查竖
            fillCol();
            //检查斜
            fillSla();

            //反复计算超过看门狗则失败
            if(++times>=dog) return false;
        }
        setFromArr(arr);

        if(doValied()) return true;
        return false;
    }

    private boolean isSymmetry() {
        //设置对称中点，按照从1开始到最大长度个数
        int centerRi=2;
        int centerCi=2;
        for (int ri=0;ri<arr.length ;ri++){
            for (int ci=0;ci<arr[ri].length;ci++){
                if(ri==centerRi-1 && ci==centerCi-1) continue;//中点自己不找对称
                if(arr[ri][ci]!=0){//!=0为填写的数字，然后我们去找他的对称位置
                    //下标+1，从1开始计算出与中点的距离，然后再反方向计算对称位置，最后下标-1还原
                    int sri=(centerRi-(ri+1)+centerRi)-1;
                    int sci=(centerCi-(ci+1)+centerCi)-1;
                    if(arr[sri][sci]!=0) return true;//有对称
                }
            }
        }
        return false;
    }

    private void fillSla() {
        fillSlaLeft();
        fillSlaRight();
    }

    private void fillSlaRight() {
        int count=0;
        int val=sum;
        int emptyRi=-1;
        int emptyCi=-1;
        for(int ri=0;ri<arr.length;ri++){
            for(int ci=arr[ri].length-1;ci>=0;ci--){
                if(ri==(arr[ri].length-1-ci)){
                    if(arr[ri][ci]!=0){
                        count++;
                        val=val-arr[ri][ci];
                    }else{
                        emptyRi=ri;
                        emptyCi=ci;
                    }
                }
            }
        }
        if(count==2){
            arr[emptyRi][emptyCi]=val;
        }
    }

    private void fillSlaLeft() {
        int count=0;
        int val=sum;
        int emptyRi=-1;
        int emptyCi=-1;
        for(int ri=0;ri<arr.length;ri++){
            for(int ci=0;ci<arr[ri].length;ci++){
                if(ri==ci){
                    if(arr[ri][ci]!=0){
                        count++;
                        val=val-arr[ri][ci];
                    }else{
                        emptyRi=ri;
                        emptyCi=ci;
                    }
                }

            }
        }
        if(count==2){
            arr[emptyRi][emptyCi]=val;
        }
    }

    private void fillCol() {
        for(int ci=0;ci<3;ci++){
            int count=0;
            int val=sum;
            int emptyRi=-1;
            for(int ri=0;ri<3;ri++){
                if(arr[ri][ci]!=0){
                    count++;
                    val=val-arr[ri][ci];
                }else{
                    emptyRi=ri;
                }
            }
            if(count==2){//有两个数可以填充一个空宫格
                arr[emptyRi][ci]=val;
            }
        }
    }

    private void fillRow() {
        for (Integer[] row :arr){
            int count=0;
            int val=sum;
            int emptyIndex=-1;
            for (int i=0;i<row.length;i++){
                if(row[i]!=0){
                    count++;
                    val=val-row[i];
                }else{
                    emptyIndex=i;
                }
            }
            if(count==2){//有两个数可以填充一个空宫格
                row[emptyIndex]=val;
            }
        }
    }

    private void genArray() {
        arr[0][0]=row1col1;
        arr[0][1]=row1col2;
        arr[0][2]=row1col3;
        arr[1][0]=row2col1;
        arr[1][1]=row2col2;
        arr[1][2]=row2col3;
        arr[2][0]=row3col1;
        arr[2][1]=row3col2;
        arr[2][2]=row3col3;
    }

    private int getInputCount() {
        int count=0;
        for (Integer[] row :arr){
            for (int i=0;i<row.length;i++){
                if(row[i]!=0) count++;
            }
        }
        return count;
    }

    public void rotate90(){
        genArray();

        Integer[][] newArr=new Integer[arr.length][arr[0].length];
        int n=2;//最大偏移量=宽度-1
        for (int i=0;i<arr.length;i++) {
            for (int j = 0; j < arr[i].length; j++) {
                newArr[i][j]=arr[n-j][i];
            }
        }
        arr=newArr;
    }

    public Integer[][] getResult(){
        return arr;
    }

    /**
     * 顺时针90度旋转
     */
    public Integer[][] rotate90for4() {

        genArray();

        Integer[][] newArr=new Integer[arr.length][arr[0].length];
        //中心点下标
        int centerRi=1;
        int centerCi=1;
        int x=2;//x偏移量
        int y=2;//y偏移量

        for (int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                if(centerRi==i && centerCi==j){//中心无需偏移
                    newArr[i][j]=arr[i][j];
                    continue;
                }
                int nr=i;
                int nj=j;
                //用xy轴分为四象。i是y轴 j是x轴。偏东+x,偏南+y,偏西-x,偏北-y
                if(i<centerRi && j<=centerCi){      //第一象：位于西北/北 顺指针偏移：西北方向向东偏移，不足向南补足
                    nj=nj+x;
                    if(nj-x>0){
                        nr=nr+(nj-x);
                        nj=x;
                    }
                    newArr[nr][nj]=arr[i][j];
                }else if(i<=centerRi && j>centerCi){//第二象：位于东北/东 顺指针偏移：东北方向向南偏移，不足向西补足
                    nr=nr+y;
                    if(nr-y>0){
                        nj=nj-(nr-y);
                        nr=y;
                    }
                    newArr[nr][nj]=arr[i][j];
                }else if(i>centerRi && j>=centerCi){//第三象：位于南/东南 顺指针偏移：东南方向向西偏移，不足向北补足
                    nj=nj-x;
                    if(nj<0){
                        nr=nr-(-nj);
                        nj=0;
                    }
                    newArr[nr][nj]=arr[i][j];
                }else if(i>=centerRi && j<centerCi){//第四象：位于西/西南 顺指针偏移：西南方向向北偏移，不足向东补足
                    nr=nr-y;
                    if(nr<0){
                        nj=nj+(-nr);
                        nr=0;
                    }
                    newArr[nr][nj]=arr[i][j];
                }
            }
        }

        arr=newArr;
        return arr;
    }

    public void setEmpty() {
        this.row1col1 = 0;
        this.row1col2 = 0;
        this.row1col3 = 0;
        this.row2col1 = 0;
        this.row2col2 = 5;
        this.row2col3 = 0;
        this.row3col1 = 0;
        this.row3col2 = 0;
        this.row3col3 = 0;
        genArray();
    }

    private void setFromArr(Integer[][] arr){
        row1col1=arr[0][0];
        row1col2=arr[0][1];
        row1col3=arr[0][2];
        row2col1=arr[1][0];
        row2col2=arr[1][1];
        row2col3=arr[1][2];
        row3col1=arr[2][0];
        row3col2=arr[2][1];
        row3col3=arr[2][2];

    }
}
