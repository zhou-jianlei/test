import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//业务流水号生成方法，流水号组成 前缀‘ymt_mybj_’ 后面跟上年月日时分； 后面跟上5位数字，不足5位前面补0；保证并发时流水号不能重复。
public class SerialNumber {
    private static final String PREFIX = "ymt_mybj_";
    private static final String DATE_FORMAT = "yyyyMMddHHmm";
    private static final String NUMBER_FORMAT = "00000";
    private static final int NUMBER_LENGTH = 5;
    private static final int MAX_NUMBER = 99999;

    private static final ThreadLocal<SimpleDateFormat> DATE_FORMATTER = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_FORMAT);
        }
    };

    private static final ThreadLocal<DecimalFormat> NUMBER_FORMATTER = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
            return new DecimalFormat(NUMBER_FORMAT);
        }
    };

    private static int number = 0;

    public static String getSerialNumber() {
        String date = DATE_FORMATTER.get().format(new Date());
        if (number >= MAX_NUMBER) {
            number = 0;
        }
        return PREFIX + date + NUMBER_FORMATTER.get().format(number++);
    }
}

// Path: ConcurrentTest.java
// public class ConcurrentTest {
//     public static void main(String[] args) {
//         final ExecutorService executorService = Executors.newFixedThreadPool(10);
//         for (int i = 0; i < 100; i++) {
//             executorService.execute(new Runnable() {
//                 @Override
//                 public void run() {
//                     System.out.println(SerialNumber.getSerialNumber());
//                 }
//             });
//         }
//         executorService.shutdown();
//     }
// }