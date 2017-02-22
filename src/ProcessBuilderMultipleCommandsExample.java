//package com.javacodegeeks.process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ProcessBuilderMultipleCommandsExample {
	public static void main(String[] args) throws InterruptedException,
			IOException {
		// multiple commands
		// /C Carries out the command specified by string and then terminates
		ProcessBuilder pb = new ProcessBuilder("cmd.exe",
				"/C dir & echo example of & echo working dir");
		pb.directory(new File("src"));

		Process process = pb.start();
		IOThreadHandler outputHandler = new IOThreadHandler(
				process.getInputStream());
		outputHandler.start();
		process.waitFor();
		System.out.println(outputHandler.getOutput());
	}

	private static class IOThreadHandler extends Thread {
		private InputStream inputStream;
		private StringBuilder output = new StringBuilder();

		IOThreadHandler(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		public void run() {
			Scanner br = null;
			try {
				br = new Scanner(new InputStreamReader(inputStream));
				String line = null;
				while (br.hasNextLine()) {
					line = br.nextLine();
					output.append(line
							+ System.getProperty("line.separator"));
				}
			} finally {
				br.close();
			}
		}

		public StringBuilder getOutput() {
			return output;
		}
	}
}