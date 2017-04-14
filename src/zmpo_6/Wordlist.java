package zmpo_6;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Wordlist {

    static String getText() {
	Scanner scanner = new Scanner(System.in);
	String text = scanner.nextLine();
	scanner.close();
	return text;
    }

    static String getText(String fileName) throws FileNotFoundException {
	Scanner load = new Scanner(new File(fileName));
	String text = "";
	while (load.hasNext())
	    text += load.nextLine();
	load.close();
	return text;
    }

    static ArrayList<String> getAllWords(String text) throws IOException {
	ArrayList<String> list = new ArrayList<String>();
	ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
	bOutput.write(text.getBytes());
	byte b[] = bOutput.toByteArray();
	StringBuffer buffer = new StringBuffer();
	for (int i = 0; i < b.length; i++) {
	    if (i < b.length && ((char) b[i] != ' ' && (char) b[i] != ',' && (char) b[i] != '.')) {
		buffer.append((char) b[i]);
		continue;
	    }
	    if (buffer.length() != 0) {
		list.add(buffer.toString());
		buffer.setLength(0);
	    }
	}
	return list;
    }

    static ArrayList<Para> getWordsWithoutRepetition(String text, int n) throws IOException {
	ArrayList<Para> list = new ArrayList<Para>();
	ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
	bOutput.write(text.getBytes());
	byte b[] = bOutput.toByteArray();
	StringBuffer buffer = new StringBuffer();

	for (int i = 0; i < b.length; i++) {
	    if (i < b.length && ((char) b[i] != ' ' && (char) b[i] != ',' && (char) b[i] != '.')) {
		buffer.append((char) b[i]);
		continue;
	    }
	    if (buffer.length() != 0) {
		Para temp = new Para();
		temp.wyraz = buffer.toString();
		boolean znaleziono = false;
		for (Para x : list)
		    if (x.wyraz.equals(buffer.toString())) {
			znaleziono = true;
			x.n++;
		    }
		if (buffer.length() != 0 && znaleziono == false)
		    list.add(temp);
	    }
	    if (list.size() == n)
		break;
	    buffer.setLength(0);
	}
	return list;
    }

    public static void main(String args[]) throws IOException, InterruptedException {

	String text = getText("file.txt");
	long start1 = System.currentTimeMillis();
	// ArrayList<String> list = getAllWords(text);
	// for (int i = 0; i < list.size(); i++)
	// System.out.println(list.get(i));
	long time1 = System.currentTimeMillis() - start1;
	System.out.println();
	long start2 = System.currentTimeMillis();
	ArrayList<Para> list2 = getWordsWithoutRepetition(text, 100);
	for (int i = 0; i < list2.size(); i++)
	    System.out.printf("%-20s %d\n", list2.get(i).wyraz, list2.get(i).n);
	    //System.out.println(list2.get(i).wyraz + "\t" + list2.get(i).n);
	long time2 = System.currentTimeMillis() - start2;
	// System.out.println(time1);
	// System.out.println(time2);
    }
}