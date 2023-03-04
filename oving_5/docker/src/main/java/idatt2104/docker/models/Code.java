package idatt2104.docker.models;

public class Code {
    private String input;
    private String output;

    public Code(String input){
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
