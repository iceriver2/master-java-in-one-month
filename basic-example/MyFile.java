import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MyFile {
  public static void main(String[] args) {
    FileOp file = new FileOp();
    out.println(file.read("./HelloWorld.java").length());
    // out.println(file.Write("./output.txt"));
    file.scan();
  }
}

class FileOp {
  public String read(String filename) {
    String context = "";

    try(
      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);
      ) {
      String line;
      while((line = br.readLine()) != null) {
        context += line + "\n";
      }
    } catch (IOException e) {
      
    }
    return context;
  }

  public boolean Write(String filename) {
    boolean flag = false;

    try (
      FileWriter fw = new FileWriter(filename);
      BufferedWriter bw = new BufferedWriter(fw);
    ) {
      for (int i =0; i<5;i++) {
        bw.write("hello, world\n");
      }
      flag = true;
    } catch (IOException e) {

    }

    return flag;
  }

  public void scan() {
    Scanner scan = new Scanner(System.in);

    System.out.println("nextLine方式接收：");
    while (scan.hasNextLine()) {
        String str2 = scan.nextLine();
        System.out.println("输入的数据为：" + str2);
    }
    scan.close();
  }
}