package com.example.calculator2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // 计算结果TextView
    private TextView tv_result;
    // 屏幕显示的文本内容
    private String showText = "";
    // 第一个操作数
    private String firstNum = "";
    // 操作符
    private String operator = "";
    // 第二个操作数
    private String secondNum = "";
    // 当前的计算结果
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_result = findViewById(R.id.result);
        // 给每一个按钮绑定点击事件
        setOnClickListener();

    }

    @Override
    public void onClick(View view) {
        String inputText;
        // 判断根号的特殊输入
        if (view.getId() == R.id.ib_sqrt) {
            inputText = "√";
        } else {
            inputText = ((TextView) view).getText().toString();
        }
        switch (view.getId()) {
            // 清除按钮
            case R.id.btn_clean:
                clear();
                break;
            // 取消按钮
            case R.id.btn_cancel:
                break;
            // 求倒数按钮
            case R.id.btn_reciprocal:
                if (isBlank(firstNum)) {
                    break;
                }
                double recResult = 1.0 / Double.parseDouble(firstNum);
                refreshResult(String.valueOf(recResult));
                refreshText(showText + "/=" + result);
                break;
            // 开方按钮
            case R.id.ib_sqrt:
                if (isBlank(firstNum)) {
                    break;
                }
                double sqrtResult = Math.sqrt(Double.parseDouble(firstNum));
                refreshResult(String.valueOf(sqrtResult));
                refreshText(showText + "√=" + result);
                break;
            // 等于
            case R.id.btn_equal:
                // 必须具备操作数和操作符，才能计算结果
                if (isBlank(operator) || isBlank(firstNum) || isBlank(secondNum)) {
                    break;
                }
                double calResult = calculateFour();
                // 更新计算结果
                refreshResult(String.valueOf(calResult));
                refreshText(showText + "=" + result);
                break;
            // 加减乘除
            case R.id.btn_plus:
            case R.id.btn_minus:
            case R.id.btn_multiply:
            case R.id.btn_divide:
                operator = inputText;
                refreshText(showText + inputText);
                break;
            // 数字和点
            default:
                // 上次的运算结果存在，需要清空显示文本
                if (isNotBlank(result) && isBlank(operator)) {
                    clear();
                }
                // 有操作符，则拼接第一个操作数
                if ("".equals(operator)) {
                    firstNum = firstNum + inputText;
                } else {
                    secondNum = secondNum + inputText;
                }
                // 如果前面有0，且当前输入的不是.，直接拼接当前的字符
                if ("0".equals(showText) && !".".equals(inputText)) {
                    refreshText(inputText);
                } else {
                    refreshText(showText + inputText);
                }
                break;
        }
    }

    public double calculateFour() {
        if (isBlank(firstNum) || isBlank(secondNum)) {
            return 0;
        }
        double first = Double.parseDouble(firstNum);
        double second = Double.parseDouble(secondNum);
        double result = 0;
        switch (operator) {
            case "+":
                result = first + second;
                break;
            case "-":
                result = first - second;
                break;
            case "×":
                result = first * second;
                break;
            case "÷":
                result = first / second;
                break;
        }
        return result;
    }

    // 判断字符串非空
    private boolean isNotBlank(String text) {
        return !isBlank(text);
    }

    // 判断字符串为空
    private boolean isBlank(String text) {
        return text == null || text.length() == 0;
    }

    /***
     * 清空文本显示并初始化
     */
    public void clear() {
        refreshResult("");
        refreshText("0");
    }

    /**
     * 刷新运算结果
     * @param newResult
     */
    public void refreshResult(String newResult) {
        result = newResult;
        firstNum = result;
        operator = "";
        secondNum = "";
    }

    /**
     * 刷新文本显示
     * @param text
     */
    public void refreshText(String text) {
        showText = text;
        tv_result.setText(showText);
    }


    private void setOnClickListener() {
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_clean).setOnClickListener(this);
        findViewById(R.id.btn_multiply).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);
        findViewById(R.id.btn_reciprocal).setOnClickListener(this);
        findViewById(R.id.ib_sqrt).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);
        findViewById(R.id.btn_seven).setOnClickListener(this);
        findViewById(R.id.btn_eight).setOnClickListener(this);
        findViewById(R.id.btn_nine).setOnClickListener(this);
        findViewById(R.id.btn_dot).setOnClickListener(this);
    }
}