package idatt2104.docker.services;


import idatt2104.docker.models.Code;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class Compiler {
    public Code compile(Code inputCode) throws IOException {
        String code = inputCode.getInput();

        String[] commands = {"docker", "run", "--rm", "python:latest", "python", "-c", code};

        Process process = Runtime.getRuntime().exec(commands);

        BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder errorsOut = new StringBuilder();
        String line;
        while ((line = errors.readLine()) != null){
            errorsOut.append(line).append("\n");
        }

        StringBuilder output = new StringBuilder();

        BufferedReader codeReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String codeLine;

        while((codeLine = codeReader.readLine()) != null){
            output.append(codeLine).append("\n");
        }

        if (errorsOut.length() != 0){
            output.append(errorsOut);
        }

        try{
            process.waitFor();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        inputCode.setOutput(output.toString());
        return inputCode;
    }

}
