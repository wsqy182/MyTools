import com.alibaba.fastjson.JSONObject;

public class FastJsonDemo {


  public static void main(String[] args) {
    JSONObject jsonObject = new JSONObject();
    long startTime = System.currentTimeMillis();
    long endTime = 0;
    for (int i = 0; i < 10000; i++) {
      jsonObject.put("" + i, i + "_value" + i + 1);
    }
    endTime = System.currentTimeMillis();
    System.out.println("插入万条数据Json库耗时：" + (endTime - startTime));


  }
}
