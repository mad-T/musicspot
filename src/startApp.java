

public class startApp {

	public static void main(String[] args) throws Exception {
		try{
			Application.createConnection();
		}catch(Exception e){
			System.out.println("nope");
		}
	}
}
