package 杂;
import java.util.*;
public class MapTest1 {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Map<String, String> map = new HashMap<String,String>();
		map.put("星期一", "Monday");
		map.put("星期日", "Sunday");
		System.out.println(map);//{星期日=Sunday, 星期一=Monday}
		System.out.println(map.put("星期一", "Mon"));//Monday
		System.out.println(map);//{星期日=Sunday, 星期一=Mon}
		String en = map.get("星期日");
		System.out.println(en);//Sunday
		String valueString = map.remove("星期日");
		System.out.println(valueString);//Sunday
		System.out.println(map);//{星期一=Mon}
				
	}

}
