import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class bbst {

	/*driver function, takes test file as input for building tree and reads commands from console and prints output on console*/

	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("Syntax: java bbst test-file.txt");
			System.exit(-1);
		}
		Counter count = new Counter();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(args[0]));
			String sCurrentLine;
			Long start_time = System.currentTimeMillis();

			int numPairs = Integer.parseInt(br.readLine());
			count.init(br, numPairs);

			Long end_time = System.currentTimeMillis();
			double d = (end_time - start_time);

			System.out.println("Time taken to read = "+d+" Milli Second");

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] data = sCurrentLine.split(" ");
				if(data.length > 0){
					String command = data[0];
					if(command.equals("increase") && data.length > 2){
						int id = Integer.parseInt(data[1]);
						int value = Integer.parseInt(data[2]);
						count.increase(id, value);

					}else if(command.equals("reduce") && data.length > 2){

						int id = Integer.parseInt(data[1]);
						int value = Integer.parseInt(data[2]);
						count.reduce(id, value);

					}else if(command.equals("count") && data.length > 1){

						int id = Integer.parseInt(data[1]);
						count.count(id);

					}else if(command.equals("next") && data.length > 1 ){

						int id = Integer.parseInt(data[1]);
						count.next(id);

					}else if(command.equals("previous") && data.length > 1){

						int id = Integer.parseInt(data[1]);
						count.previous(id);
					}else if(command.equals("inrange") && data.length > 2){

						int id1 = Integer.parseInt(data[1]);
						int id2 = Integer.parseInt(data[2]);

						count.inRange(id1, id2);
					}
					else if(command.equals("quit")){
						System.exit(0);
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
