package wbhhc.luoshu;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CalcTest {

    @Test
    public void doValied() {
        Calc calc=new Calc();
        boolean res = calc.doValied();

        assertThat(res,is(true));

        System.out.println("结果："+(res?"成功":"失败"));

        System.out.println(calc.toString());
    }

    @Test
    public void doValied_customInput() {

        Calc calc=new Calc( 8,1,6,
                            3,5,7,
                            4,9,2);
        boolean res = calc.doValied();

        assertThat(res,is(true));

        System.out.println("结果："+(res?"成功":"失败"));

        System.out.println(calc.toString());
    }

    @Test(expected = RuntimeException.class)
    public void autofill_throw_countenf() {
        Calc calc=new Calc( 0,0,0,
                            0,5,0,
                            0,9,0);
        boolean res = calc.autoFill();
    }

    @Test(expected = RuntimeException.class)
    public void autofill_throw_isSymmetry() {
        Calc calc=new Calc( 8,0,0,
                            0,5,0,
                            0,0,2);
        boolean res = calc.autoFill();
    }

    @Test
    public void autofill() {
        Calc calc=new Calc( 0,0,6,
                            3,5,0,
                            0,0,0);
        boolean res = calc.autoFill();

        assertThat(res,is(true));

        System.out.println("结果："+(res?"成功":"失败"));

        System.out.println(calc.toString());
    }

    /**
     * 旋转角度顺时针90度
     */
    @Test
    public void autofill_rotating90() {
        Calc calc=new Calc( 0,3,0,
                            0,5,0,
                            0,0,6);
        boolean res = calc.autoFill();

        assertThat(res,is(true));

        System.out.println("结果："+(res?"成功":"失败"));

        System.out.println(calc.toString());
    }

    /**
     * 旋转角度180度
     */
    @Test
    public void autofill_rotating180() {
        Calc calc=new Calc( 0,0,0,
                            0,5,3,
                            6,0,0);
        boolean res = calc.autoFill();

        assertThat(res,is(true));

        System.out.println("结果："+(res?"成功":"失败"));

        System.out.println(calc.toString());
    }

    @Test
    public void rotate90() {
        Calc calc=new Calc();
        calc.rotate90();
        Integer[][] arr = calc.getResult();

        assertThat(8,is(arr[0][2]));
        assertThat(1,is(arr[1][2]));
        assertThat(6,is(arr[2][2]));

        System.out.println(calc.toString());
    }
}